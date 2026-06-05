package simrskhanza;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import widget.Button;
import widget.InternalFrame;

/**
 * DlgInformasi - Dialog Keterangan Warna Baris
 * Support mode: "ralan" dan "ranap"
 *
 * Penggunaan:
 *   DlgInformasi dlg = new DlgInformasi(null, false, "ralan");
 *   dlg.setSize(500, 500);
 *   dlg.setLocationRelativeTo(internalFrame1);
 *   dlg.setVisible(true);
 */
public class DlgInformasi extends JDialog {

    private static final Logger logger =
            Logger.getLogger(DlgInformasi.class.getName());

    private final String tipePerawatan;

    // --- Komponen Utama ---
    private InternalFrame internalFrame1;
    private JPanel        ContainerPanel;
    private JPanel        jPanel1;
    private Button        BtnKeluar;

    // --- Panel Rawat Jalan ---
    private JPanel  PanelRalan;
    private JLabel  jLabel6;   // judul Ralan
    private JLabel  jLabel24;  // catatan Ralan (judul)
    private JLabel  jLabel23;  // catatan Ralan (isi)

    private JPanel  jPanel24;  private JLabel jLabel7;   // Sudah Periksa
    private JPanel  jPanel25;  private JLabel jLabel15;  // Batal Periksa
    private JPanel  jPanel26;  private JLabel jLabel16;  // Dirujuk/Meninggal/Pulang Paksa
    private JPanel  jPanel27;  private JLabel jLabel17;  // Dirawat
    private JPanel  jPanel28;  private JLabel jLabel18;  // Berkas diterima
    private JPanel  jPanel29;  private JLabel jLabel19;  // Kirim Klaim
    private JPanel  jPanel30;  private JLabel jLabel20;  // Sudah Bayar

    // --- Panel Rawat Inap ---
    private JPanel  PanelRanap;
    private JLabel  labelRanap; // judul Ranap
    private JLabel  jLabel13;   // catatan Ranap (judul)
    private JLabel  jLabel22;   // catatan Ranap (isi)

    private JPanel  jPanel18;  private JLabel jLabel14;  // Membaik
    private JPanel  jPanel19;  private JLabel jLabel8;   // Keadaan Khusus
    private JPanel  jPanel20;  private JLabel jLabel9;   // Meninggal
    private JPanel  jPanel21;  private JLabel jLabel10;  // Rawat Gabung
    private JPanel  jPanel22;  private JLabel jLabel11;  // Sudah Bayar
    private JPanel  jPanel23;  private JLabel jLabel12;  // Kirim Klaim

    // =========================================================
    //  Constructor
    // =========================================================

    public DlgInformasi(Frame parent, boolean modal, String tipePerawatan) {
        super(parent, modal);
        this.tipePerawatan = tipePerawatan;
        initComponents();

        if (tipePerawatan.equals("ranap")) {
            PanelRanap.setVisible(true);
            PanelRalan.setVisible(false);
        } else {
            // default: ralan
            PanelRanap.setVisible(false);
            PanelRalan.setVisible(true);
        }
    }

    // =========================================================
    //  initComponents  (struktur identik hasil decompile JD-GUI)
    // =========================================================

