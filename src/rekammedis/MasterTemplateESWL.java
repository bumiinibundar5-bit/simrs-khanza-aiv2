package rekammedis;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MasterTemplateESWL extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public MasterTemplateESWL(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
        Object[] row={
            "No",
            "Nama Template",
            
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
            "Advice"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 30; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
             if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(180);
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
            
            
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        Kd.setDocument(new batasInput((byte)5).getKata(Kd));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));    
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
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        label13 = new widget.Label();
        NamaTemplate = new widget.TextBox();
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
        jSeparator7 = new javax.swing.JSeparator();
        label37 = new widget.Label();
        label38 = new widget.Label();
        label39 = new widget.Label();
        label40 = new widget.Label();
        label41 = new widget.Label();
        label42 = new widget.Label();
        Advice = new widget.TextBox();
        label16 = new widget.Label();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
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
        jSeparator1 = new javax.swing.JSeparator();
        Keluhan = new widget.TextBox();
        label17 = new widget.Label();
        RiwayatPenyakit = new widget.TextBox();
        label18 = new widget.Label();
        Operasi = new widget.TextBox();
        label19 = new widget.Label();
        RiwayatESWL = new widget.TextBox();
        label34 = new widget.Label();
        label35 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelGlass9 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template Resume Inap]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(694, 843));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(694, 843));
        FormInput.setLayout(null);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label12.setText("No.Template :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label12);
        label12.setBounds(10, 10, 85, 23);

        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(89, 10, 70, 23);

        label13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label13.setText("Nama Template :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label13);
        label13.setBounds(175, 10, 100, 23);

        NamaTemplate.setName("NamaTemplate"); // NOI18N
        NamaTemplate.setPreferredSize(new java.awt.Dimension(207, 23));
        NamaTemplate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaTemplateKeyPressed(evt);
            }
        });
        FormInput.add(NamaTemplate);
        NamaTemplate.setBounds(269, 10, 410, 23);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("- Power Max");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(420, 560, 110, 23);

        Power.setName("Power"); // NOI18N
        Power.setPreferredSize(new java.awt.Dimension(80, 23));
        Power.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PowerKeyPressed(evt);
            }
        });
        FormInput.add(Power);
        Power.setBounds(530, 560, 120, 23);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("- Frekuensi");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(420, 590, 110, 23);

        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.setPreferredSize(new java.awt.Dimension(80, 23));
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        FormInput.add(Frekuensi);
        Frekuensi.setBounds(530, 590, 120, 23);

        Tembakan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tembakan1.setText("- Tembakan");
        Tembakan1.setName("Tembakan1"); // NOI18N
        Tembakan1.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(Tembakan1);
        Tembakan1.setBounds(420, 620, 110, 23);

        Tembakan.setName("Tembakan"); // NOI18N
        Tembakan.setPreferredSize(new java.awt.Dimension(80, 23));
        Tembakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TembakanKeyPressed(evt);
            }
        });
        FormInput.add(Tembakan);
        Tembakan.setBounds(530, 620, 120, 23);

        label23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label23.setText("- Durasi");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(420, 650, 110, 23);

        Durasi.setName("Durasi"); // NOI18N
        Durasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Durasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DurasiKeyPressed(evt);
            }
        });
        FormInput.add(Durasi);
        Durasi.setBounds(530, 650, 120, 23);

        label24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label24.setText("Keluhan Selama Tindakan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(10, 700, 140, 23);

        KeluhanTindakan.setName("KeluhanTindakan"); // NOI18N
        KeluhanTindakan.setPreferredSize(new java.awt.Dimension(80, 23));
        KeluhanTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanTindakanKeyPressed(evt);
            }
        });
        FormInput.add(KeluhanTindakan);
        KeluhanTindakan.setBounds(150, 700, 570, 23);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(10, 690, 750, 1);

        label25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label25.setText("Kompilkasi :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(10, 730, 100, 23);

        Komplikasi.setName("Komplikasi"); // NOI18N
        Komplikasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Komplikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomplikasiKeyPressed(evt);
            }
        });
        FormInput.add(Komplikasi);
        Komplikasi.setBounds(150, 730, 570, 23);

        label26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label26.setText("Evaluasi Post Tindakan :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(10, 760, 130, 23);

        Evaluasi.setName("Evaluasi"); // NOI18N
        Evaluasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Evaluasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EvaluasiKeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi);
        Evaluasi.setBounds(150, 760, 570, 23);

        label27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label27.setText("- ESWL Ke ");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(10, 560, 100, 23);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label28.setText("- Guide");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(10, 590, 100, 23);

        label29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label29.setText("- Ukuran Batu");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(10, 620, 100, 23);

        label30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label30.setText("- Analgetik Pre ESWL");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(10, 650, 100, 23);

        ESWLKe.setName("ESWLKe"); // NOI18N
        ESWLKe.setPreferredSize(new java.awt.Dimension(80, 23));
        ESWLKe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ESWLKeKeyPressed(evt);
            }
        });
        FormInput.add(ESWLKe);
        ESWLKe.setBounds(150, 560, 230, 23);

        Guide.setName("Guide"); // NOI18N
        Guide.setPreferredSize(new java.awt.Dimension(80, 23));
        Guide.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GuideKeyPressed(evt);
            }
        });
        FormInput.add(Guide);
        Guide.setBounds(150, 590, 230, 23);

        UkuranBatu.setName("UkuranBatu"); // NOI18N
        UkuranBatu.setPreferredSize(new java.awt.Dimension(80, 23));
        UkuranBatu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UkuranBatuKeyPressed(evt);
            }
        });
        FormInput.add(UkuranBatu);
        UkuranBatu.setBounds(150, 620, 230, 23);

        Analgetik.setName("Analgetik"); // NOI18N
        Analgetik.setPreferredSize(new java.awt.Dimension(80, 23));
        Analgetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnalgetikKeyPressed(evt);
            }
        });
        FormInput.add(Analgetik);
        Analgetik.setBounds(150, 650, 230, 23);

        Lokasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ginjal Kanan", "Ginjal Kiri", "Ureter Kanan", "Ureter Kiri" }));
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(150, 520, 130, 23);

        label31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label31.setText("- Lokasi Tembakan");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(10, 520, 100, 23);

        label32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label32.setText("Pole :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(310, 520, 40, 23);

        Pole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Atas", "Tengah", "Bawah" }));
        Pole.setName("Pole"); // NOI18N
        Pole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PoleKeyPressed(evt);
            }
        });
        FormInput.add(Pole);
        Pole.setBounds(360, 520, 130, 23);

        label33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label33.setText("Hidroneprosis :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(510, 520, 80, 23);

        Hidroneprosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Hidroneprosis.setName("Hidroneprosis"); // NOI18N
        Hidroneprosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HidroneprosisKeyPressed(evt);
            }
        });
        FormInput.add(Hidroneprosis);
        Hidroneprosis.setBounds(590, 520, 130, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 490, 750, 1);

        label37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label37.setText("Tindakan ESWL");
        label37.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(10, 490, 130, 30);

        label38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label38.setText("Kv");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(660, 560, 50, 23);

        label39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label39.setText("Hz");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(660, 590, 50, 23);

        label40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label40.setText("Tembakan");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label40);
        label40.setBounds(660, 620, 50, 23);

        label41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label41.setText("Menit");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label41);
        label41.setBounds(660, 650, 50, 23);

        label42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label42.setText("Advice Post Tindakan :");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label42);
        label42.setBounds(10, 790, 130, 23);

        Advice.setName("Advice"); // NOI18N
        Advice.setPreferredSize(new java.awt.Dimension(80, 23));
        Advice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AdviceKeyPressed(evt);
            }
        });
        FormInput.add(Advice);
        Advice.setBounds(150, 790, 570, 23);

        label16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label16.setText("Pemeriksaan Fisik");
        label16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label16);
        label16.setBounds(10, 200, 130, 30);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 200, 750, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 200, 750, 1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Status Urologi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(10, 260, 90, 23);

        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        TD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDKeyPressed(evt);
            }
        });
        FormInput.add(TD);
        TD.setBounds(40, 230, 70, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("mmHg");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(120, 230, 50, 23);

        jLabel17.setText("Nadi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(180, 230, 40, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        FormInput.add(Nadi);
        Nadi.setBounds(230, 230, 70, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("x/menit");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(310, 230, 50, 23);

        jLabel26.setText("RR :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(380, 230, 40, 23);

        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        RR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RRKeyPressed(evt);
            }
        });
        FormInput.add(RR);
        RR.setBounds(430, 230, 60, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("x/menit");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(500, 230, 60, 23);

        jLabel18.setText("Suhu :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(580, 230, 40, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(630, 230, 70, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("°C");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(710, 230, 20, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("TD :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(10, 230, 60, 23);

        StatusUrologi.setFocusTraversalPolicyProvider(true);
        StatusUrologi.setName("StatusUrologi"); // NOI18N
        StatusUrologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusUrologiKeyPressed(evt);
            }
        });
        FormInput.add(StatusUrologi);
        StatusUrologi.setBounds(150, 260, 570, 23);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 300, 750, 1);

        label36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label36.setText("Pemeriksaan Penunjang");
        label36.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(10, 300, 130, 30);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Urine Rutin :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(10, 330, 90, 23);

        Urine.setFocusTraversalPolicyProvider(true);
        Urine.setName("Urine"); // NOI18N
        Urine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineKeyPressed(evt);
            }
        });
        FormInput.add(Urine);
        Urine.setBounds(150, 330, 570, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Darah Rutin / Lainnya :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(10, 360, 130, 23);

        Darah.setFocusTraversalPolicyProvider(true);
        Darah.setName("Darah"); // NOI18N
        Darah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeyPressed(evt);
            }
        });
        FormInput.add(Darah);
        Darah.setBounds(150, 360, 570, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("USG Urologi :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(10, 390, 130, 23);

        USG.setFocusTraversalPolicyProvider(true);
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(150, 390, 570, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("USG Urologi :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(10, 390, 130, 23);

        TD5.setFocusTraversalPolicyProvider(true);
        TD5.setName("TD5"); // NOI18N
        TD5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TD5KeyPressed(evt);
            }
        });
        FormInput.add(TD5);
        TD5.setBounds(150, 390, 570, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("CT Scan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(10, 450, 130, 23);

        CTScan.setFocusTraversalPolicyProvider(true);
        CTScan.setName("CTScan"); // NOI18N
        CTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTScanKeyPressed(evt);
            }
        });
        FormInput.add(CTScan);
        CTScan.setBounds(150, 450, 570, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("BNO :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(10, 420, 130, 23);

        BNO.setFocusTraversalPolicyProvider(true);
        BNO.setName("BNO"); // NOI18N
        BNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BNOKeyPressed(evt);
            }
        });
        FormInput.add(BNO);
        BNO.setBounds(150, 420, 570, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 50, 750, 1);

        Keluhan.setName("Keluhan"); // NOI18N
        Keluhan.setPreferredSize(new java.awt.Dimension(80, 23));
        Keluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanKeyPressed(evt);
            }
        });
        FormInput.add(Keluhan);
        Keluhan.setBounds(150, 80, 570, 20);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("Riwayat Penyakit :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(10, 110, 130, 23);

        RiwayatPenyakit.setName("RiwayatPenyakit"); // NOI18N
        RiwayatPenyakit.setPreferredSize(new java.awt.Dimension(80, 23));
        RiwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPenyakit);
        RiwayatPenyakit.setBounds(150, 110, 570, 20);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label18.setText("Riwayat Operasi Urologi :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label18);
        label18.setBounds(10, 140, 130, 23);

        Operasi.setName("Operasi"); // NOI18N
        Operasi.setPreferredSize(new java.awt.Dimension(80, 23));
        Operasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                OperasiKeyPressed(evt);
            }
        });
        FormInput.add(Operasi);
        Operasi.setBounds(150, 140, 570, 20);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("Riwayat ESWL :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(10, 170, 130, 23);

        RiwayatESWL.setName("RiwayatESWL"); // NOI18N
        RiwayatESWL.setPreferredSize(new java.awt.Dimension(80, 23));
        RiwayatESWL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatESWLKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatESWL);
        RiwayatESWL.setBounds(150, 170, 570, 20);

        label34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label34.setText("Keluhan :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(10, 80, 130, 23);

        label35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label35.setText("Anamnesa");
        label35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(10, 50, 130, 30);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Template", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbDokter.setAutoCreateRowSorter(true);
        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDokter);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(530, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Template", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
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

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(label10);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbDokter.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
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
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(NamaTemplate.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            if(Valid.hapusTabletf(tabMode,Kd,"template_hasil_tindakan_eswl","no_template")==true){
                if(tbDokter.getSelectedRow()!= -1){
                    tabMode.removeRow(tbDokter.getSelectedRow());
                    LCount.setText(""+tabMode.getRowCount());
                    emptTeks();
                }
            }
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
       if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(NamaTemplate.getText().trim().equals("")){
            Valid.textKosong(NamaTemplate,"Nama Template");
        
        }else{
            	
				if(Valid.editTabletf(tabMode,"template_hasil_tindakan_eswl","no_template","?","no_template=?,nama_template=?,keluhan=?,riwayat_penyakit=?,"+
                "riwayat_operasi=?,riwayat_eswl=?,td=?,nadi=?,rr=?,suhu=?,status_urologi=?,"+
				"penunjang_urine=?,penunjang_darah=?,penunjang_usg=?,penunjang_bno=?,penunjang_ctscan=?,lokasi=?,pole=?,"+
				"hidroneprosis=?,tindakan_eswl=?,tindakan_guide=?,tindakan_ukuran=?,tindakan_analgetik=?,tindakan_power=?,tindakan_frekuensi=?,"+
				"tindakan_tembakan=?,tindakan_durasi=?,tindakan_keluhan=?,tindakan_komplikasi=?,"+
                "tindakan_evaluasi=?,tindakan_advice=?",32,new String[]{
                Kd.getText(),NamaTemplate.getText(),
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
                tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
            })==true){
               tbDokter.setValueAt(Kd.getText(),tbDokter.getSelectedRow(),0);
               tbDokter.setValueAt(NamaTemplate.getText(),tbDokter.getSelectedRow(),1);
              tbDokter.setValueAt(Keluhan.getText(),tbDokter.getSelectedRow(),2);
			  tbDokter.setValueAt(RiwayatPenyakit.getText(),tbDokter.getSelectedRow(),3);
			  tbDokter.setValueAt(Operasi.getText(),tbDokter.getSelectedRow(),4);
			  tbDokter.setValueAt(RiwayatESWL.getText(),tbDokter.getSelectedRow(),5);
			  tbDokter.setValueAt(TD.getText(),tbDokter.getSelectedRow(),6);
			  tbDokter.setValueAt(Nadi.getText(),tbDokter.getSelectedRow(),7);
			  tbDokter.setValueAt(RR.getText(),tbDokter.getSelectedRow(),8);
			  tbDokter.setValueAt(Suhu.getText(),tbDokter.getSelectedRow(),9);
			  tbDokter.setValueAt(StatusUrologi.getText(),tbDokter.getSelectedRow(),10);
			  tbDokter.setValueAt(Urine.getText(),tbDokter.getSelectedRow(),11);
			  tbDokter.setValueAt(Darah.getText(),tbDokter.getSelectedRow(),12);
			  tbDokter.setValueAt(USG.getText(),tbDokter.getSelectedRow(),13);
			  tbDokter.setValueAt(BNO.getText(),tbDokter.getSelectedRow(),14);
			  tbDokter.setValueAt(CTScan.getText(),tbDokter.getSelectedRow(),15);
			  tbDokter.setValueAt(Lokasi.getSelectedItem(),tbDokter.getSelectedRow(),16);
			  tbDokter.setValueAt(Pole.getSelectedItem(),tbDokter.getSelectedRow(),17);
			  tbDokter.setValueAt(Hidroneprosis.getSelectedItem(),tbDokter.getSelectedRow(),18);
			  tbDokter.setValueAt(ESWLKe.getText(),tbDokter.getSelectedRow(),19);
			  tbDokter.setValueAt(Guide.getText(),tbDokter.getSelectedRow(),20);
			  tbDokter.setValueAt(UkuranBatu.getText(),tbDokter.getSelectedRow(),21);
			  tbDokter.setValueAt(Analgetik.getText(),tbDokter.getSelectedRow(),22);
			  tbDokter.setValueAt(Power.getText(),tbDokter.getSelectedRow(),23);
			  tbDokter.setValueAt(Frekuensi.getText(),tbDokter.getSelectedRow(),24);
			  tbDokter.setValueAt(Tembakan.getText(),tbDokter.getSelectedRow(),25);
			  tbDokter.setValueAt(Durasi.getText(),tbDokter.getSelectedRow(),26);
			  tbDokter.setValueAt(KeluhanTindakan.getText(),tbDokter.getSelectedRow(),27);
			  tbDokter.setValueAt(Komplikasi.getText(),tbDokter.getSelectedRow(),28);
			  tbDokter.setValueAt(Evaluasi.getText(),tbDokter.getSelectedRow(),29);
			  tbDokter.setValueAt(Advice.getText(),tbDokter.getSelectedRow(),30);
				
				
                
                emptTeks();
                TabRawat.setSelectedIndex(1);
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
       dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kd.getText().trim().equals("")){
            Valid.textKosong(Kd,"No.Template");
        }else if(NamaTemplate.getText().trim().equals("")){
            Valid.textKosong(NamaTemplate,"Nama Template");
        
        }else{
           
				
				if(Sequel.menyimpantf("template_hasil_tindakan_eswl","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Template",31,new String[]{
					Kd.getText(),NamaTemplate.getText(),
               
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
                                Power.getText(),
				Analgetik.getText(),
				Frekuensi.getText(),
				Tembakan.getText(),
				Durasi.getText(),
				KeluhanTindakan.getText(),
				Komplikasi.getText(),
				Evaluasi.getText(),
				Advice.getText()
				
				
               
            })==true){
                emptTeks();
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
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        Valid.pindah(evt,TCari,Kd,NamaTemplate);
    }//GEN-LAST:event_KdKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void NamaTemplateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaTemplateKeyPressed
        Valid.pindah(evt,Kd,Keluhan);
    }//GEN-LAST:event_NamaTemplateKeyPressed

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

    private void AdviceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AdviceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdviceKeyPressed

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

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        
    }//GEN-LAST:event_KeluhanKeyPressed

    private void RiwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeyPressed

    }//GEN-LAST:event_RiwayatPenyakitKeyPressed

    private void OperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OperasiKeyPressed

    }//GEN-LAST:event_OperasiKeyPressed

    private void RiwayatESWLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatESWLKeyPressed

    }//GEN-LAST:event_RiwayatESWLKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MasterTemplateESWL dialog = new MasterTemplateESWL(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.TextBox CTScan;
    private widget.TextBox Darah;
    private widget.TextBox Durasi;
    private widget.TextBox ESWLKe;
    private widget.TextBox Evaluasi;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Frekuensi;
    private widget.TextBox Guide;
    private widget.ComboBox Hidroneprosis;
    private widget.TextBox Kd;
    private widget.TextBox Keluhan;
    private widget.TextBox KeluhanTindakan;
    private widget.TextBox Komplikasi;
    private widget.Label LCount;
    private widget.ComboBox Lokasi;
    private widget.TextBox Nadi;
    private widget.TextBox NamaTemplate;
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
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tembakan;
    private widget.Label Tembakan1;
    private widget.TextBox USG;
    private widget.TextBox UkuranBatu;
    private widget.TextBox Urine;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel20;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label13;
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
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select template_hasil_tindakan_eswl.no_template,template_hasil_tindakan_eswl.nama_template,"+
                    "template_hasil_tindakan_eswl.keluhan,template_hasil_tindakan_eswl.riwayat_penyakit,template_hasil_tindakan_eswl.riwayat_operasi,template_hasil_tindakan_eswl.riwayat_eswl,"+
                        "template_hasil_tindakan_eswl.td,template_hasil_tindakan_eswl.nadi,template_hasil_tindakan_eswl.rr,template_hasil_tindakan_eswl.suhu,template_hasil_tindakan_eswl.status_urologi,"+
			"template_hasil_tindakan_eswl.penunjang_urine,template_hasil_tindakan_eswl.penunjang_darah,template_hasil_tindakan_eswl.penunjang_usg,template_hasil_tindakan_eswl.penunjang_bno,template_hasil_tindakan_eswl.penunjang_ctscan,"+
                        "template_hasil_tindakan_eswl.lokasi,template_hasil_tindakan_eswl.pole,template_hasil_tindakan_eswl.hidroneprosis,"+
                        "template_hasil_tindakan_eswl.tindakan_eswl,template_hasil_tindakan_eswl.tindakan_guide,template_hasil_tindakan_eswl.tindakan_ukuran,template_hasil_tindakan_eswl.tindakan_analgetik,template_hasil_tindakan_eswl.tindakan_power,template_hasil_tindakan_eswl.tindakan_frekuensi,template_hasil_tindakan_eswl.tindakan_tembakan,template_hasil_tindakan_eswl.tindakan_durasi,"+
                        "template_hasil_tindakan_eswl.tindakan_keluhan,template_hasil_tindakan_eswl.tindakan_komplikasi,template_hasil_tindakan_eswl.tindakan_evaluasi,"+
                    
					
					"template_hasil_tindakan_eswl.tindakan_advice from template_hasil_tindakan_eswl "+
                    (TCari.getText().equals("")?"":"where template_hasil_tindakan_eswl.no_template like ? or template_hasil_tindakan_eswl.nama_template like ? or "+
                    "template_hasil_tindakan_eswl.keluhan like ? ")+
                    "order by template_hasil_tindakan_eswl.no_template");
            try {
                if(!TCari.getText().trim().equals("")){
                    ps.setString(1,"%"+TCari.getText().trim()+"%");
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_template"),rs.getString("nama_template"),
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
                System.out.println(e);
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
        Kd.setText("");
        NamaTemplate.setText("");
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
        
        //Template.setText("");
        Valid.autoNomer("template_hasil_tindakan_eswl","R",4,Kd);
        TabRawat.setSelectedIndex(0);
        Kd.requestFocus();
    }

    private void getData() {
        if(tbDokter.getSelectedRow()!= -1){
            Kd.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString());
            NamaTemplate.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),1).toString());
            
            Keluhan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString());
            RiwayatPenyakit.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
            Operasi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            RiwayatESWL.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString());
            TD.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString());
            Nadi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString());
            RR.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),8).toString());
            Suhu.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString());
            StatusUrologi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString());
            Urine.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString());
            Darah.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),12).toString());
            USG.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),13).toString());
            BNO.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString());
            CTScan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),15).toString());
            Lokasi.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),16).toString());
            Pole.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),17).toString());
            Hidroneprosis.setSelectedItem(tbDokter.getValueAt(tbDokter.getSelectedRow(),18).toString());
            ESWLKe.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),19).toString());
            Guide.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),20).toString());
            UkuranBatu.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),21).toString());
            Analgetik.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),22).toString());
            Power.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),23).toString());
            Frekuensi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),24).toString());
            Tembakan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),25).toString());
            Durasi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),26).toString());
            KeluhanTindakan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),27).toString());
            Komplikasi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),28).toString());
            Evaluasi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),29).toString());
            Advice.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),30).toString());
            
            //Template.setText(tabMode.getValueAt(tbDokter.getSelectedRow(),6).toString());
        }
    }

    public JTable getTable(){
        return tbDokter;
    }
    
    
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }
}
