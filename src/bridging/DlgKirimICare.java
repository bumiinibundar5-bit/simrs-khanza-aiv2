/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import fungsi.koneksiDB;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 *
 * @author puma
 */
public class DlgKirimICare extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DlgKirimICare.class.getName());
    private ArrayList<Object[]> listSEP = new ArrayList<>();

    private ApiICareBPJS api = new ApiICareBPJS();
    private String link = "", utc = "", requestJson = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private SwingWorker<Void, String> currentWorker;
    private volatile boolean isCancelled = false;

    // Persistent Storage
    private Set<String> processedDataKeys = ConcurrentHashMap.newKeySet();
    private final String PROCESSED_DATA_FILE = "icare_history.json";
    private final String PROCESSED_DATA_BACKUP_DIR = "backups";

    private int batchSaveCounter = 0;
    private final int BATCH_SAVE_INTERVAL = 10;

    /**
     * Creates new form DlgKirimICare
     *
     * @param parent
     * @param modal
     */
    public DlgKirimICare(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        try {
            link = koneksiDB.URLAPIICARE();
        } catch (Exception e) {
            System.out.println("E : " + e);
        }
    }

    public void setListSEP(ArrayList<Object[]> sep) {
        listSEP = sep;
    }

    private void saveProcessedDataToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("processedKeys", new ArrayList<>(processedDataKeys));
            dataToSave.put("lastUpdated", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            dataToSave.put("totalProcessed", processedDataKeys.size());

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(PROCESSED_DATA_FILE), dataToSave);

            System.out.println("Saved " + processedDataKeys.size() + " processed items to file");
        } catch (IOException e) {
            System.err.println("Error saving processed data to file: " + e.getMessage());
        }
    }

    private void loadProcessedDataFromFile() {
        try {
            File file = new File(PROCESSED_DATA_FILE);
            if (!file.exists()) {
                System.out.println("No processed data file found, starting fresh");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> loadedData = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
            });

            @SuppressWarnings("unchecked")
            List<String> keys = (List<String>) loadedData.get("processedKeys");

            if (keys != null) {
                processedDataKeys.clear();
                processedDataKeys.addAll(keys);
            }

            System.out.println("Loaded " + processedDataKeys.size() + " processed items from file");
            System.out.println("Last updated: " + loadedData.get("lastUpdated"));

        } catch (IOException e) {
            System.err.println("Error loading processed data from file: " + e.getMessage());

            createBackup();
        }
    }

    private String generateDataKey(Object[] data) {
        return data[0].toString() + "|" + data[1].toString() + "|" + data[2].toString();
    }

    private void createBackup() {
        try {

            Files.createDirectories(Paths.get(PROCESSED_DATA_BACKUP_DIR));

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String backupFileName = PROCESSED_DATA_BACKUP_DIR + "/icare_history_backup_" + timestamp + ".json";

            Files.copy(Paths.get(PROCESSED_DATA_FILE), Paths.get(backupFileName), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Backup created: " + backupFileName);
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    private void cleanOldBackups() {
        try {
            File backupDir = new File(PROCESSED_DATA_BACKUP_DIR);
            if (!backupDir.exists()) {
                return;
            }

            File[] backupFiles = backupDir.listFiles((dir, name) -> name.startsWith("icare_history_backup_"));

            if (backupFiles != null) {
                for (File backupFile : backupFiles) {
                    if (backupFile.delete()) {
                        System.out.println("Deleted old backup: " + backupFile.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error cleaning old backups: " + e.getMessage());
        }
    }

    private void markDataAsProcessedBatch(Object[] data) {
        String dataKey = generateDataKey(data);
        processedDataKeys.add(dataKey);

        batchSaveCounter++;

        if (batchSaveCounter >= BATCH_SAVE_INTERVAL) {
            saveProcessedDataToFile();
            batchSaveCounter = 0;
        }

        System.out.println("Marked as processed: " + dataKey);
    }

    private void finalizeProcessedData() {
        if (batchSaveCounter > 0) {
            saveProcessedDataToFile();
            batchSaveCounter = 0;
        }
        createBackup();
    }

    private boolean validateProcessedDataFile() {
        try {
            File file = new File(PROCESSED_DATA_FILE);
            if (!file.exists()) {
                return false;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
            });

            return data.containsKey("processedKeys")
                    && data.containsKey("lastUpdated")
                    && data.containsKey("totalProcessed");

        } catch (Exception e) {
            System.err.println("Validation failed: " + e.getMessage());
            return false;
        }
    }

    private void recoverFromBackup() {
        try {
            File backupDir = new File(PROCESSED_DATA_BACKUP_DIR);
            if (!backupDir.exists()) {
                System.out.println("No backup directory found");
                return;
            }

            File[] backupFiles = backupDir.listFiles((dir, name) -> name.startsWith("icare_history_backup_"));

            if (backupFiles != null && backupFiles.length > 0) {

                Arrays.sort(backupFiles, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
                File latestBackup = backupFiles[0];

                Files.copy(latestBackup.toPath(), Paths.get(PROCESSED_DATA_FILE), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Recovered from backup: " + latestBackup.getName());
                loadProcessedDataFromFile();
            }

        } catch (IOException e) {
            System.err.println("Error recovering from backup: " + e.getMessage());
        }
    }

    private void clearProcessedData() {
        processedDataKeys.clear();

        try {
            Files.deleteIfExists(Paths.get(PROCESSED_DATA_FILE));
            Files.deleteIfExists(Paths.get("icare_history.properties"));
            Files.deleteIfExists(Paths.get("icare_history.txt"));

            System.out.println("Cleared all processed data");
        } catch (IOException e) {
            System.err.println("Error clearing processed data files: " + e.getMessage());
        }
    }

    private void initializeStorage() {
        try {
            Files.createDirectories(Paths.get(PROCESSED_DATA_BACKUP_DIR));
        } catch (IOException e) {
            System.err.println("Error creating backup directory: " + e.getMessage());
        }

        if (validateProcessedDataFile()) {
            loadProcessedDataFromFile();
        } else {
            System.out.println("Data file invalid or empty, attempting recovery...");
            recoverFromBackup();

            if (!validateProcessedDataFile()) {
                createInitialDataFile();
            }
        }
    }

    private void createInitialDataFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> initialData = new HashMap<>();
            initialData.put("processedKeys", new ArrayList<String>());
            initialData.put("lastUpdated", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            initialData.put("totalProcessed", 0);

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(PROCESSED_DATA_FILE), initialData);

            System.out.println("Created initial data file: " + PROCESSED_DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error creating initial data file: " + e.getMessage());
        }
    }

    private void printStorageStatistics() {
        System.out.println("=== Storage Statistics ===");
        System.out.println("Processed items: " + processedDataKeys.size());

        File dataFile = new File(PROCESSED_DATA_FILE);
        if (dataFile.exists()) {
            System.out.println("Data file size: " + dataFile.length() + " bytes");
            System.out.println("Last modified: " + new Date(dataFile.lastModified()));
        }

        File backupDir = new File(PROCESSED_DATA_BACKUP_DIR);
        if (backupDir.exists()) {
            File[] backups = backupDir.listFiles();
            System.out.println("Backup files: " + (backups != null ? backups.length : 0));
        }

        System.out.println("==========================");
    }

    private void resetUIAfterCancel() {
        SwingUtilities.invokeLater(() -> {
            BtnKirim.setText("Kirim");
            BtnKirim.setEnabled(true);
            kirimProgressBar.setValue(0);
            labelLog.setText("Process cancelled by user");
        });
    }

    private void forceCancel() {
        if (currentWorker != null && !currentWorker.isDone()) {
            isCancelled = true;
            currentWorker.cancel(true);

            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                resetUIAfterCancel();
                currentWorker = null;
                isCancelled = false;
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        kirimProgressBar = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        labelLog = new javax.swing.JLabel();
        panelisi3 = new widget.panelisi();
        BtnKirim = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnClear = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 300));

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kirim Data ICare ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50)));
        internalFrame1.setPreferredSize(new java.awt.Dimension(500, 300));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 100));

        kirimProgressBar.setMaximumSize(new java.awt.Dimension(32767, 10));
        kirimProgressBar.setMinimumSize(new java.awt.Dimension(10, 10));
        kirimProgressBar.setPreferredSize(new java.awt.Dimension(100, 10));

        jLabel3.setText("Kirim:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(labelLog))
                    .addComponent(kirimProgressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelLog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kirimProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        panelisi3.setBorder(null);
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnKirim.setMnemonic('K');
        BtnKirim.setText("Kirim");
        BtnKirim.setToolTipText("");
        BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        BtnKirim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKirimKeyPressed(evt);
            }
        });
        panelisi3.add(BtnKirim);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Batal");
        BtnKeluar.setToolTipText("");
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
        panelisi3.add(BtnKeluar);

        BtnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnClear.setMnemonic('K');
        BtnClear.setText("Clear ");
        BtnClear.setToolTipText("");
        BtnClear.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClearActionPerformed(evt);
            }
        });
        BtnClear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnClearKeyPressed(evt);
            }
        });
        panelisi3.add(BtnClear);

        javax.swing.GroupLayout internalFrame1Layout = new javax.swing.GroupLayout(internalFrame1);
        internalFrame1.setLayout(internalFrame1Layout);
        internalFrame1Layout.setHorizontalGroup(
            internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, internalFrame1Layout.createSequentialGroup()
                .addComponent(panelisi3, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        internalFrame1Layout.setVerticalGroup(
            internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelisi3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleName("::[ Kirim Data ICare ]::");
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimActionPerformed

        BtnKirim.setEnabled(false);
        BtnClear.setEnabled(false);

        if (processedDataKeys.isEmpty()) {
            initializeStorage();
        }

        if (currentWorker != null && !currentWorker.isDone()) {

            isCancelled = true;
            currentWorker.cancel(true);
            resetUIAfterCancel();

            currentWorker = null;
            finalizeProcessedData();
            return;
        }

        isCancelled = false;
        BtnKirim.setText("Cancel");
        BtnKirim.setEnabled(true);

        currentWorker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    final int REQUESTS_PER_BATCH = 10;
                    final long BATCH_DELAY_MS = 60000;
                    final long REQUEST_DELAY_MS = 1000;
                    final int MAX_RETRIES = 3;
                    final long RETRY_DELAY_MS = 5000;

                    SwingUtilities.invokeLater(() -> {
                        kirimProgressBar.setValue(0);
                    });

                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Content-Type", "application/json");
                    headers.add("x-cons-id", koneksiDB.CONSIDAPIICARE());
                    utc = String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("x-timestamp", utc);
                    headers.add("x-signature", api.getHmac(utc));
                    headers.add("user_key", koneksiDB.USERKEYAPIICARE());

                    ObjectMapper mapper = new ObjectMapper();

                    // Filter out already processed data
                    List<Object[]> pendingData = new ArrayList<>();
                    for (Object[] data : listSEP) {
                        String dataKey = generateDataKey(data);
                        if (!processedDataKeys.contains(dataKey)) {
                            pendingData.add(data);
                        }
                    }

                    int totalRequests = pendingData.size();
                    int processedRequests = 0;
                    int skippedRequests = listSEP.size() - totalRequests;

                    if (skippedRequests > 0) {
                        publish("Skipped " + skippedRequests + " already processed items");
                        if (!safeSleep(2000)) {
                            publish("Process interrupted during initial delay");
                            finalizeProcessedData();
                            return null;
                        }
                    }

                    if (totalRequests == 0) {
                        publish("All data has been processed already");
                        return null;
                    }

                    for (int batchStart = 0; batchStart < totalRequests; batchStart += REQUESTS_PER_BATCH) {
                        if (isCancelled || Thread.currentThread().isInterrupted()) {
                            publish("Process cancelled - saving progress...");
                            finalizeProcessedData();
                            return null;
                        }

                        int batchEnd = Math.min(batchStart + REQUESTS_PER_BATCH, totalRequests);
                        final int currentBatch = (batchStart / REQUESTS_PER_BATCH) + 1;
                        final int totalBatches = (int) Math.ceil((double) totalRequests / REQUESTS_PER_BATCH);
                        publish("Batch " + currentBatch + " of " + totalBatches);

                        // Process each item in the batch
                        for (int idx = batchStart; idx < batchEnd; idx++) {
                            if (isCancelled || Thread.currentThread().isInterrupted()) {
                                publish("Process cancelled - saving progress...");
                                finalizeProcessedData();
                                return null;
                            }

                            Object[] data = pendingData.get(idx);

                            boolean success = false;
                            boolean shouldSkip = false;
                            int retryCount = 0;

                            while (!success && retryCount <= MAX_RETRIES && !shouldSkip) {
                                try {
                                    String logText = (idx + 1) + " : " + data[0].toString() + "/" + data[1].toString() + "/" + data[2].toString();
                                    if (retryCount > 0) {
                                        logText += " (Retry " + retryCount + "/" + MAX_RETRIES + ")";
                                    }
                                    publish(logText);

                                    requestJson = "{"
                                            + "\"param\": \"" + data[1].toString() + "\","
                                            + "\"kodedokter\": " + data[2].toString() + ""
                                            + "}";

                                    System.out.println("Processing index: " + idx + " JSON: " + requestJson);

                                    requestEntity = new HttpEntity(requestJson, headers);
                                    requestJson = mapper.writeValueAsString(
                                            api.getRest().exchange(link + "/validate", HttpMethod.POST, requestEntity, Object.class).getBody()
                                    );

                                    System.out.println("URL: " + link + "/validate");
                                    System.out.println("Response JSON: " + requestJson);

                                    root = mapper.readTree(requestJson);
                                    nameNode = root.path("metaData");

                                    if (nameNode.path("code").asText().equals("200")) {
                                        response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                                        System.out.println("Success - Response: " + response.path("url"));

                                        markDataAsProcessedBatch(data);
                                        success = true;

                                    } else {
                                        final String errorMessage = nameNode.path("message").asText();
                                        System.out.println("API Error: " + errorMessage);
                                        success = false;
                                    }

                                } catch (Exception ex) {
                                    retryCount++;

                                    if (ex instanceof InterruptedException) {
                                        Thread.currentThread().interrupt();
                                        publish("Process interrupted during API call");
                                        finalizeProcessedData();
                                        return null;
                                    }
                                    System.out.println("Exception occurred: " + ex.getMessage());

                                    if (ex.toString().contains("UnknownHostException")
                                            || ex.toString().contains("Connection reset")
                                            || ex.toString().contains("SSLException")
                                            || ex.toString().contains("ConnectException")
                                            || ex.toString().contains("SocketTimeoutException")) {

                                        if (retryCount <= MAX_RETRIES) {
                                            if (!showRetryCountdown(RETRY_DELAY_MS, retryCount, MAX_RETRIES)) {

                                                finalizeProcessedData();
                                                return null;
                                            }
                                        } else {
                                            markDataAsProcessedBatch(data);
                                            success = true;

                                            SwingUtilities.invokeLater(() -> {
                                                JOptionPane.showMessageDialog(rootPane,
                                                        "Connection failed after " + MAX_RETRIES + " attempts for: "
                                                        + data[0].toString() + "/" + data[1].toString() + "/" + data[2].toString()
                                                        + "\nThis item will be skipped.");
                                            });
                                        }
                                    } else {

                                        markDataAsProcessedBatch(data);
                                        success = true;

                                        SwingUtilities.invokeLater(() -> {
                                            JOptionPane.showMessageDialog(rootPane, "Error processing " + data[0] + ": " + ex.getMessage());
                                        });
                                    }
                                }
                            }

                            if (success) {
                                processedRequests++;

                                final int progress = (int) ((double) processedRequests / totalRequests * 100);
                                SwingUtilities.invokeLater(() -> {
                                    kirimProgressBar.setValue(progress);
                                });

                                if (!safeSleep(REQUEST_DELAY_MS)) {
                                    publish("Process interrupted during request delay");
                                    finalizeProcessedData();
                                    return null;
                                }
                            }

                            if (processedRequests % 5 == 0) {
                                finalizeProcessedData();
                            }
                        }

                        if (batchEnd < totalRequests) {
                            showBatchCountdown(BATCH_DELAY_MS, currentBatch, totalBatches);
                        }
                    }

                    finalizeProcessedData();

                } catch (InterruptedException e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(rootPane, "Unexpected error occurred: " + e.getMessage());
                    });

                    finalizeProcessedData();
                }

                return null;
            }

            private boolean showRetryCountdown(long delayMs, int currentRetry, int maxRetries) throws InterruptedException {
                long seconds = delayMs / 1000;
                for (long i = seconds; i > 0; i--) {
                    if (isCancelled || Thread.currentThread().isInterrupted() || this.isCancelled()) {
                        return false;
                    }
                    final long remainingSeconds = i;
                    publish("Retry " + currentRetry + "/" + maxRetries + " in " + remainingSeconds + " seconds...");
                    if (!safeSleep(1000)) {
                        publish("Retry countdown interrupted");
                        return false;
                    }

                }
                return true;
            }

            private boolean showBatchCountdown(long delayMs, int currentBatch, int totalBatches) throws InterruptedException {
                long seconds = delayMs / 1000;
                for (long i = seconds; i > 0; i--) {
                    if (isCancelled || Thread.currentThread().isInterrupted() || this.isCancelled()) {
                        publish("Batch countdown cancelled");
                        return false;
                    }

                    final long remainingSeconds = i;
                    final long minutes = remainingSeconds / 60;
                    final long secs = remainingSeconds % 60;
                    publish("Batch " + currentBatch + "/" + totalBatches + " completed. Next batch in "
                            + minutes + ":" + String.format("%02d", secs));
                    if (!safeSleep(1000)) {

                        publish("Batch countdown interrupted");
                        return false;
                    }

                }
                return true;
            }

            private boolean safeSleep(long milliseconds) {
                try {
                    Thread.sleep(milliseconds);
                    return true;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }

            @Override
            protected void process(List<String> chunks
            ) {
                if (!chunks.isEmpty()) {
                    labelLog.setText(chunks.get(chunks.size() - 1));
                }
            }

            @Override
            protected void done() {
                BtnKirim.setText("Kirim");
                BtnKirim.setEnabled(true);
                BtnClear.setEnabled(true);

                if (isCancelled) {
                    labelLog.setText("Process cancelled - progress saved");
                } else {
                    labelLog.setText("Processing completed successfully");
                }

                printStorageStatistics();
                currentWorker = null;
            }
        };

        currentWorker.execute();
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void BtnKirimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKirimKeyPressed

    }//GEN-LAST:event_BtnKirimKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        forceCancel();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClearActionPerformed
        clearProcessedData();
        cleanOldBackups();

    }//GEN-LAST:event_BtnClearActionPerformed

    private void BtnClearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnClearKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnClearKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DlgKirimICare dialog = new DlgKirimICare(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnClear;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirim;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar kirimProgressBar;
    private javax.swing.JLabel labelLog;
    private widget.panelisi panelisi3;
    // End of variables declaration//GEN-END:variables
}
