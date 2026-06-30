package fungsi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;

/**
 * GLOBAL FIX untuk masalah angka desimal ".0" yang muncul di banyak modul
 * (Resep Obat, Rawat Inap, dll) akibat kolom database bertipe DECIMAL/FLOAT.
 *
 * Cara kerja:
 *  - Membungkus Connection yang dihasilkan koneksiDB.condb() dengan dynamic
 *    proxy, supaya setiap PreparedStatement/Statement dan ResultSet yang
 *    dibuat dari koneksi itu juga ikut "terbungkus" secara otomatis.
 *  - Hanya method getString() pada ResultSet yang diintervensi: kalau nilai
 *    aslinya berupa angka desimal yang BENAR-BENAR bulat (misal "1.0",
 *    "3.0"), maka dikembalikan tanpa ".0" (jadi "1", "3").
 *  - Nilai pecahan asli (misal "0.5" untuk setengah tablet) TIDAK diubah.
 *  - Semua method ResultSet/Statement/Connection lain (getDouble, getInt,
 *    next(), close(), dst) tetap berjalan normal lewat delegasi otomatis,
 *    tidak perlu ditulis manual satu-satu.
 *
 * Efeknya menjalar otomatis ke SEMUA modul yang mengambil Connection lewat
 * koneksiDB.condb() -- termasuk lewat helper class lain seperti
 * sekuel2.cariIsi(), karena class-class itu juga memanggil
 * koneksiDB.condb() untuk mendapatkan Connection-nya.
 *
 * TIDAK PERLU mengubah satu pun file modul (DlgResepObat.java,
 * DlgKamarInap.java, dst) -- cukup pasang di koneksiDB.java saja.
 */
public final class ResultSetCleanerProxy {

    private ResultSetCleanerProxy(){}

    // ====== 1. Bungkus Connection ======
    public static Connection wrap(Connection target) {
        if (target == null) return null;
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class[]{Connection.class},
            new ConnectionHandler(target)
        );
    }

    static class ConnectionHandler implements InvocationHandler {
        private final Connection target;
        ConnectionHandler(Connection target){ this.target = target; }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                Object result = method.invoke(target, args);
                // Setiap kali modul minta PreparedStatement / Statement,
                // kita ikut bungkus juga supaya hasil query-nya ikut ter-proxy.
                if (result instanceof PreparedStatement) {
                    return wrapStatement(result);
                }
                if (result instanceof Statement) {
                    return wrapStatement(result);
                }
                return result;
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }

    // ====== 2. Bungkus Statement/PreparedStatement ======
    static Object wrapStatement(Object target) {
        Class<?>[] ifaces = (target instanceof PreparedStatement)
                ? new Class[]{PreparedStatement.class}
                : new Class[]{Statement.class};
        return Proxy.newProxyInstance(
            ResultSetCleanerProxy.class.getClassLoader(),
            ifaces,
            new StatementHandler(target)
        );
    }

    static class StatementHandler implements InvocationHandler {
        private final Object target;
        StatementHandler(Object target){ this.target = target; }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                Object result = method.invoke(target, args);
                if (result instanceof ResultSet) {
                    return wrapResultSet((ResultSet) result);
                }
                return result;
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }

    // ====== 3. Bungkus ResultSet -- INTI PERBAIKANNYA DI SINI ======
    static ResultSet wrapResultSet(ResultSet target) {
        if (target == null) return null;
        return (ResultSet) Proxy.newProxyInstance(
            ResultSetCleanerProxy.class.getClassLoader(),
            new Class[]{ResultSet.class},
            new ResultSetHandler(target)
        );
    }

    static class ResultSetHandler implements InvocationHandler {
        private final ResultSet target;
        ResultSetHandler(ResultSet target){ this.target = target; }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                // Hanya method getString yang kita intervensi, sisanya jalan normal
                if ("getString".equals(method.getName())) {
                    String raw = (String) method.invoke(target, args);
                    return bersihkanAngka(raw);
                }
                return method.invoke(target, args);
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw e.getCause();
            }
        }

        /**
         * Hanya buang ".0" / ".00" dst kalau nilainya BENAR-BENAR bulat.
         * Nilai pecahan asli (misal "0.5" untuk setengah tablet) TETAP UTUH.
         * String non-angka (tanggal, teks, kode barang, dll) tidak disentuh.
         */
        private String bersihkanAngka(String raw) {
            if (raw == null) return null;
            if (raw.matches("-?\\d+\\.\\d+")) {
                try {
                    double nilai = Double.parseDouble(raw);
                    if (nilai == Math.floor(nilai) && !Double.isInfinite(nilai)) {
                        return String.valueOf((long) nilai); // "1.0" -> "1"
                    }
                } catch (NumberFormatException ignore) {
                    // bukan angka valid, kembalikan apa adanya
                }
            }
            return raw;
        }
    }
}
