/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import com.mycompany.kasirpropertijavafxfadillahfajri.User;
import com.mycompany.kasirpropertijavafxfadillahfajri.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
/**
 *
 * @author Fadil
 */
public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleCombo;

    @FXML
    private void initialize() {
        roleCombo.getItems().addAll("admin", "member");
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleCombo.getValue();
        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Register Gagal", "Semua field wajib diisi!");
            return;
        }
        if (password.length() < 6) {
            showAlert("Register Gagal", "Password minimal 6 karakter!");
            return;
        }
        if (UserDAO.isUsernameExist(username)) {
            showAlert("Register Gagal", "Username sudah digunakan!");
            return;
        }
        User user = new User(username, password, role);
        if (UserDAO.registerUser(user)) {
            showAlert("Sukses", "Registrasi berhasil! Silakan login.");
            goToLogin();
        } else {
            showAlert("Register Gagal", "Registrasi gagal!");
        }
    }

    @FXML
    private void goToLogin() {
        Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/Login.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
