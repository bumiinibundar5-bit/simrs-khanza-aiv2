package bridging;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import fungsi.koneksiDB;
import fungsi.validasi;
import widget.InternalFrame;
import widget.panelisi;
import widget.Button;
import widget.TextBox;
import javax.swing.JProgressBar; // ✅ Tambahkan ini
import javax.swing.JOptionPane; // <-- Tambahkan ini
import javax.swing.BorderFactory;
import widget.Label;

public final class BridgingIDRG extends InternalFrame {

    private final validasi Valid = new validasi();
    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;

    private final Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;

    private panelisi panelBawah, panelTengah;
    private Button BtnCari, BtnKeluar;
    private TextBox NoKartu, KdDPJP;
    private Label lblNoKartu, lblDokter;
    private JProgressBar progressBar; // 🔹 Tambahan progress bar

    public BridgingIDRG() {
        super();
        initComponents();
        initWebView();

        setSize(1024, 720);
        setVisible(true);

        // === LOGIN OTOMATIS ===
        autoLogin();
    }

    private void initComponents() {
        setLayout(new BorderLayout(1, 1));

        // --- Panel Tengah (WebView) ---
        panelTengah = new panelisi();
        panelTengah.setLayout(new BorderLayout());
        panelTengah.add(jfxPanel, BorderLayout.CENTER);
        add(panelTengah, BorderLayout.CENTER);

        // --- Panel Bawah (Tombol dan input) ---
        panelBawah = new panelisi();
        panelBawah.setPreferredSize(new Dimension(100, 45));
        panelBawah.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        //lblNoKartu = new Label();
        //lblNoKartu.setText("No. Rawat Pasien:");
        //lblNoKartu.setPreferredSize(new Dimension(100, 23));
        //panelBawah.add(lblNoKartu);

        NoKartu = new TextBox();
        NoKartu.setPreferredSize(new Dimension(130, 23));
        panelBawah.add(NoKartu);

        //lblDokter = new Label();
        //lblDokter.setText("NIK Coder:");
        //lblDokter.setPreferredSize(new Dimension(80, 23));
        //panelBawah.add(lblDokter);

        KdDPJP = new TextBox();
        KdDPJP.setPreferredSize(new Dimension(130, 23));
        panelBawah.add(KdDPJP);

        BtnCari = new Button();
        BtnCari.setText("Refresh");
        BtnCari.addActionListener((ActionEvent evt) -> BtnCariActionPerformed(evt));
        panelBawah.add(BtnCari);

        //BtnKeluar = new Button();
        //BtnKeluar.setText("Tutup");
        //BtnKeluar.addActionListener(evt -> setVisible(false));
        //panelBawah.add(BtnKeluar);
        
         // 🔹 Tambahkan Progress Bar di bawah tombol
        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(200, 18));
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        panelBawah.add(progressBar);
        
