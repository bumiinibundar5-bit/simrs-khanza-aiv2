/*
 * Form Grafik Partograf WHO - Terpisah dari Form Utama
 * Menampilkan grafik sesuai standar WHO dengan data real dari database
 */
package rekammedis;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;

// Import Java Swing yang diperlukan
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

// Import yang DIPERLUKAN untuk fix error
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

// Import database dan utility
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Import existing lainnya
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 * Form untuk menampilkan grafik partograf WHO
 *
 * @author sistem
 */
public class GrafikPartografWHO extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;

    private String noRawat = "";
    private String namaPasien = "";
    private String umurPasien = "";
    private String petugasPenolong = "";

    // Data untuk grafik
    private List<DataPoint> cervixData = new ArrayList<>();
    private List<DataPoint> descentData = new ArrayList<>();
    private List<DataPoint> djjData = new ArrayList<>();
    private List<DataPoint> systolicData = new ArrayList<>();
    private List<DataPoint> diastolicData = new ArrayList<>();
    private List<DataPoint> pulseData = new ArrayList<>();

    // Inner class untuk data point
    private static class DataPoint {

        public long time;
        public double value;
        public String type;

        public DataPoint(long time, double value) {
            this.time = time;
            this.value = value;
            this.type = "";
        }

        public DataPoint(long time, double value, String type) {
            this.time = time;
            this.value = value;
            this.type = type;
        }
    }

    /**
     * Creates new form GrafikPartografWHO
     */
    public GrafikPartografWHO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        setupEventHandlers();
        setupKeyboardShortcuts();
        setupAutoRefresh();
    }

    // Constructor dengan parameter
    public GrafikPartografWHO(java.awt.Frame parent, boolean modal, String noRawat,
            String namaPasien, String umurPasien, String petugasPenolong) {
        this(parent, modal);
        this.noRawat = noRawat;
        this.namaPasien = namaPasien;
        this.umurPasien = umurPasien;
        this.petugasPenolong = petugasPenolong;

        loadDataAndUpdateCharts();
        updatePatientInfo();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jPopupMenuGrafik = new javax.swing.JPopupMenu();
        MenuRefresh = new javax.swing.JMenuItem();
        MenuExportImage = new javax.swing.JMenuItem();
        MenuExportCSV = new javax.swing.JMenuItem();
        MenuPrint = new javax.swing.JMenuItem();
        MenuSummary = new javax.swing.JMenuItem();

        internalFrame1 = new widget.InternalFrame();
        panelHeader = new widget.panelisi();
        jLabel1 = new widget.Label();
        lblInfoPasien = new widget.Label();

        // Tab Panel untuk grafik
        TabGrafik = new javax.swing.JTabbedPane();

        // Panel Grafik Cervix
        panelCervix = new javax.swing.JPanel();
        canvasCervix = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintCervixChart(g);
            }
        };

        // Panel Grafik DJJ
        panelDJJ = new javax.swing.JPanel();
        canvasDJJ = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintDJJChart(g);
            }
        };

        // Panel Grafik Vital Signs
        panelVital = new javax.swing.JPanel();
        canvasVital = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintVitalChart(g);
            }
        };

        // Panel Gabungan (Semua Grafik)
        panelGabungan = new javax.swing.JPanel();
        canvasGabungan = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintCombinedChart(g);
            }
        };

        // Panel Kontrol
        panelKontrol = new widget.panelisi();
        BtnRefresh = new widget.Button();
        BtnExportImage = new widget.Button();
        BtnExportCSV = new widget.Button();
        BtnPrint = new widget.Button();
        BtnSummary = new widget.Button();
        BtnKeluar = new widget.Button();

        // Setup Popup Menu
        jPopupMenuGrafik.setName("jPopupMenuGrafik");

        MenuRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        MenuRefresh.setText("Refresh Data");
        MenuRefresh.addActionListener(evt -> refreshData());
        jPopupMenuGrafik.add(MenuRefresh);

        MenuExportImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        MenuExportImage.setText("Export sebagai Gambar");
        MenuExportImage.addActionListener(evt -> exportAsImage());
        jPopupMenuGrafik.add(MenuExportImage);

        MenuExportCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        MenuExportCSV.setText("Export ke CSV");
        MenuExportCSV.addActionListener(evt -> exportToCSV());
        jPopupMenuGrafik.add(MenuExportCSV);

        jPopupMenuGrafik.addSeparator();

        MenuPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        MenuPrint.setText("Print Grafik");
        MenuPrint.addActionListener(evt -> printChart());
        jPopupMenuGrafik.add(MenuPrint);

        MenuSummary.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        MenuSummary.setText("Ringkasan Statistik");
        MenuSummary.addActionListener(evt -> showSummary());
        jPopupMenuGrafik.add(MenuSummary);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Grafik Partograf WHO");
        setResizable(true);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(240, 245, 235)),
                "::[ Grafik Partograf WHO - Monitoring Persalinan ]::",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Tahoma", 0, 11),
                new Color(50, 50, 50)));
        internalFrame1.setName("internalFrame1");
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        // Header Panel
        panelHeader.setName("panelHeader");
        panelHeader.setPreferredSize(new Dimension(0, 80));
        panelHeader.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new Font("Tahoma", 1, 16));
        jLabel1.setForeground(new Color(0, 100, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GRAFIK PARTOGRAF WHO - MONITORING PERSALINAN LENGKAP");
        jLabel1.setName("jLabel1");
        panelHeader.add(jLabel1, java.awt.BorderLayout.NORTH);

        lblInfoPasien.setFont(new Font("Tahoma", 0, 12));
        lblInfoPasien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoPasien.setText("Nama: - | No.Rawat: - | Umur: - | Penolong: -");
        lblInfoPasien.setName("lblInfoPasien");
        panelHeader.add(lblInfoPasien, java.awt.BorderLayout.CENTER);

        internalFrame1.add(panelHeader, java.awt.BorderLayout.NORTH);

        // Setup Tab Grafik
        TabGrafik.setName("TabGrafik");

        // Panel Cervix
        panelCervix.setName("panelCervix");
        panelCervix.setLayout(new java.awt.BorderLayout());

        canvasCervix.setBackground(Color.WHITE);
        canvasCervix.setBorder(javax.swing.BorderFactory.createTitledBorder("Pembukaan Serviks & Penurunan Kepala"));
        canvasCervix.setName("canvasCervix");
        canvasCervix.setPreferredSize(new Dimension(800, 400));
        canvasCervix.setComponentPopupMenu(jPopupMenuGrafik);
        panelCervix.add(canvasCervix, java.awt.BorderLayout.CENTER);

        TabGrafik.addTab("Pembukaan & Penurunan", panelCervix);

        // Panel DJJ
        panelDJJ.setName("panelDJJ");
        panelDJJ.setLayout(new java.awt.BorderLayout());

        canvasDJJ.setBackground(Color.WHITE);
        canvasDJJ.setBorder(javax.swing.BorderFactory.createTitledBorder("Denyut Jantung Janin (DJJ)"));
        canvasDJJ.setName("canvasDJJ");
        canvasDJJ.setPreferredSize(new Dimension(800, 400));
        canvasDJJ.setComponentPopupMenu(jPopupMenuGrafik);
        panelDJJ.add(canvasDJJ, java.awt.BorderLayout.CENTER);

        TabGrafik.addTab("DJJ (Denyut Jantung Janin)", panelDJJ);

        // Panel Vital Signs
        panelVital.setName("panelVital");
        panelVital.setLayout(new java.awt.BorderLayout());

        canvasVital.setBackground(Color.WHITE);
        canvasVital.setBorder(javax.swing.BorderFactory.createTitledBorder("Tekanan Darah & Nadi Maternal"));
        canvasVital.setName("canvasVital");
        canvasVital.setPreferredSize(new Dimension(800, 400));
        canvasVital.setComponentPopupMenu(jPopupMenuGrafik);
        panelVital.add(canvasVital, java.awt.BorderLayout.CENTER);

        TabGrafik.addTab("Vital Signs Maternal", panelVital);

        // Panel Gabungan
        panelGabungan.setName("panelGabungan");
        panelGabungan.setLayout(new java.awt.BorderLayout());

        canvasGabungan.setBackground(Color.WHITE);
        canvasGabungan.setBorder(javax.swing.BorderFactory.createTitledBorder("Semua Grafik Partograf WHO"));
        canvasGabungan.setName("canvasGabungan");
        canvasGabungan.setPreferredSize(new Dimension(800, 600));
        canvasGabungan.setComponentPopupMenu(jPopupMenuGrafik);

        JScrollPane scrollGabungan = new JScrollPane(canvasGabungan);
        scrollGabungan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollGabungan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelGabungan.add(scrollGabungan, java.awt.BorderLayout.CENTER);

        TabGrafik.addTab("Gabungan Semua Grafik", panelGabungan);

        internalFrame1.add(TabGrafik, java.awt.BorderLayout.CENTER);

        // Panel Kontrol
        panelKontrol.setName("panelKontrol");
        panelKontrol.setPreferredSize(new Dimension(0, 44));
        panelKontrol.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png")));
        BtnRefresh.setMnemonic('R');
        BtnRefresh.setText("Refresh");
        BtnRefresh.setToolTipText("Alt+R - Refresh data grafik");
        BtnRefresh.setName("BtnRefresh");
        BtnRefresh.setPreferredSize(new Dimension(100, 30));
        BtnRefresh.addActionListener(evt -> refreshData());
        panelKontrol.add(BtnRefresh);

        BtnExportImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnExportImage.setMnemonic('I');
        BtnExportImage.setText("Export Gambar");
        BtnExportImage.setToolTipText("Alt+I - Export sebagai gambar");
        BtnExportImage.setName("BtnExportImage");
        BtnExportImage.setPreferredSize(new Dimension(120, 30));
        BtnExportImage.addActionListener(evt -> exportAsImage());
        panelKontrol.add(BtnExportImage);

        BtnExportCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnExportCSV.setMnemonic('C');
        BtnExportCSV.setText("Export CSV");
        BtnExportCSV.setToolTipText("Alt+C - Export ke CSV");
        BtnExportCSV.setName("BtnExportCSV");
        BtnExportCSV.setPreferredSize(new Dimension(100, 30));
        BtnExportCSV.addActionListener(evt -> exportToCSV());
        panelKontrol.add(BtnExportCSV);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png")));
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Print");
        BtnPrint.setToolTipText("Alt+P - Print grafik");
        BtnPrint.setName("BtnPrint");
        BtnPrint.setPreferredSize(new Dimension(100, 30));
        BtnPrint.addActionListener(evt -> printChart());
        panelKontrol.add(BtnPrint);

        BtnSummary.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        BtnSummary.setMnemonic('S');
        BtnSummary.setText("Summary");
        BtnSummary.setToolTipText("Alt+S - Ringkasan statistik");
        BtnSummary.setName("BtnSummary");
        BtnSummary.setPreferredSize(new Dimension(100, 30));
        BtnSummary.addActionListener(evt -> showSummary());
        panelKontrol.add(BtnSummary);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K - Keluar");
        BtnKeluar.setName("BtnKeluar");
        BtnKeluar.setPreferredSize(new Dimension(100, 30));
        BtnKeluar.addActionListener(evt -> dispose());
        panelKontrol.add(BtnKeluar);

        internalFrame1.add(panelKontrol, java.awt.BorderLayout.SOUTH);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        setSize(1000, 700);
    }// </editor-fold>

    // =====================================================
// PERBAIKAN TAB GABUNGAN - DJJ & NADI BERGARIS + SCROLL
// =====================================================
// Method untuk menggambar gabungan semua grafik (DIPERBAIKI)
    private void paintCombinedChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set canvas size yang lebih besar untuk gabungan dengan scroll
        canvasGabungan.setPreferredSize(new Dimension(900, 1000)); // Diperbesar untuk scroll

        int width = 800;
        int chartHeight = 250; // Diperbesar untuk lebih jelas
        int startX = 50;
        int spacing = 30;

        // Clear background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvasGabungan.getWidth(), canvasGabungan.getHeight());

        // Draw title
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("GRAFIK PARTOGRAF WHO - GABUNGAN LENGKAP", 250, 25);

        // Info pasien
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Pasien: " + namaPasien + " | No.Rawat: " + noRawat + " | Umur: " + umurPasien, 50, 45);

        // Chart 1: Cervix & Descent (Y = 70)
        int y1 = 70;
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("1. PEMBUKAAN SERVIKS & PENURUNAN KEPALA", startX, y1 - 10);
        drawFixedCervixChart(g2d, width, chartHeight, startX, y1);

        // Chart 2: DJJ dengan GARIS (Y = 370) 
        int y2 = y1 + chartHeight + spacing + 30;
        g2d.drawString("2. DENYUT JANTUNG JANIN (DJJ)", startX, y2 - 10);
        drawFixedDJJChart(g2d, width, chartHeight, startX, y2);

        // Chart 3: Vital Signs dengan GARIS (Y = 670)
        int y3 = y2 + chartHeight + spacing + 30;
        g2d.drawString("3. TEKANAN DARAH & NADI MATERNAL", startX, y3 - 10);
        drawFixedVitalChart(g2d, width, chartHeight, startX, y3);

        // Overall legend at bottom
        int legendY = y3 + chartHeight + 30;
        drawOverallLegend(g2d, startX, legendY);
    }

