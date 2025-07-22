/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import com.mycompany.kasirpropertijavafxfadillahfajri.Properti;
import com.mycompany.kasirpropertijavafxfadillahfajri.Penyewa;
import com.mycompany.kasirpropertijavafxfadillahfajri.Transaksi;
import com.mycompany.kasirpropertijavafxfadillahfajri.PropertiDAO;
import com.mycompany.kasirpropertijavafxfadillahfajri.PenyewaDAO;
import com.mycompany.kasirpropertijavafxfadillahfajri.TransaksiDAO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KelolaTransaksiController {
    @FXML private ComboBox<String> jenisCombo;
    @FXML private TableView<Properti> propertiTable;
    @FXML private TableColumn<Properti, Integer> colId, colJumlahKamar;
    @FXML private TableColumn<Properti, String> colJenis, colAlamat, colStatus;
    @FXML private TableColumn<Properti, Double> colHargaSewa, colPajak;
    @FXML private Label penyewaLabel, totalLabel;
    @FXML private TextField lamaTinggalField;
    @FXML private Button tambahButton, hapusButton, resetButton, kembaliButton;
    @FXML private TableView<Transaksi> transaksiTable;
    @FXML private TableColumn<Transaksi, Integer> colTransId, colTransLama;
    @FXML private TableColumn<Transaksi, String> colTransProperti, colTransAlamat, colTransPenyewa;
    @FXML private TableColumn<Transaksi, LocalDate> colTransTanggal;
    @FXML private TableColumn<Transaksi, Double> colTransTotal;
    @FXML private TextField penyewaField, totalField;

    private Properti selectedProperti;
    private Transaksi selectedTransaksi;

    @FXML
    private void initialize() {
        jenisCombo.setItems(FXCollections.observableArrayList("Rumah", "Apartemen"));
        jenisCombo.valueProperty().addListener((obs, oldVal, newVal) -> refreshPropertiTable());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colJumlahKamar.setCellValueFactory(new PropertyValueFactory<>("jumlahKamar"));
        colHargaSewa.setCellValueFactory(new PropertyValueFactory<>("hargaSewa"));
        colPajak.setCellValueFactory(new PropertyValueFactory<>("pajak"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        propertiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedProperti = newSel;
            updateTotal();
        });
        lamaTinggalField.textProperty().addListener((obs, oldVal, newVal) -> updateTotal());
        Penyewa dataDiri = PenyewaDAO.getByUserId(UserSession.getUser().getId());
        penyewaField.setText(dataDiri != null ? dataDiri.getNama() : "(isi data diri dulu)");
        penyewaField.setEditable(false);
        colTransId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTransProperti.setCellValueFactory(new PropertyValueFactory<>("properti"));
        colTransAlamat.setCellValueFactory(cellData -> {
            String propertiNama = cellData.getValue().getProperti();
            Properti properti = PropertiDAO.getAllProperti().stream()
                .filter(p -> p.getJenis().equals(propertiNama))
                .findFirst().orElse(null);
            return new javafx.beans.property.SimpleStringProperty(properti != null ? properti.getAlamat() : "-");
        });
        colTransPenyewa.setCellValueFactory(new PropertyValueFactory<>("penyewa"));
        colTransTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colTransLama.setCellValueFactory(new PropertyValueFactory<>("lamaTinggal"));
        colTransTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colTransTotal.setCellFactory(tc -> new TableCell<Transaksi, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    setText(nf.format(value));
                }
            }
        });
        transaksiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> selectedTransaksi = newSel);
        String role = UserSession.getRole();
        if (role.equals("member")) {
            hapusButton.setDisable(true);
            resetButton.setDisable(true);
            tambahButton.setDisable(false);
            kembaliButton.setDisable(false);
        }
        refreshPropertiTable();
        refreshTransaksiTable();
    }

    private void refreshPropertiTable() {
        String jenis = jenisCombo.getValue();
        if (jenis == null) {
            propertiTable.setItems(FXCollections.observableArrayList());
            return;
        }
        List<Properti> list = PropertiDAO.getAllProperti();
        propertiTable.setItems(FXCollections.observableArrayList(
            list.stream().filter(p -> jenis.equalsIgnoreCase(p.getJenis()) && "Tersedia".equalsIgnoreCase(p.getStatus())).collect(Collectors.toList())
        ));
    }
    private void refreshTransaksiTable() {
        transaksiTable.setItems(FXCollections.observableArrayList(TransaksiDAO.getAllTransaksi()));
    }
    private void updateTotal() {
        if (selectedProperti == null) { totalField.setText("0"); return; }
        try {
            int lama = Integer.parseInt(lamaTinggalField.getText());
            double harga = selectedProperti.getHargaSewa();
            double pajak = selectedProperti.getPajak();
            double total = (harga + pajak) * lama;
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            totalField.setText(nf.format(total));
        } catch (Exception e) {
            totalField.setText("0");
        }
    }
    @FXML
    private void handleTambah() {
        if (selectedProperti == null) { showAlert("Error", "Pilih properti terlebih dahulu!"); return; }
        Penyewa dataDiri = PenyewaDAO.getByUserId(UserSession.getUser().getId());
        if (dataDiri == null) { showAlert("Error", "Isi data diri terlebih dahulu di menu Data Diri!"); return; }
        int lama;
        try { lama = Integer.parseInt(lamaTinggalField.getText()); } catch (Exception e) { showAlert("Error", "Isi lama tinggal dengan benar!"); return; }
        double harga = selectedProperti.getHargaSewa();
        double pajak = selectedProperti.getPajak();
        double total = (harga + pajak) * lama;
        Transaksi t = new Transaksi(
            selectedProperti.getJenis(),
            dataDiri.getNama(),
            LocalDate.now(),
            lama,
            total,
            dataDiri.getUserId()
        );
        if (TransaksiDAO.insertTransaksi(t)) {
            showAlert("Sukses", "Transaksi berhasil ditambahkan!");
            refreshTransaksiTable();
            clearFields();
        } else {
            showAlert("Gagal", "Transaksi gagal ditambahkan!");
        }
    }
    @FXML
    private void handleHapus() {
        if (selectedTransaksi == null) return;
        TransaksiDAO.deleteTransaksi(selectedTransaksi.getId());
        refreshTransaksiTable();
        clearFields();
    }
    @FXML
    private void handleReset() { clearFields(); }
    @FXML
    private void handleCetak() {
        if (selectedTransaksi == null) {
            showAlert("Error", "Pilih transaksi yang ingin dicetak!");
            return;
        }
        Penyewa dataDiri = PenyewaDAO.getByUserId(UserSession.getUser().getId());
        if (dataDiri == null || selectedTransaksi.getUserId() != dataDiri.getUserId()) {
            showAlert("Error", "Anda hanya bisa mencetak transaksi milik Anda sendiri!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== BUKTI TRANSAKSI ===\n");
        sb.append("ID Transaksi: ").append(selectedTransaksi.getId()).append("\n");
        sb.append("Properti: ").append(selectedTransaksi.getProperti()).append("\n");
        sb.append("Penyewa: ").append(selectedTransaksi.getPenyewa()).append("\n");
        sb.append("Tanggal: ").append(selectedTransaksi.getTanggal()).append("\n");
        sb.append("Lama Tinggal: ").append(selectedTransaksi.getLamaTinggal()).append(" bulan\n");
        sb.append("Total: ").append(NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(selectedTransaksi.getTotal())).append("\n");
        String userHome = System.getProperty("user.home");
        Path folderPath = Paths.get(userHome, "Downloads", "BUKTI TRANSAKSI");
        try {
            Files.createDirectories(folderPath);
            String fileName = "bukti_transaksi_" + selectedTransaksi.getId() + ".txt";
            Path filePath = folderPath.resolve(fileName);
            try (FileWriter fw = new FileWriter(filePath.toFile())) {
                fw.write(sb.toString());
                showAlert("Sukses", "Bukti transaksi berhasil dicetak ke:\n" + filePath.toString());
            }
        } catch (IOException e) {
            showAlert("Gagal", "Gagal mencetak bukti transaksi! Pastikan folder tujuan bisa diakses.");
        }
    }
    private void clearFields() {
        propertiTable.getSelectionModel().clearSelection();
        selectedProperti = null;
        lamaTinggalField.clear();
        totalField.setText("0");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Sukses") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void goToDasbor() {
        Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/Dasbor.fxml");
    }
}
