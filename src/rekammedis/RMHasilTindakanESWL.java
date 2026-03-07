/*
 * By Mas Elkhanza
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import rekammedis.MasterTemplateESWL;


/**
 *
 * @author perpustakaan
 */
public final class RMHasilTindakanESWL extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    private MasterTemplateESWL template=new MasterTemplateESWL(null,false);

    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMHasilTindakanESWL(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat",
            "No.RM",
            "Nama Pasien",
            "Tgl.Lahir",
            "J.K.",
            "Kode Dokter",
            "Nama Ahli Bedah",
            "NIP",
            "Nama Asisten/Perawat",
            "Waktu Mulai",
            "Waktu Selesai",
            "Keluhan",
            "Riwayat Penyakit",
            "Riwayat Operasi",
            "Riwayat ESWL",
            "TD",
            "Nadi",
            "RR",
            "Suhu",
            "Status Urologi",
            "Urine",
            "Darah",
            "USG",
            "BNO",
            "CT Scan",
            "Lokasi Tembakan",
            "Pole",
            "Hidroneprosis",
            "EWSL Ke",
            "Guide",
            "Ukuran",
            "Analgetik",
            "Power",
            "Frekuensi",
            "Tembakan",
            "Durasi",
            "Keluhan",
            "Komplikasi",
            "Evaluasi",
            "Advice"

        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 39; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(90);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(90);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(90);
            }else if(i==20){
                column.setPreferredWidth(90);
            }else if(i==21){
                column.setPreferredWidth(90);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(90);
            }else if(i==25){
                column.setPreferredWidth(90);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(90);
            }else if(i==29){
                column.setPreferredWidth(90);
            }else if(i==30){
                column.setPreferredWidth(90);
            }else if(i==31){
                column.setPreferredWidth(90);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(90);
            }else if(i==34){
                column.setPreferredWidth(90);
            }else if(i==35){
                column.setPreferredWidth(90);
            }else if(i==36){
                column.setPreferredWidth(90);
            }else if(i==37){
                column.setPreferredWidth(90);
            }else if(i==38){
                column.setPreferredWidth(90);

            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Keluhan.setDocument(new batasInput((int)50).getKata(Keluhan));

        TCari.setDocument(new batasInput((int)100).getKata(TCari));

        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    KdDokter.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    NIP.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        template.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(template.getTable().getSelectedRow()!= -1){


                    Keluhan.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),2).toString());
            RiwayatPenyakit.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),3).toString());
            Operasi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),4).toString());
            RiwayatESWL.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),5).toString());
            TD.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),6).toString());
            Nadi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),7).toString());
            RR.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),8).toString());
            Suhu.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),9).toString());
            StatusUrologi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),10).toString());
            Urine.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),11).toString());
            Darah.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),12).toString());
            USG.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),13).toString());
            BNO.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),14).toString());
            CTScan.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),15).toString());
            Lokasi.setSelectedItem(template.getTable().getValueAt(template.getTable().getSelectedRow(),16).toString());
            Pole.setSelectedItem(template.getTable().getValueAt(template.getTable().getSelectedRow(),17).toString());
            Hidroneprosis.setSelectedItem(template.getTable().getValueAt(template.getTable().getSelectedRow(),18).toString());
            ESWLKe.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),19).toString());
            Guide.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),20).toString());
            UkuranBatu.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),21).toString());
            Analgetik.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),22).toString());
            Power.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),23).toString());
            Frekuensi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),24).toString());
            Tembakan.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),25).toString());
            Durasi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),26).toString());
            KeluhanTindakan.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),27).toString());
            Komplikasi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),28).toString());
            Evaluasi.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),29).toString());
            Advice.setText(template.getTable().getValueAt(template.getTable().getSelectedRow(),30).toString());



                    Keluhan.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDokumenESWL = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        label14 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        label11 = new widget.Label();
        WaktuSelesai = new widget.Tanggal();
        label12 = new widget.Label();
        WaktuMulai = new widget.Tanggal();
        label15 = new widget.Label();
        NIP = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        Keluhan = new widget.TextBox();
        label16 = new widget.Label();
        label17 = new widget.Label();
        RiwayatPenyakit = new widget.TextBox();
        label18 = new widget.Label();
        Operasi = new widget.TextBox();
        label19 = new widget.Label();
        RiwayatESWL = new widget.TextBox();
        label20 = new widget.Label();
        Power = new widget.TextBox();
        label21 = new widget.Label();
        Frekuensi = new widget.TextBox();
        Tembakan1 = new widget.Label();
        Tembakan = new widget.TextBox();
        label23 = new widget.Label();
        Durasi = new widget.TextBox();
        label24 = new widget.Label();
        KeluhanTindakan = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        label25 = new widget.Label();
        Komplikasi = new widget.TextBox();
        label26 = new widget.Label();
        Evaluasi = new widget.TextBox();
        label27 = new widget.Label();
        label28 = new widget.Label();
        label29 = new widget.Label();
        label30 = new widget.Label();
        ESWLKe = new widget.TextBox();
        Guide = new widget.TextBox();
        UkuranBatu = new widget.TextBox();
        Analgetik = new widget.TextBox();
        Lokasi = new widget.ComboBox();
        label31 = new widget.Label();
        label32 = new widget.Label();
        Pole = new widget.ComboBox();
        label33 = new widget.Label();
        Hidroneprosis = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel22 = new widget.Label();
        TD = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel17 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel26 = new widget.Label();
        RR = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel18 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel24 = new widget.Label();
        StatusUrologi = new widget.TextBox();
        label34 = new widget.Label();
        label35 = new widget.Label();
        jSeparator6 = new javax.swing.JSeparator();
        label36 = new widget.Label();
        jLabel27 = new widget.Label();
        Urine = new widget.TextBox();
        jLabel28 = new widget.Label();
        Darah = new widget.TextBox();
        jLabel29 = new widget.Label();
        USG = new widget.TextBox();
        jLabel30 = new widget.Label();
        TD5 = new widget.TextBox();
        jLabel31 = new widget.Label();
        CTScan = new widget.TextBox();
        jLabel32 = new widget.Label();
        BNO = new widget.TextBox();
        jSeparator7 = new javax.swing.JSeparator();
        label37 = new widget.Label();
        label38 = new widget.Label();
        label39 = new widget.Label();
        label40 = new widget.Label();
        label41 = new widget.Label();
        label42 = new widget.Label();
        Advice = new widget.TextBox();
        BtnTemplateResume = new widget.Button();
        label43 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDokumenESWL.setBackground(new java.awt.Color(255, 255, 254));
        MnDokumenESWL.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDokumenESWL.setForeground(new java.awt.Color(50, 50, 50));
        MnDokumenESWL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDokumenESWL.setText("Laporan/Dokumentasi Tindakan ESWL");
        MnDokumenESWL.setName("MnDokumenESWL"); // NOI18N
        MnDokumenESWL.setPreferredSize(new java.awt.Dimension(240, 26));
        MnDokumenESWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDokumenESWLActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDokumenESWL);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Dokumentasi Tindakan ESWL ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(467, 500));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.setPreferredSize(new java.awt.Dimension(457, 480));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setPreferredSize(new java.awt.Dimension(102, 480));
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(750, 875));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        label14.setText("Ahli Bedah :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 70, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(KdDokter);
        KdDokter.setBounds(74, 70, 90, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDokter);
        NmDokter.setBounds(166, 70, 150, 23);

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
        BtnDokter.setBounds(320, 70, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(580, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(644, 10, 80, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(74, 40, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 750, 1);

        label11.setText("Waktu Selesai :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(475, 40, 110, 23);

        WaktuSelesai.setForeground(new java.awt.Color(50, 70, 50));
        WaktuSelesai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2024 14:39:34" }));
        WaktuSelesai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuSelesai.setName("WaktuSelesai"); // NOI18N
        WaktuSelesai.setOpaque(false);
        WaktuSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuSelesaiKeyPressed(evt);
            }
        });
        FormInput.add(WaktuSelesai);
        WaktuSelesai.setBounds(589, 40, 135, 23);

        label12.setText("Waktu Mulai :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(240, 40, 110, 23);

        WaktuMulai.setForeground(new java.awt.Color(50, 70, 50));
        WaktuMulai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2024 14:39:35" }));
        WaktuMulai.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        WaktuMulai.setName("WaktuMulai"); // NOI18N
        WaktuMulai.setOpaque(false);
        WaktuMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WaktuMulaiKeyPressed(evt);
            }
        });
        FormInput.add(WaktuMulai);
        WaktuMulai.setBounds(360, 40, 135, 23);

        label15.setText("Asisten :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label15);
        label15.setBounds(380, 70, 50, 23);

        NIP.setEditable(false);
        NIP.setName("NIP"); // NOI18N
        NIP.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(NIP);
        NIP.setBounds(432, 70, 90, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(524, 70, 170, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('2');
        BtnPetugas.setToolTipText("Alt+2");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(BtnPetugas);
        BtnPetugas.setBounds(696, 70, 28, 23);

        Keluhan.setName("Keluhan"); // NOI18N
        Keluhan.setPreferredSize(new java.awt.Dimension(80, 23));
        Keluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanKeyPressed(evt);
            }
        });
        FormInput.add(Keluhan);
        Keluhan.setBounds(150, 130, 570, 20);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("Pemeriksaan Fisik");
        label16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(10, 250, 130, 30);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("Riwayat Penyakit :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(10, 160, 130, 23);

        RiwayatPenyakit.setName("RiwayatPenyakit"); // NOI18N
        RiwayatPenyakit.setPreferredSize(new java.awt.Dimension(80, 23));
        RiwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPenyakit);
        RiwayatPenyakit.setBounds(150, 160, 570, 20);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label18.setText("Riwayat Operasi Urologi :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(10, 190, 130, 23);

        Operasi.setName("Operasi"); // NOI18N
        Operasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Operasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperasiKeyPressed(evt);
            }
        });
        FormInput.add(Operasi);
        Operasi.setBounds(150, 190, 570, 20);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("Riwayat ESWL :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(10, 220, 130, 23);

        RiwayatESWL.setName("RiwayatESWL"); // NOI18N
        RiwayatESWL.setPreferredSize(new java.awt.Dimension(80, 23));
        RiwayatESWL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatESWLKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatESWL);
        RiwayatESWL.setBounds(150, 220, 570, 20);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("- Power Max");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(420, 610, 110, 23);

        Power.setName("Power"); // NOI18N
        Power.setPreferredSize(new java.awt.Dimension(80, 23));
        Power.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PowerKeyPressed(evt);
            }
        });
        FormInput.add(Power);
        Power.setBounds(530, 610, 120, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("- Frekuensi");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(420, 640, 110, 23);

        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.setPreferredSize(new java.awt.Dimension(80, 23));
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi);
        Frekuensi.setBounds(530, 640, 120, 23);

        Tembakan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tembakan1.setText("- Tembakan");
        Tembakan1.setName("Tembakan1"); // NOI18N
        Tembakan1.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(Tembakan1);
        Tembakan1.setBounds(420, 670, 110, 23);

        Tembakan.setName("Tembakan"); // NOI18N
        Tembakan.setPreferredSize(new java.awt.Dimension(80, 23));
        Tembakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TembakanKeyPressed(evt);
            }
        });
        FormInput.add(Tembakan);
        Tembakan.setBounds(530, 670, 120, 23);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label23.setText("- Durasi");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(420, 700, 110, 23);

        Durasi.setName("Durasi"); // NOI18N
        Durasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(530, 700, 120, 23);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("Keluhan Selama Tindakan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(10, 750, 140, 23);

        KeluhanTindakan.setName("KeluhanTindakan"); // NOI18N
        KeluhanTindakan.setPreferredSize(new java.awt.Dimension(80, 23));
        KeluhanTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanTindakanKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanTindakan);
        KeluhanTindakan.setBounds(150, 750, 570, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 250, 750, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(10, 740, 750, 1);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("Kompilkasi :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(10, 780, 100, 23);

        Komplikasi.setName("Komplikasi"); // NOI18N
        Komplikasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Komplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiKeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi);
        Komplikasi.setBounds(150, 780, 570, 23);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label26.setText("Evaluasi Post Tindakan :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(10, 810, 130, 23);

        Evaluasi.setName("Evaluasi"); // NOI18N
        Evaluasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Evaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EvaluasiKeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi);
        Evaluasi.setBounds(150, 810, 570, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label27.setText("- ESWL Ke ");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(10, 610, 100, 23);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("- Guide");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(10, 640, 100, 23);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label29.setText("- Ukuran Batu");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(10, 670, 100, 23);

        label30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label30.setText("- Analgetik Pre ESWL");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(10, 700, 100, 23);

        ESWLKe.setName("ESWLKe"); // NOI18N
        ESWLKe.setPreferredSize(new java.awt.Dimension(80, 23));
        ESWLKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ESWLKeKeyPressed(evt);
            }
        });
        FormInput.add(ESWLKe);
        ESWLKe.setBounds(150, 610, 230, 23);

        Guide.setName("Guide"); // NOI18N
        Guide.setPreferredSize(new java.awt.Dimension(80, 23));
        Guide.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GuideKeyPressed(evt);
            }
        });
        FormInput.add(Guide);
        Guide.setBounds(150, 640, 230, 23);

        UkuranBatu.setName("UkuranBatu"); // NOI18N
        UkuranBatu.setPreferredSize(new java.awt.Dimension(80, 23));
        UkuranBatu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UkuranBatuKeyPressed(evt);
            }
        });
        FormInput.add(UkuranBatu);
        UkuranBatu.setBounds(150, 670, 200, 23);

        Analgetik.setName("Analgetik"); // NOI18N
        Analgetik.setPreferredSize(new java.awt.Dimension(80, 23));
        Analgetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnalgetikKeyPressed(evt);
            }
        });
        FormInput.add(Analgetik);
        Analgetik.setBounds(150, 700, 230, 23);

        Lokasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ginjal Kanan", "Ginjal Kiri", "Ureter Kanan", "Ureter Kiri" }));
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(150, 570, 130, 23);

        label31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label31.setText("- Lokasi Tembakan");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(10, 570, 100, 23);

        label32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label32.setText("Pole :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(310, 570, 40, 23);

        Pole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Atas", "Tengah", "Bawah" }));
        Pole.setName("Pole"); // NOI18N
        Pole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PoleKeyPressed(evt);
            }
        });
        FormInput.add(Pole);
        Pole.setBounds(360, 570, 130, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("Hidroneprosis :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(510, 570, 80, 23);

        Hidroneprosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Hidroneprosis.setName("Hidroneprosis"); // NOI18N
        Hidroneprosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidroneprosisKeyPressed(evt);
            }
        });
        FormInput.add(Hidroneprosis);
        Hidroneprosis.setBounds(590, 570, 130, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 250, 750, 1);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(10, 260, 590, 0);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Status Urologi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(10, 310, 90, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(40, 280, 70, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(120, 280, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(180, 280, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(230, 280, 70, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(310, 280, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(380, 280, 40, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(430, 280, 60, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(500, 280, 60, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(580, 280, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(630, 280, 70, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(710, 280, 20, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("TD :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 280, 60, 23);

        StatusUrologi.setFocusTraversalPolicyProvider(true);
        StatusUrologi.setName("StatusUrologi"); // NOI18N
        StatusUrologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusUrologiKeyPressed(evt);
            }
        });
        FormInput.add(StatusUrologi);
        StatusUrologi.setBounds(150, 310, 570, 23);

        label34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label34.setText("Keluhan :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(10, 130, 130, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("Anamnesa");
        label35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(10, 100, 130, 30);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 350, 750, 1);

        label36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label36.setText("Pemeriksaan Penunjang");
        label36.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(10, 350, 130, 30);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Urine Rutin :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(10, 380, 90, 23);

        Urine.setFocusTraversalPolicyProvider(true);
        Urine.setName("Urine"); // NOI18N
        Urine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineKeyPressed(evt);
            }
        });
        FormInput.add(Urine);
        Urine.setBounds(150, 380, 570, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Darah Rutin / Lainnya :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(10, 410, 130, 23);

        Darah.setFocusTraversalPolicyProvider(true);
        Darah.setName("Darah"); // NOI18N
        Darah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeyPressed(evt);
            }
        });
        FormInput.add(Darah);
        Darah.setBounds(150, 410, 570, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("USG Urologi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(10, 440, 130, 23);

        USG.setFocusTraversalPolicyProvider(true);
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(150, 440, 570, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("USG Urologi :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(10, 440, 130, 23);

        TD5.setFocusTraversalPolicyProvider(true);
        TD5.setName("TD5"); // NOI18N
        TD5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TD5KeyPressed(evt);
            }
        });
        FormInput.add(TD5);
        TD5.setBounds(150, 440, 570, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("CT Scan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(10, 500, 130, 23);

        CTScan.setFocusTraversalPolicyProvider(true);
        CTScan.setName("CTScan"); // NOI18N
        CTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTScanKeyPressed(evt);
            }
        });
        FormInput.add(CTScan);
        CTScan.setBounds(150, 500, 570, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("BNO :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(10, 470, 130, 23);

        BNO.setFocusTraversalPolicyProvider(true);
        BNO.setName("BNO"); // NOI18N
        BNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BNOKeyPressed(evt);
            }
        });
        FormInput.add(BNO);
        BNO.setBounds(150, 470, 570, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 540, 750, 1);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("Tindakan ESWL");
        label37.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(10, 540, 130, 30);

        label38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label38.setText("Kv");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(660, 610, 50, 23);

        label39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label39.setText("Hz");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(660, 640, 50, 23);

        label40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label40.setText("Tembakan");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(660, 670, 50, 23);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("Cm");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(360, 670, 30, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("Advice Post Tindakan :");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(10, 840, 130, 23);

        Advice.setName("Advice"); // NOI18N
        Advice.setPreferredSize(new java.awt.Dimension(80, 23));
        Advice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdviceKeyPressed(evt);
            }
        });
        FormInput.add(Advice);
        Advice.setBounds(150, 840, 570, 23);

        BtnTemplateResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnTemplateResume.setMnemonic('2');
        BtnTemplateResume.setToolTipText("Alt+2");
        BtnTemplateResume.setName("BtnTemplateResume"); // NOI18N
        BtnTemplateResume.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTemplateResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTemplateResumeActionPerformed(evt);
            }
        });
        BtnTemplateResume.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTemplateResumeKeyPressed(evt);
            }
        });
        FormInput.add(BtnTemplateResume);
        BtnTemplateResume.setBounds(350, 70, 28, 23);

        label43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label43.setText("Menit");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label43);
        label43.setBounds(660, 700, 50, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Dokumentasi Tindakan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30-09-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Dokumentasi Tindakan", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Ahli Bedah");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");

        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{

                    simpan();

                }
            }


}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Advice,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())==true){
                    //if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();

                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh dokter yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Ahli Bedah");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");

        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){

                                ganti();
                                JOptionPane.showMessageDialog(rootPane,"Data Tersimpan");

                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Ahli Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Asisten/Perawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Waktu Mulai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Waktu Selesai</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diagnosa</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Analgesik/Anastesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Obat Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Uraian Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Focus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Rate</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Power</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Shock</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Diintegrasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kekurangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Anjungan</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2300px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataDokumentasiTindakanESWL.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2300px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA DOKUMENTASI TINDAKAN ESWL<br><br></font>"+
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
                Desktop.getDesktop().browse(f.toURI());

            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt,WaktuSelesai,BtnPetugas);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void WaktuSelesaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuSelesaiKeyPressed
        Valid.pindah2(evt,WaktuMulai,BtnDokter);
    }//GEN-LAST:event_WaktuSelesaiKeyPressed

    private void MnDokumenESWLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDokumenESWLActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()));

            Valid.MyReportqry("rptCetakDokumentasiTindakanESWL.jasper","report","::[ Laporan Dokumentasi Tindakan ESWL ]::",
                "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,hasil_tindakan_eswl.mulai,"+
                "hasil_tindakan_eswl.selesai,hasil_tindakan_eswl.kd_dokter,hasil_tindakan_eswl.nip,hasil_tindakan_eswl.keluhan,hasil_tindakan_eswl.riwayat_penyakit,hasil_tindakan_eswl.riwayat_operasi,hasil_tindakan_eswl.riwayat_eswl,"+
                        "hasil_tindakan_eswl.td,hasil_tindakan_eswl.nadi,hasil_tindakan_eswl.rr,hasil_tindakan_eswl.suhu,hasil_tindakan_eswl.status_urologi,"+
						"hasil_tindakan_eswl.penunjang_urine,hasil_tindakan_eswl.penunjang_darah,hasil_tindakan_eswl.penunjang_usg,hasil_tindakan_eswl.penunjang_bno,hasil_tindakan_eswl.penunjang_ctscan,"+
                        "hasil_tindakan_eswl.lokasi,hasil_tindakan_eswl.pole,hasil_tindakan_eswl.hidroneprosis,"+
						"hasil_tindakan_eswl.tindakan_eswl,hasil_tindakan_eswl.tindakan_guide,hasil_tindakan_eswl.tindakan_ukuran,hasil_tindakan_eswl.tindakan_analgetik,hasil_tindakan_eswl.tindakan_power,hasil_tindakan_eswl.tindakan_frekuensi,hasil_tindakan_eswl.tindakan_tembakan,hasil_tindakan_eswl.tindakan_durasi,"+
						"hasil_tindakan_eswl.tindakan_keluhan,hasil_tindakan_eswl.tindakan_komplikasi,hasil_tindakan_eswl.tindakan_evaluasi,hasil_tindakan_eswl.tindakan_advice,"+
                "dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join hasil_tindakan_eswl on reg_periksa.no_rawat=hasil_tindakan_eswl.no_rawat "+
                "inner join dokter on hasil_tindakan_eswl.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on hasil_tindakan_eswl.nip=petugas.nip where hasil_tindakan_eswl.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' "+
                "and hasil_tindakan_eswl.mulai='"+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+"'",param);
        }
    }//GEN-LAST:event_MnDokumenESWLActionPerformed

    private void WaktuMulaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WaktuMulaiKeyPressed
       Valid.pindah2(evt,Advice,WaktuSelesai);
    }//GEN-LAST:event_WaktuMulaiKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,BtnDokter,Keluhan);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        Valid.pindah(evt,BtnPetugas,RiwayatPenyakit);
    }//GEN-LAST:event_KeluhanKeyPressed

    private void RiwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeyPressed

    }//GEN-LAST:event_RiwayatPenyakitKeyPressed

    private void OperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperasiKeyPressed

    }//GEN-LAST:event_OperasiKeyPressed

    private void RiwayatESWLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatESWLKeyPressed

    }//GEN-LAST:event_RiwayatESWLKeyPressed

    private void PowerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PowerKeyPressed

    }//GEN-LAST:event_PowerKeyPressed

    private void FrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiKeyPressed

    }//GEN-LAST:event_FrekuensiKeyPressed

    private void TembakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TembakanKeyPressed

    }//GEN-LAST:event_TembakanKeyPressed

    private void DurasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DurasiKeyPressed

    }//GEN-LAST:event_DurasiKeyPressed

    private void KeluhanTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanTindakanKeyPressed

    }//GEN-LAST:event_KeluhanTindakanKeyPressed

    private void KomplikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomplikasiKeyPressed

    }//GEN-LAST:event_KomplikasiKeyPressed

    private void EvaluasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EvaluasiKeyPressed
        Valid.pindah(evt,Advice,BtnSimpan);
    }//GEN-LAST:event_EvaluasiKeyPressed

    private void ESWLKeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ESWLKeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ESWLKeKeyPressed

    private void GuideKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GuideKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GuideKeyPressed

    private void UkuranBatuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UkuranBatuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UkuranBatuKeyPressed

    private void AnalgetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnalgetikKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnalgetikKeyPressed

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed

    }//GEN-LAST:event_LokasiKeyPressed

    private void PoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PoleKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PoleKeyPressed

    private void HidroneprosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HidroneprosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_HidroneprosisKeyPressed

    private void TDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDKeyPressed

    }//GEN-LAST:event_TDKeyPressed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt,TD,RR);
    }//GEN-LAST:event_NadiKeyPressed

    private void RRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RRKeyPressed
        Valid.pindah(evt,Nadi,Suhu);
    }//GEN-LAST:event_RRKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt,RR,Suhu);
    }//GEN-LAST:event_SuhuKeyPressed

    private void StatusUrologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusUrologiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusUrologiKeyPressed

    private void UrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrineKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UrineKeyPressed

    private void DarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DarahKeyPressed

    private void USGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_USGKeyPressed

    private void TD5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TD5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TD5KeyPressed

    private void CTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTScanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTScanKeyPressed

    private void BNOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BNOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BNOKeyPressed

    private void AdviceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdviceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdviceKeyPressed

    private void BtnTemplateResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTemplateResumeActionPerformed
        template.emptTeks();

        template.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        template.setLocationRelativeTo(internalFrame1);
        template.setVisible(true);
    }//GEN-LAST:event_BtnTemplateResumeActionPerformed

    private void BtnTemplateResumeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTemplateResumeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnTemplateResumeKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMHasilTindakanESWL dialog = new RMHasilTindakanESWL(new javax.swing.JFrame(), true);
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
    private widget.TextBox Advice;
    private widget.TextBox Analgetik;
    private widget.TextBox BNO;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTemplateResume;
    private widget.TextBox CTScan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Darah;
    private widget.TextBox Durasi;
    private widget.TextBox ESWLKe;
    private widget.TextBox Evaluasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Frekuensi;
    private widget.TextBox Guide;
    private widget.ComboBox Hidroneprosis;
    private widget.TextBox Jk;
    private widget.TextBox KdDokter;
    private widget.TextBox Keluhan;
    private widget.TextBox KeluhanTindakan;
    private widget.TextBox Komplikasi;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Lokasi;
    private javax.swing.JMenuItem MnDokumenESWL;
    private widget.TextBox NIP;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox Operasi;
    private widget.ComboBox Pole;
    private widget.TextBox Power;
    private widget.TextBox RR;
    private widget.TextBox RiwayatESWL;
    private widget.TextBox RiwayatPenyakit;
    private widget.ScrollPane Scroll;
    private widget.TextBox StatusUrologi;
    private widget.TextBox Suhu;
    private widget.TextBox TCari;
    private widget.TextBox TD;
    private widget.TextBox TD5;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox Tembakan;
    private widget.Label Tembakan1;
    private widget.TextBox TglLahir;
    private widget.TextBox USG;
    private widget.TextBox UkuranBatu;
    private widget.TextBox Urine;
    private widget.Tanggal WaktuMulai;
    private widget.Tanggal WaktuSelesai;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,hasil_tindakan_eswl.mulai,"+
                        "hasil_tindakan_eswl.selesai,hasil_tindakan_eswl.kd_dokter,hasil_tindakan_eswl.nip,hasil_tindakan_eswl.keluhan,hasil_tindakan_eswl.riwayat_penyakit,hasil_tindakan_eswl.riwayat_operasi,hasil_tindakan_eswl.riwayat_eswl,"+
                        "hasil_tindakan_eswl.td,hasil_tindakan_eswl.nadi,hasil_tindakan_eswl.rr,hasil_tindakan_eswl.suhu,hasil_tindakan_eswl.status_urologi,"+
			"hasil_tindakan_eswl.penunjang_urine,hasil_tindakan_eswl.penunjang_darah,hasil_tindakan_eswl.penunjang_usg,hasil_tindakan_eswl.penunjang_bno,hasil_tindakan_eswl.penunjang_ctscan,"+
                        "hasil_tindakan_eswl.lokasi,hasil_tindakan_eswl.pole,hasil_tindakan_eswl.hidroneprosis,"+
			"hasil_tindakan_eswl.tindakan_eswl,hasil_tindakan_eswl.tindakan_guide,hasil_tindakan_eswl.tindakan_ukuran,hasil_tindakan_eswl.tindakan_analgetik,hasil_tindakan_eswl.tindakan_power,hasil_tindakan_eswl.tindakan_frekuensi,hasil_tindakan_eswl.tindakan_tembakan,hasil_tindakan_eswl.tindakan_durasi,"+
			"hasil_tindakan_eswl.tindakan_keluhan,hasil_tindakan_eswl.tindakan_komplikasi,hasil_tindakan_eswl.tindakan_evaluasi,hasil_tindakan_eswl.tindakan_advice,"+
                        "dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_tindakan_eswl on reg_periksa.no_rawat=hasil_tindakan_eswl.no_rawat "+
                        "inner join dokter on hasil_tindakan_eswl.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on hasil_tindakan_eswl.nip=petugas.nip where hasil_tindakan_eswl.mulai between ? and ? order by hasil_tindakan_eswl.mulai");
            }else{
                ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,hasil_tindakan_eswl.mulai,"+
                        "hasil_tindakan_eswl.selesai,hasil_tindakan_eswl.kd_dokter,hasil_tindakan_eswl.nip,hasil_tindakan_eswl.keluhan,hasil_tindakan_eswl.riwayat_penyakit,hasil_tindakan_eswl.riwayat_operasi,hasil_tindakan_eswl.riwayat_eswl,"+
                        "hasil_tindakan_eswl.td,hasil_tindakan_eswl.nadi,hasil_tindakan_eswl.rr,hasil_tindakan_eswl.suhu,hasil_tindakan_eswl.status_urologi,"+
			"hasil_tindakan_eswl.penunjang_urine,hasil_tindakan_eswl.penunjang_darah,hasil_tindakan_eswl.penunjang_usg,hasil_tindakan_eswl.penunjang_bno,hasil_tindakan_eswl.penunjang_ctscan,"+
                        "hasil_tindakan_eswl.lokasi,hasil_tindakan_eswl.pole,hasil_tindakan_eswl.hidroneprosis,"+
                        "hasil_tindakan_eswl.tindakan_eswl,hasil_tindakan_eswl.tindakan_guide,hasil_tindakan_eswl.tindakan_ukuran,hasil_tindakan_eswl.tindakan_analgetik,hasil_tindakan_eswl.tindakan_power,hasil_tindakan_eswl.tindakan_frekuensi,hasil_tindakan_eswl.tindakan_tembakan,hasil_tindakan_eswl.tindakan_durasi,"+
                        "hasil_tindakan_eswl.tindakan_keluhan,hasil_tindakan_eswl.tindakan_komplikasi,hasil_tindakan_eswl.tindakan_evaluasi,hasil_tindakan_eswl.tindakan_advice,"+
                        "dokter.nm_dokter,petugas.nama from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join hasil_tindakan_eswl on reg_periksa.no_rawat=hasil_tindakan_eswl.no_rawat "+
                        "inner join dokter on hasil_tindakan_eswl.kd_dokter=dokter.kd_dokter "+
                        "inner join petugas on hasil_tindakan_eswl.nip=petugas.nip where "+
                        "hasil_tindakan_eswl.mulai between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                        "hasil_tindakan_eswl.keluhan like ? or hasil_tindakan_eswl.riwayat_penyakit like ?) order by hasil_tindakan_eswl.mulai");
            }

            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                                                rs.getString("no_rawat"),
						rs.getString("no_rkm_medis"),
						rs.getString("nm_pasien"),
						rs.getString("tgl_lahir"),
						rs.getString("jk"),
						rs.getString("kd_dokter"),
						rs.getString("nm_dokter"),
						rs.getString("nip"),
						rs.getString("nama"),
                                                rs.getString("mulai"),
						rs.getString("selesai"),
						rs.getString("keluhan"),
						rs.getString("riwayat_penyakit"),
						rs.getString("riwayat_operasi"),
						rs.getString("riwayat_eswl"),
						rs.getString("td"),
						rs.getString("nadi"),
						rs.getString("rr"),
						rs.getString("suhu"),
						rs.getString("status_urologi"),
						rs.getString("penunjang_urine"),
						rs.getString("penunjang_darah"),
						rs.getString("penunjang_usg"),
						rs.getString("penunjang_bno"),
						rs.getString("penunjang_ctscan"),
						rs.getString("lokasi"),
						rs.getString("pole"),
						rs.getString("hidroneprosis"),
						rs.getString("tindakan_eswl"),
						rs.getString("tindakan_guide"),
						rs.getString("tindakan_ukuran"),
						rs.getString("tindakan_analgetik"),
						rs.getString("tindakan_power"),
						rs.getString("tindakan_frekuensi"),
						rs.getString("tindakan_tembakan"),
						rs.getString("tindakan_durasi"),
						rs.getString("tindakan_keluhan"),
						rs.getString("tindakan_komplikasi"),
						rs.getString("tindakan_evaluasi"),
						rs.getString("tindakan_advice")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }

        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {

        WaktuMulai.setDate(new Date());
        WaktuSelesai.setDate(new Date());
        Keluhan.setText("");
        RiwayatPenyakit.setText("");
				Operasi.setText("");
				RiwayatESWL.setText("");
				TD.setText("");
				Nadi.setText("");
				RR.setText("");
				Suhu.setText("");
				StatusUrologi.setText("");
				Urine.setText("");
				Darah.setText("");
				USG.setText("");
				BNO.setText("");
				CTScan.setText("");
				Lokasi.setSelectedItem("");
				Pole.setSelectedItem("");
				Hidroneprosis.setSelectedItem("");
				ESWLKe.setText("");
				Guide.setText("");
				UkuranBatu.setText("");
				Analgetik.setText("");
				Power.setText("");
				Frekuensi.setText("");
				Tembakan.setText("");
				Durasi.setText("");
				KeluhanTindakan.setText("");
				Komplikasi.setText("");
				Evaluasi.setText("");
				Advice.setText("");
        TabRawat.setSelectedIndex(0);
        Keluhan.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Keluhan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            RiwayatPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Operasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RiwayatESWL.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            TD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Nadi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            RR.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Suhu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            StatusUrologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Urine.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Darah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            USG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            BNO.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            CTScan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Lokasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Pole.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Hidroneprosis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            ESWLKe.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Guide.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            UkuranBatu.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Analgetik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Power.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Frekuensi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Tembakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Durasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            KeluhanTindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Komplikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Evaluasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Advice.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());


            Valid.SetTgl2(WaktuMulai,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Valid.SetTgl2(WaktuSelesai,tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,"+
                    "reg_periksa.jam_reg from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.gethasil_tindakan_eswl());
        BtnHapus.setEnabled(akses.gethasil_tindakan_eswl());
        BtnEdit.setEnabled(akses.gethasil_tindakan_eswl());
        BtnEdit.setEnabled(akses.gethasil_tindakan_eswl());
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan Dokter...!!");
            }
        }
    }

    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from hasil_tindakan_eswl where no_rawat=? and mulai=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("hasil_tindakan_eswl","no_rawat=? and mulai=?","no_rawat=?,mulai=?,selesai=?,kd_dokter=?,nip=?,keluhan=?,riwayat_penyakit=?,"+
                "riwayat_operasi=?,riwayat_eswl=?,td=?,nadi=?,rr=?,suhu=?,status_urologi=?,"+
				"penunjang_urine=?,penunjang_darah=?,penunjang_usg=?,penunjang_bno=?,penunjang_ctscan=?,lokasi=?,pole=?,"+
				"hidroneprosis=?,tindakan_eswl=?,tindakan_guide=?,tindakan_ukuran=?,tindakan_analgetik=?,tindakan_power=?,tindakan_frekuensi=?,"+
				"tindakan_tembakan=?,tindakan_durasi=?,tindakan_keluhan=?,tindakan_komplikasi=?,"+
                "tindakan_evaluasi=?,tindakan_advice=?",36,new String[]{
                TNoRw.getText(),
				Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),
				KdDokter.getText(),
                NIP.getText(),
				Keluhan.getText(),
				RiwayatPenyakit.getText(),
				Operasi.getText(),
				RiwayatESWL.getText(),
				TD.getText(),
				Nadi.getText(),
				RR.getText(),
				Suhu.getText(),
				StatusUrologi.getText(),
				Urine.getText(),
				Darah.getText(),
				USG.getText(),
				BNO.getText(),
				CTScan.getText(),
				Lokasi.getSelectedItem().toString(),
				Pole.getSelectedItem().toString(),
				Hidroneprosis.getSelectedItem().toString(),
				ESWLKe.getText(),
				Guide.getText(),
				UkuranBatu.getText(),
				Analgetik.getText(),
				Power.getText(),
				Frekuensi.getText(),
				Tembakan.getText(),
				Durasi.getText(),
				KeluhanTindakan.getText(),
				Komplikasi.getText(),
				Evaluasi.getText(),
				Advice.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()
            })==true){
               tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
               tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
               tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
               tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
               tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),4);
               tbObat.setValueAt(KdDokter.getText(),tbObat.getSelectedRow(),5);
               tbObat.setValueAt(NmDokter.getText(),tbObat.getSelectedRow(),6);
               tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),7);
               tbObat.setValueAt(NmPetugas.getText(),tbObat.getSelectedRow(),8);
               tbObat.setValueAt(Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),9);
               tbObat.setValueAt(Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),10);
               tbObat.setValueAt(Keluhan.getText(),tbObat.getSelectedRow(),11);
			   tbObat.setValueAt(RiwayatPenyakit.getText(),tbObat.getSelectedRow(),12);
			   tbObat.setValueAt(Operasi.getText(),tbObat.getSelectedRow(),13);
			   tbObat.setValueAt(RiwayatESWL.getText(),tbObat.getSelectedRow(),14);
			   tbObat.setValueAt(TD.getText(),tbObat.getSelectedRow(),15);
			   tbObat.setValueAt(Nadi.getText(),tbObat.getSelectedRow(),16);
			   tbObat.setValueAt(RR.getText(),tbObat.getSelectedRow(),17);
			   tbObat.setValueAt(Suhu.getText(),tbObat.getSelectedRow(),18);
			   tbObat.setValueAt(StatusUrologi.getText(),tbObat.getSelectedRow(),19);
			   tbObat.setValueAt(Urine.getText(),tbObat.getSelectedRow(),20);
			   tbObat.setValueAt(Darah.getText(),tbObat.getSelectedRow(),21);
			   tbObat.setValueAt(USG.getText(),tbObat.getSelectedRow(),22);
			   tbObat.setValueAt(BNO.getText(),tbObat.getSelectedRow(),23);
			   tbObat.setValueAt(CTScan.getText(),tbObat.getSelectedRow(),24);
			   tbObat.setValueAt(Lokasi.getSelectedItem(),tbObat.getSelectedRow(),25);
			   tbObat.setValueAt(Pole.getSelectedItem(),tbObat.getSelectedRow(),26);
			   tbObat.setValueAt(Hidroneprosis.getSelectedItem(),tbObat.getSelectedRow(),27);
			   tbObat.setValueAt(ESWLKe.getText(),tbObat.getSelectedRow(),28);
			   tbObat.setValueAt(Guide.getText(),tbObat.getSelectedRow(),29);
			   tbObat.setValueAt(UkuranBatu.getText(),tbObat.getSelectedRow(),30);
			   tbObat.setValueAt(Analgetik.getText(),tbObat.getSelectedRow(),31);
			   tbObat.setValueAt(Power.getText(),tbObat.getSelectedRow(),32);
			   tbObat.setValueAt(Frekuensi.getText(),tbObat.getSelectedRow(),33);
			   tbObat.setValueAt(Tembakan.getText(),tbObat.getSelectedRow(),34);
			   tbObat.setValueAt(Durasi.getText(),tbObat.getSelectedRow(),35);
			   tbObat.setValueAt(KeluhanTindakan.getText(),tbObat.getSelectedRow(),36);
			   tbObat.setValueAt(Komplikasi.getText(),tbObat.getSelectedRow(),37);
			   tbObat.setValueAt(Evaluasi.getText(),tbObat.getSelectedRow(),38);
			   tbObat.setValueAt(Advice.getText(),tbObat.getSelectedRow(),39);



               emptTeks();
               TabRawat.setSelectedIndex(1);
        }
    }




    private void simpan() {
        if(Sequel.menyimpantf("hasil_tindakan_eswl","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat & Waktu Mulai",34,new String[]{
                TNoRw.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),KdDokter.getText(),NIP.getText(),
				Keluhan.getText(),
				RiwayatPenyakit.getText(),
				Operasi.getText(),
				RiwayatESWL.getText(),
				TD.getText(),
				Nadi.getText(),
				RR.getText(),
				Suhu.getText(),
				StatusUrologi.getText(),
				Urine.getText(),
				Darah.getText(),
				USG.getText(),
				BNO.getText(),
				CTScan.getText(),
				Lokasi.getSelectedItem().toString(),
				Pole.getSelectedItem().toString(),
				Hidroneprosis.getSelectedItem().toString(),
				ESWLKe.getText(),
				Guide.getText(),
				UkuranBatu.getText(),

				Analgetik.getText(),
                                Power.getText(),
				Frekuensi.getText(),
				Tembakan.getText(),
				Durasi.getText(),
				KeluhanTindakan.getText(),
				Komplikasi.getText(),
				Evaluasi.getText(),
				Advice.getText()

            })==true){
            tabMode.addRow(new String[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),Jk.getText(),KdDokter.getText(),NmDokter.getText(),NIP.getText(),
                NmPetugas.getText(),Valid.SetTgl(WaktuMulai.getSelectedItem()+"")+" "+WaktuMulai.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(WaktuSelesai.getSelectedItem()+"")+" "+WaktuSelesai.getSelectedItem().toString().substring(11,19),
				Keluhan.getText(),
				RiwayatPenyakit.getText(),
				Operasi.getText(),
				RiwayatESWL.getText(),
				TD.getText(),
				Nadi.getText(),
				RR.getText(),
				Suhu.getText(),
				StatusUrologi.getText(),
				Urine.getText(),
				Darah.getText(),
				USG.getText(),
				BNO.getText(),
				CTScan.getText(),
				Lokasi.getSelectedItem().toString(),
				Pole.getSelectedItem().toString(),
				Hidroneprosis.getSelectedItem().toString(),
				ESWLKe.getText(),
				Guide.getText(),
				UkuranBatu.getText(),
				Analgetik.getText(),
                                Power.getText(),
				Frekuensi.getText(),
				Tembakan.getText(),
				Durasi.getText(),
				KeluhanTindakan.getText(),
				Komplikasi.getText(),
				Evaluasi.getText(),
				Advice.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            JOptionPane.showMessageDialog(rootPane,"Data Tersimpan");
            emptTeks();
        }
    }
}
