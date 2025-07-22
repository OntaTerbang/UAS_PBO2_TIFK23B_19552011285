/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import com.mycompany.kasirpropertijavafxfadillahfajri.Properti;
import com.mycompany.kasirpropertijavafxfadillahfajri.PropertiDAO;
import com.mycompany.kasirpropertijavafxfadillahfajri.UserSession;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author Fadil
 */
public class KelolaPropertiController {
    @FXML private ComboBox<String> jenisCombo;
    @FXML private TextField alamatField;
    @FXML private TextField jumlahKamarField;
    @FXML private TextField hargaSewaField;
    @FXML private TextField pajakField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Button tambahButton, editButton, hapusButton, resetButton;
    @FXML private TableView<Properti> propertiTable;
    @FXML private TableColumn<Properti, Integer> colId, colJumlahKamar;
    @FXML private TableColumn<Properti, String> colJenis, colAlamat, colStatus;
    @FXML private TableColumn<Properti, Double> colHargaSewa, colPajak;

    private Properti selected;

    @FXML
    private void initialize() {
        jenisCombo.setItems(FXCollections.observableArrayList("Rumah", "Apartemen"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colJumlahKamar.setCellValueFactory(new PropertyValueFactory<>("jumlahKamar"));
        colHargaSewa.setCellValueFactory(new PropertyValueFactory<>("hargaSewa"));
        colPajak.setCellValueFactory(new PropertyValueFactory<>("pajak"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCombo.setItems(FXCollections.observableArrayList("Tersedia", "Tidak Tersedia"));
        refreshTable();
        propertiTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selected = newSel;
            if (newSel != null) {
                jenisCombo.setValue(newSel.getJenis());
                alamatField.setText(newSel.getAlamat());
                jumlahKamarField.setText(String.valueOf(newSel.getJumlahKamar()));
                hargaSewaField.setText(String.valueOf(newSel.getHargaSewa()));
                pajakField.setText(String.valueOf(newSel.getPajak()));
                statusCombo.setValue(newSel.getStatus());
            }
        });
        pajakField.setEditable(false);
        pajakField.setDisable(true);
        hargaSewaField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                double harga = Double.parseDouble(newVal);
                pajakField.setText(String.valueOf(harga * 0.005));
            } catch (Exception e) {
                pajakField.setText("");
            }
        });
    }

    @FXML
    private void handleTambah() {
        if (!validateInput()) {
            showAlert("Input tidak valid!", "Semua field harus diisi dengan benar.");
            return;
        }
        Properti p = new Properti(
            jenisCombo.getValue(),
            alamatField.getText(),
            Integer.parseInt(jumlahKamarField.getText()),
            Double.parseDouble(hargaSewaField.getText()),
            Double.parseDouble(pajakField.getText()),
            statusCombo.getValue()
        );
        if (PropertiDAO.insertProperti(p)) {
            showAlert("Sukses", "Properti berhasil ditambahkan!");
            refreshTable();
            clearFields();
        } else {
            showAlert("Gagal", "Properti gagal ditambahkan!");
        }
    }

    @FXML
    private void handleEdit() {
        if (selected == null || !validateInput()) return;
        selected.setJenis(jenisCombo.getValue());
        selected.setAlamat(alamatField.getText());
        selected.setJumlahKamar(Integer.parseInt(jumlahKamarField.getText()));
        selected.setHargaSewa(Double.parseDouble(hargaSewaField.getText()));
        selected.setPajak(Double.parseDouble(pajakField.getText()));
        selected.setStatus(statusCombo.getValue());
        if (PropertiDAO.updateProperti(selected)) refreshTable();
        clearFields();
    }

    @FXML
    private void handleHapus() {
        if (selected == null) return;
        PropertiDAO.deleteProperti(selected.getId());
        refreshTable();
        clearFields();
    }

    @FXML
    private void handleReset() { clearFields(); }

    private void refreshTable() {
        propertiTable.setItems(FXCollections.observableArrayList(PropertiDAO.getAllProperti()));
    }
    private void clearFields() {
        jenisCombo.setValue(null); alamatField.clear(); jumlahKamarField.clear(); hargaSewaField.clear(); pajakField.clear(); statusCombo.setValue(null); selected = null;
    }
    private boolean validateInput() {
        return jenisCombo.getValue() != null && !alamatField.getText().isEmpty() && !jumlahKamarField.getText().isEmpty() && !hargaSewaField.getText().isEmpty() && !pajakField.getText().isEmpty() && statusCombo.getValue() != null;
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