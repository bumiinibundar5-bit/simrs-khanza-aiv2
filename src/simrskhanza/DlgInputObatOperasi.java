package simrskhanza;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgCariSuku;
import keuangan.DlgJnsPerawatanOperasi;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import rekammedis.MasterCariTemplateLaporanOperasi;

public class DlgInputObatOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,pstindakan2,pstindakan3,pstindakan4,psobat,psset_tarif,psrekening;
    private ResultSet rs,rsset_tarif,rsrekening;
    private DlgCariPetugas petugas=new DlgCariPetugas( null,false);
    private DlgCariSuku operasi=new DlgCariSuku ( null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private MasterCariTemplateLaporanOperasi template=new MasterCariTemplateLaporanOperasi(null,false);
    private String kelas_operasi="Yes",kelas="",cara_bayar_operasi="Yes",kd_pj="",status="";
    private double ttljmdokter=0,ttljmpetugas=0,ttlpendapatan=0,ttlbhp=0;
    private String Suspen_Piutang_Operasi_Ranap="",Operasi_Ranap="",Beban_Jasa_Medik_Dokter_Operasi_Ranap="",Utang_Jasa_Medik_Dokter_Operasi_Ranap="",
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap="",Utang_Jasa_Medik_Paramedis_Operasi_Ranap="",HPP_Obat_Operasi_Ranap="",Persediaan_Obat_Kamar_Operasi_Ranap="",
            Suspen_Piutang_Operasi_Ralan="",Operasi_Ralan="",Beban_Jasa_Medik_Dokter_Operasi_Ralan="",Utang_Jasa_Medik_Dokter_Operasi_Ralan="",
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan="",Utang_Jasa_Medik_Paramedis_Operasi_Ralan="",HPP_Obat_Operasi_Ralan="",Persediaan_Obat_Kamar_Operasi_Ralan="",
            norawatibu="";
    private double y=0,biayatindakan=0,biayaobat=0;
    private int jml=0,pilihan=0,i=0,index=0;
    private boolean[] pilih; 
    private boolean sukses=true;
    private String[] kode_paket, nm_perawatan,kategori,kd_obat,nm_obat, satuan;
    private double[] operator1, operator2, operator3, asisten_operator1, asisten_operator2,asisten_operator3,dokter_pjanak,dokter_umum,
                  instrumen, dokter_anak, perawaat_resusitas, dokter_anestesi, asisten_anestesi,asisten_anestesi2, bidan,bidan2,bidan3, 
                  perawat_luar, sewa_ok, alat,akomodasi,bagian_rs,omloop,omloop2,omloop3,omloop4,omloop5,sarpras,ttltindakan,jmlobat,hargasatuan,ttlobat;


    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal*/
    public DlgInputObatOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"P","Kode Paket","Nama Operasi","Kategori","Operator 1","Operator 2","Operator 3",
                      "Asisten Op 1","Asisten Op 2","Asisten Op 3","Instrumen","dr Anak","Perawat Resus","dr Anastesi",
                      "Asisten Anast 1","Asisten Anast 2","Bidan 1","Bidan 2","Bidan 3","Perawat Luar","Alat","Sewa OK/VK",
                      "Akomodasi","N.M.S.","Onloop 1","Onloop 2","Onloop 3","Onloop 4","Onloop 5",
                      "Sarpras","dr Pj Anak","dr Umum","Total"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==3)||(colIndex==29)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                 java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
       

        
        
        //tagihan obat
        Object[] row2={"Jumlah",
        "Kode",
        "Nama",
        "Satuan",
        "Harga",
        "Total"};
        
        tabMode2=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObat.setModel(tabMode2);
        //tampil();

        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(400);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }
        }

        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    if(pilihan==1){
                        kdoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator1.requestFocus();
                    }else if(pilihan==2){
                        kdoperator2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmoperator2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdoperator2.requestFocus();
                   
                    }                   
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
        
        operasi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(operasi.getTable().getSelectedRow()!= -1){                    
                    if(pilihan==1){
                        kdoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator1.requestFocus();
                    }else if(pilihan==2){
                        kdoperator2.setText(operasi.getTable().getValueAt(operasi.getTable().getSelectedRow(),0).toString());
                        nmoperator2.setText(operasi.getTable().getValueAt(operasi.getTable().getSelectedRow(),1).toString());
                        kdoperator2.requestFocus();
                   
                    }                   
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
        
        TCari.requestFocus();
        ChkInput.setSelected(false);
        isForm();
        
        try {
            psrekening=koneksi.prepareStatement(
                "select set_akun_ralan.Suspen_Piutang_Operasi_Ralan,set_akun_ralan.Operasi_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Operasi_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Dokter_Operasi_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Operasi_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Operasi_Ralan,set_akun_ralan.HPP_Obat_Operasi_Ralan,"+
                "set_akun_ralan.Persediaan_Obat_Kamar_Operasi_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Operasi_Ralan=rsrekening.getString("Suspen_Piutang_Operasi_Ralan");
                    Operasi_Ralan=rsrekening.getString("Operasi_Ralan");
                    Beban_Jasa_Medik_Dokter_Operasi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");
                    Utang_Jasa_Medik_Dokter_Operasi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ralan");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ralan");
                    HPP_Obat_Operasi_Ralan=rsrekening.getString("HPP_Obat_Operasi_Ralan");
                    Persediaan_Obat_Kamar_Operasi_Ralan=rsrekening.getString("Persediaan_Obat_Kamar_Operasi_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }  
            
            psrekening=koneksi.prepareStatement(
               "select set_akun_ranap.Suspen_Piutang_Operasi_Ranap,set_akun_ranap.Operasi_Ranap,set_akun_ranap.Beban_Jasa_Medik_Dokter_Operasi_Ranap,"+
               "set_akun_ranap.Utang_Jasa_Medik_Dokter_Operasi_Ranap,set_akun_ranap.Beban_Jasa_Medik_Paramedis_Operasi_Ranap,"+
               "set_akun_ranap.Utang_Jasa_Medik_Paramedis_Operasi_Ranap,set_akun_ranap.HPP_Obat_Operasi_Ranap from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Operasi_Ranap=rsrekening.getString("Suspen_Piutang_Operasi_Ranap");
                    Operasi_Ranap=rsrekening.getString("Operasi_Ranap");
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                    HPP_Obat_Operasi_Ranap=rsrekening.getString("HPP_Obat_Operasi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }   
            
            psrekening=koneksi.prepareStatement("select set_akun_ranap2.Persediaan_Obat_Kamar_Operasi_Ranap from set_akun_ranap2");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Persediaan_Obat_Kamar_Operasi_Ranap=rsrekening.getString("Persediaan_Obat_Kamar_Operasi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } 
        
        try {
            psset_tarif=koneksi.prepareStatement("select set_tarif.cara_bayar_operasi,set_tarif.kelas_operasi from set_tarif");
            try {
                rsset_tarif=psset_tarif.executeQuery();
                if(rsset_tarif.next()){
                    cara_bayar_operasi=rsset_tarif.getString("cara_bayar_operasi");
                    kelas_operasi=rsset_tarif.getString("kelas_operasi");
                }else{
                    cara_bayar_operasi="Yes";
                    kelas_operasi="Yes";
                }  
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsset_tarif != null){
                    rsset_tarif.close();
                }
                if(psset_tarif != null){
                    psset_tarif.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
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

        Kd2 = new widget.TextBox();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        LTotal = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.panelisi();
        label14 = new widget.Label();
        kdoperator1 = new widget.TextBox();
        nmoperator1 = new widget.TextBox();
        BtnOperator1 = new widget.Button();
        label11 = new widget.Label();
        tgl = new widget.Tanggal();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        label19 = new widget.Label();
        kdoperator2 = new widget.TextBox();
        nmoperator2 = new widget.TextBox();
        BtnOperator2 = new widget.Button();
        nmoperator3 = new widget.TextBox();
        ChkInput = new widget.CekBox();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tagihan Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), " Obat & BHP ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 102));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setBorder(null);
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(215, 23));
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi4.add(BtnCari1);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelisi4.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi4.add(BtnTambah);

        jPanel2.add(panelisi4, java.awt.BorderLayout.PAGE_END);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        jPanel2.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LTotal.setText("Total Biaya : 0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(500, 30));
        panelisi1.add(LTotal);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelisi1.add(BtnCari);

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
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 126));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(89, 200));
        FormInput.setLayout(null);

        label14.setText("Operator 1 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 81, 23);

        kdoperator1.setEditable(false);
        kdoperator1.setName("kdoperator1"); // NOI18N
        kdoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator1);
        kdoperator1.setBounds(84, 40, 100, 23);

        nmoperator1.setEditable(false);
        nmoperator1.setName("nmoperator1"); // NOI18N
        nmoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator1);
        nmoperator1.setBounds(190, 40, 290, 23);

        BtnOperator1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator1.setMnemonic('2');
        BtnOperator1.setToolTipText("Alt+2");
        BtnOperator1.setName("BtnOperator1"); // NOI18N
        BtnOperator1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator1ActionPerformed(evt);
            }
        });
        BtnOperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOperator1KeyPressed(evt);
            }
        });
        FormInput.add(BtnOperator1);
        BtnOperator1.setBounds(480, 40, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(410, 40, 150, 23);

        tgl.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        tgl.setName("tgl"); // NOI18N
        tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglKeyPressed(evt);
            }
        });
        FormInput.add(tgl);
        tgl.setBounds(570, 40, 140, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 81, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 180, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(265, 10, 450, 23);

        label19.setText("Data Operasi :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(0, 70, 81, 23);

        kdoperator2.setEditable(false);
        kdoperator2.setName("kdoperator2"); // NOI18N
        kdoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator2);
        kdoperator2.setBounds(84, 70, 100, 23);

        nmoperator2.setEditable(false);
        nmoperator2.setName("nmoperator2"); // NOI18N
        nmoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator2);
        nmoperator2.setBounds(490, 70, 200, 23);

        BtnOperator2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator2.setMnemonic('2');
        BtnOperator2.setToolTipText("Alt+2");
        BtnOperator2.setName("BtnOperator2"); // NOI18N
        BtnOperator2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator2ActionPerformed(evt);
            }
        });
        BtnOperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOperator2KeyPressed(evt);
            }
        });
        FormInput.add(BtnOperator2);
        BtnOperator2.setBounds(690, 70, 28, 23);

        nmoperator3.setEditable(false);
        nmoperator3.setName("nmoperator3"); // NOI18N
        nmoperator3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator3);
        nmoperator3.setBounds(190, 70, 290, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.PAGE_START);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariTagihanOperasi form=new DlgCariTagihanOperasi(null,false);
        //form.emptTeks();      
        form.setPasien(TNoRw.getText());
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void kdoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nmoperator1.setText(dokter.tampil3(kdoperator1.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnOperator1ActionPerformed(null);
        }else{
            Valid.pindah(evt,tgl,kdoperator2);
        }
}//GEN-LAST:event_kdoperator1KeyPressed

