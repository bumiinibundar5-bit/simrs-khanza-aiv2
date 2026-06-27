package rekammedis;

import fungsi.koneksiDB;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class RMPartografGrafik extends JDialog {

    private String namaPasien = "";

    public RMPartografGrafik(String title, String query1, String kolom1, String kolom2, String kolom3, String kolom4,
            String query2, String kolomPembukaan, String kolomPenurunan) {

        // Ambil nama pasien dari database
        getNamaPasien(query1);

        String judulLengkap = "PARTOGRAF " + (namaPasien.isEmpty() ? "PASIEN TIDAK DIKETAHUI" : namaPasien);
        setTitle(judulLengkap);

        // Buat panel chart langsung tanpa legend
        JPanel chartPanel = createDemoPanel(query1, kolom1, kolom2, kolom3, kolom4, query2, kolomPembukaan, kolomPenurunan);

        // Buat scroll pane dengan scroll vertikal
        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setContentPane(scrollPane);

        setModal(true);
        setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screen.width * 0.95), (int) (screen.height * 0.9));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        chartPanel.setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
    }

    // Method untuk mendapatkan identitas lengkap pasien dari database
    private void getNamaPasien(String query) {
        try {
            // Modifikasi query untuk mendapatkan identitas lengkap pasien
            String queryIdentitas = query.replace("SELECT *", "SELECT p.nm_pasien, p.no_rkm_medis, p.tgl_lahir")
                    .replace("ORDER BY", "GROUP BY p.nm_pasien, p.no_rkm_medis, p.tgl_lahir ORDER BY");
            if (!queryIdentitas.contains("nm_pasien")) {
                // Jika query tidak memiliki nm_pasien, coba ambil dari reg_periksa
                queryIdentitas = "SELECT p.nm_pasien, p.no_rkm_medis, p.tgl_lahir FROM reg_periksa r "
                        + "INNER JOIN pasien p ON r.no_rkm_medis = p.no_rkm_medis "
                        + "WHERE " + query.substring(query.indexOf("WHERE") + 5,
                                query.indexOf("ORDER BY") > 0 ? query.indexOf("ORDER BY") : query.length())
                        + " LIMIT 1";
            }

            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(queryIdentitas);
            if (rs.next()) {
                String nama = rs.getString("nm_pasien");
                String noRM = rs.getString("no_rkm_medis");
                String tglLahir = rs.getString("tgl_lahir");

                // Format identitas: Nama (No.RM: xxx, Lahir: dd-mm-yyyy)
                namaPasien = nama + " (No.RM: " + noRM
                        + (tglLahir != null ? ", Lahir: " + tglLahir : "") + ")";
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            System.out.println("Error getting patient identity: " + e.getMessage());
            namaPasien = "PASIEN TIDAK DIKETAHUI";
        }
    }

    // Helper method untuk format waktu yang konsisten
    private static String formatWaktu(String tanggal, String jam) {
        try {
            // Parse jam dari format HH:mm:ss ke HH:mm
            String jamFormatted = jam.length() >= 5 ? jam.substring(0, 5) : jam;
            
            // Format tanggal dari yyyy-MM-dd ke dd/MM
            String[] tglParts = tanggal.split("-");
            if (tglParts.length == 3) {
                return tglParts[2] + "/" + tglParts[1] + " " + jamFormatted;
            }
            
            // Fallback
            return tanggal.substring(8, 10) + "/" + tanggal.substring(5, 7) + " " + jamFormatted;
        } catch (Exception e) {
            // Fallback jika parsing gagal
            String jamFormatted = jam.length() >= 5 ? jam.substring(0, 5) : jam;
            return tanggal + " " + jamFormatted;
        }
    }

    // Helper method untuk mengurutkan data berdasarkan waktu
    private static Map<String, Object[]> getSortedTimeData(String query, DataExtractor extractor) {
        Map<String, Object[]> timeDataMap = new TreeMap<>();
        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktuKey = tanggal + " " + jam; // Untuk sorting
                String waktuDisplay = formatWaktu(tanggal, jam); // Untuk display
                
                Object[] data = extractor.extractData(rs, waktuDisplay);
                if (data != null) {
                    timeDataMap.put(waktuKey, data);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeDataMap;
    }

    // Interface untuk extract data
    private interface DataExtractor {
        Object[] extractData(ResultSet rs, String waktuDisplay) throws SQLException;
    }

    // Dataset untuk Denyut Jantung Janin (80-200 bpm sesuai form)
    public static CategoryDataset createDatasetDJJ(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesDJJ = "DJJ";
        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String djjStr = rs.getString("djj");
                if (djjStr != null && !djjStr.trim().isEmpty()) {
                    try {
                        double djj = Double.parseDouble(djjStr);
                        if (djj >= 80 && djj <= 200) { // Sesuai range form PDF
                            result.addValue(djj, seriesDJJ, waktu);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing DJJ: " + djjStr);
                    }
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Air Ketuban sesuai database
    public static CategoryDataset createDatasetAirKetuban(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesKetuban = "Ketuban";
        String seriesPenyusupan = "Penyusupan";

        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String ketuban = rs.getString("ketuban");
                String penyusupan = rs.getString("penyusupan");

                if (ketuban != null && !ketuban.trim().isEmpty()) {
                    double nilai = parseKetubanValue(ketuban);
                    result.addValue(nilai, seriesKetuban, waktu);
                }

                if (penyusupan != null && !penyusupan.trim().isEmpty()) {
                    double nilai = parsePenyusupanValue(penyusupan);
                    result.addValue(nilai, seriesPenyusupan, waktu);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Pembukaan Serviks dengan Garis WASPADA dan BERTINDAK
    public static CategoryDataset createDatasetPembukaan(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesPembukaan = "Pembukaan Serviks";
        String seriesWaspada = "WASPADA";
        String seriesBertindak = "BERTINDAK";

        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);

            java.util.List<String> waktuList = new java.util.ArrayList<>();
            java.util.List<Double> pembukaanList = new java.util.ArrayList<>();

            // Collect data
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String pembukaanStr = rs.getString("pembukaan");
                if (pembukaanStr != null && !pembukaanStr.trim().isEmpty()) {
                    try {
                        double pembukaan = Double.parseDouble(pembukaanStr);
                        waktuList.add(waktu);
                        pembukaanList.add(pembukaan);
                        result.addValue(pembukaan, seriesPembukaan, waktu);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing Pembukaan: " + pembukaanStr);
                    }
                }
            }

            // Buat garis WASPADA dan BERTINDAK sesuai form PDF
            // Garis WASPADA dimulai dari jam ke-1 pada pembukaan 4cm
            // Garis BERTINDAK 4 jam setelah garis WASPADA
            for (int i = 0; i < waktuList.size(); i++) {
                String waktu = waktuList.get(i);

                // Garis WASPADA: mulai dari 4cm, naik 1cm per jam
                double waspada = 4 + i; // i adalah jam ke-i
                if (waspada <= 10) {
                    result.addValue(waspada, seriesWaspada, waktu);
                }

                // Garis BERTINDAK: 4 jam setelah garis waspada
                if (i >= 4) {
                    double bertindak = 4 + (i - 4);
                    if (bertindak <= 10) {
                        result.addValue(bertindak, seriesBertindak, waktu);
                    }
                }
            }

            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Penurunan Kepala (Turunnya kepala janin)
    public static CategoryDataset createDatasetPenurunanKepala(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesPenurunan = "Penurunan Kepala";
        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String penurunanStr = rs.getString("penurunankepala");
                if (penurunanStr != null && !penurunanStr.trim().isEmpty()) {
                    double penurunan = parseStationValue(penurunanStr);
                    result.addValue(penurunan, seriesPenurunan, waktu);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Kontraksi sesuai enum database
    public static CategoryDataset createDatasetKontraksi(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesKontraksi1 = "< 20 detik";
        String seriesKontraksi2 = "20-40 detik";
        String seriesKontraksi3 = "> 40 detik";

        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String kontraksi = rs.getString("kontraksi"); // enum('<20','20-40','>40')
                String lamaKontraksi = rs.getString("lamakontraksi");

                if (kontraksi != null && lamaKontraksi != null) {
                    try {
                        double lama = Double.parseDouble(lamaKontraksi);

                        if (kontraksi.equals("<20")) {
                            result.addValue(lama, seriesKontraksi1, waktu);
                        } else if (kontraksi.equals("20-40")) {
                            result.addValue(lama, seriesKontraksi2, waktu);
                        } else if (kontraksi.equals(">40")) {
                            result.addValue(lama, seriesKontraksi3, waktu);
                        }
                        System.out.println("Kontraksi: " + waktu + " = " + kontraksi + " selama " + lama + " detik");
                    } catch (NumberFormatException e) {
                        // Jika lamakontraksi bukan angka, gunakan nilai default berdasarkan kategori
                        double defaultValue = kontraksi.equals("<20") ? 1
                                : kontraksi.equals("20-40") ? 2 : 3;

                        if (kontraksi.equals("<20")) {
                            result.addValue(defaultValue, seriesKontraksi1, waktu);
                        } else if (kontraksi.equals("20-40")) {
                            result.addValue(defaultValue, seriesKontraksi2, waktu);
                        } else if (kontraksi.equals(">40")) {
                            result.addValue(defaultValue, seriesKontraksi3, waktu);
                        }
                    }
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Oksitosin, Obat dan Cairan IV
    public static CategoryDataset createDatasetObatCairan(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesOksitosin = "Oksitosin";
        String seriesObat = "Obat Lain";

        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String oksitosinStr = rs.getString("oksitosin");
                String obatStr = rs.getString("obat_lain");

                if (oksitosinStr != null && !oksitosinStr.trim().isEmpty()) {
                    try {
                        double oksitosin = Double.parseDouble(oksitosinStr);
                        result.addValue(oksitosin, seriesOksitosin, waktu);
                    } catch (NumberFormatException e) {
                        result.addValue(1, seriesOksitosin, waktu); // Ada pemberian
                    }
                }

                if (obatStr != null && !obatStr.trim().isEmpty()) {
                    result.addValue(1, seriesObat, waktu);
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Nadi dan Tekanan Darah - SESUAI STRUKTUR DATABASE
    public static CategoryDataset createDatasetVitalSign(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesNadi = "Nadi";
        String seriesSistolik = "Sistolik";
        String seriesDiastolik = "Diastolik";

        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            int count = 0;

            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                // Ambil data sesuai struktur tabel partograf_observasi
                String nadiStr = rs.getString("nadi");
                String tdStr = rs.getString("td"); // Format: "120/80"

                // Parsing nadi
                if (nadiStr != null && !nadiStr.trim().isEmpty()) {
                    try {
                        double nadi = Double.parseDouble(nadiStr);
                        if (nadi > 0 && nadi < 300) {
                            result.addValue(nadi, seriesNadi, waktu);
                            System.out.println("Nadi Data: " + waktu + " = " + nadi);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing Nadi: " + nadiStr);
                    }
                }

                // Parsing tekanan darah format "120/80"
                if (tdStr != null && !tdStr.trim().isEmpty() && tdStr.contains("/")) {
                    String[] parts = tdStr.split("/");
                    if (parts.length == 2) {
                        try {
                            double sistolik = Double.parseDouble(parts[0].trim());
                            double diastolik = Double.parseDouble(parts[1].trim());

                            if (sistolik > 0 && sistolik < 300 && diastolik > 0 && diastolik < 200) {
                                result.addValue(sistolik, seriesSistolik, waktu);
                                result.addValue(diastolik, seriesDiastolik, waktu);
                                System.out.println("Tekanan Darah: " + waktu + " = " + sistolik + "/" + diastolik);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing TD: " + tdStr);
                        }
                    }
                }
                count++;
            }
            System.out.println("Total Vital Signs data rows processed: " + count);
            rs.close();
            stat.close();
        } catch (SQLException e) {
            System.out.println("SQL Error Vital Signs: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Suhu
    public static CategoryDataset createDatasetSuhu(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesSuhu = "Suhu";
        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String suhuStr = rs.getString("suhu");
                if (suhuStr != null && !suhuStr.trim().isEmpty()) {
                    try {
                        double suhu = Double.parseDouble(suhuStr);
                        if (suhu > 30 && suhu < 50) { // Validasi range suhu
                            result.addValue(suhu, seriesSuhu, waktu);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing Suhu: " + suhuStr);
                    }
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Dataset untuk Urin (Protein, Aseton, Volume)
    public static CategoryDataset createDatasetUrin(String query) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        String seriesProtein = "Protein";
        String seriesAseton = "Aseton";
        String seriesVolume = "Volume";

        try {
            Statement stat = koneksiDB.condb().createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                String tanggal = rs.getString("tgl");
                String jam = rs.getString("jam_rawat");
                String waktu = formatWaktu(tanggal, jam);

                String proteinStr = rs.getString("protein");
                String asetonStr = rs.getString("aseton");
                String volumeStr = rs.getString("volume");

                if (proteinStr != null && !proteinStr.trim().isEmpty()) {
                    double protein = parseUrinValue(proteinStr);
                    result.addValue(protein, seriesProtein, waktu);
                }

                if (asetonStr != null && !asetonStr.trim().isEmpty()) {
                    double aseton = parseUrinValue(asetonStr);
                    result.addValue(aseton, seriesAseton, waktu);
                }

                if (volumeStr != null && !volumeStr.trim().isEmpty()) {
                    try {
                        double volume = Double.parseDouble(volumeStr);
                        result.addValue(volume / 10, seriesVolume, waktu); // Scale down untuk visualisasi
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing Volume: " + volumeStr);
                    }
                }
            }
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Grafik partograf TANPA LEGEND untuk menghindari tumpang tindih
    private static JFreeChart createChart(String query1, String kolom1, String kolom2, String kolom3, String kolom4,
            String query2, String kolomPembukaan, String kolomPenurunan) {

        // 1. Denyut Jantung Janin (80-200 bpm) - Paling atas dan terbesar
        CategoryDataset datasetDJJ = createDatasetDJJ(query1);
        NumberAxis rangeAxisDJJ = new NumberAxis("DJJ");
        rangeAxisDJJ.setRange(80, 200);
        rangeAxisDJJ.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxisDJJ.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisDJJ.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        LineAndShapeRenderer rendererDJJ = new LineAndShapeRenderer(true, true);
        rendererDJJ.setSeriesPaint(0, Color.BLACK);
        rendererDJJ.setSeriesStroke(0, new BasicStroke(2.0f));
        // Set renderer tanpa item labels
        rendererDJJ.setSeriesItemLabelsVisible(0, false);
        CategoryPlot subplotDJJ = new CategoryPlot(datasetDJJ, null, rangeAxisDJJ, rendererDJJ);
        subplotDJJ.setBackgroundPaint(Color.WHITE);
        subplotDJJ.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 2. Air Ketuban dan Penyusupan - Section kecil
        CategoryDataset datasetAirKetuban = createDatasetAirKetuban(query1);
        NumberAxis rangeAxisAirKetuban = new NumberAxis("Ketuban");
        rangeAxisAirKetuban.setRange(0, 5);
        rangeAxisAirKetuban.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisAirKetuban.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        LineAndShapeRenderer rendererAirKetuban = new LineAndShapeRenderer(false, true);
        rendererAirKetuban.setSeriesPaint(0, Color.BLUE);
        rendererAirKetuban.setSeriesPaint(1, Color.RED);
        // Set renderer tanpa item labels
        rendererAirKetuban.setSeriesItemLabelsVisible(0, false);
        rendererAirKetuban.setSeriesItemLabelsVisible(1, false);
        CategoryPlot subplotAirKetuban = new CategoryPlot(datasetAirKetuban, null, rangeAxisAirKetuban, rendererAirKetuban);
        subplotAirKetuban.setBackgroundPaint(Color.WHITE);
        subplotAirKetuban.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 3. Pembukaan Serviks dengan WASPADA dan BERTINDAK
        CategoryDataset datasetPembukaan = createDatasetPembukaan(query1);
        NumberAxis rangeAxisPembukaan = new NumberAxis("Pembukaan");
        rangeAxisPembukaan.setRange(0, 10);
        rangeAxisPembukaan.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxisPembukaan.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisPembukaan.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));

        LineAndShapeRenderer rendererPembukaan = new LineAndShapeRenderer(true, true) {
            @Override
            public Shape getItemShape(int row, int column) {
                if (row == 0) {
                    return createXShape();        // Pembukaan - X
                }
                if (row == 1) {
                    return createSmallCircle();   // WASPADA - circle
                }
                if (row == 2) {
                    return createSmallSquare();   // BERTINDAK - square
                }
                return super.getItemShape(row, column);
            }
        };

        rendererPembukaan.setSeriesPaint(0, Color.BLACK);
        rendererPembukaan.setSeriesPaint(1, new Color(255, 165, 0)); // WASPADA - Orange
        rendererPembukaan.setSeriesPaint(2, Color.RED); // BERTINDAK - Merah

        // Garis WASPADA dan BERTINDAK dengan style yang jelas
        rendererPembukaan.setSeriesStroke(0, new BasicStroke(2.0f));
        rendererPembukaan.setSeriesStroke(1, new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND, 1.0f, new float[]{8.0f, 4.0f}, 0.0f));
        rendererPembukaan.setSeriesStroke(2, new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND, 1.0f, new float[]{10.0f, 5.0f}, 0.0f));

        // Set renderer tanpa item labels untuk menghindari tumpang tindih
        rendererPembukaan.setSeriesItemLabelsVisible(0, false);
        rendererPembukaan.setSeriesItemLabelsVisible(1, false);
        rendererPembukaan.setSeriesItemLabelsVisible(2, false);
        CategoryPlot subplotPembukaan = new CategoryPlot(datasetPembukaan, null, rangeAxisPembukaan, rendererPembukaan);
        subplotPembukaan.setBackgroundPaint(Color.WHITE);
        subplotPembukaan.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 4. Penurunan Kepala
        CategoryDataset datasetPenurunan = createDatasetPenurunanKepala(query1);
        NumberAxis rangeAxisPenurunan = new NumberAxis("Penurunan Kepala");
        rangeAxisPenurunan.setRange(-3, 5);
        rangeAxisPenurunan.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisPenurunan.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        LineAndShapeRenderer rendererPenurunan = new LineAndShapeRenderer(false, true) {
            @Override
            public Shape getItemShape(int row, int column) {
                return createCircleShape();
            }
        };
        rendererPenurunan.setSeriesPaint(0, Color.BLACK);
        // Set renderer tanpa item labels
        rendererPenurunan.setSeriesItemLabelsVisible(0, false);
        CategoryPlot subplotPenurunan = new CategoryPlot(datasetPenurunan, null, rangeAxisPenurunan, rendererPenurunan);
        subplotPenurunan.setBackgroundPaint(Color.WHITE);
        subplotPenurunan.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 5. Kontraksi dengan pola arsir WHO
        CategoryDataset datasetKontraksi = createDatasetKontraksi(query1);
        NumberAxis rangeAxisKontraksi = new NumberAxis("HIS");
        rangeAxisKontraksi.setRange(0, 5);
        rangeAxisKontraksi.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisKontraksi.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));

        LineAndShapeRenderer rendererKontraksi = new LineAndShapeRenderer(false, true) {
            @Override
            public Shape getItemShape(int row, int column) {
                // Berbeda shape untuk durasi kontraksi yang berbeda
                if (row == 0) {
                    return createDottedSquare();    // < 20 detik - dots
                }
                if (row == 1) {
                    return createHatchedSquare();   // 20-40 detik - hatch
                }
                if (row == 2) {
                    return createSolidSquare();     // > 40 detik - solid
                }
                return createSquareShape();
            }
        };
        rendererKontraksi.setSeriesPaint(0, Color.BLACK);
        rendererKontraksi.setSeriesPaint(1, Color.BLACK);
        rendererKontraksi.setSeriesPaint(2, Color.BLACK);
        // Set renderer tanpa item labels
        rendererKontraksi.setSeriesItemLabelsVisible(0, false);
        rendererKontraksi.setSeriesItemLabelsVisible(1, false);
        rendererKontraksi.setSeriesItemLabelsVisible(2, false);
        CategoryPlot subplotKontraksi = new CategoryPlot(datasetKontraksi, null, rangeAxisKontraksi, rendererKontraksi);
        subplotKontraksi.setBackgroundPaint(Color.WHITE);
        subplotKontraksi.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 6. Oksitosin dan Obat
        CategoryDataset datasetObat = createDatasetObatCairan(query1);
        NumberAxis rangeAxisObat = new NumberAxis("Oxytocin");
        rangeAxisObat.setRange(0, 10);
        rangeAxisObat.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisObat.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        LineAndShapeRenderer rendererObat = new LineAndShapeRenderer(false, true);
        rendererObat.setSeriesPaint(0, Color.MAGENTA);
        rendererObat.setSeriesPaint(1, Color.CYAN);
        // Set renderer tanpa item labels
        rendererObat.setSeriesItemLabelsVisible(0, false);
        rendererObat.setSeriesItemLabelsVisible(1, false);
        CategoryPlot subplotObat = new CategoryPlot(datasetObat, null, rangeAxisObat, rendererObat);
        subplotObat.setBackgroundPaint(Color.WHITE);
        subplotObat.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 7. Nadi dan Tekanan Darah (TANPA garis max-min)
        CategoryDataset datasetVital = createDatasetVitalSign(query1);
        NumberAxis rangeAxisVital = new NumberAxis("Tensi");
        rangeAxisVital.setRange(60, 180);
        rangeAxisVital.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisVital.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));

        LineAndShapeRenderer rendererVital = new LineAndShapeRenderer(true, true) {
            @Override
            public Shape getItemShape(int row, int column) {
                if (row == 0) {
                    return createDotShape();     // Nadi - dot
                }
                if (row == 1) {
                    return createUpArrow();      // Sistolik - panah atas
                }
                if (row == 2) {
                    return createDownArrow();    // Diastolik - panah bawah
                }
                return super.getItemShape(row, column);
            }

            @Override
            public void drawItem(java.awt.Graphics2D g2,
                    org.jfree.chart.renderer.category.CategoryItemRendererState state,
                    java.awt.geom.Rectangle2D dataArea,
                    CategoryPlot plot,
                    org.jfree.chart.axis.CategoryAxis domainAxis,
                    org.jfree.chart.axis.ValueAxis rangeAxis,
                    CategoryDataset dataset,
                    int row,
                    int column,
                    int pass) {

                super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);

                // Gambar garis vertikal dari sistolik ke diastolik
                if (row == 1 && column < dataset.getColumnCount()) {
                    Number sistolikValue = dataset.getValue(1, column);
                    Number diastolikValue = dataset.getValue(2, column);

                    if (sistolikValue != null && diastolikValue != null) {
                        double categoryX = domainAxis.getCategoryMiddle(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
                        double sistolikY = rangeAxis.valueToJava2D(sistolikValue.doubleValue(), dataArea, plot.getRangeAxisEdge());
                        double diastolikY = rangeAxis.valueToJava2D(diastolikValue.doubleValue(), dataArea, plot.getRangeAxisEdge());

                        g2.setStroke(new BasicStroke(2.0f));
                        g2.setPaint(Color.RED);
                        g2.drawLine((int) categoryX, (int) sistolikY, (int) categoryX, (int) diastolikY);
                    }
                }
            }
        };

        rendererVital.setSeriesPaint(0, Color.BLUE);  // Nadi
        rendererVital.setSeriesPaint(1, Color.RED);   // Sistolik
        rendererVital.setSeriesPaint(2, Color.RED);   // Diastolik
        rendererVital.setSeriesLinesVisible(0, true); // Nadi dengan garis
        rendererVital.setSeriesLinesVisible(1, false); // Sistolik tanpa garis horizontal
        rendererVital.setSeriesLinesVisible(2, false); // Diastolik tanpa garis horizontal
        CategoryPlot subplotVital = new CategoryPlot(datasetVital, null, rangeAxisVital, rendererVital);
        subplotVital.setBackgroundPaint(Color.WHITE);
        subplotVital.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 8. Suhu
        CategoryDataset datasetSuhu = createDatasetSuhu(query1);
        NumberAxis rangeAxisSuhu = new NumberAxis("Suhu");
        rangeAxisSuhu.setRange(35, 42);
        rangeAxisSuhu.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisSuhu.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        LineAndShapeRenderer rendererSuhu = new LineAndShapeRenderer(true, true);
        rendererSuhu.setSeriesPaint(0, Color.GREEN);
        CategoryPlot subplotSuhu = new CategoryPlot(datasetSuhu, null, rangeAxisSuhu, rendererSuhu);
        subplotSuhu.setBackgroundPaint(Color.WHITE);
        subplotSuhu.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // 9. Urin
        CategoryDataset datasetUrin = createDatasetUrin(query1);
        NumberAxis rangeAxisUrin = new NumberAxis("Urin");
        rangeAxisUrin.setRange(0, 10);
        rangeAxisUrin.setLabelFont(new Font("Arial", Font.ITALIC, 8));
        rangeAxisUrin.setTickLabelFont(new Font("Arial", Font.PLAIN, 7));
        LineAndShapeRenderer rendererUrin = new LineAndShapeRenderer(false, true);
        rendererUrin.setSeriesPaint(0, Color.ORANGE);  // Protein
        rendererUrin.setSeriesPaint(1, Color.PINK);    // Aseton
        rendererUrin.setSeriesPaint(2, Color.GRAY);    // Volume
        CategoryPlot subplotUrin = new CategoryPlot(datasetUrin, null, rangeAxisUrin, rendererUrin);
        subplotUrin.setBackgroundPaint(Color.WHITE);
        subplotUrin.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Gabungkan semua subplot dengan domain axis yang rapi
        CategoryAxis domainAxis = new CategoryAxis("Waktu");
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setLabelFont(new Font("Arial", Font.BOLD, 10));
        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 8));
        domainAxis.setMaximumCategoryLabelLines(2);
        domainAxis.setCategoryMargin(0.02);

        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Proporsi sesuai form PDF RSUD dengan spacing yang baik
        plot.add(subplotDJJ, 4);           // DJJ - terbesar
        plot.add(subplotAirKetuban, 1);    // Air ketuban - kecil
        plot.add(subplotPembukaan, 3);     // Pembukaan - besar (penting)
        plot.add(subplotPenurunan, 2);     // Penurunan - sedang 
        plot.add(subplotKontraksi, 2);     // Kontraksi - sedang
        plot.add(subplotObat, 1);          // Oksitosin - kecil
        plot.add(subplotVital, 3);         // Vital signs - besar
        plot.add(subplotSuhu, 1);          // Suhu - kecil
        plot.add(subplotUrin, 2);          // Urin - sedang

        // Buat chart TANPA LEGEND untuk menghindari tumpang tindih
        return new JFreeChart("PARTOGRAF",
                new Font("Arial", Font.BOLD, 14), plot, false); // false = no legend
    }

    public static JPanel createDemoPanel(String query1, String kolom1, String kolom2, String kolom3, String kolom4,
            String query2, String kolomPembukaan, String kolomPenurunan) {
        JFreeChart chart = createChart(query1, kolom1, kolom2, kolom3, kolom4, query2, kolomPembukaan, kolomPenurunan);
        ChartPanel chartPanel = new ChartPanel(chart);

        // Ukuran optimal untuk chart tanpa legend
        chartPanel.setPreferredSize(new Dimension(1600, 1800));
        chartPanel.setMinimumSize(new Dimension(1200, 1400));

        // Set margin yang cukup untuk label y-axis
        chart.setPadding(new org.jfree.ui.RectangleInsets(20, 80, 20, 20));

        return chartPanel;
    }

    // Helper methods untuk parsing data sesuai database
    private static double parseKetubanValue(String ketuban) {
        // Berdasarkan data: 'J', 'U' mungkin kode untuk jenis ketuban
        if (ketuban.equalsIgnoreCase("U")) {
            return 1; // Utuh
        }
        if (ketuban.equalsIgnoreCase("J")) {
            return 2; // Jernih
        }
        if (ketuban.equalsIgnoreCase("M")) {
            return 3; // Mekonium
        }
        if (ketuban.equalsIgnoreCase("B")) {
            return 4; // Berdarah
        }
        if (ketuban.equalsIgnoreCase("A")) {
            return 5; // Tidak ada
        }
        return 0;
    }

    private static double parsePenyusupanValue(String penyusupan) {
        if (penyusupan.equals("0")) {
            return 0;
        }
        if (penyusupan.equals("1")) {
            return 1;
        }
        if (penyusupan.equals("2")) {
            return 2;
        }
        if (penyusupan.equals("3")) {
            return 3;
        }
        try {
            return Double.parseDouble(penyusupan);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double parseUrinValue(String urinStr) {
        if (urinStr.equals("0") || urinStr.equals("-")) {
            return 0;
        }
        if (urinStr.equals("1") || urinStr.equals("+")) {
            return 1;
        }
        if (urinStr.equals("2") || urinStr.equals("++")) {
            return 2;
        }
        if (urinStr.equals("3") || urinStr.equals("+++")) {
            return 3;
        }
        if (urinStr.equals("4") || urinStr.equals("++++")) {
            return 4;
        }
        return 0;
    }

    // Helper method untuk parsing station value
    private static double parseStationValue(String stationStr) {
        if (stationStr == null || stationStr.trim().isEmpty()) {
            return 0;
        }

        stationStr = stationStr.trim();

        // Handle format "1/5", "2/5", etc dari database
        if (stationStr.contains("/")) {
            String[] parts = stationStr.split("/");
            if (parts.length == 2) {
                try {
                    int numerator = Integer.parseInt(parts[0].trim());
                    // Konversi dari skala 1/5-5/5 ke station -2 sampai +2
                    return numerator - 3; // 1/5 = -2, 2/5 = -1, 3/5 = 0, 4/5 = +1, 5/5 = +2
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }

        // Handle format "+1", "+2", etc
        if (stationStr.startsWith("+")) {
            try {
                return Double.parseDouble(stationStr.substring(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        // Handle format "-1", "-2", etc  
        if (stationStr.startsWith("-")) {
            try {
                return Double.parseDouble(stationStr);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        // Handle numeric values
        try {
            double value = Double.parseDouble(stationStr);
            // Jika nilai 1-5, konversi ke station
            if (value >= 1 && value <= 5) {
                return value - 3;
            }
            return value;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Shape methods sesuai standar WHO
    private static Shape createXShape() {
        GeneralPath path = new GeneralPath();
        path.moveTo(-4.0, -4.0);
        path.lineTo(4.0, 4.0);
        path.moveTo(-4.0, 4.0);
        path.lineTo(4.0, -4.0);
        return path;
    }

    private static Shape createCircleShape() {
        return new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0);
    }

    private static Shape createSquareShape() {
        return new Rectangle2D.Double(-3.0, -3.0, 6.0, 6.0);
    }

    private static Shape createDotShape() {
        return new java.awt.geom.Ellipse2D.Double(-2.0, -2.0, 4.0, 4.0);
    }

    private static Shape createUpArrow() {
        GeneralPath arrow = new GeneralPath();
        arrow.moveTo(0.0, -4.0);
        arrow.lineTo(-3.0, 2.0);
        arrow.lineTo(3.0, 2.0);
        arrow.closePath();
        return arrow;
    }

    private static Shape createDownArrow() {
        GeneralPath arrow = new GeneralPath();
        arrow.moveTo(0.0, 4.0);
        arrow.lineTo(-3.0, -2.0);
        arrow.lineTo(3.0, -2.0);
        arrow.closePath();
        return arrow;
    }

    private static Shape createSmallCircle() {
        return new java.awt.geom.Ellipse2D.Double(-2.0, -2.0, 4.0, 4.0);
    }

    private static Shape createSmallSquare() {
        return new Rectangle2D.Double(-2.0, -2.0, 4.0, 4.0);
    }

    // Shapes untuk kontraksi dengan pola arsir WHO
    private static Shape createDottedSquare() {
        return new Rectangle2D.Double(-4.0, -4.0, 8.0, 8.0);
    }

    private static Shape createHatchedSquare() {
        return new Rectangle2D.Double(-4.0, -4.0, 8.0, 8.0);
    }

    private static Shape createSolidSquare() {
        return new Rectangle2D.Double(-4.0, -4.0, 8.0, 8.0);
    }
}