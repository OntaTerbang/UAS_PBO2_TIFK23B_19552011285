
package com.mycompany.kasirpropertijavafxfadillahfajri;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class KelolaPenyewaController {
    @FXML private TextField namaField;
    @FXML private TextField noHpField;
    @FXML private TextArea alamatField;
    @FXML private Button simpanButton;

    private int userId;

    @FXML
    private void initialize() {
        userId = UserSession.getUser().getId();
        Penyewa dataDiri = PenyewaDAO.getByUserId(userId);
        if (dataDiri != null) {
            namaField.setText(dataDiri.getNama());
            noHpField.setText(dataDiri.getNoHp());
            alamatField.setText(dataDiri.getAlamat());
        }
    }

    @FXML
    private void handleSimpan() {
        String nama = namaField.getText();
        String noHp = noHpField.getText();
        String alamat = alamatField.getText();
        if (nama.isEmpty() || noHp.isEmpty() || alamat.isEmpty()) {
            showAlert("Error", "Semua field harus diisi!");
            return;
        }
        Penyewa dataDiri = new Penyewa(nama, noHp, alamat, userId);
        if (PenyewaDAO.getByUserId(userId) == null) {
            if (PenyewaDAO.insertPenyewa(dataDiri)) {
                showAlert("Sukses", "Data diri berhasil disimpan!");
            } else {
                showAlert("Gagal", "Gagal menyimpan data diri!");
            }
        } else {
            if (PenyewaDAO.updatePenyewa(dataDiri)) {
                showAlert("Sukses", "Data diri berhasil diupdate!");
            } else {
                showAlert("Gagal", "Gagal mengupdate data diri!");
            }
        }
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