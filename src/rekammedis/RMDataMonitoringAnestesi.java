/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import setting.DlgCariRuangOperasi;

/**
 *
 * @author perpustakaan
 */
public final class RMDataMonitoringAnestesi extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode2;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariRuangOperasi ruangok = new DlgCariRuangOperasi(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMDataMonitoringAnestesi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No Rawat", "No.R.M.", "Nama Pasien", "Tgl.Lahir", "JK", "Tgl.Obser", "Jam Obser", "NIP", "Nama Petugas",
            "Diagnosa", "Tindakan", "Kode Ruangan", "Nama Ruangan", "Keadaan Umum", "Kesadaran", "Anamnesis", "GCS", "Berat Badan",
            "Tinggi Badan", "Lain-lain", "Kode Dokter", "Nama Dokter"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// Mengatur lebar kolom sesuai jumlah kolom dalam tabMode
        int[] columnWidths = {105, 65, 160, 75, 40, 80, 60, 55, 100, 100, 90, 90, 120, 100, 80, 80, 50, 70, 70, 100, 50, 100};

        for (int i = 0; i < tabMode.getColumnCount(); i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

// Warna tabel
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new Object[]{
            "No Rawat", "No.R.M.", "Nama Pasien", "Tgl.Lahir", "JK", "Tgl.Obser", "Jam Obser", "NIP", "Nama Petugas",
            "Obat", "Dosis", "Waktu", "Tensi", "Nadi", "Respirasi", "Suhu"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbObat1.setModel(tabMode2);
        tbObat1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

// Mengatur lebar kolom sesuai jumlah kolom dalam tabMode
        int[] columnWidths2 = {105, 65, 160, 75, 40, 80, 60, 55, 100, 100, 90, 90, 120, 100, 80, 80};

        for (int i = 0; i < tabMode2.getColumnCount(); i++) {
            TableColumn column = tbObat1.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths2[i]);
        }

// Warna tabel
        tbObat1.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte) 20).getKata(NIP));
        KdRuangOperasi.setDocument(new batasInput((byte) 5).getKata(KdRuangOperasi));
        NmRuangOperasi.setDocument(new batasInput((byte) 30).getKata(NmRuangOperasi));
        Diagnosa.setDocument(new batasInput((byte) 30).getKata(Diagnosa));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KdDokter.requestFocus();
                }
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

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                NIP.requestFocus();
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

        ruangok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (ruangok.getTable().getSelectedRow() != -1) {
                    KdRuangOperasi.setText(ruangok.getTable().getValueAt(ruangok.getTable().getSelectedRow(), 0).toString());
                    NmRuangOperasi.setText(ruangok.getTable().getValueAt(ruangok.getTable().getSelectedRow(), 1).toString());
                }
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

        ChkInput.setSelected(false);
        isForm();
        jam();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanCekGDS = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnCetakMonitoring = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        tabAnestesi = new widget.TabPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        Diagnosa = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel23 = new widget.Label();
        Anemnesis = new widget.TextBox();
        jLabel24 = new widget.Label();
        KeadaanUmum = new widget.TextBox();
        jLabel25 = new widget.Label();
        Kesadaran = new widget.TextBox();
        jLabel26 = new widget.Label();
        Bb = new widget.TextBox();
        jLabel28 = new widget.Label();
        Lain = new widget.TextBox();
        jLabel29 = new widget.Label();
        Gcs = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        Tb = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel12 = new widget.Label();
        KdRuangOperasi = new widget.TextBox();
        NmRuangOperasi = new widget.TextBox();
        BtnRuangOperasi = new widget.Button();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        ChkInput = new widget.CekBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbObat1 = new widget.Table();
        PanelInput1 = new javax.swing.JPanel();
        FormInput1 = new widget.PanelBiasa();
        Obat = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        Dosis = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        Tensi = new widget.TextBox();
        jLabel45 = new widget.Label();
        Waktu = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        Respirasi = new widget.TextBox();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel51 = new widget.Label();
        ChkInput1 = new widget.CekBox();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        JK = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanCekGDS.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanCekGDS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanCekGDS.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanCekGDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanCekGDS.setText("Formulir Catatan Cek GDS");
        MnCatatanCekGDS.setName("MnCatatanCekGDS"); // NOI18N
        MnCatatanCekGDS.setPreferredSize(new java.awt.Dimension(230, 26));
        MnCatatanCekGDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanCekGDSActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanCekGDS);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnCetakMonitoring.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakMonitoring.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakMonitoring.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakMonitoring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakMonitoring.setText("Cetak Monitoring");
        MnCetakMonitoring.setName("MnCetakMonitoring"); // NOI18N
        MnCetakMonitoring.setPreferredSize(new java.awt.Dimension(230, 26));
        MnCetakMonitoring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakMonitoringActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnCetakMonitoring);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Monitoring Status Fisiologis Pasien Selama Pemberian Anestesi Di Luar Kamar Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        tabAnestesi.setName("tabAnestesi"); // NOI18N

        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 160));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        Diagnosa.setFocusTraversalPolicyProvider(true);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(90, 10, 219, 23);

        jLabel20.setText("Diagnosa:");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 10, 90, 23);

        jLabel22.setText("Tindakan:");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(310, 10, 60, 23);

        Tindakan.setFocusTraversalPolicyProvider(true);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(380, 10, 400, 23);

        jLabel23.setText("Anamnesis:");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(10, 70, 70, 23);

        Anemnesis.setFocusTraversalPolicyProvider(true);
        Anemnesis.setName("Anemnesis"); // NOI18N
        Anemnesis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnemnesisKeyPressed(evt);
            }
        });
        FormInput.add(Anemnesis);
        Anemnesis.setBounds(90, 70, 270, 23);

        jLabel24.setText("Keadaan Umum:");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(360, 40, 90, 23);

        KeadaanUmum.setFocusTraversalPolicyProvider(true);
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(450, 40, 160, 23);

        jLabel25.setText("Kesadaran:");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(610, 40, 60, 23);

        Kesadaran.setFocusTraversalPolicyProvider(true);
        Kesadaran.setName("Kesadaran"); // NOI18N
        Kesadaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesadaranKeyPressed(evt);
            }
        });
        FormInput.add(Kesadaran);
        Kesadaran.setBounds(670, 40, 120, 23);

        jLabel26.setText("Kg");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(660, 70, 20, 23);

        Bb.setFocusTraversalPolicyProvider(true);
        Bb.setName("Bb"); // NOI18N
        Bb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BbKeyPressed(evt);
            }
        });
        FormInput.add(Bb);
        Bb.setBounds(610, 70, 50, 23);

        jLabel28.setText("Lain-Lain:");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(10, 100, 70, 23);

        Lain.setFocusTraversalPolicyProvider(true);
        Lain.setName("Lain"); // NOI18N
        Lain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LainKeyPressed(evt);
            }
        });
        FormInput.add(Lain);
        Lain.setBounds(90, 100, 310, 23);

        jLabel29.setText("GCS:");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(360, 70, 40, 23);

        Gcs.setFocusTraversalPolicyProvider(true);
        Gcs.setName("Gcs"); // NOI18N
        Gcs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GcsKeyPressed(evt);
            }
        });
        FormInput.add(Gcs);
        Gcs.setBounds(410, 70, 130, 23);

        jLabel30.setText("Berat Badan:");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(540, 70, 70, 23);

        jLabel31.setText("Tinggi Badan:");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(680, 70, 80, 23);

        Tb.setFocusTraversalPolicyProvider(true);
        Tb.setName("Tb"); // NOI18N
        Tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TbKeyPressed(evt);
            }
        });
        FormInput.add(Tb);
        Tb.setBounds(760, 70, 50, 23);

        jLabel32.setText("Cm");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(810, 70, 20, 23);

        jLabel12.setText("Ruangan:");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(10, 40, 70, 23);

        KdRuangOperasi.setFocusTraversalPolicyProvider(true);
        KdRuangOperasi.setName("KdRuangOperasi"); // NOI18N
        KdRuangOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRuangOperasiKeyPressed(evt);
            }
        });
        FormInput.add(KdRuangOperasi);
        KdRuangOperasi.setBounds(90, 40, 60, 23);

        NmRuangOperasi.setFocusTraversalPolicyProvider(true);
        NmRuangOperasi.setName("NmRuangOperasi"); // NOI18N
        NmRuangOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmRuangOperasiKeyPressed(evt);
            }
        });
        FormInput.add(NmRuangOperasi);
        NmRuangOperasi.setBounds(150, 40, 180, 23);

        BtnRuangOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnRuangOperasi.setMnemonic('X');
        BtnRuangOperasi.setToolTipText("Alt+X");
        BtnRuangOperasi.setName("BtnRuangOperasi"); // NOI18N
        BtnRuangOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRuangOperasiActionPerformed(evt);
            }
        });
        BtnRuangOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRuangOperasiKeyPressed(evt);
            }
        });
        FormInput.add(BtnRuangOperasi);
        BtnRuangOperasi.setBounds(330, 40, 28, 23);

        label14.setText("Dokter Operator :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(400, 100, 110, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(520, 100, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(610, 100, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(790, 100, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame2.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        tabAnestesi.addTab("Inputan Awal", internalFrame2);

        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat1.setComponentPopupMenu(jPopupMenu2);
        tbObat1.setName("tbObat1"); // NOI18N
        tbObat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObat1MouseClicked(evt);
            }
        });
        tbObat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObat1KeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbObat1);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        PanelInput1.setName("PanelInput1"); // NOI18N
        PanelInput1.setOpaque(false);
        PanelInput1.setPreferredSize(new java.awt.Dimension(192, 100));
        PanelInput1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput1.setBackground(new java.awt.Color(250, 255, 245));
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput1.setLayout(null);

        Obat.setFocusTraversalPolicyProvider(true);
        Obat.setName("Obat"); // NOI18N
        Obat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatKeyPressed(evt);
            }
        });
        FormInput1.add(Obat);
        Obat.setBounds(60, 10, 260, 23);

        jLabel34.setText("Obat:");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput1.add(jLabel34);
        jLabel34.setBounds(0, 10, 50, 23);

        jLabel35.setText("Waktu:");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput1.add(jLabel35);
        jLabel35.setBounds(480, 10, 60, 23);

        Dosis.setFocusTraversalPolicyProvider(true);
        Dosis.setName("Dosis"); // NOI18N
        Dosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DosisKeyPressed(evt);
            }
        });
        FormInput1.add(Dosis);
        Dosis.setBounds(410, 10, 90, 23);

        jLabel36.setText("mmHg");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput1.add(jLabel36);
        jLabel36.setBounds(150, 40, 40, 23);

        jLabel37.setText("Tensi:");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput1.add(jLabel37);
        jLabel37.setBounds(10, 40, 40, 23);

        Tensi.setFocusTraversalPolicyProvider(true);
        Tensi.setName("Tensi"); // NOI18N
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        FormInput1.add(Tensi);
        Tensi.setBounds(60, 40, 90, 23);

        jLabel45.setText("Dosis:");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput1.add(jLabel45);
        jLabel45.setBounds(360, 10, 40, 23);

        Waktu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pre Anestesi", "Durante Anestesi", "Post Anestesi" }));
        Waktu.setName("Waktu"); // NOI18N
        FormInput1.add(Waktu);
        Waktu.setBounds(550, 10, 170, 20);

        jLabel46.setText("Nadi:");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput1.add(jLabel46);
        jLabel46.setBounds(200, 40, 30, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput1.add(Nadi);
        Nadi.setBounds(240, 40, 80, 23);

        jLabel47.setText("x/menit");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput1.add(jLabel47);
        jLabel47.setBounds(320, 40, 40, 23);

        jLabel48.setText("Respirasi:");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput1.add(jLabel48);
        jLabel48.setBounds(390, 40, 50, 23);

        Respirasi.setFocusTraversalPolicyProvider(true);
        Respirasi.setName("Respirasi"); // NOI18N
        Respirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiKeyPressed(evt);
            }
        });
        FormInput1.add(Respirasi);
        Respirasi.setBounds(450, 40, 80, 23);

        jLabel49.setText("x/menit");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput1.add(jLabel49);
        jLabel49.setBounds(530, 40, 40, 23);

        jLabel50.setText("Suhu:");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput1.add(jLabel50);
        jLabel50.setBounds(580, 40, 50, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput1.add(Suhu);
        Suhu.setBounds(640, 40, 80, 23);

        jLabel51.setText("C");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput1.add(jLabel51);
        jLabel51.setBounds(720, 40, 30, 23);

        PanelInput1.add(FormInput1, java.awt.BorderLayout.CENTER);

        ChkInput1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setMnemonic('I');
        ChkInput1.setText(".: Input Data");
        ChkInput1.setToolTipText("Alt+I");
        ChkInput1.setBorderPainted(true);
        ChkInput1.setBorderPaintedFlat(true);
        ChkInput1.setFocusable(false);
        ChkInput1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput1.setName("ChkInput1"); // NOI18N
        ChkInput1.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInput1ActionPerformed(evt);
            }
        });
        PanelInput1.add(ChkInput1, java.awt.BorderLayout.PAGE_END);

        internalFrame3.add(PanelInput1, java.awt.BorderLayout.PAGE_START);

        tabAnestesi.addTab("Monitoring", internalFrame3);

        internalFrame1.add(tabAnestesi, java.awt.BorderLayout.CENTER);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(12, 80));
        panelBiasa1.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelBiasa1.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoRM);
        TNoRM.setBounds(212, 10, 112, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        panelBiasa1.add(TPasien);
        TPasien.setBounds(326, 10, 295, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelBiasa1.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        panelBiasa1.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        panelBiasa1.add(JK);
        JK.setBounds(70, 40, 90, 23);

        jLabel9.setText("JK:");
        jLabel9.setName("jLabel9"); // NOI18N
        panelBiasa1.add(jLabel9);
        jLabel9.setBounds(10, 40, 50, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        panelBiasa1.add(jLabel16);
        jLabel16.setBounds(140, 40, 70, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelBiasa1.add(Tanggal);
        Tanggal.setBounds(210, 40, 90, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        panelBiasa1.add(Jam);
        Jam.setBounds(300, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        panelBiasa1.add(Menit);
        Menit.setBounds(370, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        panelBiasa1.add(Detik);
        Detik.setBounds(430, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        panelBiasa1.add(ChkKejadian);
        ChkKejadian.setBounds(500, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelBiasa1.add(jLabel18);
        jLabel18.setBounds(520, 40, 60, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        panelBiasa1.add(NIP);
        NIP.setBounds(590, 40, 70, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        panelBiasa1.add(NamaPetugas);
        NamaPetugas.setBounds(670, 40, 120, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        panelBiasa1.add(btnPetugas);
        btnPetugas.setBounds(790, 40, 28, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
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

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
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

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-03-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
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
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
            isPsien();
        } else {
            Valid.pindah(evt, TCari, Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt, TCari, BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        switch (tabAnestesi.getSelectedIndex()) {
            case 0:
                if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "pasien");
                } else if (Diagnosa.getText().trim().equals("")) {
                    Valid.textKosong(Diagnosa, "Diagnosa");
                } else if (Tindakan.getText().trim().equals("")) {
                    Valid.textKosong(Tindakan, "Tindakan");
                } else if (KdRuangOperasi.getText().trim().equals("")) {
                    Valid.textKosong(KdRuangOperasi, "Ruang");
                } else if (KeadaanUmum.getText().trim().equals("")) {
                    Valid.textKosong(KeadaanUmum, "Keadaan Umum");
                } else if (Kesadaran.getText().trim().equals("")) {
                    Valid.textKosong(Kesadaran, "Kesadaran");
                } else if (Anemnesis.getText().trim().equals("")) {
                    Valid.textKosong(Anemnesis, "Anemnesis");
                } else if (Gcs.getText().trim().equals("")) {
                    Valid.textKosong(Gcs, "GCS");
                } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
                    Valid.textKosong(NIP, "Petugas");
                } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
                    Valid.textKosong(KdDokter, "Dokter");
                } else {
                    if (akses.getkode().equals("Admin Utama")) {
                        simpan();
                    } else {
                        if (TanggalRegistrasi.getText().equals("")) {
                            TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                        }
                        if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem()) == true) {
                            simpan();
                        }
                    }
                }
                break;
            case 1:
                if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "pasien");
                } else if (Obat.getText().trim().equals("")) {
                    Valid.textKosong(Obat, "Obat");
                } else if (Dosis.getText().trim().equals("")) {
                    Valid.textKosong(Dosis, "Dosis");
                } else if (Tensi.getText().trim().equals("")) {
                    Valid.textKosong(Tensi, "Tensi");
                } else if (Nadi.getText().trim().equals("")) {
                    Valid.textKosong(Nadi, "Nadi");
                } else if (Respirasi.getText().trim().equals("")) {
                    Valid.textKosong(Respirasi, "Respirasi");
                } else if (Suhu.getText().trim().equals("")) {
                    Valid.textKosong(Suhu, "Suhu");
                } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
                    Valid.textKosong(NIP, "Petugas");
                } else {
                    if (akses.getkode().equals("Admin Utama")) {
                        simpan2();
                    } else {
                        if (TanggalRegistrasi.getText().equals("")) {
                            TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?", TNoRw.getText()));
                        }
                        if (Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem()) == true) {
                            simpan2();
                        }
                    }
                }
                break;
            default:
        }


}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, Diagnosa, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();

}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        switch (tabAnestesi.getSelectedIndex()) {
            case 0:
                if (tbObat.getSelectedRow() > -1) {
                    if (akses.getkode().equals("Admin Utama")) {
                        hapus();
                    } else {
                        if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString())) {
                            hapus();
                        } else {
                            JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            case 1:
                if (tbObat1.getSelectedRow() > -1) {
                    if (akses.getkode().equals("Admin Utama")) {
                        hapus2();
                    } else {
                        if (NIP.getText().equals(tbObat1.getValueAt(tbObat1.getSelectedRow(), 7).toString())) {
                            hapus2();
                        } else {
                            JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                }
                break;
            default:
        }


}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        switch (tabAnestesi.getSelectedIndex()) {
            case 0:
                if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "pasien");
                } else if (Diagnosa.getText().trim().equals("")) {
                    Valid.textKosong(Diagnosa, "Diagnosa");
                } else if (Tindakan.getText().trim().equals("")) {
                    Valid.textKosong(Tindakan, "Tindakan");
                } else if (KdRuangOperasi.getText().trim().equals("")) {
                    Valid.textKosong(KdRuangOperasi, "Ruang");
                } else if (KeadaanUmum.getText().trim().equals("")) {
                    Valid.textKosong(KeadaanUmum, "Keadaan Umum");
                } else if (Kesadaran.getText().trim().equals("")) {
                    Valid.textKosong(Kesadaran, "Kesadaran");
                } else if (Anemnesis.getText().trim().equals("")) {
                    Valid.textKosong(Anemnesis, "Anemnesis");
                } else if (Gcs.getText().trim().equals("")) {
                    Valid.textKosong(Gcs, "GCS");
                } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
                    Valid.textKosong(NIP, "Petugas");
                } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
                    Valid.textKosong(KdDokter, "Dokter");
                } else {
                    if (tbObat.getSelectedRow() > -1) {
                        if (akses.getkode().equals("Admin Utama")) {
                            ganti();
                        } else {
                            if (NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString())) {
                                ganti();
                            } else {
                                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                    }
                }
                break;
            case 1:
                if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "pasien");
                } else if (Obat.getText().trim().equals("")) {
                    Valid.textKosong(Obat, "Obat");
                } else if (Dosis.getText().trim().equals("")) {
                    Valid.textKosong(Dosis, "Dosis");
                } else if (Tensi.getText().trim().equals("")) {
                    Valid.textKosong(Tensi, "Tensi");
                } else if (Nadi.getText().trim().equals("")) {
                    Valid.textKosong(Nadi, "Nadi");
                } else if (Respirasi.getText().trim().equals("")) {
                    Valid.textKosong(Respirasi, "Respirasi");
                } else if (Suhu.getText().trim().equals("")) {
                    Valid.textKosong(Suhu, "Suhu");
                } else if (NIP.getText().trim().equals("") || NamaPetugas.getText().trim().equals("")) {
                    Valid.textKosong(NIP, "Petugas");
                } else {
                    if (tbObat1.getSelectedRow() > -1) {
                        if (akses.getkode().equals("Admin Utama")) {
                            ganti2();
                        } else {
                            if (NIP.getText().equals(tbObat1.getValueAt(tbObat1.getSelectedRow(), 11).toString())) {
                                ganti2();
                            } else {
                                JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
                    }
                }
                break;
            default:
        }


}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));

            if (TCari.getText().trim().equals("")) {
                Valid.MyReportqry("rptDataCatatanCekGDS.jasper", "report", "::[ Data Catatan Cek GDS ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                        + "pasien.jk,pasien.tgl_lahir,monitoring_anestesirscb.tgl_perawatan,monitoring_anestesirscb.jam_rawat,monitoring_anestesirscb.gdp,"
                        + "monitoring_anestesirscb.insulin,monitoring_anestesirscb.obat_gula,monitoring_anestesirscb.nip,petugas.nama "
                        + "from monitoring_anestesirscb inner join reg_periksa on monitoring_anestesirscb.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on monitoring_anestesirscb.nip=petugas.nip where "
                        + "monitoring_anestesirscb.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' order by monitoring_anestesirscb.tgl_perawatan", param);
            } else {
                Valid.MyReportqry("rptDataCatatanCekGDS.jasper", "report", "::[ Data Catatan Cek GDS ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                        + "pasien.jk,pasien.tgl_lahir,monitoring_anestesirscb.tgl_perawatan,monitoring_anestesirscb.jam_rawat,monitoring_anestesirscb.gdp,"
                        + "monitoring_anestesirscb.insulin,monitoring_anestesirscb.obat_gula,monitoring_anestesirscb.nip,petugas.nama "
                        + "from monitoring_anestesirscb inner join reg_periksa on monitoring_anestesirscb.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on monitoring_anestesirscb.nip=petugas.nip where "
                        + "monitoring_anestesirscb.tgl_perawatan between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and "
                        + "(reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or pasien.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                        + "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or monitoring_anestesirscb.nip like '%" + TCari.getText().trim() + "%' or petugas.nama like '%" + TCari.getText().trim() + "%') "
                        + "order by monitoring_anestesirscb.tgl_perawatan ", param);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        switch (tabAnestesi.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TCari, Jam);
}//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt, Tanggal, Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt, Jam, Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt, Menit, btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NamaPetugas, NIP.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Detik.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            KdRuangOperasi.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NIPKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt, Detik, KdRuangOperasi);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnCatatanCekGDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanCekGDSActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            param.put("logo2", Sequel.cariGambar("select setting.logo2 from setting"));
            Valid.MyReportqry("rptFormulirCatatanCekGDS.jasper", "report", "::[ Formulir Catatan Cek GDS ]::",
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                    + "pasien.jk,pasien.tgl_lahir,monitoring_anestesirscb.tgl_perawatan,monitoring_anestesirscb.jam_rawat,monitoring_anestesirscb.gdp,"
                    + "monitoring_anestesirscb.insulin,monitoring_anestesirscb.obat_gula,monitoring_anestesirscb.nip,petugas.nama "
                    + "from monitoring_anestesirscb inner join reg_periksa on monitoring_anestesirscb.no_rawat=reg_periksa.no_rawat "
                    + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "inner join petugas on monitoring_anestesirscb.nip=petugas.nip where reg_periksa.no_rawat='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
        }
    }//GEN-LAST:event_MnCatatanCekGDSActionPerformed

    private void KdRuangOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRuangOperasiKeyPressed
        Valid.pindah(evt, btnPetugas, NmRuangOperasi);
    }//GEN-LAST:event_KdRuangOperasiKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt, NmRuangOperasi, BtnSimpan);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void NmRuangOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmRuangOperasiKeyPressed
        Valid.pindah(evt, KdRuangOperasi, Diagnosa);
    }//GEN-LAST:event_NmRuangOperasiKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanKeyPressed

    private void AnemnesisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnemnesisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnemnesisKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void KesadaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesadaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesadaranKeyPressed

    private void BbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BbKeyPressed

    private void LainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LainKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LainKeyPressed

    private void GcsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GcsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GcsKeyPressed

    private void TbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TbKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TbKeyPressed

    private void tbObat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObat1MouseClicked
        if (tabMode2.getRowCount() != 0) {
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObat1MouseClicked

    private void tbObat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbObat1KeyPressed

    private void ObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ObatKeyPressed

    private void DosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DosisKeyPressed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TensiKeyPressed

    private void ChkInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInput1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkInput1ActionPerformed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NadiKeyPressed

    private void RespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RespirasiKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuhuKeyPressed

    private void BtnRuangOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRuangOperasiActionPerformed
        ruangok.isCek();
        ruangok.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ruangok.setLocationRelativeTo(internalFrame1);
        ruangok.setVisible(true);
    }//GEN-LAST:event_BtnRuangOperasiActionPerformed

    private void BtnRuangOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRuangOperasiKeyPressed

    }//GEN-LAST:event_BtnRuangOperasiKeyPressed

    private void MnCetakMonitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakMonitoringActionPerformed
        if (tbObat1.getSelectedRow() > -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            try {
                ps = koneksi.prepareStatement("SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, monitoring_anestesirscb.tgl_perawatan, "
                        + "monitoring_anestesirscb.jam_rawat, monitoring_anestesirscb.nip, monitoring_anestesirscb.diagnosa, monitoring_anestesirscb.tindakan, "
                        + "monitoring_anestesirscb.ruangan, monitoring_anestesirscb.keadaanumum, monitoring_anestesirscb.kesadaran, monitoring_anestesirscb.anamnesis, "
                        + "monitoring_anestesirscb.gcs, monitoring_anestesirscb.bb, monitoring_anestesirscb.tb, monitoring_anestesirscb.lain, petugas.nama, ruang_ok.nm_ruang_ok,monitoring_anestesirscb.kddokter, "
                        + "ruang_ok.kd_ruang_ok,dokter.nm_dokter FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN monitoring_anestesirscb ON "
                        + "reg_periksa.no_rawat = monitoring_anestesirscb.no_rawat INNER JOIN petugas ON monitoring_anestesirscb.nip = petugas.nip INNER JOIN ruang_ok ON "
                        + "monitoring_anestesirscb.ruangan = ruang_ok.kd_ruang_ok inner join dokter ON  monitoring_anestesirscb.kddokter = dokter.kd_dokter where monitoring_anestesirscb.no_rawat = ? ");
                try {
                    ps.setString(1, tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        param.put("tgl_perawatan", rs.getString("tgl_perawatan"));
                        param.put("jam_rawat", rs.getString("jam_rawat"));
                        param.put("no_rkm_medis", rs.getString("no_rkm_medis"));
                        param.put("nm_pasien", rs.getString("nm_pasien"));
                        param.put("tgl_lahir", rs.getString("tgl_lahir"));
                        param.put("nm_ruang_ok", rs.getString("nm_ruang_ok"));
                        param.put("nip", rs.getString("nip"));
                        param.put("nama", rs.getString("nama"));
                        param.put("nm_dokter", rs.getString("nm_dokter"));
                        param.put("diagnosa", rs.getString("diagnosa"));
                        param.put("tindakan", rs.getString("tindakan"));
                        param.put("keadaanumum", rs.getString("keadaanumum"));
                        param.put("kesadaran", rs.getString("kesadaran"));
                        param.put("anamnesis", rs.getString("anamnesis"));
                        param.put("gcs", rs.getString("gcs"));
                        param.put("bb", rs.getString("bb"));
                        param.put("tb", rs.getString("tb"));
                        param.put("lain", rs.getString("lain"));

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
                System.out.println("Notif : " + e);
            }

            Valid.MyReportqry("rptCetakMonitoringAnestesiRsCB.jasper", "report", "::[ Monitoring Status Fisiologis Pasien Selama Pemberian Anestesi DiLuar Kamar Operasi ]::",
                    "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                    + "monitoring_anestesirscb_detail.tgl_perawatan, monitoring_anestesirscb_detail.jam_rawat, monitoring_anestesirscb_detail.nip, "
                    + "monitoring_anestesirscb_detail.obat, monitoring_anestesirscb_detail.dosis, monitoring_anestesirscb_detail.waktu, "
                    + "monitoring_anestesirscb_detail.tensi, monitoring_anestesirscb_detail.nadi, monitoring_anestesirscb_detail.rr, "
                    + "monitoring_anestesirscb_detail.suhu, petugas.nama FROM reg_periksa "
                    + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "INNER JOIN monitoring_anestesirscb_detail ON reg_periksa.no_rawat = monitoring_anestesirscb_detail.no_rawat "
                    + "INNER JOIN petugas ON monitoring_anestesirscb_detail.nip = petugas.nip where reg_periksa.no_rawat='" + tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString() + "'", param);
        }
    }//GEN-LAST:event_MnCetakMonitoringActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //Valid.pindah(evt,Monitoring,BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataMonitoringAnestesi dialog = new RMDataMonitoringAnestesi(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Anemnesis;
    private widget.TextBox Bb;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRuangOperasi;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkInput1;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.TextBox Diagnosa;
    private widget.TextBox Dosis;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.TextBox Gcs;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KdDokter;
    private widget.TextBox KdRuangOperasi;
    private widget.TextBox KeadaanUmum;
    private widget.TextBox Kesadaran;
    private widget.Label LCount;
    private widget.TextBox Lain;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnCatatanCekGDS;
    private javax.swing.JMenuItem MnCetakMonitoring;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NmDokter;
    private widget.TextBox NmRuangOperasi;
    private widget.TextBox Obat;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPanel PanelInput1;
    private widget.TextBox Respirasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox Tb;
    private widget.TextBox Tensi;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.ComboBox Waktu;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel12;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel4;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private widget.Label label14;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.TabPane tabAnestesi;
    private widget.Table tbObat;
    private widget.Table tbObat1;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().toString().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, monitoring_anestesirscb.tgl_perawatan, "
                        + "monitoring_anestesirscb.jam_rawat, monitoring_anestesirscb.nip, monitoring_anestesirscb.diagnosa, monitoring_anestesirscb.tindakan, "
                        + "monitoring_anestesirscb.ruangan, monitoring_anestesirscb.keadaanumum, monitoring_anestesirscb.kesadaran, monitoring_anestesirscb.anamnesis, "
                        + "monitoring_anestesirscb.gcs, monitoring_anestesirscb.bb, monitoring_anestesirscb.tb, monitoring_anestesirscb.lain, petugas.nama, ruang_ok.nm_ruang_ok,monitoring_anestesirscb.kddokter, "
                        + "ruang_ok.kd_ruang_ok,dokter.nm_dokter FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN monitoring_anestesirscb ON "
                        + "reg_periksa.no_rawat = monitoring_anestesirscb.no_rawat INNER JOIN petugas ON monitoring_anestesirscb.nip = petugas.nip INNER JOIN ruang_ok ON "
                        + "monitoring_anestesirscb.ruangan = ruang_ok.kd_ruang_ok inner join dokter ON  monitoring_anestesirscb.kddokter = dokter.kd_dokter where "
                        + "monitoring_anestesirscb.tgl_perawatan between ? and ? order by monitoring_anestesirscb.tgl_perawatan");
            } else {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, monitoring_anestesirscb.tgl_perawatan, "
                        + "monitoring_anestesirscb.jam_rawat, monitoring_anestesirscb.nip, monitoring_anestesirscb.diagnosa, monitoring_anestesirscb.tindakan, "
                        + "monitoring_anestesirscb.ruangan, monitoring_anestesirscb.keadaanumum, monitoring_anestesirscb.kesadaran, monitoring_anestesirscb.anamnesis, "
                        + "monitoring_anestesirscb.gcs, monitoring_anestesirscb.bb, monitoring_anestesirscb.tb, monitoring_anestesirscb.lain, petugas.nama, ruang_ok.nm_ruang_ok,monitoring_anestesirscb.kddokter, "
                        + "ruang_ok.kd_ruang_ok,dokter.nm_dokter FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis INNER JOIN monitoring_anestesirscb ON "
                        + "reg_periksa.no_rawat = monitoring_anestesirscb.no_rawat INNER JOIN petugas ON monitoring_anestesirscb.nip = petugas.nip INNER JOIN ruang_ok ON "
                        + "monitoring_anestesirscb.ruangan = ruang_ok.kd_ruang_ok inner join dokter ON monitoring_anestesirscb.kddokter = dokter.kd_dokter where "
                        + "monitoring_anestesirscb.tgl_perawatan between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? "
                        + "or pasien.nm_pasien like ? or monitoring_anestesirscb.nip like ? or petugas.nama like ?) "
                        + "order by monitoring_anestesirscb.tgl_perawatan ");
            }

            try {
                if (TCari.getText().toString().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("jk"),
                        rs.getString("tgl_lahir"), rs.getString("tgl_perawatan"), rs.getString("jam_rawat"), rs.getString("nip"),
                        rs.getString("nama"), rs.getString("diagnosa"), rs.getString("tindakan"), rs.getString("ruangan"),
                        rs.getString("nm_ruang_ok"), rs.getString("keadaanumum"), rs.getString("kesadaran"), rs.getString("anamnesis"),
                        rs.getString("gcs"), rs.getString("bb"), rs.getString("tb"), rs.getString("lain"), rs.getString("kddokter"),
                        rs.getString("nm_dokter")

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
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void tampil2() {
        Valid.tabelKosong(tabMode2);
        try {
            if (TCari.getText().toString().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                        + "monitoring_anestesirscb_detail.tgl_perawatan, monitoring_anestesirscb_detail.jam_rawat, monitoring_anestesirscb_detail.nip, "
                        + "monitoring_anestesirscb_detail.obat, monitoring_anestesirscb_detail.dosis, monitoring_anestesirscb_detail.waktu, "
                        + "monitoring_anestesirscb_detail.tensi, monitoring_anestesirscb_detail.nadi, monitoring_anestesirscb_detail.rr, "
                        + "monitoring_anestesirscb_detail.suhu, petugas.nama FROM reg_periksa "
                        + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN monitoring_anestesirscb_detail ON reg_periksa.no_rawat = monitoring_anestesirscb_detail.no_rawat "
                        + "INNER JOIN petugas ON monitoring_anestesirscb_detail.nip = petugas.nip "
                        + "WHERE monitoring_anestesirscb_detail.tgl_perawatan BETWEEN ? AND ? "
                        + "ORDER BY monitoring_anestesirscb_detail.tgl_perawatan");
            } else {
                ps = koneksi.prepareStatement(
                        "SELECT reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, pasien.tgl_lahir, "
                        + "monitoring_anestesirscb_detail.tgl_perawatan, monitoring_anestesirscb_detail.jam_rawat, monitoring_anestesirscb_detail.nip, "
                        + "monitoring_anestesirscb_detail.obat, monitoring_anestesirscb_detail.dosis, monitoring_anestesirscb_detail.waktu, "
                        + "monitoring_anestesirscb_detail.tensi, monitoring_anestesirscb_detail.nadi, monitoring_anestesirscb_detail.rr, "
                        + "monitoring_anestesirscb_detail.suhu, petugas.nama FROM reg_periksa "
                        + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN monitoring_anestesirscb_detail ON reg_periksa.no_rawat = monitoring_anestesirscb_detail.no_rawat "
                        + "INNER JOIN petugas ON monitoring_anestesirscb_detail.nip = petugas.nip "
                        + "WHERE monitoring_anestesirscb_detail.tgl_perawatan BETWEEN ? AND ? AND "
                        + "(reg_periksa.no_rawat LIKE ? OR pasien.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ? OR "
                        + "monitoring_anestesirscb_detail.nip LIKE ? OR petugas.nama LIKE ?) "
                        + "ORDER BY monitoring_anestesirscb_detail.tgl_perawatan");
            }

            try {
                if (TCari.getText().toString().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                    ps.setString(7, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode2.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("jk"),
                        rs.getString("tgl_lahir"), rs.getString("tgl_perawatan"), rs.getString("jam_rawat"), rs.getString("nip"),
                        rs.getString("nama"), rs.getString("obat"), rs.getString("dosis"), rs.getString("waktu"),
                        rs.getString("tensi"), rs.getString("nadi"), rs.getString("rr"), rs.getString("suhu")
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
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode2.getRowCount());
    }

    public void emptTeks() {
        switch (tabAnestesi.getSelectedIndex()) {
            case 0:
                Diagnosa.setText("");
                Tindakan.setText("");
                NmRuangOperasi.setText("");
                KeadaanUmum.setText("");
                Kesadaran.setText("");
                Anemnesis.setText("");
                Gcs.setText("");
                Bb.setText("");
                Tb.setText("");
                Lain.setText("");
                KdDokter.setText("");
                NmDokter.setText("");
                ChkInput.setSelected(true);
                isForm();
                break;
            case 1:
                Obat.setText("");
                Dosis.setText("");
                Tensi.setText("");
                Nadi.setText("");
                Respirasi.setText("");
                Suhu.setText("");
                break;
            default:
        }

    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString().substring(0, 2));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString().substring(3, 5));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString().substring(6, 8));
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            KdRuangOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            NmRuangOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            KeadaanUmum.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            Kesadaran.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            Anemnesis.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            Gcs.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Bb.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            Tb.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            Lain.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
        }
    }

    private void getData2() {
        if (tbObat1.getSelectedRow() != -1) {
            TNoRw.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString());
            TNoRM.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 2).toString());
            TglLahir.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 3).toString());
            JK.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 4).toString());
            Valid.SetTgl(Tanggal, tbObat1.getValueAt(tbObat1.getSelectedRow(), 5).toString());
            Jam.setSelectedItem(tbObat1.getValueAt(tbObat1.getSelectedRow(), 6).toString().substring(0, 2));
            Menit.setSelectedItem(tbObat1.getValueAt(tbObat1.getSelectedRow(), 6).toString().substring(3, 5));
            Detik.setSelectedItem(tbObat1.getValueAt(tbObat1.getSelectedRow(), 6).toString().substring(6, 8));
            NIP.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 7).toString());
            NamaPetugas.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 8).toString());
            Obat.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 9).toString());
            Dosis.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 10).toString());
            Waktu.setSelectedItem(tbObat1.getValueAt(tbObat1.getSelectedRow(), 11).toString());
            Tensi.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 12).toString());
            Nadi.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 13).toString());
            Respirasi.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 14).toString());
            Suhu.setText(tbObat1.getValueAt(tbObat1.getSelectedRow(), 15).toString());
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", TPasien);
        Sequel.cariIsi("select date_format(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ", TglLahir, TNoRM.getText());
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='" + norwt + "'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 180));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.getsignin_sebelum_anestesi());
        BtnHapus.setEnabled(akses.getsignin_sebelum_anestesi());
        BtnEdit.setEnabled(akses.getsignin_sebelum_anestesi());
        BtnPrint.setEnabled(akses.getsignin_sebelum_anestesi());
        if (akses.getjml2() >= 1) {
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?", NamaPetugas, NIP.getText());
            if (NamaPetugas.getText().equals("")) {
                NIP.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan petugas...!!");
            }
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";

                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkKejadian.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkKejadian.isSelected() == false) {
                    nilai_jam = Jam.getSelectedIndex();
                    nilai_menit = Menit.getSelectedIndex();
                    nilai_detik = Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void ganti() {
        Sequel.mengedit("monitoring_anestesirscb",
                "tgl_perawatan=? and jam_rawat=? and no_rawat=?",
                "no_rawat=?, tgl_perawatan=?, jam_rawat=?, nip=?, diagnosa=?, tindakan=?, ruangan=?, keadaanumum=?, kesadaran=?, anamnesis=?, gcs=?, bb=?, tb=?, lain=?, kddokter=?",
                18, new String[]{
                    TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                    NIP.getText(), Diagnosa.getText(), Tindakan.getText(), KdRuangOperasi.getText(), KeadaanUmum.getText(), Kesadaran.getText(), Anemnesis.getText(),
                    Gcs.getText(), Bb.getText(), Tb.getText(), Lain.getText(), KdDokter.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString(),
                    tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                });
        if (tabMode.getRowCount() != 0) {
            tampil();
        }
        emptTeks();
    }

    private void ganti2() {
        Sequel.mengedit("monitoring_anestesirscb_detail",
                "tgl_perawatan=? and jam_rawat=? and no_rawat=?",
                "no_rawat=?, tgl_perawatan=?, jam_rawat=?, nip=?, obat=?, dosis=?, waktu=?, tensi=?, nadi=?, rr=?, suhu=?",
                14, new String[]{
                    TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                    NIP.getText(), Obat.getText(), Dosis.getText(), Waktu.getSelectedItem().toString(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), Suhu.getText(), tbObat1.getValueAt(tbObat1.getSelectedRow(), 5).toString(),
                    tbObat1.getValueAt(tbObat1.getSelectedRow(), 6).toString(), tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString()
                });
        if (tabMode2.getRowCount() != 0) {
            tampil2();
        }
        emptTeks();
    }

    private void hapus() {
        if (Sequel.queryu2tf("delete from monitoring_anestesirscb where tgl_perawatan=? and jam_rawat=? and no_rawat=?", 3, new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
        }) == true) {
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void hapus2() {
        if (Sequel.queryu2tf("delete from monitoring_anestesirscb_detail where tgl_perawatan=? and jam_rawat=? and no_rawat=?", 3, new String[]{
            tbObat1.getValueAt(tbObat1.getSelectedRow(), 5).toString(), tbObat1.getValueAt(tbObat1.getSelectedRow(), 6).toString(), tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString()
        }) == true) {
            tabMode2.removeRow(tbObat1.getSelectedRow());
            LCount.setText("" + tabMode2.getRowCount());
            emptTeks();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if (Sequel.menyimpantf("monitoring_anestesirscb", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 15, new String[]{
            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
            NIP.getText(), Diagnosa.getText(), Tindakan.getText(), KdRuangOperasi.getText(), KeadaanUmum.getText(), Kesadaran.getText(), Anemnesis.getText(),
            Gcs.getText(), Bb.getText(), Tb.getText(), Lain.getText(), KdDokter.getText()
        }) == true) {
            tabMode.addRow(new String[]{
                TNoRw.getText(), TNoRM.getText(), TPasien.getText(), TglLahir.getText(), JK.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                NIP.getText(), NamaPetugas.getText(), Diagnosa.getText(), Tindakan.getText(), KdRuangOperasi.getText(), NmRuangOperasi.getText(), KeadaanUmum.getText(), Kesadaran.getText(), Anemnesis.getText(),
                Gcs.getText(), Bb.getText(), Tb.getText(), Lain.getText(), KdDokter.getText(), NmDokter.getText()
            });
            LCount.setText("" + tabMode.getRowCount());
            emptTeks();
        }
    }

    private void simpan2() {
        if (Sequel.menyimpantf("monitoring_anestesirscb_detail", "?,?,?,?,?,?,?,?,?,?,?", "Data", 11, new String[]{
            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
            NIP.getText(), Obat.getText(), Dosis.getText(), Waktu.getSelectedItem().toString(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), Suhu.getText()
        }) == true) {
            tabMode2.addRow(new String[]{
                TNoRw.getText(), TNoRM.getText(), TPasien.getText(), TglLahir.getText(), JK.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                NIP.getText(), NamaPetugas.getText(), Obat.getText(), Dosis.getText(), Waktu.getSelectedItem().toString(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), Suhu.getText()
            });
            LCount.setText("" + tabMode2.getRowCount());
            emptTeks();
        }
    }

}