private void BtnOperator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator1ActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnOperator1ActionPerformed

private void tglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglKeyPressed
       
}//GEN-LAST:event_tglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tabMode.getRowCount();
            for(int r=0;r<row2;r++){ 
                tabMode.setValueAt("",r,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select concat(pasien.no_rkm_medis,', ',pasien.nm_pasien) from reg_periksa inner join pasien "+
                        " on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
        }else{            
            Valid.pindah(evt,TCari,kdoperator1);
        }
}//GEN-LAST:event_TNoRwKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil2();
}//GEN-LAST:event_BtnAllActionPerformed

private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatOperasi produsen=new DlgObatOperasi(null,false);
        produsen.emptTeks();   
        produsen.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        produsen.setLocationRelativeTo(internalFrame1);
        produsen.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());         
}//GEN-LAST:event_BtnTambahActionPerformed

private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
        LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
}//GEN-LAST:event_tbObatMouseClicked

private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    getData();
                    int row=tbObat.getSelectedColumn();
                    if(row==1){
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbObat.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                }
            }            
        }
        LTotal.setText("Total Biaya : "+Valid.SetAngka(biayaobat+biayatindakan));
}//GEN-LAST:event_tbObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
   isForm();  
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
       
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdoperator2.getText().trim().equals("")||nmoperator2.getText().trim().equals("")){
            kdoperator2.setText("-");
            nmoperator2.setText("-");
        }

        

        jml=0;
        
        
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
  
        }else if(kdoperator1.getText().trim().equals("")||nmoperator1.getText().trim().equals("")){
            Valid.textKosong(kdoperator1,"Operator 1");
        

        }else{            
            if(Sequel.cariRegistrasi(TNoRw.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                
            }else{
                Sequel.AutoComitFalse();
                sukses=true;
                ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;
                
                
                if(sukses==true){
                    for(int r=0;r<tbObat.getRowCount();r++){
                        if(Valid.SetAngka(tbObat.getValueAt(r,0).toString())>0){
                            if(Sequel.menyimpantf2("beri_obat_operasi","'"+TNoRw.getText()+"','"+Valid.SetTgl(tgl.getSelectedItem()+"")+" "+tgl.getSelectedItem().toString().substring(11,19)+
                                "','"+tbObat.getValueAt(r,1).toString()+"','"+tbObat.getValueAt(r,4).toString()+
                                "','"+tbObat.getValueAt(r,0).toString()+"'","data")==true){
                                ttlbhp=ttlbhp+Double.parseDouble(tbObat.getValueAt(r,5).toString());
                            }else{
                                sukses=false;
                            }
                        }
                    }
                    ttlpendapatan=ttlpendapatan+ttlbhp;
                }
                    
                
                if(sukses==true){
                    if(status.equals("Ranap")){
                        Sequel.queryu("delete from tampjurnal");    
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Operasi_Ranap+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Operasi_Ranap+"'");                                
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban Jasa Medik Dokter Operasi Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"'");  
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'");                              
                        }
                        if(ttljmpetugas>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban Jasa Medik Petugas Operasi Ranap','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang Jasa Medik Petugas Operasi Ranap','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"'");                                 
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_Obat_Operasi_Ranap+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_Obat_Kamar_Operasi_Ranap+"'");                                
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","OPERASI RAWAT INAP PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode());                                              
                    }else if(status.equals("Ralan")){
                        Sequel.queryu("delete from tampjurnal");    
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ralan+"','Suspen Piutang Operasi Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Operasi_Ralan+"'");    
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ralan+"','Pendapatan Operasi Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Operasi_Ralan+"'");                                
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"','Beban Jasa Medik Dokter Operasi Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Operasi_Ralan+"'");  
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang Jasa Medik Dokter Operasi Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'");                              
                        }
                        if(ttljmpetugas>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"','Beban Jasa Medik Petugas Operasi Ralan','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Operasi_Ralan+"'");   
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"','Utang Jasa Medik Petugas Operasi Ralan','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Operasi_Ralan+"'");                                 
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ralan+"','HPP Persediaan Operasi Rawat Jalan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_Obat_Operasi_Ralan+"'");     
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ralan+"','Persediaan BHP Operasi Rawat Jalan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_Obat_Kamar_Operasi_Ralan+"'");                                
                        }
                        sukses=jur.simpanJurnal(TNoRw.getText(),"U","OPERASI RAWAT JALAN PASIEN "+TPasien.getText()+" DIPOSTING OLEH "+akses.getkode());                                              
                    }
                }
                    
                if(sukses==true){
                    Sequel.Commit();
                    
                 
                    for(int r=0;r<tbObat.getRowCount();r++){
                        tbObat.setValueAt("",r,0);
                    }
                    tampil2();
                    LTotal.setText("Total Biaya : 0");
                  
                    JOptionPane.showMessageDialog(rootPane,"Proses simpan selesai...!");
                }else{
                    JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }
                Sequel.AutoComitTrue();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed
  
    private void BtnOperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOperator1KeyPressed
        
    }//GEN-LAST:event_BtnOperator1KeyPressed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void BtnOperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOperator2KeyPressed
       
    }//GEN-LAST:event_BtnOperator2KeyPressed

    private void BtnOperator2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator2ActionPerformed
        pilihan=2;
        operasi.emptTeks();
        operasi.isCek();
        operasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        operasi.setLocationRelativeTo(internalFrame1);
        operasi.setVisible(true);
    }//GEN-LAST:event_BtnOperator2ActionPerformed

    private void kdoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator2KeyPressed
        
    }//GEN-LAST:event_kdoperator2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTagihanOperasi dialog = new DlgTagihanOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnOperator1;
    private widget.Button BtnOperator2;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox kdoperator1;
    private widget.TextBox kdoperator2;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label19;
    private widget.Label label9;
    private widget.TextBox nmoperator1;
    private widget.TextBox nmoperator2;
    private widget.TextBox nmoperator3;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.Table tbObat;
    private widget.Tanggal tgl;
    // End of variables declaration//GEN-END:variables

    //obat
    private void tampil2() {
        jml=0;
        for(i=0;i<tbObat.getRowCount();i++){
            //System.out.println(tbObat.getValueAt(i,0).toString());
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        
        jmlobat=new double[jml];
        kd_obat=new String[jml];
        nm_obat=new String[jml];
        satuan=new String[jml];
        hargasatuan=new double[jml];
        ttlobat=new double[jml];
        
        index=0;        
        for(i=0;i<tabMode2.getRowCount();i++){
            if(!tabMode2.getValueAt(i,0).toString().equals("")){
                jmlobat[index]=Double.parseDouble(tabMode2.getValueAt(i,0).toString());
                kd_obat[index]=tabMode2.getValueAt(i,1).toString();
                nm_obat[index]=tabMode2.getValueAt(i,2).toString();
                satuan[index]=tabMode2.getValueAt(i,3).toString();
                hargasatuan[index]=Double.parseDouble(tabMode2.getValueAt(i,4).toString());
                ttlobat[index]=Double.parseDouble(tabMode2.getValueAt(i,5).toString());
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode2);
        
        for(i=0;i<jml;i++){
            tabMode2.addRow(new Object[]{jmlobat[i],kd_obat[i],nm_obat[i],satuan[i],hargasatuan[i],ttlobat[i]});
        }
        
        try{    
            psobat=koneksi.prepareStatement("select obatbhp_ok.kd_obat, obatbhp_ok.nm_obat, kodesatuan.satuan, "+
                       "obatbhp_ok.hargasatuan from obatbhp_ok inner join kodesatuan "+
                       "on obatbhp_ok.kode_sat=kodesatuan.kode_sat "+
                       "where obatbhp_ok.kd_obat like ? or "+
                       "obatbhp_ok.nm_obat like ? or "+
                       "kodesatuan.satuan like ? "+
                       "order by obatbhp_ok.kd_obat");
            try{
                psobat.setString(1,"%"+TCari.getText()+"%");
                psobat.setString(2,"%"+TCari.getText()+"%");
                psobat.setString(3,"%"+TCari.getText()+"%");
                rs=psobat.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new Object[]{"",rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),0});
                }
            }catch(SQLException e){
                System.out.println(e);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(psobat!=null){
                    psobat.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }
   
    private void getData(){
       int row=tbObat.getSelectedRow();
        if(row!= -1){         
           int kolom=tbObat.getSelectedColumn();  
           if((kolom==0)||(kolom==1)){    
               if(!tbObat.getValueAt(row,0).toString().equals("")){
                   try {
                       tbObat.setValueAt(Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(row,0).toString())*Double.parseDouble(tbObat.getValueAt(row,4).toString())), row,5);                    
                   } catch (Exception e) {
                       tbObat.setValueAt(0, row,5);                    
                   }
                }else if(tbObat.getValueAt(row,0).toString().equals("")){
                    tbObat.setValueAt(0, row,5);   
                }                 
            }              
            
            biayaobat=0;
            y=0;
            int row2=tbObat.getRowCount();
            for(int r=0;r<row2;r++){ 
                if(!tbObat.getValueAt(r,5).toString().isEmpty()){
                    try {
                        y=Double.parseDouble(tbObat.getValueAt(r,5).toString()); 
                    } catch (Exception e) {
                        y=0;
                    }                                   
                }else if(tbObat.getValueAt(r,5).toString().isEmpty()){
                    y=0;                
                }
                biayaobat=biayaobat+y;
            }
        }
    }
    
    public void isCek(){
       // Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(nota_jual,6),signed)),0) from penjualan ","PJ",6,NoNota); 
        TCari.requestFocus();
        if(akses.getjml2()>=1){
            BtnSimpan.setEnabled(akses.getoperasi());
            BtnTambah.setEnabled(akses.getoperasi());
        }        
    }
    
    public void setNoRm(String norm,String nama,String posisi){
        TNoRw.setText(norm);
        TPasien.setText(nama);
        this.status=posisi;
        this.kd_pj=Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",norm);        
        if(status.equals("Ranap")){
            norawatibu=Sequel.cariIsi("select ranap_gabung.no_rawat from ranap_gabung where ranap_gabung.no_rawat2=?",TNoRw.getText());
        
            if(!norawatibu.equals("")){
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norawatibu);
            }else{
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            } 
        }else if(status.equals("Ralan")){
            kelas="Rawat Jalan";
        }
       
        tampil2();
    }
    
    public void setNoRm(String norm,String nama,String posisi,String KodeOperator,String NamaOperator){
        TNoRw.setText(norm);
        TPasien.setText(nama);
        this.status=posisi;
        this.kd_pj=Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",norm);        
        if(status.equals("Ranap")){
            norawatibu=Sequel.cariIsi("select ranap_gabung.no_rawat from ranap_gabung where ranap_gabung.no_rawat2=?",TNoRw.getText());
        
            if(!norawatibu.equals("")){
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norawatibu);
            }else{
                kelas=Sequel.cariIsi(
                    "select kamar.kelas from kamar inner join kamar_inap "+
                    "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "+
                    "and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            } 
        }else if(status.equals("Ralan")){
            kelas="Rawat Jalan";
        }
       
        tampil2();
        kdoperator1.setText(KodeOperator);
        nmoperator1.setText(NamaOperator);
    }
    
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,126));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
   
 
}