        // === Progress bar sebagai status bar permanen ===
        progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(250, 18));
        progressBar.setStringPainted(true);
        progressBar.setForeground(new java.awt.Color(46, 204, 113)); // hijau lembut
        progressBar.setBackground(java.awt.Color.WHITE);
        progressBar.setBorder(BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
        progressBar.setValue(0);
        progressBar.setString("Menunggu halaman dimuat...");
        panelBawah.add(progressBar);

        add(panelBawah, BorderLayout.PAGE_END);
    }

    private void initWebView() {
    Platform.runLater(() -> {
        WebView webView = new WebView();
        engine = webView.getEngine();
        engine.setJavaScriptEnabled(true);

        // === Progress bar selalu tampil sebagai status bar ===
        SwingUtilities.invokeLater(() -> {
            progressBar.setVisible(true);
            progressBar.setString("Menunggu halaman dimuat...");
            progressBar.setIndeterminate(true); // tampilan awal
        });

        // === Listener untuk progress numeric ===
        engine.getLoadWorker().progressProperty().addListener((obs, oldProgress, newProgress) -> {
            SwingUtilities.invokeLater(() -> {
                if (newProgress.doubleValue() < 1.0 && newProgress.doubleValue() > 0.0) {
                    progressBar.setIndeterminate(false);
                    int percent = (int) (newProgress.doubleValue() * 100);
                    progressBar.setValue(percent);
                    progressBar.setString("Memuat halaman... " + percent + "%");
                }
            });
        });

        // === Listener untuk status loading halaman ===
        engine.getLoadWorker().stateProperty().addListener(
            (ChangeListener<State>) (ObservableValue<? extends State> ov, State oldState, State newState) -> {
                //System.out.println("State berubah: " + newState);

                SwingUtilities.invokeLater(() -> {
                    switch (newState) {
                        case SCHEDULED:
                        case RUNNING:
                            progressBar.setIndeterminate(true);
                            progressBar.setString("🔄 Sedang memuat halaman...");
                            break;

                        case SUCCEEDED:
                            progressBar.setIndeterminate(false);
                            progressBar.setValue(100);
                            progressBar.setString("✅ Halaman berhasil dimuat");
                            break;

                        case FAILED:
                            progressBar.setIndeterminate(false);
                            progressBar.setValue(0);
                            progressBar.setString("❌ Gagal memuat halaman");
                            JOptionPane.showMessageDialog(
                                null,
                                "❌ Gagal memuat halaman, periksa koneksi.",
                                "Kesalahan",
                                JOptionPane.ERROR_MESSAGE
                            );
                            break;
                    }
                });

                // === Setelah load sukses, cek notifikasi SweetAlert ===
                if (newState == State.SUCCEEDED) {
                    try {
                        String result = (String) engine.executeScript(
                            "document.body.getAttribute('data-swal-message')"
                        );

                        if (result != null && !result.isEmpty()) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                null,
                                "Kirim Klaim berhasil: " + result,
                                "Notifikasi Bridging IDRG INACBG",
                                JOptionPane.INFORMATION_MESSAGE
                            ));
                        }
                    } catch (Exception e) {
                        System.err.println("⚠️ Gagal membaca notifikasi SweetAlert: " + e.getMessage());
                    }
                }
            }
        );

        jfxPanel.setScene(new Scene(webView));
        System.out.println("Scene WebView sudah diinisialisasi ke JFXPanel.");
    });
}



    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        if (NoKartu.getText().trim().isEmpty()) {
            Valid.textKosong(NoKartu, "No. Rawat");
            return;
        }
        if (KdDPJP.getText().trim().isEmpty()) {
            Valid.textKosong(KdDPJP, "Kode Dokter");
            return;
        }

        String url = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" +
                     koneksiDB.HYBRIDWEB() +
                     "/inacbg/index.php?act=DetailKirim&corona=BukanCorona" +
                     "&norawat=" + NoKartu.getText() +
                     "&codernik=" + KdDPJP.getText() +
                     "&carabayar=BPJ";

        System.out.println("[Klaim IDRG] Memuat URL: " + url);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Platform.runLater(() -> engine.load(url));
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void autoLogin() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        String loginURL = "http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" +
                          koneksiDB.HYBRIDWEB() +
                          "/inacbg/login.php?act=login" +
                          "&usere=" + koneksiDB.USERHYBRIDWEB() +
                          "&passwordte=" + koneksiDB.PASHYBRIDWEB() +
                          "&page=KlaimBaruManual&codernik=0";

        System.out.println("[AutoLogin] Memuat URL Login: " + loginURL);

        Platform.runLater(() -> {
            try {
                if (engine == null) {
                    System.out.println("Engine belum siap, membuat scene baru...");
                    initWebView();
                    Thread.sleep(500);
                }
                engine.load(loginURL);
            } catch (Exception e) {
                System.err.println("Gagal auto-login: " + e.getMessage());
            }
        });

        this.setCursor(Cursor.getDefaultCursor());
    }

    public void setPasien(String noRawat, String kodeDokter) {
        System.out.println("[setPasien] NoRawat: " + noRawat + ", Dokter: " + kodeDokter);
        NoKartu.setText(noRawat);
        KdDPJP.setText(kodeDokter);

        SwingUtilities.invokeLater(() -> BtnCari.doClick());
    }
}