// =====================================================
// CHART METHODS YANG DIPERBAIKI DENGAN GARIS
// =====================================================
    private void drawFixedCervixChart(Graphics2D g2d, int width, int height, int startX, int startY) {
        // Draw grid
        drawGrid(g2d, width, height, startX, startY, "cervix");

        // Draw WHO lines
        drawWHOLines(g2d, width, height, startX, startY);

        // Draw cervix data dengan X dan GARIS penghubung
        if (!cervixData.isEmpty()) {
            g2d.setColor(new Color(33, 150, 243));
            g2d.setStroke(new BasicStroke(2));

            // GARIS penghubung cervix
            for (int i = 0; i < cervixData.size() - 1; i++) {
                DataPoint p1 = cervixData.get(i);
                DataPoint p2 = cervixData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                int y1 = startY + height - (int) ((p1.value * height) / 10.0);
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) ((p2.value * height) / 10.0);

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Simbol X untuk cervix
            g2d.setStroke(new BasicStroke(3));
            for (DataPoint point : cervixData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) ((point.value * height) / 10.0);

                int size = 5;
                g2d.drawLine(x - size, y - size, x + size, y + size);
                g2d.drawLine(x - size, y + size, x + size, y - size);
            }
        }

        // Draw descent data dengan O dan GARIS penghubung - SKALA SAMA DENGAN CERVIX
        if (!descentData.isEmpty()) {
            g2d.setColor(new Color(76, 175, 80));
            g2d.setStroke(new BasicStroke(2));

            // GARIS penghubung descent - gunakan skala 0-10
            for (int i = 0; i < descentData.size() - 1; i++) {
                DataPoint p1 = descentData.get(i);
                DataPoint p2 = descentData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                int y1 = startY + height - (int) ((p1.value * height) / 10.0); // Skala 0-10
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) ((p2.value * height) / 10.0); // Skala 0-10

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Simbol O untuk descent
            for (DataPoint point : descentData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) ((point.value * height) / 10.0); // Skala 0-10
                g2d.drawOval(x - 6, y - 6, 12, 12);
            }
        }

        // Mini legend untuk chart ini
        drawMiniLegendCervix(g2d, startX, startY + height + 15);
    }

    private void drawFixedDJJChart(Graphics2D g2d, int width, int height, int startX, int startY) {
        // Draw grid
        drawGrid(g2d, width, height, startX, startY, "djj");

        // Draw normal zone (110-160 bpm)
        g2d.setColor(new Color(76, 175, 80, 30));
        int normalTop = startY + height - ((160 - 100) * height / 80);
        int normalBottom = startY + height - ((110 - 100) * height / 80);
        g2d.fillRect(startX, normalTop, width, normalBottom - normalTop);

        // Draw DJJ data dengan GARIS PENGHUBUNG (DIPERBAIKI)
        if (!djjData.isEmpty()) {
            g2d.setStroke(new BasicStroke(2));

            // GARIS penghubung DJJ
            for (int i = 0; i < djjData.size() - 1; i++) {
                DataPoint p1 = djjData.get(i);
                DataPoint p2 = djjData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                int y1 = startY + height - (int) (((p1.value - 100) * height) / 80.0);
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) (((p2.value - 100) * height) / 80.0);

                // Warna berdasarkan nilai DJJ
                if (p1.value < 110 || p2.value < 110) {
                    g2d.setColor(new Color(244, 67, 54)); // Red
                } else if (p1.value > 160 || p2.value > 160) {
                    g2d.setColor(new Color(255, 152, 0)); // Orange  
                } else {
                    g2d.setColor(new Color(76, 175, 80)); // Green
                }

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Titik DJJ
            for (DataPoint point : djjData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) (((point.value - 100) * height) / 80.0);

                if (point.value < 110) {
                    g2d.setColor(new Color(244, 67, 54));
                } else if (point.value > 160) {
                    g2d.setColor(new Color(255, 152, 0));
                } else {
                    g2d.setColor(new Color(76, 175, 80));
                }

                g2d.fillOval(x - 4, y - 4, 8, 8);
            }
        }

        // Mini legend untuk DJJ
        drawMiniLegendDJJ(g2d, startX, startY + height + 15);
    }

    private void drawFixedVitalChart(Graphics2D g2d, int width, int height, int startX, int startY) {
        // Draw grid
        drawGrid(g2d, width, height, startX, startY, "vital");

        // Kumpulkan data TD berdasarkan waktu
        java.util.Map<Long, DataPoint> systolicByTime = new java.util.HashMap<>();
        java.util.Map<Long, DataPoint> diastolicByTime = new java.util.HashMap<>();

        for (DataPoint point : systolicData) {
            systolicByTime.put(point.time, point);
        }
        for (DataPoint point : diastolicData) {
            diastolicByTime.put(point.time, point);
        }

        // Gambar garis vertikal TD pada waktu yang sama
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(new Color(233, 30, 99));

        for (Long time : systolicByTime.keySet()) {
            if (diastolicByTime.containsKey(time)) {
                DataPoint systolicPoint = systolicByTime.get(time);
                DataPoint diastolicPoint = diastolicByTime.get(time);

                int x = startX + (int) ((time * width) / 12.0);
                int ySystolic = startY + height - (int) (((systolicPoint.value - 60) * height) / 140.0);
                int yDiastolic = startY + height - (int) (((diastolicPoint.value - 60) * height) / 140.0);

                // Garis vertikal
                g2d.drawLine(x, ySystolic, x, yDiastolic);

                // Segitiga sistol
                int[] xPointsSys = {x, x - 4, x + 4};
                int[] yPointsSys = {ySystolic - 4, ySystolic + 2, ySystolic + 2};
                g2d.fillPolygon(xPointsSys, yPointsSys, 3);

                // Segitiga diastol
                int[] xPointsDias = {x, x - 4, x + 4};
                int[] yPointsDias = {yDiastolic + 4, yDiastolic - 2, yDiastolic - 2};
                g2d.fillPolygon(xPointsDias, yPointsDias, 3);
            }
        }

        // Draw nadi dengan GARIS PENGHUBUNG (DIPERBAIKI)
        if (!pulseData.isEmpty()) {
            g2d.setColor(new Color(255, 87, 34));
            g2d.setStroke(new BasicStroke(2));

            // GARIS penghubung nadi
            for (int i = 0; i < pulseData.size() - 1; i++) {
                DataPoint p1 = pulseData.get(i);
                DataPoint p2 = pulseData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                int y1 = startY + height - (int) (((p1.value - 60) * height) / 140.0);
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) (((p2.value - 60) * height) / 140.0);

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Titik nadi
            for (DataPoint point : pulseData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) (((point.value - 60) * height) / 140.0);
                g2d.fillOval(x - 4, y - 4, 8, 8);
            }
        }

        // Mini legend untuk vital
        drawMiniLegendVital(g2d, startX, startY + height + 15);
    }

