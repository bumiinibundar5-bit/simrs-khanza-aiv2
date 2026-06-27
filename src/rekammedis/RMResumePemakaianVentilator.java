/*
 * RMResumePemakaianVentilator.java
 * Form: Resume Pemakaian Ventilator Pasien BPJS Kesehatan - RSUD Kabupaten Mamuju
 *
 * REVISI TAMPILAN: mengikuti pola asli SIMRS Khanza (acuan: RMDataCatatanKeperawatanRalan.java)
 *  - Dialog undecorated, dibungkus widget.InternalFrame + TitledBorder
 *  - No.Rawat TIDAK dicari di dalam dialog ini. Pasien sudah dipilih sebelumnya
 *    di menu luar, lalu dialog ini dipanggil dan diisi lewat setNoRm(no_rawat, tgl)
 *    -> persis pola form referensi (tidak ada panel pencarian master di sini lagi).
 *  - Panel input (data pasien/ICU + input episode pasang-lepas) digabung jadi SATU
 *    panel collapsible (toggle ChkInput ".: Input Data"), field diposisikan absolute
 *    (setBounds) seperti FormInput pada form referensi.
 *  - Tabel episode ventilator (tbDetail) jadi elemen dominan di tengah (CENTER),
 *    tumbuh ke bawah setiap kali data baru disimpan.
 *  - HANYA SATU tombol "Simpan": sekali klik akan otomatis upsert header
 *    (resume_pemakaian_ventilator - insert kalau belum ada baris utk No.Rawat ini,
 *    update kalau sudah ada) DAN insert baris episode baru (detail_pemakaian_ventilator).
 *    Tombol "Ganti"/"Hapus" tetap khusus untuk mengubah/menghapus baris episode yang
 *    sedang dipilih di tabel.
 *
 * CATATAN:
 *  - File ini ditulis manual (tanpa file .form), tetap bisa di-compile seperti
 *    file .java biasa, tapi tidak tampil di NetBeans GUI Builder (Design view).
 *  - Pastikan 2 tabel (resume_pemakaian_ventilator, detail_pemakaian_ventilator)
 *    sudah dibuat di database, dan method akses.getresume_pemakaian_ventilator()
 *    sudah ditambahkan di class fungsi.akses.
 *  - File rptResumePemakaianVentilator.jasper untuk cetak belum dibuat.
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author Ipin - IT RSUD Mamuju
 */
public final class RMResumePemakaianVentilator extends javax.swing.JDialog {

    private final DefaultTableModel tabModeDetail;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String TANGGALMUNDUR = "yes";
    private boolean headerAda = false;

    private final DlgCariPetugas dlgKaPerawatan = new DlgCariPetugas(null, false);
    private final DlgCariDokter dlgDokterAnestesi = new DlgCariDokter(null, false);

    // ===================== BINGKAI UTAMA =====================
    private widget.InternalFrame internalFrame1;
    private widget.ScrollPane Scroll;
    private widget.Table tbDetail;

    // ===================== PANEL INPUT (collapsible) =====================
    private JPanel PanelInput;
    private widget.PanelBiasa FormInput;
    private widget.CekBox ChkInput;

    // ---- identitas pasien (readonly, hasil isRawat()) ----
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.TextBox JK;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TNoBPJS;

    // ---- data ICU (header) ----
    private widget.Tanggal TglMasukICU;
    private widget.ComboBox JamMasukICU;
    private widget.ComboBox MenitMasukICU;
    private widget.Tanggal TglKeluarICU;
    private widget.ComboBox JamKeluarICU;
    private widget.ComboBox MenitKeluarICU;

    // ---- intubasi / extubasi pertama (header) ----
    private widget.Tanggal TglIntubasi;
    private widget.ComboBox JamIntubasi;
    private widget.ComboBox MenitIntubasi;
    private widget.Tanggal TglExtubasi;
    private widget.ComboBox JamExtubasi;
    private widget.ComboBox MenitExtubasi;

    private widget.TextBox TTotalJamVentilator;

    // ---- petugas (header) ----
    private widget.TextBox NIPKaPerawatan;
    private widget.TextBox NamaKaPerawatan;
    private widget.Button btnKaPerawatan;
    private widget.TextBox NIPDokterAnestesi;
    private widget.TextBox NamaDokterAnestesi;
    private widget.Button btnDokterAnestesi;

    // ---- input episode (detail) ----
    private widget.CekBox ChkKejadian;
    private widget.Tanggal TglPasang;
    private widget.ComboBox JamPasang;
    private widget.ComboBox MenitPasang;
    private widget.Tanggal TglLepas;
    private widget.ComboBox JamLepas;
    private widget.ComboBox MenitLepas;
    private widget.TextBox TTotalJamEpisode;

    // ===================== TOOLBAR =====================
    private widget.Button BtnSimpan;
    private widget.Button BtnBatal;
    private widget.Button BtnHapus;
    private widget.Button BtnGanti;
    private widget.Button BtnCetak;
    private widget.Label LCountDetail;
    private widget.Button BtnKeluar;

