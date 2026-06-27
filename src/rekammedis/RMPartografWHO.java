/*
 * RMPartografWHO - Complete Implementation
 * Layout Structure: Data Pasien (Top) → Tab Container → Form Input → ChkInput → Table Data → Control Buttons
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.util.Base64;

public final class RMPartografWHO extends javax.swing.JDialog {

    // ========== CORE SYSTEM VARIABLES ==========
    private DefaultTableModel tabModeIdentitas, tabModeKala12, tabModeKala3, tabModeNeonatal;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private int i = 0;
    private String TANGGALMUNDUR = "yes";
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);

    // ========== GUI COMPONENTS - DATA PASIEN SECTION ==========
    private widget.PanelBiasa FormInput3;
    private widget.Label jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9;
    private widget.TextBox TNoRw, TNoRM, TPasien, TglLahir, Umur;
    private widget.Tanggal TglPartograf;
    private widget.ComboBox cmbJam, cmbMnt, cmbDtk;

    // ========== TAB CONTAINER ==========
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame2, internalFrame3, internalFrame4, internalFrame5;

    // ========== TAB IDENTITAS ==========
    private widget.ScrollPane Scroll1;
    private widget.Table tbIdentitas;
    private javax.swing.JPanel PanelInput1;
    private widget.CekBox ChkInput1;
    private widget.ScrollPane scrollPane1;
    private widget.panelisi panelGlass1;

    // ========== FORM COMPONENTS - PETUGAS ==========
    private widget.TextBox NIPPetugas, TPetugas;
    private widget.Button BtnSeekPetugas;

    // ========== IDENTITAS COMPONENTS ==========
    private widget.TextBox BeratBadan, TinggiBadan, Paritas, Gravida, Abortus;
    private widget.ComboBox RiwayatObstetri, RisikoTinggi;
    private widget.Tanggal MulesSejak, PecahKetubanSejak;
    private widget.TextArea CatatanIdentitas;

    // ========== TAB KALA I & II ==========
    private widget.ScrollPane Scroll2;
    private widget.Table tbKala12;
    private javax.swing.JPanel PanelInput2;
    private widget.CekBox ChkInput2;
    private widget.ScrollPane scrollPane2;
    private widget.panelisi panelGlass2;

    // ========== KALA I & II COMPONENTS ==========
    private widget.TextBox DJJ, PembukaanServiks, KontraksiFreq, KontraksiDurasi;
    private widget.ComboBox AirKetuban, Moulage, PenurunanKepala;
    private widget.TextBox TD, Nadi, Suhu, ProteinUrin, VolumeUrin, Oksitosin;
    private widget.TextArea CatatanKala12;

    // ========== TAB KALA III ==========
    private widget.ScrollPane Scroll3;
    private widget.Table tbKala3;
    private javax.swing.JPanel PanelInput3;
    private widget.CekBox ChkInput3;
    private widget.ScrollPane scrollPane3;
    private widget.panelisi panelGlass3;

    // ========== KALA III COMPONENTS ==========
    private widget.TextBox WaktuKala3, PerdarahanKala3, KondisiPlasenta;
    private widget.TextArea CatatanKala3;

    // ========== TAB NEONATAL ==========
    private widget.ScrollPane Scroll4;
    private widget.Table tbNeonatal;
    private javax.swing.JPanel PanelInput4;
    private widget.CekBox ChkInput4;
    private widget.ScrollPane scrollPane4;
    private widget.panelisi panelGlass4;

    // ========== NEONATAL COMPONENTS ==========
    private widget.TextBox BeratBayi, PanjangBayi, LingkarKepala, ApgarScore1, ApgarScore5;
    private widget.ComboBox JenisKelamin, CaraPersalinan, KondisiBayi;
    private widget.TextArea CatatanNeonatal;

    // ========== CONTROL BUTTONS ==========
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8, panelGlass10;
    private widget.Button BtnSimpan, BtnBatal, BtnHapus, BtnEdit, BtnPrint, BtnAll, BtnKeluar;
    private widget.Label LCount;
    private widget.Tanggal DTPCari1, DTPCari2;
    private widget.TextBox TCari;
    private widget.Button BtnCari;

    private widget.ComboBox Jam, Menit, Detik;  // Sesuai method jam()
    private widget.CekBox ChkKejadian;  // Toggle otomatis
    private javax.swing.Timer timer;  // Timer management

    private widget.TextBox BeratPlasenta;
    private widget.ComboBox JenisPerdarahan, KondisiUterus, TindakanKala3;

// Neonatal additional components  
    private widget.TextBox LingkarDada;
    private widget.ComboBox Resusitasi, StatusIMD;

    private widget.TextBox Obat;      // TextBox untuk obat lainnya
    private widget.TextBox Cairan;    // TextBox untuk cairan IV
    private widget.Label jLabelObat, jLabel42;  // Label untuk obat
    private widget.Label jLabelCairan;

    private widget.Button BtnGrafik;
    private widget.ComboBox GarisWaspada, Episiotomi, Pendamping, GawatJanin, DistosiaBahu;
    private widget.ComboBox Oksitosin100, OksitosinLain, PenegangTali, MasaseFundus;
    private widget.ComboBox PlasentaLengkap, PlasentaTidakLahir;
    private widget.TextBox MasalahKala1, PenatalaksanaanKala1, HasilKala1;
    private widget.TextBox TindakanGawatJanin, TindakanDistosia;
    private widget.TextBox MasalahKala2, PenatalaksanaanKala2, HasilKala2;
    private widget.TextBox LamaKala3, WaktuOksitosin, AlasanTidakOksitosin;

    private widget.TextBox WaktuOksitosin100, AlasanTidakOksitosin100, AlasanTidakOksitosinLain;

    private widget.TextBox AlasanTidakPenegang, AlasanTidakMasase;

    private widget.TextBox TindakanPlasentaTidakLengkap, TindakanPlasentaTidakLahir;
    private widget.ComboBox Laserasi, AtoniaUteri;
    private widget.TextBox LokasiLaserasi, DerajatLaserasi, TindakanAtonia;
    private widget.TextBox JumlahPerdarahan;
    private widget.TextBox MasalahKala3, PenatalaksanaanKala3, HasilKala3;

// === BAYI BARU LAHIR VARIABLES ===
    private widget.TextBox BeratBadanBayi, PanjangBadanBayi;
    private widget.ComboBox JenisKelaminBayi, PenilaianBayi, BayiLahir;
    private widget.TextBox TindakanBayi, CacatBawaan, HipotermiTindakan;
    private widget.ComboBox PemberianASI;
    private widget.TextBox WaktuPemberianASI, AlasanTidakASI;
    private widget.TextBox MasalahLainBayi, HasilnyaBayi;
    private widget.Table tbDataCatatanPersalinan;
    private DefaultTableModel tabModeDataCatatanPersalinan;

    // Tabel dan Model untuk Observasi Kala IV
    private widget.Table tbObservasiKalaIV;
    private DefaultTableModel tabModeObservasiKalaIV;

// ScrollPane untuk tabel Kala IV
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane scrollPane5;

// Internal Frames
    private widget.InternalFrame internalFrame6;

// Input Components untuk Observasi Kala IV
    private widget.ComboBox JamKe;
    private widget.TextBox TekananDarahKalaIV;
    private widget.TextBox NadiKalaIV;
    private widget.TextBox SuhuKalaIV;
    private widget.TextBox TFU;
    private widget.TextBox KontraksiUterusKalaIV;
    private widget.TextBox KandungKemih;
    private widget.TextBox PerdarahanKalaIV;

// Panel untuk Kala IV
    private widget.panelisi panelGlass5;
    // TAMBAHKAN VARIABEL UNTUK TAB RUJUKAN
    private widget.InternalFrame internalFrameRujukan;
    private widget.ScrollPane scrollPaneRujukan;
    private widget.panelisi panelGlassRujukan;

// VARIABEL KOMPONEN RUJUKAN
    private widget.ComboBox StatusRujukan;
    private widget.Tanggal TanggalRujukan;
    private widget.ComboBox JamRujukan, MenitRujukan;
    private widget.ComboBox AlasanRujukan;
    private widget.TextBox AlasanLainnya;
    private widget.ComboBox TempatTujuanRujukan;
    private widget.TextBox TempatRujukanLain;
    private widget.ComboBox TransportasiRujukan;
    private widget.TextBox BiayaTransportasi;
    private widget.ComboBox PendampingRujukan;
    private widget.TextBox NamaPendamping;
    private widget.TextBox NoTelpRujukan;
    private widget.ComboBox KondisiSaatRujukan;
    private widget.TextArea CatatanRujukan;
    private widget.TextBox PetugasRujukan;
    private widget.TextBox NIPPetugasRujukan;

// TABEL RUJUKAN
    private widget.Table tbRujukan;
    private DefaultTableModel tabModeRujukan;
    private widget.ScrollPane ScrollRujukan;

    private DefaultTableModel tabModeMasalahKalaIV;
    private widget.ScrollPane ScrollMasalahKalaIV;

    private widget.ComboBox JamKeObsKalaIV;
    private widget.TextBox TekananDarahObsKalaIV;
    private widget.TextBox NadiObsKalaIV;
    private widget.TextBox SuhuObsKalaIV;
    private widget.TextBox TFUObsKalaIV;
    private widget.TextBox KontraksiUterusObsKalaIV;
    private widget.TextBox KandungKemihObsKalaIV;
    private widget.TextBox PerdarahanObsKalaIV;

    // ========== VARIABEL KOMPONEN MASALAH KALA IV (tambahkan di class variables) ==========
    private widget.ComboBox CatatanRujukanKala;
    private widget.ComboBox PendampingSaatMerujuk;
    private widget.TextBox AlasanMerujuk;
    private widget.TextBox TempatMerujuk;
    private widget.TextBox MasalahKalaIV;
    private widget.TextBox PenatalaksanaanKalaIV;
    private widget.TextBox HasilnyaKalaIV;

    private widget.ComboBox KalaRujukanEvaluasi;
    private widget.ComboBox PendampingEvaluasi;

// TextBox components - nama baru
    private widget.TextBox AlasanEvaluasi;
    private widget.TextBox TempatEvaluasi;
    private widget.TextBox MasalahEvaluasi;
    private widget.TextBox TindakanEvaluasi;
    private widget.TextBox HasilEvaluasi;

// Table dan Model - nama baru
    private widget.Table tbEvaluasiPostpartum;
    private DefaultTableModel tabModeEvaluasiPostpartum;
    private widget.ScrollPane ScrollEvaluasiPostpartum;

// Panel components - nama baru
    private widget.panelisi panelGlassEvaluasi;
    private widget.ScrollPane scrollPaneEvaluasi;
    private widget.InternalFrame internalFrameEvaluasi;
    private javax.swing.JPanel PanelInputEvaluasi;

    // ========== CONSTRUCTOR ==========
    public RMPartografWHO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(1200, 800);

        setupTableProperties();
        setupInputValidation();
        setupEventHandlers();
        setupComboBoxWaktu();
        initKalaIV();

        // ========== SAFE CHECKBOX INITIALIZATION ==========
        // Check if components exist before setting selected state
        if (ChkInput1 != null) {
            ChkInput1.setSelected(true);
        }
        if (ChkInput2 != null) {
            ChkInput2.setSelected(true);
        }
        // Remove ChkInput4 reference
        isForm();

        tampil();

        // SET JAM SEKARANG SAAT FORM DIBUKA
        setJamSekarangStatis();

        // SET TANGGAL SEKARANG
        TglPartograf.setDate(new Date());

        // AKTIFKAN CHECKBOX OTOMATIS
        ChkKejadian.setSelected(false);

        initTimer(); // Start time auto-update

        try {
            TANGGALMUNDUR = koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR = "yes";
        }
    }

    // Method untuk set jam sekarang tanpa timer otomatis
    private void setJamSekarangStatis() {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        // Format dengan leading zero
        String jamSekarang = String.format("%02d", cal.get(java.util.Calendar.HOUR_OF_DAY));
        String menitSekarang = String.format("%02d", cal.get(java.util.Calendar.MINUTE));
        String detikSekarang = String.format("%02d", cal.get(java.util.Calendar.SECOND));

        // Set untuk ComboBox di header (Data Pasien) - SEKALI SAJA
        if (Jam != null) {
            Jam.setSelectedItem(jamSekarang);
        }
        if (Menit != null) {
            Menit.setSelectedItem(menitSekarang);
        }
        if (Detik != null) {
            Detik.setSelectedItem(detikSekarang);
        }

        // Set untuk ComboBox waktu observasi (jika ada) - SEKALI SAJA
        if (cmbJam != null) {
            cmbJam.setSelectedItem(jamSekarang);
        }
        if (cmbMnt != null) {
            cmbMnt.setSelectedItem(menitSekarang);
        }
        if (cmbDtk != null) {
            cmbDtk.setSelectedItem(detikSekarang);
        }

        System.out.println("Jam diset ke waktu sekarang (statis): " + jamSekarang + ":" + menitSekarang + ":" + detikSekarang);
    }

    // Method untuk inisialisasi timer tanpa memulainya
    private void initTimer() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hanya update jika checkbox otomatis aktif
                if (ChkKejadian.isSelected()) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();

                    String jamSekarang = String.format("%02d", cal.get(java.util.Calendar.HOUR_OF_DAY));
                    String menitSekarang = String.format("%02d", cal.get(java.util.Calendar.MINUTE));
                    String detikSekarang = String.format("%02d", cal.get(java.util.Calendar.SECOND));

                    // Update ComboBox di header
                    if (Jam != null) {
                        Jam.setSelectedItem(jamSekarang);
                    }
                    if (Menit != null) {
                        Menit.setSelectedItem(menitSekarang);
                    }
                    if (Detik != null) {
                        Detik.setSelectedItem(detikSekarang);
                    }

                    // Update ComboBox observasi jika ada
                    if (cmbJam != null) {
                        cmbJam.setSelectedItem(jamSekarang);
                    }
                    if (cmbMnt != null) {
                        cmbMnt.setSelectedItem(menitSekarang);
                    }
                    if (cmbDtk != null) {
                        cmbDtk.setSelectedItem(detikSekarang);
                    }
                }
            }
        };

        // Timer dibuat tapi TIDAK distart otomatis
        timer = new Timer(1000, taskPerformer);
        // timer.start(); // DIHAPUS - tidak start otomatis
    }

    private void addComboWaktuToLayout() {
        // Label waktu
        widget.Label lblWaktu = new widget.Label();
        lblWaktu.setText("Waktu Observasi:");
        lblWaktu.setName("lblWaktu");
        lblWaktu.setFont(new Font("Tahoma", Font.BOLD, 11));
        panelGlass2.add(lblWaktu);
        lblWaktu.setBounds(0, 15, 100, 23);

        // Jam
        widget.Label lblJam = new widget.Label();
        lblJam.setText("Jam :");
        lblJam.setName("lblJam");
        panelGlass2.add(lblJam);
        lblJam.setBounds(110, 15, 40, 23);

        panelGlass2.add(cmbJam);
        cmbJam.setBounds(155, 15, 50, 23);

        // Menit
        widget.Label lblTitikDua1 = new widget.Label();
        lblTitikDua1.setText(":");
        lblTitikDua1.setName("lblTitikDua1");
        panelGlass2.add(lblTitikDua1);
        lblTitikDua1.setBounds(210, 15, 10, 23);

        panelGlass2.add(cmbMnt);
        cmbMnt.setBounds(220, 15, 50, 23);

        // Detik
        widget.Label lblTitikDua2 = new widget.Label();
        lblTitikDua2.setText(":");
        lblTitikDua2.setName("lblTitikDua2");
        panelGlass2.add(lblTitikDua2);
        lblTitikDua2.setBounds(275, 15, 10, 23);

        panelGlass2.add(cmbDtk);
        cmbDtk.setBounds(285, 15, 50, 23);

        // Label format
        widget.Label lblFormat = new widget.Label();
        lblFormat.setText("(HH:mm:ss)");
        lblFormat.setName("lblFormat");
        lblFormat.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblFormat);
        lblFormat.setBounds(340, 15, 70, 23);
    }

    // 14. METHOD UNTUK DEBUG - Cek apakah combo box sudah diinisialisasi
    private void debugComboBoxStatus() {
        System.out.println("=== DEBUG COMBO BOX STATUS ===");
        System.out.println("cmbJam: " + (cmbJam != null ? "OK" : "NULL"));
        System.out.println("cmbMnt: " + (cmbMnt != null ? "OK" : "NULL"));
        System.out.println("cmbDtk: " + (cmbDtk != null ? "OK" : "NULL"));

        if (cmbJam != null) {
            System.out.println("cmbJam selected: " + cmbJam.getSelectedItem());
        }
    }

    private void simpanWithExceptionHandler() {
        try {
            simpanKala12();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in simpan: " + e.getMessage());
            e.printStackTrace();

            // Debug combo box status
            debugComboBoxStatus();

            // Coba inisialisasi ulang
            if (cmbJam == null || cmbMnt == null || cmbDtk == null) {
                setupComboBoxWaktu();
                JOptionPane.showMessageDialog(this,
                        "Error: ComboBox waktu belum diinisialisasi. Silakan coba lagi.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error saat menyimpan data: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("General Exception in simpan: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void setupComboBoxWaktu() {
        // ComboBox Jam (0-23)
        cmbJam = new widget.ComboBox();
        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(getJamArray()));
        cmbJam.setName("cmbJam");
        cmbJam.setPreferredSize(new java.awt.Dimension(60, 23));

        // ComboBox Menit (0-59)
        cmbMnt = new widget.ComboBox();
        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(getMenitArray()));
        cmbMnt.setName("cmbMnt");
        cmbMnt.setPreferredSize(new java.awt.Dimension(60, 23));

        // ComboBox Detik (0-59)
        cmbDtk = new widget.ComboBox();
        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(getDetikArray()));
        cmbDtk.setName("cmbDtk");
        cmbDtk.setPreferredSize(new java.awt.Dimension(60, 23));

        // Set jam sekarang
        setJamSekarang();
    }

    // 2. HELPER METHOD - Generate array jam
    private String[] getJamArray() {
        String[] jam = new String[24];
        for (int i = 0; i < 24; i++) {
            jam[i] = String.format("%02d", i);
        }
        return jam;
    }

// 3. HELPER METHOD - Generate array menit
    private String[] getMenitArray() {
        String[] menit = new String[60];
        for (int i = 0; i < 60; i++) {
            menit[i] = String.format("%02d", i);
        }
        return menit;
    }

// 4. HELPER METHOD - Generate array detik
    private String[] getDetikArray() {
        String[] detik = new String[60];
        for (int i = 0; i < 60; i++) {
            detik[i] = String.format("%02d", i);
        }
        return detik;
    }

    private void setJamSekarang() {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        // Format dengan leading zero
        String jamSekarang = String.format("%02d", cal.get(java.util.Calendar.HOUR_OF_DAY));
        String menitSekarang = String.format("%02d", cal.get(java.util.Calendar.MINUTE));
        String detikSekarang = String.format("%02d", cal.get(java.util.Calendar.SECOND));

        // Set untuk ComboBox di header (Data Pasien)
        if (Jam != null) {
            Jam.setSelectedItem(jamSekarang);
        }
        if (Menit != null) {
            Menit.setSelectedItem(menitSekarang);
        }
        if (Detik != null) {
            Detik.setSelectedItem(detikSekarang);
        }

        // Set untuk ComboBox waktu observasi (jika ada)
        if (cmbJam != null) {
            cmbJam.setSelectedItem(jamSekarang);
        }
        if (cmbMnt != null) {
            cmbMnt.setSelectedItem(menitSekarang);
        }
        if (cmbDtk != null) {
            cmbDtk.setSelectedItem(detikSekarang);
        }

        System.out.println("Jam diset ke: " + jamSekarang + ":" + menitSekarang + ":" + detikSekarang);
    }
// 6. METHOD JAM - DIPERBAIKI dengan null check

    // ========== INITIALIZE COMPONENTS ==========
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        // Setup layout
        getContentPane().setLayout(new java.awt.BorderLayout());

        setupDataPasienSection();
        setupTabContainer();
        setupControlButtons();
        setupBtnGrafik();
        // Add to main container
        getContentPane().add(FormInput3, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(TabRawat, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        pack();

    }

    private void setupBtnGrafik() {
        BtnGrafik = new widget.Button();
        BtnGrafik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        BtnGrafik.setMnemonic('G');
        BtnGrafik.setText("Grafik Partograf WHO");
        BtnGrafik.setToolTipText("Alt+G - Tampilkan Grafik Partograf WHO");
        BtnGrafik.setName("BtnGrafik");
        BtnGrafik.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnGrafik.addActionListener(evt -> BtnGrafikActionPerformed(evt));

        // Tambahkan ke panel kontrol (sesuaikan nama panel Anda)
        panelGlass8.add(BtnGrafik);
    }

    private void setupDataPasienSection() {
        FormInput3 = new widget.PanelBiasa();
        FormInput3.setName("FormInput3");
        FormInput3.setPreferredSize(new java.awt.Dimension(1200, 110)); // Increased height
        FormInput3.setLayout(null);

        // Row 1: No.Rawat, No.RM, Nama Pasien
        jLabel3 = new widget.Label();
        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3");
        FormInput3.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw = new widget.TextBox();
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw");
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput3.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        jLabel4 = new widget.Label();
        jLabel4.setText("No.RM :");
        jLabel4.setName("jLabel4");
        FormInput3.add(jLabel4);
        jLabel4.setBounds(205, 10, 50, 23);

        TNoRM = new widget.TextBox();
        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM");
        FormInput3.add(TNoRM);
        TNoRM.setBounds(260, 10, 80, 23);

        jLabel5 = new widget.Label();
        jLabel5.setText("Nama Pasien :");
        jLabel5.setName("jLabel5");
        FormInput3.add(jLabel5);
        jLabel5.setBounds(345, 10, 85, 23);

        TPasien = new widget.TextBox();
        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien");
        FormInput3.add(TPasien);
        TPasien.setBounds(435, 10, 300, 23);

        // Row 2: Tanggal Lahir, Umur
        jLabel6 = new widget.Label();
        jLabel6.setText("Tanggal Lahir :");
        jLabel6.setName("jLabel6");
        FormInput3.add(jLabel6);
        jLabel6.setBounds(0, 40, 90, 23);

        TglLahir = new widget.TextBox();
        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir");
        FormInput3.add(TglLahir);
        TglLahir.setBounds(95, 40, 100, 23);

        jLabel7 = new widget.Label();
        jLabel7.setText("Umur :");
        jLabel7.setName("jLabel7");
        FormInput3.add(jLabel7);
        jLabel7.setBounds(205, 40, 50, 23);

        Umur = new widget.TextBox();
        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur");
        FormInput3.add(Umur);
        Umur.setBounds(260, 40, 150, 23);

        // Row 3: Tanggal, Jam, NIP Petugas, Nama Petugas
        jLabel8 = new widget.Label();
        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8");
        FormInput3.add(jLabel8);
        jLabel8.setBounds(0, 70, 60, 23);

        TglPartograf = new widget.Tanggal();
        TglPartograf.setForeground(new java.awt.Color(50, 70, 50));
        TglPartograf.setDisplayFormat("dd-MM-yyyy");
        TglPartograf.setName("TglPartograf");
        FormInput3.add(TglPartograf);
        TglPartograf.setBounds(65, 70, 100, 23);

        jLabel9 = new widget.Label();
        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9");
        FormInput3.add(jLabel9);
        jLabel9.setBounds(175, 70, 35, 23);

        // Time components
        Jam = new widget.ComboBox();
        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
        }));
        Jam.setName("Jam");
        FormInput3.add(Jam);
        Jam.setBounds(215, 70, 45, 23);

        Menit = new widget.ComboBox();
        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        }));
        Menit.setName("Menit");
        FormInput3.add(Menit);
        Menit.setBounds(265, 70, 45, 23);

        Detik = new widget.ComboBox();
        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        }));
        Detik.setName("Detik");
        FormInput3.add(Detik);
        Detik.setBounds(315, 70, 45, 23);

        // Checkbox for auto time update
        ChkKejadian = new widget.CekBox();
        ChkKejadian.setText("Otomatis");
        ChkKejadian.setName("ChkKejadian");
        ChkKejadian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkKejadianActionPerformed(evt);
            }
        });
        FormInput3.add(ChkKejadian);
        ChkKejadian.setBounds(370, 70, 80, 23);

        // NIP Petugas - moved to same row as time
        widget.Label lblNIP = new widget.Label();
        lblNIP.setText("NIP :");
        lblNIP.setName("lblNIP");
        FormInput3.add(lblNIP);
        lblNIP.setBounds(460, 70, 35, 23);

        NIPPetugas = new widget.TextBox();
        NIPPetugas.setName("NIPPetugas");
        NIPPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPPetugasKeyPressed(evt);
            }
        });
        FormInput3.add(NIPPetugas);
        NIPPetugas.setBounds(500, 70, 100, 23);

        widget.Label lblPetugas = new widget.Label();
        lblPetugas.setText("Petugas :");
        lblPetugas.setName("lblPetugas");
        FormInput3.add(lblPetugas);
        lblPetugas.setBounds(610, 70, 60, 23);

        TPetugas = new widget.TextBox();
        TPetugas.setEditable(false);
        TPetugas.setName("TPetugas");
        FormInput3.add(TPetugas);
        TPetugas.setBounds(675, 70, 180, 23);

        BtnSeekPetugas = new widget.Button();
        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        BtnSeekPetugas.setMnemonic('1');
        BtnSeekPetugas.setToolTipText("Alt+1");
        BtnSeekPetugas.setName("BtnSeekPetugas");
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        FormInput3.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(860, 70, 28, 23);
    }

    // ========== SETUP TAB CONTAINER ==========
    private void setupTabContainer() {
        // PASTIKAN TabRawat hanya diinisialisasi SEKALI
        if (TabRawat == null) {
            TabRawat = new javax.swing.JTabbedPane();
            TabRawat.setBackground(new java.awt.Color(254, 255, 254));
            TabRawat.setForeground(new java.awt.Color(50, 50, 50));
            TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11));
            TabRawat.setName("TabRawat");
        }

        // CLEAR existing tabs sebelum menambah yang baru
        TabRawat.removeAll();

        setupTabIdentitas();                     // Tab 0: Anamnesa Awal/Identitas
        setupTabKala12();                       // Tab 1: Observasi/Kala I & II
        setupTabCatatanPersalinan();           // Tab 2: Catatan Persalinan   
        setupTabDataCatatanPersalinan();       // Tab 5: Rujukan (reusing existing functionality)
        setupTabObservasiKalaIV();             // Tab 3: Observasi Persalinan Kala IV
        setupTabEvaluasiPostpartum();
        setupTabRujukan();

    }

    // ========== SETUP TAB IDENTITAS ==========
    private void setupTabIdentitas() {
        internalFrame2 = new widget.InternalFrame();
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2");
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        // Form Input Panel
        PanelInput1 = new javax.swing.JPanel();
        PanelInput1.setName("PanelInput1");
        PanelInput1.setOpaque(false);
        PanelInput1.setPreferredSize(new java.awt.Dimension(192, 300));
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        // Form Content
        scrollPane1 = new widget.ScrollPane();
        scrollPane1.setName("scrollPane1");
        scrollPane1.setPreferredSize(new java.awt.Dimension(46, 300));

        panelGlass1 = new widget.panelisi();
        panelGlass1.setName("panelGlass1");
        panelGlass1.setPreferredSize(new java.awt.Dimension(44, 280));
        panelGlass1.setLayout(null);

        setupFormIdentitas();

        scrollPane1.setViewportView(panelGlass1);
        PanelInput1.add(scrollPane1, java.awt.BorderLayout.PAGE_START);

        // Checkbox Input
        ChkInput1 = new widget.CekBox();
        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput1.setMnemonic('I');
        ChkInput1.setText(".: Input Data Identitas");
        ChkInput1.setToolTipText("Alt+I");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1");
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        internalFrame2.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        // Table
        Scroll1 = new widget.ScrollPane();
        Scroll1.setName("Scroll1");
        Scroll1.setOpaque(true);

        tbIdentitas = new widget.Table();
        tbIdentitas.setName("tbIdentitas");
        tbIdentitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbIdentitasMouseClicked(evt);
            }
        });
        tbIdentitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbIdentitasKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbIdentitas);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Identitas", internalFrame2);
    }

    private void setupFormIdentitas() {
        // HAPUS bagian NIP dan Nama Petugas - sudah ada di header dekat jam

        // Data Identitas Ibu - langsung mulai dari sini
        widget.Label lblBerat = new widget.Label();
        lblBerat.setText("Berat Badan :");
        lblBerat.setName("lblBerat");
        panelGlass1.add(lblBerat);
        lblBerat.setBounds(0, 10, 80, 23);  // Mulai dari posisi 10, bukan 40

        BeratBadan = new widget.TextBox();
        BeratBadan.setName("BeratBadan");
        BeratBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BeratBadanKeyPressed(evt);
            }
        });
        panelGlass1.add(BeratBadan);
        BeratBadan.setBounds(85, 10, 60, 23);

        widget.Label lblKg = new widget.Label();
        lblKg.setText("kg");
        lblKg.setName("lblKg");
        panelGlass1.add(lblKg);
        lblKg.setBounds(150, 10, 20, 23);

        widget.Label lblTinggi = new widget.Label();
        lblTinggi.setText("Tinggi :");
        lblTinggi.setName("lblTinggi");
        panelGlass1.add(lblTinggi);
        lblTinggi.setBounds(175, 10, 50, 23);

        TinggiBadan = new widget.TextBox();
        TinggiBadan.setName("TinggiBadan");
        TinggiBadan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TinggiBadanKeyPressed(evt);
            }
        });
        panelGlass1.add(TinggiBadan);
        TinggiBadan.setBounds(230, 10, 60, 23);

        widget.Label lblCm = new widget.Label();
        lblCm.setText("cm");
        lblCm.setName("lblCm");
        panelGlass1.add(lblCm);
        lblCm.setBounds(295, 10, 20, 23);

        widget.Label lblGravida = new widget.Label();
        lblGravida.setText("Gravida :");
        lblGravida.setName("lblGravida");
        panelGlass1.add(lblGravida);
        lblGravida.setBounds(320, 10, 60, 23);

        Gravida = new widget.TextBox();
        Gravida.setName("Gravida");
        Gravida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GravidaKeyPressed(evt);
            }
        });
        panelGlass1.add(Gravida);
        Gravida.setBounds(385, 10, 40, 23);

        widget.Label lblParitas = new widget.Label();
        lblParitas.setText("Para :");
        lblParitas.setName("lblParitas");
        panelGlass1.add(lblParitas);
        lblParitas.setBounds(435, 10, 40, 23);

        Paritas = new widget.TextBox();
        Paritas.setName("Paritas");
        Paritas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ParitasKeyPressed(evt);
            }
        });
        panelGlass1.add(Paritas);
        Paritas.setBounds(480, 10, 40, 23);

        widget.Label lblAbortus = new widget.Label();
        lblAbortus.setText("Abortus :");
        lblAbortus.setName("lblAbortus");
        panelGlass1.add(lblAbortus);
        lblAbortus.setBounds(530, 10, 60, 23);

        Abortus = new widget.TextBox();
        Abortus.setName("Abortus");
        Abortus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbortusKeyPressed(evt);
            }
        });
        panelGlass1.add(Abortus);
        Abortus.setBounds(595, 10, 40, 23);

        // Riwayat Obstetri - posisi dinaikkan
        widget.Label lblRiwayat = new widget.Label();
        lblRiwayat.setText("Riwayat Obstetri :");
        lblRiwayat.setName("lblRiwayat");
        panelGlass1.add(lblRiwayat);
        lblRiwayat.setBounds(0, 40, 100, 23);  // Dari 70 ke 40

        RiwayatObstetri = new widget.ComboBox();
        RiwayatObstetri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Normal", "SC", "Vacuum", "Forceps", "Manual Plasenta", "Retensio Plasenta", "Atonia Uteri", "Laserasi"
        }));
        RiwayatObstetri.setName("RiwayatObstetri");
        panelGlass1.add(RiwayatObstetri);
        RiwayatObstetri.setBounds(105, 40, 150, 23);

        widget.Label lblRisiko = new widget.Label();
        lblRisiko.setText("Risiko Tinggi :");
        lblRisiko.setName("lblRisiko");
        panelGlass1.add(lblRisiko);
        lblRisiko.setBounds(265, 40, 80, 23);

        RisikoTinggi = new widget.ComboBox();
        RisikoTinggi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya - Hipertensi", "Ya - DM", "Ya - Jantung", "Ya - Anemia", "Ya - Lain-lain"
        }));
        RisikoTinggi.setName("RisikoTinggi");
        panelGlass1.add(RisikoTinggi);
        RisikoTinggi.setBounds(350, 40, 150, 23);

        // Mules Sejak - posisi dinaikkan
        widget.Label lblMules = new widget.Label();
        lblMules.setText("Mules Sejak :");
        lblMules.setName("lblMules");
        panelGlass1.add(lblMules);
        lblMules.setBounds(0, 70, 80, 23);  // Dari 100 ke 70

        MulesSejak = new widget.Tanggal();
        MulesSejak.setForeground(new java.awt.Color(50, 70, 50));
        MulesSejak.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        MulesSejak.setName("MulesSejak");
        panelGlass1.add(MulesSejak);
        MulesSejak.setBounds(85, 70, 150, 23);

        // Pecah Ketuban Sejak - posisi dinaikkan
        widget.Label lblPecah = new widget.Label();
        lblPecah.setText("Pecah Ketuban Sejak :");
        lblPecah.setName("lblPecah");
        panelGlass1.add(lblPecah);
        lblPecah.setBounds(250, 70, 120, 23);

        PecahKetubanSejak = new widget.Tanggal();
        PecahKetubanSejak.setForeground(new java.awt.Color(50, 70, 50));
        PecahKetubanSejak.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        PecahKetubanSejak.setName("PecahKetubanSejak");
        // Set default value to 00:00:0000 00:00:00
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            java.util.Date defaultDate = sdf.parse("01-01-1900 00:00:00");
            PecahKetubanSejak.setDate(defaultDate);
        } catch (java.text.ParseException e) {
            PecahKetubanSejak.setDate(new java.util.Date());
        }
        panelGlass1.add(PecahKetubanSejak);
        PecahKetubanSejak.setBounds(375, 70, 150, 23);

        // Catatan - posisi dinaikkan
        widget.Label lblCatatan1 = new widget.Label();
        lblCatatan1.setText("Catatan :");
        lblCatatan1.setName("lblCatatan1");
        panelGlass1.add(lblCatatan1);
        lblCatatan1.setBounds(0, 100, 60, 23);  // Dari 130 ke 100

        CatatanIdentitas = new widget.TextArea();
        CatatanIdentitas.setColumns(20);
        CatatanIdentitas.setRows(3);
        CatatanIdentitas.setName("CatatanIdentitas");
        CatatanIdentitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanIdentitasKeyPressed(evt);
            }
        });
        widget.ScrollPane jScrollPane1 = new widget.ScrollPane();
        jScrollPane1.setViewportView(CatatanIdentitas);
        jScrollPane1.setName("jScrollPane1");
        panelGlass1.add(jScrollPane1);
        jScrollPane1.setBounds(65, 100, 500, 60);  // Dari 130 ke 100
    }

    private void setupTabKala12() {
        internalFrame3 = new widget.InternalFrame();
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3");
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        // Form Input Panel - UKURAN DIPERBESAR SIGNIFIKAN
        PanelInput2 = new javax.swing.JPanel();
        PanelInput2.setName("PanelInput2");
        PanelInput2.setOpaque(false);
        PanelInput2.setPreferredSize(new java.awt.Dimension(800, 450)); // TINGGI 450
        PanelInput2.setLayout(new java.awt.BorderLayout(1, 1));

        // Form Content - SCROLL PANE DIPERBESAR
        scrollPane2 = new widget.ScrollPane();
        scrollPane2.setName("scrollPane2");
        scrollPane2.setPreferredSize(new java.awt.Dimension(780, 420)); // TINGGI 420

        panelGlass2 = new widget.panelisi();
        panelGlass2.setName("panelGlass2");
        panelGlass2.setPreferredSize(new java.awt.Dimension(750, 410)); // TINGGI 410 - HARUS LEBIH BESAR DARI Y TERAKHIR
        panelGlass2.setLayout(null);

        setupFormKala12();

        scrollPane2.setViewportView(panelGlass2);
        PanelInput2.add(scrollPane2, java.awt.BorderLayout.PAGE_START);

        // Checkbox Input
        ChkInput2 = new widget.CekBox();
        ChkInput2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput2.setMnemonic('I');
        ChkInput2.setText(".: Input Data Kala I & II");
        ChkInput2.setToolTipText("Alt+I");
        ChkInput2.setBorderPainted(true);
        ChkInput2.setBorderPaintedFlat(true);
        ChkInput2.setFocusable(false);
        ChkInput2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput2.setName("ChkInput2");
        ChkInput2.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput2ActionPerformed(evt);
            }
        });
        PanelInput2.add(ChkInput2, java.awt.BorderLayout.PAGE_END);

        internalFrame3.add(PanelInput2, java.awt.BorderLayout.PAGE_START);

        // Table
        Scroll2 = new widget.ScrollPane();
        Scroll2.setName("Scroll2");
        Scroll2.setOpaque(true);

        tbKala12 = new widget.Table();
        tbKala12.setName("tbKala12");
        tbKala12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKala12MouseClicked(evt);
            }
        });
        tbKala12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKala12KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbKala12);

        internalFrame3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Kala I & II", internalFrame3);
    }

    private void setupTabCatatanPersalinan() {
        internalFrame4 = new widget.InternalFrame();
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4");
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== DIRECT SCROLL PANE - NO PANEL INPUT WRAPPER ==========
        scrollPane3 = new widget.ScrollPane();
        scrollPane3.setName("scrollPane3");
        scrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelGlass3 = new widget.panelisi();
        panelGlass3.setName("panelGlass3");
        panelGlass3.setPreferredSize(new java.awt.Dimension(1150, 1300)); // Full width, tall height
        panelGlass3.setLayout(null);

        // Setup form catatan persalinan
        setupFormCatatanPersalinan();

        scrollPane3.setViewportView(panelGlass3);

        // ========== ADD SCROLL PANE DIRECTLY TO FRAME ==========
        internalFrame4.add(scrollPane3, java.awt.BorderLayout.CENTER);

        // ========== ADD TAB ==========
        TabRawat.addTab("Catatan Persalinan", internalFrame4);
    }

    // ========== SETUP FORM CATATAN PERSALINAN ==========
    // ========== SETUP FORM CATATAN PERSALINAN - COMPLETE IMPLEMENTATION ==========
    private void setupFormCatatanPersalinan() {
        // === KALA I ===
        widget.Label lblKala1 = new widget.Label();
        lblKala1.setText("KALA I:");
        lblKala1.setName("lblKala1");
        lblKala1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblKala1.setForeground(new Color(0, 80, 0));
        panelGlass3.add(lblKala1);
        lblKala1.setBounds(20, 20, 80, 23); // Wider margin

        // Partograf mellewati garis waspada
        widget.Label lblGarisWaspada = new widget.Label();
        lblGarisWaspada.setText("Partograf mellewati garis waspada:");
        lblGarisWaspada.setName("lblGarisWaspada");
        panelGlass3.add(lblGarisWaspada);
        lblGarisWaspada.setBounds(20, 50, 200, 23);

        GarisWaspada = new widget.ComboBox();
        GarisWaspada.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        GarisWaspada.setName("GarisWaspada");
        panelGlass3.add(GarisWaspada);
        GarisWaspada.setBounds(230, 50, 80, 23);

        // Masalah lain Kala I
        widget.Label lblMasalahKala1 = new widget.Label();
        lblMasalahKala1.setText("Masalah lain, sebutkan:");
        lblMasalahKala1.setName("lblMasalahKala1");
        panelGlass3.add(lblMasalahKala1);
        lblMasalahKala1.setBounds(20, 80, 150, 23);

        MasalahKala1 = new widget.TextBox();
        MasalahKala1.setName("MasalahKala1");
        panelGlass3.add(MasalahKala1);
        MasalahKala1.setBounds(180, 80, 500, 23); // Wider textbox

        // Penatalaksanaan Masalah Kala I
        widget.Label lblPenatalaksanaanKala1 = new widget.Label();
        lblPenatalaksanaanKala1.setText("Penatalaksanaan Masalah Tersebut:");
        lblPenatalaksanaanKala1.setName("lblPenatalaksanaanKala1");
        panelGlass3.add(lblPenatalaksanaanKala1);
        lblPenatalaksanaanKala1.setBounds(20, 110, 200, 23);

        PenatalaksanaanKala1 = new widget.TextBox();
        PenatalaksanaanKala1.setName("PenatalaksanaanKala1");
        panelGlass3.add(PenatalaksanaanKala1);
        PenatalaksanaanKala1.setBounds(230, 110, 450, 23); // Wider textbox

        // Hasilnya Kala I
        widget.Label lblHasilKala1 = new widget.Label();
        lblHasilKala1.setText("Hasilnya:");
        lblHasilKala1.setName("lblHasilKala1");
        panelGlass3.add(lblHasilKala1);
        lblHasilKala1.setBounds(20, 140, 60, 23);

        HasilKala1 = new widget.TextBox();
        HasilKala1.setName("HasilKala1");
        panelGlass3.add(HasilKala1);
        HasilKala1.setBounds(90, 140, 590, 23); // Much wider textbox

        // === KALA II ===
        widget.Label lblKala2 = new widget.Label();
        lblKala2.setText("KALA II:");
        lblKala2.setName("lblKala2");
        lblKala2.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblKala2.setForeground(new Color(0, 80, 0));
        panelGlass3.add(lblKala2);
        lblKala2.setBounds(20, 180, 80, 23);

        // Episiotomi
        widget.Label lblEpisiotomi = new widget.Label();
        lblEpisiotomi.setText("Episiotomi:");
        lblEpisiotomi.setName("lblEpisiotomi");
        panelGlass3.add(lblEpisiotomi);
        lblEpisiotomi.setBounds(20, 210, 80, 23);

        Episiotomi = new widget.ComboBox();
        Episiotomi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        Episiotomi.setName("Episiotomi");
        panelGlass3.add(Episiotomi);
        Episiotomi.setBounds(110, 210, 80, 23);

        // Pendamping pada saat persalinan
        widget.Label lblPendamping = new widget.Label();
        lblPendamping.setText("Pendamping pada saat persalinan:");
        lblPendamping.setName("lblPendamping");
        panelGlass3.add(lblPendamping);
        lblPendamping.setBounds(200, 210, 200, 23);

        Pendamping = new widget.ComboBox();
        Pendamping.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Suami", "Keluarga", "Teman", "Bidan", "Tidak Ada"
        }));
        Pendamping.setName("Pendamping");
        panelGlass3.add(Pendamping);
        Pendamping.setBounds(410, 210, 100, 23);

        // Gawat Janin
        widget.Label lblGawatJanin = new widget.Label();
        lblGawatJanin.setText("Gawat Janin:");
        lblGawatJanin.setName("lblGawatJanin");
        panelGlass3.add(lblGawatJanin);
        lblGawatJanin.setBounds(10, 270, 80, 23);

        GawatJanin = new widget.ComboBox();
        GawatJanin.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        GawatJanin.setName("GawatJanin");
        panelGlass3.add(GawatJanin);
        GawatJanin.setBounds(100, 270, 80, 23);

        widget.Label lblTindakanGawatJanin = new widget.Label();
        lblTindakanGawatJanin.setText("Tindakan yang dilakukan:");
        lblTindakanGawatJanin.setName("lblTindakanGawatJanin");
        panelGlass3.add(lblTindakanGawatJanin);
        lblTindakanGawatJanin.setBounds(200, 270, 150, 23);

        TindakanGawatJanin = new widget.TextBox();
        TindakanGawatJanin.setName("TindakanGawatJanin");
        panelGlass3.add(TindakanGawatJanin);
        TindakanGawatJanin.setBounds(360, 270, 260, 23);

        // Distosia Bahu
        widget.Label lblDistosiaBahu = new widget.Label();
        lblDistosiaBahu.setText("Distosia Bahu:");
        lblDistosiaBahu.setName("lblDistosiaBahu");
        panelGlass3.add(lblDistosiaBahu);
        lblDistosiaBahu.setBounds(10, 300, 90, 23);

        DistosiaBahu = new widget.ComboBox();
        DistosiaBahu.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        DistosiaBahu.setName("DistosiaBahu");
        panelGlass3.add(DistosiaBahu);
        DistosiaBahu.setBounds(110, 300, 80, 23);

        widget.Label lblTindakanDistosia = new widget.Label();
        lblTindakanDistosia.setText("Tindakan yang dilakukan:");
        lblTindakanDistosia.setName("lblTindakanDistosia");
        panelGlass3.add(lblTindakanDistosia);
        lblTindakanDistosia.setBounds(210, 300, 150, 23);

        TindakanDistosia = new widget.TextBox();
        TindakanDistosia.setName("TindakanDistosia");
        panelGlass3.add(TindakanDistosia);
        TindakanDistosia.setBounds(370, 300, 250, 23);

        // Masalah lain Kala II
        widget.Label lblMasalahKala2 = new widget.Label();
        lblMasalahKala2.setText("Masalah lain, sebutkan:");
        lblMasalahKala2.setName("lblMasalahKala2");
        panelGlass3.add(lblMasalahKala2);
        lblMasalahKala2.setBounds(10, 330, 150, 23);

        MasalahKala2 = new widget.TextBox();
        MasalahKala2.setName("MasalahKala2");
        panelGlass3.add(MasalahKala2);
        MasalahKala2.setBounds(170, 330, 450, 23);

        // Penatalaksanaan Masalah Kala II
        widget.Label lblPenatalaksanaanKala2 = new widget.Label();
        lblPenatalaksanaanKala2.setText("Penatalaksanaan Masalah Tersebut:");
        lblPenatalaksanaanKala2.setName("lblPenatalaksanaanKala2");
        panelGlass3.add(lblPenatalaksanaanKala2);
        lblPenatalaksanaanKala2.setBounds(10, 360, 200, 23);

        PenatalaksanaanKala2 = new widget.TextBox();
        PenatalaksanaanKala2.setName("PenatalaksanaanKala2");
        panelGlass3.add(PenatalaksanaanKala2);
        PenatalaksanaanKala2.setBounds(220, 360, 400, 23);

        // Hasilnya Kala II
        widget.Label lblHasilKala2 = new widget.Label();
        lblHasilKala2.setText("Hasilnya:");
        lblHasilKala2.setName("lblHasilKala2");
        panelGlass3.add(lblHasilKala2);
        lblHasilKala2.setBounds(10, 390, 60, 23);

        HasilKala2 = new widget.TextBox();
        HasilKala2.setName("HasilKala2");
        panelGlass3.add(HasilKala2);
        HasilKala2.setBounds(80, 390, 540, 23);

        // === KALA III ===
        widget.Label lblKala3 = new widget.Label();
        lblKala3.setText("KALA III:");
        lblKala3.setName("lblKala3");
        lblKala3.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblKala3.setForeground(new Color(0, 80, 0));
        panelGlass3.add(lblKala3);
        lblKala3.setBounds(10, 430, 80, 23);

        // Lama Kala III
        widget.Label lblLamaKala3 = new widget.Label();
        lblLamaKala3.setText("Lama Kala III:");
        lblLamaKala3.setName("lblLamaKala3");
        panelGlass3.add(lblLamaKala3);
        lblLamaKala3.setBounds(10, 460, 90, 23);

        LamaKala3 = new widget.TextBox();
        LamaKala3.setName("LamaKala3");
        panelGlass3.add(LamaKala3);
        LamaKala3.setBounds(110, 460, 80, 23);

        widget.Label lblMenitKala3 = new widget.Label();
        lblMenitKala3.setText("menit");
        lblMenitKala3.setName("lblMenitKala3");
        panelGlass3.add(lblMenitKala3);
        lblMenitKala3.setBounds(200, 460, 40, 23);

        // Pemberian Oksitosin 100 MI
        widget.Label lblOksitosin100 = new widget.Label();
        lblOksitosin100.setText("Pemberian Oksitosin 100 MI:");
        lblOksitosin100.setName("lblOksitosin100");
        panelGlass3.add(lblOksitosin100);
        lblOksitosin100.setBounds(10, 490, 180, 23);

        Oksitosin100 = new widget.ComboBox();
        Oksitosin100.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        Oksitosin100.setName("Oksitosin100");
        panelGlass3.add(Oksitosin100);
        Oksitosin100.setBounds(200, 490, 80, 23);

        WaktuOksitosin100 = new widget.TextBox();
        WaktuOksitosin100.setName("WaktuOksitosin100");
        panelGlass3.add(WaktuOksitosin100);
        WaktuOksitosin100.setBounds(290, 490, 80, 23);

        widget.Label lblMenitSetelahOks100 = new widget.Label();
        lblMenitSetelahOks100.setText("menit sesudah persalinan");
        lblMenitSetelahOks100.setName("lblMenitSetelahOks100");
        panelGlass3.add(lblMenitSetelahOks100);
        lblMenitSetelahOks100.setBounds(380, 490, 150, 23);

        widget.Label lblJikaTidakAlasan100 = new widget.Label();
        lblJikaTidakAlasan100.setText("Jika tidak, alasannya apa:");
        lblJikaTidakAlasan100.setName("lblJikaTidakAlasan100");
        panelGlass3.add(lblJikaTidakAlasan100);
        lblJikaTidakAlasan100.setBounds(10, 520, 150, 23);

        AlasanTidakOksitosin100 = new widget.TextBox();
        AlasanTidakOksitosin100.setName("AlasanTidakOksitosin100");
        panelGlass3.add(AlasanTidakOksitosin100);
        AlasanTidakOksitosin100.setBounds(170, 520, 450, 23);

        // Pemberian Oksitosin (2x)
        widget.Label lblOksitosinLain = new widget.Label();
        lblOksitosinLain.setText("Pemberian Oksitosin (2x):");
        lblOksitosinLain.setName("lblOksitosinLain");
        panelGlass3.add(lblOksitosinLain);
        lblOksitosinLain.setBounds(10, 550, 150, 23);

        OksitosinLain = new widget.ComboBox();
        OksitosinLain.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        OksitosinLain.setName("OksitosinLain");
        panelGlass3.add(OksitosinLain);
        OksitosinLain.setBounds(170, 550, 80, 23);

        AlasanTidakOksitosinLain = new widget.TextBox();
        AlasanTidakOksitosinLain.setName("AlasanTidakOksitosinLain");
        panelGlass3.add(AlasanTidakOksitosinLain);
        AlasanTidakOksitosinLain.setBounds(260, 550, 360, 23);

        // Penegangan tali pusat terkendali
        widget.Label lblPenegangTali = new widget.Label();
        lblPenegangTali.setText("Penegangan tali pusat terkendali:");
        lblPenegangTali.setName("lblPenegangTali");
        panelGlass3.add(lblPenegangTali);
        lblPenegangTali.setBounds(10, 580, 190, 23);

        PenegangTali = new widget.ComboBox();
        PenegangTali.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        PenegangTali.setName("PenegangTali");
        panelGlass3.add(PenegangTali);
        PenegangTali.setBounds(210, 580, 80, 23);

        widget.Label lblJikaTidakPenegang = new widget.Label();
        lblJikaTidakPenegang.setText("Jika tidak, alasannya apa:");
        lblJikaTidakPenegang.setName("lblJikaTidakPenegang");
        panelGlass3.add(lblJikaTidakPenegang);
        lblJikaTidakPenegang.setBounds(300, 580, 150, 23);

        AlasanTidakPenegang = new widget.TextBox();
        AlasanTidakPenegang.setName("AlasanTidakPenegang");
        panelGlass3.add(AlasanTidakPenegang);
        AlasanTidakPenegang.setBounds(460, 580, 160, 23);

        // Masase Fundus Uteri
        widget.Label lblMasaseFundus = new widget.Label();
        lblMasaseFundus.setText("Masase Fundus Uteri:");
        lblMasaseFundus.setName("lblMasaseFundus");
        panelGlass3.add(lblMasaseFundus);
        lblMasaseFundus.setBounds(10, 610, 130, 23);

        MasaseFundus = new widget.ComboBox();
        MasaseFundus.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        MasaseFundus.setName("MasaseFundus");
        panelGlass3.add(MasaseFundus);
        MasaseFundus.setBounds(150, 610, 80, 23);

        widget.Label lblJikaTidakMasase = new widget.Label();
        lblJikaTidakMasase.setText("Jika tidak, alasannya apa:");
        lblJikaTidakMasase.setName("lblJikaTidakMasase");
        panelGlass3.add(lblJikaTidakMasase);
        lblJikaTidakMasase.setBounds(240, 610, 150, 23);

        AlasanTidakMasase = new widget.TextBox();
        AlasanTidakMasase.setName("AlasanTidakMasase");
        panelGlass3.add(AlasanTidakMasase);
        AlasanTidakMasase.setBounds(400, 610, 220, 23);

        // Plasenta lahir lengkap (intek)
        widget.Label lblPlasentaLengkap = new widget.Label();
        lblPlasentaLengkap.setText("Plasenta lahir lengkap (intek):");
        lblPlasentaLengkap.setName("lblPlasentaLengkap");
        panelGlass3.add(lblPlasentaLengkap);
        lblPlasentaLengkap.setBounds(10, 640, 170, 23);

        PlasentaLengkap = new widget.ComboBox();
        PlasentaLengkap.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        PlasentaLengkap.setName("PlasentaLengkap");
        panelGlass3.add(PlasentaLengkap);
        PlasentaLengkap.setBounds(190, 640, 80, 23);

        widget.Label lblJikaTidakLengkapPlasenta = new widget.Label();
        lblJikaTidakLengkapPlasenta.setText("Jika tidak lengkap, tindakan yang dilakukan:");
        lblJikaTidakLengkapPlasenta.setName("lblJikaTidakLengkapPlasenta");
        panelGlass3.add(lblJikaTidakLengkapPlasenta);
        lblJikaTidakLengkapPlasenta.setBounds(10, 670, 250, 23);

        TindakanPlasentaTidakLengkap = new widget.TextBox();
        TindakanPlasentaTidakLengkap.setName("TindakanPlasentaTidakLengkap");
        panelGlass3.add(TindakanPlasentaTidakLengkap);
        TindakanPlasentaTidakLengkap.setBounds(270, 670, 350, 23);

        // Plasenta tidak lahir 30 menit
        widget.Label lblPlasentaTidakLahir = new widget.Label();
        lblPlasentaTidakLahir.setText("Plasenta tidak lahir 30 menit:");
        lblPlasentaTidakLahir.setName("lblPlasentaTidakLahir");
        panelGlass3.add(lblPlasentaTidakLahir);
        lblPlasentaTidakLahir.setBounds(10, 700, 170, 23);

        PlasentaTidakLahir = new widget.ComboBox();
        PlasentaTidakLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        PlasentaTidakLahir.setName("PlasentaTidakLahir");
        panelGlass3.add(PlasentaTidakLahir);
        PlasentaTidakLahir.setBounds(190, 700, 80, 23);

        widget.Label lblJikaYaTindakan = new widget.Label();
        lblJikaYaTindakan.setText("Jika ya, tindakan yang dilakukan:");
        lblJikaYaTindakan.setName("lblJikaYaTindakan");
        panelGlass3.add(lblJikaYaTindakan);
        lblJikaYaTindakan.setBounds(280, 700, 180, 23);

        TindakanPlasentaTidakLahir = new widget.TextBox();
        TindakanPlasentaTidakLahir.setName("TindakanPlasentaTidakLahir");
        panelGlass3.add(TindakanPlasentaTidakLahir);
        TindakanPlasentaTidakLahir.setBounds(470, 700, 150, 23);

        // Laserasi
        widget.Label lblLaserasi = new widget.Label();
        lblLaserasi.setText("Laserasi:");
        lblLaserasi.setName("lblLaserasi");
        panelGlass3.add(lblLaserasi);
        lblLaserasi.setBounds(10, 730, 60, 23);

        Laserasi = new widget.ComboBox();
        Laserasi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        Laserasi.setName("Laserasi");
        panelGlass3.add(Laserasi);
        Laserasi.setBounds(80, 730, 80, 23);

        widget.Label lblJikaYaDimana = new widget.Label();
        lblJikaYaDimana.setText("Jika Ya dimana:");
        lblJikaYaDimana.setName("lblJikaYaDimana");
        panelGlass3.add(lblJikaYaDimana);
        lblJikaYaDimana.setBounds(170, 730, 100, 23);

        LokasiLaserasi = new widget.TextBox();
        LokasiLaserasi.setName("LokasiLaserasi");
        panelGlass3.add(LokasiLaserasi);
        LokasiLaserasi.setBounds(280, 730, 340, 23);

        // Jika laserasi perineum, derajat
        widget.Label lblJikaLaserasiPerineum = new widget.Label();
        lblJikaLaserasiPerineum.setText("Jika laserasi perineum, derajat:");
        lblJikaLaserasiPerineum.setName("lblJikaLaserasiPerineum");
        panelGlass3.add(lblJikaLaserasiPerineum);
        lblJikaLaserasiPerineum.setBounds(10, 760, 180, 23);

        DerajatLaserasi = new widget.TextBox();
        DerajatLaserasi.setName("DerajatLaserasi");
        panelGlass3.add(DerajatLaserasi);
        DerajatLaserasi.setBounds(200, 760, 100, 23);

        // Atonia Uteri
        widget.Label lblAtoniaUteri = new widget.Label();
        lblAtoniaUteri.setText("Atonia Uteri:");
        lblAtoniaUteri.setName("lblAtoniaUteri");
        panelGlass3.add(lblAtoniaUteri);
        lblAtoniaUteri.setBounds(310, 760, 80, 23);

        AtoniaUteri = new widget.ComboBox();
        AtoniaUteri.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        AtoniaUteri.setName("AtoniaUteri");
        panelGlass3.add(AtoniaUteri);
        AtoniaUteri.setBounds(400, 760, 80, 23);

        widget.Label lblJikaYaTindakanAtonia = new widget.Label();
        lblJikaYaTindakanAtonia.setText("Jika Ya tindakan:");
        lblJikaYaTindakanAtonia.setName("lblJikaYaTindakanAtonia");
        panelGlass3.add(lblJikaYaTindakanAtonia);
        lblJikaYaTindakanAtonia.setBounds(490, 760, 110, 23);

        TindakanAtonia = new widget.TextBox();
        TindakanAtonia.setName("TindakanAtonia");
        panelGlass3.add(TindakanAtonia);
        TindakanAtonia.setBounds(10, 790, 610, 23);

        // Jumlah perdarahan
        widget.Label lblJumlahPerdarahan = new widget.Label();
        lblJumlahPerdarahan.setText("Jumlah perdarahan:");
        lblJumlahPerdarahan.setName("lblJumlahPerdarahan");
        panelGlass3.add(lblJumlahPerdarahan);
        lblJumlahPerdarahan.setBounds(10, 820, 130, 23);

        JumlahPerdarahan = new widget.TextBox();
        JumlahPerdarahan.setName("JumlahPerdarahan");
        panelGlass3.add(JumlahPerdarahan);
        JumlahPerdarahan.setBounds(150, 820, 80, 23);

        widget.Label lblMl = new widget.Label();
        lblMl.setText("ml");
        lblMl.setName("lblMl");
        panelGlass3.add(lblMl);
        lblMl.setBounds(240, 820, 20, 23);

        // Masalah lain Kala III
        widget.Label lblMasalahKala3 = new widget.Label();
        lblMasalahKala3.setText("Masalah lain, sebutkan:");
        lblMasalahKala3.setName("lblMasalahKala3");
        panelGlass3.add(lblMasalahKala3);
        lblMasalahKala3.setBounds(10, 850, 150, 23);

        MasalahKala3 = new widget.TextBox();
        MasalahKala3.setName("MasalahKala3");
        panelGlass3.add(MasalahKala3);
        MasalahKala3.setBounds(170, 850, 450, 23);

        // Penatalaksanaan Masalah Kala III
        widget.Label lblPenatalaksanaanKala3 = new widget.Label();
        lblPenatalaksanaanKala3.setText("Penatalaksanaan Masalah Tersebut:");
        lblPenatalaksanaanKala3.setName("lblPenatalaksanaanKala3");
        panelGlass3.add(lblPenatalaksanaanKala3);
        lblPenatalaksanaanKala3.setBounds(10, 880, 200, 23);

        PenatalaksanaanKala3 = new widget.TextBox();
        PenatalaksanaanKala3.setName("PenatalaksanaanKala3");
        panelGlass3.add(PenatalaksanaanKala3);
        PenatalaksanaanKala3.setBounds(220, 880, 400, 23);

        // Hasilnya Kala III
        widget.Label lblHasilKala3 = new widget.Label();
        lblHasilKala3.setText("Hasilnya:");
        lblHasilKala3.setName("lblHasilKala3");
        panelGlass3.add(lblHasilKala3);
        lblHasilKala3.setBounds(10, 910, 60, 23);

        HasilKala3 = new widget.TextBox();
        HasilKala3.setName("HasilKala3");
        panelGlass3.add(HasilKala3);
        HasilKala3.setBounds(80, 910, 540, 23);

        // === BAYI BARU LAHIR SECTION ===
        widget.Label lblBayiHeader = new widget.Label();
        lblBayiHeader.setText("BAYI BARU LAHIR:");
        lblBayiHeader.setName("lblBayiHeader");
        lblBayiHeader.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblBayiHeader.setForeground(new Color(0, 80, 0));
        panelGlass3.add(lblBayiHeader);
        lblBayiHeader.setBounds(10, 950, 150, 23);

        // Berat badan bayi
        widget.Label lblBeratBadanBayi = new widget.Label();
        lblBeratBadanBayi.setText("Berat badan:");
        lblBeratBadanBayi.setName("lblBeratBadanBayi");
        panelGlass3.add(lblBeratBadanBayi);
        lblBeratBadanBayi.setBounds(10, 980, 80, 23);

        BeratBadanBayi = new widget.TextBox();
        BeratBadanBayi.setName("BeratBadanBayi");
        panelGlass3.add(BeratBadanBayi);
        BeratBadanBayi.setBounds(100, 980, 80, 23);

        widget.Label lblGramBayi = new widget.Label();
        lblGramBayi.setText("gram");
        lblGramBayi.setName("lblGramBayi");
        panelGlass3.add(lblGramBayi);
        lblGramBayi.setBounds(190, 980, 40, 23);

        // Panjang bayi
        widget.Label lblPanjangBayi = new widget.Label();
        lblPanjangBayi.setText("Panjang:");
        lblPanjangBayi.setName("lblPanjangBayi");
        panelGlass3.add(lblPanjangBayi);
        lblPanjangBayi.setBounds(240, 980, 60, 23);

        PanjangBadanBayi = new widget.TextBox();
        PanjangBadanBayi.setName("PanjangBadanBayi");
        panelGlass3.add(PanjangBadanBayi);
        PanjangBadanBayi.setBounds(310, 980, 80, 23);

        widget.Label lblCmBayi = new widget.Label();
        lblCmBayi.setText("gram");
        lblCmBayi.setName("lblCmBayi");
        panelGlass3.add(lblCmBayi);
        lblCmBayi.setBounds(400, 980, 40, 23);

        // Jenis kelamin bayi
        widget.Label lblJenisKelaminBayi = new widget.Label();
        lblJenisKelaminBayi.setText("Jenis kelamin:");
        lblJenisKelaminBayi.setName("lblJenisKelaminBayi");
        panelGlass3.add(lblJenisKelaminBayi);
        lblJenisKelaminBayi.setBounds(10, 1010, 90, 23);

        JenisKelaminBayi = new widget.ComboBox();
        JenisKelaminBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "L", "P"
        }));
        JenisKelaminBayi.setName("JenisKelaminBayi");
        panelGlass3.add(JenisKelaminBayi);
        JenisKelaminBayi.setBounds(110, 1010, 50, 23);

        // Penilaian Bayi Baru Lahir
        widget.Label lblPenilaianBayi = new widget.Label();
        lblPenilaianBayi.setText("Penilaian Bayi Baru Lahir:");
        lblPenilaianBayi.setName("lblPenilaianBayi");
        panelGlass3.add(lblPenilaianBayi);
        lblPenilaianBayi.setBounds(10, 1040, 150, 23);

        PenilaianBayi = new widget.ComboBox();
        PenilaianBayi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Baik", "Tidak Baik"
        }));
        PenilaianBayi.setName("PenilaianBayi");
        panelGlass3.add(PenilaianBayi);
        PenilaianBayi.setBounds(170, 1040, 100, 23);

        // Bayi lahir
        widget.Label lblBayiLahir = new widget.Label();
        lblBayiLahir.setText("Bayi lahir:");
        lblBayiLahir.setName("lblBayiLahir");
        panelGlass3.add(lblBayiLahir);
        lblBayiLahir.setBounds(10, 1070, 70, 23);

        BayiLahir = new widget.ComboBox();
        BayiLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Normal", "Tidak Normal"
        }));
        BayiLahir.setName("BayiLahir");
        panelGlass3.add(BayiLahir);
        BayiLahir.setBounds(90, 1070, 100, 23);

        widget.Label lblTindakanBayi = new widget.Label();
        lblTindakanBayi.setText("Tindakan:");
        lblTindakanBayi.setName("lblTindakanBayi");
        panelGlass3.add(lblTindakanBayi);
        lblTindakanBayi.setBounds(200, 1070, 70, 23);

        TindakanBayi = new widget.TextBox();
        TindakanBayi.setName("TindakanBayi");
        panelGlass3.add(TindakanBayi);
        TindakanBayi.setBounds(280, 1070, 340, 23);

        // Cacat Bawaan
        widget.Label lblCacatBawaan = new widget.Label();
        lblCacatBawaan.setText("Cacat Bawaan:");
        lblCacatBawaan.setName("lblCacatBawaan");
        panelGlass3.add(lblCacatBawaan);
        lblCacatBawaan.setBounds(10, 1100, 90, 23);

        CacatBawaan = new widget.TextBox();
        CacatBawaan.setName("CacatBawaan");
        panelGlass3.add(CacatBawaan);
        CacatBawaan.setBounds(110, 1100, 510, 23);

        // Hipotermi tindakan
        widget.Label lblHipotermiTindakan = new widget.Label();
        lblHipotermiTindakan.setText("Hipotermi, tindakan:");
        lblHipotermiTindakan.setName("lblHipotermiTindakan");
        panelGlass3.add(lblHipotermiTindakan);
        lblHipotermiTindakan.setBounds(10, 1130, 120, 23);

        HipotermiTindakan = new widget.TextBox();
        HipotermiTindakan.setName("HipotermiTindakan");
        panelGlass3.add(HipotermiTindakan);
        HipotermiTindakan.setBounds(140, 1130, 480, 23);

        // Pemberian ASI
        widget.Label lblPemberianASI = new widget.Label();
        lblPemberianASI.setText("Pemberian ASI:");
        lblPemberianASI.setName("lblPemberianASI");
        panelGlass3.add(lblPemberianASI);
        lblPemberianASI.setBounds(10, 1160, 100, 23);

        PemberianASI = new widget.ComboBox();
        PemberianASI.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak", "Ya"
        }));
        PemberianASI.setName("PemberianASI");
        panelGlass3.add(PemberianASI);
        PemberianASI.setBounds(120, 1160, 80, 23);

        widget.Label lblJikaYaBerapa = new widget.Label();
        lblJikaYaBerapa.setText("Jika Ya, berapa jam setelah lahir:");
        lblJikaYaBerapa.setName("lblJikaYaBerapa");
        panelGlass3.add(lblJikaYaBerapa);
        lblJikaYaBerapa.setBounds(210, 1160, 180, 23);

        WaktuPemberianASI = new widget.TextBox();
        WaktuPemberianASI.setName("WaktuPemberianASI");
        panelGlass3.add(WaktuPemberianASI);
        WaktuPemberianASI.setBounds(400, 1160, 80, 23);

        widget.Label lblJikaTidakAlasanASI = new widget.Label();
        lblJikaTidakAlasanASI.setText("Jika Tidak alasannya apa:");
        lblJikaTidakAlasanASI.setName("lblJikaTidakAlasanASI");
        panelGlass3.add(lblJikaTidakAlasanASI);
        lblJikaTidakAlasanASI.setBounds(10, 1190, 150, 23);

        AlasanTidakASI = new widget.TextBox();
        AlasanTidakASI.setName("AlasanTidakASI");
        panelGlass3.add(AlasanTidakASI);
        AlasanTidakASI.setBounds(170, 1190, 450, 23);

        // Masalah lain bayi
        widget.Label lblMasalahLainBayi = new widget.Label();
        lblMasalahLainBayi.setText("Masalah lain, sebutkan:");
        lblMasalahLainBayi.setName("lblMasalahLainBayi");
        panelGlass3.add(lblMasalahLainBayi);
        lblMasalahLainBayi.setBounds(10, 1220, 150, 23);

        MasalahLainBayi = new widget.TextBox();
        MasalahLainBayi.setName("MasalahLainBayi");
        panelGlass3.add(MasalahLainBayi);
        MasalahLainBayi.setBounds(170, 1220, 450, 23);

        // Hasilnya bayi
        widget.Label lblHasilnyaBayi = new widget.Label();
        lblHasilnyaBayi.setText("Hasilnya:");
        lblHasilnyaBayi.setName("lblHasilnyaBayi");
        panelGlass3.add(lblHasilnyaBayi);
        lblHasilnyaBayi.setBounds(10, 1250, 60, 23);

        HasilnyaBayi = new widget.TextBox();
        HasilnyaBayi.setName("HasilnyaBayi");
        panelGlass3.add(HasilnyaBayi);
        HasilnyaBayi.setBounds(80, 1250, 540, 23);

        // Set preferred size for scrollable panel
        panelGlass3.setPreferredSize(new java.awt.Dimension(650, 1300));
    }

    private void setupTabKala3() {
        internalFrame4 = new widget.InternalFrame();
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4");
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== PANEL INPUT KALA III ==========
        PanelInput3 = new javax.swing.JPanel();
        PanelInput3.setName("PanelInput3");
        PanelInput3.setOpaque(false);
        PanelInput3.setPreferredSize(new java.awt.Dimension(192, 350)); // Increased height for more fields
        PanelInput3.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== FORM CONTENT SCROLL PANE ==========
        scrollPane3 = new widget.ScrollPane();
        scrollPane3.setName("scrollPane3");
        scrollPane3.setPreferredSize(new java.awt.Dimension(46, 320));

        panelGlass3 = new widget.panelisi();
        panelGlass3.setName("panelGlass3");
        panelGlass3.setPreferredSize(new java.awt.Dimension(44, 300));
        panelGlass3.setLayout(null);

        // Call setup form method
        setupFormKala3();

        scrollPane3.setViewportView(panelGlass3);
        PanelInput3.add(scrollPane3, java.awt.BorderLayout.PAGE_START);

        // ========== CHECKBOX INPUT KALA III ==========
        ChkInput3 = new widget.CekBox();
        ChkInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput3.setMnemonic('I');
        ChkInput3.setText(".: Input Data Kala III - Pengeluaran Plasenta");
        ChkInput3.setToolTipText("Alt+I");
        ChkInput3.setBorderPainted(true);
        ChkInput3.setBorderPaintedFlat(true);
        ChkInput3.setFocusable(false);
        ChkInput3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput3.setName("ChkInput3");
        ChkInput3.setPreferredSize(new java.awt.Dimension(250, 20));
        ChkInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput3ActionPerformed(evt);
            }
        });
        PanelInput3.add(ChkInput3, java.awt.BorderLayout.PAGE_END);

        internalFrame4.add(PanelInput3, java.awt.BorderLayout.PAGE_START);

        // ========== TABLE KALA III ==========
        Scroll3 = new widget.ScrollPane();
        Scroll3.setName("Scroll3");
        Scroll3.setOpaque(true);

        tbKala3 = new widget.Table();
        tbKala3.setName("tbKala3");
        tbKala3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKala3MouseClicked(evt);
            }
        });
        tbKala3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKala3KeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbKala3);

        internalFrame4.add(Scroll3, java.awt.BorderLayout.CENTER);

        // ========== ADD TAB TO CONTAINER ==========
        TabRawat.addTab("Kala III - Pengeluaran Plasenta", internalFrame4);
    }

    private void setupFormKala12() {
        // === MONITORING JANIN ===
        widget.Label lblMonitoringJanin = new widget.Label();
        lblMonitoringJanin.setText("MONITORING JANIN:");
        lblMonitoringJanin.setName("lblMonitoringJanin");
        lblMonitoringJanin.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblMonitoringJanin.setForeground(new Color(0, 100, 0));
        panelGlass2.add(lblMonitoringJanin);
        lblMonitoringJanin.setBounds(10, 10, 150, 23);

        // Baris 1: DJJ, Air Ketuban, Moulage
        widget.Label lblDJJ = new widget.Label();
        lblDJJ.setText("DJJ :");
        lblDJJ.setName("lblDJJ");
        panelGlass2.add(lblDJJ);
        lblDJJ.setBounds(10, 35, 40, 23);

        DJJ = new widget.TextBox();
        DJJ.setName("DJJ");
        DJJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DJJKeyPressed(evt);
            }
        });
        panelGlass2.add(DJJ);
        DJJ.setBounds(55, 35, 60, 23);

        widget.Label lblBpm = new widget.Label();
        lblBpm.setText("bpm");
        lblBpm.setName("lblBpm");
        lblBpm.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblBpm);
        lblBpm.setBounds(120, 35, 30, 23);

        widget.Label lblAirKetuban = new widget.Label();
        lblAirKetuban.setText("Air Ketuban :");
        lblAirKetuban.setName("lblAirKetuban");
        panelGlass2.add(lblAirKetuban);
        lblAirKetuban.setBounds(170, 35, 80, 23);

        AirKetuban = new widget.ComboBox();
        AirKetuban.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "-", "U", "J", "M", "D", "K"
        }));
        AirKetuban.setName("AirKetuban");
        panelGlass2.add(AirKetuban);
        AirKetuban.setBounds(255, 35, 50, 23);

        widget.Label lblMoulage = new widget.Label();
        lblMoulage.setText("Moulage :");
        lblMoulage.setName("lblMoulage");
        panelGlass2.add(lblMoulage);
        lblMoulage.setBounds(320, 35, 60, 23);

        Moulage = new widget.ComboBox();
        Moulage.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "0", "+", "++", "+++"
        }));
        Moulage.setName("Moulage");
        panelGlass2.add(Moulage);
        Moulage.setBounds(385, 35, 60, 23);

        // === KEMAJUAN PERSALINAN ===
        widget.Label lblKemajuan = new widget.Label();
        lblKemajuan.setText("KEMAJUAN PERSALINAN:");
        lblKemajuan.setName("lblKemajuan");
        lblKemajuan.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblKemajuan.setForeground(new Color(0, 100, 0));
        panelGlass2.add(lblKemajuan);
        lblKemajuan.setBounds(10, 65, 180, 23);

        // Baris 2: Pembukaan, Penurunan, Kontraksi
        widget.Label lblPembukaan = new widget.Label();
        lblPembukaan.setText("Pembukaan :");
        lblPembukaan.setName("lblPembukaan");
        panelGlass2.add(lblPembukaan);
        lblPembukaan.setBounds(10, 90, 80, 23);

        PembukaanServiks = new widget.TextBox();
        PembukaanServiks.setName("PembukaanServiks");
        PembukaanServiks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembukaanServiksKeyPressed(evt);
            }
        });
        panelGlass2.add(PembukaanServiks);
        PembukaanServiks.setBounds(95, 90, 50, 23);

        widget.Label lblCm = new widget.Label();
        lblCm.setText("cm");
        lblCm.setName("lblCm");
        lblCm.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblCm);
        lblCm.setBounds(150, 90, 25, 23);

        widget.Label lblPenurunan = new widget.Label();
        lblPenurunan.setText("Penurunan :");
        lblPenurunan.setName("lblPenurunan");
        panelGlass2.add(lblPenurunan);
        lblPenurunan.setBounds(185, 90, 75, 23);

        PenurunanKepala = new widget.ComboBox();
        PenurunanKepala.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "0", "1", "2", "3", "4", "5"
        }));
        PenurunanKepala.setName("PenurunanKepala");
        panelGlass2.add(PenurunanKepala);
        PenurunanKepala.setBounds(265, 90, 50, 23);

        widget.Label lblKontraksi = new widget.Label();
        lblKontraksi.setText("Kontraksi/10m :");
        lblKontraksi.setName("lblKontraksi");
        panelGlass2.add(lblKontraksi);
        lblKontraksi.setBounds(330, 90, 90, 23);

        KontraksiFreq = new widget.TextBox();
        KontraksiFreq.setName("KontraksiFreq");
        KontraksiFreq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraksiFreqKeyPressed(evt);
            }
        });
        panelGlass2.add(KontraksiFreq);
        KontraksiFreq.setBounds(425, 90, 35, 23);

        widget.Label lblDurasi = new widget.Label();
        lblDurasi.setText("Durasi :");
        lblDurasi.setName("lblDurasi");
        panelGlass2.add(lblDurasi);
        lblDurasi.setBounds(470, 90, 50, 23);

        KontraksiDurasi = new widget.TextBox();
        KontraksiDurasi.setName("KontraksiDurasi");
        KontraksiDurasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontraksiDurasiKeyPressed(evt);
            }
        });
        panelGlass2.add(KontraksiDurasi);
        KontraksiDurasi.setBounds(525, 90, 35, 23);

        widget.Label lblDetik = new widget.Label();
        lblDetik.setText("dtk");
        lblDetik.setName("lblDetik");
        lblDetik.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblDetik);
        lblDetik.setBounds(565, 90, 25, 23);

        // === TANDA VITAL IBU ===
        widget.Label lblVitalIbu = new widget.Label();
        lblVitalIbu.setText("TANDA VITAL IBU:");
        lblVitalIbu.setName("lblVitalIbu");
        lblVitalIbu.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblVitalIbu.setForeground(new Color(0, 100, 0));
        panelGlass2.add(lblVitalIbu);
        lblVitalIbu.setBounds(10, 120, 130, 23);

        // Baris 3: Tensi, Nadi, Suhu
        widget.Label lblTensi = new widget.Label();
        lblTensi.setText("Tensi :");
        lblTensi.setName("lblTensi");
        panelGlass2.add(lblTensi);
        lblTensi.setBounds(10, 145, 45, 23);

        TD = new widget.TextBox();
        TD.setName("TD");
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        panelGlass2.add(TD);
        TD.setBounds(60, 145, 70, 23);

        widget.Label lblMmHg = new widget.Label();
        lblMmHg.setText("mmHg");
        lblMmHg.setName("lblMmHg");
        lblMmHg.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblMmHg);
        lblMmHg.setBounds(135, 145, 40, 23);

        widget.Label lblNadi = new widget.Label();
        lblNadi.setText("Nadi :");
        lblNadi.setName("lblNadi");
        panelGlass2.add(lblNadi);
        lblNadi.setBounds(185, 145, 40, 23);

        Nadi = new widget.TextBox();
        Nadi.setName("Nadi");
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        panelGlass2.add(Nadi);
        Nadi.setBounds(230, 145, 50, 23);

        widget.Label lblXMnt = new widget.Label();
        lblXMnt.setText("x/mnt");
        lblXMnt.setName("lblXMnt");
        lblXMnt.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblXMnt);
        lblXMnt.setBounds(285, 145, 40, 23);

        widget.Label lblSuhu = new widget.Label();
        lblSuhu.setText("Suhu :");
        lblSuhu.setName("lblSuhu");
        panelGlass2.add(lblSuhu);
        lblSuhu.setBounds(335, 145, 40, 23);

        Suhu = new widget.TextBox();
        Suhu.setName("Suhu");
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        panelGlass2.add(Suhu);
        Suhu.setBounds(380, 145, 50, 23);

        widget.Label lblCelcius = new widget.Label();
        lblCelcius.setText("°C");
        lblCelcius.setName("lblCelcius");
        lblCelcius.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblCelcius);
        lblCelcius.setBounds(435, 145, 25, 23);

        // === URIN & MEDIKASI ===
        widget.Label lblUrinMed = new widget.Label();
        lblUrinMed.setText("URIN & MEDIKASI:");
        lblUrinMed.setName("lblUrinMed");
        lblUrinMed.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblUrinMed.setForeground(new Color(0, 100, 0));
        panelGlass2.add(lblUrinMed);
        lblUrinMed.setBounds(10, 175, 130, 23);

        // BARIS 4 - LAYOUT BARU: Protein Urin, Volume Urin, Oksitosin, Obat
        widget.Label lblProtein = new widget.Label();
        lblProtein.setText("Protein Urin :");
        lblProtein.setName("lblProtein");
        panelGlass2.add(lblProtein);
        lblProtein.setBounds(10, 200, 85, 23);

        ProteinUrin = new widget.TextBox();
        ProteinUrin.setName("ProteinUrin");
        ProteinUrin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProteinUrinKeyPressed(evt);
            }
        });
        panelGlass2.add(ProteinUrin);
        ProteinUrin.setBounds(100, 200, 60, 23);

        widget.Label lblVolume = new widget.Label();
        lblVolume.setText("Volume :");
        lblVolume.setName("lblVolume");
        panelGlass2.add(lblVolume);
        lblVolume.setBounds(170, 200, 55, 23);

        VolumeUrin = new widget.TextBox();
        VolumeUrin.setName("VolumeUrin");
        VolumeUrin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VolumeUrinKeyPressed(evt);
            }
        });
        panelGlass2.add(VolumeUrin);
        VolumeUrin.setBounds(230, 200, 50, 23);

        widget.Label lblMl = new widget.Label();
        lblMl.setText("ml");
        lblMl.setName("lblMl");
        lblMl.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblMl);
        lblMl.setBounds(285, 200, 20, 23);

        // OKSITOSIN PINDAH KE BARIS YANG SAMA
        widget.Label lblOksitosin = new widget.Label();
        lblOksitosin.setText("Oksitosin :");
        lblOksitosin.setName("lblOksitosin");
        panelGlass2.add(lblOksitosin);
        lblOksitosin.setBounds(315, 200, 65, 23);

        Oksitosin = new widget.TextBox();
        Oksitosin.setName("Oksitosin");
        Oksitosin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OksitosinKeyPressed(evt);
            }
        });
        panelGlass2.add(Oksitosin);
        Oksitosin.setBounds(385, 200, 40, 23);

        widget.Label lblUnit = new widget.Label();
        lblUnit.setText("unit");
        lblUnit.setName("lblUnit");
        lblUnit.setForeground(new Color(100, 100, 100));
        panelGlass2.add(lblUnit);
        lblUnit.setBounds(430, 200, 30, 23);

        // OBAT JUGA PINDAH KE BARIS YANG SAMA
        jLabelObat = new widget.Label();
        jLabelObat.setText("Obat :");
        jLabelObat.setName("jLabelObat");
        panelGlass2.add(jLabelObat);
        jLabelObat.setBounds(470, 200, 45, 23);

        Obat = new widget.TextBox();
        Obat.setName("Obat");
        Obat.setToolTipText("Contoh: Petidin 50mg, Antibiotik");
        panelGlass2.add(Obat);
        Obat.setBounds(520, 200, 120, 23);

        // BARIS 5 - CAIRAN IV MENEMPATI POSISI OKSITOSIN SEBELUMNYA
        jLabelCairan = new widget.Label();
        jLabelCairan.setText("Cairan IV :");
        jLabelCairan.setName("jLabelCairan");
        panelGlass2.add(jLabelCairan);
        jLabelCairan.setBounds(0, 240, 80, 23); // Y=275

        Cairan = new widget.TextBox();
        Cairan.setName("Cairan");
        Cairan.setToolTipText("Contoh: RL 500ml, NaCl 0.9%");
        panelGlass2.add(Cairan);
        Cairan.setBounds(85, 240, 200, 23);

        jLabel42 = new widget.Label();
        jLabel42.setText("ml");
        jLabel42.setName("jLabel42");
        jLabel42.setForeground(new Color(100, 100, 100));
        panelGlass2.add(jLabel42);
        jLabel42.setBounds(290, 240, 30, 23);

        // === CATATAN - POSISI YANG BENAR ===
        widget.Label lblCatatan2 = new widget.Label();
        lblCatatan2.setText("Catatan Kala I & II :");
        lblCatatan2.setName("lblCatatan2");
        lblCatatan2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblCatatan2.setForeground(new Color(0, 100, 0));
        panelGlass2.add(lblCatatan2);
        lblCatatan2.setBounds(0, 300, 130, 23); // Y=305

        CatatanKala12 = new widget.TextArea();
        CatatanKala12.setColumns(20);
        CatatanKala12.setRows(3);
        CatatanKala12.setName("CatatanKala12");
        CatatanKala12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKala12KeyPressed(evt);
            }
        });

        widget.ScrollPane jScrollPane2 = new widget.ScrollPane();
        jScrollPane2.setViewportView(CatatanKala12);
        jScrollPane2.setName("jScrollPane2");
        panelGlass2.add(jScrollPane2);
        jScrollPane2.setBounds(0, 330, 600, 70); // Y=330, ukuran 600x70
        // Tambahkan ini di setupFormKala12() untuk debugging
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED, 2));
        CatatanKala12.setBackground(java.awt.Color.LIGHT_GRAY); // Temporary untuk melihat area
    }

    // ========== SETUP FORM KALA III ==========
    private void setupFormKala3() {
        // Placeholder untuk form Kala III - implementasi serupa dengan Kala I&II
        widget.Label lblInfo3 = new widget.Label();
        lblInfo3.setText("Form Kala III - Pengeluaran Plasenta");
        lblInfo3.setName("lblInfo3");
        panelGlass3.add(lblInfo3);
        lblInfo3.setBounds(0, 10, 300, 23);

        // Waktu Kala III
        widget.Label lblWaktu3 = new widget.Label();
        lblWaktu3.setText("Waktu Kala III :");
        lblWaktu3.setName("lblWaktu3");
        panelGlass3.add(lblWaktu3);
        lblWaktu3.setBounds(0, 40, 100, 23);

        WaktuKala3 = new widget.TextBox();
        WaktuKala3.setName("WaktuKala3");
        panelGlass3.add(WaktuKala3);
        WaktuKala3.setBounds(105, 40, 100, 23);

        widget.Label lblMenit3 = new widget.Label();
        lblMenit3.setText("menit");
        lblMenit3.setName("lblMenit3");
        panelGlass3.add(lblMenit3);
        lblMenit3.setBounds(210, 40, 40, 23);

        // Kondisi Plasenta
        widget.Label lblPlasenta = new widget.Label();
        lblPlasenta.setText("Kondisi Plasenta :");
        lblPlasenta.setName("lblPlasenta");
        panelGlass3.add(lblPlasenta);
        lblPlasenta.setBounds(0, 70, 100, 23);

        KondisiPlasenta = new widget.TextBox();
        KondisiPlasenta.setName("KondisiPlasenta");
        panelGlass3.add(KondisiPlasenta);
        KondisiPlasenta.setBounds(105, 70, 200, 23);

        // Perdarahan
        widget.Label lblPerdarahan3 = new widget.Label();
        lblPerdarahan3.setText("Perdarahan :");
        lblPerdarahan3.setName("lblPerdarahan3");
        panelGlass3.add(lblPerdarahan3);
        lblPerdarahan3.setBounds(0, 100, 80, 23);

        PerdarahanKala3 = new widget.TextBox();
        PerdarahanKala3.setName("PerdarahanKala3");
        panelGlass3.add(PerdarahanKala3);
        PerdarahanKala3.setBounds(85, 100, 80, 23);

        widget.Label lblCc = new widget.Label();
        lblCc.setText("cc");
        lblCc.setName("lblCc");
        panelGlass3.add(lblCc);
        lblCc.setBounds(170, 100, 20, 23);

        // Catatan Kala III
        widget.Label lblCatatan3 = new widget.Label();
        lblCatatan3.setText("Catatan :");
        lblCatatan3.setName("lblCatatan3");
        panelGlass3.add(lblCatatan3);
        lblCatatan3.setBounds(0, 130, 60, 23);

        CatatanKala3 = new widget.TextArea();
        CatatanKala3.setColumns(20);
        CatatanKala3.setRows(2);
        CatatanKala3.setName("CatatanKala3");
        widget.ScrollPane jScrollPane3 = new widget.ScrollPane();
        jScrollPane3.setViewportView(CatatanKala3);
        jScrollPane3.setName("jScrollPane3");
        panelGlass3.add(jScrollPane3);
        jScrollPane3.setBounds(65, 130, 400, 50);
    }

    // ========== SETUP FORM NEONATAL - DATA BAYI BARU LAHIR ==========
    // ========== SETUP CONTROL BUTTONS ==========
    private void setupControlButtons() {
        jPanel3 = new javax.swing.JPanel();
        jPanel3.setBackground(new java.awt.Color(215, 225, 215));
        jPanel3.setName("jPanel3");
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        // Button Panel
        panelGlass8 = new widget.panelisi();
        panelGlass8.setName("panelGlass8");
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan = new widget.Button();
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setIconTextGap(3);
        BtnSimpan.setName("BtnSimpan");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal = new widget.Button();
        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setIconTextGap(3);
        BtnBatal.setName("BtnBatal");
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus = new widget.Button();
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setIconTextGap(3);
        BtnHapus.setName("BtnHapus");
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit = new widget.Button();
        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setIconTextGap(3);
        BtnEdit.setName("BtnEdit");
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint = new widget.Button();
        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setIconTextGap(3);
        BtnPrint.setName("BtnPrint");
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll = new widget.Button();
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setIconTextGap(3);
        BtnAll.setName("BtnAll");
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        widget.Label jLabel10 = new widget.Label();
        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10");
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 30));
        panelGlass8.add(jLabel10);

        LCount = new widget.Label();
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount");
        LCount.setPreferredSize(new java.awt.Dimension(87, 30));
        panelGlass8.add(LCount);

        BtnKeluar = new widget.Button();
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setIconTextGap(3);
        BtnKeluar.setName("BtnKeluar");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        // Search Panel
        panelGlass10 = new widget.panelisi();
        panelGlass10.setName("panelGlass10");
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));

        widget.Label jLabel23 = new widget.Label();
        jLabel23.setText("Tgl.Rawat :");
        jLabel23.setName("jLabel23");
        jLabel23.setPreferredSize(new java.awt.Dimension(64, 23));
        panelGlass10.add(jLabel23);

        DTPCari1 = new widget.Tanggal();
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"02-09-2025"}));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari1);

        widget.Label jLabel24 = new widget.Label();
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("s.d.");
        jLabel24.setName("jLabel24");
        jLabel24.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel24);

        DTPCari2 = new widget.Tanggal();
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"02-09-2025"}));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari2);

        widget.Label jLabel6 = new widget.Label();
        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6");
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari = new widget.TextBox();
        TCari.setName("TCari");
        TCari.setPreferredSize(new java.awt.Dimension(273, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari = new widget.Button();
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
        BtnCari.setName("BtnCari");
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);
    }

    // ========== SETUP TABLE PROPERTIES ==========
    private void setupTableProperties() {
        tabModeIdentitas = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Tanggal", "Jam", "NIP", "Petugas", "Berat", "Tinggi",
            "Gravida", "Para", "Abortus", "Riwayat", "Risiko", "Catatan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbIdentitas.setModel(tabModeIdentitas);

        // Table Model Kala I & II (unchanged)
        tabModeKala12 = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.RM", "Nama Pasien", "Tanggal", "Jam", "DJJ", "Air Ketuban", "Moulage",
            "Pembukaan", "Penurunan", "Kontraksi Freq", "Kontraksi Durasi", "Tensi", "Nadi", "Suhu",
            "Protein", "Volume", "Oksitosin", "Obat", "Cairan", "Catatan", "NIP Petugas", "Nama Petugas"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKala12.setModel(tabModeKala12);

        // ========== NEW: Table Model Data Catatan Persalinan ==========
        tabModeDataCatatanPersalinan = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Tanggal", "Jam", "Garis Waspada", "Episiotomi", "Pendamping",
            "Gawat Janin", "Distosia Bahu", "Lama Kala III", "Oksitosin 100", "Laserasi",
            "Atonia Uteri", "Jumlah Perdarahan", "Petugas"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbDataCatatanPersalinan.setModel(tabModeDataCatatanPersalinan);

        // Set table properties
        tbIdentitas.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbIdentitas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbIdentitas.setDefaultRenderer(Object.class, new WarnaTable());

        tbKala12.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKala12.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbKala12.setDefaultRenderer(Object.class, new WarnaTable());

        tbDataCatatanPersalinan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDataCatatanPersalinan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbDataCatatanPersalinan.setDefaultRenderer(Object.class, new WarnaTable());

        // Set column widths for Data Catatan Persalinan table
        for (i = 0; i < tbDataCatatanPersalinan.getColumnCount(); i++) {
            TableColumn column = tbDataCatatanPersalinan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);      // No.Rawat
            } else if (i == 1) {
                column.setPreferredWidth(80);       // Tanggal
            } else if (i == 2) {
                column.setPreferredWidth(60);       // Jam
            } else if (i >= 3 && i <= 12) {
                column.setPreferredWidth(90);       // Data columns
            } else if (i == 13) {
                column.setPreferredWidth(120);      // Petugas
            }
        }

        tabModeObservasiKalaIV = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Tanggal", "Jam", "Jam Ke", "Tekanan Darah", "Nadi", "Suhu",
            "TFU", "Kontraksi Uterus", "Kandung Kemih", "Perdarahan", "NIP", "Petugas"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObservasiKalaIV.setModel(tabModeObservasiKalaIV);
        tbObservasiKalaIV.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObservasiKalaIV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbObservasiKalaIV.setDefaultRenderer(Object.class, new WarnaTable());

        if (tabModeRujukan == null) {
            tabModeRujukan = new DefaultTableModel(null, new Object[]{
                "No.Rawat", "Tanggal", "Jam", "Status", "Alasan", "Ket.Alasan",
                "Tempat Tujuan", "Nama Tempat", "Transportasi", "Biaya", "Pendamping",
                "Nama Pendamping", "No.Telp", "Kondisi", "Catatan", "NIP", "Petugas"}) {
                @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return false;
                }
            };

            if (tbRujukan != null) {
                tbRujukan.setModel(tabModeRujukan);
                tbRujukan.setPreferredScrollableViewportSize(new Dimension(500, 500));
                tbRujukan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tbRujukan.setDefaultRenderer(Object.class, new WarnaTable());
            }
        }

    }

    // ========== EVENT HANDLERS ==========
    private void tbObservasiKalaIVMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeObservasiKalaIV.getRowCount() != 0) {
            try {
                getDataObservasiKalaIVBaru();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void gantiObservasiKalaIVBaru() {
        if (tbObservasiKalaIV.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diganti!");
            return;
        }

        if (NIPPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        try {
            int selectedRow = tbObservasiKalaIV.getSelectedRow();
            String oldNoRawat = tbObservasiKalaIV.getValueAt(selectedRow, 0).toString();
            String oldTanggal = tbObservasiKalaIV.getValueAt(selectedRow, 1).toString();
            String oldJam = tbObservasiKalaIV.getValueAt(selectedRow, 2).toString();

            String updateSql = "UPDATE partograf_observasi_kala4 SET "
                    + "no_rawat=?, tanggal=?, jam=?, jam_ke=?, tekanan_darah=?, nadi=?, suhu=?, "
                    + "tfu=?, kontraksi_uterus=?, kandung_kemih=?, perdarahan=?, nip=? "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=?";

            if (Sequel.queryu2tf(updateSql, 15, new String[]{
                TNoRw.getText(),
                Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                getFormattedTime(),
                JamKeObsKalaIV.getSelectedItem().toString(),
                TekananDarahObsKalaIV.getText(),
                NadiObsKalaIV.getText(),
                SuhuObsKalaIV.getText(),
                TFUObsKalaIV.getText(),
                KontraksiUterusObsKalaIV.getText(),
                KandungKemihObsKalaIV.getText(),
                PerdarahanObsKalaIV.getText(),
                NIPPetugas.getText(),
                oldNoRawat,
                oldTanggal,
                oldJam
            }) == true) {
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
                tampilObservasiKalaIV();
                emptTeksKalaIVBaru();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate data!");
            }

        } catch (Exception e) {
            System.out.println("Error update: " + e);
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void tbObservasiKalaIVKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeObservasiKalaIV.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataObservasiKalaIVBaru();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void ChkInput4ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm();
    }

    private void tbDataCatatanPersalinanMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeDataCatatanPersalinan.getRowCount() != 0) {
            try {
                getDataCatatanPersalinan();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbDataCatatanPersalinanKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeDataCatatanPersalinan.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataCatatanPersalinan();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void getDataCatatanPersalinan() {
        if (tbDataCatatanPersalinan.getSelectedRow() != -1) {
            try {
                int selectedRow = tbDataCatatanPersalinan.getSelectedRow();
                String noRawat = tbDataCatatanPersalinan.getValueAt(selectedRow, 0).toString();
                String tanggal = tbDataCatatanPersalinan.getValueAt(selectedRow, 1).toString();
                String jam = tbDataCatatanPersalinan.getValueAt(selectedRow, 2).toString();

                // Load complete data from database
                String sql = "SELECT * FROM partograf_catatan_persalinan WHERE no_rawat=? AND tanggal=? AND jam=?";
                PreparedStatement psGet = koneksi.prepareStatement(sql);
                psGet.setString(1, noRawat);
                psGet.setString(2, tanggal);
                psGet.setString(3, jam);

                ResultSet rsGet = psGet.executeQuery();
                if (rsGet.next()) {
                    // Load basic data
                    TNoRw.setText(rsGet.getString("no_rawat"));
                    isPsien();
                    Valid.SetTgl(TglPartograf, rsGet.getString("tanggal"));
                    parseJamToCombo(rsGet.getString("jam"));
                    NIPPetugas.setText(rsGet.getString("nip"));
                    isPetugas();

                    // Load KALA I data
                    setComboSelection(GarisWaspada, rsGet.getString("garis_waspada"));
                    setTextSafe(MasalahKala1, rsGet.getString("masalah_kala1"));
                    setTextSafe(PenatalaksanaanKala1, rsGet.getString("penatalaksanaan_kala1"));
                    setTextSafe(HasilKala1, rsGet.getString("hasil_kala1"));

                    // Load KALA II data
                    setComboSelection(Episiotomi, rsGet.getString("episiotomi"));
                    setComboSelection(Pendamping, rsGet.getString("pendamping"));
                    setComboSelection(GawatJanin, rsGet.getString("gawat_janin"));
                    setTextSafe(TindakanGawatJanin, rsGet.getString("tindakan_gawat_janin"));
                    setComboSelection(DistosiaBahu, rsGet.getString("distosia_bahu"));
                    setTextSafe(TindakanDistosia, rsGet.getString("tindakan_distosia"));
                    setTextSafe(MasalahKala2, rsGet.getString("masalah_kala2"));
                    setTextSafe(PenatalaksanaanKala2, rsGet.getString("penatalaksanaan_kala2"));
                    setTextSafe(HasilKala2, rsGet.getString("hasil_kala2"));

                    // Load KALA III data
                    setTextSafe(LamaKala3, String.valueOf(rsGet.getInt("lama_kala3")));
                    setComboSelection(Oksitosin100, rsGet.getString("oksitosin_100"));
                    setTextSafe(WaktuOksitosin100, String.valueOf(rsGet.getInt("waktu_oksitosin_100")));
                    setTextSafe(AlasanTidakOksitosin100, rsGet.getString("alasan_tidak_oksitosin_100"));
                    setComboSelection(OksitosinLain, rsGet.getString("oksitosin_lain"));
                    setTextSafe(AlasanTidakOksitosinLain, rsGet.getString("alasan_tidak_oksitosin_lain"));
                    setComboSelection(PenegangTali, rsGet.getString("penegang_tali"));
                    setTextSafe(AlasanTidakPenegang, rsGet.getString("alasan_tidak_penegang"));
                    setComboSelection(MasaseFundus, rsGet.getString("masase_fundus"));
                    setTextSafe(AlasanTidakMasase, rsGet.getString("alasan_tidak_masase"));
                    setComboSelection(PlasentaLengkap, rsGet.getString("plasenta_lengkap"));
                    setTextSafe(TindakanPlasentaTidakLengkap, rsGet.getString("tindakan_plasenta_tidak_lengkap"));
                    setComboSelection(PlasentaTidakLahir, rsGet.getString("plasenta_tidak_lahir_30"));
                    setTextSafe(TindakanPlasentaTidakLahir, rsGet.getString("tindakan_plasenta_tidak_lahir"));
                    setComboSelection(Laserasi, rsGet.getString("laserasi"));
                    setTextSafe(LokasiLaserasi, rsGet.getString("lokasi_laserasi"));
                    setTextSafe(DerajatLaserasi, rsGet.getString("derajat_laserasi"));
                    setComboSelection(AtoniaUteri, rsGet.getString("atonia_uteri"));
                    setTextSafe(TindakanAtonia, rsGet.getString("tindakan_atonia"));
                    setTextSafe(JumlahPerdarahan, String.valueOf(rsGet.getInt("jumlah_perdarahan")));
                    setTextSafe(MasalahKala3, rsGet.getString("masalah_kala3"));
                    setTextSafe(PenatalaksanaanKala3, rsGet.getString("penatalaksanaan_kala3"));
                    setTextSafe(HasilKala3, rsGet.getString("hasil_kala3"));

                    // Load BAYI data
                    setTextSafe(BeratBadanBayi, String.valueOf(rsGet.getInt("berat_badan_bayi")));
                    setTextSafe(PanjangBadanBayi, String.valueOf(rsGet.getInt("panjang_badan_bayi")));
                    setComboSelection(JenisKelaminBayi, rsGet.getString("jenis_kelamin_bayi"));
                    setComboSelection(PenilaianBayi, rsGet.getString("penilaian_bayi"));
                    setComboSelection(BayiLahir, rsGet.getString("bayi_lahir"));
                    setTextSafe(TindakanBayi, rsGet.getString("tindakan_bayi"));
                    setTextSafe(CacatBawaan, rsGet.getString("cacat_bawaan"));
                    setTextSafe(HipotermiTindakan, rsGet.getString("hipotermi_tindakan"));
                    setComboSelection(PemberianASI, rsGet.getString("pemberian_asi"));
                    setTextSafe(WaktuPemberianASI, String.valueOf(rsGet.getInt("waktu_pemberian_asi")));
                    setTextSafe(AlasanTidakASI, rsGet.getString("alasan_tidak_asi"));
                    setTextSafe(MasalahLainBayi, rsGet.getString("masalah_lain_bayi"));
                    setTextSafe(HasilnyaBayi, rsGet.getString("hasilnya_bayi"));
                }

                rsGet.close();
                psGet.close();

            } catch (Exception e) {
                System.out.println("Error getDataCatatanPersalinan: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void setTextSafe(widget.TextBox textBox, String value) {
        if (textBox != null) {
            if (value != null && !value.equals("0")) {
                textBox.setText(value);
            } else {
                textBox.setText("");
            }
        }
    }

    private void emptTeksCatatanPersalinan() {
        // Reset KALA I
        if (GarisWaspada != null) {
            GarisWaspada.setSelectedIndex(0);
        }
        if (MasalahKala1 != null) {
            MasalahKala1.setText("");
        }
        if (PenatalaksanaanKala1 != null) {
            PenatalaksanaanKala1.setText("");
        }
        if (HasilKala1 != null) {
            HasilKala1.setText("");
        }

        // Reset KALA II
        if (Episiotomi != null) {
            Episiotomi.setSelectedIndex(0);
        }
        if (Pendamping != null) {
            Pendamping.setSelectedIndex(0);
        }
        if (GawatJanin != null) {
            GawatJanin.setSelectedIndex(0);
        }
        if (TindakanGawatJanin != null) {
            TindakanGawatJanin.setText("");
        }
        if (DistosiaBahu != null) {
            DistosiaBahu.setSelectedIndex(0);
        }
        if (TindakanDistosia != null) {
            TindakanDistosia.setText("");
        }
        if (MasalahKala2 != null) {
            MasalahKala2.setText("");
        }
        if (PenatalaksanaanKala2 != null) {
            PenatalaksanaanKala2.setText("");
        }
        if (HasilKala2 != null) {
            HasilKala2.setText("");
        }

        // Reset KALA III
        if (LamaKala3 != null) {
            LamaKala3.setText("");
        }
        if (Oksitosin100 != null) {
            Oksitosin100.setSelectedIndex(0);
        }
        if (WaktuOksitosin100 != null) {
            WaktuOksitosin100.setText("");
        }
        if (AlasanTidakOksitosin100 != null) {
            AlasanTidakOksitosin100.setText("");
        }
        if (OksitosinLain != null) {
            OksitosinLain.setSelectedIndex(0);
        }
        if (AlasanTidakOksitosinLain != null) {
            AlasanTidakOksitosinLain.setText("");
        }
        if (PenegangTali != null) {
            PenegangTali.setSelectedIndex(0);
        }
        if (AlasanTidakPenegang != null) {
            AlasanTidakPenegang.setText("");
        }
        if (MasaseFundus != null) {
            MasaseFundus.setSelectedIndex(0);
        }
        if (AlasanTidakMasase != null) {
            AlasanTidakMasase.setText("");
        }
        if (PlasentaLengkap != null) {
            PlasentaLengkap.setSelectedIndex(0);
        }
        if (TindakanPlasentaTidakLengkap != null) {
            TindakanPlasentaTidakLengkap.setText("");
        }
        if (PlasentaTidakLahir != null) {
            PlasentaTidakLahir.setSelectedIndex(0);
        }
        if (TindakanPlasentaTidakLahir != null) {
            TindakanPlasentaTidakLahir.setText("");
        }
        if (Laserasi != null) {
            Laserasi.setSelectedIndex(0);
        }
        if (LokasiLaserasi != null) {
            LokasiLaserasi.setText("");
        }
        if (DerajatLaserasi != null) {
            DerajatLaserasi.setText("");
        }
        if (AtoniaUteri != null) {
            AtoniaUteri.setSelectedIndex(0);
        }
        if (TindakanAtonia != null) {
            TindakanAtonia.setText("");
        }
        if (JumlahPerdarahan != null) {
            JumlahPerdarahan.setText("");
        }
        if (MasalahKala3 != null) {
            MasalahKala3.setText("");
        }
        if (PenatalaksanaanKala3 != null) {
            PenatalaksanaanKala3.setText("");
        }
        if (HasilKala3 != null) {
            HasilKala3.setText("");
        }

        // Reset BAYI
        if (BeratBadanBayi != null) {
            BeratBadanBayi.setText("");
        }
        if (PanjangBadanBayi != null) {
            PanjangBadanBayi.setText("");
        }
        if (JenisKelaminBayi != null) {
            JenisKelaminBayi.setSelectedIndex(0);
        }
        if (PenilaianBayi != null) {
            PenilaianBayi.setSelectedIndex(0);
        }
        if (BayiLahir != null) {
            BayiLahir.setSelectedIndex(0);
        }
        if (TindakanBayi != null) {
            TindakanBayi.setText("");
        }
        if (CacatBawaan != null) {
            CacatBawaan.setText("");
        }
        if (HipotermiTindakan != null) {
            HipotermiTindakan.setText("");
        }
        if (PemberianASI != null) {
            PemberianASI.setSelectedIndex(0);
        }
        if (WaktuPemberianASI != null) {
            WaktuPemberianASI.setText("");
        }
        if (AlasanTidakASI != null) {
            AlasanTidakASI.setText("");
        }
        if (MasalahLainBayi != null) {
            MasalahLainBayi.setText("");
        }
        if (HasilnyaBayi != null) {
            HasilnyaBayi.setText("");
        }
    }

    // ========== SETUP INPUT VALIDATION - FIXED VERSION ==========
    private void setupInputValidation() {
        // Text fields with alphanumeric validation
        if (TNoRw != null) {
            TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        }
        if (NIPPetugas != null) {
            NIPPetugas.setDocument(new batasInput((byte) 20).getKata(NIPPetugas));
        }

        // Numeric fields - USE getKata() instead of getAngka() for compatibility
        if (BeratBadan != null) {
            BeratBadan.setDocument(new batasInput((byte) 5).getKata(BeratBadan));
        }
        if (TinggiBadan != null) {
            TinggiBadan.setDocument(new batasInput((byte) 3).getKata(TinggiBadan));
        }
        if (Gravida != null) {
            Gravida.setDocument(new batasInput((byte) 2).getKata(Gravida));
        }
        if (Paritas != null) {
            Paritas.setDocument(new batasInput((byte) 2).getKata(Paritas));
        }
        if (Abortus != null) {
            Abortus.setDocument(new batasInput((byte) 2).getKata(Abortus));
        }

        // Kala I & II numeric fields
        if (DJJ != null) {
            DJJ.setDocument(new batasInput((byte) 3).getKata(DJJ));
        }
        if (PembukaanServiks != null) {
            PembukaanServiks.setDocument(new batasInput((byte) 2).getKata(PembukaanServiks));
        }
        if (KontraksiFreq != null) {
            KontraksiFreq.setDocument(new batasInput((byte) 2).getKata(KontraksiFreq));
        }
        if (KontraksiDurasi != null) {
            KontraksiDurasi.setDocument(new batasInput((byte) 3).getKata(KontraksiDurasi));
        }

        // Vital signs - mixed alphanumeric (e.g., "120/80")
        if (TD != null) {
            TD.setDocument(new batasInput((byte) 7).getKata(TD));
        }
        if (Nadi != null) {
            Nadi.setDocument(new batasInput((byte) 3).getKata(Nadi));
        }
        if (Suhu != null) {
            Suhu.setDocument(new batasInput((byte) 5).getKata(Suhu));
        }
        if (ProteinUrin != null) {
            ProteinUrin.setDocument(new batasInput((byte) 10).getKata(ProteinUrin));
        }
        if (VolumeUrin != null) {
            VolumeUrin.setDocument(new batasInput((byte) 4).getKata(VolumeUrin));
        }
        if (Oksitosin != null) {
            Oksitosin.setDocument(new batasInput((byte) 3).getKata(Oksitosin));
        }

        // Kala III fields
        if (WaktuKala3 != null) {
            WaktuKala3.setDocument(new batasInput((byte) 3).getKata(WaktuKala3));
        }
        if (KondisiPlasenta != null) {
            KondisiPlasenta.setDocument(new batasInput((byte) 100).getKata(KondisiPlasenta));
        }
        if (PerdarahanKala3 != null) {
            PerdarahanKala3.setDocument(new batasInput((byte) 4).getKata(PerdarahanKala3));
        }

        // Neonatal fields
        if (BeratBayi != null) {
            BeratBayi.setDocument(new batasInput((byte) 4).getKata(BeratBayi));
        }
        if (PanjangBayi != null) {
            PanjangBayi.setDocument(new batasInput((byte) 3).getKata(PanjangBayi));
        }
        if (LingkarKepala != null) {
            LingkarKepala.setDocument(new batasInput((byte) 3).getKata(LingkarKepala));
        }
        if (ApgarScore1 != null) {
            ApgarScore1.setDocument(new batasInput((byte) 2).getKata(ApgarScore1));
        }
        if (ApgarScore5 != null) {
            ApgarScore5.setDocument(new batasInput((byte) 2).getKata(ApgarScore5));
        }

        // Search field
        if (TCari != null) {
            TCari.setDocument(new batasInput((int) 100).getKata(TCari));
        }
    }

    // ========== SETUP EVENT HANDLERS ==========
    private void setupEventHandlers() {
        petugas.addWindowListener(new java.awt.event.WindowListener() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    NIPPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    TPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                NIPPetugas.requestFocus();
            }

            @Override
            public void windowIconified(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowDeiconified(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
            }

            @Override
            public void windowDeactivated(java.awt.event.WindowEvent e) {
            }
        });
    }

    // ========== EVENT HANDLER METHODS ==========
    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isPsien();
            NIPPetugas.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isPsien();
            NIPPetugas.requestFocus();
        }
    }

    private void NIPPetugasKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isPetugas();
            cmbJam.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isPetugas();
            cmbJam.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TNoRw.requestFocus();
        }
    }

    private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(getWidth() - 20, getHeight() - 20);
        petugas.setLocationRelativeTo(this);
        petugas.setVisible(true);
    }

    // Key pressed handlers for form fields
    private void BeratBadanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TinggiBadan.requestFocus();
        }
    }

    private void TinggiBadanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Gravida.requestFocus();
        }
    }

    private void GravidaKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Paritas.requestFocus();
        }
    }

    private void ParitasKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Abortus.requestFocus();
        }
    }

    private void AbortusKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            RiwayatObstetri.requestFocus();
        }
    }

    private void CatatanIdentitasKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            BtnSimpan.requestFocus();
        }
    }

    private void DJJKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AirKetuban.requestFocus();
        }
    }

    private void PembukaanServiksKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            PenurunanKepala.requestFocus();
        }
    }

    private void KontraksiFreqKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            KontraksiDurasi.requestFocus();
        }
    }

    private void KontraksiDurasiKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TD.requestFocus();
        }
    }

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Nadi.requestFocus();
        }
    }

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Suhu.requestFocus();
        }
    }

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ProteinUrin.requestFocus();
        }
    }

    private void ProteinUrinKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            VolumeUrin.requestFocus();
        }
    }

    private void VolumeUrinKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Oksitosin.requestFocus();
        }
    }

    private void OksitosinKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CatatanKala12.requestFocus();
        }
    }

    private void CatatanKala12KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            BtnSimpan.requestFocus();
        }
    }

    // Checkbox actions
    private void ChkInput1ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm();
    }

    private void ChkInput2ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm();
    }

    private void ChkInput3ActionPerformed(java.awt.event.ActionEvent evt) {
        isForm();
    }

    // Table click handlers
    private void tbIdentitasMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeIdentitas.getRowCount() != 0) {
            try {
                getDataIdentitas();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbIdentitasKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeIdentitas.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataIdentitas();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void tbKala12MouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeKala12.getRowCount() != 0) {
            try {
                getDataKala12();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbKala12KeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeKala12.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataKala12();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void tbKala3MouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeKala3.getRowCount() != 0) {
            try {
                getDataKala3();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbKala3KeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeKala3.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataKala3();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void tbNeonatalMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeNeonatal.getRowCount() != 0) {
            try {
                getDataNeonatal();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbNeonatalKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeNeonatal.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataNeonatal();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    // Button actions
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            int selectedTab = TabRawat.getSelectedIndex();
            switch (selectedTab) {
                case 0: // Identitas
                    simpanIdentitas();
                    break;
                case 1: // Kala I & II
                    simpanKala12();
                    break;
                case 2: // Catatan Persalinan
                    simpanCatatanPersalinan();
                    break;
                case 3: // Data Catatan Persalinan (read-only)
                    JOptionPane.showMessageDialog(null, "Tab ini hanya untuk menampilkan data. Gunakan tab lain untuk input data.");
                    break;
                case 4: // Observasi Kala IV
                    simpanObservasiKalaIVBaru();
                    break;
                case 5: // Masalah Kala IV
                    simpanEvaluasiPostpartum();
                    break;
                case 6: // Rujukan - TAMBAHKAN INI
                    simpanRujukan();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Tab tidak dikenali.");
            }
        }
    }

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, CatatanNeonatal, BtnBatal);
        }
    }

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        emptTeks();
    }

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedTab = TabRawat.getSelectedIndex();
        switch (selectedTab) {
            case 0: // Identitas
                if (tbIdentitas.getSelectedRow() > -1) {
                    hapusIdentitas();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            case 1: // Kala I & II
                if (tbKala12.getSelectedRow() > -1) {
                    hapusKala12();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            case 2: // Catatan Persalinan (form)
                emptTeksCatatanPersalinan();
                break;
            case 3: // Data Catatan Persalinan
                if (tbDataCatatanPersalinan.getSelectedRow() > -1) {
                    hapusCatatanPersalinan();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            case 4: // Observasi Kala IV
                if (tbObservasiKalaIV.getSelectedRow() > -1) {
                    hapusObservasiKalaIV();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            case 5: // Masalah Kala IV - UBAH MENJADI HAPUS DARI TABEL
                if (tbEvaluasiPostpartum.getSelectedRow() > -1) {
                    hapusEvaluasiPostpartum();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            case 6: // Rujukan - TAMBAHKAN INI
                if (tbRujukan.getSelectedRow() > -1) {
                    hapusRujukan();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;

        }
    }

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            int selectedTab = TabRawat.getSelectedIndex();
            switch (selectedTab) {
                case 0: // Identitas
                    if (tbIdentitas.getSelectedRow() > -1) {
                        gantiIdentitas();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                    }
                    break;
                case 1: // Kala I & II
                    if (tbKala12.getSelectedRow() > -1) {
                        gantiKala12();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                    }
                    break;
                case 2: // Catatan Persalinan (form)
                    JOptionPane.showMessageDialog(null, "Gunakan tab Data Catatan Persalinan untuk mengedit data yang sudah ada.");
                    break;
                case 3: // Data Catatan Persalinan
                    if (tbDataCatatanPersalinan.getSelectedRow() > -1) {
                        gantiCatatanPersalinan();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                    }
                    break;

                case 4: // Observasi Kala IV
                    if (tbObservasiKalaIV.getSelectedRow() > -1) {
                        gantiObservasiKalaIVBaru();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                    }
                    break;
                case 5: // Masalah Kala IV - UBAH MENJADI EDIT DARI TABEL
                    if (tbEvaluasiPostpartum.getSelectedRow() > -1) {
                        gantiEvaluasiPostpartum();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                    }
                    break;
                case 6: // Rujukan - TAMBAHKAN INI
                    if (tbRujukan.getSelectedRow() > -1) {
                        gantiRujukan();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data yang mau diganti..!!");
                    }
                    break;

            }
        }
    }

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
    }

// Enhanced CSS untuk layout yang lebih rapi sesuai PDF
    private String getCompleteReportCSS() {
        return "<style>\n"
                + "    @page {\n"
                + "        size: A4;\n"
                + "        margin: 15mm;\n"
                + "    }\n"
                + "    @media print {\n"
                + "        body { \n"
                + "            margin: 0; \n"
                + "            font-size: 9px; \n"
                + "            line-height: 1.2;\n"
                + "        }\n"
                + "        .page-break { \n"
                + "            page-break-before: always; \n"
                + "        }\n"
                + "        .no-print { \n"
                + "            display: none; \n"
                + "        }\n"
                + "        .print-only { \n"
                + "            display: block; \n"
                + "        }\n"
                + "        .partograf-image { \n"
                + "            max-width: 100%; \n"
                + "            height: auto;\n"
                + "            page-break-inside: avoid;\n"
                + "        }\n"
                + "        .chart-section {\n"
                + "            page-break-before: always;\n"
                + "            page-break-inside: avoid;\n"
                + "        }\n"
                + "    }\n"
                + "    @media screen {\n"
                + "        .print-only { display: none; }\n"
                + "    }\n"
                + "    body {\n"
                + "        font-family: 'Times New Roman', Times, serif;\n"
                + "        margin: 0;\n"
                + "        padding: 15px;\n"
                + "        font-size: 10px;\n"
                + "        background: white;\n"
                + "        line-height: 1.3;\n"
                + "        color: #000;\n"
                + "    }\n"
                + "    .print-controls {\n"
                + "        position: fixed;\n"
                + "        top: 10px;\n"
                + "        right: 10px;\n"
                + "        z-index: 1000;\n"
                + "        background: white;\n"
                + "        padding: 10px;\n"
                + "        border-radius: 5px;\n"
                + "        box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n"
                + "    }\n"
                + "    .print-controls button {\n"
                + "        background: #007bff;\n"
                + "        color: white;\n"
                + "        border: none;\n"
                + "        padding: 8px 15px;\n"
                + "        margin: 0 5px;\n"
                + "        border-radius: 4px;\n"
                + "        cursor: pointer;\n"
                + "        font-size: 12px;\n"
                + "    }\n"
                + "    .print-controls button:hover {\n"
                + "        background: #0056b3;\n"
                + "    }\n"
                // Header styles yang sesuai dengan PDF template
                + "    .header {\n"
                + "        text-align: center;\n"
                + "        border-bottom: 2px solid #000;\n"
                + "        padding-bottom: 15px;\n"
                + "        margin-bottom: 20px;\n"
                + "        position: relative;\n"
                + "    }\n"
                + "    .header-top {\n"
                + "        display: flex;\n"
                + "        align-items: center;\n"
                + "        justify-content: space-between;\n"
                + "        margin-bottom: 15px;\n"
                + "    }\n"
                + "    .logo-section {\n"
                + "        width: 80px;\n"
                + "        height: 80px;\n"
                + "    }\n"
                + "    .hospital-info {\n"
                + "        flex: 1;\n"
                + "        text-align: center;\n"
                + "    }\n"
                + "    .hospital-info h1 {\n"
                + "        font-size: 14px;\n"
                + "        font-weight: bold;\n"
                + "        margin: 5px 0;\n"
                + "        text-transform: uppercase;\n"
                + "    }\n"
                + "    .hospital-info p {\n"
                + "        margin: 2px 0;\n"
                + "        font-size: 9px;\n"
                + "    }\n"
                + "    .rm-box {\n"
                + "        width: 120px;\n"
                + "        height: 60px;\n"
                + "        border: 2px solid #000;\n"
                + "        display: flex;\n"
                + "        flex-direction: column;\n"
                + "        align-items: center;\n"
                + "        justify-content: center;\n"
                + "        font-size: 8px;\n"
                + "        font-weight: bold;\n"
                + "    }\n"
                + "    .partograf-title {\n"
                + "        font-size: 36px;\n"
                + "        font-weight: bold;\n"
                + "        margin: 15px 0;\n"
                + "        letter-spacing: 8px;\n"
                + "    }\n"
                // Patient info section sesuai dengan PDF
                + "    .patient-section {\n"
                + "        display: grid;\n"
                + "        grid-template-columns: 2fr 1fr 1fr 1fr 1fr;\n"
                + "        gap: 10px;\n"
                + "        margin: 20px 0;\n"
                + "        font-size: 9px;\n"
                + "        align-items: center;\n"
                + "    }\n"
                + "    .patient-field {\n"
                + "        display: flex;\n"
                + "        align-items: center;\n"
                + "    }\n"
                + "    .patient-field label {\n"
                + "        font-weight: bold;\n"
                + "        margin-right: 5px;\n"
                + "        white-space: nowrap;\n"
                + "    }\n"
                + "    .patient-field input {\n"
                + "        border: none;\n"
                + "        border-bottom: 1px solid #000;\n"
                + "        background: transparent;\n"
                + "        padding: 2px;\n"
                + "        font-size: inherit;\n"
                + "        flex: 1;\n"
                + "    }\n"
                // Chart section styles
                + "    .chart-section {\n"
                + "        margin: 20px 0;\n"
                + "        text-align: center;\n"
                + "    }\n"
                + "    .image-container {\n"
                + "        text-align: center;\n"
                + "        margin: 20px 0;\n"
                + "        background: white;\n"
                + "    }\n"
                + "    .partograf-image {\n"
                + "        max-width: 100%;\n"
                + "        height: auto;\n"
                + "        border: 1px solid #ccc;\n"
                + "        border-radius: 4px;\n"
                + "        box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n"
                + "        background: white;\n"
                + "    }\n"
                // Section styles yang lebih rapi
                + "    .section {\n"
                + "        margin-bottom: 15px;\n"
                + "        border: 1px solid #000;\n"
                + "        page-break-inside: avoid;\n"
                + "    }\n"
                + "    .section-title {\n"
                + "        background: #000;\n"
                + "        color: white;\n"
                + "        padding: 5px 10px;\n"
                + "        font-weight: bold;\n"
                + "        margin: 0;\n"
                + "        font-size: 10px;\n"
                + "        text-align: left;\n"
                + "    }\n"
                + "    .section-content {\n"
                + "        padding: 10px;\n"
                + "    }\n"
                // Table styles yang sesuai dengan PDF
                + "    table {\n"
                + "        width: 100%;\n"
                + "        border-collapse: collapse;\n"
                + "        margin-bottom: 10px;\n"
                + "        font-size: 8px;\n"
                + "    }\n"
                + "    th, td {\n"
                + "        border: 1px solid #000;\n"
                + "        padding: 3px 5px;\n"
                + "        text-align: center;\n"
                + "        vertical-align: middle;\n"
                + "    }\n"
                + "    th {\n"
                + "        background: #f0f0f0;\n"
                + "        font-weight: bold;\n"
                + "        font-size: 7px;\n"
                + "    }\n"
                + "    .checkbox {\n"
                + "        width: 12px;\n"
                + "        height: 12px;\n"
                + "        border: 1px solid #000;\n"
                + "        display: inline-block;\n"
                + "        margin-right: 5px;\n"
                + "        vertical-align: middle;\n"
                + "        background: white;\n"
                + "    }\n"
                + "    .checked {\n"
                + "        background: #000;\n"
                + "        position: relative;\n"
                + "    }\n"
                + "    .checked::after {\n"
                + "        content: '✓';\n"
                + "        color: white;\n"
                + "        position: absolute;\n"
                + "        top: -2px;\n"
                + "        left: 1px;\n"
                + "        font-size: 10px;\n"
                + "    }\n"
                + "    .signature-area {\n"
                + "        display: grid;\n"
                + "        grid-template-columns: 1fr 1fr 1fr;\n"
                + "        gap: 20px;\n"
                + "        margin-top: 40px;\n"
                + "        text-align: center;\n"
                + "        page-break-inside: avoid;\n"
                + "    }\n"
                + "    .signature-box {\n"
                + "        border: 1px solid #000;\n"
                + "        padding: 60px 10px 15px;\n"
                + "        position: relative;\n"
                + "        min-height: 80px;\n"
                + "    }\n"
                + "    .signature-title {\n"
                + "        position: absolute;\n"
                + "        top: 10px;\n"
                + "        left: 0;\n"
                + "        right: 0;\n"
                + "        font-weight: bold;\n"
                + "        font-size: 9px;\n"
                + "        background: white;\n"
                + "    }\n"
                + "    .footer {\n"
                + "        margin-top: 20px;\n"
                + "        text-align: center;\n"
                + "        font-size: 8px;\n"
                + "        color: #666;\n"
                + "        border-top: 1px solid #ccc;\n"
                + "        padding-top: 10px;\n"
                + "    }\n"
                + "</style>";
    }

// Enhanced method untuk generate complete report dengan layout yang lebih baik
    private String generateCompletePartografReport(String base64Image) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n")
                .append("<html lang=\"id\">\n")
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                .append("    <title>PARTOGRAF - ").append(TPasien.getText()).append("</title>\n")
                .append(getCompleteReportCSS()).append("\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("    <!-- Print Controls -->\n")
                .append("    <div class=\"no-print print-controls\">\n")
                .append("        <button onclick=\"window.print()\">🖨️ CETAK</button>\n")
                .append("        <button onclick=\"savePDF()\">📄 SAVE PDF</button>\n")
                .append("    </div>\n\n");

        // Header sesuai dengan PDF template
        html.append(generateEnhancedHeader());

        // Patient info section
        html.append(generatePatientInfoSection());

        // GRAFIK PARTOGRAF - halaman terpisah
        html.append(generateEnhancedPartografImageSection(base64Image));

        // Observasi detail
        html.append(generateObservasiSection());

        // Catatan persalinan
        html.append(generateCatatanPersalinanEnhanced());

        // Signature area
        html.append(generateSignatureArea());

        // Footer
        html.append(generateFooter());

        // JavaScript untuk PDF export
        html.append(getJavaScriptFunctions());

        html.append("</body>\n</html>");

        return html.toString();
    }

// Enhanced header yang sesuai dengan PDF template
    private String generateEnhancedHeader() {
        return "<div class=\"header\">\n"
                + "    <div class=\"header-top\">\n"
                + "        <div class=\"logo-section\">\n"
                + "            <!-- Logo placeholder -->\n"
                + "            <div style=\"width: 60px; height: 60px; border: 2px solid #000; border-radius: 50%;\n"
                + "                 display: flex; align-items: center; justify-content: center;\n"
                + "                 font-size: 8px; font-weight: bold; margin: 0 auto;\">LOGO</div>\n"
                + "        </div>\n"
                + "        <div class=\"hospital-info\">\n"
                + "            <h1>RSUD H.BADARUDDIN KASIM</h1>\n"
                + "            <p>Jl. Tanjung Baru Desa Maburai Kec. Murung Pudak Kab.Tabalong</p>\n"
                + "            <p>Telp. (0526) 2021018</p>\n"
                + "            <p>Website: rsud.tabalongkab.go.id | Email: rsuhb.tanjung@gmail.com</p>\n"
                + "        </div>\n"
                + "        <div class=\"rm-box\">\n"
                + "            <div>RM.</div>\n"
                + "            <div style=\"font-size: 12px; margin-top: 5px;\">" + TNoRM.getText() + "</div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"partograf-title\">PARTOGRAF</div>\n"
                + "    <div style=\"font-size: 10px; text-align: left; margin-top: 10px;\">\n"
                + "        RM.18a.1 | RM.18a.1/Rev.001/01/2020 | Hal. 1/2\n"
                + "    </div>\n"
                + "</div>";
    }

// Enhanced patient info section
    private String generatePatientInfoSection() {
        return "<div class=\"patient-section\">\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>No. Register:</label>\n"
                + "        <input type=\"text\" value=\"" + TNoRw.getText() + "\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>Nama Ibu:</label>\n"
                + "        <input type=\"text\" value=\"" + TPasien.getText() + "\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>Umur:</label>\n"
                + "        <input type=\"text\" value=\"" + Umur.getText() + "\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>G:</label>\n"
                + "        <input type=\"text\" value=\"\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>P:</label>\n"
                + "        <input type=\"text\" value=\"\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>No. Puskesmas:</label>\n"
                + "        <input type=\"text\" value=\"\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>Tanggal:</label>\n"
                + "        <input type=\"text\" value=\"" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>Jam:</label>\n"
                + "        <input type=\"text\" value=\"" + new SimpleDateFormat("HH:mm").format(new Date()) + "\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>Alamat:</label>\n"
                + "        <input type=\"text\" value=\"\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\">\n"
                + "        <label>A:</label>\n"
                + "        <input type=\"text\" value=\"\" readonly>\n"
                + "    </div>\n"
                + "    <div class=\"patient-field\" style=\"grid-column: 1 / -1;\">\n"
                + "        <label>Ketuban pecah:</label>\n"
                + "        <span>Sejak jam _______ mules sejak jam _______</span>\n"
                + "    </div>\n"
                + "</div>";
    }

// Enhanced partograf image section
    private String generateEnhancedPartografImageSection(String base64Image) {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"chart-section page-break\">\n");

        if (base64Image != null && !base64Image.isEmpty()) {
            html.append("    <div class=\"image-container\">\n")
                    .append("        <img src=\"").append(base64Image).append("\" \n")
                    .append("             alt=\"Grafik Partograf WHO\" \n")
                    .append("             class=\"partograf-image\" />\n")
                    .append("    </div>\n");
        } else {
            html.append("    <div class=\"image-container\">\n")
                    .append("        <div style=\"border: 2px dashed #ccc; padding: 50px; text-align: center; color: #666;\">\n")
                    .append("            <p>Grafik partograf tidak dapat dimuat</p>\n")
                    .append("            <p>Silakan periksa data observasi</p>\n")
                    .append("        </div>\n")
                    .append("    </div>\n");
        }

        html.append("</div>\n");
        return html.toString();
    }

// JavaScript functions untuk export PDF
    private String getJavaScriptFunctions() {
        return "<script>\n"
                + "function savePDF() {\n"
                + "    if (window.jsPDF) {\n"
                + "        // Implementation for jsPDF if available\n"
                + "        alert('PDF export functionality requires additional library');\n"
                + "    } else {\n"
                + "        // Fallback to print\n"
                + "        window.print();\n"
                + "    }\n"
                + "}\n"
                + "</script>";
    }

// Method generateCatatanPersalinanEnhanced yang hilang
    private String generateCatatanPersalinanEnhanced() {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"section\">\n")
                .append("    <div class=\"section-title\">CATATAN PERSALINAN</div>\n")
                .append("    <div class=\"section-content\">\n");

        try {
            String sql = "SELECT pcp.*, pet.nama as nama_petugas "
                    + "FROM partograf_catatan_persalinan pcp "
                    + "LEFT JOIN petugas pet ON pcp.nip = pet.nip "
                    + "WHERE pcp.no_rawat = ? "
                    + "ORDER BY pcp.tanggal DESC, pcp.jam DESC LIMIT 1";

            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, TNoRw.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Kala I
                html.append("        <h4>KALA I</h4>\n")
                        .append("        <div style=\"display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin: 10px 0;\">\n")
                        .append("            <div>\n")
                        .append("                <div style=\"margin-bottom: 8px;\">\n")
                        .append("                    <span class=\"checkbox ")
                        .append("Ya".equals(rs.getString("garis_waspada")) ? "checked" : "")
                        .append("\"></span> Partograf melewati garis waspada\n")
                        .append("                </div>\n")
                        .append("                <div style=\"margin-bottom: 8px;\">\n")
                        .append("                    <strong>Masalah lain:</strong><br>\n")
                        .append("                    ").append(rs.getString("masalah_kala1") != null ? rs.getString("masalah_kala1") : "-").append("\n")
                        .append("                </div>\n")
                        .append("            </div>\n")
                        .append("            <div>\n")
                        .append("                <div style=\"margin-bottom: 8px;\">\n")
                        .append("                    <strong>Penatalaksanaan:</strong><br>\n")
                        .append("                    ").append(rs.getString("penatalaksanaan_kala1") != null ? rs.getString("penatalaksanaan_kala1") : "-").append("\n")
                        .append("                </div>\n")
                        .append("                <div style=\"margin-bottom: 8px;\">\n")
                        .append("                    <strong>Hasil:</strong><br>\n")
                        .append("                    ").append(rs.getString("hasil_kala1") != null ? rs.getString("hasil_kala1") : "-").append("\n")
                        .append("                </div>\n")
                        .append("            </div>\n")
                        .append("        </div>\n");

                // Kala II jika ada data
                if (rs.getString("masalah_kala2") != null || rs.getString("penatalaksanaan_kala2") != null) {
                    html.append("        <h4>KALA II</h4>\n")
                            .append("        <div style=\"display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin: 10px 0;\">\n")
                            .append("            <div>\n")
                            .append("                <div style=\"margin-bottom: 8px;\">\n")
                            .append("                    <strong>Masalah:</strong><br>\n")
                            .append("                    ").append(rs.getString("masalah_kala2") != null ? rs.getString("masalah_kala2") : "-").append("\n")
                            .append("                </div>\n")
                            .append("            </div>\n")
                            .append("            <div>\n")
                            .append("                <div style=\"margin-bottom: 8px;\">\n")
                            .append("                    <strong>Penatalaksanaan:</strong><br>\n")
                            .append("                    ").append(rs.getString("penatalaksanaan_kala2") != null ? rs.getString("penatalaksanaan_kala2") : "-").append("\n")
                            .append("                </div>\n")
                            .append("            </div>\n")
                            .append("        </div>\n");
                }

                // Info petugas dan waktu
                html.append("        <div style=\"margin-top: 15px; padding-top: 10px; border-top: 1px solid #ddd; font-size: 9px;\">\n")
                        .append("            <strong>Petugas:</strong> ").append(rs.getString("nama_petugas") != null ? rs.getString("nama_petugas") : "-")
                        .append(" | <strong>Tanggal:</strong> ").append(rs.getString("tanggal"))
                        .append(" <strong>Jam:</strong> ").append(rs.getString("jam")).append("\n")
                        .append("        </div>\n");

            } else {
                html.append("        <p>Tidak ada catatan persalinan tersimpan.</p>\n");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            html.append("        <p>Error loading catatan persalinan: ").append(e.getMessage()).append("</p>\n");
        }

        html.append("    </div>\n")
                .append("</div>\n");
        return html.toString();
    }

// Method generateSignatureArea yang hilang
    private String generateSignatureArea() {
        return "<div class=\"signature-area\">\n"
                + "    <div class=\"signature-box\">\n"
                + "        <div class=\"signature-title\">Dokter Penanggung Jawab</div>\n"
                + "        <div style=\"margin-top: 40px; text-align: center;\">\n"
                + "            <div style=\"height: 60px; border-bottom: 1px solid #000; margin-bottom: 5px;\"></div>\n"
                + "            <div style=\"font-size: 9px;\">\n"
                + "                <div>Nama: ________________________</div>\n"
                + "                <div>Tanggal: ____________________</div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"signature-box\">\n"
                + "        <div class=\"signature-title\">Bidan Koordinator</div>\n"
                + "        <div style=\"margin-top: 40px; text-align: center;\">\n"
                + "            <div style=\"height: 60px; border-bottom: 1px solid #000; margin-bottom: 5px;\"></div>\n"
                + "            <div style=\"font-size: 9px;\">\n"
                + "                <div>Nama: ________________________</div>\n"
                + "                <div>Tanggal: ____________________</div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"signature-box\">\n"
                + "        <div class=\"signature-title\">Kepala Ruangan</div>\n"
                + "        <div style=\"margin-top: 40px; text-align: center;\">\n"
                + "            <div style=\"height: 60px; border-bottom: 1px solid #000; margin-bottom: 5px;\"></div>\n"
                + "            <div style=\"font-size: 9px;\">\n"
                + "                <div>Nama: ________________________</div>\n"
                + "                <div>Tanggal: ____________________</div>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</div>\n";
    }

// Method generateFooter yang hilang
    private String generateFooter() {
        return "<div class=\"footer\">\n"
                + "    <p>Dicetak pada: " + new SimpleDateFormat("dd MMMM yyyy 'pukul' HH:mm:ss").format(new Date()) + "</p>\n"
                + "    <p>RSUD H.Badaruddin Kasim - Sistem Informasi Partograf WHO</p>\n"
                + "    <p>RM.18a.1/Rev.001/01/2020</p>\n"
                + "</div>\n";
    }

// Method tambahan untuk mengatasi error BtnPrintActionPerformed
// Pastikan method ini sudah ada di class Anda, jika belum ada, tambahkan:
    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            // Validasi data
            if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Pilih pasien terlebih dahulu!");
                return;
            }

            // Check if there's any data to print
            boolean hasData = checkPartografData();
            if (!hasData) {
                JOptionPane.showMessageDialog(null,
                        "Belum ada data partograf untuk pasien ini!\nSimpan data observasi terlebih dahulu.");
                return;
            }

            // Generate grafik partograf sebagai gambar
            String base64Image = generatePartografImage();

            // Generate HTML report dengan grafik
            String htmlContent = generateCompletePartografReport(base64Image);

            // Create temporary HTML file
            File tempFile = File.createTempFile("partograf_report_", ".html");
            tempFile.deleteOnExit();

            // Write HTML to file with UTF-8 encoding
            try ( FileWriter writer = new FileWriter(tempFile, StandardCharsets.UTF_8)) {
                writer.write(htmlContent);
            }

            // Open in default browser
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(tempFile.toURI());
                    JOptionPane.showMessageDialog(null, "Report berhasil dibuat dan dibuka di browser!");
                } else {
                    openBrowserFallback(tempFile.getAbsolutePath());
                }
            } else {
                openBrowserFallback(tempFile.getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error membuat report: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    // Method untuk check apakah ada data partograf
    private boolean checkPartografData() {
        try {
            String sql = "SELECT COUNT(*) as total FROM ("
                    + "SELECT no_rawat FROM partograf_identitas WHERE no_rawat = ? "
                    + "UNION ALL "
                    + "SELECT no_rawat FROM partograf_kala12 WHERE no_rawat = ? "
                    + "UNION ALL "
                    + "SELECT no_rawat FROM partograf_catatan_persalinan WHERE no_rawat = ?"
                    + ") as combined";

            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, TNoRw.getText());
            ps.setString(2, TNoRw.getText());
            ps.setString(3, TNoRw.getText());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean hasData = rs.getInt("total") > 0;
                rs.close();
                ps.close();
                return hasData;
            }

            rs.close();
            ps.close();
            return false;
        } catch (Exception e) {
            System.out.println("Error checking partograf data: " + e.getMessage());
            return false;
        }
    }

// Fallback method untuk buka browser
    private void openBrowserFallback(String filePath) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;

            if (os.contains("windows")) {
                pb = new ProcessBuilder("cmd", "/c", "start", "\"\"", filePath);
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", filePath);
            } else {
                // Linux/Unix
                pb = new ProcessBuilder("xdg-open", filePath);
            }

            pb.start();
            JOptionPane.showMessageDialog(null, "Report dibuat dan dibuka di browser!");

        } catch (Exception e) {
            System.out.println("Error opening browser: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Report berhasil dibuat di: " + filePath
                    + "\nSilakan buka file tersebut secara manual di browser.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

// Method tambahan untuk validasi koneksi database (opsional, untuk safety)
    private boolean isConnectionValid() {
        try {
            if (koneksi == null || koneksi.isClosed()) {
                return false;
            }
            // Test dengan query sederhana
            PreparedStatement ps = koneksi.prepareStatement("SELECT 1");
            ResultSet rs = ps.executeQuery();
            rs.close();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return false;
        }
    }

// Method untuk debug - cek apakah table partograf ada (opsional)
    private void debugCheckTables() {
        try {
            String[] tables = {"partograf_identitas", "partograf_kala12", "partograf_catatan_persalinan"};

            for (String table : tables) {
                String sql = "SELECT COUNT(*) as total FROM " + table + " WHERE no_rawat = ?";
                PreparedStatement ps = koneksi.prepareStatement(sql);
                ps.setString(1, TNoRw.getText());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt("total");
                    System.out.println("Table " + table + " has " + count + " records for no_rawat: " + TNoRw.getText());
                }

                rs.close();
                ps.close();
            }
        } catch (Exception e) {
            System.out.println("Debug check tables error: " + e.getMessage());
        }
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        BtnCariActionPerformed(null);
    }

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
        }
    }

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        tampil();
    }

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }

    // ========== UPDATED DATABASE SAVE METHODS ==========
    // Update all simpan methods to use new time format
    private String getFormattedTime() {
        return Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem();
    }

    // ========== UTILITY METHODS ==========
    public void emptTeks() {
        // Clear field monitoring janin
        DJJ.setText("");
        AirKetuban.setSelectedIndex(0);
        Moulage.setSelectedIndex(0);

        // Clear field kemajuan persalinan
        PembukaanServiks.setText("");
        PenurunanKepala.setSelectedIndex(0); // Reset ke index 0 (angka 0)

        // Clear field kontraksi
        KontraksiFreq.setText("");
        KontraksiDurasi.setText("");

        // Clear field tanda vital
        TD.setText("");
        Nadi.setText("");
        Suhu.setText("");

        // Clear field urin & medikasi
        ProteinUrin.setText("");
        VolumeUrin.setText("");
        Oksitosin.setText("");
        Obat.setText("");      // Field baru
        Cairan.setText("");    // Field baru

        // Clear catatan
        CatatanKala12.setText("");

        // Reset tanggal dan jam ke sekarang
        TglPartograf.setDate(new Date());
        jam(); // Method untuk set jam sekarang

        // RESET TANGGAL DAN WAKTU KE SEKARANG
        TglPartograf.setDate(new Date());

        // SET JAM SEKARANG
        setJamSekarangStatis();

        // RESET CHECKBOX KE OTOMATIS
        ChkKejadian.setSelected(true);

        // RESET CHECKBOX - default ke otomatis
        ChkKejadian.setSelected(true);

        // KOSONGKAN SEMUA FORM IDENTITAS
        BeratBadan.setText("");
        TinggiBadan.setText("");
        Gravida.setText("");
        Paritas.setText("");
        Abortus.setText("");
        RiwayatObstetri.setSelectedIndex(0);
        RisikoTinggi.setSelectedIndex(0);
        MulesSejak.setDate(new Date());

        // Set PecahKetubanSejak ke default 1900-01-01 00:00:00
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            java.util.Date defaultDate = sdf.parse("01-01-1900 00:00:00");
            PecahKetubanSejak.setDate(defaultDate);
        } catch (java.text.ParseException e) {
            PecahKetubanSejak.setDate(new java.util.Date());
        }
        CatatanIdentitas.setText("");

        // KOSONGKAN SEMUA FORM KALA I & II
        if (DJJ != null) {
            DJJ.setText("");
        }
        if (AirKetuban != null) {
            AirKetuban.setSelectedIndex(0);
        }
        if (Moulage != null) {
            Moulage.setSelectedIndex(0);
        }
        if (PembukaanServiks != null) {
            PembukaanServiks.setText("");
        }
        if (PenurunanKepala != null) {
            PenurunanKepala.setSelectedIndex(0);
        }
        if (KontraksiFreq != null) {
            KontraksiFreq.setText("");
        }
        if (KontraksiDurasi != null) {
            KontraksiDurasi.setText("");
        }
        if (TD != null) {
            TD.setText("");
        }
        if (Nadi != null) {
            Nadi.setText("");
        }
        if (Suhu != null) {
            Suhu.setText("");
        }
        if (ProteinUrin != null) {
            ProteinUrin.setText("");
        }
        if (VolumeUrin != null) {
            VolumeUrin.setText("");
        }
        if (Oksitosin != null) {
            Oksitosin.setText("");
        }
        if (CatatanKala12 != null) {
            CatatanKala12.setText("");
        }

        // KOSONGKAN SEMUA FORM KALA III
        if (WaktuKala3 != null) {
            WaktuKala3.setText("");
        }
        if (KondisiPlasenta != null) {
            KondisiPlasenta.setText("");
        }
        if (PerdarahanKala3 != null) {
            PerdarahanKala3.setText("");
        }
        if (CatatanKala3 != null) {
            CatatanKala3.setText("");
        }

        // KOSONGKAN SEMUA FORM NEONATAL
        if (BeratBayi != null) {
            BeratBayi.setText("");
        }
        if (PanjangBayi != null) {
            PanjangBayi.setText("");
        }
        if (LingkarKepala != null) {
            LingkarKepala.setText("");
        }
        if (ApgarScore1 != null) {
            ApgarScore1.setText("");
        }
        if (ApgarScore5 != null) {
            ApgarScore5.setText("");
        }
        if (JenisKelamin != null) {
            JenisKelamin.setSelectedIndex(0);
        }
        if (CaraPersalinan != null) {
            CaraPersalinan.setSelectedIndex(0);
        }
        if (KondisiBayi != null) {
            KondisiBayi.setSelectedIndex(0);
        }
        if (CatatanNeonatal != null) {
            CatatanNeonatal.setText("");
        }

        // FOCUS KE FIELD PERTAMA YANG BISA DIEDIT (BERAT BADAN)
        if (BeratBadan != null) {
            BeratBadan.requestFocus();
        } else {
            // Jika BeratBadan belum diinisialisasi, focus ke tanggal
            TglPartograf.requestFocus();
        }
    }

    // Method untuk set waktu manual (ketika checkbox otomatis dimatikan)
    private void setWaktuManual() {
        if (Jam != null && Menit != null && Detik != null) {
            // Jam tetap di posisi terakhir yang dipilih user
            System.out.println("Mode manual: " + Jam.getSelectedItem() + ":"
                    + Menit.getSelectedItem() + ":" + Detik.getSelectedItem());
        }
    }

    private void isForm() {
        if ((ChkInput1 != null && ChkInput1.isSelected())
                || (ChkInput2 != null && ChkInput2.isSelected())) {

            if (ChkInput1 != null) {
                if (ChkInput1.isSelected()) {
                    ChkInput1.setVisible(false);
                    PanelInput1.setPreferredSize(new Dimension(getWidth(), 300));
                    scrollPane1.setVisible(true);
                    ChkInput1.setVisible(true);
                } else {
                    ChkInput1.setVisible(false);
                    PanelInput1.setPreferredSize(new Dimension(getWidth(), 20));
                    scrollPane1.setVisible(false);
                    ChkInput1.setVisible(true);
                }
            }

            if (ChkInput2 != null) {
                if (ChkInput2.isSelected()) {
                    ChkInput2.setVisible(false);
                    PanelInput2.setPreferredSize(new Dimension(getWidth(), 300));
                    scrollPane2.setVisible(true);
                    ChkInput2.setVisible(true);
                } else {
                    ChkInput2.setVisible(false);
                    PanelInput2.setPreferredSize(new Dimension(getWidth(), 20));
                    scrollPane2.setVisible(false);
                    ChkInput2.setVisible(true);
                }
            }

            // ChkInput3 removed - form always visible
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hanya update jika checkbox otomatis aktif
                if (ChkKejadian.isSelected()) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();

                    String jamSekarang = String.format("%02d", cal.get(java.util.Calendar.HOUR_OF_DAY));
                    String menitSekarang = String.format("%02d", cal.get(java.util.Calendar.MINUTE));
                    String detikSekarang = String.format("%02d", cal.get(java.util.Calendar.SECOND));

                    // Update ComboBox di header
                    if (Jam != null) {
                        Jam.setSelectedItem(jamSekarang);
                    }
                    if (Menit != null) {
                        Menit.setSelectedItem(menitSekarang);
                    }
                    if (Detik != null) {
                        Detik.setSelectedItem(detikSekarang);
                    }

                    // Update ComboBox observasi jika ada
                    if (cmbJam != null) {
                        cmbJam.setSelectedItem(jamSekarang);
                    }
                    if (cmbMnt != null) {
                        cmbMnt.setSelectedItem(menitSekarang);
                    }
                    if (cmbDtk != null) {
                        cmbDtk.setSelectedItem(detikSekarang);
                    }
                }
            }
        };

        // Timer update setiap detik
        timer = new Timer(1000, taskPerformer);
        timer.start();
    }

    public void isPetugas() {
        try {
            if (!NIPPetugas.getText().equals("")) {
                ps3 = koneksi.prepareStatement("select nama from pegawai where nik=?");
                ps3.setString(1, NIPPetugas.getText());
                rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    TPetugas.setText(rs3.getString("nama"));
                }
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            } else {
                TPetugas.setText("");
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void isPsien() {
        try {
            if (!TNoRw.getText().equals("")) {
                ps2 = koneksi.prepareStatement("select pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,reg_periksa.umurdaftar,reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
                ps2.setString(1, TNoRw.getText());
                rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    TNoRM.setText(rs2.getString("no_rkm_medis"));
                    TPasien.setText(rs2.getString("nm_pasien"));
                    TglLahir.setText(rs2.getString("tgl_lahir"));
                    Umur.setText(rs2.getString("umurdaftar") + " " + rs2.getString("sttsumur"));
                }
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } else {
                TNoRM.setText("");
                TPasien.setText("");
                TglLahir.setText("");
                Umur.setText("");
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void getDataIdentitas() {
        if (tbIdentitas.getSelectedRow() != -1) {
            try {
                // Ambil data dari table model berdasarkan row yang dipilih
                int selectedRow = tbIdentitas.getSelectedRow();

                // Data utama pasien
                TNoRw.setText(tbIdentitas.getValueAt(selectedRow, 0).toString());
                isPsien(); // Load data pasien otomatis

                // Data tanggal dan jam
                String tanggal = tbIdentitas.getValueAt(selectedRow, 1).toString();
                Valid.SetTgl(TglPartograf, tanggal);

                String jamLengkap = tbIdentitas.getValueAt(selectedRow, 2).toString();
                // Parse jam format HH:MM:SS
                if (jamLengkap.length() >= 8) {
                    String[] waktu = jamLengkap.split(":");
                    Jam.setSelectedItem(waktu[0]);
                    Menit.setSelectedItem(waktu[1]);
                    Detik.setSelectedItem(waktu[2]);
                }

                // Data petugas
                NIPPetugas.setText(tbIdentitas.getValueAt(selectedRow, 3).toString());
                TPetugas.setText(tbIdentitas.getValueAt(selectedRow, 4).toString());

                // Data identitas ibu - dengan null check
                BeratBadan.setText(getTableValue(selectedRow, 5));
                TinggiBadan.setText(getTableValue(selectedRow, 6));
                Gravida.setText(getTableValue(selectedRow, 7));
                Paritas.setText(getTableValue(selectedRow, 8));
                Abortus.setText(getTableValue(selectedRow, 9));

                // ComboBox selections
                RiwayatObstetri.setSelectedItem(getTableValue(selectedRow, 10));
                RisikoTinggi.setSelectedItem(getTableValue(selectedRow, 11));

                // Catatan - index terakhir
                CatatanIdentitas.setText(getTableValue(selectedRow, 12));

                // Load data tambahan dari database jika diperlukan (mules_sejak, pecah_ketuban_sejak)
                loadAdditionalIdentitasData(TNoRw.getText(), tanggal, jamLengkap);

            } catch (Exception e) {
                System.out.println("Error getDataIdentitas: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ========== HELPER METHOD - Get Table Value with Null Check ==========
    private String getTableValue(int row, int column) {
        try {
            Object value = tbIdentitas.getValueAt(row, column);
            return (value != null) ? value.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

// ========== LOAD ADDITIONAL DATA FROM DATABASE ==========
    private void loadAdditionalIdentitasData(String noRawat, String tanggal, String jam) {
        try {
            // Query untuk mendapatkan data lengkap dari database
            ps = koneksi.prepareStatement(
                    "SELECT mules_sejak, pecah_ketuban_sejak FROM partograf_identitas "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=?");
            ps.setString(1, noRawat);
            ps.setString(2, tanggal);
            ps.setString(3, jam);

            rs = ps.executeQuery();
            if (rs.next()) {
                // Set data tanggal mules dan pecah ketuban
                java.sql.Timestamp mulesSejak = rs.getTimestamp("mules_sejak");
                if (mulesSejak != null) {
                    MulesSejak.setDate(new Date(mulesSejak.getTime()));
                } else {
                    MulesSejak.setDate(new Date());
                }

                java.sql.Timestamp pecahKetuban = rs.getTimestamp("pecah_ketuban_sejak");
                if (pecahKetuban != null) {
                    PecahKetubanSejak.setDate(new Date(pecahKetuban.getTime()));
                } else {
                    // Set default 1900-01-01 00:00:00
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date defaultDate = sdf.parse("01-01-1900 00:00:00");
                        PecahKetubanSejak.setDate(defaultDate);
                    } catch (Exception e) {
                        PecahKetubanSejak.setDate(new Date());
                    }
                }
            }

            // Cleanup
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }

        } catch (SQLException e) {
            System.out.println("Error loading additional data: " + e.getMessage());
        }
    }

    private void getDataKala3() {
        if (tbKala3.getSelectedRow() != -1) {
            TNoRw.setText(tbKala3.getValueAt(tbKala3.getSelectedRow(), 0).toString());
            isPsien();
            Valid.SetTgl(TglPartograf, tbKala3.getValueAt(tbKala3.getSelectedRow(), 1).toString());
            cmbJam.setSelectedItem(tbKala3.getValueAt(tbKala3.getSelectedRow(), 2).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbKala3.getValueAt(tbKala3.getSelectedRow(), 2).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbKala3.getValueAt(tbKala3.getSelectedRow(), 2).toString().substring(6, 8));
            WaktuKala3.setText(tbKala3.getValueAt(tbKala3.getSelectedRow(), 3).toString());
            KondisiPlasenta.setText(tbKala3.getValueAt(tbKala3.getSelectedRow(), 4).toString());
            PerdarahanKala3.setText(tbKala3.getValueAt(tbKala3.getSelectedRow(), 5).toString());
            CatatanKala3.setText(tbKala3.getValueAt(tbKala3.getSelectedRow(), 6).toString());
        }
    }

    private void getDataNeonatal() {
        if (tbNeonatal.getSelectedRow() != -1) {
            TNoRw.setText(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 0).toString());
            isPsien();
            Valid.SetTgl(TglPartograf, tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 1).toString());
            cmbJam.setSelectedItem(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 2).toString().substring(0, 2));
            cmbMnt.setSelectedItem(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 2).toString().substring(3, 5));
            cmbDtk.setSelectedItem(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 2).toString().substring(6, 8));
            BeratBayi.setText(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 3).toString());
            PanjangBayi.setText(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 4).toString());
            ApgarScore1.setText(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 5).toString());
            ApgarScore5.setText(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 6).toString());
            JenisKelamin.setSelectedItem(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 7).toString());
            CaraPersalinan.setSelectedItem(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 8).toString());
            KondisiBayi.setSelectedItem(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 9).toString());
            CatatanNeonatal.setText(tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 10).toString());
        }
    }

    // ========== DATABASE METHODS ==========
    private void simpan() {
        int selectedTab = TabRawat.getSelectedIndex();
        switch (selectedTab) {
            case 0: // Identitas
                simpanIdentitas();
                break;
            case 1: // Kala I & II
                simpanKala12();
                break;
            case 2: // Kala III
                simpanKala3();
                break;
            case 3: // Neonatal
                simpanNeonatal();
                break;
        }
    }

    private void simpanIdentitas() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else {
            // PERBAIKAN: Pastikan jumlah placeholder (?) sesuai dengan jumlah parameter
            // Berdasarkan struktur tabel partograf_identitas yang memiliki 15 kolom:
            // no_rawat, tanggal, jam, nip, nama_petugas, berat_badan, tinggi_badan, 
            // gravida, para, abortus, riwayat_obstetri, risiko_tinggi, mules_sejak, 
            // pecah_ketuban_sejak, catatan

            if (Sequel.menyimpantf("partograf_identitas",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", // 15 placeholder untuk 15 kolom
                    "Data", 15, new String[]{
                        TNoRw.getText(), // 1. no_rawat
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal
                        getFormattedTime(), // 3. jam
                        NIPPetugas.getText(), // 4. nip
                        TPetugas.getText(), // 5. nama_petugas
                        BeratBadan.getText().equals("") ? "0" : BeratBadan.getText(), // 6. berat_badan
                        TinggiBadan.getText().equals("") ? "0" : TinggiBadan.getText(), // 7. tinggi_badan
                        Gravida.getText().equals("") ? "0" : Gravida.getText(), // 8. gravida
                        Paritas.getText().equals("") ? "0" : Paritas.getText(), // 9. para
                        Abortus.getText().equals("") ? "0" : Abortus.getText(), // 10. abortus
                        RiwayatObstetri.getSelectedItem().toString(), // 11. riwayat_obstetri
                        RisikoTinggi.getSelectedItem().toString(), // 12. risiko_tinggi
                        Valid.SetTgl(MulesSejak.getSelectedItem() + "") + " "
                        + // 13. mules_sejak
                        MulesSejak.getSelectedItem().toString().substring(11),
                        Valid.SetTgl(PecahKetubanSejak.getSelectedItem() + "") + " "
                        + // 14. pecah_ketuban_sejak
                        PecahKetubanSejak.getSelectedItem().toString().substring(11),
                        CatatanIdentitas.getText() // 15. catatan
                    }) == true) {

                // Update table model dengan data yang sama
                tabModeIdentitas.addRow(new String[]{
                    TNoRw.getText(), // no_rawat
                    Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // tanggal
                    getFormattedTime(), // jam
                    NIPPetugas.getText(), // nip
                    TPetugas.getText(), // nama_petugas
                    BeratBadan.getText(), // berat_badan
                    TinggiBadan.getText(), // tinggi_badan
                    Gravida.getText(), // gravida
                    Paritas.getText(), // para
                    Abortus.getText(), // abortus
                    RiwayatObstetri.getSelectedItem().toString(), // riwayat_obstetri
                    RisikoTinggi.getSelectedItem().toString(), // risiko_tinggi
                    CatatanIdentitas.getText() // catatan (table hanya 13 kolom)
                });

                emptTeks();
                LCount.setText("" + tabModeIdentitas.getRowCount());
            }
        }
    }

    private void simpanKala12() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else if (DJJ.getText().trim().equals("")) {
            Valid.textKosong(DJJ, "DJJ");
        } else {
            String jamString = getJamString();

            // SQL dengan 20 parameter sesuai struktur database
            if (Sequel.menyimpantf("partograf_kala12",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data Partograf Kala12", 20, new String[]{
                        TNoRw.getText(), // 1. no_rawat
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tgl_perawatan
                        jamString, // 3. jam_rawat
                        PembukaanServiks.getText(), // 4. pembukaan_serviks
                        getPenurunanValue(), // 5. penurunan_kepala (0-5)
                        DJJ.getText(), // 6. djj
                        getComboValue(AirKetuban), // 7. air_ketuban
                        getComboValue(Moulage), // 8. moulage
                        KontraksiFreq.getText(), // 9. kontraksi_freq
                        KontraksiDurasi.getText(), // 10. kontraksi_durasi
                        TD.getText(), // 11. tensi (format: 120/80)
                        Nadi.getText(), // 12. nadi
                        Suhu.getText(), // 13. suhu
                        ProteinUrin.getText(), // 14. protein_urin
                        VolumeUrin.getText(), // 15. volume_urin
                        Oksitosin.getText(), // 16. oksitosin
                        Obat.getText(), // 17. obat_lain
                        Cairan.getText(), // 18. cairan_iv
                        CatatanKala12.getText(), // 19. catatan_kala12
                        NIPPetugas.getText() // 20. nip
                    }) == true) {

                // Tambah ke tabel tampil
                tabModeKala12.addRow(new String[]{
                    TNoRw.getText(), // No.Rawat
                    TPasien.getText(), // Nama Pasien
                    TNoRM.getText(), // No.RM
                    Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tgl_perawatan
                    jamString, // 3. jam_rawat
                    PembukaanServiks.getText(), // 4. pembukaan_serviks
                    getPenurunanValue(), // 5. penurunan_kepala (0-5)
                    DJJ.getText(), // 6. djj
                    getComboValue(AirKetuban), // 7. air_ketuban
                    getComboValue(Moulage), // 8. moulage
                    KontraksiFreq.getText(), // 9. kontraksi_freq
                    KontraksiDurasi.getText(), // 10. kontraksi_durasi
                    TD.getText(), // 11. tensi (format: 120/80)
                    Nadi.getText(), // 12. nadi
                    Suhu.getText(), // 13. suhu
                    ProteinUrin.getText(), // 14. protein_urin
                    VolumeUrin.getText(), // 15. volume_urin
                    Oksitosin.getText(), // 16. oksitosin
                    Obat.getText(), // 17. obat_lain
                    Cairan.getText(), // 18. cairan_iv
                    CatatanKala12.getText(), // 19. catatan_kala12
                    NIPPetugas.getText() // 20. nip
                });

                emptTeks();
                LCount.setText("" + tabModeKala12.getRowCount());
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        }
    }

// 3. HELPER METHOD - Get deskripsi penurunan kepala
    private String getPenurunanDescription() {
        String value = getPenurunanValue();
        switch (value) {
            case "0":
                return "0 - Kepala masih tinggi";
            case "1":
                return "1 - Sebagian kepala masuk PAP";
            case "2":
                return "2 - Sebagian besar kepala masuk PAP";
            case "3":
                return "3 - Kepala sudah engagement";
            case "4":
                return "4 - Kepala hampir lahir";
            case "5":
                return "5 - Kepala di perineum";
            default:
                return value;
        }
    }

    private String getPenurunanValue() {
        if (PenurunanKepala != null) {
            Object selected = PenurunanKepala.getSelectedItem();
            if (selected != null) {
                String value = selected.toString();
                // Ambil angka pertama (0-5)
                if (value.length() > 0 && Character.isDigit(value.charAt(0))) {
                    return String.valueOf(value.charAt(0));
                }
            }
        }
        return "0"; // Default value
    }

// 9. HELPER METHOD - Get combo value dengan null check
    private String getComboValue(widget.ComboBox combo) {
        if (combo != null) {
            Object selected = combo.getSelectedItem();
            if (selected != null) {
                return selected.toString();
            }
        }
        return ""; // Default empty
    }

    private void simpanKala3() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else {
            if (Sequel.menyimpantf("partograf_kala3", "?,?,?,?,?,?,?", "Data", 7, new String[]{
                TNoRw.getText(), Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                WaktuKala3.getText(), KondisiPlasenta.getText(), PerdarahanKala3.getText(), CatatanKala3.getText()
            }) == true) {
                tabModeKala3.addRow(new String[]{
                    TNoRw.getText(), Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                    WaktuKala3.getText(), KondisiPlasenta.getText(), PerdarahanKala3.getText(), CatatanKala3.getText()
                });
                emptTeks();
                LCount.setText("" + tabModeKala3.getRowCount());
            }
        }
    }

    private void simpanNeonatal() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else if (BeratBayi.getText().trim().equals("")) {
            Valid.textKosong(BeratBayi, "Berat Bayi");
        } else {
            if (Sequel.menyimpantf("partograf_neonatal", "?,?,?,?,?,?,?,?,?,?,?", "Data", 11, new String[]{
                TNoRw.getText(), Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                BeratBayi.getText(), PanjangBayi.getText(), ApgarScore1.getText(), ApgarScore5.getText(),
                JenisKelamin.getSelectedItem().toString(), CaraPersalinan.getSelectedItem().toString(),
                KondisiBayi.getSelectedItem().toString(), CatatanNeonatal.getText()
            }) == true) {
                tabModeNeonatal.addRow(new String[]{
                    TNoRw.getText(), Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                    cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                    BeratBayi.getText(), PanjangBayi.getText(), ApgarScore1.getText(), ApgarScore5.getText(),
                    JenisKelamin.getSelectedItem().toString(), CaraPersalinan.getSelectedItem().toString(),
                    KondisiBayi.getSelectedItem().toString(), CatatanNeonatal.getText()
                });
                emptTeks();
                LCount.setText("" + tabModeNeonatal.getRowCount());
            }
        }
    }

    private void hapusIdentitas() {
        if (akses.getkode().equals("Admin Utama")) {
            if (Sequel.queryu2tf("delete from partograf_identitas where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
                tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 0).toString(),
                tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 1).toString(),
                tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 2).toString()
            }) == true) {
                tabModeIdentitas.removeRow(tbIdentitas.getSelectedRow());
                LCount.setText("" + tabModeIdentitas.getRowCount());
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
            }
        } else {
            if (NIPPetugas.getText().equals(tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 3).toString())) {
                if (Sequel.queryu2tf("delete from partograf_identitas where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
                    tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 0).toString(),
                    tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 1).toString(),
                    tbIdentitas.getValueAt(tbIdentitas.getSelectedRow(), 2).toString()
                }) == true) {
                    tabModeIdentitas.removeRow(tbIdentitas.getSelectedRow());
                    LCount.setText("" + tabModeIdentitas.getRowCount());
                    emptTeks();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
            }
        }
    }

    private void hapusKala12() {
        if (tbKala12.getSelectedRow() != -1) {
            // Konfirmasi hapus
            int jawab = JOptionPane.showConfirmDialog(null,
                    "Yakin data akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (jawab == JOptionPane.YES_OPTION) {
                try {
                    // Ambil primary key dari tabel yang dipilih - SESUAI KOLOM YANG BENAR
                    String noRawat = tbKala12.getValueAt(tbKala12.getSelectedRow(), 0).toString(); // No.Rawat
                    String tanggal = tbKala12.getValueAt(tbKala12.getSelectedRow(), 3).toString(); // Tanggal (kolom 3)
                    String jam = tbKala12.getValueAt(tbKala12.getSelectedRow(), 4).toString();     // Jam (kolom 4)

                    // Cek permission - Admin atau petugas yang sama
                    boolean canDelete = false;
                    String nipPetugas = tbKala12.getValueAt(tbKala12.getSelectedRow(), 21).toString(); // NIP Petugas (kolom 21)

                    if (akses.getkode().equals("Admin Utama")) {
                        canDelete = true;
                    } else if (NIPPetugas.getText().equals(nipPetugas)) {
                        canDelete = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan atau Admin..!!");
                        return;
                    }

                    if (canDelete) {
                        // SQL delete berdasarkan composite primary key
                        if (Sequel.queryu2tf("DELETE FROM partograf_kala12 WHERE no_rawat=? AND tgl_perawatan=? AND jam_rawat=?",
                                3, new String[]{noRawat, tanggal, jam}) == true) {

                            // Hapus dari tabel tampil
                            tabModeKala12.removeRow(tbKala12.getSelectedRow());

                            // Clear form
                            emptTeks();

                            // Update counter
                            LCount.setText("" + tabModeKala12.getRowCount());

                            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");

                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error hapus: " + e);
                    JOptionPane.showMessageDialog(null, "Terjadi error: " + e.getMessage());
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus terlebih dahulu!");
        }
    }

    private void hapusKala3() {
        if (akses.getkode().equals("Admin Utama")) {
            if (Sequel.queryu2tf("delete from partograf_kala3 where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
                tbKala3.getValueAt(tbKala3.getSelectedRow(), 0).toString(),
                tbKala3.getValueAt(tbKala3.getSelectedRow(), 1).toString(),
                tbKala3.getValueAt(tbKala3.getSelectedRow(), 2).toString()
            }) == true) {
                tabModeKala3.removeRow(tbKala3.getSelectedRow());
                LCount.setText("" + tabModeKala3.getRowCount());
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh Admin atau petugas yang bersangkutan..!!");
        }
    }

    private void hapusNeonatal() {
        if (akses.getkode().equals("Admin Utama")) {
            if (Sequel.queryu2tf("delete from partograf_neonatal where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
                tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 0).toString(),
                tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 1).toString(),
                tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 2).toString()
            }) == true) {
                tabModeNeonatal.removeRow(tbNeonatal.getSelectedRow());
                LCount.setText("" + tabModeNeonatal.getRowCount());
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh Admin atau petugas yang bersangkutan..!!");
        }
    }

    private void gantiIdentitas() {
        // Validasi apakah ada baris yang dipilih
        if (tbIdentitas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang mau diganti..!!");
            return;
        }

        // Validasi input wajib
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
            return;
        }

        try {
            int selectedRow = tbIdentitas.getSelectedRow();

            // Ambil data lama untuk primary key
            String oldNoRawat = tbIdentitas.getValueAt(selectedRow, 0).toString();
            String oldTanggal = tbIdentitas.getValueAt(selectedRow, 1).toString();
            String oldJam = tbIdentitas.getValueAt(selectedRow, 2).toString();

            System.out.println("=== UPDATE DATA IDENTITAS ===");
            System.out.println("Old Key: " + oldNoRawat + " | " + oldTanggal + " | " + oldJam);
            System.out.println("New Data: " + TNoRw.getText() + " | "
                    + Valid.SetTgl(TglPartograf.getSelectedItem() + "") + " | "
                    + getFormattedTime());

            // Cek permission - Admin atau petugas yang sama
            boolean canEdit = false;
            String oldNIP = tbIdentitas.getValueAt(selectedRow, 3).toString();

            if (akses.getkode().equals("Admin Utama")) {
                canEdit = true;
            } else if (NIPPetugas.getText().equals(oldNIP)) {
                canEdit = true;
            } else {
                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan atau Admin..!!");
                return;
            }

            if (canEdit) {
                // Siapkan data untuk update
                String[] newData = {
                    TNoRw.getText(), // 1. no_rawat
                    Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal  
                    getFormattedTime(), // 3. jam
                    NIPPetugas.getText(), // 4. nip
                    TPetugas.getText(), // 5. nama_petugas
                    BeratBadan.getText().trim().equals("") ? null : BeratBadan.getText(), // 6. berat_badan
                    TinggiBadan.getText().trim().equals("") ? null : TinggiBadan.getText(), // 7. tinggi_badan
                    Gravida.getText().trim().equals("") ? null : Gravida.getText(), // 8. gravida
                    Paritas.getText().trim().equals("") ? null : Paritas.getText(), // 9. para
                    Abortus.getText().trim().equals("") ? null : Abortus.getText(), // 10. abortus
                    RiwayatObstetri.getSelectedItem().toString(), // 11. riwayat_obstetri
                    RisikoTinggi.getSelectedItem().toString(), // 12. risiko_tinggi
                    CatatanIdentitas.getText(), // 13. catatan
                    oldNoRawat, // WHERE no_rawat
                    oldTanggal, // WHERE tanggal
                    oldJam // WHERE jam
                };

                // Update database menggunakan Sequel.mengedittf
                if (Sequel.mengedittf("partograf_identitas",
                        "no_rawat=? and tanggal=? and jam=?",
                        "no_rawat=?,tanggal=?,jam=?,nip=?,nama_petugas=?,berat_badan=?,tinggi_badan=?,gravida=?,para=?,abortus=?,riwayat_obstetri=?,risiko_tinggi=?,catatan=?",
                        16, newData) == true) {

                    // Update berhasil - update table model
                    tbIdentitas.setValueAt(TNoRw.getText(), selectedRow, 0);
                    tbIdentitas.setValueAt(Valid.SetTgl(TglPartograf.getSelectedItem() + ""), selectedRow, 1);
                    tbIdentitas.setValueAt(getFormattedTime(), selectedRow, 2);
                    tbIdentitas.setValueAt(NIPPetugas.getText(), selectedRow, 3);
                    tbIdentitas.setValueAt(TPetugas.getText(), selectedRow, 4);
                    tbIdentitas.setValueAt(BeratBadan.getText(), selectedRow, 5);
                    tbIdentitas.setValueAt(TinggiBadan.getText(), selectedRow, 6);
                    tbIdentitas.setValueAt(Gravida.getText(), selectedRow, 7);
                    tbIdentitas.setValueAt(Paritas.getText(), selectedRow, 8);
                    tbIdentitas.setValueAt(Abortus.getText(), selectedRow, 9);
                    tbIdentitas.setValueAt(RiwayatObstetri.getSelectedItem().toString(), selectedRow, 10);
                    tbIdentitas.setValueAt(RisikoTinggi.getSelectedItem().toString(), selectedRow, 11);
                    tbIdentitas.setValueAt(CatatanIdentitas.getText(), selectedRow, 12);

                    JOptionPane.showMessageDialog(null, "Data identitas berhasil diupdate");
                    emptTeks();

                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengupdate data identitas");
                }
            }

        } catch (Exception e) {
            System.out.println("Error gantiIdentitas: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error update data: " + e.getMessage());
        }
    }

    private void gantiKala12() {
        // Validasi input wajib
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
            return;
        }

        if (NIPPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        if (DJJ.getText().trim().equals("")) {
            Valid.textKosong(DJJ, "DJJ");
            return;
        }

        if (tbKala12.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diganti!");
            return;
        }

        try {
            // Ambil primary key dari row yang dipilih - SESUAI KOLOM YANG BENAR
            int selectedRow = tbKala12.getSelectedRow();
            String oldNoRawat = tbKala12.getValueAt(selectedRow, 0).toString(); // No.Rawat (kolom 0)
            String oldTanggal = tbKala12.getValueAt(selectedRow, 3).toString(); // Tanggal (kolom 3)
            String oldJam = tbKala12.getValueAt(selectedRow, 4).toString();     // Jam (kolom 4)

            // Cek permission - Admin atau petugas yang sama
            boolean canEdit = false;
            String oldNIP = tbKala12.getValueAt(selectedRow, 21).toString();    // NIP Petugas (kolom 21)

            if (akses.getkode().equals("Admin Utama")) {
                canEdit = true;
            } else if (NIPPetugas.getText().equals(oldNIP)) {
                canEdit = true;
            } else {
                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan atau Admin..!!");
                return;
            }

            if (canEdit) {
                String jamString = getJamString();

                // SQL Update dengan semua 20 field (19 SET + 3 WHERE)
                String sql = "UPDATE partograf_kala12 SET "
                        + "no_rawat=?, tgl_perawatan=?, jam_rawat=?, pembukaan_serviks=?, "
                        + "penurunan_kepala=?, djj=?, air_ketuban=?, moulage=?, "
                        + "kontraksi_freq=?, kontraksi_durasi=?, tensi=?, nadi=?, suhu=?, "
                        + "protein_urin=?, volume_urin=?, oksitosin=?, obat_lain=?, cairan_iv=?, "
                        + "catatan_kala12=?, nip=? "
                        + "WHERE no_rawat=? AND tgl_perawatan=? AND jam_rawat=?";

                if (Sequel.queryu2tf(sql, 23, new String[]{
                    // 20 SET values
                    TNoRw.getText(), // 1
                    Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2
                    jamString, // 3
                    PembukaanServiks.getText(), // 4
                    getPenurunanValue(), // 5
                    DJJ.getText(), // 6
                    getComboValue(AirKetuban), // 7
                    getComboValue(Moulage), // 8
                    KontraksiFreq.getText(), // 9
                    KontraksiDurasi.getText(), // 10
                    TD.getText(), // 11
                    Nadi.getText(), // 12
                    Suhu.getText(), // 13
                    ProteinUrin.getText(), // 14
                    VolumeUrin.getText(), // 15
                    Oksitosin.getText(), // 16
                    Obat.getText(), // 17 - Field yang baru ditambahkan
                    Cairan.getText(), // 18 - Field yang baru ditambahkan
                    CatatanKala12.getText(), // 19
                    NIPPetugas.getText(), // 20
                    // 3 WHERE values
                    oldNoRawat, // 21
                    oldTanggal, // 22
                    oldJam // 23
                }) == true) {

                    // Update tampilan tabel - SESUAI URUTAN KOLOM YANG BENAR
                    updateTableRowKala12(selectedRow);
                    emptTeks();
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");

                } else {
                    JOptionPane.showMessageDialog(null, "Gagal mengupdate data!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error update: " + e);
            JOptionPane.showMessageDialog(null, "Error update: " + e.getMessage());
        }
    }

// 3. HELPER METHOD - Update row di tabel tampil
    private void updateTableRow(int row) {
        String jamString = getJamString();

        tabModeKala12.setValueAt(TNoRw.getText(), row, 0);
        tabModeKala12.setValueAt(TPasien.getText(), row, 1);
        tabModeKala12.setValueAt(TNoRM.getText(), row, 2);
        tabModeKala12.setValueAt(Valid.SetTgl(TglPartograf.getSelectedItem() + ""), row, 3);
        tabModeKala12.setValueAt(jamString, row, 4);
        tabModeKala12.setValueAt(PembukaanServiks.getText() + " cm", row, 5);
        tabModeKala12.setValueAt(getPenurunanDescription(), row, 6);
        tabModeKala12.setValueAt(DJJ.getText() + " bpm", row, 7);
        tabModeKala12.setValueAt(getComboValue(AirKetuban), row, 8);
        tabModeKala12.setValueAt(getComboValue(Moulage), row, 9);
        tabModeKala12.setValueAt(KontraksiFreq.getText() + "/10m", row, 10);
        tabModeKala12.setValueAt(KontraksiDurasi.getText() + " dtk", row, 11);
        tabModeKala12.setValueAt(TD.getText(), row, 12);
        tabModeKala12.setValueAt(Nadi.getText() + " x/m", row, 13);
        tabModeKala12.setValueAt(Suhu.getText() + " °C", row, 14);
        tabModeKala12.setValueAt(ProteinUrin.getText(), row, 15);
        tabModeKala12.setValueAt(VolumeUrin.getText() + " ml", row, 16);
        tabModeKala12.setValueAt(Oksitosin.getText(), row, 17);
        tabModeKala12.setValueAt(Obat.getText(), row, 18);
        tabModeKala12.setValueAt(Cairan.getText(), row, 19);
        tabModeKala12.setValueAt(CatatanKala12.getText(), row, 20);
        tabModeKala12.setValueAt(NIPPetugas.getText(), row, 21);
        tabModeKala12.setValueAt(TPetugas.getText(), row, 22);
    }

    private void BtnGrafikActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih pasien terlebih dahulu!");
            return;
        }

        // Validasi apakah ada data partograf untuk pasien ini
        try {
            String sql = "SELECT COUNT(*) FROM partograf_kala12 WHERE no_rawat = ?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, TNoRw.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(null,
                        "Belum ada data partograf untuk pasien ini!\nSimpan data observasi terlebih dahulu.");
                return;
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error check data: " + e);
        }

        // Buka form grafik dengan parameter
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            GrafikPartografWHO grafikForm = new GrafikPartografWHO(
                    null, // parent frame
                    true, // modal
                    TNoRw.getText(), // no rawat
                    TPasien.getText(), // nama pasien  
                    calculateAge(), // umur pasien
                    TPetugas.getText() // petugas penolong
            );

            // SET FULL SCREEN
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // SET FULL SCREEN untuk JDialog
            grafikForm.setSize(screenSize);
            grafikForm.setLocation(0, 0);
            grafikForm.setResizable(true);

            // Tampilkan form grafik
            grafikForm.setVisible(true);

        } catch (Exception e) {
            System.out.println("Error open grafik: " + e);
            JOptionPane.showMessageDialog(null, "Error membuka grafik: " + e.getMessage());
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private String calculateAge() {
        if (TNoRM.getText().trim().equals("")) {
            return "-";
        }

        try {
            String sql = "SELECT TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as umur, "
                    + "TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) % 12 as bulan "
                    + "FROM pasien WHERE no_rkm_medis = ?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, TNoRM.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int tahun = rs.getInt("umur");
                int bulan = rs.getInt("bulan");

                if (tahun > 0) {
                    return tahun + " tahun " + bulan + " bulan";
                } else {
                    return bulan + " bulan";
                }
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error calculate age: " + e);
        }

        return "-";
    }

    private void gantiKala3() {
        if (akses.getkode().equals("Admin Utama")) {
            if (Sequel.mengedittf("partograf_kala3", "no_rawat=? and tanggal=? and jam=?",
                    "no_rawat=?,tanggal=?,jam=?,waktu_kala3=?,kondisi_plasenta=?,perdarahan=?,catatan=?", 10, new String[]{
                        TNoRw.getText(), Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        WaktuKala3.getText(), KondisiPlasenta.getText(), PerdarahanKala3.getText(), CatatanKala3.getText(),
                        tbKala3.getValueAt(tbKala3.getSelectedRow(), 0).toString(),
                        tbKala3.getValueAt(tbKala3.getSelectedRow(), 1).toString(),
                        tbKala3.getValueAt(tbKala3.getSelectedRow(), 2).toString()
                    }) == true) {
                tbKala3.setValueAt(TNoRw.getText(), tbKala3.getSelectedRow(), 0);
                tbKala3.setValueAt(Valid.SetTgl(TglPartograf.getSelectedItem() + ""), tbKala3.getSelectedRow(), 1);
                tbKala3.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbKala3.getSelectedRow(), 2);
                tbKala3.setValueAt(WaktuKala3.getText(), tbKala3.getSelectedRow(), 3);
                tbKala3.setValueAt(KondisiPlasenta.getText(), tbKala3.getSelectedRow(), 4);
                tbKala3.setValueAt(PerdarahanKala3.getText(), tbKala3.getSelectedRow(), 5);
                tbKala3.setValueAt(CatatanKala3.getText(), tbKala3.getSelectedRow(), 6);
                emptTeks();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh Admin atau petugas yang bersangkutan..!!");
        }
    }

    private void gantiNeonatal() {
        if (akses.getkode().equals("Admin Utama")) {
            if (Sequel.mengedittf("partograf_neonatal", "no_rawat=? and tanggal=? and jam=?",
                    "no_rawat=?,tanggal=?,jam=?,berat_bayi=?,panjang_bayi=?,apgar1=?,apgar5=?,jenis_kelamin=?,cara_persalinan=?,kondisi_bayi=?,catatan=?", 14, new String[]{
                        TNoRw.getText(), Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                        cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(),
                        BeratBayi.getText(), PanjangBayi.getText(), ApgarScore1.getText(), ApgarScore5.getText(),
                        JenisKelamin.getSelectedItem().toString(), CaraPersalinan.getSelectedItem().toString(),
                        KondisiBayi.getSelectedItem().toString(), CatatanNeonatal.getText(),
                        tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 0).toString(),
                        tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 1).toString(),
                        tbNeonatal.getValueAt(tbNeonatal.getSelectedRow(), 2).toString()
                    }) == true) {
                tbNeonatal.setValueAt(TNoRw.getText(), tbNeonatal.getSelectedRow(), 0);
                tbNeonatal.setValueAt(Valid.SetTgl(TglPartograf.getSelectedItem() + ""), tbNeonatal.getSelectedRow(), 1);
                tbNeonatal.setValueAt(cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), tbNeonatal.getSelectedRow(), 2);
                tbNeonatal.setValueAt(BeratBayi.getText(), tbNeonatal.getSelectedRow(), 3);
                tbNeonatal.setValueAt(PanjangBayi.getText(), tbNeonatal.getSelectedRow(), 4);
                tbNeonatal.setValueAt(ApgarScore1.getText(), tbNeonatal.getSelectedRow(), 5);
                tbNeonatal.setValueAt(ApgarScore5.getText(), tbNeonatal.getSelectedRow(), 6);
                tbNeonatal.setValueAt(JenisKelamin.getSelectedItem().toString(), tbNeonatal.getSelectedRow(), 7);
                tbNeonatal.setValueAt(CaraPersalinan.getSelectedItem().toString(), tbNeonatal.getSelectedRow(), 8);
                tbNeonatal.setValueAt(KondisiBayi.getSelectedItem().toString(), tbNeonatal.getSelectedRow(), 9);
                tbNeonatal.setValueAt(CatatanNeonatal.getText(), tbNeonatal.getSelectedRow(), 10);
                emptTeks();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh Admin atau petugas yang bersangkutan..!!");
        }
    }

    public void tampil() {
        int selectedTab = TabRawat.getSelectedIndex();
        switch (selectedTab) {
            case 0:
                tampilIdentitas();
                break;
            case 1:
                tampilKala12();
                break;
            case 2:
                // Catatan Persalinan - form only
                break;
            case 3:
                tampilDataCatatanPersalinan();
                break;
            case 4:
                tampilObservasiKalaIV();
                break;
            case 5: // Masalah Kala IV - UBAH MENJADI TAMPIL TABEL
                tampilEvaluasiPostpartum();
                break;
            case 6:
                tampilRujukan(); // TAMBAHKAN INI
                break;

            default:
                System.out.println("Unknown tab index: " + selectedTab);
        }
    }

    private void tampilIdentitas() {
        Valid.tabelKosong(tabModeIdentitas);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select partograf_identitas.no_rawat,partograf_identitas.tanggal,partograf_identitas.jam,"
                        + "partograf_identitas.nip,partograf_identitas.nama_petugas,partograf_identitas.berat_badan,"
                        + "partograf_identitas.tinggi_badan,partograf_identitas.gravida,partograf_identitas.para,"
                        + "partograf_identitas.abortus,partograf_identitas.riwayat_obstetri,partograf_identitas.risiko_tinggi,"
                        + "partograf_identitas.catatan from partograf_identitas where "
                        + "partograf_identitas.tanggal between ? and ? order by partograf_identitas.tanggal,partograf_identitas.jam");
            } else {
                ps = koneksi.prepareStatement(
                        "select partograf_identitas.no_rawat,partograf_identitas.tanggal,partograf_identitas.jam,"
                        + "partograf_identitas.nip,partograf_identitas.nama_petugas,partograf_identitas.berat_badan,"
                        + "partograf_identitas.tinggi_badan,partograf_identitas.gravida,partograf_identitas.para,"
                        + "partograf_identitas.abortus,partograf_identitas.riwayat_obstetri,partograf_identitas.risiko_tinggi,"
                        + "partograf_identitas.catatan from partograf_identitas where "
                        + "partograf_identitas.tanggal between ? and ? and (partograf_identitas.no_rawat like ? or "
                        + "partograf_identitas.nip like ? or partograf_identitas.nama_petugas like ?) "
                        + "order by partograf_identitas.tanggal,partograf_identitas.jam");
            }

            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeIdentitas.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("tanggal"), rs.getString("jam"),
                        rs.getString("nip"), rs.getString("nama_petugas"), rs.getString("berat_badan"),
                        rs.getString("tinggi_badan"), rs.getString("gravida"), rs.getString("para"),
                        rs.getString("abortus"), rs.getString("riwayat_obstetri"), rs.getString("risiko_tinggi"),
                        rs.getString("catatan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeIdentitas.getRowCount());
    }

    private void tampilKala12() {
        Valid.tabelKosong(tabModeKala12);
        try {
            String sql;
            PreparedStatement ps;

            // Build SQL query based on search criteria
            if (TCari.getText().trim().equals("")) {
                sql = "SELECT pk.no_rawat, p.no_rkm_medis, p.nm_pasien, "
                        + "pk.tgl_perawatan, pk.jam_rawat, pk.djj, pk.air_ketuban, pk.moulage, "
                        + "pk.pembukaan_serviks, pk.penurunan_kepala, pk.kontraksi_freq, pk.kontraksi_durasi, "
                        + "pk.tensi, pk.nadi, pk.suhu, pk.protein_urin, pk.volume_urin, "
                        + "pk.oksitosin, pk.obat_lain, pk.cairan_iv, pk.catatan_kala12, pk.nip, pet.nama "
                        + "FROM partograf_kala12 pk "
                        + "INNER JOIN reg_periksa rp ON pk.no_rawat = rp.no_rawat "
                        + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                        + "INNER JOIN petugas pet ON pk.nip = pet.nip "
                        + "WHERE pk.tgl_perawatan BETWEEN ? AND ? "
                        + "ORDER BY pk.tgl_perawatan DESC, pk.jam_rawat DESC";

                ps = koneksi.prepareStatement(sql);
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

            } else {
                sql = "SELECT pk.no_rawat, p.no_rkm_medis, p.nm_pasien, "
                        + "pk.tgl_perawatan, pk.jam_rawat, pk.djj, pk.air_ketuban, pk.moulage, "
                        + "pk.pembukaan_serviks, pk.penurunan_kepala, pk.kontraksi_freq, pk.kontraksi_durasi, "
                        + "pk.tensi, pk.nadi, pk.suhu, pk.protein_urin, pk.volume_urin, "
                        + "pk.oksitosin, pk.obat_lain, pk.cairan_iv, pk.catatan_kala12, pk.nip, pet.nama "
                        + "FROM partograf_kala12 pk "
                        + "INNER JOIN reg_periksa rp ON pk.no_rawat = rp.no_rawat "
                        + "INNER JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis "
                        + "INNER JOIN petugas pet ON pk.nip = pet.nip "
                        + "WHERE pk.tgl_perawatan BETWEEN ? AND ? "
                        + "AND (p.nm_pasien LIKE ? OR p.no_rkm_medis LIKE ? OR pk.no_rawat LIKE ?) "
                        + "ORDER BY pk.tgl_perawatan DESC, pk.jam_rawat DESC";

                ps = koneksi.prepareStatement(sql);
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText() + "%");
                ps.setString(4, "%" + TCari.getText() + "%");
                ps.setString(5, "%" + TCari.getText() + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Format data dengan proper null handling dan formatting
                tabModeKala12.addRow(new String[]{
                    // Basic Info (0-4)
                    rs.getString("no_rawat") != null ? rs.getString("no_rawat") : "", // 0 - No.Rawat
                    rs.getString("no_rkm_medis") != null ? rs.getString("no_rkm_medis") : "", // 1 - No.RM
                    rs.getString("nm_pasien") != null ? rs.getString("nm_pasien") : "", // 2 - Nama Pasien
                    rs.getString("tgl_perawatan") != null ? rs.getString("tgl_perawatan") : "", // 3 - Tanggal
                    rs.getString("jam_rawat") != null ? rs.getString("jam_rawat") : "", // 4 - Jam

                    // Monitoring Janin (5-7)
                    formatDJJ(rs.getString("djj")), // 5 - DJJ
                    rs.getString("air_ketuban") != null ? rs.getString("air_ketuban") : "-", // 6 - Air Ketuban
                    rs.getString("moulage") != null ? rs.getString("moulage") : "0", // 7 - Moulage

                    // Kemajuan Persalinan (8-9)
                    formatPembukaan(rs.getString("pembukaan_serviks")), // 8 - Pembukaan
                    formatPenurunan(rs.getString("penurunan_kepala")), // 9 - Penurunan

                    // Kontraksi (10-11)
                    formatKontraksiFreq(rs.getString("kontraksi_freq")), // 10 - Kontraksi Freq
                    formatKontraksiDurasi(rs.getString("kontraksi_durasi")), // 11 - Kontraksi Durasi

                    // Tanda Vital (12-14)
                    rs.getString("tensi") != null ? rs.getString("tensi") : "", // 12 - Tensi
                    formatNadi(rs.getString("nadi")), // 13 - Nadi
                    formatSuhu(rs.getString("suhu")), // 14 - Suhu

                    // Urin & Medikasi (15-19)
                    rs.getString("protein_urin") != null ? rs.getString("protein_urin") : "", // 15 - Protein
                    formatVolume(rs.getString("volume_urin")), // 16 - Volume
                    rs.getString("oksitosin") != null ? rs.getString("oksitosin") : "", // 17 - Oksitosin
                    rs.getString("obat_lain") != null ? rs.getString("obat_lain") : "", // 18 - Obat
                    rs.getString("cairan_iv") != null ? rs.getString("cairan_iv") : "", // 19 - Cairan

                    // Notes & Staff (20-22)
                    rs.getString("catatan_kala12") != null ? rs.getString("catatan_kala12") : "", // 20 - Catatan
                    rs.getString("nip") != null ? rs.getString("nip") : "", // 21 - NIP Petugas
                    rs.getString("nama") != null ? rs.getString("nama") : "" // 22 - Nama Petugas
                });
            }

            LCount.setText("" + tabModeKala12.getRowCount());
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("SQL Error tampil: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error tampil: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error tampil data: " + e.getMessage());
        }
    }

    private String formatPenurunan(String penurunan) {
        if (penurunan == null || penurunan.trim().equals("")) {
            return "";
        }

        // Convert database value (0-5) to descriptive text
        switch (penurunan.trim()) {
            case "0":
                return "0 - Kepala masih tinggi";
            case "1":
                return "1 - Sebagian kepala masuk PAP";
            case "2":
                return "2 - Sebagian besar kepala masuk PAP";
            case "3":
                return "3 - Kepala sudah engagement";
            case "4":
                return "4 - Kepala hampir lahir";
            case "5":
                return "5 - Kepala di perineum";
            default:
                return penurunan;
        }
    }

    private String formatPembukaan(String pembukaan) {
        if (pembukaan == null || pembukaan.trim().equals("") || pembukaan.equals("0")) {
            return "";
        }
        return pembukaan + " cm";
    }

    private String formatDJJ(String djj) {
        if (djj == null || djj.trim().equals("") || djj.equals("0")) {
            return "";
        }
        return djj + " bpm";
    }

    private String getPenurunanTextFromDB(String penurunan) {
        // Null check
        if (penurunan == null || penurunan.trim().equals("") || penurunan.equals("null")) {
            return "";
        }

        // Clean the input - remove any extra characters
        String cleanValue = penurunan.trim();

        // Handle case where value might contain extra text (e.g., "2 - Sebagian besar...")
        if (cleanValue.contains(" ")) {
            cleanValue = cleanValue.substring(0, 1);
        }

        // Convert to description based on WHO partograf standard
        switch (cleanValue) {
            case "0":
                return "0 - Kepala masih tinggi";
            case "1":
                return "1 - Sebagian kepala masuk PAP";
            case "2":
                return "2 - Sebagian besar kepala masuk PAP";
            case "3":
                return "3 - Kepala sudah engagement";
            case "4":
                return "4 - Kepala hampir lahir";
            case "5":
                return "5 - Kepala di perineum";
            default:
                // If not recognized, return original value
                System.out.println("Warning: Unrecognized penurunan value: " + penurunan);
                return penurunan;
        }
    }

    private void tampilKala3() {
        Valid.tabelKosong(tabModeKala3);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select no_rawat,tanggal,jam,waktu_kala3,kondisi_plasenta,perdarahan,catatan "
                        + "from partograf_kala3 where tanggal between ? and ? order by tanggal,jam");
            } else {
                ps = koneksi.prepareStatement(
                        "select no_rawat,tanggal,jam,waktu_kala3,kondisi_plasenta,perdarahan,catatan "
                        + "from partograf_kala3 where tanggal between ? and ? and no_rawat like ? order by tanggal,jam");
            }

            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                    ps.setString(3, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeKala3.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("tanggal"), rs.getString("jam"),
                        rs.getString("waktu_kala3"), rs.getString("kondisi_plasenta"), rs.getString("perdarahan"),
                        rs.getString("catatan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeKala3.getRowCount());
    }

    private void tampilNeonatal() {
        tampilCatatanPersalinan(); // Call the correct method
    }

    private void tampilCatatanPersalinan() {
        Valid.tabelKosong(tabModeNeonatal);
        try {
            if (TCari.getText().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select no_rawat,tanggal,jam,catatan_kala1,catatan_kala2,catatan_kala3,catatan_kala4 "
                        + "from partograf_catatan_persalinan where tanggal between ? and ? order by tanggal,jam");
            } else {
                ps = koneksi.prepareStatement(
                        "select no_rawat,tanggal,jam,catatan_kala1,catatan_kala2,catatan_kala3,catatan_kala4 "
                        + "from partograf_catatan_persalinan where tanggal between ? and ? and no_rawat like ? order by tanggal,jam");
            }

            try {
                if (TCari.getText().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                    ps.setString(3, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeNeonatal.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("tanggal"), rs.getString("jam"),
                        rs.getString("catatan_kala1"), rs.getString("catatan_kala2"), rs.getString("catatan_kala3"),
                        rs.getString("catatan_kala4")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeNeonatal.getRowCount());
    }

    // ========== MAIN METHOD ==========
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPartografWHO dialog = new RMPartografWHO(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // ========== PUBLIC METHODS ==========
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isPsien();
        tampil();
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getcatatan_observasi_ranap_kebidanan());
        BtnHapus.setEnabled(akses.getcatatan_observasi_ranap_kebidanan());
        BtnEdit.setEnabled(akses.getcatatan_observasi_ranap_kebidanan());
        BtnPrint.setEnabled(akses.getcatatan_observasi_ranap_kebidanan());
        ChkKejadian.setEnabled(true);
        if (akses.getjml2() >= 1) {
            NIPPetugas.setEditable(false);
            BtnSeekPetugas.setEnabled(false);
            NIPPetugas.setText(akses.getkode());
            TPetugas.setText(petugas.tampil3(NIPPetugas.getText()));
            if (TPetugas.getText().equals("")) {
                NIPPetugas.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }

        if (TANGGALMUNDUR.equals("no")) {
            if (!akses.getkode().equals("Admin Utama")) {
                TglPartograf.setEditable(false);
                TglPartograf.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
        }
    }

    private void ChkKejadianActionPerformed(java.awt.event.ActionEvent evt) {
        if (ChkKejadian.isSelected()) {
            // OTOMATIS AKTIF - jam progres real time
            Jam.setEnabled(false);      // Disable manual selection
            Menit.setEnabled(false);
            Detik.setEnabled(false);

            // Set ke waktu sekarang
            setJamSekarangStatis();

            // START timer untuk progres otomatis
            if (timer != null) {
                timer.start();
            }

            System.out.println("Mode otomatis diaktifkan - timer dimulai");

        } else {
            // MANUAL MODE - user bisa pilih sendiri
            Jam.setEnabled(true);       // Enable manual selection
            Menit.setEnabled(true);
            Detik.setEnabled(true);

            // STOP timer
            if (timer != null) {
                timer.stop();
            }

            System.out.println("Mode manual diaktifkan - timer dihentikan");
        }
    }

    // Method yang dipanggil saat user pindah ke tab baru untuk input data
    private void setJamUntukInputBaru() {
        // Set tanggal dan jam ke sekarang untuk input baru
        TglPartograf.setDate(new Date());
        setJamSekarang();
        ChkKejadian.setSelected(true);

        System.out.println("Input baru - jam diset ke waktu sekarang");
    }

    // ========== KEY PRESSED HANDLERS TAMBAHAN ==========
    private void WaktuKala3KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            KondisiPlasenta.requestFocus();
        }
    }

    private void KondisiPlasentaKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BeratPlasenta.requestFocus();
        }
    }

    private void BeratPlasentaKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            PerdarahanKala3.requestFocus();
        }
    }

    private void PerdarahanKala3KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JenisPerdarahan.requestFocus();
        }
    }

    private void CatatanKala3KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            BtnSimpan.requestFocus();
        }
    }

// Neonatal key handlers
    private void BeratBayiKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            PanjangBayi.requestFocus();
        }
    }

    private void PanjangBayiKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LingkarKepala.requestFocus();
        }
    }

    private void LingkarKepalaKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LingkarDada.requestFocus();
        }
    }

    private void LingkarDadaKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ApgarScore1.requestFocus();
        }
    }

    private void ApgarScore1KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ApgarScore5.requestFocus();
        }
    }

    private void ApgarScore5KeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            JenisKelamin.requestFocus();
        }
    }

    private void CatatanNeonatalKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            BtnSimpan.requestFocus();
        }
    }

    private void getDataHapusKala12() {
        if (tbKala12.getSelectedRow() != -1) {
            try {
                int row = tbKala12.getSelectedRow();

                // Load data sesuai urutan kolom yang BENAR
                TNoRw.setText(tbKala12.getValueAt(row, 0).toString());          // No.Rawat
                TNoRM.setText(tbKala12.getValueAt(row, 1).toString());          // No.RM
                TPasien.setText(tbKala12.getValueAt(row, 2).toString());        // Nama Pasien

                // Parse tanggal & jam
                Valid.SetTgl2(TglPartograf, tbKala12.getValueAt(row, 3).toString());
                parseJamToCombo(tbKala12.getValueAt(row, 4).toString());

                // Load data observasi sesuai urutan baru
                DJJ.setText(extractNumber(tbKala12.getValueAt(row, 5).toString()));         // DJJ
                setComboSelection(AirKetuban, tbKala12.getValueAt(row, 6).toString());     // Air Ketuban
                setComboSelection(Moulage, tbKala12.getValueAt(row, 7).toString());       // Moulage
                PembukaanServiks.setText(extractNumber(tbKala12.getValueAt(row, 8).toString())); // Pembukaan
                setPenurunanFromText(tbKala12.getValueAt(row, 9).toString());             // Penurunan
                KontraksiFreq.setText(extractNumber(tbKala12.getValueAt(row, 10).toString())); // Kontraksi Freq
                KontraksiDurasi.setText(extractNumber(tbKala12.getValueAt(row, 11).toString())); // Kontraksi Durasi
                TD.setText(tbKala12.getValueAt(row, 12).toString());                      // Tensi
                Nadi.setText(extractNumber(tbKala12.getValueAt(row, 13).toString()));    // Nadi
                Suhu.setText(extractNumber(tbKala12.getValueAt(row, 14).toString()));    // Suhu
                ProteinUrin.setText(tbKala12.getValueAt(row, 15).toString());            // Protein
                VolumeUrin.setText(extractNumber(tbKala12.getValueAt(row, 16).toString())); // Volume
                Oksitosin.setText(tbKala12.getValueAt(row, 17).toString());              // Oksitosin
                CatatanKala12.setText(tbKala12.getValueAt(row, 18).toString());          // Catatan
                NIPPetugas.setText(tbKala12.getValueAt(row, 19).toString());             // Kode Petugas
                TPetugas.setText(tbKala12.getValueAt(row, 20).toString());               // Nama Petugas

            } catch (Exception e) {
                System.out.println("Error load data: " + e);
                JOptionPane.showMessageDialog(null, "Error load data: " + e.getMessage());
            }
        }
    }

    // METHOD EXTRACT NUMBER yang hilang
    private String extractNumber(String text) {
        if (text == null || text.trim().equals("") || text.equals("null")) {
            return "";
        }

        // Clean the input
        String cleanText = text.trim();

        // Extract number dari text seperti "120 bpm", "5 cm", "80 x/m"
        // Split by space dan ambil bagian pertama
        String[] parts = cleanText.split(" ");
        if (parts.length > 0) {
            // Remove semua karakter non-numeric kecuali titik, koma, dan slash untuk decimal/fraksi
            String numberPart = parts[0].replaceAll("[^0-9.,/]", "");
            return numberPart;
        }

        return cleanText;
    }

    private void updateTableRowKala12(int row) {
        String jamString = getJamString();

        // Update sesuai urutan kolom yang benar (23 kolom)
        tabModeKala12.setValueAt(TNoRw.getText(), row, 0);                              // No.Rawat
        tabModeKala12.setValueAt(TNoRM.getText(), row, 1);                              // No.RM
        tabModeKala12.setValueAt(TPasien.getText(), row, 2);                            // Nama Pasien
        tabModeKala12.setValueAt(Valid.SetTgl(TglPartograf.getSelectedItem() + ""), row, 3); // Tanggal
        tabModeKala12.setValueAt(jamString, row, 4);                                   // Jam
        tabModeKala12.setValueAt(DJJ.getText() + " bpm", row, 5);                      // DJJ
        tabModeKala12.setValueAt(getComboValue(AirKetuban), row, 6);                   // Air Ketuban
        tabModeKala12.setValueAt(getComboValue(Moulage), row, 7);                      // Moulage
        tabModeKala12.setValueAt(PembukaanServiks.getText() + " cm", row, 8);          // Pembukaan
        tabModeKala12.setValueAt(getPenurunanDescription(), row, 9);                   // Penurunan
        tabModeKala12.setValueAt(KontraksiFreq.getText() + "/10m", row, 10);           // Kontraksi Freq
        tabModeKala12.setValueAt(KontraksiDurasi.getText() + " dtk", row, 11);         // Kontraksi Durasi
        tabModeKala12.setValueAt(TD.getText(), row, 12);                               // Tensi
        tabModeKala12.setValueAt(Nadi.getText() + " x/m", row, 13);                    // Nadi
        tabModeKala12.setValueAt(Suhu.getText() + " °C", row, 14);                     // Suhu
        tabModeKala12.setValueAt(ProteinUrin.getText(), row, 15);                      // Protein
        tabModeKala12.setValueAt(VolumeUrin.getText() + " ml", row, 16);               // Volume
        tabModeKala12.setValueAt(Oksitosin.getText(), row, 17);                        // Oksitosin
        tabModeKala12.setValueAt(Obat.getText(), row, 18);                             // Obat - FIELD BARU
        tabModeKala12.setValueAt(Cairan.getText(), row, 19);                           // Cairan - FIELD BARU
        tabModeKala12.setValueAt(CatatanKala12.getText(), row, 20);                    // Catatan
        tabModeKala12.setValueAt(NIPPetugas.getText(), row, 21);                       // NIP Petugas
        tabModeKala12.setValueAt(TPetugas.getText(), row, 22);                         // Nama Petugas
    }

    private void simpanKala12Updated() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else if (DJJ.getText().trim().equals("")) {
            Valid.textKosong(DJJ, "DJJ");
        } else {
            String jamString = getJamString();

            // SQL tetap 20 parameter sesuai database
            if (Sequel.menyimpantf("partograf_kala12",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data Partograf Kala12", 20, new String[]{
                        TNoRw.getText(),
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                        jamString,
                        PembukaanServiks.getText(),
                        getPenurunanValue(),
                        DJJ.getText(),
                        getComboValue(AirKetuban),
                        getComboValue(Moulage),
                        KontraksiFreq.getText(),
                        KontraksiDurasi.getText(),
                        TD.getText(),
                        Nadi.getText(),
                        Suhu.getText(),
                        ProteinUrin.getText(),
                        VolumeUrin.getText(),
                        Oksitosin.getText(),
                        Obat.getText(), // Masih ada field obat_lain & cairan_iv di database
                        Cairan.getText(), // tapi tidak ditampilkan di tabel
                        CatatanKala12.getText(),
                        NIPPetugas.getText()
                    }) == true) {

                // ADD ROW sesuai urutan header tabel: No.Rawat, No.RM, Nama Pasien
                tabModeKala12.addRow(new String[]{
                    TNoRw.getText(), // 0 - No.Rawat
                    TNoRM.getText(), // 1 - No.RM
                    TPasien.getText(), // 2 - Nama Pasien
                    Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 3 - Tanggal
                    jamString, // 4 - Jam
                    DJJ.getText() + " bpm", // 5 - DJJ
                    getComboValue(AirKetuban), // 6 - Air Ketuban
                    getComboValue(Moulage), // 7 - Moulage
                    PembukaanServiks.getText() + " cm", // 8 - Pembukaan
                    getPenurunanDescription(), // 9 - Penurunan
                    KontraksiFreq.getText() + "/10m", // 10 - Kontraksi Freq
                    KontraksiDurasi.getText() + " dtk", // 11 - Kontraksi Durasi
                    TD.getText(), // 12 - Tensi
                    Nadi.getText() + " x/m", // 13 - Nadi
                    Suhu.getText() + " °C", // 14 - Suhu
                    ProteinUrin.getText(), // 15 - Protein
                    VolumeUrin.getText() + " ml", // 16 - Volume
                    Oksitosin.getText(), // 17 - Oksitosin
                    Obat.getText(), // 18 - Obat
                    Cairan.getText(), // 19 - Cairan
                    CatatanKala12.getText(), // 20 - Catatan
                    NIPPetugas.getText(), // 21 - NIP Petugas
                    TPetugas.getText() // 22 - Nama Petugas
                });

                emptTeks();
                LCount.setText("" + tabModeKala12.getRowCount());
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }
        }
    }

    private void setupTableModel() {
        String[] header = new String[]{
            "No.Rawat", "Nama Pasien", "No.RM", "Tanggal", "Jam",
            "Pembukaan", "Penurunan", "DJJ", "Air Ketuban", "Moulage",
            "Kontraksi Freq", "Kontraksi Durasi", "Tensi", "Nadi", "Suhu",
            "Protein Urin", "Volume Urin", "Oksitosin", "Obat", "Cairan IV",
            "Catatan", "NIP", "Petugas"
        };

        tabModeKala12 = new javax.swing.table.DefaultTableModel(
                new Object[][]{}, header
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbKala12.setModel(tabModeKala12);

        // Set preferred column widths
        if (tbKala12.getColumnModel().getColumnCount() >= 23) {
            tbKala12.getColumnModel().getColumn(0).setPreferredWidth(100);  // No.Rawat
            tbKala12.getColumnModel().getColumn(1).setPreferredWidth(150);  // Nama
            tbKala12.getColumnModel().getColumn(2).setPreferredWidth(80);   // No.RM
            tbKala12.getColumnModel().getColumn(12).setPreferredWidth(80);  // Tensi
            tbKala12.getColumnModel().getColumn(20).setPreferredWidth(200); // Catatan
            tbKala12.getColumnModel().getColumn(22).setPreferredWidth(120); // Petugas
        }
    }

    private void setupTableModelKala12() {
        String[] header = new String[]{
            "No.Rawat", // 0
            "No.RM", // 1
            "Nama Pasien", // 2
            "Tanggal", // 3
            "Jam", // 4
            "DJJ", // 5 - Denyut Jantung Janin
            "Air Ketuban", // 6
            "Moulage", // 7
            "Pembukaan", // 8 - Pembukaan Serviks
            "Penurunan", // 9 - Penurunan Kepala
            "Kontraksi Freq", // 10
            "Kontraksi Durasi", // 11
            "Tensi", // 12
            "Nadi", // 13
            "Suhu", // 14
            "Protein", // 15
            "Volume", // 16
            "Oksitosin", // 17
            "Catatan", // 18
            "Kode Petugas", // 19
            "Nama Petugas" // 20
        };

        tabModeKala12 = new javax.swing.table.DefaultTableModel(
                new Object[][]{}, header
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbKala12.setModel(tabModeKala12);

        // Set column widths sesuai UI
        tbKala12.getColumnModel().getColumn(0).setPreferredWidth(100);  // No.Rawat
        tbKala12.getColumnModel().getColumn(1).setPreferredWidth(80);   // Nama
        tbKala12.getColumnModel().getColumn(2).setPreferredWidth(50);   // No.RM
        tbKala12.getColumnModel().getColumn(3).setPreferredWidth(80);   // Tanggal
        tbKala12.getColumnModel().getColumn(4).setPreferredWidth(60);   // Jam
        tbKala12.getColumnModel().getColumn(5).setPreferredWidth(60);   // DJJ
        tbKala12.getColumnModel().getColumn(6).setPreferredWidth(70);   // Air Ketuban
        tbKala12.getColumnModel().getColumn(7).setPreferredWidth(50);   // Moulage
        tbKala12.getColumnModel().getColumn(8).setPreferredWidth(80);   // Pembukaan
        tbKala12.getColumnModel().getColumn(9).setPreferredWidth(80);   // Penurunan
        tbKala12.getColumnModel().getColumn(10).setPreferredWidth(70);  // Kontraksi Freq
        tbKala12.getColumnModel().getColumn(11).setPreferredWidth(70);  // Kontraksi Durasi
        tbKala12.getColumnModel().getColumn(12).setPreferredWidth(60);  // Tensi
        tbKala12.getColumnModel().getColumn(13).setPreferredWidth(50);  // Nadi
        tbKala12.getColumnModel().getColumn(14).setPreferredWidth(50);  // Suhu
        tbKala12.getColumnModel().getColumn(15).setPreferredWidth(50);  // Protein
        tbKala12.getColumnModel().getColumn(16).setPreferredWidth(50);  // Volume
        tbKala12.getColumnModel().getColumn(17).setPreferredWidth(80);  // Oksitosin
        tbKala12.getColumnModel().getColumn(18).setPreferredWidth(150); // Catatan
        tbKala12.getColumnModel().getColumn(19).setPreferredWidth(60);  // NIP
        tbKala12.getColumnModel().getColumn(20).setPreferredWidth(100); // Petugas
    }

    private String formatKontraksiFreq(String freq) {
        if (freq == null || freq.trim().equals("") || freq.equals("0")) {
            return "";
        }
        return freq + "/10m";
    }

    private String formatKontraksiDurasi(String durasi) {
        if (durasi == null || durasi.trim().equals("") || durasi.equals("0")) {
            return "";
        }
        return durasi + " dtk";
    }

    private String formatNadi(String nadi) {
        if (nadi == null || nadi.trim().equals("") || nadi.equals("0")) {
            return "";
        }
        return nadi + " x/m";
    }

    private String formatSuhu(String suhu) {
        if (suhu == null || suhu.trim().equals("") || suhu.equals("0")) {
            return "";
        }
        return suhu + " °C";
    }

    private String formatVolume(String volume) {
        if (volume == null || volume.trim().equals("") || volume.equals("0")) {
            return "";
        }
        return volume + " ml";
    }

// ========== ORIGINAL formatValue METHOD (keep for compatibility) ==========
    private String formatValue(String value, String unit) {
        if (value == null || value.trim().equals("") || value.equals("null") || value.equals("0")) {
            return "";
        }
        return value + unit;
    }

    // ========== FIXED GET DATA KALA I & II METHOD ==========
    private void getDataKala12() {
        if (tbKala12.getSelectedRow() != -1) {
            try {
                int selectedRow = tbKala12.getSelectedRow();

                // Load data sesuai urutan kolom yang BENAR
                TNoRw.setText(tbKala12.getValueAt(selectedRow, 0).toString());          // No.Rawat
                TNoRM.setText(tbKala12.getValueAt(selectedRow, 1).toString());          // No.RM
                TPasien.setText(tbKala12.getValueAt(selectedRow, 2).toString());        // Nama Pasien

                // Parse tanggal & jam
                Valid.SetTgl(TglPartograf, tbKala12.getValueAt(selectedRow, 3).toString());
                parseJamToCombo(tbKala12.getValueAt(selectedRow, 4).toString());

                // Load data observasi - HAPUS FORMATTING UNITS
                DJJ.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 5).toString()));         // DJJ - remove " bpm"
                setComboSelection(AirKetuban, tbKala12.getValueAt(selectedRow, 6).toString());         // Air Ketuban
                setComboSelection(Moulage, tbKala12.getValueAt(selectedRow, 7).toString());           // Moulage
                PembukaanServiks.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 8).toString())); // Pembukaan - remove " cm"
                setPenurunanFromText(tbKala12.getValueAt(selectedRow, 9).toString());                 // Penurunan
                KontraksiFreq.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 10).toString())); // Kontraksi Freq - remove "/10m"
                KontraksiDurasi.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 11).toString())); // Kontraksi Durasi - remove " dtk"
                TD.setText(tbKala12.getValueAt(selectedRow, 12).toString());                          // Tensi (keep as is, can have format like 120/80)
                Nadi.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 13).toString()));    // Nadi - remove " x/m"
                Suhu.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 14).toString()));    // Suhu - remove " °C"
                ProteinUrin.setText(tbKala12.getValueAt(selectedRow, 15).toString());                // Protein (keep as is, can be neg/+/++/+++)
                VolumeUrin.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 16).toString())); // Volume - remove " ml"
                Oksitosin.setText(extractNumberOnly(tbKala12.getValueAt(selectedRow, 17).toString())); // Oksitosin - remove units if any
                Obat.setText(tbKala12.getValueAt(selectedRow, 18).toString());                       // Obat (keep as is)
                Cairan.setText(tbKala12.getValueAt(selectedRow, 19).toString());                     // Cairan (keep as is)
                CatatanKala12.setText(tbKala12.getValueAt(selectedRow, 20).toString());              // Catatan
                NIPPetugas.setText(tbKala12.getValueAt(selectedRow, 21).toString());                 // NIP Petugas
                TPetugas.setText(tbKala12.getValueAt(selectedRow, 22).toString());                   // Nama Petugas

            } catch (Exception e) {
                System.out.println("Error load data: " + e);
                JOptionPane.showMessageDialog(null, "Error load data: " + e.getMessage());
            }
        }
    }

// ========== IMPROVED EXTRACT NUMBER METHOD ==========
    private String extractNumberOnly(String text) {
        if (text == null || text.trim().equals("") || text.equals("null")) {
            return "";
        }

        // Clean the input
        String cleanText = text.trim();

        // Handle empty or dash values
        if (cleanText.equals("-") || cleanText.equals("")) {
            return "";
        }

        try {
            // Remove common units and formatting
            String numberOnly = cleanText
                    .replace(" bpm", "") // DJJ unit
                    .replace(" cm", "") // Pembukaan unit
                    .replace("/10m", "") // Kontraksi freq unit
                    .replace(" dtk", "") // Kontraksi durasi unit
                    .replace(" x/m", "") // Nadi unit
                    .replace(" °C", "") // Suhu unit
                    .replace(" ml", "") // Volume unit
                    .replace("unit/ml", "") // Oksitosin unit
                    .replace("unit", "") // Generic unit
                    .trim();

            // Handle special cases for penurunan (extract only the first digit)
            if (numberOnly.contains(" - ")) {
                numberOnly = numberOnly.substring(0, 1);
            }

            // Remove any remaining non-numeric characters except decimal points and slashes (for fractions like BP)
            // Keep slash for blood pressure format (120/80)
            if (!numberOnly.contains("/")) {
                numberOnly = numberOnly.replaceAll("[^0-9.,]", "");
            }

            // Return cleaned number
            return numberOnly;

        } catch (Exception e) {
            System.out.println("Error extracting number from: " + text + " - " + e.getMessage());
            return "";
        }
    }

// ========== HELPER METHOD FOR COMBO PARSING ==========
    private void parseJamToCombo(String jamString) {
        if (jamString != null && !jamString.trim().equals("")) {
            String[] parts = jamString.split(":");
            if (parts.length >= 3) {
                // Use the correct combo boxes from header (Jam, Menit, Detik)
                if (Jam != null) {
                    Jam.setSelectedItem(parts[0]);
                }
                if (Menit != null) {
                    Menit.setSelectedItem(parts[1]);
                }
                if (Detik != null) {
                    Detik.setSelectedItem(parts[2]);
                }
            }
        }
    }

// ========== HELPER METHOD FOR COMBO SELECTION ==========
    private void setComboSelection(widget.ComboBox combo, String value) {
        if (combo != null && value != null && !value.trim().equals("")) {
            // Find matching item in combo
            for (int i = 0; i < combo.getItemCount(); i++) {
                String item = combo.getItemAt(i).toString();
                if (item.equals(value)) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }
        }
        // Default to first item if not found
        if (combo != null) {
            combo.setSelectedIndex(0);
        }
    }

// ========== HELPER METHOD FOR PENURUNAN PARSING ==========
    private void setPenurunanFromText(String text) {
        if (text != null && text.length() > 0) {
            try {
                // Extract the first character which should be the number (0-5)
                char firstChar = text.charAt(0);
                if (Character.isDigit(firstChar)) {
                    int index = Character.getNumericValue(firstChar);
                    if (index >= 0 && index <= 5 && PenurunanKepala != null) {
                        PenurunanKepala.setSelectedIndex(index);
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error parsing penurunan: " + text);
            }
        }

        // Default to 0 if parsing fails
        if (PenurunanKepala != null) {
            PenurunanKepala.setSelectedIndex(0);
        }
    }

// ========== ADDITIONAL HELPER - GET JAM STRING ==========
    private String getJamString() {
        try {
            // Use the header combo boxes (Jam, Menit, Detik)
            if (Jam != null && Menit != null && Detik != null) {
                Object jamObj = Jam.getSelectedItem();
                Object menitObj = Menit.getSelectedItem();
                Object detikObj = Detik.getSelectedItem();

                if (jamObj != null && menitObj != null && detikObj != null) {
                    return jamObj.toString() + ":" + menitObj.toString() + ":" + detikObj.toString();
                }
            }

            // Fallback to current time if combo boxes are null
            java.util.Calendar cal = java.util.Calendar.getInstance();
            return String.format("%02d:%02d:%02d",
                    cal.get(java.util.Calendar.HOUR_OF_DAY),
                    cal.get(java.util.Calendar.MINUTE),
                    cal.get(java.util.Calendar.SECOND));

        } catch (Exception e) {
            System.out.println("Error getting jam string: " + e.getMessage());
            // Return current time as fallback
            java.util.Calendar cal = java.util.Calendar.getInstance();
            return String.format("%02d:%02d:%02d",
                    cal.get(java.util.Calendar.HOUR_OF_DAY),
                    cal.get(java.util.Calendar.MINUTE),
                    cal.get(java.util.Calendar.SECOND));
        }
    }

    private void simpanCatatanPersalinan() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            // PERBAIKAN: Sesuaikan dengan 52 kolom dalam database
            if (Sequel.menyimpantf("partograf_catatan_persalinan",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "Data Catatan Persalinan", 53, new String[]{
                        // Primary keys (4 kolom)
                        TNoRw.getText(), // 1. no_rawat
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal
                        getFormattedTime(), // 3. jam
                        NIPPetugas.getText(), // 4. nip

                        // KALA I (4 kolom)
                        getComboValueSafe(GarisWaspada), // 5. garis_waspada
                        getTextValueSafe(MasalahKala1, ""), // 6. masalah_kala1
                        getTextValueSafe(PenatalaksanaanKala1, ""), // 7. penatalaksanaan_kala1
                        getTextValueSafe(HasilKala1, ""), // 8. hasil_kala1

                        // KALA II (9 kolom)
                        getComboValueSafe(Episiotomi), // 9. episiotomi
                        getComboValueSafe(Pendamping), // 10. pendamping
                        getComboValueSafe(GawatJanin), // 11. gawat_janin
                        getTextValueSafe(TindakanGawatJanin, ""), // 12. tindakan_gawat_janin
                        getComboValueSafe(DistosiaBahu), // 13. distosia_bahu
                        getTextValueSafe(TindakanDistosia, ""), // 14. tindakan_distosia
                        getTextValueSafe(MasalahKala2, ""), // 15. masalah_kala2
                        getTextValueSafe(PenatalaksanaanKala2, ""), // 16. penatalaksanaan_kala2
                        getTextValueSafe(HasilKala2, ""), // 17. hasil_kala2

                        // KALA III (23 kolom)
                        getTextValueSafe(LamaKala3, "0"), // 18. lama_kala3
                        getComboValueSafe(Oksitosin100), // 19. oksitosin_100
                        getTextValueSafe(WaktuOksitosin100, "0"), // 20. waktu_oksitosin_100
                        getTextValueSafe(AlasanTidakOksitosin100, ""), // 21. alasan_tidak_oksitosin_100
                        getComboValueSafe(OksitosinLain), // 22. oksitosin_lain
                        getTextValueSafe(AlasanTidakOksitosinLain, ""), // 23. alasan_tidak_oksitosin_lain
                        getComboValueSafe(PenegangTali), // 24. penegang_tali
                        getTextValueSafe(AlasanTidakPenegang, ""), // 25. alasan_tidak_penegang
                        getComboValueSafe(MasaseFundus), // 26. masase_fundus
                        getTextValueSafe(AlasanTidakMasase, ""), // 27. alasan_tidak_masase
                        getComboValueSafe(PlasentaLengkap), // 28. plasenta_lengkap
                        getTextValueSafe(TindakanPlasentaTidakLengkap, ""), // 29. tindakan_plasenta_tidak_lengkap
                        getComboValueSafe(PlasentaTidakLahir), // 30. plasenta_tidak_lahir_30
                        getTextValueSafe(TindakanPlasentaTidakLahir, ""), // 31. tindakan_plasenta_tidak_lahir
                        getComboValueSafe(Laserasi), // 32. laserasi
                        getTextValueSafe(LokasiLaserasi, ""), // 33. lokasi_laserasi
                        getTextValueSafe(DerajatLaserasi, ""), // 34. derajat_laserasi
                        getComboValueSafe(AtoniaUteri), // 35. atonia_uteri
                        getTextValueSafe(TindakanAtonia, ""), // 36. tindakan_atonia
                        getTextValueSafe(JumlahPerdarahan, "0"), // 37. jumlah_perdarahan
                        getTextValueSafe(MasalahKala3, ""), // 38. masalah_kala3
                        getTextValueSafe(PenatalaksanaanKala3, ""), // 39. penatalaksanaan_kala3
                        getTextValueSafe(HasilKala3, ""), // 40. hasil_kala3

                        // BAYI BARU LAHIR (12 kolom - SESUAI DATABASE)
                        getTextValueSafe(BeratBadanBayi, "0"), // 41. berat_badan_bayi
                        getTextValueSafe(PanjangBadanBayi, "0"), // 42. panjang_badan_bayi
                        getComboValueSafe(JenisKelaminBayi), // 43. jenis_kelamin_bayi
                        getComboValueSafe(PenilaianBayi), // 44. penilaian_bayi
                        getComboValueSafe(BayiLahir), // 45. bayi_lahir
                        getTextValueSafe(TindakanBayi, ""), // 46. tindakan_bayi
                        getTextValueSafe(CacatBawaan, ""), // 47. cacat_bawaan
                        getTextValueSafe(HipotermiTindakan, ""), // 48. hipotermi_tindakan
                        getComboValueSafe(PemberianASI), // 49. pemberian_asi
                        getTextValueSafe(WaktuPemberianASI, "0"), // 50. waktu_pemberian_asi
                        getTextValueSafe(AlasanTidakASI, ""), // 51. alasan_tidak_asi
                        getTextValueSafe(MasalahLainBayi, ""), // 52. masalah_lain_bayi
                        getTextValueSafe(HasilnyaBayi, "") // 53. hasilnya_bayi - TAMBAH KOLOM INI
                    }) == true) {

                JOptionPane.showMessageDialog(null, "Data catatan persalinan berhasil disimpan!");
                emptTeksCatatanPersalinan();
                tampil();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data catatan persalinan!");
            }
        }
    }

    // ========== 2. UPDATE/GANTI CATATAN PERSALINAN ==========
    private void gantiCatatanPersalinan() {
        if (tbDataCatatanPersalinan.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diganti!");
            return;
        }

        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        try {
            int selectedRow = tbDataCatatanPersalinan.getSelectedRow();

            // Get old primary key values - PERBAIKI INDEX KOLOM
            String oldNoRawat = tbDataCatatanPersalinan.getValueAt(selectedRow, 0).toString();
            String oldTanggal = tbDataCatatanPersalinan.getValueAt(selectedRow, 1).toString();
            String oldJam = tbDataCatatanPersalinan.getValueAt(selectedRow, 2).toString();

            // Check permission
            boolean canEdit = false;
            if (akses.getkode().equals("Admin Utama")) {
                canEdit = true;
            } else {
                // Check if current user is the same as the one who created the record
                try {
                    String checkNipSql = "SELECT nip FROM partograf_catatan_persalinan WHERE no_rawat=? AND tanggal=? AND jam=?";
                    PreparedStatement psCheck = koneksi.prepareStatement(checkNipSql);
                    psCheck.setString(1, oldNoRawat);
                    psCheck.setString(2, oldTanggal);
                    psCheck.setString(3, oldJam);
                    ResultSet rsCheck = psCheck.executeQuery();
                    if (rsCheck.next()) {
                        if (NIPPetugas.getText().equals(rsCheck.getString("nip"))) {
                            canEdit = true;
                        }
                    }
                    rsCheck.close();
                    psCheck.close();
                } catch (Exception e) {
                    System.out.println("Error check permission: " + e);
                }
            }

            if (!canEdit) {
                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan atau Admin!");
                return;
            }

            // PERBAIKI: Build update SQL dengan benar - 52 SET fields
            String updateSql = "UPDATE partograf_catatan_persalinan SET "
                    + "no_rawat=?, tanggal=?, jam=?, nip=?, "
                    + "garis_waspada=?, masalah_kala1=?, penatalaksanaan_kala1=?, hasil_kala1=?, "
                    + "episiotomi=?, pendamping=?, gawat_janin=?, tindakan_gawat_janin=?, "
                    + "distosia_bahu=?, tindakan_distosia=?, masalah_kala2=?, penatalaksanaan_kala2=?, hasil_kala2=?, "
                    + "lama_kala3=?, oksitosin_100=?, waktu_oksitosin_100=?, alasan_tidak_oksitosin_100=?, "
                    + "oksitosin_lain=?, alasan_tidak_oksitosin_lain=?, penegang_tali=?, alasan_tidak_penegang=?, "
                    + "masase_fundus=?, alasan_tidak_masase=?, plasenta_lengkap=?, tindakan_plasenta_tidak_lengkap=?, "
                    + "plasenta_tidak_lahir_30=?, tindakan_plasenta_tidak_lahir=?, laserasi=?, lokasi_laserasi=?, "
                    + "derajat_laserasi=?, atonia_uteri=?, tindakan_atonia=?, jumlah_perdarahan=?, "
                    + "masalah_kala3=?, penatalaksanaan_kala3=?, hasil_kala3=?, "
                    + "berat_badan_bayi=?, panjang_badan_bayi=?, jenis_kelamin_bayi=?, penilaian_bayi=?, "
                    + "bayi_lahir=?, tindakan_bayi=?, cacat_bawaan=?, hipotermi_tindakan=?, "
                    + "pemberian_asi=?, waktu_pemberian_asi=?, alasan_tidak_asi=?, masalah_lain_bayi=?, hasilnya_bayi=? "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=?";

            // HITUNG PARAMETER: 52 SET + 3 WHERE = 55 total
            if (Sequel.queryu2tf(updateSql, 55, new String[]{
                // SET values (52 fields) - PASTIKAN URUTAN SESUAI DATABASE
                TNoRw.getText(), // 1. no_rawat
                Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal 
                getFormattedTime(), // 3. jam
                NIPPetugas.getText(), // 4. nip
                getComboValueSafe(GarisWaspada), // 5. garis_waspada
                getTextValueSafe(MasalahKala1, ""), // 6. masalah_kala1
                getTextValueSafe(PenatalaksanaanKala1, ""), // 7. penatalaksanaan_kala1
                getTextValueSafe(HasilKala1, ""), // 8. hasil_kala1
                getComboValueSafe(Episiotomi), // 9. episiotomi
                getComboValueSafe(Pendamping), // 10. pendamping
                getComboValueSafe(GawatJanin), // 11. gawat_janin
                getTextValueSafe(TindakanGawatJanin, ""), // 12. tindakan_gawat_janin
                getComboValueSafe(DistosiaBahu), // 13. distosia_bahu
                getTextValueSafe(TindakanDistosia, ""), // 14. tindakan_distosia
                getTextValueSafe(MasalahKala2, ""), // 15. masalah_kala2
                getTextValueSafe(PenatalaksanaanKala2, ""), // 16. penatalaksanaan_kala2
                getTextValueSafe(HasilKala2, ""), // 17. hasil_kala2
                getNumericValueSafe(LamaKala3, "0"), // 18. lama_kala3
                getComboValueSafe(Oksitosin100), // 19. oksitosin_100
                getNumericValueSafe(WaktuOksitosin100, "0"), // 20. waktu_oksitosin_100
                getTextValueSafe(AlasanTidakOksitosin100, ""), // 21. alasan_tidak_oksitosin_100
                getComboValueSafe(OksitosinLain), // 22. oksitosin_lain
                getTextValueSafe(AlasanTidakOksitosinLain, ""), // 23. alasan_tidak_oksitosin_lain
                getComboValueSafe(PenegangTali), // 24. penegang_tali
                getTextValueSafe(AlasanTidakPenegang, ""), // 25. alasan_tidak_penegang
                getComboValueSafe(MasaseFundus), // 26. masase_fundus
                getTextValueSafe(AlasanTidakMasase, ""), // 27. alasan_tidak_masase
                getComboValueSafe(PlasentaLengkap), // 28. plasenta_lengkap
                getTextValueSafe(TindakanPlasentaTidakLengkap, ""), // 29. tindakan_plasenta_tidak_lengkap
                getComboValueSafe(PlasentaTidakLahir), // 30. plasenta_tidak_lahir_30
                getTextValueSafe(TindakanPlasentaTidakLahir, ""), // 31. tindakan_plasenta_tidak_lahir
                getComboValueSafe(Laserasi), // 32. laserasi
                getTextValueSafe(LokasiLaserasi, ""), // 33. lokasi_laserasi
                getTextValueSafe(DerajatLaserasi, ""), // 34. derajat_laserasi
                getComboValueSafe(AtoniaUteri), // 35. atonia_uteri
                getTextValueSafe(TindakanAtonia, ""), // 36. tindakan_atonia
                getNumericValueSafe(JumlahPerdarahan, "0"), // 37. jumlah_perdarahan
                getTextValueSafe(MasalahKala3, ""), // 38. masalah_kala3
                getTextValueSafe(PenatalaksanaanKala3, ""), // 39. penatalaksanaan_kala3
                getTextValueSafe(HasilKala3, ""), // 40. hasil_kala3
                getNumericValueSafe(BeratBadanBayi, "0"), // 41. berat_badan_bayi
                getNumericValueSafe(PanjangBadanBayi, "0"), // 42. panjang_badan_bayi
                getComboValueSafe(JenisKelaminBayi), // 43. jenis_kelamin_bayi
                getComboValueSafe(PenilaianBayi), // 44. penilaian_bayi
                getComboValueSafe(BayiLahir), // 45. bayi_lahir
                getTextValueSafe(TindakanBayi, ""), // 46. tindakan_bayi
                getTextValueSafe(CacatBawaan, ""), // 47. cacat_bawaan
                getTextValueSafe(HipotermiTindakan, ""), // 48. hipotermi_tindakan
                getComboValueSafe(PemberianASI), // 49. pemberian_asi
                getNumericValueSafe(WaktuPemberianASI, "0"), // 50. waktu_pemberian_asi
                getTextValueSafe(AlasanTidakASI, ""), // 51. alasan_tidak_asi
                getTextValueSafe(MasalahLainBayi, ""), // 52. masalah_lain_bayi
                getTextValueSafe(HasilnyaBayi, ""), // 53. hasilnya_bayi
                // WHERE clause (3 fields)
                oldNoRawat, // 54. WHERE no_rawat
                oldTanggal, // 55. WHERE tanggal
                oldJam // 56. WHERE jam
            }) == true) {
                JOptionPane.showMessageDialog(null, "Data catatan persalinan berhasil diupdate!");
                tampil(); // Refresh tampilan
                emptTeksCatatanPersalinan(); // Clear form
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate data catatan persalinan!");
            }

        } catch (Exception e) {
            System.out.println("Error update catatan persalinan: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error update: " + e.getMessage());
        }
    }

    private String getNumericValueSafe(widget.TextBox textBox, String defaultValue) {
        if (textBox != null && !textBox.getText().trim().equals("") && !textBox.getText().trim().equals("0")) {
            try {
                // Validate numeric
                Integer.parseInt(textBox.getText().trim());
                return textBox.getText().trim();
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private void debugParameterCount() {
        System.out.println("=== DATABASE SCHEMA VERIFICATION ===");
        System.out.println("Expected columns in partograf_catatan_persalinan: 52");
        System.out.println("SQL Placeholders: 52");
        System.out.println("Java Parameters: Should be 52");

        // Verifikasi dengan query count kolom
        try {
            String countSql = "SELECT COUNT(*) as column_count FROM information_schema.COLUMNS "
                    + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'partograf_catatan_persalinan'";
            PreparedStatement ps = koneksi.prepareStatement(countSql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Actual database columns: " + rs.getInt("column_count"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error checking column count: " + e);
        }
    }

    // HELPER METHODS - Null-safe getters
    private String getComboValueSafe(widget.ComboBox combo) {
        if (combo != null && combo.getSelectedItem() != null) {
            return combo.getSelectedItem().toString();
        }
        return "Tidak"; // Default value untuk enum
    }

    private String getTextValueSafe(widget.TextBox textBox, String defaultValue) {
        if (textBox != null && !textBox.getText().trim().equals("")) {
            return textBox.getText().trim();
        }
        return defaultValue;
    }

    private void hapusCatatanPersalinan() {
        if (tbDataCatatanPersalinan.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin data catatan persalinan akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tbDataCatatanPersalinan.getSelectedRow();
                String noRawat = tbDataCatatanPersalinan.getValueAt(selectedRow, 0).toString();
                String tanggal = tbDataCatatanPersalinan.getValueAt(selectedRow, 1).toString();
                String jam = tbDataCatatanPersalinan.getValueAt(selectedRow, 2).toString();

                // Check permission
                boolean canDelete = false;
                if (akses.getkode().equals("Admin Utama")) {
                    canDelete = true;
                } else {
                    // Check if current user is the same as the one who created the record
                    try {
                        String checkNipSql = "SELECT nip FROM partograf_catatan_persalinan WHERE no_rawat=? AND tanggal=? AND jam=?";
                        PreparedStatement psCheck = koneksi.prepareStatement(checkNipSql);
                        psCheck.setString(1, noRawat);
                        psCheck.setString(2, tanggal);
                        psCheck.setString(3, jam);
                        ResultSet rsCheck = psCheck.executeQuery();
                        if (rsCheck.next()) {
                            if (NIPPetugas.getText().equals(rsCheck.getString("nip"))) {
                                canDelete = true;
                            }
                        }
                        rsCheck.close();
                        psCheck.close();
                    } catch (Exception e) {
                        System.out.println("Error check permission: " + e);
                    }
                }

                if (!canDelete) {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan atau Admin!");
                    return;
                }

                // Delete record
                if (Sequel.queryu2tf("DELETE FROM partograf_catatan_persalinan WHERE no_rawat=? AND tanggal=? AND jam=?",
                        3, new String[]{noRawat, tanggal, jam}) == true) {

                    tabModeDataCatatanPersalinan.removeRow(selectedRow);
                    LCount.setText("" + tabModeDataCatatanPersalinan.getRowCount());
                    emptTeksCatatanPersalinan();
                    JOptionPane.showMessageDialog(null, "Data catatan persalinan berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
                }

            } catch (Exception e) {
                System.out.println("Error delete catatan persalinan: " + e);
                JOptionPane.showMessageDialog(null, "Error delete: " + e.getMessage());
            }
        }
    }

    private void setupTabDataCatatanPersalinan() {
        internalFrame5 = new widget.InternalFrame();
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5");
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== NO FORM INPUT - JUST TABLE ==========
        // ========== TABLE DATA CATATAN PERSALINAN ==========
        Scroll4 = new widget.ScrollPane();
        Scroll4.setName("Scroll4");
        Scroll4.setOpaque(true);

        tbDataCatatanPersalinan = new widget.Table();
        tbDataCatatanPersalinan.setName("tbDataCatatanPersalinan");
        tbDataCatatanPersalinan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataCatatanPersalinanMouseClicked(evt);
            }
        });
        tbDataCatatanPersalinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataCatatanPersalinanKeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbDataCatatanPersalinan);

        internalFrame5.add(Scroll4, java.awt.BorderLayout.CENTER);

        // Add tab
        TabRawat.addTab("Data Catatan Persalinan", internalFrame5);
    }

    // ========== SETUP TAB OBSERVASI PERSALINAN KALA IV ==========
    private void setupTabObservasiKalaIV() {
        internalFrame5 = new widget.InternalFrame();
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5");
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== PANEL INPUT OBSERVASI KALA IV ==========
        PanelInput4 = new javax.swing.JPanel();
        PanelInput4.setName("PanelInput4");
        PanelInput4.setOpaque(false);
        PanelInput4.setPreferredSize(new java.awt.Dimension(192, 250));
        PanelInput4.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== FORM CONTENT SCROLL PANE ==========
        scrollPane4 = new widget.ScrollPane();
        scrollPane4.setName("scrollPane4");
        scrollPane4.setPreferredSize(new java.awt.Dimension(46, 220));

        panelGlass4 = new widget.panelisi();
        panelGlass4.setName("panelGlass4");
        panelGlass4.setPreferredSize(new java.awt.Dimension(44, 200));
        panelGlass4.setLayout(null);

        // Setup form observasi kala IV
        setupFormObservasiKalaIV();

        scrollPane4.setViewportView(panelGlass4);
        PanelInput4.add(scrollPane4, java.awt.BorderLayout.PAGE_START);

        // ========== CHECKBOX INPUT ==========
        ChkInput4 = new widget.CekBox();
        ChkInput4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput4.setMnemonic('I');
        ChkInput4.setText(".: Input Data Observasi Kala IV");
        ChkInput4.setToolTipText("Alt+I");
        ChkInput4.setBorderPainted(true);
        ChkInput4.setBorderPaintedFlat(true);
        ChkInput4.setFocusable(false);
        ChkInput4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput4.setName("ChkInput4");
        ChkInput4.setPreferredSize(new java.awt.Dimension(200, 20));
        ChkInput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput4ActionPerformed(evt);
            }
        });
        PanelInput4.add(ChkInput4, java.awt.BorderLayout.PAGE_END);

        internalFrame5.add(PanelInput4, java.awt.BorderLayout.PAGE_START);

        // ========== TABLE OBSERVASI KALA IV ==========
        Scroll5 = new widget.ScrollPane();
        Scroll5.setName("Scroll5");
        Scroll5.setOpaque(true);

        tbObservasiKalaIV = new widget.Table();
        tbObservasiKalaIV.setName("tbObservasiKalaIV");
        tbObservasiKalaIV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObservasiKalaIVMouseClicked(evt);
            }
        });
        tbObservasiKalaIV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObservasiKalaIVKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbObservasiKalaIV);

        internalFrame5.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Observasi Persalinan Kala IV", internalFrame5);
    }

    private void setupFormObservasiKalaIV() {
        // BERSIHKAN FORM LAMA
        panelGlass4.removeAll();

        // HEADER
        widget.Label lblHeaderObs = new widget.Label();
        lblHeaderObs.setText("OBSERVASI PERSALINAN KALA IV (POST PARTUM)");
        lblHeaderObs.setName("lblHeaderObs");
        lblHeaderObs.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHeaderObs.setForeground(new Color(0, 100, 0));
        panelGlass4.add(lblHeaderObs);
        lblHeaderObs.setBounds(20, 20, 400, 25);

        // BARIS 1: Jam Ke, Tekanan Darah, Nadi
        widget.Label lblJamKeObs = new widget.Label();
        lblJamKeObs.setText("Jam ke:");
        lblJamKeObs.setName("lblJamKeObs");
        panelGlass4.add(lblJamKeObs);
        lblJamKeObs.setBounds(20, 60, 50, 23);

        JamKeObsKalaIV = new widget.ComboBox();
        JamKeObsKalaIV.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "1", "2", "3", "4", "5", "6"
        }));
        JamKeObsKalaIV.setName("JamKeObsKalaIV");
        panelGlass4.add(JamKeObsKalaIV);
        JamKeObsKalaIV.setBounds(80, 60, 50, 23);

        widget.Label lblTekananDarahObs = new widget.Label();
        lblTekananDarahObs.setText("Tekanan Darah:");
        lblTekananDarahObs.setName("lblTekananDarahObs");
        panelGlass4.add(lblTekananDarahObs);
        lblTekananDarahObs.setBounds(150, 60, 90, 23);

        TekananDarahObsKalaIV = new widget.TextBox();
        TekananDarahObsKalaIV.setName("TekananDarahObsKalaIV");
        panelGlass4.add(TekananDarahObsKalaIV);
        TekananDarahObsKalaIV.setBounds(250, 60, 80, 23);

        widget.Label lblNadiObs = new widget.Label();
        lblNadiObs.setText("Nadi:");
        lblNadiObs.setName("lblNadiObs");
        panelGlass4.add(lblNadiObs);
        lblNadiObs.setBounds(350, 60, 40, 23);

        NadiObsKalaIV = new widget.TextBox();
        NadiObsKalaIV.setName("NadiObsKalaIV");
        panelGlass4.add(NadiObsKalaIV);
        NadiObsKalaIV.setBounds(400, 60, 60, 23);

        // BARIS 2: Suhu, TFU, Kontraksi
        widget.Label lblSuhuObs = new widget.Label();
        lblSuhuObs.setText("Suhu:");
        lblSuhuObs.setName("lblSuhuObs");
        panelGlass4.add(lblSuhuObs);
        lblSuhuObs.setBounds(480, 60, 40, 23);

        SuhuObsKalaIV = new widget.TextBox();
        SuhuObsKalaIV.setName("SuhuObsKalaIV");
        panelGlass4.add(SuhuObsKalaIV);
        SuhuObsKalaIV.setBounds(530, 60, 60, 23);

        widget.Label lblTFUObs = new widget.Label();
        lblTFUObs.setText("TFU (cm):");
        lblTFUObs.setName("lblTFUObs");
        panelGlass4.add(lblTFUObs);
        lblTFUObs.setBounds(20, 100, 60, 23);

        TFUObsKalaIV = new widget.TextBox();
        TFUObsKalaIV.setName("TFUObsKalaIV");
        panelGlass4.add(TFUObsKalaIV);
        TFUObsKalaIV.setBounds(90, 100, 60, 23);

        widget.Label lblKontraksiObs = new widget.Label();
        lblKontraksiObs.setText("Kontraksi Uterus:");
        lblKontraksiObs.setName("lblKontraksiObs");
        panelGlass4.add(lblKontraksiObs);
        lblKontraksiObs.setBounds(170, 100, 100, 23);

        KontraksiUterusObsKalaIV = new widget.TextBox();
        KontraksiUterusObsKalaIV.setName("KontraksiUterusObsKalaIV");
        panelGlass4.add(KontraksiUterusObsKalaIV);
        KontraksiUterusObsKalaIV.setBounds(280, 100, 80, 23);

        // BARIS 3: Kandung Kemih, Perdarahan
        widget.Label lblKandungKemihObs = new widget.Label();
        lblKandungKemihObs.setText("Kandung Kemih:");
        lblKandungKemihObs.setName("lblKandungKemihObs");
        panelGlass4.add(lblKandungKemihObs);
        lblKandungKemihObs.setBounds(380, 100, 90, 23);

        KandungKemihObsKalaIV = new widget.TextBox();
        KandungKemihObsKalaIV.setName("KandungKemihObsKalaIV");
        panelGlass4.add(KandungKemihObsKalaIV);
        KandungKemihObsKalaIV.setBounds(480, 100, 80, 23);

        widget.Label lblPerdarahanObs = new widget.Label();
        lblPerdarahanObs.setText("Perdarahan (ml):");
        lblPerdarahanObs.setName("lblPerdarahanObs");
        panelGlass4.add(lblPerdarahanObs);
        lblPerdarahanObs.setBounds(20, 140, 100, 23);

        PerdarahanObsKalaIV = new widget.TextBox();
        PerdarahanObsKalaIV.setName("PerdarahanObsKalaIV");
        panelGlass4.add(PerdarahanObsKalaIV);
        PerdarahanObsKalaIV.setBounds(130, 140, 80, 23);

        // UPDATE PANEL SIZE
        panelGlass4.setPreferredSize(new java.awt.Dimension(650, 200));
        panelGlass4.revalidate();
        panelGlass4.repaint();
    }

    // SIMPAN DATA DENGAN KOMPONEN BARU
    private void simpanObservasiKalaIVBaru() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
            return;
        }

        try {
            // Validasi duplikasi
            String checkSql = "SELECT COUNT(*) FROM partograf_observasi_kala4 "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=? AND jam_ke=?";

            PreparedStatement psCheck = koneksi.prepareStatement(checkSql);
            psCheck.setString(1, TNoRw.getText());
            psCheck.setString(2, Valid.SetTgl(TglPartograf.getSelectedItem() + ""));
            psCheck.setString(3, getFormattedTime());
            psCheck.setString(4, JamKeObsKalaIV.getSelectedItem().toString());

            ResultSet rsCheck = psCheck.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null,
                        "Data observasi untuk jam ke-" + JamKeObsKalaIV.getSelectedItem()
                        + " pada waktu ini sudah ada!");
                rsCheck.close();
                psCheck.close();
                return;
            }
            rsCheck.close();
            psCheck.close();

            // Debug nilai komponen baru
            System.out.println("=== DEBUG KOMPONEN BARU ===");
            System.out.println("JamKe: " + JamKeObsKalaIV.getSelectedItem());
            System.out.println("TekananDarah: '" + TekananDarahObsKalaIV.getText() + "'");
            System.out.println("Nadi: '" + NadiObsKalaIV.getText() + "'");
            System.out.println("Suhu: '" + SuhuObsKalaIV.getText() + "'");
            System.out.println("TFU: '" + TFUObsKalaIV.getText() + "'");
            System.out.println("Kontraksi: '" + KontraksiUterusObsKalaIV.getText() + "'");
            System.out.println("Kandung Kemih: '" + KandungKemihObsKalaIV.getText() + "'");
            System.out.println("Perdarahan: '" + PerdarahanObsKalaIV.getText() + "'");

            // Simpan dengan komponen baru
            if (Sequel.menyimpantf("partograf_observasi_kala4",
                    "?,?,?,?,?,?,?,?,?,?,?,?", "Data Partograf Kala IV", 12, new String[]{
                        TNoRw.getText(),
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                        getFormattedTime(),
                        JamKeObsKalaIV.getSelectedItem().toString(),
                        TekananDarahObsKalaIV.getText(),
                        NadiObsKalaIV.getText(),
                        SuhuObsKalaIV.getText(),
                        TFUObsKalaIV.getText(),
                        KontraksiUterusObsKalaIV.getText(),
                        KandungKemihObsKalaIV.getText(),
                        PerdarahanObsKalaIV.getText(),
                        NIPPetugas.getText()
                    }) == true) {

                JOptionPane.showMessageDialog(null, "Data observasi kala IV berhasil disimpan!");
                tampilObservasiKalaIV();
                emptTeksKalaIVBaru();

            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data!");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

// EMPTY FORM BARU
    private void emptTeksKalaIVBaru() {
        JamKeObsKalaIV.setSelectedIndex(0);
        TekananDarahObsKalaIV.setText("");
        NadiObsKalaIV.setText("");
        SuhuObsKalaIV.setText("");
        TFUObsKalaIV.setText("");
        KontraksiUterusObsKalaIV.setText("");
        KandungKemihObsKalaIV.setText("");
        PerdarahanObsKalaIV.setText("");
    }

// GET DATA BARU
    private void getDataObservasiKalaIVBaru() {
        if (tbObservasiKalaIV.getSelectedRow() != -1) {
            int selectedRow = tbObservasiKalaIV.getSelectedRow();

            TNoRw.setText(tbObservasiKalaIV.getValueAt(selectedRow, 0).toString());
            isPsien();

            Valid.SetTgl(TglPartograf, tbObservasiKalaIV.getValueAt(selectedRow, 1).toString());
            parseJamToCombo(tbObservasiKalaIV.getValueAt(selectedRow, 2).toString());

            JamKeObsKalaIV.setSelectedItem(tbObservasiKalaIV.getValueAt(selectedRow, 3).toString());
            TekananDarahObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 4).toString());
            NadiObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 5).toString());
            SuhuObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 6).toString());
            TFUObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 7).toString());
            KontraksiUterusObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 8).toString());
            KandungKemihObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 9).toString());
            PerdarahanObsKalaIV.setText(tbObservasiKalaIV.getValueAt(selectedRow, 10).toString());

            NIPPetugas.setText(tbObservasiKalaIV.getValueAt(selectedRow, 11).toString());
            TPetugas.setText(tbObservasiKalaIV.getValueAt(selectedRow, 12).toString());
        }
    }

// ========== SETUP TAB MASALAH KALA IV ==========
    private void setupTabMasalahKalaIV() {
        internalFrame6 = new widget.InternalFrame();
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6");
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== PANEL INPUT MASALAH KALA IV ==========
        javax.swing.JPanel PanelInputMasalahKalaIV = new javax.swing.JPanel();
        PanelInputMasalahKalaIV.setName("PanelInputMasalahKalaIV");
        PanelInputMasalahKalaIV.setOpaque(false);
        PanelInputMasalahKalaIV.setPreferredSize(new java.awt.Dimension(192, 350));
        PanelInputMasalahKalaIV.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== FORM CONTENT SCROLL PANE ==========
        scrollPane5 = new widget.ScrollPane();
        scrollPane5.setName("scrollPane5");
        scrollPane5.setPreferredSize(new java.awt.Dimension(46, 320));

        panelGlass5 = new widget.panelisi();
        panelGlass5.setName("panelGlass5");
        panelGlass5.setPreferredSize(new java.awt.Dimension(44, 300));
        panelGlass5.setLayout(null);

        // Setup form masalah kala IV
        setupFormMasalahKalaIV();

        scrollPane5.setViewportView(panelGlass5);
        PanelInputMasalahKalaIV.add(scrollPane5, java.awt.BorderLayout.PAGE_START);

        // ========== CHECKBOX INPUT ==========
        widget.CekBox ChkInputMasalahKalaIV = new widget.CekBox();
        ChkInputMasalahKalaIV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInputMasalahKalaIV.setMnemonic('M');
        ChkInputMasalahKalaIV.setText(".: Input Data Masalah Kala IV");
        ChkInputMasalahKalaIV.setToolTipText("Alt+M");
        ChkInputMasalahKalaIV.setBorderPainted(true);
        ChkInputMasalahKalaIV.setBorderPaintedFlat(true);
        ChkInputMasalahKalaIV.setFocusable(false);
        ChkInputMasalahKalaIV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInputMasalahKalaIV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInputMasalahKalaIV.setName("ChkInputMasalahKalaIV");
        ChkInputMasalahKalaIV.setPreferredSize(new java.awt.Dimension(200, 20));

        PanelInputMasalahKalaIV.add(ChkInputMasalahKalaIV, java.awt.BorderLayout.PAGE_END);

        internalFrame6.add(PanelInputMasalahKalaIV, java.awt.BorderLayout.PAGE_START);

        // ========== TABEL MASALAH KALA IV ==========
        ScrollMasalahKalaIV = new widget.ScrollPane();
        ScrollMasalahKalaIV.setName("ScrollMasalahKalaIV");
        ScrollMasalahKalaIV.setOpaque(true);

        internalFrame6.add(ScrollMasalahKalaIV, java.awt.BorderLayout.CENTER);

        // ========== ADD TAB ==========
        TabRawat.addTab("Masalah Kala IV", internalFrame6);
    }

    // ========== INISIALISASI KOMPONEN MASALAH KALA IV ==========
    private void initMasalahKalaIV() {
        // Inisialisasi tabel model berdasarkan struktur database
        tabModeMasalahKalaIV = new DefaultTableModel(null, new Object[]{
            "No.Rawat", // 0
            "Tanggal", // 1
            "Jam", // 2
            "Rujukan Kala", // 3
            "Pendamping Rujuk", // 4
            "Alasan Rujuk", // 5
            "Tempat Rujuk", // 6
            "Masalah Kala IV", // 7
            "Penatalaksanaan", // 8
            "Hasilnya", // 9
            "NIP", // 10
            "Petugas" // 11
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

    }

    private void setupFormMasalahKalaIV() {
        // Header
        widget.Label lblHeaderMasalah = new widget.Label();
        lblHeaderMasalah.setText("MASALAH KALA IV - OBSERVASI POST PARTUM");
        lblHeaderMasalah.setName("lblHeaderMasalah");
        lblHeaderMasalah.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHeaderMasalah.setForeground(new Color(0, 100, 0));
        panelGlass5.add(lblHeaderMasalah);
        lblHeaderMasalah.setBounds(20, 10, 400, 23);

        // Baris 1: Catatan Rujukan Kala, Pendamping Saat Merujuk
        widget.Label lblCatatanRujukan = new widget.Label();
        lblCatatanRujukan.setText("Rujukan Kala:");
        lblCatatanRujukan.setName("lblCatatanRujukan");
        panelGlass5.add(lblCatatanRujukan);
        lblCatatanRujukan.setBounds(20, 40, 85, 23);

        CatatanRujukanKala = new widget.ComboBox();
        CatatanRujukanKala.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "-", "I", "II", "III", "IV"
        }));
        CatatanRujukanKala.setName("CatatanRujukanKala");
        panelGlass5.add(CatatanRujukanKala);
        CatatanRujukanKala.setBounds(110, 40, 50, 23);

        widget.Label lblPendampingSaatMerujuk = new widget.Label();
        lblPendampingSaatMerujuk.setText("Pendamping Rujuk:");
        lblPendampingSaatMerujuk.setName("lblPendampingSaatMerujuk");
        panelGlass5.add(lblPendampingSaatMerujuk);
        lblPendampingSaatMerujuk.setBounds(180, 40, 110, 23);

        PendampingSaatMerujuk = new widget.ComboBox();
        PendampingSaatMerujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "-", "Bidan", "Dokter", "Perawat", "Keluarga", "Tidak Ada"
        }));
        PendampingSaatMerujuk.setName("PendampingSaatMerujuk");
        panelGlass5.add(PendampingSaatMerujuk);
        PendampingSaatMerujuk.setBounds(295, 40, 100, 23);

        // Baris 2: Alasan Merujuk
        widget.Label lblAlasanMerujuk = new widget.Label();
        lblAlasanMerujuk.setText("Alasan Rujuk:");
        lblAlasanMerujuk.setName("lblAlasanMerujuk");
        panelGlass5.add(lblAlasanMerujuk);
        lblAlasanMerujuk.setBounds(20, 70, 85, 23);

        AlasanMerujuk = new widget.TextBox();
        AlasanMerujuk.setName("AlasanMerujuk");
        panelGlass5.add(AlasanMerujuk);
        AlasanMerujuk.setBounds(110, 70, 500, 23);

        // Baris 3: Tempat Merujuk
        widget.Label lblTempatMerujuk = new widget.Label();
        lblTempatMerujuk.setText("Tempat Rujuk:");
        lblTempatMerujuk.setName("lblTempatMerujuk");
        panelGlass5.add(lblTempatMerujuk);
        lblTempatMerujuk.setBounds(20, 100, 85, 23);

        TempatMerujuk = new widget.TextBox();
        TempatMerujuk.setName("TempatMerujuk");
        panelGlass5.add(TempatMerujuk);
        TempatMerujuk.setBounds(110, 100, 500, 23);

        // Baris 4: Masalah Kala IV
        widget.Label lblMasalahKalaIV = new widget.Label();
        lblMasalahKalaIV.setText("Masalah:");
        lblMasalahKalaIV.setName("lblMasalahKalaIV");
        panelGlass5.add(lblMasalahKalaIV);
        lblMasalahKalaIV.setBounds(20, 130, 60, 23);

        MasalahKalaIV = new widget.TextBox();
        MasalahKalaIV.setName("MasalahKalaIV");
        panelGlass5.add(MasalahKalaIV);
        MasalahKalaIV.setBounds(85, 130, 525, 23);

        // Baris 5: Penatalaksanaan
        widget.Label lblPenatalaksanaanKalaIV = new widget.Label();
        lblPenatalaksanaanKalaIV.setText("Penatalaksanaan:");
        lblPenatalaksanaanKalaIV.setName("lblPenatalaksanaanKalaIV");
        panelGlass5.add(lblPenatalaksanaanKalaIV);
        lblPenatalaksanaanKalaIV.setBounds(20, 160, 100, 23);

        PenatalaksanaanKalaIV = new widget.TextBox();
        PenatalaksanaanKalaIV.setName("PenatalaksanaanKalaIV");
        panelGlass5.add(PenatalaksanaanKalaIV);
        PenatalaksanaanKalaIV.setBounds(125, 160, 485, 23);

        // Baris 6: Hasilnya
        widget.Label lblHasilnyaKalaIV = new widget.Label();
        lblHasilnyaKalaIV.setText("Hasilnya:");
        lblHasilnyaKalaIV.setName("lblHasilnyaKalaIV");
        panelGlass5.add(lblHasilnyaKalaIV);
        lblHasilnyaKalaIV.setBounds(20, 190, 60, 23);

        HasilnyaKalaIV = new widget.TextBox();
        HasilnyaKalaIV.setName("HasilnyaKalaIV");
        panelGlass5.add(HasilnyaKalaIV);
        HasilnyaKalaIV.setBounds(85, 190, 525, 23);
    }

    private void initObservasiKalaIV() {
        // Inisialisasi tabel model
        tabModeObservasiKalaIV = new DefaultTableModel(null, new Object[]{
            "No.RW", "Tanggal", "Jam", "Jam Ke", "TD (mmHg)", "Nadi(/mnt)",
            "Suhu(°C)", "TFU(cm)", "Kontraksi Uterus", "Kandung Kemih",
            "Perdarahan", "NIP Petugas"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class
                    getColumnClass(int column) {
                return String.class;
            }
        };

        // Set model ke tabel
        tbObservasiKalaIV.setModel(tabModeObservasiKalaIV);
        tbObservasiKalaIV.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObservasiKalaIV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbObservasiKalaIV
                .setDefaultRenderer(Object.class,
                        new WarnaTable());

        // Set lebar kolom
        tbObservasiKalaIV.getColumnModel().getColumn(0).setPreferredWidth(60);
        tbObservasiKalaIV.getColumnModel().getColumn(1).setPreferredWidth(80);
        tbObservasiKalaIV.getColumnModel().getColumn(2).setPreferredWidth(60);
        tbObservasiKalaIV.getColumnModel().getColumn(3).setPreferredWidth(55);
        tbObservasiKalaIV.getColumnModel().getColumn(4).setPreferredWidth(70);
        tbObservasiKalaIV.getColumnModel().getColumn(5).setPreferredWidth(70);
        tbObservasiKalaIV.getColumnModel().getColumn(6).setPreferredWidth(60);
        tbObservasiKalaIV.getColumnModel().getColumn(7).setPreferredWidth(60);
        tbObservasiKalaIV.getColumnModel().getColumn(8).setPreferredWidth(100);
        tbObservasiKalaIV.getColumnModel().getColumn(9).setPreferredWidth(90);
        tbObservasiKalaIV.getColumnModel().getColumn(10).setPreferredWidth(80);
        tbObservasiKalaIV.getColumnModel().getColumn(11).setPreferredWidth(80);
    }

    // ========== INISIALISASI COMBO BOX ==========
    private void initComboBoxes() {
        // ComboBox JamKe
        JamKe = new widget.ComboBox();
        JamKe.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "1", "2", "3", "4", "5", "6"
        }));

        // ComboBox untuk Catatan Rujukan
        CatatanRujukanKala = new widget.ComboBox();
        CatatanRujukanKala.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "-", "Tidak dirujuk", "Dirujuk"
        }));

        // ComboBox untuk Pendamping Saat Merujuk
        PendampingSaatMerujuk = new widget.ComboBox();
        PendampingSaatMerujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "-", "Perawat", "Bidan", "Dokter", "Keluarga"
        }));
    }

// ========== INISIALISASI TEXT FIELDS ==========
    private void initTextFields() {
        // TextBox untuk Observasi Kala IV
        TekananDarahKalaIV = new widget.TextBox();
        NadiKalaIV = new widget.TextBox();
        SuhuKalaIV = new widget.TextBox();
        TFU = new widget.TextBox();
        KontraksiUterusKalaIV = new widget.TextBox();
        KandungKemih = new widget.TextBox();
        PerdarahanKalaIV = new widget.TextBox();

        // TextBox untuk Masalah Kala IV
        AlasanMerujuk = new widget.TextBox();
        TempatMerujuk = new widget.TextBox();
        MasalahKalaIV = new widget.TextBox();
        PenatalaksanaanKalaIV = new widget.TextBox();
        HasilnyaKalaIV = new widget.TextBox();
    }

// ========== METHOD VALIDASI OBSERVASI KALA IV ==========
    private void validasiObservasiKalaIV() {
        if (tabModeObservasiKalaIV.getRowCount() != 0) {
            // Hapus data lama
            for (int i = tabModeObservasiKalaIV.getRowCount() - 1; i >= 0; i--) {
                tabModeObservasiKalaIV.removeRow(i);
            }
        }

        if (tabModeObservasiKalaIV.getRowCount() != 0) {
            // Clear table jika masih ada data
            Valid.tabelKosong(tabModeObservasiKalaIV);
        }
    }

    private void simpanObservasiKalaIV() {
        // PASTIKAN BERADA DI TAB YANG BENAR
        int selectedTab = TabRawat.getSelectedIndex();
        System.out.println("Current tab: " + selectedTab);

        // TAB OBSERVASI KALA IV ADALAH INDEX BERAPA?
        if (selectedTab != 4) { // Sesuaikan dengan index tab Observasi Kala IV
            System.out.println("WARNING: Not in correct tab!");
        }

        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
            return;
        }

        try {
            // Validasi duplikasi data
            String checkSql = "SELECT COUNT(*) FROM partograf_observasi_kala4 "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=? AND jam_ke=?";

            PreparedStatement psCheck = koneksi.prepareStatement(checkSql);
            psCheck.setString(1, TNoRw.getText());
            psCheck.setString(2, Valid.SetTgl(TglPartograf.getSelectedItem() + ""));
            psCheck.setString(3, getFormattedTime());
            psCheck.setString(4, JamKe.getSelectedItem().toString());

            ResultSet rsCheck = psCheck.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null,
                        "Data observasi untuk jam ke-" + JamKe.getSelectedItem()
                        + " pada waktu ini sudah ada!\nSilakan gunakan tombol Edit untuk mengubah data.");
                rsCheck.close();
                psCheck.close();
                return;
            }
            rsCheck.close();
            psCheck.close();

            // Debug: Print nilai textbox sebelum menyimpan
            System.out.println("=== DEBUG TEXTBOX VALUES ===");
            System.out.println("1. no_rawat: " + TNoRw.getText());
            System.out.println("2. tanggal: " + Valid.SetTgl(TglPartograf.getSelectedItem() + ""));
            System.out.println("3. jam: " + getFormattedTime());
            System.out.println("4. jam_ke: " + JamKe.getSelectedItem().toString());
            System.out.println("5. tekanan_darah: " + TekananDarahKalaIV.getText());
            System.out.println("6. nadi: " + NadiKalaIV.getText());
            System.out.println("7. suhu: " + SuhuKalaIV.getText());
            System.out.println("8. tfu: " + TFU.getText());
            System.out.println("9. kontraksi_uterus: " + KontraksiUterusKalaIV.getText());
            System.out.println("10. kandung_kemih: " + KandungKemih.getText());
            System.out.println("11. perdarahan: " + PerdarahanKalaIV.getText());
            System.out.println("12. nip: " + NIPPetugas.getText());

            // Simpan langsung dengan getText()
            if (Sequel.menyimpantf("partograf_observasi_kala4",
                    "?,?,?,?,?,?,?,?,?,?,?,?", "Data Partograf Kala IV", 12, new String[]{
                        TNoRw.getText(), // 1. no_rawat
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal
                        getFormattedTime(), // 3. jam
                        JamKe.getSelectedItem().toString(), // 4. jam_ke
                        TekananDarahKalaIV.getText(), // 5. tekanan_darah
                        NadiKalaIV.getText(), // 6. nadi
                        SuhuKalaIV.getText(), // 7. suhu
                        TFU.getText(), // 8. tfu
                        KontraksiUterusKalaIV.getText(), // 9. kontraksi_uterus
                        KandungKemih.getText(), // 10. kandung_kemih
                        PerdarahanKalaIV.getText(), // 11. perdarahan
                        NIPPetugas.getText() // 12. nip
                    }) == true) {

                JOptionPane.showMessageDialog(null, "Data observasi kala IV berhasil disimpan!");
                tampilObservasiKalaIV();
                emptTeksKalaIV();

            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data observasi kala IV!");
                System.out.println("ERROR: Sequel.menyimpantf returned false");
            }

        } catch (Exception e) {
            System.out.println("ERROR simpanObservasiKalaIV: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error menyimpan data: " + e.getMessage());
        }
    }

    // Method debug tambahan
    private void debugDatabaseInsert(String[] data) {
        try {
            // Test manual insert untuk debugging
            String testSql = "INSERT INTO partograf_observasi_kala4 "
                    + "(no_rawat, tanggal, jam, jam_ke, tekanan_darah, nadi, suhu, tfu, "
                    + "kontraksi_uterus, kandung_kemih, perdarahan, nip) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement psTest = koneksi.prepareStatement(testSql);
            for (int i = 0; i < data.length; i++) {
                psTest.setString(i + 1, data[i]);
            }

            int result = psTest.executeUpdate();
            System.out.println("Manual insert result: " + result);
            psTest.close();

        } catch (SQLException e) {
            System.out.println("Manual insert error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }
    }

    private String getTextValueSafe(widget.TextBox textBox) {
        if (textBox != null) {
            String text = textBox.getText().trim();
            // Return null untuk field kosong agar tidak menyebabkan constraint error
            return text.equals("") ? null : text;
        }
        return null;
    }

// ========== METHOD SIMPAN MASALAH KALA IV ==========
    private void simpanMasalahKalaIV() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            // SQL INSERT sesuai struktur database - 11 kolom
            if (Sequel.menyimpantf("partograf_masalah_kala4",
                    "?,?,?,?,?,?,?,?,?,?,?", // 11 placeholder
                    "Masalah Kala IV", 11, new String[]{
                        TNoRw.getText(), // 1. no_rawat
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal
                        getFormattedTime(), // 3. jam
                        CatatanRujukanKala.getSelectedItem().toString(), // 4. catatan_rujukan_kala
                        PendampingSaatMerujuk.getSelectedItem().toString(), // 5. pendamping_saat_merujuk
                        AlasanMerujuk.getText(), // 6. alasan_merujuk
                        TempatMerujuk.getText(), // 7. tempat_merujuk
                        MasalahKalaIV.getText(), // 8. masalah_kala4
                        PenatalaksanaanKalaIV.getText(), // 9. penatalaksanaan_kala4
                        HasilnyaKalaIV.getText(), // 10. hasilnya_kala4
                        NIPPetugas.getText() // 11. nip
                    }) == true) {

                JOptionPane.showMessageDialog(null, "Data masalah kala IV berhasil disimpan!");
                tampilMasalahKalaIV();
                emptTeksMasalahKalaIV();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data masalah kala IV!");
            }
        }
    }

// ========== METHOD MENGISI DATA KE FORM ==========
    private void getDataObservasiKalaIV() {
        if (tbObservasiKalaIV.getSelectedRow() != -1) {
            int selectedRow = tbObservasiKalaIV.getSelectedRow();

            try {
                TNoRw.setText(tbObservasiKalaIV.getValueAt(selectedRow, 0).toString());
                isPsien();

                Valid.SetTgl(TglPartograf, tbObservasiKalaIV.getValueAt(selectedRow, 1).toString());
                parseJamToCombo(tbObservasiKalaIV.getValueAt(selectedRow, 2).toString());

                JamKe.setSelectedItem(tbObservasiKalaIV.getValueAt(selectedRow, 3).toString());

                // Load data dengan null check
                setTextBoxValue(TekananDarahKalaIV, tbObservasiKalaIV.getValueAt(selectedRow, 4));
                setTextBoxValue(NadiKalaIV, tbObservasiKalaIV.getValueAt(selectedRow, 5));
                setTextBoxValue(SuhuKalaIV, tbObservasiKalaIV.getValueAt(selectedRow, 6));
                setTextBoxValue(TFU, tbObservasiKalaIV.getValueAt(selectedRow, 7));
                setTextBoxValue(KontraksiUterusKalaIV, tbObservasiKalaIV.getValueAt(selectedRow, 8));
                setTextBoxValue(KandungKemih, tbObservasiKalaIV.getValueAt(selectedRow, 9));
                setTextBoxValue(PerdarahanKalaIV, tbObservasiKalaIV.getValueAt(selectedRow, 10));

                NIPPetugas.setText(tbObservasiKalaIV.getValueAt(selectedRow, 11).toString());
                TPetugas.setText(tbObservasiKalaIV.getValueAt(selectedRow, 12).toString());

            } catch (Exception e) {
                System.out.println("Error getDataObservasiKalaIV: " + e.getMessage());
            }
        }
    }

    private void setTextBoxValue(widget.TextBox textBox, Object value) {
        if (textBox != null) {
            if (value != null && !value.toString().equals("-") && !value.toString().equals("null")) {
                textBox.setText(value.toString());
            } else {
                textBox.setText("");
            }
        }
    }

// ========== METHOD TAMPIL DATA ==========
    private void tampilObservasiKalaIV() {
        Valid.tabelKosong(tabModeObservasiKalaIV);
        try {
            String sql = "SELECT obs.no_rawat, obs.tanggal, obs.jam, obs.jam_ke, "
                    + "obs.tekanan_darah, obs.nadi, obs.suhu, obs.tfu, "
                    + "obs.kontraksi_uterus, obs.kandung_kemih, obs.perdarahan, "
                    + "obs.nip, p.nama "
                    + "FROM partograf_observasi_kala4 obs "
                    + "LEFT JOIN petugas p ON obs.nip = p.nip "
                    + "WHERE obs.tanggal BETWEEN ? AND ? ";

            if (!TCari.getText().trim().equals("")) {
                sql += "AND (obs.no_rawat LIKE ? OR p.nama LIKE ?) ";
            }

            sql += "ORDER BY obs.tanggal DESC, obs.jam DESC, obs.jam_ke ASC";

            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

            if (!TCari.getText().trim().equals("")) {
                ps.setString(3, "%" + TCari.getText() + "%");
                ps.setString(4, "%" + TCari.getText() + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tabModeObservasiKalaIV.addRow(new String[]{
                    rs.getString("no_rawat"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getString("jam_ke"),
                    rs.getString("tekanan_darah") != null ? rs.getString("tekanan_darah") : "-",
                    rs.getString("nadi") != null ? rs.getString("nadi") : "-",
                    rs.getString("suhu") != null ? rs.getString("suhu") : "-",
                    rs.getString("tfu") != null ? rs.getString("tfu") : "-",
                    rs.getString("kontraksi_uterus") != null ? rs.getString("kontraksi_uterus") : "-",
                    rs.getString("kandung_kemih") != null ? rs.getString("kandung_kemih") : "-",
                    rs.getString("perdarahan") != null ? rs.getString("perdarahan") : "-",
                    rs.getString("nip"),
                    rs.getString("nama") != null ? rs.getString("nama") : "-"
                });
            }

            LCount.setText("" + tabModeObservasiKalaIV.getRowCount());
            rs.close();
            ps.close();

            System.out.println("Tampil Observasi Kala IV: " + tabModeObservasiKalaIV.getRowCount() + " records");

        } catch (Exception e) {
            System.out.println("Error tampil observasi kala IV: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error menampilkan data: " + e.getMessage());
        }
    }

    private void emptTeksMasalahKalaIV() {
        CatatanRujukanKala.setSelectedIndex(0);
        PendampingSaatMerujuk.setSelectedIndex(0);
        AlasanMerujuk.setText("");
        TempatMerujuk.setText("");
        MasalahKalaIV.setText("");
        PenatalaksanaanKalaIV.setText("");
        HasilnyaKalaIV.setText("");
    }

    private void tampilMasalahKalaIV() {
        Valid.tabelKosong(tabModeMasalahKalaIV);
        try {
            String sql = "SELECT mk.no_rawat, mk.tanggal, mk.jam, "
                    + "mk.catatan_rujukan_kala, mk.pendamping_saat_merujuk, "
                    + "mk.alasan_merujuk, mk.tempat_merujuk, mk.masalah_kala4, "
                    + "mk.penatalaksanaan_kala4, mk.hasilnya_kala4, mk.nip, p.nama "
                    + "FROM partograf_masalah_kala4 mk "
                    + "LEFT JOIN petugas p ON mk.nip = p.nip "
                    + "WHERE mk.tanggal BETWEEN ? AND ? ";

            if (!TCari.getText().trim().equals("")) {
                sql += "AND (mk.no_rawat LIKE ? OR p.nama LIKE ? OR mk.masalah_kala4 LIKE ?) ";
            }

            sql += "ORDER BY mk.tanggal DESC, mk.jam DESC";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

            if (!TCari.getText().trim().equals("")) {
                String searchTerm = "%" + TCari.getText() + "%";
                ps.setString(3, searchTerm);
                ps.setString(4, searchTerm);
                ps.setString(5, searchTerm);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                tabModeMasalahKalaIV.addRow(new String[]{
                    rs.getString("no_rawat"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getString("catatan_rujukan_kala"),
                    rs.getString("pendamping_saat_merujuk"),
                    rs.getString("alasan_merujuk") != null ? rs.getString("alasan_merujuk") : "-",
                    rs.getString("tempat_merujuk") != null ? rs.getString("tempat_merujuk") : "-",
                    rs.getString("masalah_kala4") != null ? rs.getString("masalah_kala4") : "-",
                    rs.getString("penatalaksanaan_kala4") != null ? rs.getString("penatalaksanaan_kala4") : "-",
                    rs.getString("hasilnya_kala4") != null ? rs.getString("hasilnya_kala4") : "-",
                    rs.getString("nip"),
                    rs.getString("nama") != null ? rs.getString("nama") : "-"
                });
            }

            LCount.setText("" + tabModeMasalahKalaIV.getRowCount());
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error tampil masalah kala IV: " + e.getMessage());
            e.printStackTrace();
        }
    }

// METHOD 2: isPsikolog() - untuk validasi atau update data petugas
    private void isPsikolog() {
        // Method ini sepertinya dipanggil setelah mengisi NIP petugas
        // untuk update informasi petugas atau validasi

        try {
            if (!NIPPetugas.getText().equals("")) {
                // Query untuk mendapatkan info petugas berdasarkan NIP
                ps = koneksi.prepareStatement(
                        "SELECT nama, jabatan FROM petugas WHERE nip=?"
                );
                ps.setString(1, NIPPetugas.getText());
                rs = ps.executeQuery();

                if (rs.next()) {
                    TPetugas.setText(rs.getString("nama"));

                    // Optional: bisa ditambahkan validasi jabatan
                    String jabatan = rs.getString("jabatan");
                    if (jabatan != null && (jabatan.toLowerCase().contains("psikolog")
                            || jabatan.toLowerCase().contains("bidan")
                            || jabatan.toLowerCase().contains("dokter"))) {
                        // Petugas valid untuk partograf
                        System.out.println("Petugas valid: " + jabatan);
                    } else {
                        // Warning jika bukan petugas yang sesuai
                        System.out.println("Warning: Petugas mungkin bukan tenaga medis");
                    }
                } else {
                    TPetugas.setText("");
                    System.out.println("NIP tidak ditemukan");
                }

                // Cleanup
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }

            } else {
                TPetugas.setText("");
            }
        } catch (Exception e) {
            System.out.println("Error isPsikolog: " + e);
            TPetugas.setText("");
        }
    }

    private void setupTabRujukan() {
        internalFrameRujukan = new widget.InternalFrame();
        internalFrameRujukan.setBorder(null);
        internalFrameRujukan.setName("internalFrameRujukan");
        internalFrameRujukan.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== PANEL INPUT RUJUKAN ==========
        javax.swing.JPanel PanelInputRujukan = new javax.swing.JPanel();
        PanelInputRujukan.setName("PanelInputRujukan");
        PanelInputRujukan.setOpaque(false);
        PanelInputRujukan.setPreferredSize(new java.awt.Dimension(192, 350));
        PanelInputRujukan.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== FORM CONTENT SCROLL PANE ==========
        scrollPaneRujukan = new widget.ScrollPane();
        scrollPaneRujukan.setName("scrollPaneRujukan");
        scrollPaneRujukan.setPreferredSize(new java.awt.Dimension(46, 320));

        panelGlassRujukan = new widget.panelisi();
        panelGlassRujukan.setName("panelGlassRujukan");
        panelGlassRujukan.setPreferredSize(new java.awt.Dimension(44, 450));
        panelGlassRujukan.setLayout(null);

        // Setup form rujukan
        setupFormRujukan();

        scrollPaneRujukan.setViewportView(panelGlassRujukan);
        PanelInputRujukan.add(scrollPaneRujukan, java.awt.BorderLayout.PAGE_START);

        // ========== CHECKBOX INPUT RUJUKAN ==========
        widget.CekBox ChkInputRujukan = new widget.CekBox();
        ChkInputRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInputRujukan.setMnemonic('R');
        ChkInputRujukan.setText(".: Input Data Rujukan");
        ChkInputRujukan.setToolTipText("Alt+R");
        ChkInputRujukan.setBorderPainted(true);
        ChkInputRujukan.setBorderPaintedFlat(true);
        ChkInputRujukan.setFocusable(false);
        ChkInputRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInputRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInputRujukan.setName("ChkInputRujukan");
        ChkInputRujukan.setPreferredSize(new java.awt.Dimension(200, 20));
        ChkInputRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputRujukanActionPerformed(evt);
            }
        });
        PanelInputRujukan.add(ChkInputRujukan, java.awt.BorderLayout.PAGE_END);

        internalFrameRujukan.add(PanelInputRujukan, java.awt.BorderLayout.PAGE_START);

        // ========== TABLE RUJUKAN ==========
        ScrollRujukan = new widget.ScrollPane();
        ScrollRujukan.setName("ScrollRujukan");
        ScrollRujukan.setOpaque(true);

        tbRujukan = new widget.Table();
        tbRujukan.setName("tbRujukan");
        tbRujukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRujukanMouseClicked(evt);
            }
        });
        tbRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRujukanKeyPressed(evt);
            }
        });
        ScrollRujukan.setViewportView(tbRujukan);

        internalFrameRujukan.add(ScrollRujukan, java.awt.BorderLayout.CENTER);

        // ========== ADD TAB TO CONTAINER ==========
        TabRawat.addTab("Rujukan", internalFrameRujukan);
    }

// ========== SETUP FORM RUJUKAN ==========
    private void setupFormRujukan() {
        // Header Rujukan
        widget.Label lblHeaderRujukan = new widget.Label();
        lblHeaderRujukan.setText("DATA RUJUKAN PASIEN");
        lblHeaderRujukan.setName("lblHeaderRujukan");
        lblHeaderRujukan.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblHeaderRujukan.setForeground(new Color(0, 100, 0));
        panelGlassRujukan.add(lblHeaderRujukan);
        lblHeaderRujukan.setBounds(20, 20, 250, 25);

        // Baris 1: Status Rujukan, Tanggal, Jam
        widget.Label lblStatusRujukan = new widget.Label();
        lblStatusRujukan.setText("Status Rujukan:");
        lblStatusRujukan.setName("lblStatusRujukan");
        panelGlassRujukan.add(lblStatusRujukan);
        lblStatusRujukan.setBounds(20, 60, 100, 23);

        StatusRujukan = new widget.ComboBox();
        StatusRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Tidak Dirujuk", "Rujuk", "Rujuk Balik", "Rujuk Darurat"
        }));
        StatusRujukan.setName("StatusRujukan");
        StatusRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusRujukanActionPerformed(evt);
            }
        });
        panelGlassRujukan.add(StatusRujukan);
        StatusRujukan.setBounds(130, 60, 120, 23);

        widget.Label lblTanggalRujukan = new widget.Label();
        lblTanggalRujukan.setText("Tanggal:");
        lblTanggalRujukan.setName("lblTanggalRujukan");
        panelGlassRujukan.add(lblTanggalRujukan);
        lblTanggalRujukan.setBounds(270, 60, 60, 23);

        TanggalRujukan = new widget.Tanggal();
        TanggalRujukan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujukan.setDisplayFormat("dd-MM-yyyy");
        TanggalRujukan.setName("TanggalRujukan");
        panelGlassRujukan.add(TanggalRujukan);
        TanggalRujukan.setBounds(340, 60, 100, 23);

        widget.Label lblJamRujukan = new widget.Label();
        lblJamRujukan.setText("Jam:");
        lblJamRujukan.setName("lblJamRujukan");
        panelGlassRujukan.add(lblJamRujukan);
        lblJamRujukan.setBounds(460, 60, 35, 23);

        JamRujukan = new widget.ComboBox();
        JamRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
        }));
        JamRujukan.setName("JamRujukan");
        panelGlassRujukan.add(JamRujukan);
        JamRujukan.setBounds(500, 60, 45, 23);

        MenitRujukan = new widget.ComboBox();
        MenitRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "00", "15", "30", "45"
        }));
        MenitRujukan.setName("MenitRujukan");
        panelGlassRujukan.add(MenitRujukan);
        MenitRujukan.setBounds(550, 60, 45, 23);

        // Baris 2: Alasan Rujukan
        widget.Label lblAlasanRujukan = new widget.Label();
        lblAlasanRujukan.setText("Alasan Rujukan:");
        lblAlasanRujukan.setName("lblAlasanRujukan");
        panelGlassRujukan.add(lblAlasanRujukan);
        lblAlasanRujukan.setBounds(20, 100, 100, 23);

        AlasanRujukan = new widget.ComboBox();
        AlasanRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Komplikasi Persalinan", "Gawat Janin", "Partograf Melewati Garis Waspada",
            "Perdarahan Ante Partum", "Perdarahan Post Partum", "Pre Eklampsia/Eklampsia",
            "Distosia", "Ketuban Pecah Dini", "Persalinan Prematur", "Lain-lain"
        }));
        AlasanRujukan.setName("AlasanRujukan");
        AlasanRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlasanRujukanActionPerformed(evt);
            }
        });
        panelGlassRujukan.add(AlasanRujukan);
        AlasanRujukan.setBounds(130, 100, 200, 23);

        widget.Label lblAlasanLainnya = new widget.Label();
        lblAlasanLainnya.setText("Sebutkan:");
        lblAlasanLainnya.setName("lblAlasanLainnya");
        panelGlassRujukan.add(lblAlasanLainnya);
        lblAlasanLainnya.setBounds(340, 100, 70, 23);

        AlasanLainnya = new widget.TextBox();
        AlasanLainnya.setName("AlasanLainnya");
        AlasanLainnya.setEnabled(true);
        panelGlassRujukan.add(AlasanLainnya);
        AlasanLainnya.setBounds(415, 100, 200, 23);

        // Baris 3: Tempat Tujuan Rujukan
        widget.Label lblTempatTujuan = new widget.Label();
        lblTempatTujuan.setText("Tempat Rujukan:");
        lblTempatTujuan.setName("lblTempatTujuan");
        panelGlassRujukan.add(lblTempatTujuan);
        lblTempatTujuan.setBounds(20, 140, 110, 23);

        TempatTujuanRujukan = new widget.ComboBox();
        TempatTujuanRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "RSUD", "RSU", "RS Swasta", "Puskesmas PONED", "Rumah Bersalin",
            "Praktik Dokter Spesialis", "Lain-lain"
        }));
        TempatTujuanRujukan.setName("TempatTujuanRujukan");
        TempatTujuanRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TempatTujuanRujukanActionPerformed(evt);
            }
        });
        panelGlassRujukan.add(TempatTujuanRujukan);
        TempatTujuanRujukan.setBounds(140, 140, 150, 23);

        widget.Label lblTempatLain = new widget.Label();
        lblTempatLain.setText("Nama Tempat:");
        lblTempatLain.setName("lblTempatLain");
        panelGlassRujukan.add(lblTempatLain);
        lblTempatLain.setBounds(300, 140, 90, 23);

        TempatRujukanLain = new widget.TextBox();
        TempatRujukanLain.setName("TempatRujukanLain");
        panelGlassRujukan.add(TempatRujukanLain);
        TempatRujukanLain.setBounds(395, 140, 220, 23);

        // Baris 4: Transportasi dan Biaya
        widget.Label lblTransportasi = new widget.Label();
        lblTransportasi.setText("Transportasi:");
        lblTransportasi.setName("lblTransportasi");
        panelGlassRujukan.add(lblTransportasi);
        lblTransportasi.setBounds(20, 180, 90, 23);

        TransportasiRujukan = new widget.ComboBox();
        TransportasiRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Ambulans", "Mobil Pribadi", "Motor", "Angkutan Umum", "Ojek", "Lain-lain"
        }));
        TransportasiRujukan.setName("TransportasiRujukan");
        panelGlassRujukan.add(TransportasiRujukan);
        TransportasiRujukan.setBounds(115, 180, 120, 23);

        widget.Label lblBiaya = new widget.Label();
        lblBiaya.setText("Biaya:");
        lblBiaya.setName("lblBiaya");
        panelGlassRujukan.add(lblBiaya);
        lblBiaya.setBounds(250, 180, 45, 23);

        BiayaTransportasi = new widget.TextBox();
        BiayaTransportasi.setName("BiayaTransportasi");
        panelGlassRujukan.add(BiayaTransportasi);
        BiayaTransportasi.setBounds(300, 180, 80, 23);

        widget.Label lblRupiah = new widget.Label();
        lblRupiah.setText("Rupiah");
        lblRupiah.setName("lblRupiah");
        panelGlassRujukan.add(lblRupiah);
        lblRupiah.setBounds(385, 180, 45, 23);

        // Baris 5: Pendamping
        widget.Label lblPendampingRujukan = new widget.Label();
        lblPendampingRujukan.setText("Pendamping:");
        lblPendampingRujukan.setName("lblPendampingRujukan");
        panelGlassRujukan.add(lblPendampingRujukan);
        lblPendampingRujukan.setBounds(20, 220, 90, 23);

        PendampingRujukan = new widget.ComboBox();
        PendampingRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Bidan", "Perawat", "Dokter", "Keluarga", "Suami", "Tidak Ada"
        }));
        PendampingRujukan.setName("PendampingRujukan");
        PendampingRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PendampingRujukanActionPerformed(evt);
            }
        });
        panelGlassRujukan.add(PendampingRujukan);
        PendampingRujukan.setBounds(115, 220, 100, 23);

        widget.Label lblNamaPendamping = new widget.Label();
        lblNamaPendamping.setText("Nama:");
        lblNamaPendamping.setName("lblNamaPendamping");
        panelGlassRujukan.add(lblNamaPendamping);
        lblNamaPendamping.setBounds(225, 220, 45, 23);

        NamaPendamping = new widget.TextBox();
        NamaPendamping.setName("NamaPendamping");
        panelGlassRujukan.add(NamaPendamping);
        NamaPendamping.setBounds(275, 220, 150, 23);

        widget.Label lblNoTelp = new widget.Label();
        lblNoTelp.setText("No.Telp:");
        lblNoTelp.setName("lblNoTelp");
        panelGlassRujukan.add(lblNoTelp);
        lblNoTelp.setBounds(435, 220, 55, 23);

        NoTelpRujukan = new widget.TextBox();
        NoTelpRujukan.setName("NoTelpRujukan");
        panelGlassRujukan.add(NoTelpRujukan);
        NoTelpRujukan.setBounds(495, 220, 120, 23);

        // Baris 6: Kondisi Saat Rujukan
        widget.Label lblKondisiSaat = new widget.Label();
        lblKondisiSaat.setText("Kondisi Saat Rujukan:");
        lblKondisiSaat.setName("lblKondisiSaat");
        panelGlassRujukan.add(lblKondisiSaat);
        lblKondisiSaat.setBounds(20, 260, 140, 23);

        KondisiSaatRujukan = new widget.ComboBox();
        KondisiSaatRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Stabil", "Tidak Stabil", "Gawat", "Kritis", "Dalam Proses Persalinan"
        }));
        KondisiSaatRujukan.setName("KondisiSaatRujukan");
        panelGlassRujukan.add(KondisiSaatRujukan);
        KondisiSaatRujukan.setBounds(170, 260, 150, 23);

        // Catatan Rujukan
        widget.Label lblCatatanRujukan = new widget.Label();
        lblCatatanRujukan.setText("Catatan Rujukan:");
        lblCatatanRujukan.setName("lblCatatanRujukan");
        panelGlassRujukan.add(lblCatatanRujukan);
        lblCatatanRujukan.setBounds(20, 300, 110, 23);

        CatatanRujukan = new widget.TextArea();
        CatatanRujukan.setColumns(20);
        CatatanRujukan.setRows(4);
        CatatanRujukan.setName("CatatanRujukan");
        CatatanRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanRujukanKeyPressed(evt);
            }
        });

        widget.ScrollPane jScrollPaneRujukan = new widget.ScrollPane();
        jScrollPaneRujukan.setViewportView(CatatanRujukan);
        jScrollPaneRujukan.setName("jScrollPaneRujukan");
        panelGlassRujukan.add(jScrollPaneRujukan);
        jScrollPaneRujukan.setBounds(20, 330, 670, 80);
    }

// ========== EVENT HANDLERS ==========
    private void ChkInputRujukanActionPerformed(java.awt.event.ActionEvent evt) {
        // Toggle visibility form rujukan
        isFormRujukan();
    }

    private void AlasanRujukanActionPerformed(java.awt.event.ActionEvent evt) {
        // Enable AlasanLainnya jika pilih "Lain-lain"
        boolean isLainLain = AlasanRujukan.getSelectedItem().toString().equals("Lain-lain");
        AlasanLainnya.setEnabled(isLainLain);
        if (!isLainLain) {
            AlasanLainnya.setText("");
        }
    }

    private void TempatTujuanRujukanActionPerformed(java.awt.event.ActionEvent evt) {
        // Focus ke TempatRujukanLain untuk input nama tempat
        TempatRujukanLain.requestFocus();
    }

    private void PendampingRujukanActionPerformed(java.awt.event.ActionEvent evt) {
        // Clear nama pendamping saat ganti jenis pendamping
        NamaPendamping.setText("");
        NoTelpRujukan.setText("");
        NamaPendamping.requestFocus();
    }

    private void NIPPetugasRujukanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isPetugasRujukan();
            CatatanRujukan.requestFocus();
        }
    }

    private void CatatanRujukanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            BtnSimpan.requestFocus();
        }
    }

    private void tbRujukanMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeRujukan.getRowCount() != 0) {
            try {
                getDataRujukan();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbRujukanKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeRujukan.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataRujukan();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

// ========== HELPER METHODS ==========
    private void setCurrentTimeRujukan() {
        Date now = new Date();
        JamRujukan.setSelectedItem(String.format("%02d", now.getHours()));
        MenitRujukan.setSelectedItem(String.format("%02d", (now.getMinutes() / 15) * 15));
    }

    private void isPetugasRujukan() {
        try {
            if (!NIPPetugasRujukan.getText().equals("")) {
                ps = koneksi.prepareStatement("SELECT nama FROM petugas WHERE nip=?");
                ps.setString(1, NIPPetugasRujukan.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    PetugasRujukan.setText(rs.getString("nama"));
                } else {
                    PetugasRujukan.setText("");
                }
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } else {
                PetugasRujukan.setText("");
            }
        } catch (Exception e) {
            System.out.println("Error isPetugasRujukan: " + e);
            PetugasRujukan.setText("");
        }
    }

    private void isFormRujukan() {
        // Toggle form visibility logic untuk rujukan
        // Implementasi serupa dengan isForm() untuk tab lain
    }

// ========== CRUD METHODS ==========
    private void simpanRujukan() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
        } else if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.menyimpantf("partograf_rujukan",
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",
                    "Data Rujukan", 16, new String[]{
                        TNoRw.getText(),
                        Valid.SetTgl(TanggalRujukan.getSelectedItem() + ""),
                        JamRujukan.getSelectedItem() + ":" + MenitRujukan.getSelectedItem() + ":00",
                        StatusRujukan.getSelectedItem().toString(),
                        AlasanRujukan.getSelectedItem().toString(),
                        AlasanLainnya.getText(),
                        TempatTujuanRujukan.getSelectedItem().toString(),
                        TempatRujukanLain.getText(),
                        TransportasiRujukan.getSelectedItem().toString(),
                        BiayaTransportasi.getText().equals("") ? "0" : BiayaTransportasi.getText(),
                        PendampingRujukan.getSelectedItem().toString(),
                        NamaPendamping.getText(),
                        NoTelpRujukan.getText(),
                        KondisiSaatRujukan.getSelectedItem().toString(),
                        CatatanRujukan.getText(),
                        NIPPetugas.getText() // GUNAKAN NIP DARI HEADER
                    }) == true) {

                JOptionPane.showMessageDialog(null, "Data rujukan berhasil disimpan!");
                tampilRujukan();
                emptTeksRujukan();
            }
        }
    }

    private void getDataRujukan() {
        if (tbRujukan.getSelectedRow() != -1) {
            int selectedRow = tbRujukan.getSelectedRow();

            // DEBUG: Cek data dari tabel
            System.out.println("=== DEBUG DATA DARI TABEL ===");
            System.out.println("Selected Row: " + selectedRow);

            // Debug semua kolom yang relevan
            for (int i = 0; i < 17; i++) {
                Object cellValue = tbRujukan.getValueAt(selectedRow, i);
                System.out.println("Kolom " + i + ": [" + cellValue + "] (Type: "
                        + (cellValue != null ? cellValue.getClass().getSimpleName() : "null") + ")");
            }

            // SET DATA DASAR
            TNoRw.setText(tbRujukan.getValueAt(selectedRow, 0).toString());
            isPsien();
            Valid.SetTgl(TanggalRujukan, tbRujukan.getValueAt(selectedRow, 1).toString());

            String[] waktu = tbRujukan.getValueAt(selectedRow, 2).toString().split(":");
            JamRujukan.setSelectedItem(waktu[0]);
            MenitRujukan.setSelectedItem(waktu[1]);

            // DEBUG STATUS RUJUKAN SECARA DETAIL
            Object statusObj = tbRujukan.getValueAt(selectedRow, 3);
            String status = statusObj != null ? statusObj.toString().trim() : "";

            System.out.println("=== DEBUG STATUS RUJUKAN ===");
            System.out.println("Raw status object: [" + statusObj + "]");
            System.out.println("Status string: [" + status + "]");
            System.out.println("Status length: " + status.length());
            System.out.println("Status equals 'Tidak Dirujuk': " + status.equals("Tidak Dirujuk"));
            System.out.println("Status equals 'Dirujuk': " + status.equals("Dirujuk"));

            // Cek karakter tersembunyi
            for (int i = 0; i < status.length(); i++) {
                System.out.println("Char " + i + ": '" + status.charAt(i) + "' (ASCII: " + (int) status.charAt(i) + ")");
            }

            boolean isRujuk = !status.equals("Tidak Dirujuk");
            System.out.println("isRujuk calculated: " + isRujuk);

            // SET STATUS RUJUKAN
            StatusRujukan.setSelectedItem(status);

            // VERIFIKASI STATUS SETELAH DI-SET
            String currentStatus = StatusRujukan.getSelectedItem() != null
                    ? StatusRujukan.getSelectedItem().toString() : "null";
            System.out.println("Status after setSelectedItem: [" + currentStatus + "]");

            // ENABLE KOMPONEN BERDASARKAN STATUS YANG BENAR
            // Gunakan status dari combo box untuk memastikan konsistensi
            boolean finalIsRujuk = !currentStatus.equals("Tidak Dirujuk");
            System.out.println("Final isRujuk: " + finalIsRujuk);

            // ENABLE KOMPONEN
            TanggalRujukan.setEnabled(finalIsRujuk);
            JamRujukan.setEnabled(finalIsRujuk);
            MenitRujukan.setEnabled(finalIsRujuk);
            AlasanRujukan.setEnabled(finalIsRujuk);
            TempatTujuanRujukan.setEnabled(finalIsRujuk);
            TempatRujukanLain.setEnabled(finalIsRujuk);
            TransportasiRujukan.setEnabled(finalIsRujuk);
            BiayaTransportasi.setEnabled(finalIsRujuk);
            PendampingRujukan.setEnabled(finalIsRujuk);
            NamaPendamping.setEnabled(finalIsRujuk);
            NoTelpRujukan.setEnabled(finalIsRujuk);
            KondisiSaatRujukan.setEnabled(finalIsRujuk);
            CatatanRujukan.setEnabled(finalIsRujuk);

            // LOAD DATA SETELAH KOMPONEN DIAKTIFKAN
            if (finalIsRujuk) {
                AlasanRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 4).toString());
                AlasanLainnya.setText(tbRujukan.getValueAt(selectedRow, 5).toString());
                TempatTujuanRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 6).toString());
                TempatRujukanLain.setText(tbRujukan.getValueAt(selectedRow, 7).toString());
                TransportasiRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 8).toString());
                BiayaTransportasi.setText(tbRujukan.getValueAt(selectedRow, 9).toString());
                PendampingRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 10).toString());
                NamaPendamping.setText(tbRujukan.getValueAt(selectedRow, 11).toString());
                NoTelpRujukan.setText(tbRujukan.getValueAt(selectedRow, 12).toString());
                KondisiSaatRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 13).toString());
                CatatanRujukan.setText(tbRujukan.getValueAt(selectedRow, 14).toString());

                // HANDLE ALASAN LAINNYA
                String alasanRujukan = tbRujukan.getValueAt(selectedRow, 4).toString();
                boolean isLainLain = alasanRujukan.equals("Lain-lain");
                AlasanLainnya.setEnabled(finalIsRujuk && isLainLain);
            }

            // SET NIP DAN PETUGAS
            NIPPetugas.setText(tbRujukan.getValueAt(selectedRow, 15).toString());
            TPetugas.setText(tbRujukan.getValueAt(selectedRow, 16).toString());

            debugRujukanComponents();
        }
    }

// Method untuk debug ComboBox items
    private void debugComboBoxItems() {
        System.out.println("=== DEBUG COMBO BOX ITEMS ===");
        System.out.println("StatusRujukan items:");
        for (int i = 0; i < StatusRujukan.getItemCount(); i++) {
            Object item = StatusRujukan.getItemAt(i);
            System.out.println("  Item " + i + ": [" + item + "]");
        }
    }

// Enhanced debug method
    private void debugRujukanComponents() {
        System.out.println("=== DEBUG RUJUKAN COMPONENTS ===");
        Object selected = StatusRujukan.getSelectedItem();
        System.out.println("StatusRujukan selected: [" + selected + "]");
        System.out.println("StatusRujukan selected type: " + (selected != null ? selected.getClass().getSimpleName() : "null"));
        System.out.println("TanggalRujukan enabled: " + TanggalRujukan.isEnabled());
        System.out.println("AlasanRujukan enabled: " + AlasanRujukan.isEnabled());
        System.out.println("AlasanLainnya enabled: " + AlasanLainnya.isEnabled());
        System.out.println("TempatRujukanLain enabled: " + TempatRujukanLain.isEnabled());

        // Debug tambahan untuk memahami mengapa status menjadi "Tidak Dirujuk"
        debugComboBoxItems();
    }

// ALTERNATIF SOLUSI 2: Menggunakan method terpisah
    private void getDataRujukanAlternatif() {
        if (tbRujukan.getSelectedRow() != -1) {
            int selectedRow = tbRujukan.getSelectedRow();

            // SET DATA DASAR
            TNoRw.setText(tbRujukan.getValueAt(selectedRow, 0).toString());
            isPsien();
            Valid.SetTgl(TanggalRujukan, tbRujukan.getValueAt(selectedRow, 1).toString());

            String[] waktu = tbRujukan.getValueAt(selectedRow, 2).toString().split(":");
            JamRujukan.setSelectedItem(waktu[0]);
            MenitRujukan.setSelectedItem(waktu[1]);

            // SET STATUS RUJUKAN TANPA TRIGGER EVENT
            setStatusRujukanSilent(tbRujukan.getValueAt(selectedRow, 3).toString());

            // LOAD SEMUA DATA
            loadRujukanData(selectedRow);

            // TERAKHIR SET ENABLE/DISABLE BERDASARKAN STATUS
            setRujukanComponentsEnabled();

            debugRujukanComponents();
        }
    }

// Method helper untuk set status tanpa trigger event
    private void setStatusRujukanSilent(String status) {
        // Temporary hapus listener jika ada
        ActionListener[] listeners = StatusRujukan.getActionListeners();
        for (ActionListener listener : listeners) {
            StatusRujukan.removeActionListener(listener);
        }

        StatusRujukan.setSelectedItem(status);

        // Tambahkan kembali listener
        for (ActionListener listener : listeners) {
            StatusRujukan.addActionListener(listener);
        }
    }

// Method untuk load data
    private void loadRujukanData(int selectedRow) {
        AlasanRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 4).toString());
        AlasanLainnya.setText(tbRujukan.getValueAt(selectedRow, 5).toString());
        TempatTujuanRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 6).toString());
        TempatRujukanLain.setText(tbRujukan.getValueAt(selectedRow, 7).toString());
        TransportasiRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 8).toString());
        BiayaTransportasi.setText(tbRujukan.getValueAt(selectedRow, 9).toString());
        PendampingRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 10).toString());
        NamaPendamping.setText(tbRujukan.getValueAt(selectedRow, 11).toString());
        NoTelpRujukan.setText(tbRujukan.getValueAt(selectedRow, 12).toString());
        KondisiSaatRujukan.setSelectedItem(tbRujukan.getValueAt(selectedRow, 13).toString());
        CatatanRujukan.setText(tbRujukan.getValueAt(selectedRow, 14).toString());
        NIPPetugas.setText(tbRujukan.getValueAt(selectedRow, 15).toString());
        TPetugas.setText(tbRujukan.getValueAt(selectedRow, 16).toString());
    }

// Method untuk set enable/disable komponen
    private void setRujukanComponentsEnabled() {
        String status = StatusRujukan.getSelectedItem().toString();
        boolean isRujuk = !status.equals("Tidak Dirujuk");

        TanggalRujukan.setEnabled(isRujuk);
        JamRujukan.setEnabled(isRujuk);
        MenitRujukan.setEnabled(isRujuk);
        AlasanRujukan.setEnabled(isRujuk);
        TempatTujuanRujukan.setEnabled(isRujuk);
        TempatRujukanLain.setEnabled(isRujuk);
        TransportasiRujukan.setEnabled(isRujuk);
        BiayaTransportasi.setEnabled(isRujuk);
        PendampingRujukan.setEnabled(isRujuk);
        NamaPendamping.setEnabled(isRujuk);
        NoTelpRujukan.setEnabled(isRujuk);
        KondisiSaatRujukan.setEnabled(isRujuk);
        CatatanRujukan.setEnabled(isRujuk);

        // Handle alasan lainnya
        String alasanRujukan = AlasanRujukan.getSelectedItem().toString();
        boolean isLainLain = alasanRujukan.equals("Lain-lain");
        AlasanLainnya.setEnabled(isRujuk && isLainLain);
    }

    private void enableRujukanComponents() {
        // AKTIFKAN SEMUA KOMPONEN RUJUKAN
        StatusRujukan.setEnabled(true);
        TanggalRujukan.setEnabled(true);
        JamRujukan.setEnabled(true);
        MenitRujukan.setEnabled(true);
        AlasanRujukan.setEnabled(true);
        AlasanLainnya.setEnabled(true);
        TempatTujuanRujukan.setEnabled(true);
        TempatRujukanLain.setEnabled(true);
        TransportasiRujukan.setEnabled(true);
        BiayaTransportasi.setEnabled(true);
        PendampingRujukan.setEnabled(true);
        NamaPendamping.setEnabled(true);
        NoTelpRujukan.setEnabled(true);
        KondisiSaatRujukan.setEnabled(true);
        CatatanRujukan.setEnabled(true);
    }

    private void StatusRujukanActionPerformed(java.awt.event.ActionEvent evt) {
        // Enable/disable fields based on status rujukan
        boolean isRujuk = !StatusRujukan.getSelectedItem().toString().equals("Tidak Dirujuk");

        TanggalRujukan.setEnabled(isRujuk);
        JamRujukan.setEnabled(isRujuk);
        MenitRujukan.setEnabled(isRujuk);
        AlasanRujukan.setEnabled(isRujuk);
        TempatTujuanRujukan.setEnabled(isRujuk);
        TempatRujukanLain.setEnabled(isRujuk);
        TransportasiRujukan.setEnabled(isRujuk);
        BiayaTransportasi.setEnabled(isRujuk);
        PendampingRujukan.setEnabled(isRujuk);
        NamaPendamping.setEnabled(isRujuk);
        NoTelpRujukan.setEnabled(isRujuk);
        KondisiSaatRujukan.setEnabled(isRujuk);
        CatatanRujukan.setEnabled(isRujuk);

        // Handle AlasanLainnya
        if (isRujuk && AlasanRujukan.getSelectedItem() != null) {
            AlasanLainnya.setEnabled(AlasanRujukan.getSelectedItem().toString().equals("Lain-lain"));
        } else {
            AlasanLainnya.setEnabled(false);
        }

        // Set tanggal dan jam default hanya jika bukan dari load data dan adalah rujukan
        if (isRujuk && evt != null) { // evt != null berarti dipanggil dari user action, bukan dari getDataRujukan
            TanggalRujukan.setDate(new Date());
            setCurrentTimeRujukan();

            // Clear data lainnya jika berubah ke rujukan
            AlasanRujukan.setSelectedIndex(0);
            AlasanLainnya.setText("");
            TempatTujuanRujukan.setSelectedIndex(0);
            TempatRujukanLain.setText("");
            TransportasiRujukan.setSelectedIndex(0);
            BiayaTransportasi.setText("");
            PendampingRujukan.setSelectedIndex(0);
            NamaPendamping.setText("");
            NoTelpRujukan.setText("");
            KondisiSaatRujukan.setSelectedIndex(0);
            CatatanRujukan.setText("");
        }
    }

    private void tampilRujukan() {
        Valid.tabelKosong(tabModeRujukan);
        try {
            String sql = "SELECT pr.no_rawat, pr.tanggal, pr.jam, pr.status_rujukan, "
                    + "pr.alasan_rujukan, pr.alasan_lainnya, pr.tempat_tujuan, pr.nama_tempat, "
                    + "pr.transportasi, pr.biaya_transportasi, pr.pendamping, pr.nama_pendamping, "
                    + "pr.no_telp, pr.kondisi_saat_rujukan, pr.catatan, pr.nip, pet.nama "
                    + "FROM partograf_rujukan pr "
                    + "LEFT JOIN petugas pet ON pr.nip = pet.nip "
                    + "WHERE pr.tanggal BETWEEN ? AND ? ";

            if (!TCari.getText().trim().equals("")) {
                sql += "AND (pr.no_rawat LIKE ? OR pet.nama LIKE ?) ";
            }

            sql += "ORDER BY pr.tanggal DESC, pr.jam DESC";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

            if (!TCari.getText().trim().equals("")) {
                ps.setString(3, "%" + TCari.getText() + "%");
                ps.setString(4, "%" + TCari.getText() + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                tabModeRujukan.addRow(new String[]{
                    rs.getString("no_rawat"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getString("status_rujukan"),
                    rs.getString("alasan_rujukan"),
                    rs.getString("alasan_lainnya"),
                    rs.getString("tempat_tujuan"),
                    rs.getString("nama_tempat"),
                    rs.getString("transportasi"),
                    rs.getString("biaya_transportasi"),
                    rs.getString("pendamping"),
                    rs.getString("nama_pendamping"),
                    rs.getString("no_telp"),
                    rs.getString("kondisi_saat_rujukan"),
                    rs.getString("catatan"),
                    rs.getString("nip"),
                    rs.getString("nama")
                });
            }

            LCount.setText("" + tabModeRujukan.getRowCount());
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error tampil rujukan: " + e.getMessage());
        }
    }

    private void emptTeksRujukan() {
        StatusRujukan.setSelectedIndex(0);
        TanggalRujukan.setDate(new Date());
        JamRujukan.setSelectedIndex(0);
        MenitRujukan.setSelectedIndex(0);
        AlasanRujukan.setSelectedIndex(0);
        AlasanLainnya.setText("");
        TempatTujuanRujukan.setSelectedIndex(0);
        TempatRujukanLain.setText("");
        TransportasiRujukan.setSelectedIndex(0);
        BiayaTransportasi.setText("");
        PendampingRujukan.setSelectedIndex(0);
        NamaPendamping.setText("");
        NoTelpRujukan.setText("");
        KondisiSaatRujukan.setSelectedIndex(0);
        CatatanRujukan.setText("");
    }

// ========== SETUP TABLE MODEL RUJUKAN ==========
    private void setupTableModelRujukan() {
        tabModeRujukan = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Tanggal", "Jam", "Status", "Alasan", "Ket.Alasan",
            "Tempat Tujuan", "Nama Tempat", "Transportasi", "Biaya", "Pendamping",
            "Nama Pendamping", "No.Telp", "Kondisi", "Catatan", "NIP", "Petugas"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbRujukan.setModel(tabModeRujukan);
        tbRujukan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRujukan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbRujukan
                .setDefaultRenderer(Object.class,
                        new WarnaTable());
    }

    private void hapusRujukan() {
        if (tbRujukan.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin data rujukan akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tbRujukan.getSelectedRow();
                String noRawat = tbRujukan.getValueAt(selectedRow, 0).toString();
                String tanggal = tbRujukan.getValueAt(selectedRow, 1).toString();
                String jam = tbRujukan.getValueAt(selectedRow, 2).toString();

                if (Sequel.queryu2tf("DELETE FROM partograf_rujukan WHERE no_rawat=? AND tanggal=? AND jam=?",
                        3, new String[]{noRawat, tanggal, jam}) == true) {

                    tabModeRujukan.removeRow(selectedRow);
                    LCount.setText("" + tabModeRujukan.getRowCount());
                    emptTeksRujukan();
                    JOptionPane.showMessageDialog(null, "Data rujukan berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
                }

            } catch (Exception e) {
                System.out.println("Error delete rujukan: " + e);
                JOptionPane.showMessageDialog(null, "Error delete: " + e.getMessage());
            }
        }
    }

    private void gantiRujukan() {
        if (tbRujukan.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diganti!");
            return;
        }

        if (NIPPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        try {
            int selectedRow = tbRujukan.getSelectedRow();
            String oldNoRawat = tbRujukan.getValueAt(selectedRow, 0).toString();
            String oldTanggal = tbRujukan.getValueAt(selectedRow, 1).toString();
            String oldJam = tbRujukan.getValueAt(selectedRow, 2).toString();

            String updateSql = "UPDATE partograf_rujukan SET "
                    + "no_rawat=?, tanggal=?, jam=?, status_rujukan=?, alasan_rujukan=?, "
                    + "alasan_lainnya=?, tempat_tujuan=?, nama_tempat=?, transportasi=?, "
                    + "biaya_transportasi=?, pendamping=?, nama_pendamping=?, no_telp=?, "
                    + "kondisi_saat_rujukan=?, catatan=?, nip=? "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=?";

            if (Sequel.queryu2tf(updateSql, 19, new String[]{
                TNoRw.getText(),
                Valid.SetTgl(TanggalRujukan.getSelectedItem() + ""),
                JamRujukan.getSelectedItem() + ":" + MenitRujukan.getSelectedItem() + ":00",
                StatusRujukan.getSelectedItem().toString(),
                AlasanRujukan.getSelectedItem().toString(),
                AlasanLainnya.getText(),
                TempatTujuanRujukan.getSelectedItem().toString(),
                TempatRujukanLain.getText(),
                TransportasiRujukan.getSelectedItem().toString(),
                BiayaTransportasi.getText().equals("") ? "0" : BiayaTransportasi.getText(),
                PendampingRujukan.getSelectedItem().toString(),
                NamaPendamping.getText(),
                NoTelpRujukan.getText(),
                KondisiSaatRujukan.getSelectedItem().toString(),
                CatatanRujukan.getText(),
                NIPPetugas.getText(), // GUNAKAN NIP DARI HEADER
                oldNoRawat,
                oldTanggal,
                oldJam
            }) == true) {
                JOptionPane.showMessageDialog(null, "Data rujukan berhasil diupdate!");
                tampilRujukan();
                emptTeksRujukan();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate data!");
            }

        } catch (Exception e) {
            System.out.println("Error update rujukan: " + e);
            JOptionPane.showMessageDialog(null, "Error update: " + e.getMessage());
        }
    }

    private void hapusObservasiKalaIV() {
        // Method untuk hapus observasi kala IV
        if (tbObservasiKalaIV.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin data observasi kala IV akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tbObservasiKalaIV.getSelectedRow();
                String noRawat = tbObservasiKalaIV.getValueAt(selectedRow, 0).toString();
                String tanggal = tbObservasiKalaIV.getValueAt(selectedRow, 1).toString();
                String jam = tbObservasiKalaIV.getValueAt(selectedRow, 2).toString();

                if (Sequel.queryu2tf("DELETE FROM partograf_observasi_kala4 WHERE no_rawat=? AND tanggal=? AND jam=?",
                        3, new String[]{noRawat, tanggal, jam}) == true) {

                    tabModeObservasiKalaIV.removeRow(selectedRow);
                    LCount.setText("" + tabModeObservasiKalaIV.getRowCount());
                    emptTeksKalaIV();
                    JOptionPane.showMessageDialog(null, "Data observasi kala IV berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
                }

            } catch (Exception e) {
                System.out.println("Error delete observasi kala IV: " + e);
                JOptionPane.showMessageDialog(null, "Error delete: " + e.getMessage());
            }
        }
    }

    private void gantiObservasiKalaIV() {
        // Method untuk edit observasi kala IV
        if (tbObservasiKalaIV.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diganti!");
            return;
        }

        if (NIPPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        try {
            int selectedRow = tbObservasiKalaIV.getSelectedRow();
            String oldNoRawat = tbObservasiKalaIV.getValueAt(selectedRow, 0).toString();
            String oldTanggal = tbObservasiKalaIV.getValueAt(selectedRow, 1).toString();
            String oldJam = tbObservasiKalaIV.getValueAt(selectedRow, 2).toString();

            String updateSql = "UPDATE partograf_observasi_kala4 SET "
                    + "no_rawat=?, tanggal=?, jam=?, jam_ke=?, tekanan_darah=?, nadi=?, suhu=?, "
                    + "tfu=?, kontraksi_uterus=?, kandung_kemih=?, perdarahan=?, nip=? "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=?";

            if (Sequel.queryu2tf(updateSql, 15, new String[]{
                TNoRw.getText(),
                Valid.SetTgl(TglPartograf.getSelectedItem() + ""),
                getFormattedTime(),
                JamKe.getSelectedItem().toString(),
                TekananDarahKalaIV.getText(),
                NadiKalaIV.getText(),
                SuhuKalaIV.getText(),
                TFU.getText(),
                KontraksiUterusKalaIV.getText(),
                KandungKemih.getText(),
                PerdarahanKalaIV.getText(),
                NIPPetugas.getText(),
                oldNoRawat,
                oldTanggal,
                oldJam
            }) == true) {
                JOptionPane.showMessageDialog(null, "Data observasi kala IV berhasil diupdate!");
                tampilObservasiKalaIV();
                emptTeksKalaIV();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate data!");
            }

        } catch (Exception e) {
            System.out.println("Error update observasi kala IV: " + e);
            JOptionPane.showMessageDialog(null, "Error update: " + e.getMessage());
        }
    }

    private void emptTeksKalaIV() {
        if (JamKe != null) {
            JamKe.setSelectedIndex(0);
        }
        if (TekananDarahKalaIV != null) {
            TekananDarahKalaIV.setText("");
        }
        if (NadiKalaIV != null) {
            NadiKalaIV.setText("");
        }
        if (SuhuKalaIV != null) {
            SuhuKalaIV.setText("");
        }
        if (TFU != null) {
            TFU.setText("");
        }
        if (KontraksiUterusKalaIV != null) {
            KontraksiUterusKalaIV.setText("");
        }
        if (KandungKemih != null) {
            KandungKemih.setText("");
        }
        if (PerdarahanKalaIV != null) {
            PerdarahanKalaIV.setText("");
        }
    }

    private boolean testDatabaseConnection() {
        try {
            String testSql = "SELECT COUNT(*) FROM partograf_observasi_kala4 LIMIT 1";
            PreparedStatement psTest = koneksi.prepareStatement(testSql);
            ResultSet rsTest = psTest.executeQuery();
            boolean hasAccess = rsTest.next();
            rsTest.close();
            psTest.close();

            System.out.println("Database connection test: " + (hasAccess ? "SUCCESS" : "FAILED"));
            return hasAccess;

        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return false;
        }
    }

// DEBUGGING METHOD - untuk memastikan tab terlihat
    private void debugTabStatus() {
        System.out.println("=== TAB DEBUG INFO ===");
        if (TabRawat != null) {
            System.out.println("Total tabs: " + TabRawat.getTabCount());
            for (int i = 0; i < TabRawat.getTabCount(); i++) {
                System.out.println("Tab " + i + ": " + TabRawat.getTitleAt(i));
            }
            System.out.println("Selected tab: " + TabRawat.getSelectedIndex());
        } else {
            System.out.println("TabRawat is NULL!");
        }
    }

    private void debugSavedData(String noRawat, String tanggal, String jam, String jamKe) {
        try {
            String debugSql = "SELECT * FROM partograf_observasi_kala4 "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=? AND jam_ke=?";

            PreparedStatement psDebug = koneksi.prepareStatement(debugSql);
            psDebug.setString(1, noRawat);
            psDebug.setString(2, tanggal);
            psDebug.setString(3, jam);
            psDebug.setString(4, jamKe);

            ResultSet rsDebug = psDebug.executeQuery();
            if (rsDebug.next()) {
                System.out.println("=== DATA TERSIMPAN ===");
                System.out.println("No Rawat: " + rsDebug.getString("no_rawat"));
                System.out.println("Tanggal: " + rsDebug.getString("tanggal"));
                System.out.println("Jam: " + rsDebug.getString("jam"));
                System.out.println("Jam Ke: " + rsDebug.getString("jam_ke"));
                System.out.println("Tekanan Darah: " + rsDebug.getString("tekanan_darah"));
                System.out.println("Nadi: " + rsDebug.getString("nadi"));
                System.out.println("Suhu: " + rsDebug.getString("suhu"));
                System.out.println("TFU: " + rsDebug.getString("tfu"));
                System.out.println("Kontraksi: " + rsDebug.getString("kontraksi_uterus"));
                System.out.println("Kandung Kemih: " + rsDebug.getString("kandung_kemih"));
                System.out.println("Perdarahan: " + rsDebug.getString("perdarahan"));
                System.out.println("NIP: " + rsDebug.getString("nip"));
            } else {
                System.out.println("=== DATA TIDAK DITEMUKAN ===");
            }

            rsDebug.close();
            psDebug.close();

        } catch (Exception e) {
            System.out.println("Error debug: " + e.getMessage());
        }
    }

// ========== 10. PERBAIKAN VALIDASI INPUT ==========
    private boolean validateObservasiKalaIVInput() {
        // Validasi jam ke - harus unik per waktu
        try {
            String checkDuplikate = "SELECT COUNT(*) FROM partograf_observasi_kala4 "
                    + "WHERE no_rawat=? AND tanggal=? AND jam_ke=?";

            PreparedStatement ps = koneksi.prepareStatement(checkDuplikate);
            ps.setString(1, TNoRw.getText());
            ps.setString(2, Valid.SetTgl(TglPartograf.getSelectedItem() + ""));
            ps.setString(3, JamKe.getSelectedItem().toString());

            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null,
                        "Data observasi jam ke-" + JamKe.getSelectedItem() + " untuk hari ini sudah ada!");
                rs.close();
                ps.close();
                return false;
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error validasi: " + e.getMessage());
        }

        return true;
    }

    private void setupTableDataCatatanPersalinan() {
        tabModeDataCatatanPersalinan = new DefaultTableModel(null, new Object[]{
            "No.Rawat", // 0
            "Tanggal", // 1  
            "Jam", // 2
            "Petugas", // 3
            "Garis Waspada", // 4
            "Masalah Kala I", // 5
            "Episiotomi", // 6
            "Pendamping", // 7
            "Gawat Janin", // 8
            "Distosia Bahu", // 9
            "Lama Kala III", // 10
            "Oksitosin 100MI", // 11
            "Penegang Tali", // 12
            "Masase Fundus", // 13
            "Plasenta Lengkap", // 14
            "Laserasi", // 15
            "Atonia Uteri", // 16
            "Perdarahan (ml)", // 17
            "BB Bayi (gr)", // 18
            "PB Bayi (cm)", // 19
            "JK Bayi", // 20
            "Pemberian ASI", // 21
            "Status" // 22 - Status lengkap atau tidak
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbDataCatatanPersalinan.setModel(tabModeDataCatatanPersalinan);
        tbDataCatatanPersalinan.setPreferredScrollableViewportSize(new Dimension(800, 500));
        tbDataCatatanPersalinan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbDataCatatanPersalinan.setDefaultRenderer(Object.class, new WarnaTable());

        // Set column widths
        int[] columnWidths = {105, 80, 60, 120, 90, 150, 70, 100, 80, 90, 80, 90, 90, 90, 90, 70, 80, 90, 80, 80, 50, 80, 80};
        for (int i = 0; i < columnWidths.length && i < tbDataCatatanPersalinan.getColumnCount(); i++) {
            tbDataCatatanPersalinan.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

// PERBAIKAN: Method tampilDataCatatanPersalinan dengan query lengkap
    private void tampilDataCatatanPersalinan() {
        Valid.tabelKosong(tabModeDataCatatanPersalinan);
        try {
            String sql = "SELECT pc.no_rawat, pc.tanggal, pc.jam, pet.nama as petugas, "
                    + "pc.garis_waspada, "
                    + "CASE WHEN pc.masalah_kala1 IS NOT NULL AND pc.masalah_kala1 != '' "
                    + "     THEN CONCAT(SUBSTRING(pc.masalah_kala1, 1, 50), '...') "
                    + "     ELSE '-' END as masalah_kala1_summary, "
                    + "pc.episiotomi, pc.pendamping, pc.gawat_janin, pc.distosia_bahu, "
                    + "COALESCE(pc.lama_kala3, 0) as lama_kala3, "
                    + "pc.oksitosin_100, pc.penegang_tali, pc.masase_fundus, pc.plasenta_lengkap, "
                    + "pc.laserasi, pc.atonia_uteri, "
                    + "COALESCE(pc.jumlah_perdarahan, 0) as jumlah_perdarahan, "
                    + "COALESCE(pc.berat_badan_bayi, 0) as berat_badan_bayi, "
                    + "COALESCE(pc.panjang_badan_bayi, 0) as panjang_badan_bayi, "
                    + "pc.jenis_kelamin_bayi, pc.pemberian_asi, "
                    + "CASE WHEN (pc.masalah_kala1 IS NOT NULL AND pc.masalah_kala1 != '') "
                    + "          OR (pc.masalah_kala2 IS NOT NULL AND pc.masalah_kala2 != '') "
                    + "          OR (pc.masalah_kala3 IS NOT NULL AND pc.masalah_kala3 != '') "
                    + "          OR (pc.masalah_lain_bayi IS NOT NULL AND pc.masalah_lain_bayi != '') "
                    + "     THEN 'Ada Masalah' ELSE 'Normal' END as status_lengkap "
                    + "FROM partograf_catatan_persalinan pc "
                    + "LEFT JOIN petugas pet ON pc.nip = pet.nip "
                    + "WHERE pc.tanggal BETWEEN ? AND ? ";

            if (!TCari.getText().trim().equals("")) {
                sql += "AND (pc.no_rawat LIKE ? OR pet.nama LIKE ? OR pc.masalah_kala1 LIKE ? OR pc.masalah_kala2 LIKE ?) ";
            }

            sql += "ORDER BY pc.tanggal DESC, pc.jam DESC";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

            if (!TCari.getText().trim().equals("")) {
                String searchTerm = "%" + TCari.getText() + "%";
                ps.setString(3, searchTerm);
                ps.setString(4, searchTerm);
                ps.setString(5, searchTerm);
                ps.setString(6, searchTerm);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                tabModeDataCatatanPersalinan.addRow(new String[]{
                    rs.getString("no_rawat"), // 0
                    rs.getString("tanggal"), // 1  
                    rs.getString("jam"), // 2
                    rs.getString("petugas") != null ? rs.getString("petugas") : "-", // 3
                    rs.getString("garis_waspada"), // 4
                    rs.getString("masalah_kala1_summary"), // 5
                    rs.getString("episiotomi"), // 6
                    rs.getString("pendamping"), // 7
                    rs.getString("gawat_janin"), // 8
                    rs.getString("distosia_bahu"), // 9
                    rs.getInt("lama_kala3") > 0 ? rs.getInt("lama_kala3") + " menit" : "-", // 10
                    rs.getString("oksitosin_100"), // 11
                    rs.getString("penegang_tali"), // 12
                    rs.getString("masase_fundus"), // 13
                    rs.getString("plasenta_lengkap"), // 14
                    rs.getString("laserasi"), // 15
                    rs.getString("atonia_uteri"), // 16
                    rs.getInt("jumlah_perdarahan") > 0 ? rs.getInt("jumlah_perdarahan") + " ml" : "-", // 17
                    rs.getInt("berat_badan_bayi") > 0 ? rs.getInt("berat_badan_bayi") + " gr" : "-", // 18
                    rs.getInt("panjang_badan_bayi") > 0 ? rs.getInt("panjang_badan_bayi") + " cm" : "-", // 19
                    rs.getString("jenis_kelamin_bayi") != null ? rs.getString("jenis_kelamin_bayi") : "-", // 20
                    rs.getString("pemberian_asi"), // 21
                    rs.getString("status_lengkap") // 22
                });
            }

            LCount.setText("" + tabModeDataCatatanPersalinan.getRowCount());
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error tampil data catatan persalinan: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error menampilkan data: " + e.getMessage());
        }
    }

// 11. HELPER METHOD UNTUK TOGGLE FORM
    private void isFormMasalahKalaIV() {
        // Implementasi toggle visibility form (optional)
    }

// 12. MODIFIKASI METHOD INITKALATIV() - PANGGIL INIT MASALAH KALA IV
    public void initKalaIV() {
        initObservasiKalaIV();
        initMasalahKalaIV();  // TAMBAHKAN INI
        initComboBoxes();
        initTextFields();
        initEvaluasiPostpartum();
    }

    private String getTextSafe(widget.TextBox textBox) {
        if (textBox != null && textBox.getText() != null) {
            String text = textBox.getText().trim();
            return text.isEmpty() ? "" : text; // Return empty string, bukan null
        }
        return "";
    }

    // Method untuk numeric dengan default
    private String getNumericSafe(widget.TextBox textBox, String defaultValue) {
        if (textBox != null && textBox.getText() != null) {
            String text = textBox.getText().trim();
            if (text.isEmpty()) {
                return defaultValue;
            }

            try {
                // Validasi numeric
                Double.parseDouble(text);
                return text;
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private boolean validateKalaIVData() {
        // Validasi field wajib dengan pesan yang jelas
        if (TekananDarahKalaIV.getText().trim().isEmpty()) {
            Valid.textKosong(TekananDarahKalaIV, "Tekanan Darah");
            TekananDarahKalaIV.requestFocus();
            return false;
        }

        if (NadiKalaIV.getText().trim().isEmpty()) {
            Valid.textKosong(NadiKalaIV, "Nadi");
            NadiKalaIV.requestFocus();
            return false;
        }

        if (SuhuKalaIV.getText().trim().isEmpty()) {
            Valid.textKosong(SuhuKalaIV, "Suhu");
            SuhuKalaIV.requestFocus();
            return false;
        }

        if (TFU.getText().trim().isEmpty()) {
            Valid.textKosong(TFU, "TFU");
            TFU.requestFocus();
            return false;
        }

        // Validasi format numeric
        try {
            if (!NadiKalaIV.getText().trim().isEmpty()) {
                Double.parseDouble(NadiKalaIV.getText().trim());
            }
            if (!SuhuKalaIV.getText().trim().isEmpty()) {
                Double.parseDouble(SuhuKalaIV.getText().trim());
            }
            if (!TFU.getText().trim().isEmpty()) {
                Double.parseDouble(TFU.getText().trim());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Format angka tidak valid! Periksa input Nadi, Suhu, dan TFU.");
            return false;
        }

        return true;
    }

    private void initEvaluasiPostpartum() {
        // Inisialisasi tabel model - menggunakan tabel database yang sama
        tabModeEvaluasiPostpartum = new DefaultTableModel(null, new Object[]{
            "No.Rawat", // 0
            "Tanggal", // 1
            "Jam", // 2
            "Kala Rujukan", // 3
            "Pendamping", // 4
            "Alasan", // 5
            "Tempat", // 6
            "Masalah", // 7
            "Tindakan", // 8
            "Hasil", // 9
            "NIP", // 10
            "Petugas" // 11
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        // Set model ke tabel
        if (tbEvaluasiPostpartum != null) {
            tbEvaluasiPostpartum.setModel(tabModeEvaluasiPostpartum);
            tbEvaluasiPostpartum.setPreferredScrollableViewportSize(new Dimension(500, 500));
            tbEvaluasiPostpartum.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tbEvaluasiPostpartum.setDefaultRenderer(Object.class, new WarnaTable());

            // Set lebar kolom
            tbEvaluasiPostpartum.getColumnModel().getColumn(0).setPreferredWidth(100); // No.Rawat
            tbEvaluasiPostpartum.getColumnModel().getColumn(1).setPreferredWidth(80);  // Tanggal
            tbEvaluasiPostpartum.getColumnModel().getColumn(2).setPreferredWidth(60);  // Jam
            tbEvaluasiPostpartum.getColumnModel().getColumn(3).setPreferredWidth(80);  // Kala Rujukan
            tbEvaluasiPostpartum.getColumnModel().getColumn(4).setPreferredWidth(100); // Pendamping
            tbEvaluasiPostpartum.getColumnModel().getColumn(5).setPreferredWidth(200); // Alasan
            tbEvaluasiPostpartum.getColumnModel().getColumn(6).setPreferredWidth(150); // Tempat
            tbEvaluasiPostpartum.getColumnModel().getColumn(7).setPreferredWidth(200); // Masalah
            tbEvaluasiPostpartum.getColumnModel().getColumn(8).setPreferredWidth(200); // Tindakan
            tbEvaluasiPostpartum.getColumnModel().getColumn(9).setPreferredWidth(200); // Hasil
            tbEvaluasiPostpartum.getColumnModel().getColumn(10).setPreferredWidth(60); // NIP
            tbEvaluasiPostpartum.getColumnModel().getColumn(11).setPreferredWidth(120); // Petugas
        }
    }

// ========== SETUP TAB EVALUASI POSTPARTUM ==========
    private void setupTabEvaluasiPostpartum() {
        internalFrameEvaluasi = new widget.InternalFrame();
        internalFrameEvaluasi.setBorder(null);
        internalFrameEvaluasi.setName("internalFrameEvaluasi");
        internalFrameEvaluasi.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== PANEL INPUT ==========
        PanelInputEvaluasi = new javax.swing.JPanel();
        PanelInputEvaluasi.setName("PanelInputEvaluasi");
        PanelInputEvaluasi.setOpaque(false);
        PanelInputEvaluasi.setPreferredSize(new java.awt.Dimension(192, 350));
        PanelInputEvaluasi.setLayout(new java.awt.BorderLayout(1, 1));

        // ========== FORM CONTENT SCROLL PANE ==========
        scrollPaneEvaluasi = new widget.ScrollPane();
        scrollPaneEvaluasi.setName("scrollPaneEvaluasi");
        scrollPaneEvaluasi.setPreferredSize(new java.awt.Dimension(46, 320));

        panelGlassEvaluasi = new widget.panelisi();
        panelGlassEvaluasi.setName("panelGlassEvaluasi");
        panelGlassEvaluasi.setPreferredSize(new java.awt.Dimension(44, 300));
        panelGlassEvaluasi.setLayout(null);

        // Setup form evaluasi
        setupFormEvaluasiPostpartum();

        scrollPaneEvaluasi.setViewportView(panelGlassEvaluasi);
        PanelInputEvaluasi.add(scrollPaneEvaluasi, java.awt.BorderLayout.PAGE_START);

        // ========== CHECKBOX INPUT ==========
        widget.CekBox ChkInputEvaluasi = new widget.CekBox();
        ChkInputEvaluasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInputEvaluasi.setMnemonic('E');
        ChkInputEvaluasi.setText(".: Input Data Evaluasi Postpartum");
        ChkInputEvaluasi.setToolTipText("Alt+E");
        ChkInputEvaluasi.setBorderPainted(true);
        ChkInputEvaluasi.setBorderPaintedFlat(true);
        ChkInputEvaluasi.setFocusable(false);
        ChkInputEvaluasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInputEvaluasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInputEvaluasi.setName("ChkInputEvaluasi");
        ChkInputEvaluasi.setPreferredSize(new java.awt.Dimension(220, 20));
        ChkInputEvaluasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputEvaluasiActionPerformed(evt);
            }
        });
        PanelInputEvaluasi.add(ChkInputEvaluasi, java.awt.BorderLayout.PAGE_END);

        internalFrameEvaluasi.add(PanelInputEvaluasi, java.awt.BorderLayout.PAGE_START);

        // ========== TABLE EVALUASI ==========
        ScrollEvaluasiPostpartum = new widget.ScrollPane();
        ScrollEvaluasiPostpartum.setName("ScrollEvaluasiPostpartum");
        ScrollEvaluasiPostpartum.setOpaque(true);

        tbEvaluasiPostpartum = new widget.Table();
        tbEvaluasiPostpartum.setName("tbEvaluasiPostpartum");
        tbEvaluasiPostpartum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEvaluasiPostpartumMouseClicked(evt);
            }
        });
        tbEvaluasiPostpartum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbEvaluasiPostpartumKeyPressed(evt);
            }
        });
        ScrollEvaluasiPostpartum.setViewportView(tbEvaluasiPostpartum);

        internalFrameEvaluasi.add(ScrollEvaluasiPostpartum, java.awt.BorderLayout.CENTER);

        // ========== ADD TAB ==========
        TabRawat.addTab("Masalah Kala IV", internalFrameEvaluasi);
    }

// ========== SETUP FORM EVALUASI ==========
    private void setupFormEvaluasiPostpartum() {
        // Header
        widget.Label lblHeaderEvaluasi = new widget.Label();
        lblHeaderEvaluasi.setText("MASALAH  KALA IV");
        lblHeaderEvaluasi.setName("lblHeaderEvaluasi");
        lblHeaderEvaluasi.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHeaderEvaluasi.setForeground(new Color(0, 100, 0));
        panelGlassEvaluasi.add(lblHeaderEvaluasi);
        lblHeaderEvaluasi.setBounds(20, 10, 400, 23);

        // Baris 1: Kala Rujukan, Pendamping
        widget.Label lblKalaRujukan = new widget.Label();
        lblKalaRujukan.setText("Kala Rujukan:");
        lblKalaRujukan.setName("lblKalaRujukan");
        panelGlassEvaluasi.add(lblKalaRujukan);
        lblKalaRujukan.setBounds(20, 40, 85, 23);

        KalaRujukanEvaluasi = new widget.ComboBox();
        KalaRujukanEvaluasi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "I", "II", "III", "IV"
        }));
        KalaRujukanEvaluasi.setName("KalaRujukanEvaluasi");
        panelGlassEvaluasi.add(KalaRujukanEvaluasi);
        KalaRujukanEvaluasi.setBounds(110, 40, 50, 23);

        widget.Label lblPendampingEval = new widget.Label();
        lblPendampingEval.setText("Pendamping:");
        lblPendampingEval.setName("lblPendampingEval");
        panelGlassEvaluasi.add(lblPendampingEval);
        lblPendampingEval.setBounds(180, 40, 80, 23);

        PendampingEvaluasi = new widget.ComboBox();
        PendampingEvaluasi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{
            "Bidan", "Dokter", "Perawat", "Keluarga", "Tidak Ada"
        }));
        PendampingEvaluasi.setName("PendampingEvaluasi");
        panelGlassEvaluasi.add(PendampingEvaluasi);
        PendampingEvaluasi.setBounds(265, 40, 100, 23);

        // Baris 2: Alasan
        widget.Label lblAlasanEval = new widget.Label();
        lblAlasanEval.setText("Alasan:");
        lblAlasanEval.setName("lblAlasanEval");
        panelGlassEvaluasi.add(lblAlasanEval);
        lblAlasanEval.setBounds(20, 70, 60, 23);

        AlasanEvaluasi = new widget.TextBox();
        AlasanEvaluasi.setName("AlasanEvaluasi");
        panelGlassEvaluasi.add(AlasanEvaluasi);
        AlasanEvaluasi.setBounds(85, 70, 525, 23);

        // Baris 3: Tempat
        widget.Label lblTempatEval = new widget.Label();
        lblTempatEval.setText("Tempat:");
        lblTempatEval.setName("lblTempatEval");
        panelGlassEvaluasi.add(lblTempatEval);
        lblTempatEval.setBounds(20, 100, 60, 23);

        TempatEvaluasi = new widget.TextBox();
        TempatEvaluasi.setName("TempatEvaluasi");
        panelGlassEvaluasi.add(TempatEvaluasi);
        TempatEvaluasi.setBounds(85, 100, 525, 23);

        // Baris 4: Masalah
        widget.Label lblMasalahEval = new widget.Label();
        lblMasalahEval.setText("Masalah:");
        lblMasalahEval.setName("lblMasalahEval");
        panelGlassEvaluasi.add(lblMasalahEval);
        lblMasalahEval.setBounds(20, 130, 60, 23);

        MasalahEvaluasi = new widget.TextBox();
        MasalahEvaluasi.setName("MasalahEvaluasi");
        panelGlassEvaluasi.add(MasalahEvaluasi);
        MasalahEvaluasi.setBounds(85, 130, 525, 23);

        // Baris 5: Tindakan
        widget.Label lblTindakanEval = new widget.Label();
        lblTindakanEval.setText("Tindakan:");
        lblTindakanEval.setName("lblTindakanEval");
        panelGlassEvaluasi.add(lblTindakanEval);
        lblTindakanEval.setBounds(20, 160, 70, 23);

        TindakanEvaluasi = new widget.TextBox();
        TindakanEvaluasi.setName("TindakanEvaluasi");
        panelGlassEvaluasi.add(TindakanEvaluasi);
        TindakanEvaluasi.setBounds(95, 160, 515, 23);

        // Baris 6: Hasil
        widget.Label lblHasilEval = new widget.Label();
        lblHasilEval.setText("Hasil:");
        lblHasilEval.setName("lblHasilEval");
        panelGlassEvaluasi.add(lblHasilEval);
        lblHasilEval.setBounds(20, 190, 50, 23);

        HasilEvaluasi = new widget.TextBox();
        HasilEvaluasi.setName("HasilEvaluasi");
        panelGlassEvaluasi.add(HasilEvaluasi);
        HasilEvaluasi.setBounds(75, 190, 535, 23);
    }

// ========== CRUD METHODS EVALUASI POSTPARTUM ==========
    private void simpanEvaluasiPostpartum() {
        if (NIPPetugas.getText().trim().equals("") || TPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
            return;
        }

        try {
            // Debug input values
            System.out.println("=== SIMPAN EVALUASI POSTPARTUM ===");
            System.out.println("No Rawat: " + TNoRw.getText());
            System.out.println("Tanggal: " + Valid.SetTgl(TglPartograf.getSelectedItem() + ""));
            System.out.println("Jam: " + getFormattedTime());
            System.out.println("Kala: " + KalaRujukanEvaluasi.getSelectedItem());
            System.out.println("Pendamping: " + PendampingEvaluasi.getSelectedItem());
            System.out.println("Alasan: " + AlasanEvaluasi.getText());
            System.out.println("Tempat: " + TempatEvaluasi.getText());
            System.out.println("Masalah: " + MasalahEvaluasi.getText());
            System.out.println("Tindakan: " + TindakanEvaluasi.getText());
            System.out.println("Hasil: " + HasilEvaluasi.getText());
            System.out.println("NIP: " + NIPPetugas.getText());

            // SQL INSERT menggunakan tabel partograf_masalah_kala4 yang sudah ada
            if (Sequel.menyimpantf("partograf_masalah_kala4",
                    "?,?,?,?,?,?,?,?,?,?,?", // 11 placeholder
                    "Data Evaluasi Postpartum", 11, new String[]{
                        TNoRw.getText(), // 1. no_rawat
                        Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2. tanggal
                        getFormattedTime(), // 3. jam
                        KalaRujukanEvaluasi.getSelectedItem().toString(), // 4. catatan_rujukan_kala
                        PendampingEvaluasi.getSelectedItem().toString(), // 5. pendamping_saat_merujuk
                        AlasanEvaluasi.getText(), // 6. alasan_merujuk
                        TempatEvaluasi.getText(), // 7. tempat_merujuk
                        MasalahEvaluasi.getText(), // 8. masalah_kala4
                        TindakanEvaluasi.getText(), // 9. penatalaksanaan_kala4
                        HasilEvaluasi.getText(), // 10. hasilnya_kala4
                        NIPPetugas.getText() // 11. nip
                    }) == true) {

                JOptionPane.showMessageDialog(null, "Data evaluasi postpartum berhasil disimpan!");
                tampilEvaluasiPostpartum();
                emptTeksEvaluasiPostpartum();

            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data evaluasi postpartum!");
            }

        } catch (Exception e) {
            System.out.println("Error simpan evaluasi: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void tampilEvaluasiPostpartum() {
        Valid.tabelKosong(tabModeEvaluasiPostpartum);
        try {
            String sql = "SELECT mk.no_rawat, mk.tanggal, mk.jam, "
                    + "mk.catatan_rujukan_kala, mk.pendamping_saat_merujuk, "
                    + "mk.alasan_merujuk, mk.tempat_merujuk, mk.masalah_kala4, "
                    + "mk.penatalaksanaan_kala4, mk.hasilnya_kala4, mk.nip, p.nama "
                    + "FROM partograf_masalah_kala4 mk "
                    + "LEFT JOIN petugas p ON mk.nip = p.nip "
                    + "WHERE mk.tanggal BETWEEN ? AND ? ";

            if (!TCari.getText().trim().equals("")) {
                sql += "AND (mk.no_rawat LIKE ? OR p.nama LIKE ? OR mk.masalah_kala4 LIKE ?) ";
            }

            sql += "ORDER BY mk.tanggal DESC, mk.jam DESC";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

            if (!TCari.getText().trim().equals("")) {
                String searchTerm = "%" + TCari.getText() + "%";
                ps.setString(3, searchTerm);
                ps.setString(4, searchTerm);
                ps.setString(5, searchTerm);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                tabModeEvaluasiPostpartum.addRow(new String[]{
                    rs.getString("no_rawat"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getString("catatan_rujukan_kala"),
                    rs.getString("pendamping_saat_merujuk"),
                    rs.getString("alasan_merujuk") != null ? rs.getString("alasan_merujuk") : "-",
                    rs.getString("tempat_merujuk") != null ? rs.getString("tempat_merujuk") : "-",
                    rs.getString("masalah_kala4") != null ? rs.getString("masalah_kala4") : "-",
                    rs.getString("penatalaksanaan_kala4") != null ? rs.getString("penatalaksanaan_kala4") : "-",
                    rs.getString("hasilnya_kala4") != null ? rs.getString("hasilnya_kala4") : "-",
                    rs.getString("nip"),
                    rs.getString("nama") != null ? rs.getString("nama") : "-"
                });
            }

            LCount.setText("" + tabModeEvaluasiPostpartum.getRowCount());
            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error tampil evaluasi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getDataEvaluasiPostpartum() {
        if (tbEvaluasiPostpartum.getSelectedRow() != -1) {
            int selectedRow = tbEvaluasiPostpartum.getSelectedRow();

            TNoRw.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 0).toString());
            isPsien();

            Valid.SetTgl(TglPartograf, tbEvaluasiPostpartum.getValueAt(selectedRow, 1).toString());
            parseJamToCombo(tbEvaluasiPostpartum.getValueAt(selectedRow, 2).toString());

            KalaRujukanEvaluasi.setSelectedItem(tbEvaluasiPostpartum.getValueAt(selectedRow, 3).toString());
            PendampingEvaluasi.setSelectedItem(tbEvaluasiPostpartum.getValueAt(selectedRow, 4).toString());
            AlasanEvaluasi.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 5).toString());
            TempatEvaluasi.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 6).toString());
            MasalahEvaluasi.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 7).toString());
            TindakanEvaluasi.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 8).toString());
            HasilEvaluasi.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 9).toString());
            NIPPetugas.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 10).toString());
            TPetugas.setText(tbEvaluasiPostpartum.getValueAt(selectedRow, 11).toString());
        }
    }

    private void emptTeksEvaluasiPostpartum() {
        KalaRujukanEvaluasi.setSelectedIndex(0);
        PendampingEvaluasi.setSelectedIndex(0);
        AlasanEvaluasi.setText("");
        TempatEvaluasi.setText("");
        MasalahEvaluasi.setText("");
        TindakanEvaluasi.setText("");
        HasilEvaluasi.setText("");
    }

    private void hapusEvaluasiPostpartum() {
        if (tbEvaluasiPostpartum.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin data evaluasi postpartum akan dihapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int selectedRow = tbEvaluasiPostpartum.getSelectedRow();
                String noRawat = tbEvaluasiPostpartum.getValueAt(selectedRow, 0).toString();
                String tanggal = tbEvaluasiPostpartum.getValueAt(selectedRow, 1).toString();
                String jam = tbEvaluasiPostpartum.getValueAt(selectedRow, 2).toString();

                if (Sequel.queryu2tf("DELETE FROM partograf_masalah_kala4 WHERE no_rawat=? AND tanggal=? AND jam=?",
                        3, new String[]{noRawat, tanggal, jam}) == true) {

                    tabModeEvaluasiPostpartum.removeRow(selectedRow);
                    LCount.setText("" + tabModeEvaluasiPostpartum.getRowCount());
                    emptTeksEvaluasiPostpartum();
                    JOptionPane.showMessageDialog(null, "Data evaluasi postpartum berhasil dihapus!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus data!");
                }

            } catch (Exception e) {
                System.out.println("Error delete evaluasi: " + e);
                JOptionPane.showMessageDialog(null, "Error delete: " + e.getMessage());
            }
        }
    }

    private void gantiEvaluasiPostpartum() {
        if (tbEvaluasiPostpartum.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diganti!");
            return;
        }

        if (NIPPetugas.getText().trim().equals("")) {
            Valid.textKosong(NIPPetugas, "Petugas");
            return;
        }

        try {
            int selectedRow = tbEvaluasiPostpartum.getSelectedRow();
            String oldNoRawat = tbEvaluasiPostpartum.getValueAt(selectedRow, 0).toString();
            String oldTanggal = tbEvaluasiPostpartum.getValueAt(selectedRow, 1).toString();
            String oldJam = tbEvaluasiPostpartum.getValueAt(selectedRow, 2).toString();

            String updateSql = "UPDATE partograf_masalah_kala4 SET "
                    + "no_rawat=?, tanggal=?, jam=?, catatan_rujukan_kala=?, "
                    + "pendamping_saat_merujuk=?, alasan_merujuk=?, tempat_merujuk=?, "
                    + "masalah_kala4=?, penatalaksanaan_kala4=?, hasilnya_kala4=?, nip=? "
                    + "WHERE no_rawat=? AND tanggal=? AND jam=?";

            if (Sequel.queryu2tf(updateSql, 14, new String[]{
                TNoRw.getText(), // 1
                Valid.SetTgl(TglPartograf.getSelectedItem() + ""), // 2
                getFormattedTime(), // 3
                KalaRujukanEvaluasi.getSelectedItem().toString(), // 4
                PendampingEvaluasi.getSelectedItem().toString(), // 5
                AlasanEvaluasi.getText(), // 6
                TempatEvaluasi.getText(), // 7
                MasalahEvaluasi.getText(), // 8
                TindakanEvaluasi.getText(), // 9
                HasilEvaluasi.getText(), // 10
                NIPPetugas.getText(), // 11
                oldNoRawat, // 12 - WHERE
                oldTanggal, // 13 - WHERE
                oldJam // 14 - WHERE
            }) == true) {
                JOptionPane.showMessageDialog(null, "Data evaluasi postpartum berhasil diupdate!");
                tampilEvaluasiPostpartum();
                emptTeksEvaluasiPostpartum();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate data!");
            }

        } catch (Exception e) {
            System.out.println("Error update evaluasi: " + e);
            JOptionPane.showMessageDialog(null, "Error update: " + e.getMessage());
        }
    }

// ========== EVENT HANDLERS ==========
    private void tbEvaluasiPostpartumMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabModeEvaluasiPostpartum.getRowCount() != 0) {
            try {
                getDataEvaluasiPostpartum();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbEvaluasiPostpartumKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabModeEvaluasiPostpartum.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataEvaluasiPostpartum();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void ChkInputEvaluasiActionPerformed(java.awt.event.ActionEvent evt) {
        // Toggle form visibility jika diperlukan
    }

    private String generatePartografImage() {
        try {
            // Query dengan kolom yang benar sesuai struktur database
            String queryPartograf = "SELECT DATE(tgl_perawatan) as tgl, jam_rawat, djj, air_ketuban, moulage, "
                    + "pembukaan_serviks, penurunan_kepala, kontraksi_freq, kontraksi_durasi, "
                    + "oksitosin, obat_lain, cairan_iv, nadi, tensi, suhu, protein_urin, volume_urin "
                    + "FROM partograf_kala12 WHERE no_rawat = '" + TNoRw.getText() + "' "
                    + "ORDER BY tgl_perawatan, jam_rawat";

            // Pastikan ada data sebelum membuat grafik
            PreparedStatement psCheck = koneksi.prepareStatement("SELECT COUNT(*) as total FROM partograf_kala12 WHERE no_rawat = ?");
            psCheck.setString(1, TNoRw.getText());
            ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next() && rsCheck.getInt("total") == 0) {
                rsCheck.close();
                psCheck.close();
                System.out.println("No data found for no_rawat: " + TNoRw.getText());
                return createEmptyPartografImage();
            }

            int dataCount = rsCheck.getInt("total");
            System.out.println("Found " + dataCount + " records for no_rawat: " + TNoRw.getText());

            rsCheck.close();
            psCheck.close();

            // Dapatkan panel grafik dengan parameter yang benar
            JPanel chartPanel = null;
            int attempts = 0;
            while (chartPanel == null && attempts < 3) {
                try {
                    chartPanel = RMPartografGrafik.createDemoPanel(
                            queryPartograf,
                            "djj", "air_ketuban", "moulage", "kontraksi_freq", // Parameter sesuai nama kolom
                            queryPartograf,
                            "pembukaan_serviks", "penurunan_kepala"
                    );
                    break; // Berhasil, keluar dari loop
                } catch (Exception e) {
                    System.out.println("Attempt " + (attempts + 1) + " failed: " + e.getMessage());
                    attempts++;
                    if (attempts >= 3) {
                        return createEmptyPartografImage();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return createEmptyPartografImage();
                    }
                }
            }

            if (chartPanel == null) {
                return createEmptyPartografImage();
            }

            // Set ukuran optimal untuk A4 landscape
            int width = 1400;
            int height = 1000;
            chartPanel.setSize(width, height);
            chartPanel.setPreferredSize(new Dimension(width, height));

            // Force layout update
            chartPanel.invalidate();
            chartPanel.validate();
            chartPanel.doLayout();

            // Wait for components to be ready
            final JPanel finalChartPanel = chartPanel;
            try {
                SwingUtilities.invokeAndWait(() -> {
                    finalChartPanel.repaint();
                });
            } catch (Exception e) {
                System.out.println("Error in SwingUtilities.invokeAndWait: " + e.getMessage());
            }

            // Buat BufferedImage dengan background putih
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Set background putih
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Set rendering hints untuk kualitas tinggi
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Render chart panel
            finalChartPanel.print(g2d);
            g2d.dispose();

            // Save debug image
            try {
                File tempImageFile = File.createTempFile("partograf_debug_", ".png");
                ImageIO.write(image, "PNG", tempImageFile);
                System.out.println("Debug image saved to: " + tempImageFile.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Could not save debug image: " + e.getMessage());
            }

            // Convert to Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return "data:image/png;base64," + base64Image;

        } catch (Exception e) {
            System.out.println("Error generating partograf image: " + e.getMessage());
            e.printStackTrace();
            return createEmptyPartografImage();
        }
    }

// Method generateObservasiSection dengan kolom yang benar
    private String generateObservasiSection() {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"section page-break\">\n")
                .append("    <div class=\"section-title\">OBSERVASI KALA I & II (DETAIL)</div>\n")
                .append("    <div class=\"section-content\">\n");

        try {
            String sql = "SELECT pk.*, pet.nama as nama_petugas "
                    + "FROM partograf_kala12 pk "
                    + "LEFT JOIN petugas pet ON pk.nip = pet.nip "
                    + "WHERE pk.no_rawat = ? "
                    + "ORDER BY pk.tgl_perawatan, pk.jam_rawat";

            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, TNoRw.getText());
            ResultSet rs = ps.executeQuery();

            html.append("        <table>\n")
                    .append("            <thead>\n")
                    .append("                <tr>\n")
                    .append("                    <th>Tanggal</th>\n")
                    .append("                    <th>Jam</th>\n")
                    .append("                    <th>DJJ<br>(bpm)</th>\n")
                    .append("                    <th>Air Ketuban</th>\n")
                    .append("                    <th>Moulage</th>\n")
                    .append("                    <th>Pembukaan<br>(cm)</th>\n")
                    .append("                    <th>Penurunan</th>\n")
                    .append("                    <th>Kontraksi<br>(frekuensi)</th>\n")
                    .append("                    <th>Durasi<br>(detik)</th>\n")
                    .append("                    <th>Tensi</th>\n")
                    .append("                    <th>Nadi</th>\n")
                    .append("                    <th>Suhu<br>(°C)</th>\n")
                    .append("                    <th>Protein Urin</th>\n")
                    .append("                    <th>Volume Urin</th>\n")
                    .append("                    <th>Petugas</th>\n")
                    .append("                </tr>\n")
                    .append("            </thead>\n")
                    .append("            <tbody>\n");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                html.append("                <tr>\n")
                        .append("                    <td>").append(rs.getString("tgl_perawatan") != null ? rs.getString("tgl_perawatan") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("jam_rawat") != null ? rs.getString("jam_rawat") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("djj") != null ? rs.getString("djj") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("air_ketuban") != null ? rs.getString("air_ketuban") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("moulage") != null ? rs.getString("moulage") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("pembukaan_serviks") != null ? rs.getString("pembukaan_serviks") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("penurunan_kepala") != null ? rs.getString("penurunan_kepala") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("kontraksi_freq") != null ? rs.getString("kontraksi_freq") + "/10m" : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("kontraksi_durasi") != null ? rs.getString("kontraksi_durasi") + "s" : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("tensi") != null ? rs.getString("tensi") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("nadi") != null ? rs.getString("nadi") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("suhu") != null ? rs.getString("suhu") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("protein_urin") != null ? rs.getString("protein_urin") : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("volume_urin") != null ? rs.getString("volume_urin") + "ml" : "-").append("</td>\n")
                        .append("                    <td>").append(rs.getString("nama_petugas") != null ? rs.getString("nama_petugas") : "-").append("</td>\n")
                        .append("                </tr>\n");
            }

            if (!hasData) {
                html.append("                <tr><td colspan=\"15\">Tidak ada data observasi tersimpan.</td></tr>\n");
            }

            html.append("            </tbody>\n")
                    .append("        </table>\n");

            // Tambahkan informasi obat jika ada
            if (hasData) {
                rs = ps.executeQuery(); // Reset ResultSet
                html.append("        <h4>OBAT DAN CAIRAN</h4>\n")
                        .append("        <table>\n")
                        .append("            <thead>\n")
                        .append("                <tr>\n")
                        .append("                    <th>Tanggal</th>\n")
                        .append("                    <th>Jam</th>\n")
                        .append("                    <th>Oksitosin</th>\n")
                        .append("                    <th>Obat Lain</th>\n")
                        .append("                    <th>Cairan IV</th>\n")
                        .append("                    <th>Catatan</th>\n")
                        .append("                </tr>\n")
                        .append("            </thead>\n")
                        .append("            <tbody>\n");

                while (rs.next()) {
                    html.append("                <tr>\n")
                            .append("                    <td>").append(rs.getString("tgl_perawatan") != null ? rs.getString("tgl_perawatan") : "-").append("</td>\n")
                            .append("                    <td>").append(rs.getString("jam_rawat") != null ? rs.getString("jam_rawat") : "-").append("</td>\n")
                            .append("                    <td>").append(rs.getString("oksitosin") != null && !rs.getString("oksitosin").trim().isEmpty() ? rs.getString("oksitosin") : "-").append("</td>\n")
                            .append("                    <td>").append(rs.getString("obat_lain") != null && !rs.getString("obat_lain").trim().isEmpty() ? rs.getString("obat_lain") : "-").append("</td>\n")
                            .append("                    <td>").append(rs.getString("cairan_iv") != null && !rs.getString("cairan_iv").trim().isEmpty() ? rs.getString("cairan_iv") : "-").append("</td>\n")
                            .append("                    <td>").append(rs.getString("catatan_kala12") != null && !rs.getString("catatan_kala12").trim().isEmpty() ? rs.getString("catatan_kala12") : "-").append("</td>\n")
                            .append("                </tr>\n");
                }

                html.append("            </tbody>\n")
                        .append("        </table>\n");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            html.append("        <p>Error loading observasi data: ").append(e.getMessage()).append("</p>\n");
        }

        html.append("    </div>\n")
                .append("</div>\n");
        return html.toString();
    }

// Method untuk generate identitas section dengan data yang benar
    private String generateIdentitasSection() {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"section\">\n")
                .append("    <div class=\"section-title\">DATA IDENTITAS IBU</div>\n")
                .append("    <div class=\"section-content\">\n");

        try {
            String sql = "SELECT pi.*, pet.nama as nama_petugas "
                    + "FROM partograf_identitas pi "
                    + "LEFT JOIN petugas pet ON pi.nip = pet.nip "
                    + "WHERE pi.no_rawat = ? ORDER BY pi.tanggal DESC, pi.jam DESC LIMIT 1";

            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, TNoRw.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                html.append("        <div style=\"display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin: 10px 0;\">\n")
                        .append("            <div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Berat Badan:</span> ")
                        .append(rs.getString("berat_badan")).append(" kg\n")
                        .append("                </div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Tinggi Badan:</span> ")
                        .append(rs.getString("tinggi_badan")).append(" cm\n")
                        .append("                </div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Gravida:</span> ")
                        .append(rs.getString("gravida")).append("\n")
                        .append("                </div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Para:</span> ")
                        .append(rs.getString("para")).append("\n")
                        .append("                </div>\n")
                        .append("            </div>\n")
                        .append("            <div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Abortus:</span> ")
                        .append(rs.getString("abortus")).append("\n")
                        .append("                </div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Riwayat Obstetri:</span> ")
                        .append(rs.getString("riwayat_obstetri")).append("\n")
                        .append("                </div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Risiko Tinggi:</span> ")
                        .append(rs.getString("risiko_tinggi")).append("\n")
                        .append("                </div>\n")
                        .append("                <div class=\"info-item\">\n")
                        .append("                    <span class=\"info-label\">Petugas:</span> ")
                        .append(rs.getString("nama_petugas")).append("\n")
                        .append("                </div>\n")
                        .append("            </div>\n")
                        .append("        </div>\n");

                // Informasi waktu mules dan pecah ketuban
                if (rs.getString("mules_sejak") != null || rs.getString("pecah_ketuban_sejak") != null) {
                    html.append("        <h4>INFORMASI WAKTU</h4>\n")
                            .append("        <div style=\"display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin: 10px 0;\">\n")
                            .append("            <div class=\"info-item\">\n")
                            .append("                <span class=\"info-label\">Mules Sejak:</span> ")
                            .append(rs.getString("mules_sejak") != null ? rs.getString("mules_sejak") : "-").append("\n")
                            .append("            </div>\n")
                            .append("            <div class=\"info-item\">\n")
                            .append("                <span class=\"info-label\">Pecah Ketuban:</span> ");

                    String pecahKetuban = rs.getString("pecah_ketuban_sejak");
                    if (pecahKetuban != null && !pecahKetuban.equals("1900-01-01 00:00:00")) {
                        html.append(pecahKetuban);
                    } else {
                        html.append("Belum pecah");
                    }
                    html.append("\n").append("            </div>\n")
                            .append("        </div>\n");
                }

                if (rs.getString("catatan") != null && !rs.getString("catatan").trim().isEmpty()) {
                    html.append("        <div class=\"info-item\" style=\"margin-top: 15px; padding: 10px; background: #f9f9f9; border-left: 4px solid #007bff;\">\n")
                            .append("            <span class=\"info-label\">Catatan:</span><br>\n")
                            .append("            ").append(rs.getString("catatan")).append("\n")
                            .append("        </div>\n");
                }
            } else {
                html.append("        <p>Tidak ada data identitas tersimpan.</p>\n");
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            html.append("        <p>Error loading identitas data: ").append(e.getMessage()).append("</p>\n");
        }

        html.append("    </div>\n")
                .append("</div>\n");
        return html.toString();
    }

    // Method untuk membuat gambar kosong jika tidak ada data atau error
    private String createEmptyPartografImage() {
        try {
            int width = 1400;
            int height = 1000;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Background putih
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Set rendering hints untuk kualitas yang baik
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Gambar border
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(50, 50, width - 100, height - 100);

            // Judul partograf
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 28));
            FontMetrics fm = g2d.getFontMetrics();
            String title = "PARTOGRAF WHO";
            int titleWidth = fm.stringWidth(title);
            g2d.drawString(title, (width - titleWidth) / 2, 120);

            // Pesan utama
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            fm = g2d.getFontMetrics();
            String message1 = "Belum ada data observasi untuk digambarkan dalam grafik partograf";
            int messageWidth = fm.stringWidth(message1);
            g2d.drawString(message1, (width - messageWidth) / 2, (height / 2) - 20);

            String message2 = "Silakan lengkapi data observasi kala I dan II terlebih dahulu";
            int message2Width = fm.stringWidth(message2);
            g2d.drawString(message2, (width - message2Width) / 2, (height / 2) + 20);

            // Gambar grid placeholder seperti partograf asli
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));

            // Grid horizontal
            int startX = 100;
            int endX = width - 100;
            int startY = 200;
            int endY = height - 200;
            int gridRows = 20;
            int gridCols = 24;

            for (int i = 0; i <= gridRows; i++) {
                int y = startY + (i * (endY - startY) / gridRows);
                g2d.drawLine(startX, y, endX, y);
            }

            // Grid vertikal
            for (int i = 0; i <= gridCols; i++) {
                int x = startX + (i * (endX - startX) / gridCols);
                g2d.drawLine(x, startY, x, endY);
            }

            // Label sumbu
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));

            // Label Y axis (DJJ)
            g2d.drawString("DJJ (bpm)", 10, startY + 50);
            g2d.drawString("200", 70, startY);
            g2d.drawString("100", 70, startY + (endY - startY) / 2);
            g2d.drawString("50", 75, endY);

            // Label X axis (Jam)
            g2d.drawString("Jam ke-", startX, endY + 30);
            for (int i = 0; i <= 12; i++) {
                int x = startX + (i * (endX - startX) / 12);
                g2d.drawString(String.valueOf(i), x - 5, endY + 15);
            }

            // Info tambahan
            g2d.setFont(new Font("Arial", Font.ITALIC, 14));
            fm = g2d.getFontMetrics();
            String info = "Pasien: " + TPasien.getText() + " | No. RM: " + TNoRM.getText() + " | No. Rawat: " + TNoRw.getText();
            int infoWidth = fm.stringWidth(info);
            g2d.drawString(info, (width - infoWidth) / 2, height - 50);

            g2d.dispose();

            // Convert ke Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return "data:image/png;base64," + base64Image;

        } catch (Exception e) {
            System.out.println("Error creating empty partograf image: " + e.getMessage());
            // Return minimal base64 image jika gagal total
            return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg==";
        }
    }

// Method alternatif yang lebih sederhana jika method di atas bermasalah
    private String createSimpleEmptyPartografImage() {
        try {
            int width = 800;
            int height = 600;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Background putih
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // Border
            g2d.setColor(Color.BLACK);
            g2d.drawRect(10, 10, width - 20, height - 20);

            // Text
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("GRAFIK PARTOGRAF", 300, 50);

            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("Tidak ada data untuk ditampilkan", 280, 300);
            g2d.drawString("No. Rawat: " + TNoRw.getText(), 50, 550);

            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

        } catch (Exception e) {
            return "";
        }
    }

}