// =====================================================
// MINI LEGENDS UNTUK SETIAP CHART
// =====================================================
    private void drawMiniLegendCervix(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        // X untuk cervix
        g2d.setColor(new Color(33, 150, 243));
        g2d.setStroke(new BasicStroke(2));
        int size = 3;
        g2d.drawLine(x - size, y - size, x + size, y + size);
        g2d.drawLine(x - size, y + size, x + size, y - size);
        g2d.setColor(Color.BLACK);
        g2d.drawString("X-Serviks", x + 10, y + 3); // Hapus detail cm

        // O untuk descent  
        g2d.setColor(new Color(76, 175, 80));
        g2d.drawOval(x + 70 - 3, y - 3, 6, 6);
        g2d.setColor(Color.BLACK);
        g2d.drawString("O-Penurunan", x + 80, y + 3); // Hapus detail

        // WHO lines
        g2d.setColor(new Color(255, 152, 0));
        g2d.drawString("Alert", x + 150, y + 3);
        g2d.setColor(new Color(244, 67, 54));
        g2d.drawString("Action", x + 190, y + 3);
    }

    private void drawMiniLegendDJJ(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        g2d.setColor(new Color(76, 175, 80));
        g2d.fillOval(x, y - 2, 4, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Normal 110-160", x + 10, y + 3);

        g2d.setColor(new Color(255, 152, 0));
        g2d.fillOval(x + 100, y - 2, 4, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Tachy >160", x + 110, y + 3);

        g2d.setColor(new Color(244, 67, 54));
        g2d.fillOval(x + 190, y - 2, 4, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Brady <110", x + 200, y + 3);
    }

    private void drawMiniLegendVital(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        // TD vertikal
        g2d.setColor(new Color(233, 30, 99));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x, y - 4, x, y + 4);
        g2d.setColor(Color.BLACK);
        g2d.drawString("TD Vertikal", x + 10, y + 3);

        // Nadi
        g2d.setColor(new Color(255, 87, 34));
        g2d.fillOval(x + 80, y - 2, 4, 4);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Nadi", x + 90, y + 3);
    }

// =====================================================
// PERBAIKAN SETUP CANVAS GABUNGAN DENGAN SCROLL
// =====================================================
// Update di initComponents() - Panel Gabungan
    private void setupPanelGabungan() {
        // Panel Gabungan dengan scroll yang proper
        panelGabungan.setName("panelGabungan");
        panelGabungan.setLayout(new java.awt.BorderLayout());

        canvasGabungan.setBackground(Color.WHITE);
        canvasGabungan.setBorder(javax.swing.BorderFactory.createTitledBorder("Semua Grafik Partograf WHO"));
        canvasGabungan.setName("canvasGabungan");
        canvasGabungan.setPreferredSize(new Dimension(900, 1000)); // Size besar untuk scroll
        canvasGabungan.setComponentPopupMenu(jPopupMenuGrafik);

        // JScrollPane dengan konfigurasi yang tepat
        JScrollPane scrollGabungan = new JScrollPane(canvasGabungan);
        scrollGabungan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollGabungan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGabungan.getVerticalScrollBar().setUnitIncrement(20); // Scroll speed
        scrollGabungan.getHorizontalScrollBar().setUnitIncrement(20);
        scrollGabungan.setPreferredSize(new Dimension(850, 400)); // Viewport size

        panelGabungan.add(scrollGabungan, java.awt.BorderLayout.CENTER);

        // Instruksi scroll
        javax.swing.JLabel lblInstruksi = new javax.swing.JLabel();
        lblInstruksi.setText("Gunakan scroll vertikal untuk melihat semua grafik");
        lblInstruksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruksi.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInstruksi.setForeground(new Color(100, 100, 100));
        panelGabungan.add(lblInstruksi, java.awt.BorderLayout.SOUTH);

        TabGrafik.addTab("Gabungan Semua Grafik", panelGabungan);
    }

// =====================================================
// TAMBAHAN: METHOD AUTO SCROLL KE GRAFIK TERTENTU
// =====================================================
    public void scrollToChart(int chartNumber) {
        if (TabGrafik.getSelectedIndex() == 3) { // Tab gabungan
            JScrollPane scrollPane = (JScrollPane) panelGabungan.getComponent(0);

            int[] chartPositions = {0, 320, 620, 920}; // Y positions dari setiap chart

            if (chartNumber >= 1 && chartNumber <= 3) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    scrollPane.getVerticalScrollBar().setValue(chartPositions[chartNumber]);
                });
            }
        }
    }

// Method untuk scroll ke chart dengan alert
    public void scrollToAlertChart() {
        // Scroll otomatis ke chart yang ada alert
        if (!djjData.isEmpty()) {
            boolean hasAlert = djjData.stream().anyMatch(d -> d.value < 110 || d.value > 160);
            if (hasAlert) {
                scrollToChart(2); // Scroll ke DJJ chart
                return;
            }
        }

        if (!systolicData.isEmpty()) {
            boolean hasHypertension = systolicData.stream().anyMatch(d -> d.value >= 160);
            if (hasHypertension) {
                scrollToChart(3); // Scroll ke Vital chart
            }
        }
    }

    // Method untuk setup event handlers
    private void setupEventHandlers() {
        // Tab change listener untuk refresh otomatis
        TabGrafik.addChangeListener(e -> {
            refreshCanvases();
        });
    }

    // Method untuk setup keyboard shortcuts
    private void setupKeyboardShortcuts() {
        // F5 untuk refresh
        javax.swing.KeyStroke refreshKey = javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_F5, 0);
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(refreshKey, "refresh");
        getRootPane().getActionMap().put("refresh", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        // Ctrl+E untuk export
        javax.swing.KeyStroke exportKey = javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK);
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(exportKey, "export");
        getRootPane().getActionMap().put("export", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportAsImage();
            }
        });
    }

    // Method untuk setup auto refresh
    private void setupAutoRefresh() {
        Timer autoRefresh = new Timer(30000, e -> { // 30 detik
            if (!noRawat.isEmpty()) {
                loadDataAndUpdateCharts();
            }
        });
        autoRefresh.start();
    }

    // Method untuk load data dari database
    private void loadDataAndUpdateCharts() {
        if (noRawat.isEmpty()) {
            return;
        }

        loadCervixData();
        loadDescentData();
        loadDJJData();
        loadVitalData();
        refreshCanvases();

        // Check emergency conditions dan WHO lines
        checkEmergencyConditions();
        checkProgressAgainstWHOLines(); // Tambahan check WHO
    }

    // =====================================================