    private void initComponents() {

        // --- instansiasi semua komponen ---
        internalFrame1 = new InternalFrame();
        ContainerPanel = new JPanel();

        // Ralan
        PanelRalan  = new JPanel();
        jLabel6     = new JLabel();
        jPanel24    = new JPanel();   jLabel7   = new JLabel();
        jPanel25    = new JPanel();   jLabel15  = new JLabel();
        jPanel26    = new JPanel();   jLabel16  = new JLabel();
        jPanel27    = new JPanel();   jLabel17  = new JLabel();
        jPanel28    = new JPanel();   jLabel18  = new JLabel();
        jPanel29    = new JPanel();   jLabel19  = new JLabel();
        jPanel30    = new JPanel();   jLabel20  = new JLabel();
        jLabel23    = new JLabel();
        jLabel24    = new JLabel();

        // Ranap
        PanelRanap  = new JPanel();
        jPanel18    = new JPanel();   jLabel14  = new JLabel();
        jPanel19    = new JPanel();   jLabel8   = new JLabel();
        jPanel20    = new JPanel();   jLabel9   = new JLabel();
        jPanel21    = new JPanel();   jLabel10  = new JLabel();
        jPanel22    = new JPanel();   jLabel11  = new JLabel();
        jPanel23    = new JPanel();   jLabel12  = new JLabel();
        jLabel13    = new JLabel();
        labelRanap  = new JLabel();
        jLabel22    = new JLabel();

        // Tombol
        jPanel1     = new JPanel();
        BtnKeluar   = new Button();

        // --- Dialog ---
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("::[ Informasi ]::");

        // --- InternalFrame ---
        internalFrame1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(240, 245, 235)),
                "::[ Informasi ]::", 0, 0,
                new Font("Tahoma", Font.PLAIN, 11),
                new Color(50, 50, 50)));
        internalFrame1.setLayout(new BoxLayout(
                (Container) internalFrame1, BoxLayout.Y_AXIS));

        // --- ContainerPanel (OverlayLayout agar Ralan/Ranap overlap) ---
        ContainerPanel.setBackground(new Color(255, 255, 255));
        ContainerPanel.setLayout(new OverlayLayout(ContainerPanel));

        // =====================================================
        //  PANEL RAWAT JALAN
        // =====================================================
        PanelRalan.setBackground(new Color(255, 255, 255));

        // Judul
        jLabel6.setFont(new Font("Tahoma", Font.ITALIC, 11));
        jLabel6.setForeground(new Color(50, 50, 50));
        jLabel6.setText(".: Warna Baris Rawat Jalan");
        jLabel6.setPreferredSize(new Dimension(150, 16));

        // Baris warna
        setupColorPanel(jPanel24, new Color(200,   0,   0));
        jLabel7.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel7.setForeground(new Color(50, 50, 50));
        jLabel7.setText("Sudah Periksa");

        setupColorPanel(jPanel25, new Color(255, 243, 109));
        jLabel15.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel15.setForeground(new Color(50, 50, 50));
        jLabel15.setText("Batal Periksa");

        setupColorPanel(jPanel26, new Color(152, 152, 156));
        jLabel16.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel16.setForeground(new Color(50, 50, 50));
        jLabel16.setText("Dirujuk/Meninggal/Pulang Paksa");

        setupColorPanel(jPanel27, new Color(119, 221, 119));
        jLabel17.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel17.setForeground(new Color(50, 50, 50));
        jLabel17.setText("Dirawat");

        setupColorPanel(jPanel28, new Color(  0, 153,   0));
        jLabel18.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel18.setForeground(new Color(50, 50, 50));
        jLabel18.setText("Berkas diterima");

        setupColorPanel(jPanel29, new Color(106,  50, 159));
        jLabel19.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel19.setForeground(new Color(50, 50, 50));
        jLabel19.setText("Kirim Klaim");

        setupColorPanel(jPanel30, new Color(102, 204, 255));
        jLabel20.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel20.setForeground(new Color(50, 50, 50));
        jLabel20.setText("Sudah Bayar");

        // Catatan Ralan
        jLabel24.setFont(new Font("Tahoma", Font.ITALIC, 11));
        jLabel24.setForeground(new Color(50, 50, 50));
        jLabel24.setText(".: Catatan Rawat Jalan");
        jLabel24.setPreferredSize(new Dimension(150, 16));

        jLabel23.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel23.setForeground(new Color(50, 50, 50));
        jLabel23.setText(
                "[x] Jika Diagnosa Utama kosong, resume Ralan belum dibuat.");

        // --- GroupLayout PanelRalan ---
        GroupLayout ralanLayout = new GroupLayout(PanelRalan);
        PanelRalan.setLayout(ralanLayout);

        ralanLayout.setHorizontalGroup(ralanLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ralanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6,  -2, 150, -2)
                    .addComponent(jLabel24, -2, 150, -2)
                    .addComponent(jLabel23)
                    .addGroup(ralanLayout.createSequentialGroup()
                        .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel24, -1, -1, Short.MAX_VALUE)
                            .addComponent(jPanel25, -1, -1, Short.MAX_VALUE))
                        .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(ralanLayout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15, -2, 90, -2))
                            .addGroup(GroupLayout.Alignment.TRAILING, ralanLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel7, -2, 90, -2))))
                    .addGroup(ralanLayout.createSequentialGroup()
                        .addComponent(jPanel26, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16, -2, 180, -2))
                    .addGroup(ralanLayout.createSequentialGroup()
                        .addComponent(jPanel27, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17, -2, 90, -2))
                    .addGroup(ralanLayout.createSequentialGroup()
                        .addComponent(jPanel28, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18, -2, 90, -2))
                    .addGroup(ralanLayout.createSequentialGroup()
                        .addComponent(jPanel29, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19, -2, 90, -2))
                    .addGroup(ralanLayout.createSequentialGroup()
                        .addComponent(jPanel30, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20, -2, 90, -2)))
                .addContainerGap(195, Short.MAX_VALUE)));

        ralanLayout.setVerticalGroup(ralanLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ralanLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel6, -2, 20, -2)
                .addGap(18, 18, 18)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7,   -2, 20, -2)
                    .addComponent(jPanel24,  -2, -1, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel25,  -2, -1, -2)
                    .addComponent(jLabel15,  -2, 20, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel26,  -2, -1, -2)
                    .addComponent(jLabel16,  -2, 20, -2))
                .addGap(18, 18, 18)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel27,  -2, -1, -2)
                    .addComponent(jLabel17,  -2, 20, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel28,  -2, -1, -2)
                    .addComponent(jLabel18,  -2, 20, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel29,  -2, -1, -2)
                    .addComponent(jLabel19,  -2, 20, -2))
                .addGap(18, 18, 18)
                .addGroup(ralanLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel30,  -2, -1, -2)
                    .addComponent(jLabel20,  -2, 20, -2))
                .addGap(18, 18, 18)
                .addComponent(jLabel24, -2, 20, -2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, -2, 20, -2)
                .addContainerGap(-1, Short.MAX_VALUE)));

        ContainerPanel.add(PanelRalan);

        // =====================================================
        //  PANEL RAWAT INAP
        // =====================================================
        PanelRanap.setBackground(new Color(255, 255, 255));

        // Judul
        labelRanap.setFont(new Font("Tahoma", Font.ITALIC, 11));
        labelRanap.setForeground(new Color(50, 50, 50));
        labelRanap.setText(".: Warna Baris Rawat Inap");
        labelRanap.setPreferredSize(new Dimension(150, 16));

        // Baris warna
        setupColorPanel(jPanel18, new Color(240, 171,  59));
        jLabel14.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel14.setForeground(new Color(50, 50, 50));
        jLabel14.setText("Membaik");

        setupColorPanel(jPanel19, new Color(255, 243, 109));
        jLabel8.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel8.setForeground(new Color(50, 50, 50));
        jLabel8.setText("Keadaan Khusus");

        setupColorPanel(jPanel20, new Color( 50,  50,  50));
        jLabel9.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel9.setForeground(new Color(50, 50, 50));
        jLabel9.setText("Meninggal");

        setupColorPanel(jPanel21, new Color(255, 102, 204));
        jLabel10.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel10.setForeground(new Color(50, 50, 50));
        jLabel10.setText("Rawat Gabung");

        setupColorPanel(jPanel22, new Color(102, 204, 255));
        jLabel11.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel11.setForeground(new Color(50, 50, 50));
        jLabel11.setText("Sudah Bayar");

        setupColorPanel(jPanel23, new Color(106,  50, 159));
        jLabel12.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel12.setForeground(new Color(50, 50, 50));
        jLabel12.setText("Kirim Klaim");

        // Catatan Ranap
        jLabel13.setFont(new Font("Tahoma", Font.ITALIC, 11));
        jLabel13.setForeground(new Color(50, 50, 50));
        jLabel13.setText(".: Catatan Rawat Inap");
        jLabel13.setPreferredSize(new Dimension(150, 16));

        jLabel22.setFont(new Font("Tahoma", Font.PLAIN, 11));
        jLabel22.setForeground(new Color(50, 50, 50));
        jLabel22.setText(
                "[x] Jika Keadaan Keluar = \u201cBelum\u201d. Resume Ranap belum dibuat.");

        // --- GroupLayout PanelRanap ---
        GroupLayout ranapLayout = new GroupLayout(PanelRanap);
        PanelRanap.setLayout(ranapLayout);

        ranapLayout.setHorizontalGroup(ranapLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ranapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(labelRanap, -2, 150, -2)
                    .addComponent(jLabel13,   -2, 150, -2)
                    .addComponent(jLabel22)
                    .addGroup(ranapLayout.createSequentialGroup()
                        .addComponent(jPanel18, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, -2, 90, -2))
                    .addGroup(ranapLayout.createSequentialGroup()
                        .addComponent(jPanel19, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8,  -2, 90, -2))
                    .addGroup(ranapLayout.createSequentialGroup()
                        .addComponent(jPanel20, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9,  -2, 90, -2))
                    .addGroup(ranapLayout.createSequentialGroup()
                        .addComponent(jPanel21, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, -2, 90, -2))
                    .addGroup(ranapLayout.createSequentialGroup()
                        .addComponent(jPanel22, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, -2, 90, -2))
                    .addGroup(ranapLayout.createSequentialGroup()
                        .addComponent(jPanel23, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, -2, 90, -2)))
                .addContainerGap(182, Short.MAX_VALUE)));

        ranapLayout.setVerticalGroup(ranapLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ranapLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(labelRanap, -2, 20, -2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, -2, -1, -2)
                    .addComponent(jLabel14, -2, 20, -2))
                .addGap(12, 12, 12)
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, -2, -1, -2)
                    .addComponent(jLabel8,  -2, 20, -2))
                .addGap(12, 12, 12)
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel20, -2, -1, -2)
                    .addComponent(jLabel9,  -2, 20, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel21, -2, -1, -2)
                    .addComponent(jLabel10, -2, 20, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel22, -2, -1, -2)
                    .addComponent(jLabel11, -2, 20, -2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ranapLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel23, -2, -1, -2)
                    .addComponent(jLabel12, -2, 20, -2))
                .addGap(26, 26, 26)
                .addComponent(jLabel13, -2, 20, -2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, -2, 20, -2)
                .addGap(0, 40, Short.MAX_VALUE)));

        ContainerPanel.add(PanelRanap);

        // --- Tambahkan ContainerPanel ke InternalFrame ---
        internalFrame1.add(ContainerPanel);

        // =====================================================
        //  PANEL TOMBOL
        // =====================================================
        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 16));

        BtnKeluar.setIcon(new ImageIcon(
                getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setGlassColor(new Color(204, 102, 0));
        BtnKeluar.setPreferredSize(new Dimension(100, 30));
        BtnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });

        jPanel1.add((Component) BtnKeluar);
        internalFrame1.add(jPanel1);

        // --- Tambahkan InternalFrame ke Dialog ---
        getContentPane().add((Component) internalFrame1, "Center");

        pack();
    }

    // =========================================================
    //  Helper: setup panel warna kotak (GroupLayout minimal)
    // =========================================================
    private void setupColorPanel(JPanel panel, Color bg) {
        panel.setBackground(bg);
        panel.setPreferredSize(new Dimension(20, 20));
        GroupLayout gl = new GroupLayout(panel);
        panel.setLayout(gl);
        gl.setHorizontalGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 20, Short.MAX_VALUE));
        gl.setVerticalGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 20, Short.MAX_VALUE));
    }

    // =========================================================
    //  Action
    // =========================================================
    private void BtnKeluarActionPerformed(ActionEvent evt) {
        dispose();
    }

    // =========================================================
    //  main (untuk test standalone)
    // =========================================================
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info :
                    UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> {
            // Ganti "ranap" dengan "ralan" untuk test mode Rawat Jalan
            DlgInformasi dialog =
                    new DlgInformasi(new JFrame(), true, "ranap");
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
}