    /**
     * Creates new form RMResumePemakaianVentilator
     *
     * @param parent
     * @param modal
     */
    public RMResumePemakaianVentilator(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        tabModeDetail = new DefaultTableModel(null, new Object[]{
            "Ke-", "Tgl Pasang", "Jam Pasang", "Tgl Lepas", "Jam Lepas", "Total Jam", "id"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        initComponents();
        buildLayout();

        setSize(1040, 700);
        setLocationRelativeTo(parent);

        TNoBPJS.setDocument(new batasInput((byte) 25).getKata(TNoBPJS));
        NIPKaPerawatan.setDocument(new batasInput((byte) 20).getKata(NIPKaPerawatan));
        NIPDokterAnestesi.setDocument(new batasInput((byte) 20).getKata(NIPDokterAnestesi));
        TTotalJamVentilator.setDocument(new batasInput((byte) 30).getKata(TTotalJamVentilator));
        TTotalJamEpisode.setDocument(new batasInput((byte) 30).getKata(TTotalJamEpisode));

        // Navigasi pindah fokus pakai tombol Enter, mengikuti pola Valid.pindah()
        // pada RMDataCatatanObservasiVentilator.java
        TNoBPJS.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                Valid.pindah(evt, TNoBPJS, TTotalJamVentilator);
            }
        });
        TTotalJamVentilator.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                Valid.pindah(evt, TTotalJamVentilator, NIPKaPerawatan);
            }
        });
        NIPKaPerawatan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                Valid.pindah(evt, NIPKaPerawatan, NIPDokterAnestesi);
            }
        });
        NIPDokterAnestesi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                Valid.pindah(evt, NIPDokterAnestesi, TTotalJamEpisode);
            }
        });
        TTotalJamEpisode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                Valid.pindah(evt, TTotalJamEpisode, BtnSimpan);
            }
        });

        isiJamMenit();
        jam();

        dlgKaPerawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dlgKaPerawatan.getTable().getSelectedRow() != -1) {
                    NIPKaPerawatan.setText(dlgKaPerawatan.getTable().getValueAt(dlgKaPerawatan.getTable().getSelectedRow(), 0).toString());
                    NamaKaPerawatan.setText(dlgKaPerawatan.getTable().getValueAt(dlgKaPerawatan.getTable().getSelectedRow(), 1).toString());
                }
                NIPKaPerawatan.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        dlgDokterAnestesi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dlgDokterAnestesi.getTable().getSelectedRow() != -1) {
                    NIPDokterAnestesi.setText(dlgDokterAnestesi.getTable().getValueAt(dlgDokterAnestesi.getTable().getSelectedRow(), 0).toString());
                    NamaDokterAnestesi.setText(dlgDokterAnestesi.getTable().getValueAt(dlgDokterAnestesi.getTable().getSelectedRow(), 1).toString());
                }
                NIPDokterAnestesi.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        try {
            TANGGALMUNDUR = koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR = "yes";
        }

        isCek();
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
    }

    // =====================================================================
    // SETUP KOMPONEN
    // =====================================================================
    @SuppressWarnings("unchecked")
    private void initComponents() {
        internalFrame1 = new widget.InternalFrame();
        internalFrame1.setBorder(new TitledBorder("::[ Resume Pemakaian Ventilator Pasien BPJS Kesehatan ]::"));
        internalFrame1.setLayout(new BorderLayout(1, 1));

        Scroll = new widget.ScrollPane();
        tbDetail = new widget.Table();

        PanelInput = new JPanel(new BorderLayout(1, 1));
        PanelInput.setOpaque(false);
        FormInput = new widget.PanelBiasa();
        FormInput.setLayout(null);
        ChkInput = new widget.CekBox();
        ChkInput.setIcon(new ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setRolloverIcon(new ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.setSelectedIcon(new ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.setText(".: Data Pasien, ICU & Episode Ventilator");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setPreferredSize(new Dimension(280, 20));

        TNoRw = new widget.TextBox();
        TNoRw.setEditable(false);
        TNoRM = new widget.TextBox();
        TNoRM.setEditable(false);
        TPasien = new widget.TextBox();
        TPasien.setEditable(false);
        JK = new widget.TextBox();
        JK.setEditable(false);
        TglLahir = new widget.TextBox();
        TglLahir.setEditable(false);
        Umur = new widget.TextBox();
        Umur.setEditable(false);
        TanggalRegistrasi = new widget.TextBox();
        TanggalRegistrasi.setEditable(false);
        TNoBPJS = new widget.TextBox();

        TglMasukICU = new widget.Tanggal();
        TglMasukICU.setDisplayFormat("dd-MM-yyyy");
        TglMasukICU.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"04-01-2024"}));
        JamMasukICU = new widget.ComboBox();
        MenitMasukICU = new widget.ComboBox();
        TglKeluarICU = new widget.Tanggal();
        TglKeluarICU.setDisplayFormat("dd-MM-yyyy");
        TglKeluarICU.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"04-01-2024"}));
        JamKeluarICU = new widget.ComboBox();
        MenitKeluarICU = new widget.ComboBox();

        TglIntubasi = new widget.Tanggal();
        TglIntubasi.setDisplayFormat("dd-MM-yyyy");
        TglIntubasi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"04-01-2024"}));
        JamIntubasi = new widget.ComboBox();
        MenitIntubasi = new widget.ComboBox();
        TglExtubasi = new widget.Tanggal();
        TglExtubasi.setDisplayFormat("dd-MM-yyyy");
        TglExtubasi.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"04-01-2024"}));
        JamExtubasi = new widget.ComboBox();
        MenitExtubasi = new widget.ComboBox();

        TTotalJamVentilator = new widget.TextBox();

        NIPKaPerawatan = new widget.TextBox();
        NamaKaPerawatan = new widget.TextBox();
        NamaKaPerawatan.setEditable(false);
        btnKaPerawatan = new widget.Button();
        btnKaPerawatan.setIcon(new ImageIcon(getClass().getResource("/picture/190.png")));

        NIPDokterAnestesi = new widget.TextBox();
        NamaDokterAnestesi = new widget.TextBox();
        NamaDokterAnestesi.setEditable(false);
        btnDokterAnestesi = new widget.Button();
        btnDokterAnestesi.setIcon(new ImageIcon(getClass().getResource("/picture/190.png")));

        ChkKejadian = new widget.CekBox();
        ChkKejadian.setText("Kejadian Sekarang");
        ChkKejadian.setToolTipText("Jika dicentang, Tgl & Jam Pasang otomatis ikut waktu sistem (real-time)");
        ChkKejadian.setFocusable(false);

        TglPasang = new widget.Tanggal();
        TglPasang.setDisplayFormat("dd-MM-yyyy");
        TglPasang.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"04-01-2024"}));
        JamPasang = new widget.ComboBox();
        MenitPasang = new widget.ComboBox();
        TglLepas = new widget.Tanggal();
        TglLepas.setDisplayFormat("dd-MM-yyyy");
        TglLepas.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"04-01-2024"}));
        JamLepas = new widget.ComboBox();
        MenitLepas = new widget.ComboBox();
        TTotalJamEpisode = new widget.TextBox();

        BtnSimpan = new widget.Button();
        BtnSimpan.setIcon(new ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setPreferredSize(new Dimension(100, 30));

        BtnBatal = new widget.Button();
        BtnBatal.setIcon(new ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setPreferredSize(new Dimension(100, 30));

        BtnHapus = new widget.Button();
        BtnHapus.setIcon(new ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setPreferredSize(new Dimension(100, 30));

        BtnGanti = new widget.Button();
        BtnGanti.setIcon(new ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnGanti.setMnemonic('G');
        BtnGanti.setText("Ganti");
        BtnGanti.setToolTipText("Alt+G");
        BtnGanti.setPreferredSize(new Dimension(100, 30));

        BtnCetak = new widget.Button();
        BtnCetak.setIcon(new ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnCetak.setMnemonic('T');
        BtnCetak.setText("Cetak");
        BtnCetak.setToolTipText("Alt+T");
        BtnCetak.setPreferredSize(new Dimension(100, 30));

        LCountDetail = new widget.Label();
        LCountDetail.setText("0");
        LCountDetail.setPreferredSize(new Dimension(160, 23));

        BtnKeluar = new widget.Button();
        BtnKeluar.setIcon(new ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setPreferredSize(new Dimension(100, 30));

        // ---- Aksi tombol ----
        BtnSimpan.addActionListener((ActionEvent e) -> simpan());
        BtnBatal.addActionListener((ActionEvent e) -> emptDetailTeks());
        BtnHapus.addActionListener((ActionEvent e) -> hapus());
        BtnGanti.addActionListener((ActionEvent e) -> ganti());
        BtnCetak.addActionListener((ActionEvent e) -> cetak());
        BtnKeluar.addActionListener((ActionEvent e) -> dispose());

        ChkInput.addActionListener((ActionEvent e) -> isForm());

        btnKaPerawatan.addActionListener((ActionEvent e) -> {
            dlgKaPerawatan.emptTeks();
            dlgKaPerawatan.isCek();
            dlgKaPerawatan.setLocationRelativeTo(this);
            dlgKaPerawatan.setVisible(true);
        });

        btnDokterAnestesi.addActionListener((ActionEvent e) -> {
            dlgDokterAnestesi.emptTeks();
            dlgDokterAnestesi.setLocationRelativeTo(this);
            dlgDokterAnestesi.setVisible(true);
        });

        tbDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                getDataDetail();
            }
        });
    }

    // =====================================================================
    // LAYOUT
    // =====================================================================
    private void buildLayout() {
        getContentPane().setLayout(new BorderLayout(1, 1));

        // ---- isi FormInput (absolute / setBounds), mengikuti pola FormInput referensi ----
        addAbs(FormInput, lbl("No.Rawat"), 0, 10, 70, 23);
        addAbs(FormInput, TNoRw, 74, 10, 140, 23);
        addAbs(FormInput, lbl("No.R.M."), 222, 10, 55, 23);
        addAbs(FormInput, TNoRM, 278, 10, 90, 23);
        addAbs(FormInput, lbl("Nama Pasien"), 376, 10, 80, 23);
        addAbs(FormInput, TPasien, 458, 10, 240, 23);
        addAbs(FormInput, lbl("Tgl.Lahir"), 706, 10, 60, 23);
        addAbs(FormInput, TglLahir, 768, 10, 90, 23);

        addAbs(FormInput, lbl("JK"), 0, 40, 70, 23);
        addAbs(FormInput, JK, 74, 40, 40, 23);
        addAbs(FormInput, lbl("Umur"), 122, 40, 50, 23);
        addAbs(FormInput, Umur, 174, 40, 80, 23);
        addAbs(FormInput, lbl("Tgl Registrasi"), 262, 40, 90, 23);
        addAbs(FormInput, TanggalRegistrasi, 354, 40, 140, 23);
        addAbs(FormInput, lbl("No.Kartu BPJS"), 502, 40, 100, 23);
        addAbs(FormInput, TNoBPJS, 604, 40, 254, 23);

        addAbs(FormInput, lbl("Tgl & Jam Masuk ICU"), 0, 70, 140, 23);
        addAbs(FormInput, TglMasukICU, 144, 70, 90, 23);
        addAbs(FormInput, JamMasukICU, 236, 70, 45, 23);
        addAbs(FormInput, MenitMasukICU, 283, 70, 45, 23);
        addAbs(FormInput, lbl("Tgl & Jam Keluar ICU"), 350, 70, 140, 23);
        addAbs(FormInput, TglKeluarICU, 494, 70, 90, 23);
        addAbs(FormInput, JamKeluarICU, 586, 70, 45, 23);
        addAbs(FormInput, MenitKeluarICU, 633, 70, 45, 23);

        addAbs(FormInput, lbl("Tgl & Jam Intubasi Pertama"), 0, 100, 170, 23);
        addAbs(FormInput, TglIntubasi, 174, 100, 90, 23);
        addAbs(FormInput, JamIntubasi, 266, 100, 45, 23);
        addAbs(FormInput, MenitIntubasi, 313, 100, 45, 23);
        addAbs(FormInput, lbl("Tgl & Jam Extubasi Pertama"), 380, 100, 170, 23);
        addAbs(FormInput, TglExtubasi, 554, 100, 90, 23);
        addAbs(FormInput, JamExtubasi, 646, 100, 45, 23);
        addAbs(FormInput, MenitExtubasi, 693, 100, 45, 23);

        addAbs(FormInput, lbl("Jumlah Total Jam Ventilator"), 0, 130, 170, 23);
        addAbs(FormInput, TTotalJamVentilator, 174, 130, 220, 23);
        addAbs(FormInput, lbl("Ka.Perawatan ICU"), 410, 130, 105, 23);
        addAbs(FormInput, NIPKaPerawatan, 518, 130, 80, 23);
        addAbs(FormInput, btnKaPerawatan, 600, 130, 28, 23);
        addAbs(FormInput, NamaKaPerawatan, 630, 130, 150, 23);

        addAbs(FormInput, lbl("Dokter Sp.Anestesi"), 0, 160, 120, 23);
        addAbs(FormInput, NIPDokterAnestesi, 124, 160, 80, 23);
        addAbs(FormInput, btnDokterAnestesi, 206, 160, 28, 23);
        addAbs(FormInput, NamaDokterAnestesi, 236, 160, 180, 23);

        addAbs(FormInput, lbl("Tgl & Jam Pasang"), 0, 195, 120, 23);
        addAbs(FormInput, TglPasang, 124, 195, 90, 23);
        addAbs(FormInput, JamPasang, 216, 195, 45, 23);
        addAbs(FormInput, MenitPasang, 263, 195, 45, 23);
        addAbs(FormInput, lbl("Tgl & Jam Lepas"), 320, 195, 110, 23);
        addAbs(FormInput, TglLepas, 432, 195, 90, 23);
        addAbs(FormInput, JamLepas, 524, 195, 45, 23);
        addAbs(FormInput, MenitLepas, 571, 195, 45, 23);
        addAbs(FormInput, lbl("Total Jam"), 628, 195, 65, 23);
        addAbs(FormInput, TTotalJamEpisode, 695, 195, 163, 23);

        addAbs(FormInput, ChkKejadian, 124, 220, 200, 23);

        FormInput.setPreferredSize(new Dimension(870, 252));

        PanelInput.add(FormInput, BorderLayout.CENTER);
        PanelInput.add(ChkInput, BorderLayout.PAGE_END);
        PanelInput.setPreferredSize(new Dimension(870, 272));

        internalFrame1.add(PanelInput, BorderLayout.PAGE_START);

        // ---- tabel episode (dominan, di tengah) ----
        tbDetail.setModel(tabModeDetail);
        tbDetail.setToolTipText("Silahkan klik untuk memilih episode yang mau diganti ataupun dihapus");
        tbDetail.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        tbDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbDetail.setDefaultRenderer(Object.class, new WarnaTable());
        TableColumn kolomId = tbDetail.getColumnModel().getColumn(6);
        kolomId.setMinWidth(0);
        kolomId.setMaxWidth(0);
        kolomId.setPreferredWidth(0);
        Scroll.setViewportView(tbDetail);
        internalFrame1.add(Scroll, BorderLayout.CENTER);

        // ---- toolbar bawah ----
        JPanel jPanelToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 9));
        jPanelToolbar.setOpaque(false);
        jPanelToolbar.add(BtnSimpan);
        jPanelToolbar.add(BtnGanti);
        jPanelToolbar.add(BtnHapus);
        jPanelToolbar.add(BtnBatal);
        jPanelToolbar.add(BtnCetak);
        jPanelToolbar.add(lbl("Record :"));
        jPanelToolbar.add(LCountDetail);
        jPanelToolbar.add(BtnKeluar);
        internalFrame1.add(jPanelToolbar, BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, BorderLayout.CENTER);
    }

    private void addAbs(widget.PanelBiasa panel, java.awt.Component comp, int x, int y, int w, int h) {
        panel.add(comp);
        comp.setBounds(x, y, w, h);
    }

    private widget.Label lbl(String text) {
        widget.Label l = new widget.Label();
        l.setText(text);
        return l;
    }

    private void isForm() {
        if (ChkInput.isSelected()) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(870, 272));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(870, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
        internalFrame1.revalidate();
    }

    private void isiJamMenit() {
        isiCombo(JamMasukICU, 24);
        isiCombo(MenitMasukICU, 60);
        isiCombo(JamKeluarICU, 24);
        isiCombo(MenitKeluarICU, 60);
        isiCombo(JamIntubasi, 24);
        isiCombo(MenitIntubasi, 60);
        isiCombo(JamExtubasi, 24);
        isiCombo(MenitExtubasi, 60);
        isiCombo(JamPasang, 24);
        isiCombo(MenitPasang, 60);
        isiCombo(JamLepas, 24);
        isiCombo(MenitLepas, 60);
    }

    private void isiCombo(widget.ComboBox cb, int maks) {
        cb.removeAllItems();
        for (int x = 0; x < maks; x++) {
            cb.addItem(x < 10 ? ("0" + x) : ("" + x));
        }
    }

    /**
     * Live clock untuk Tgl & Jam Pasang ventilator (pola sama dengan
     * RMDataCatatanObservasiVentilator.java). Selama ChkKejadian dicentang,
     * TglPasang/JamPasang/MenitPasang otomatis ikut waktu sistem setiap
     * detik. Begitu user un-check, nilainya berhenti diupdate dan bisa
     * diatur manual seperti biasa.
     */
    private void jam() {
        ActionListener taskPerformer = (ActionEvent e) -> {
            if (ChkKejadian.isSelected()) {
                Calendar now = Calendar.getInstance();
                int nilaiJam = now.get(Calendar.HOUR_OF_DAY);
                int nilaiMenit = now.get(Calendar.MINUTE);
                String jamStr = (nilaiJam <= 9 ? "0" : "") + nilaiJam;
                String menitStr = (nilaiMenit <= 9 ? "0" : "") + nilaiMenit;
                TglPasang.setDate(new Date());
                JamPasang.setSelectedItem(jamStr);
                MenitPasang.setSelectedItem(menitStr);
            }
        };
        new Timer(1000, taskPerformer).start();
    }

    // =====================================================================
    // HAK AKSES
    // =====================================================================
    public void isCek() {
        // TODO: tambahkan dulu method akses.getresume_pemakaian_ventilator()
        // di class fungsi.akses mengikuti pola method akses form lain.
        try {
            BtnSimpan.setEnabled(akses.getresume_pemakaian_ventilator());
            BtnGanti.setEnabled(akses.getresume_pemakaian_ventilator());
            BtnHapus.setEnabled(akses.getresume_pemakaian_ventilator());
            BtnCetak.setEnabled(akses.getresume_pemakaian_ventilator());
        } catch (Exception e) {
            // method akses belum dibuat - sementara biarkan semua tombol aktif
            System.out.println("Notif isCek: " + e);
        }

        if (akses.getjml2() >= 1) {
            NIPKaPerawatan.setEditable(false);
            btnKaPerawatan.setEnabled(false);
            NIPKaPerawatan.setText(akses.getkode());
            NamaKaPerawatan.setText(dlgKaPerawatan.tampil3(NIPKaPerawatan.getText()));
        }
    }

    // =====================================================================
    // LOAD DATA PASIEN + HEADER RESUME (dipanggil saat No.Rawat di-set)
    // =====================================================================
    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi," +
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur,reg_periksa.jam_reg " +
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Umur.setText(rs.getString("umurdaftar") + " " + rs.getString("sttsumur"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"));
                } else {
                    JOptionPane.showMessageDialog(this, "No.Rawat tidak ditemukan di reg_periksa..!!");
                    return;
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }

            // Ambil data header resume ventilator jika sudah pernah diisi
            headerAda = false;
            ps = koneksi.prepareStatement("select * from resume_pemakaian_ventilator where no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    headerAda = true;
                    TNoBPJS.setText(rs.getString("no_kartu_bpjs") == null ? "" : rs.getString("no_kartu_bpjs"));
                    isiTanggalJam(TglMasukICU, JamMasukICU, MenitMasukICU, rs.getString("tgl_masuk_icu"), rs.getString("jam_masuk_icu"));
                    isiTanggalJam(TglKeluarICU, JamKeluarICU, MenitKeluarICU, rs.getString("tgl_keluar_icu"), rs.getString("jam_keluar_icu"));
                    isiTanggalJam(TglIntubasi, JamIntubasi, MenitIntubasi, rs.getString("tgl_intubasi_pertama"), rs.getString("jam_intubasi_pertama"));
                    isiTanggalJam(TglExtubasi, JamExtubasi, MenitExtubasi, rs.getString("tgl_extubasi_pertama"), rs.getString("jam_extubasi_pertama"));
                    TTotalJamVentilator.setText(rs.getString("jumlah_total_jam_ventilator") == null ? "" : rs.getString("jumlah_total_jam_ventilator"));
                    NIPKaPerawatan.setText(rs.getString("nip_ka_perawatan_icu") == null ? "" : rs.getString("nip_ka_perawatan_icu"));
                    NamaKaPerawatan.setText(dlgKaPerawatan.tampil3(NIPKaPerawatan.getText()));
                    NIPDokterAnestesi.setText(rs.getString("kd_dokter_anestesi") == null ? "" : rs.getString("kd_dokter_anestesi"));
                    NamaDokterAnestesi.setText(dlgDokterAnestesi.tampil3(NIPDokterAnestesi.getText()));
                } else {
                    TNoBPJS.setText("");
                    isiDefaultMasukICUdariKamarInap();
                    TglKeluarICU.setDate(new Date());
                    TglIntubasi.setDate(new Date());
                    TglExtubasi.setDate(new Date());
                    TTotalJamVentilator.setText("");
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif isRawat: " + e);
        }
    }

    /**
     * Isi default Tgl & Jam Masuk ICU dari data kamar_inap yang sudah ada
     * (bukan diketik manual dari nol). Diutamakan baris kamar ICU yang masih
     * aktif (kamar_inap.stts_pulang='-', sesuai konvensi standar Khanza yang
     * juga dipakai di DlgKamarInap.java); kalau tidak ada yang aktif, pakai
     * baris ICU paling baru. Kalau pasien belum pernah tercatat di kamar ICU
     * sama sekali, baru fallback ke tanggal hari ini sebagai default.
     *
     * Catatan: filter ICU saat ini memakai bangsal.nm_bangsal LIKE '%ICU%'.
     * Sesuaikan kata kunci ini (mis. jadi '%HCU%' atau kd_bangsal tertentu)
     * kalau penamaan bangsal ICU di database Anda berbeda.
     */
    private void isiDefaultMasukICUdariKamarInap() {
        boolean ketemu = false;
        try {
            PreparedStatement psKmr = koneksi.prepareStatement(
                    "select kamar_inap.tgl_masuk,kamar_inap.jam_masuk " +
                    "from kamar_inap " +
                    "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar " +
                    "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal " +
                    "where kamar_inap.no_rawat=? and bangsal.nm_bangsal like '%ICU%' " +
                    "order by (kamar_inap.stts_pulang='-') desc, kamar_inap.tgl_masuk desc, kamar_inap.jam_masuk desc limit 1");
            try {
                psKmr.setString(1, TNoRw.getText());
                ResultSet rsKmr = psKmr.executeQuery();
                if (rsKmr.next()) {
                    ketemu = true;
                    Valid.SetTgl(TglMasukICU, rsKmr.getString("tgl_masuk"));
                    String jamMasuk = rsKmr.getString("jam_masuk");
                    if (jamMasuk != null && jamMasuk.length() >= 5) {
                        JamMasukICU.setSelectedItem(jamMasuk.substring(0, 2));
                        MenitMasukICU.setSelectedItem(jamMasuk.substring(3, 5));
                    }
                }
                rsKmr.close();
            } finally {
                psKmr.close();
            }
        } catch (Exception e) {
            System.out.println("Notif isiDefaultMasukICUdariKamarInap: " + e);
        }
        if (!ketemu) {
            // Pasien belum tercatat di kamar ber-bangsal ICU - fallback tanggal hari ini
            TglMasukICU.setDate(new Date());
        }
    }

    private void isiTanggalJam(widget.Tanggal tgl, widget.ComboBox jam, widget.ComboBox menit, String tglStr, String jamStr) {
        if (tglStr != null && !tglStr.equals("")) {
            Valid.SetTgl(tgl, tglStr);
        }
        if (jamStr != null && jamStr.length() >= 5) {
            jam.setSelectedItem(jamStr.substring(0, 2));
            menit.setSelectedItem(jamStr.substring(3, 5));
        }
    }

    /**
     * Dipanggil dari form/menu luar (mis. daftar pasien ICU di Rawat Inap)
     * ketika membuka resume ini untuk No.Rawat tertentu. Mengikuti pola
     * setNoRm(no_rawat, tgl) pada form referensi - TIDAK ada pencarian
     * manual lagi di dalam dialog ini.
     *
     * @param norawat
     * @param tgl
     */
    public void setNoRm(String norawat, Date tgl) {
        emptTeks();
        TNoRw.setText(norawat);
        isRawat();
        tampilDetail();
        ChkInput.setSelected(true);
        isForm();
    }

    /**
     * Alias refresh tampilan, dipanggil dari menu luar setelah setNoRm()
     * (mengikuti pola pemanggilan form.tampil() pada form-form RM lain,
     * contoh: DlgRawatJalan.java). Cukup memuat ulang data pasien/header
     * dan daftar episode utk No.Rawat yang sedang aktif.
     */
    public void tampil() {
        if (!TNoRw.getText().trim().equals("")) {
            isRawat();
            tampilDetail();
        }
    }

    /**
     * Bersihkan seluruh isian form (header + input episode), dipanggil
     * sebelum form ditampilkan ke user (pola emptTeks() standar Khanza).
     */
    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        JK.setText("");
        TglLahir.setText("");
        Umur.setText("");
        TanggalRegistrasi.setText("");
        TNoBPJS.setText("");
        TglMasukICU.setDate(new Date());
        JamMasukICU.setSelectedIndex(0);
        MenitMasukICU.setSelectedIndex(0);
        TglKeluarICU.setDate(new Date());
        JamKeluarICU.setSelectedIndex(0);
        MenitKeluarICU.setSelectedIndex(0);
        TglIntubasi.setDate(new Date());
        JamIntubasi.setSelectedIndex(0);
        MenitIntubasi.setSelectedIndex(0);
        TglExtubasi.setDate(new Date());
        JamExtubasi.setSelectedIndex(0);
        MenitExtubasi.setSelectedIndex(0);
        TTotalJamVentilator.setText("");
        NIPKaPerawatan.setText("");
        NamaKaPerawatan.setText("");
        NIPDokterAnestesi.setText("");
        NamaDokterAnestesi.setText("");
        headerAda = false;
        emptDetailTeks();
        Valid.tabelKosong(tabModeDetail);
        LCountDetail.setText("0");
    }

    // =====================================================================
    // SIMPAN (SATU TOMBOL): upsert header + insert episode baru sekaligus
    // =====================================================================
    private void simpan() {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "No.Rawat belum dipilih. Buka form ini dari menu pasien ICU terlebih dahulu..!!");
            return;
        }
        if (!simpanHeader()) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data pasien & ICU..!!");
            return;
        }
        simpanDetail();
    }

    private boolean simpanHeader() {
        String tglMasukICU = Valid.SetTgl(TglMasukICU.getSelectedItem() + "");
        String jamMasukICU = JamMasukICU.getSelectedItem() + ":" + MenitMasukICU.getSelectedItem() + ":00";
        String tglKeluarICU = Valid.SetTgl(TglKeluarICU.getSelectedItem() + "");
        String jamKeluarICU = JamKeluarICU.getSelectedItem() + ":" + MenitKeluarICU.getSelectedItem() + ":00";
        String tglIntubasi = Valid.SetTgl(TglIntubasi.getSelectedItem() + "");
        String jamIntubasi = JamIntubasi.getSelectedItem() + ":" + MenitIntubasi.getSelectedItem() + ":00";
        String tglExtubasi = Valid.SetTgl(TglExtubasi.getSelectedItem() + "");
        String jamExtubasi = JamExtubasi.getSelectedItem() + ":" + MenitExtubasi.getSelectedItem() + ":00";

        boolean sukses;
        if (!headerAda) {
            sukses = Sequel.menyimpantf("resume_pemakaian_ventilator", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 14, new String[]{
                TNoRw.getText(), TNoRM.getText(), TNoBPJS.getText(),
                tglMasukICU, jamMasukICU, tglKeluarICU, jamKeluarICU,
                tglIntubasi, jamIntubasi, tglExtubasi, jamExtubasi,
                TTotalJamVentilator.getText(), NIPKaPerawatan.getText(), NIPDokterAnestesi.getText()
            });
        } else {
            sukses = Sequel.mengedittf("resume_pemakaian_ventilator", "no_rawat=?",
                    "no_kartu_bpjs=?,tgl_masuk_icu=?,jam_masuk_icu=?,tgl_keluar_icu=?,jam_keluar_icu=?," +
                    "tgl_intubasi_pertama=?,jam_intubasi_pertama=?,tgl_extubasi_pertama=?,jam_extubasi_pertama=?," +
                    "jumlah_total_jam_ventilator=?,nip_ka_perawatan_icu=?,kd_dokter_anestesi=?,no_rawat=?", 14, new String[]{
                        TNoBPJS.getText(), tglMasukICU, jamMasukICU, tglKeluarICU, jamKeluarICU,
                        tglIntubasi, jamIntubasi, tglExtubasi, jamExtubasi,
                        TTotalJamVentilator.getText(), NIPKaPerawatan.getText(), NIPDokterAnestesi.getText(), TNoRw.getText()
                    });
        }
        if (sukses) {
            headerAda = true;
        }
        return sukses;
    }

    // =====================================================================
    // CRUD DETAIL (detail_pemakaian_ventilator)
    // =====================================================================
    public void tampilDetail() {
        Valid.tabelKosong(tabModeDetail);
        if (TNoRw.getText().trim().equals("")) {
            LCountDetail.setText("0");
            return;
        }
        try {
            ps = koneksi.prepareStatement(
                    "select id,urutan_ke,tgl_pasang,jam_pasang,tgl_lepas,jam_lepas,total_jam " +
                    "from detail_pemakaian_ventilator where no_rawat=? order by urutan_ke");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeDetail.addRow(new String[]{
                        rs.getString("urutan_ke"),
                        rs.getString("tgl_pasang"), rs.getString("jam_pasang"),
                        rs.getString("tgl_lepas"), rs.getString("jam_lepas"),
                        rs.getString("total_jam"), rs.getString("id")
                    });
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif tampilDetail: " + e);
        }
        LCountDetail.setText("" + tabModeDetail.getRowCount());
    }

    private void simpanDetail() {
        int urutanKe = tabModeDetail.getRowCount() + 1;
        String tglPasang = Valid.SetTgl(TglPasang.getSelectedItem() + "");
        String jamPasang = JamPasang.getSelectedItem() + ":" + MenitPasang.getSelectedItem() + ":00";
        String tglLepas = Valid.SetTgl(TglLepas.getSelectedItem() + "");
        String jamLepas = JamLepas.getSelectedItem() + ":" + MenitLepas.getSelectedItem() + ":00";

        // Kolom id AUTO_INCREMENT tidak boleh diisi lewat Sequel.menyimpantf (rawan
        // mismatch tipe karena semua parameter di-bind sebagai String). Insert manual
        // dengan menyebut nama kolom secara eksplisit (id tidak disertakan = auto).
        try {
            PreparedStatement psIns = koneksi.prepareStatement(
                    "insert into detail_pemakaian_ventilator " +
                    "(no_rawat,urutan_ke,tgl_pasang,jam_pasang,tgl_lepas,jam_lepas,total_jam) " +
                    "values (?,?,?,?,?,?,?)");
            psIns.setString(1, TNoRw.getText());
            psIns.setInt(2, urutanKe);
            psIns.setString(3, tglPasang);
            psIns.setString(4, jamPasang);
            psIns.setString(5, tglLepas);
            psIns.setString(6, jamLepas);
            psIns.setString(7, TTotalJamEpisode.getText());
            psIns.executeUpdate();
            psIns.close();
            tampilDetail();
            emptDetailTeks();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan..");
        } catch (Exception e) {
            System.out.println("Notif simpanDetail: " + e);
            JOptionPane.showMessageDialog(this, "Gagal menyimpan episode ventilator..!!");
        }
    }

    private void getDataDetail() {
        if (tbDetail.getSelectedRow() != -1) {
            TglPasang.setDate(new Date());
            Valid.SetTgl(TglPasang, tbDetail.getValueAt(tbDetail.getSelectedRow(), 1).toString());
            String jamP = tbDetail.getValueAt(tbDetail.getSelectedRow(), 2).toString();
            if (jamP.length() >= 5) {
                JamPasang.setSelectedItem(jamP.substring(0, 2));
                MenitPasang.setSelectedItem(jamP.substring(3, 5));
            }
            Valid.SetTgl(TglLepas, tbDetail.getValueAt(tbDetail.getSelectedRow(), 3).toString());
            String jamL = tbDetail.getValueAt(tbDetail.getSelectedRow(), 4).toString();
            if (jamL.length() >= 5) {
                JamLepas.setSelectedItem(jamL.substring(0, 2));
                MenitLepas.setSelectedItem(jamL.substring(3, 5));
            }
            TTotalJamEpisode.setText(tbDetail.getValueAt(tbDetail.getSelectedRow(), 5).toString());
        }
    }

    private void ganti() {
        if (tbDetail.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih dulu baris episode yang akan diedit..!!");
            return;
        }
        String idTerpilih = tbDetail.getValueAt(tbDetail.getSelectedRow(), 6).toString();
        String tglPasang = Valid.SetTgl(TglPasang.getSelectedItem() + "");
        String jamPasang = JamPasang.getSelectedItem() + ":" + MenitPasang.getSelectedItem() + ":00";
        String tglLepas = Valid.SetTgl(TglLepas.getSelectedItem() + "");
        String jamLepas = JamLepas.getSelectedItem() + ":" + MenitLepas.getSelectedItem() + ":00";

        if (Sequel.mengedittf("detail_pemakaian_ventilator", "id=?",
                "tgl_pasang=?,jam_pasang=?,tgl_lepas=?,jam_lepas=?,total_jam=?", 6, new String[]{
                    tglPasang, jamPasang, tglLepas, jamLepas, TTotalJamEpisode.getText(), idTerpilih
                }) == true) {
            tampilDetail();
            emptDetailTeks();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data episode..!!");
        }
    }

    private void hapus() {
        if (tbDetail.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih dulu baris episode yang akan dihapus..!!");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Yakin hapus episode ini..?", "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        String idTerpilih = tbDetail.getValueAt(tbDetail.getSelectedRow(), 6).toString();
        if (Sequel.queryu2tf("delete from detail_pemakaian_ventilator where id=?", 1, new String[]{idTerpilih}) == true) {
            tampilDetail();
            emptDetailTeks();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data episode..!!");
        }
    }

    private void emptDetailTeks() {
        TglPasang.setDate(new Date());
        JamPasang.setSelectedIndex(0);
        MenitPasang.setSelectedIndex(0);
        TglLepas.setDate(new Date());
        JamLepas.setSelectedIndex(0);
        MenitLepas.setSelectedIndex(0);
        TTotalJamEpisode.setText("");
    }

    // =====================================================================
    // CETAK (Jasper Report)
    // =====================================================================
    private void cetak() {
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "No.Rawat belum diisi..!!");
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
        param.put("no_rawat", TNoRw.getText());

        // Pola sama dengan rptCatatanObservasiVentilator (lihat contoh hasil cetak
        // RMDataCatatanObservasiVentilator): query FLAT, header pasien/ICU di-JOIN
        // dengan SEMUA baris episode detail_pemakaian_ventilator utk no_rawat ini,
        // supaya 1 query mengembalikan N baris (N = jumlah episode). Di template
        // Jasper (rptResumePemakaianVentilator.jasper), buat Group by no_rawat:
        //  - Group Header  : cetak identitas pasien + data ICU/intubasi sekali saja
        //                    (field2 ini nilainya sama di semua baris hasil query)
        //  - Detail band   : urutan_ke, tgl_pasang, jam_pasang, tgl_lepas, jam_lepas,
        //                    total_jam -> berulang ke bawah persis seperti tabel
        //                    "Tanggal & Jam / Mode / VT / ..." di gambar 2.
        Valid.MyReportqry("rptResumePemakaianVentilator.jasper", "report",
                "::[ Resume Pemakaian Ventilator ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir," +
                "reg_periksa.tgl_registrasi,reg_periksa.jam_reg," +
                "resume_pemakaian_ventilator.no_kartu_bpjs,resume_pemakaian_ventilator.tgl_masuk_icu,resume_pemakaian_ventilator.jam_masuk_icu," +
                "resume_pemakaian_ventilator.tgl_keluar_icu,resume_pemakaian_ventilator.jam_keluar_icu," +
                "resume_pemakaian_ventilator.tgl_intubasi_pertama,resume_pemakaian_ventilator.jam_intubasi_pertama," +
                "resume_pemakaian_ventilator.tgl_extubasi_pertama,resume_pemakaian_ventilator.jam_extubasi_pertama," +
                "resume_pemakaian_ventilator.jumlah_total_jam_ventilator,ka.nama as nama_ka_perawatan,dok.nm_dokter as nama_dokter_anestesi," +
                "detail_pemakaian_ventilator.urutan_ke,detail_pemakaian_ventilator.tgl_pasang,detail_pemakaian_ventilator.jam_pasang," +
                "detail_pemakaian_ventilator.tgl_lepas,detail_pemakaian_ventilator.jam_lepas,detail_pemakaian_ventilator.total_jam " +
                "from resume_pemakaian_ventilator " +
                "inner join reg_periksa on resume_pemakaian_ventilator.no_rawat=reg_periksa.no_rawat " +
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                "left join petugas ka on resume_pemakaian_ventilator.nip_ka_perawatan_icu=ka.nip " +
                "left join dokter dok on resume_pemakaian_ventilator.kd_dokter_anestesi=dok.kd_dokter " +
                "left join detail_pemakaian_ventilator on detail_pemakaian_ventilator.no_rawat=resume_pemakaian_ventilator.no_rawat " +
                "where resume_pemakaian_ventilator.no_rawat='" + TNoRw.getText() + "' " +
                "order by detail_pemakaian_ventilator.urutan_ke", param);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMResumePemakaianVentilator dialog = new RMResumePemakaianVentilator(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            // Contoh pemanggilan dari menu luar setelah memilih pasien ICU:
            // dialog.setNoRm("2026/06/19/000110", new Date());
            dialog.setVisible(true);
        });
    }
}