// METHOD refreshCanvases() - Refresh Semua Canvas Grafik
// =====================================================
    /**
     * Method untuk refresh semua canvas grafik partograf Digunakan setelah data
     * update atau perubahan tampilan
     */
    private void refreshCanvases() {
        // Refresh canvas individual
        if (canvasCervix != null) {
            canvasCervix.repaint();
            canvasCervix.revalidate();
        }

        if (canvasDJJ != null) {
            canvasDJJ.repaint();
            canvasDJJ.revalidate();
        }

        if (canvasVital != null) {
            canvasVital.repaint();
            canvasVital.revalidate();
        }

        if (canvasGabungan != null) {
            // Update preferred size untuk scroll yang proper
            canvasGabungan.setPreferredSize(new Dimension(900, 1000));
            canvasGabungan.repaint();
            canvasGabungan.revalidate();

            // Update scroll pane jika ada
            java.awt.Container parent = canvasGabungan.getParent();
            if (parent instanceof javax.swing.JViewport) {
                javax.swing.JViewport viewport = (javax.swing.JViewport) parent;
                java.awt.Container scrollParent = viewport.getParent();
                if (scrollParent instanceof javax.swing.JScrollPane) {
                    javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane) scrollParent;
                    scrollPane.revalidate();
                    scrollPane.repaint();
                }
            }
        }

        // Force update container
        if (TabGrafik != null) {
            TabGrafik.revalidate();
            TabGrafik.repaint();
        }

        // Update status bar jika ada
        updateStatusInfo();
    }

    private void updateStatusInfo() {
        if (lblInfoPasien != null) {
            int totalObservasi = cervixData.size() + djjData.size() + systolicData.size();
            String status = totalObservasi > 0 ? "Data tersedia" : "Tidak ada data";

            lblInfoPasien.setText(String.format(
                    "Pasien: %s | No.Rawat: %s | Status: %s (%d observasi)",
                    namaPasien, noRawat, status, totalObservasi
            ));
        }
    }

    // Load data pembukaan serviks
    private void loadCervixData() {
        cervixData.clear();
        try {
            String sql = "SELECT tgl_perawatan, jam_rawat, pembukaan_serviks "
                    + "FROM partograf_kala12 WHERE no_rawat = ? AND pembukaan_serviks IS NOT NULL "
                    + "AND pembukaan_serviks != '' ORDER BY tgl_perawatan, jam_rawat";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);
            rs = ps.executeQuery();

            LocalDateTime startTime = null;

            while (rs.next()) {
                String dateTimeStr = rs.getString("tgl_perawatan") + " " + rs.getString("jam_rawat");
                LocalDateTime currentTime = LocalDateTime.parse(dateTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (startTime == null) {
                    startTime = currentTime;
                }

                long hours = Duration.between(startTime, currentTime).toHours();
                double cervixValue = Double.parseDouble(rs.getString("pembukaan_serviks"));

                cervixData.add(new DataPoint(hours, cervixValue));
            }
        } catch (Exception e) {
            System.out.println("Error loading cervix data: " + e);
        } finally {
            closeResultSet();
        }
    }

    private void loadDescentData() {
        descentData.clear();
        try {
            String sql = "SELECT tgl_perawatan, jam_rawat, penurunan_kepala "
                    + "FROM partograf_kala12 WHERE no_rawat = ? AND penurunan_kepala IS NOT NULL "
                    + "AND penurunan_kepala != '' ORDER BY tgl_perawatan, jam_rawat";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);
            rs = ps.executeQuery();

            LocalDateTime startTime = null;

            while (rs.next()) {
                String dateTimeStr = rs.getString("tgl_perawatan") + " " + rs.getString("jam_rawat");
                LocalDateTime currentTime = LocalDateTime.parse(dateTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (startTime == null) {
                    startTime = currentTime;
                }

                long hours = Duration.between(startTime, currentTime).toHours();
                double penurunanValue = Double.parseDouble(rs.getString("penurunan_kepala"));

                descentData.add(new DataPoint(hours, penurunanValue));
            }
        } catch (Exception e) {
            System.out.println("Error loading descent data: " + e);
        } finally {
            closeResultSet();
        }
    }

    // Load data DJJ
    private void loadDJJData() {
        djjData.clear();
        try {
            String sql = "SELECT tgl_perawatan, jam_rawat, djj "
                    + "FROM partograf_kala12 WHERE no_rawat = ? AND djj IS NOT NULL "
                    + "AND djj != '' ORDER BY tgl_perawatan, jam_rawat";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);
            rs = ps.executeQuery();

            LocalDateTime startTime = null;

            while (rs.next()) {
                String dateTimeStr = rs.getString("tgl_perawatan") + " " + rs.getString("jam_rawat");
                LocalDateTime currentTime = LocalDateTime.parse(dateTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (startTime == null) {
                    startTime = currentTime;
                }

                long hours = Duration.between(startTime, currentTime).toHours();
                double djjValue = Double.parseDouble(rs.getString("djj"));

                djjData.add(new DataPoint(hours, djjValue));
            }
        } catch (Exception e) {
            System.out.println("Error loading DJJ data: " + e);
        } finally {
            closeResultSet();
        }
    }

    // Load data vital signs
    private void loadVitalData() {
        systolicData.clear();
        diastolicData.clear();
        pulseData.clear();

        try {
            // Query dengan field tensi gabungan (bukan td_sistol, td_diastol terpisah)
            String sql = "SELECT tgl_perawatan, jam_rawat, tensi, nadi "
                    + "FROM partograf_kala12 WHERE no_rawat = ? "
                    + "ORDER BY tgl_perawatan, jam_rawat";

            ps = koneksi.prepareStatement(sql);
            ps.setString(1, noRawat);
            rs = ps.executeQuery();

            LocalDateTime startTime = null;

            while (rs.next()) {
                String dateTimeStr = rs.getString("tgl_perawatan") + " " + rs.getString("jam_rawat");
                LocalDateTime currentTime = LocalDateTime.parse(dateTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (startTime == null) {
                    startTime = currentTime;
                }

                long hours = Duration.between(startTime, currentTime).toHours();

                // Parse field tensi gabungan (format: "120/80")
                String tensiStr = rs.getString("tensi");
                if (tensiStr != null && !tensiStr.isEmpty()) {
                    String[] tensiParts = parseTensi(tensiStr);
                    if (tensiParts != null && tensiParts.length == 2) {
                        try {
                            double sistol = Double.parseDouble(tensiParts[0]);
                            double diastol = Double.parseDouble(tensiParts[1]);

                            systolicData.add(new DataPoint(hours, sistol, "systolic"));
                            diastolicData.add(new DataPoint(hours, diastol, "diastolic"));
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing tensi: " + tensiStr);
                        }
                    }
                }

                // Parse nadi
                String nadiStr = rs.getString("nadi");
                if (nadiStr != null && !nadiStr.isEmpty()) {
                    try {
                        double nadi = Double.parseDouble(nadiStr);
                        pulseData.add(new DataPoint(hours, nadi, "pulse"));
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing nadi: " + nadiStr);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading vital data: " + e);
        } finally {
            closeResultSet();
        }
    }

    // Helper method untuk parse field tensi gabungan
    private String[] parseTensi(String tensiStr) {
        if (tensiStr == null || tensiStr.trim().isEmpty()) {
            return null;
        }

        // Remove whitespace dan convert ke lowercase untuk konsistensi
        tensiStr = tensiStr.trim().toLowerCase();

        // Handle berbagai format yang mungkin:
        // "120/80", "120 / 80", "120-80", "120 80", "120mmHg/80mmHg", dll
        String[] patterns = {
            "/", // Format standar: 120/80
            " / ", // Format dengan spasi: 120 / 80  
            "-", // Format dengan dash: 120-80
            " ", // Format dengan spasi: 120 80
            "mmhg/" // Format dengan unit: 120mmHg/80mmHg
        };

        for (String pattern : patterns) {
            if (tensiStr.contains(pattern)) {
                String[] parts = tensiStr.split(pattern);
                if (parts.length == 2) {
                    // Clean up parts (remove mmHg, spaces, etc)
                    String sistol = parts[0].replaceAll("[^0-9.]", "").trim();
                    String diastol = parts[1].replaceAll("[^0-9.]", "").trim();

                    if (!sistol.isEmpty() && !diastol.isEmpty()) {
                        return new String[]{sistol, diastol};
                    }
                }
            }
        }

        // Jika tidak ada separator yang ditemukan, coba deteksi otomatis
        // Asumsi: 3 digit pertama = sistol, 2 digit terakhir = diastol
        // Contoh: "12080" -> "120" dan "80"
        String numbersOnly = tensiStr.replaceAll("[^0-9]", "");
        if (numbersOnly.length() >= 4 && numbersOnly.length() <= 6) {
            if (numbersOnly.length() == 5) {
                // Format: 12080
                return new String[]{numbersOnly.substring(0, 3), numbersOnly.substring(3)};
            } else if (numbersOnly.length() == 4) {
                // Format: 1260 (bisa jadi 126/0 atau 12/60, pilih yang masuk akal)
                String part1 = numbersOnly.substring(0, 2);
                String part2 = numbersOnly.substring(2);

                int val1 = Integer.parseInt(part1);
                int val2 = Integer.parseInt(part2);

                // Logika: sistol biasanya 90-200, diastol 40-120
                if (val1 >= 90 && val1 <= 200 && val2 >= 40 && val2 <= 120) {
                    return new String[]{part1, part2};
                }
            } else if (numbersOnly.length() == 6) {
                // Format: 120080 -> 120/80
                return new String[]{numbersOnly.substring(0, 3), numbersOnly.substring(3)};
            }
        }

        System.out.println("Cannot parse tensi format: " + tensiStr);
        return null;
    }

// Method untuk validasi range tensi yang masuk akal
    private boolean isValidTensiRange(double sistol, double diastol) {
        return (sistol >= 60 && sistol <= 250)
                && (diastol >= 30 && diastol <= 150)
                && (sistol > diastol); // Sistol harus lebih tinggi dari diastol
    }

    // Helper method untuk parsing station
    private double parseStation(String station) {
        if (station == null || station.isEmpty()) {
            return 0;
        }

        try {
            if (station.startsWith("+")) {
                return Double.parseDouble(station.substring(1));
            } else if (station.startsWith("-")) {
                return -Double.parseDouble(station.substring(1));
            } else {
                return Double.parseDouble(station);
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Method untuk close ResultSet dan PreparedStatement
    private void closeResultSet() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing result set: " + e);
        }
    }

    // Update info pasien di header
    private void updatePatientInfo() {
        lblInfoPasien.setText(String.format("Nama: %s | No.Rawat: %s | Umur: %s | Penolong: %s",
                namaPasien, noRawat, umurPasien, petugasPenolong));
    }

    // Method refresh data
    private void refreshData() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            loadDataAndUpdateCharts();
            JOptionPane.showMessageDialog(this, "Data grafik berhasil diperbarui!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error refresh data: " + e.getMessage());
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    // Setter untuk data dari form utama
    public void setData(String noRawat, String namaPasien, String umurPasien, String petugasPenolong) {
        this.noRawat = noRawat;
        this.namaPasien = namaPasien;
        this.umurPasien = umurPasien;
        this.petugasPenolong = petugasPenolong;

        updatePatientInfo();
        loadDataAndUpdateCharts();
    }

    private void drawCervixLegendFixed(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));

        // Legend pembukaan serviks
        g2d.setColor(new Color(33, 150, 243));
        g2d.setStroke(new BasicStroke(2));
        int size = 4;
        g2d.drawLine(x - size, y - size, x + size, y + size);
        g2d.drawLine(x - size, y + size, x + size, y - size);
        g2d.setColor(Color.BLACK);
        g2d.drawString("X = Pembukaan Serviks", x + 15, y + 4); // Hapus "(cm)"

        // Legend penurunan kepala
        g2d.setColor(new Color(76, 175, 80));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x + 200 - 5, y - 5, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("O = Penurunan Kepala", x + 215, y + 4);

        // Legend untuk WHO lines
        y += 20;
        g2d.setColor(new Color(255, 152, 0));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(x, y, x + 20, y);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawString("ALERT LINE: Garis Waspada WHO (4cm→10cm/4jam)", x + 25, y + 4);

        y += 15;
        g2d.setColor(new Color(244, 67, 54));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(x, y, x + 20, y);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawString("ACTION LINE: Garis Tindakan WHO (4 jam setelah Alert)", x + 25, y + 4);
    }

    private void drawVitalLegendFixed(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));

        // Legend untuk tekanan darah dengan garis vertikal
        g2d.setColor(new Color(233, 30, 99));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y - 8, x, y + 8);

        // Segitiga atas untuk sistol
        int[] xPointsSys = {x, x - 3, x + 3};
        int[] yPointsSys = {y - 8, y - 5, y - 5};
        g2d.fillPolygon(xPointsSys, yPointsSys, 3);

        // Segitiga bawah untuk diastol  
        int[] xPointsDias = {x, x - 3, x + 3};
        int[] yPointsDias = {y + 8, y + 5, y + 5};
        g2d.fillPolygon(xPointsDias, yPointsDias, 3);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Tekanan Darah: ▲ Sistol, ▼ Diastol, | Garis Vertikal", x + 15, y + 4);

        // Legend untuk nadi
        g2d.setColor(new Color(255, 87, 34));
        g2d.fillOval(x + 350, y - 4, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("● = Nadi (bpm)", x + 365, y + 4);
    }

    // Method untuk menggambar grafik DJJ
    private void paintDJJChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = canvasDJJ.getWidth() - 80;
        int height = canvasDJJ.getHeight() - 80;
        int startX = 50;
        int startY = 30;

        if (width <= 0 || height <= 0) {
            return;
        }

        // Clear background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvasDJJ.getWidth(), canvasDJJ.getHeight());

        // Draw grid
        drawGrid(g2d, width, height, startX, startY, "djj");

        // Draw normal zone (110-160 bpm)
        g2d.setColor(new Color(76, 175, 80, 50));
        int normalTop = startY + height - ((160 - 100) * height / 80);
        int normalBottom = startY + height - ((110 - 100) * height / 80);
        g2d.fillRect(startX, normalTop, width, normalBottom - normalTop);

        // Draw DJJ data
        if (!djjData.isEmpty()) {
            g2d.setStroke(new BasicStroke(3));

            for (int i = 0; i < djjData.size() - 1; i++) {
                DataPoint p1 = djjData.get(i);
                DataPoint p2 = djjData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                int y1 = startY + height - (int) (((p1.value - 100) * height) / 80.0);
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) (((p2.value - 100) * height) / 80.0);

                // Color based on DJJ value
                if (p1.value < 110) {
                    g2d.setColor(new Color(244, 67, 54)); // Red for bradycardia
                } else if (p1.value > 160) {
                    g2d.setColor(new Color(255, 152, 0)); // Orange for tachycardia
                } else {
                    g2d.setColor(new Color(76, 175, 80)); // Green for normal
                }

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Draw points
            for (DataPoint point : djjData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) (((point.value - 100) * height) / 80.0);

                if (point.value < 110) {
                    g2d.setColor(new Color(244, 67, 54));
                } else if (point.value > 160) {
                    g2d.setColor(new Color(255, 152, 0));
                } else {
                    g2d.setColor(new Color(76, 175, 80));
                }

                g2d.fillOval(x - 4, y - 4, 8, 8);
            }
        }

        // Draw legend
        drawDJJLegend(g2d, startX, startY + height + 20);
    }

// Method untuk menggambar grafik vital signs dengan garis vertikal TD (DIPERBAIKI)
    private void paintVitalChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = canvasVital.getWidth() - 80;
        int height = canvasVital.getHeight() - 80;
        int startX = 50;
        int startY = 30;

        if (width <= 0 || height <= 0) {
            return;
        }

        // Clear background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvasVital.getWidth(), canvasVital.getHeight());

        // Draw grid
        drawGrid(g2d, width, height, startX, startY, "vital");

        // PERBAIKAN: Gambar garis vertikal sistol-diastol pada waktu yang sama
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(new Color(233, 30, 99)); // Pink untuk TD

        // Kumpulkan data TD berdasarkan waktu yang sama
        java.util.Map<Long, DataPoint> systolicByTime = new java.util.HashMap<>();
        java.util.Map<Long, DataPoint> diastolicByTime = new java.util.HashMap<>();

        for (DataPoint point : systolicData) {
            systolicByTime.put(point.time, point);
        }
        for (DataPoint point : diastolicData) {
            diastolicByTime.put(point.time, point);
        }

        // Gambar garis vertikal untuk setiap waktu pemeriksaan yang memiliki kedua data
        for (Long time : systolicByTime.keySet()) {
            if (diastolicByTime.containsKey(time)) {
                DataPoint systolicPoint = systolicByTime.get(time);
                DataPoint diastolicPoint = diastolicByTime.get(time);

                int x = startX + (int) ((time * width) / 12.0);
                int ySystolic = startY + height - (int) (((systolicPoint.value - 60) * height) / 140.0);
                int yDiastolic = startY + height - (int) (((diastolicPoint.value - 60) * height) / 140.0);

                // Gambar garis vertikal dari sistol ke diastol
                g2d.drawLine(x, ySystolic, x, yDiastolic);

                // Gambar titik sistol (segitiga ke atas)
                int[] xPointsSys = {x, x - 5, x + 5};
                int[] yPointsSys = {ySystolic - 5, ySystolic + 3, ySystolic + 3};
                g2d.fillPolygon(xPointsSys, yPointsSys, 3);

                // Gambar titik diastol (segitiga ke bawah)
                int[] xPointsDias = {x, x - 5, x + 5};
                int[] yPointsDias = {yDiastolic + 5, yDiastolic - 3, yDiastolic - 3};
                g2d.fillPolygon(xPointsDias, yPointsDias, 3);
            }
        }

        // Draw pulse data dengan garis penghubung horizontal
        if (!pulseData.isEmpty()) {
            g2d.setColor(new Color(255, 87, 34));
            g2d.setStroke(new BasicStroke(2));

            // Hubungkan titik nadi dengan garis
            for (int i = 0; i < pulseData.size() - 1; i++) {
                DataPoint p1 = pulseData.get(i);
                DataPoint p2 = pulseData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                int y1 = startY + height - (int) (((p1.value - 60) * height) / 140.0);
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) (((p2.value - 60) * height) / 140.0);

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Gambar titik nadi (dot/bulatan penuh)
            for (DataPoint point : pulseData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) (((point.value - 60) * height) / 140.0);
                g2d.fillOval(x - 4, y - 4, 8, 8);
            }
        }

        // Draw legend
        drawVitalLegendFixed(g2d, startX, startY + height + 20);
    }

    // Helper methods untuk menggambar grid
    private void drawGrid(Graphics2D g2d, int width, int height, int startX, int startY, String type) {
        g2d.setColor(new Color(240, 240, 240));
        g2d.setStroke(new BasicStroke(1));

        // Vertical grid (12 hours)
        for (int i = 0; i <= 12; i++) {
            int x = startX + (i * width / 12);
            g2d.drawLine(x, startY, x, startY + height);

            // Time labels
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(i + "h", x - 8, startY + height + 15);
            g2d.setColor(new Color(240, 240, 240));
        }

        // Horizontal grid based on type
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        switch (type) {
            case "cervix":
                for (int i = 0; i <= 10; i++) {
                    int y = startY + height - (i * height / 10);
                    g2d.setColor(new Color(240, 240, 240));
                    g2d.drawLine(startX, y, startX + width, y);
                    g2d.setColor(Color.BLACK);

                    // Label kiri: angka pembukaan (tanpa "cm")
                    g2d.drawString(String.valueOf(i), 5, y + 4);

                    // Label kanan: angka penurunan kepala (0-5 saja yang ditampilkan)
                    if (i >= 0 && i <= 5) {
                        g2d.drawString(String.valueOf(i), startX + width + 5, y + 4);
                    }
                }
                break;

            case "djj":
                int[] djjMarks = {100, 110, 120, 130, 140, 150, 160, 170, 180};
                for (int mark : djjMarks) {
                    int y = startY + height - ((mark - 100) * height / 80);
                    g2d.setColor(new Color(240, 240, 240));
                    g2d.drawLine(startX, y, startX + width, y);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.valueOf(mark), 5, y + 4);
                }
                break;

            case "vital":
                for (int i = 60; i <= 200; i += 20) {
                    int y = startY + height - ((i - 60) * height / 140);
                    g2d.setColor(new Color(240, 240, 240));
                    g2d.drawLine(startX, y, startX + width, y);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.valueOf(i), 5, y + 4);
                }
                break;
        }
    }

    // Method untuk menggambar WHO standard lines
    private void drawWHOLines(Graphics2D g2d, int width, int height, int startX, int startY) {
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));

        if (!cervixData.isEmpty()) {
            // Ambil nilai pembukaan pertama
            DataPoint firstCervix = cervixData.get(0);
            double firstOpening = firstCervix.value;

            // ALERT LINE dimulai dari pembukaan pertama
            g2d.setColor(new Color(255, 152, 0)); // Orange
            int alertStartX = startX;
            int alertStartY = startY + height - (int) ((firstOpening * height) / 10.0);
            int alertEndX = startX + (int) ((4.0 * width) / 12.0);
            int alertEndY = startY + height - (int) ((10.0 * height) / 10.0);

            g2d.drawLine(alertStartX, alertStartY, alertEndX, alertEndY);

            // ACTION LINE - 4 jam setelah Alert Line
            g2d.setColor(new Color(244, 67, 54)); // Red
            int actionStartX = startX + (int) ((4.0 * width) / 12.0);
            int actionStartY = startY + height - (int) ((firstOpening * height) / 10.0);
            int actionEndX = startX + (int) ((8.0 * width) / 12.0);
            int actionEndY = startY + height - (int) ((10.0 * height) / 10.0);

            g2d.drawLine(actionStartX, actionStartY, actionEndX, actionEndY);

            // Labels
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.setColor(new Color(255, 152, 0));
            g2d.drawString("ALERT", alertStartX + 20, alertStartY - 10);
            g2d.drawString("(dari " + String.format("%.1f", firstOpening) + "cm)",
                    alertStartX + 20, alertStartY + 5);

            g2d.setColor(new Color(244, 67, 54));
            g2d.drawString("ACTION", actionStartX + 20, actionStartY - 10);
            g2d.drawString("(4 jam setelah Alert)", actionStartX + 20, actionStartY + 5);

        } else {
            // Garis WHO standard jika belum ada data (4cm default)
            g2d.setColor(new Color(255, 152, 0));
            int alertStartX = startX;
            int alertStartY = startY + height - (int) ((4.0 * height) / 10.0);
            int alertEndX = startX + (int) ((4.0 * width) / 12.0);
            int alertEndY = startY + height - (int) ((10.0 * height) / 10.0);
            g2d.drawLine(alertStartX, alertStartY, alertEndX, alertEndY);

            g2d.setColor(new Color(244, 67, 54));
            int actionStartX = startX + (int) ((4.0 * width) / 12.0);
            int actionStartY = startY + height - (int) ((4.0 * height) / 10.0);
            int actionEndX = startX + (int) ((8.0 * width) / 12.0);
            int actionEndY = startY + height - (int) ((10.0 * height) / 10.0);
            g2d.drawLine(actionStartX, actionStartY, actionEndX, actionEndY);

            // Labels default
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            g2d.setColor(new Color(255, 152, 0));
            g2d.drawString("ALERT", alertStartX + 20, alertStartY - 10);
            g2d.drawString("(Standard WHO)", alertStartX + 20, alertStartY + 5);

            g2d.setColor(new Color(244, 67, 54));
            g2d.drawString("ACTION", actionStartX + 20, actionStartY - 10);
        }

        g2d.setStroke(new BasicStroke(1)); // Reset stroke
    }

    private void drawDefaultWHOLines(Graphics2D g2d, int width, int height, int startX, int startY) {
        // Garis WHO standard jika belum ada data
        g2d.setColor(new Color(255, 152, 0));
        int alertStartX = startX;
        int alertStartY = startY + height - (int) ((4.0 * height) / 10.0);
        int alertEndX = startX + (int) ((4.0 * width) / 12.0);
        int alertEndY = startY + height - (int) ((10.0 * height) / 10.0);
        g2d.drawLine(alertStartX, alertStartY, alertEndX, alertEndY);

        g2d.setColor(new Color(244, 67, 54));
        int actionStartX = startX + (int) ((4.0 * width) / 12.0);
        int actionStartY = startY + height - (int) ((4.0 * height) / 10.0);
        int actionEndX = startX + (int) ((8.0 * width) / 12.0);
        int actionEndY = startY + height - (int) ((10.0 * height) / 10.0);
        g2d.drawLine(actionStartX, actionStartY, actionEndX, actionEndY);
    }

    // Methods untuk menggambar legend
    private void drawCervixLegend(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));

        // Data line legends
        g2d.setColor(new Color(33, 150, 243));
        g2d.fillOval(x, y, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Pembukaan Serviks (cm)", x + 15, y + 8);

        g2d.setColor(new Color(76, 175, 80));
        int[] xPoints = {x + 150, x + 145, x + 155};
        int[] yPoints = {y - 1, y + 7, y + 7};
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Penurunan Kepala (station)", x + 165, y + 8);

        // WHO Lines legend - diperbaiki
        y += 20;
        g2d.setColor(new Color(255, 152, 0));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(x, y + 4, x + 20, y + 4);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawString("ALERT LINE: 4cm→10cm dalam 4 jam (1.5cm/jam)", x + 25, y + 8);

        y += 15;
        g2d.setColor(new Color(244, 67, 54));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(x, y + 4, x + 20, y + 4);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        g2d.drawString("ACTION LINE: 4 jam setelah Alert Line", x + 25, y + 8);

        // Interpretasi WHO
        y += 20;
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(new Color(0, 100, 0));
        g2d.drawString("INTERPRETASI WHO:", x, y);

        y += 15;
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        g2d.setColor(Color.BLACK);
        g2d.drawString("• Di bawah Alert Line = Normal, lanjutkan observasi", x, y);

        y += 12;
        g2d.drawString("• Menyentuh Alert Line = Monitor lebih ketat, siapkan intervensi", x, y);

        y += 12;
        g2d.drawString("• Melewati Action Line = TINDAKAN SEGERA (augmentasi atau SC)", x, y);
    }

    private void drawDJJLegend(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));

        g2d.setColor(new Color(76, 175, 80));
        g2d.fillOval(x, y, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Normal (110-160 bpm)", x + 15, y + 8);

        g2d.setColor(new Color(255, 152, 0));
        g2d.fillOval(x + 150, y, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Tachycardia (>160 bpm)", x + 165, y + 8);

        g2d.setColor(new Color(244, 67, 54));
        g2d.fillOval(x + 300, y, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Bradycardia (<110 bpm)", x + 315, y + 8);
    }

    private void drawVitalLegend(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));

        g2d.setColor(new Color(233, 30, 99));
        int[] xPoints1 = {x, x - 4, x + 4};
        int[] yPoints1 = {y, y + 8, y + 8};
        g2d.fillPolygon(xPoints1, yPoints1, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawString("TD Sistol (mmHg)", x + 15, y + 8);

        g2d.setColor(new Color(156, 39, 176));
        int[] xPoints2 = {x + 140, x + 136, x + 144};
        int[] yPoints2 = {y + 8, y, y};
        g2d.fillPolygon(xPoints2, yPoints2, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawString("TD Diastol (mmHg)", x + 155, y + 8);

        g2d.setColor(new Color(255, 87, 34));
        g2d.fillOval(x + 280, y, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Nadi (bpm)", x + 295, y + 8);
    }

    private void drawMiniCervixChart(Graphics2D g2d, int width, int height, int startX, int startY) {
        drawGrid(g2d, width, height, startX, startY, "cervix");
        drawWHOLines(g2d, width, height, startX, startY);

        // Draw cervix data dengan X (simplified)
        if (!cervixData.isEmpty()) {
            g2d.setColor(new Color(33, 150, 243));
            g2d.setStroke(new BasicStroke(2));

            for (DataPoint point : cervixData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) ((point.value * height) / 10.0);

                // Mini X
                int size = 3;
                g2d.drawLine(x - size, y - size, x + size, y + size);
                g2d.drawLine(x - size, y + size, x + size, y - size);
            }
        }

        // Draw descent data dengan O (simplified)
        if (!descentData.isEmpty()) {
            g2d.setColor(new Color(76, 175, 80));
            g2d.setStroke(new BasicStroke(1));

            for (DataPoint point : descentData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) (((point.value + 3) * height) / 6.0);

                // Mini O
                g2d.drawOval(x - 3, y - 3, 6, 6);
            }
        }
    }

    private void drawMiniDJJChart(Graphics2D g2d, int width, int height, int startX, int startY) {
        drawGrid(g2d, width, height, startX, startY, "djj");

        // Normal zone
        g2d.setColor(new Color(76, 175, 80, 30));
        int normalTop = startY + height - ((160 - 100) * height / 80);
        int normalBottom = startY + height - ((110 - 100) * height / 80);
        g2d.fillRect(startX, normalTop, width, normalBottom - normalTop);

        if (!djjData.isEmpty()) {
            for (DataPoint point : djjData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) (((point.value - 100) * height) / 80.0);

                if (point.value < 110) {
                    g2d.setColor(new Color(244, 67, 54));
                } else if (point.value > 160) {
                    g2d.setColor(new Color(255, 152, 0));
                } else {
                    g2d.setColor(new Color(76, 175, 80));
                }

                g2d.fillOval(x - 3, y - 3, 6, 6);
            }
        }
    }

    private void drawMiniVitalChart(Graphics2D g2d, int width, int height, int startX, int startY) {
        drawGrid(g2d, width, height, startX, startY, "vital");

        // Draw TD dengan garis vertikal (simplified)
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(233, 30, 99));

        java.util.Map<Long, DataPoint> systolicByTime = new java.util.HashMap<>();
        java.util.Map<Long, DataPoint> diastolicByTime = new java.util.HashMap<>();

        for (DataPoint point : systolicData) {
            systolicByTime.put(point.time, point);
        }
        for (DataPoint point : diastolicData) {
            diastolicByTime.put(point.time, point);
        }

        for (Long time : systolicByTime.keySet()) {
            if (diastolicByTime.containsKey(time)) {
                DataPoint systolicPoint = systolicByTime.get(time);
                DataPoint diastolicPoint = diastolicByTime.get(time);

                int x = startX + (int) ((time * width) / 12.0);
                int ySystolic = startY + height - (int) (((systolicPoint.value - 60) * height) / 140.0);
                int yDiastolic = startY + height - (int) (((diastolicPoint.value - 60) * height) / 140.0);

                g2d.drawLine(x, ySystolic, x, yDiastolic);
            }
        }
        // Draw pulse dengan dots
        g2d.setColor(new Color(255, 87, 34));
        for (DataPoint point : pulseData) {
            int x = startX + (int) ((point.time * width) / 12.0);
            int y = startY + height - (int) (((point.value - 60) * height) / 140.0);
            g2d.fillOval(x - 2, y - 2, 4, 4);
        }
    }

    private void drawOverallLegend(Graphics2D g2d, int x, int y) {
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("KETERANGAN SIMBOL PARTOGRAF WHO:", x, y);

        y += 20;
        g2d.setFont(new Font("Arial", Font.PLAIN, 11));

        // Cervix legend dengan X
        g2d.setColor(new Color(33, 150, 243));
        g2d.setStroke(new BasicStroke(2));
        int size = 4;
        g2d.drawLine(x - size, y - size, x + size, y + size);
        g2d.drawLine(x - size, y + size, x + size, y - size);
        g2d.setColor(Color.BLACK);
        g2d.drawString("X = Pembukaan Serviks", x + 15, y + 4); // Hapus "(cm)"

        // Descent dengan O
        g2d.setColor(new Color(76, 175, 80));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x + 150 - 5, y - 5, 10, 10);
        g2d.setColor(Color.BLACK);
        g2d.drawString("O = Penurunan Kepala", x + 165, y + 4);
        // TD dengan garis vertikal
        y += 20;
        g2d.setColor(new Color(233, 30, 99));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x, y - 6, x, y + 6);

        int[] xPointsSys = {x, x - 3, x + 3};
        int[] yPointsSys = {y - 6, y - 3, y - 3};
        g2d.fillPolygon(xPointsSys, yPointsSys, 3);

        int[] xPointsDias = {x, x - 3, x + 3};
        int[] yPointsDias = {y + 6, y + 3, y + 3};
        g2d.fillPolygon(xPointsDias, yPointsDias, 3);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Tekanan Darah: Garis Vertikal Sistol-Diastol", x + 15, y + 4);

        // Nadi dengan dot
        g2d.setColor(new Color(255, 87, 34));
        g2d.fillOval(x + 300, y - 4, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.drawString("● = Nadi", x + 315, y + 4);

        // WHO Lines
        y += 20;
        g2d.setColor(new Color(255, 152, 0));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(x, y, x + 20, y);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawString("Alert Line WHO", x + 25, y + 4);

        g2d.setColor(new Color(244, 67, 54));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(x + 150, y, x + 170, y);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawString("Action Line WHO", x + 175, y + 4);
    }

    
    // Method export as image
    private void exportAsImage() {
        if (cervixData.isEmpty() && djjData.isEmpty() && systolicData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Grafik sebagai Gambar");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        fileChooser.setSelectedFile(new File("Partograf_" + noRawat + "_"
                + new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                int selectedTab = TabGrafik.getSelectedIndex();
                javax.swing.JPanel currentCanvas = null;

                switch (selectedTab) {
                    case 0:
                        currentCanvas = canvasCervix;
                        break;
                    case 1:
                        currentCanvas = canvasDJJ;
                        break;
                    case 2:
                        currentCanvas = canvasVital;
                        break;
                    case 3:
                        currentCanvas = canvasGabungan;
                        break;
                }

                if (currentCanvas != null) {
                    BufferedImage image = new BufferedImage(
                            currentCanvas.getWidth(),
                            currentCanvas.getHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
                    currentCanvas.paint(g2d);
                    g2d.dispose();

                    ImageIO.write(image, "png", fileChooser.getSelectedFile());
                    JOptionPane.showMessageDialog(this,
                            "Grafik berhasil disimpan: " + fileChooser.getSelectedFile().getAbsolutePath());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error export gambar: " + e.getMessage());
            }
        }
    }

    // Method export to CSV - DIPERBAIKI untuk tensi gabungan
    private void exportToCSV() {
        if (cervixData.isEmpty() && djjData.isEmpty() && systolicData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Data ke CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        fileChooser.setSelectedFile(new File("Partograf_Data_" + noRawat + "_"
                + new java.text.SimpleDateFormat("yyyyMMdd").format(new Date()) + ".csv"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try ( PrintWriter writer = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()))) {

                // Header CSV - disesuaikan dengan field database
                writer.println("No_Rawat,Nama_Pasien,Jam_ke,Pembukaan_cm,Penurunan_0to5,DJJ_bpm,"
                        + "Tensi_mmHg,TD_Sistol,TD_Diastol,Nadi_bpm");

                // Get all unique time points
                java.util.Set<Long> allTimes = new java.util.TreeSet<>();
                cervixData.forEach(d -> allTimes.add(d.time));
                descentData.forEach(d -> allTimes.add(d.time));
                djjData.forEach(d -> allTimes.add(d.time));
                systolicData.forEach(d -> allTimes.add(d.time));

                // Write data for each time point
                for (Long time : allTimes) {
                    String cervixVal = findValueAtTime(cervixData, time);
                    String descentVal = findValueAtTime(descentData, time);
                    String djjVal = findValueAtTime(djjData, time);
                    String systolicVal = findValueAtTime(systolicData, time);
                    String diastolicVal = findValueAtTime(diastolicData, time);
                    String pulseVal = findValueAtTime(pulseData, time);

                    // Format tensi gabungan
                    String tensiGabungan = "";
                    if (!systolicVal.isEmpty() && !diastolicVal.isEmpty()) {
                        tensiGabungan = systolicVal + "/" + diastolicVal;
                    }

                    writer.printf("%s,%s,%d,%s,%s,%s,%s,%s,%s,%s%n",
                            noRawat, namaPasien, time, cervixVal, descentVal, djjVal,
                            tensiGabungan, systolicVal, diastolicVal, pulseVal);
                }

                JOptionPane.showMessageDialog(this,
                        "Data berhasil diekspor ke: " + fileChooser.getSelectedFile().getAbsolutePath());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error export CSV: " + e.getMessage());
            }
        }
    }

    // Helper method untuk find value at specific time
    private String findValueAtTime(List<DataPoint> dataList, Long targetTime) {
        for (DataPoint point : dataList) {
            if (point.time == targetTime) {
                if (point.type.equals("diastolic") || point.type.equals("pulse") || point.type.equals("systolic")) {
                    return String.format("%.0f", point.value);
                } else {
                    return String.format("%.1f", point.value);
                }
            }
        }
        return "";
    }

    // Method print chart
    private void printChart() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                    throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Scale to fit page
                double scaleX = pageFormat.getImageableWidth() / 900;
                double scaleY = pageFormat.getImageableHeight() / 700;
                double scale = Math.min(scaleX, scaleY);
                g2d.scale(scale, scale);

                // Print header
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.drawString("PARTOGRAF WHO - " + namaPasien, 20, 20);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                g2d.drawString("No. Rawat: " + noRawat + " | Tanggal: "
                        + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()), 20, 40);

                // Print selected chart
                int selectedTab = TabGrafik.getSelectedIndex();
                g2d.translate(0, 60);

                switch (selectedTab) {
                    case 0:
                        canvasCervix.paint(g2d);
                        break;
                    case 1:
                        canvasDJJ.paint(g2d);
                        break;
                    case 2:
                        canvasVital.paint(g2d);
                        break;
                    case 3:
                        // For combined chart, scale down more
                        g2d.scale(0.8, 0.8);
                        canvasGabungan.paint(g2d);
                        break;
                }

                return PAGE_EXISTS;
            }
        });

        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "Error printing: " + e.getMessage());
            }
        }
    }

    private void checkProgressAgainstWHOLines() {
        if (cervixData.isEmpty()) {
            return;
        }

        StringBuilder alerts = new StringBuilder();
        boolean hasAlert = false;

        // Tentukan waktu mulai fase aktif (biasanya pembukaan 4 cm)
        DataPoint phaseStart = null;
        for (DataPoint point : cervixData) {
            if (point.value >= 4.0) {
                phaseStart = point;
                break;
            }
        }

        if (phaseStart == null) {
            return; // Belum masuk fase aktif
        }
        // Check setiap data point terhadap garis WHO
        for (DataPoint point : cervixData) {
            if (point.time >= phaseStart.time) {
                double hoursInActivePhase = point.time - phaseStart.time;

                // Hitung posisi pada Alert Line dan Action Line
                // Alert Line: 4 cm + (1.5 cm/jam * waktu)
                double alertLineValue = 4.0 + (1.5 * hoursInActivePhase);

                // Action Line: 4 jam lebih lambat dari Alert Line
                double actionLineValue = 4.0 + (1.5 * Math.max(0, hoursInActivePhase - 4.0));

                // Check posisi pembukaan terhadap garis
                if (hoursInActivePhase >= 4.0 && point.value <= actionLineValue) {
                    alerts.append("⛔ MELEWATI ACTION LINE: Pembukaan ")
                            .append(String.format("%.1f", point.value))
                            .append(" cm pada jam ke-").append(String.format("%.1f", hoursInActivePhase))
                            .append(" fase aktif. TINDAKAN MEDIS SEGERA!\n");
                    hasAlert = true;
                } else if (point.value <= alertLineValue && hoursInActivePhase > 0) {
                    alerts.append("⚠️ MELEWATI ALERT LINE: Pembukaan ")
                            .append(String.format("%.1f", point.value))
                            .append(" cm pada jam ke-").append(String.format("%.1f", hoursInActivePhase))
                            .append(" fase aktif. MONITOR KETAT!\n");
                    hasAlert = true;
                }
            }
        }

    /*    if (hasAlert) {
            JOptionPane.showMessageDialog(this,
                    "🚨 ALERT PARTOGRAF WHO:\n\n" + alerts.toString()
                    + "\nInterpretasi WHO:\n"
                    + "• Alert Line = Monitor lebih ketat, siapkan intervensi\n"
                    + "• Action Line = Lakukan tindakan medis (augmentasi/SC)\n"
                    + "• Konsultasi SpOG segera bila melewati Action Line!",
                    "Alert WHO Partograf",
                    JOptionPane.WARNING_MESSAGE);
        } */
    }

    private void showSummary() {
        if (cervixData.isEmpty() && djjData.isEmpty() && systolicData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada data untuk dianalisis!");
            return;
        }

        StringBuilder summary = new StringBuilder();
        summary.append("=== RINGKASAN PARTOGRAF WHO ===\n\n");

        // Info pasien
        summary.append("Pasien: ").append(namaPasien).append("\n");
        summary.append("No. Rawat: ").append(noRawat).append("\n");
        summary.append("Petugas: ").append(petugasPenolong).append("\n");
        summary.append("Tanggal Analisis: ").append(
                new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date())).append("\n\n");

        // Analisis pembukaan serviks
        if (!cervixData.isEmpty()) {
            DataPoint first = cervixData.get(0);
            DataPoint last = cervixData.get(cervixData.size() - 1);
            double progress = last.value - first.value;
            double duration = last.time - first.time;
            double rate = duration > 0 ? progress / duration : 0;

            summary.append("PEMBUKAAN SERVIKS:\n");
            summary.append("• Observasi: ").append(cervixData.size()).append(" kali\n");
            summary.append("• Awal: ").append(String.format("%.1f", first.value)).append(" cm\n");
            summary.append("• Akhir: ").append(String.format("%.1f", last.value)).append(" cm\n");
            summary.append("• Kemajuan: ").append(String.format("%.1f", progress)).append(" cm\n");
            summary.append("• Durasi: ").append(String.format("%.1f", duration)).append(" jam\n");
            summary.append("• Kecepatan: ").append(String.format("%.2f", rate)).append(" cm/jam\n");

            if (rate < 1.0 && duration > 4) {
                summary.append("• Status: ⚠️ LAMBAT (< 1 cm/jam)\n");
            } else if (rate >= 1.0 && rate <= 2.0) {
                summary.append("• Status: ✓ NORMAL (1-2 cm/jam)\n");
            } else if (rate > 2.0) {
                summary.append("• Status: ⚡ CEPAT (> 2 cm/jam)\n");
            }
            summary.append("\n");
        }

        // Analisis DJJ
        if (!djjData.isEmpty()) {
            double totalDJJ = 0;
            int normalCount = 0;
            int abnormalCount = 0;
            double minDJJ = Double.MAX_VALUE;
            double maxDJJ = Double.MIN_VALUE;

            for (DataPoint point : djjData) {
                totalDJJ += point.value;
                if (point.value >= 110 && point.value <= 160) {
                    normalCount++;
                } else {
                    abnormalCount++;
                }
                minDJJ = Math.min(minDJJ, point.value);
                maxDJJ = Math.max(maxDJJ, point.value);
            }

            double avgDJJ = totalDJJ / djjData.size();

            summary.append("DENYUT JANTUNG JANIN:\n");
            summary.append("• Observasi: ").append(djjData.size()).append(" kali\n");
            summary.append("• Rata-rata: ").append(String.format("%.0f", avgDJJ)).append(" bpm\n");
            summary.append("• Rentang: ").append(String.format("%.0f", minDJJ)).append(" - ")
                    .append(String.format("%.0f", maxDJJ)).append(" bpm\n");
            summary.append("• Normal (110-160): ").append(normalCount).append(" observasi\n");
            summary.append("• Abnormal: ").append(abnormalCount).append(" observasi\n");

            if (abnormalCount == 0) {
                summary.append("• Status: ✓ NORMAL sepanjang persalinan\n");
            } else {
                double abnormalPercentage = (abnormalCount * 100.0) / djjData.size();
                summary.append("• Status: ");
                if (abnormalPercentage > 30) {
                    summary.append("🚨 GAWAT (").append(String.format("%.1f", abnormalPercentage)).append("% abnormal)\n");
                } else if (abnormalPercentage > 10) {
                    summary.append("⚠️ WASPADA (").append(String.format("%.1f", abnormalPercentage)).append("% abnormal)\n");
                } else {
                    summary.append("✓ ACCEPTABLE (").append(String.format("%.1f", abnormalPercentage)).append("% abnormal)\n");
                }
            }
            summary.append("\n");
        }

        // Analisis vital signs maternal - DIPERBAIKI untuk tensi gabungan
        if (!systolicData.isEmpty() && !diastolicData.isEmpty()) {
            double avgSystolic = systolicData.stream().mapToDouble(p -> p.value).average().orElse(0);
            double avgDiastolic = diastolicData.stream().mapToDouble(p -> p.value).average().orElse(0);
            double avgPulse = pulseData.stream().mapToDouble(p -> p.value).average().orElse(0);

            summary.append("VITAL SIGNS MATERNAL:\n");
            summary.append("• TD rata-rata: ").append(String.format("%.0f", avgSystolic))
                    .append("/").append(String.format("%.0f", avgDiastolic)).append(" mmHg\n");
            if (!pulseData.isEmpty()) {
                summary.append("• Nadi rata-rata: ").append(String.format("%.0f", avgPulse)).append(" bpm\n");
            }

            // Analisis tensi berdasarkan field gabungan
            boolean hypertension = avgSystolic >= 140 || avgDiastolic >= 90;
            boolean severeHypertension = avgSystolic >= 160 || avgDiastolic >= 110;

            summary.append("• Status: ");
            if (severeHypertension) {
                summary.append("🚨 HIPERTENSI BERAT - Segera konsultasi!\n");
            } else if (hypertension) {
                summary.append("⚠️ HIPERTENSI - Monitor ketat\n");
            } else {
                summary.append("✓ NORMAL\n");
            }

            // Tambahan analisis per observasi
            int hyperCount = 0;
            for (int i = 0; i < systolicData.size(); i++) {
                double sys = systolicData.get(i).value;
                double dias = diastolicData.get(i).value;
                if (sys >= 140 || dias >= 90) {
                    hyperCount++;
                }
            }

            if (hyperCount > 0) {
                double hyperPercentage = (hyperCount * 100.0) / systolicData.size();
                summary.append("• Hipertensi: ").append(hyperCount).append(" dari ")
                        .append(systolicData.size()).append(" observasi (")
                        .append(String.format("%.1f", hyperPercentage)).append("%)\n");
            }
            summary.append("\n");
        }

        // Emergency alert berdasarkan tensi gabungan
        boolean hasEmergencyTensi = false;
        for (DataPoint point : systolicData) {
            if (point.value >= 160) {
                hasEmergencyTensi = true;
                break;
            }
        }

        // Rekomendasi
        summary.append("REKOMENDASI:\n");
        if (hasEmergencyTensi) {
            summary.append("• 🚨 SEGERA konsultasi SpOG (hipertensi berat terdeteksi)\n");
        }
        if (!cervixData.isEmpty()) {
            DataPoint lastCervix = cervixData.get(cervixData.size() - 1);
            if (lastCervix.time > 12) {
                summary.append("• 🚨 SEGERA konsultasi SpOG (persalinan >12 jam)\n");
            } else if (lastCervix.time > 8) {
                summary.append("• ⚠️ Monitor ketat kemajuan persalinan\n");
            }
        }

        summary.append("• 📊 Update data setiap 30 menit untuk Kala I-II\n");
        summary.append("• 📋 Dokumentasi lengkap sesuai standar WHO\n");
        summary.append("• 🏥 Siapkan emergency kit bila diperlukan\n");
        summary.append("• 🩺 Monitor tekanan darah ketat (field gabungan: sistol/diastol)\n");

        // Tampilkan summary
        JTextArea textArea = new JTextArea(summary.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Ringkasan Analisis Partograf WHO", JOptionPane.INFORMATION_MESSAGE);
    }

// Method untuk check emergency conditions - DIPERBAIKI untuk tensi gabungan  
    private void checkEmergencyConditions() {
        StringBuilder alerts = new StringBuilder();
        boolean hasEmergency = false;

        // Check prolonged labor
        if (!cervixData.isEmpty()) {
            DataPoint lastCervix = cervixData.get(cervixData.size() - 1);
            if (lastCervix.time > 12) {
                alerts.append("• PERSALINAN LAMA: >12 jam - Konsultasi SpOG segera!\n");
                hasEmergency = true;
            }

            // Check if progress crosses action line
            for (DataPoint point : cervixData) {
                if (point.time >= 4) {
                    double expectedMin = 4 + ((point.time - 4) * 4 / 8);
                    if (point.value < expectedMin - 1) {
                        alerts.append("• KEMAJUAN LAMBAT: Pembukaan di bawah Action Line!\n");
                        hasEmergency = true;
                        break;
                    }
                }
            }
        }

        // Check fetal distress
        if (!djjData.isEmpty()) {
            int abnormalCount = 0;
            for (DataPoint point : djjData) {
                if (point.value < 110 || point.value > 170) {
                    abnormalCount++;
                }
            }

            double abnormalPercentage = (abnormalCount * 100.0) / djjData.size();
            if (abnormalPercentage > 30) {
                alerts.append("• GAWAT JANIN: >30% observasi DJJ abnormal!\n");
                hasEmergency = true;
            }
        }

        // Check maternal vital signs - DIPERBAIKI untuk sistol dari field gabungan
        for (DataPoint point : systolicData) {
            if (point.value >= 160) {
                alerts.append("• HIPERTENSI BERAT: TD Sistol ≥160 mmHg - Risk eklampsia!\n");
                hasEmergency = true;
                break;
            }
        }

        // Check diastol juga
        for (DataPoint point : diastolicData) {
            if (point.value >= 110) {
                alerts.append("• HIPERTENSI BERAT: TD Diastol ≥110 mmHg - Risk eklampsia!\n");
                hasEmergency = true;
                break;
            }
        }

    /*    if (hasEmergency) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ PERINGATAN KLINIS TERDETEKSI:\n\n" + alerts.toString()
                    + "\nSEGERA LAKUKAN EVALUASI MEDIS!",
                    "Alert Klinis",
                    JOptionPane.WARNING_MESSAGE);
        } */
    }

    private void testParseTensi() {
        String[] testCases = {
            "120/80", "140 / 90", "160-100", "180 120", "12080",
            "120mmHg/80mmHg", "150/90mmHg", "110 70", "invalid"
        };

        System.out.println("Testing tensi parsing:");
        for (String test : testCases) {
            String[] result = parseTensi(test);
            if (result != null) {
                System.out.println(test + " -> " + result[0] + "/" + result[1]);
            } else {
                System.out.println(test + " -> Failed to parse");
            }
        }
    }

    @Override
    public void dispose() {
        // Cleanup resources
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            System.out.println("Error during disposal: " + e);
        }

        super.dispose();
    }

    private String calculateExpectedProgress(double currentCervix, double hoursInActivePhase) {
        if (hoursInActivePhase <= 0) {
            return "Belum memasuki fase aktif";
        }

        // Expected progress berdasarkan WHO (minimum 1 cm/jam)
        double expectedMinimum = 4.0 + hoursInActivePhase;

        // Alert line progress (1.5 cm/jam)
        double alertLineProgress = 4.0 + (1.5 * hoursInActivePhase);

        String status;
        if (currentCervix >= expectedMinimum) {
            if (currentCervix >= alertLineProgress) {
                status = "✓ SANGAT BAIK (di atas Alert Line)";
            } else {
                status = "✓ NORMAL (sesuai minimum WHO)";
            }
        } else {
            double deficit = expectedMinimum - currentCervix;
            if (deficit <= 1.0) {
                status = "⚠️ SEDIKIT LAMBAT (deficit " + String.format("%.1f", deficit) + " cm)";
            } else {
                status = "🚨 SANGAT LAMBAT (deficit " + String.format("%.1f", deficit) + " cm)";
            }
        }

        return String.format("Expected: %.1f cm | Actual: %.1f cm | Status: %s",
                expectedMinimum, currentCervix, status);
    }

    private void paintCervixChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = canvasCervix.getWidth() - 80;
        int height = canvasCervix.getHeight() - 80;
        int startX = 50;
        int startY = 30;

        if (width <= 0 || height <= 0) {
            return;
        }

        // Clear background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, canvasCervix.getWidth(), canvasCervix.getHeight());

        // Draw grid first
        drawGrid(g2d, width, height, startX, startY, "cervix");

        // Draw WHO lines - HARUS SEBELUM plotting data
        drawWHOLinesCorrect(g2d, width, height, startX, startY);

        // Draw cervix data dengan aturan WHO yang BENAR
        if (!cervixData.isEmpty()) {
            g2d.setColor(new Color(33, 150, 243));
            g2d.setStroke(new BasicStroke(2));

            // Plot SEMUA titik termasuk observasi pertama
            for (int i = 0; i < cervixData.size(); i++) {
                DataPoint point = cervixData.get(i);

                int x, y;
                if (i == 0) {
                    // Observasi pertama: Plot di jam 0 pada Alert Line sesuai pembukaan
                    x = startX;
                    y = startY + height - (int) ((point.value * height) / 10.0);
                } else {
                    // Observasi selanjutnya: Plot sesuai waktu actual
                    x = startX + (int) ((point.time * width) / 12.0);
                    y = startY + height - (int) ((point.value * height) / 10.0);
                }

                // Gambar simbol X
                g2d.setStroke(new BasicStroke(3));
                int size = 6;
                g2d.drawLine(x - size, y - size, x + size, y + size);
                g2d.drawLine(x - size, y + size, x + size, y - size);
            }

            // Hubungkan titik dengan garis
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < cervixData.size() - 1; i++) {
                DataPoint p1 = cervixData.get(i);
                DataPoint p2 = cervixData.get(i + 1);

                int x1, y1, x2, y2;

                if (i == 0) {
                    // Dari observasi pertama di Alert Line
                    x1 = startX;
                    y1 = startY + height - (int) ((p1.value * height) / 10.0);
                } else {
                    x1 = startX + (int) ((p1.time * width) / 12.0);
                    y1 = startY + height - (int) ((p1.value * height) / 10.0);
                }

                x2 = startX + (int) ((p2.time * width) / 12.0);
                y2 = startY + height - (int) ((p2.value * height) / 10.0);

                g2d.drawLine(x1, y1, x2, y2);
            }
        }

        /// Draw descent data - langsung gunakan nilai database tanpa konversi
        if (!descentData.isEmpty()) {
            g2d.setColor(new Color(76, 175, 80));
            g2d.setStroke(new BasicStroke(2));

            // Hubungkan dengan garis - gunakan skala yang sama dengan cervix (0-10)
            for (int i = 0; i < descentData.size() - 1; i++) {
                DataPoint p1 = descentData.get(i);
                DataPoint p2 = descentData.get(i + 1);

                int x1 = startX + (int) ((p1.time * width) / 12.0);
                // Langsung plot nilai database pada skala 0-10 (sama dengan cervix)
                int y1 = startY + height - (int) ((p1.value * height) / 10.0);
                int x2 = startX + (int) ((p2.time * width) / 12.0);
                int y2 = startY + height - (int) ((p2.value * height) / 10.0);

                g2d.drawLine(x1, y1, x2, y2);
            }

            // Gambar simbol O
            for (DataPoint point : descentData) {
                int x = startX + (int) ((point.time * width) / 12.0);
                int y = startY + height - (int) ((point.value * height) / 10.0);
                int diameter = 12;
                g2d.drawOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
            }
        }

        // Draw legend
        drawCervixLegendFixed(g2d, startX, startY + height + 20);
    }

    private void drawWHOLinesCorrect(Graphics2D g2d, int width, int height, int startX, int startY) {
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));

        if (!cervixData.isEmpty()) {
            DataPoint firstPoint = cervixData.get(0);
            double firstOpening = firstPoint.value;

            // ALERT LINE: Dari pembukaan pertama ke 10cm dalam 4 jam
            g2d.setColor(new Color(255, 152, 0));
            int alertStartX = startX;
            int alertStartY = startY + height - (int) ((firstOpening * height) / 10.0);
            int alertEndX = startX + (int) ((4.0 * width) / 12.0); // 4 jam
            int alertEndY = startY + height - (int) ((10.0 * height) / 10.0); // 10cm

            g2d.drawLine(alertStartX, alertStartY, alertEndX, alertEndY);

            // ACTION LINE: Parallel 4 jam di sebelah kanan Alert Line
            g2d.setColor(new Color(244, 67, 54));
            int actionStartX = startX + (int) ((4.0 * width) / 12.0);
            int actionStartY = startY + height - (int) ((firstOpening * height) / 10.0);
            int actionEndX = startX + (int) ((8.0 * width) / 12.0); // 8 jam
            int actionEndY = startY + height - (int) ((10.0 * height) / 10.0);

            g2d.drawLine(actionStartX, actionStartY, actionEndX, actionEndY);

            // Labels
            g2d.setFont(new Font("Arial", Font.BOLD, 9));
            g2d.setColor(new Color(255, 152, 0));
            g2d.drawString("ALERT", alertStartX + 10, alertStartY - 5);

            g2d.setColor(new Color(244, 67, 54));
            g2d.drawString("ACTION", actionStartX + 10, actionStartY - 5);

        }

        g2d.setStroke(new BasicStroke(1));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            GrafikPartografWHO dialog = new GrafikPartografWHO(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration
    private widget.Button BtnExportCSV;
    private widget.Button BtnExportImage;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefresh;
    private widget.Button BtnSummary;
    private javax.swing.JMenuItem MenuExportCSV;
    private javax.swing.JMenuItem MenuExportImage;
    private javax.swing.JMenuItem MenuPrint;
    private javax.swing.JMenuItem MenuRefresh;
    private javax.swing.JMenuItem MenuSummary;
    private javax.swing.JTabbedPane TabGrafik;
    private javax.swing.JPanel canvasCervix;
    private javax.swing.JPanel canvasDJJ;
    private javax.swing.JPanel canvasGabungan;
    private javax.swing.JPanel canvasVital;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel1;
    private javax.swing.JPopupMenu jPopupMenuGrafik;
    private widget.Label lblInfoPasien;
    private javax.swing.JPanel panelCervix;
    private javax.swing.JPanel panelDJJ;
    private javax.swing.JPanel panelGabungan;
    private widget.panelisi panelHeader;
    private widget.panelisi panelKontrol;
    private javax.swing.JPanel panelVital;

}
