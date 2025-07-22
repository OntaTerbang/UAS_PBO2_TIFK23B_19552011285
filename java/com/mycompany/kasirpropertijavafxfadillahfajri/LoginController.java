/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirpropertijavafxfadillahfajri;

import com.mycompany.kasirpropertijavafxfadillahfajri.User;
import com.mycompany.kasirpropertijavafxfadillahfajri.UserDAO;
import com.mycompany.kasirpropertijavafxfadillahfajri.UserSession;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Gagal", "Username dan password wajib diisi!");
            return;
        }
        User user = UserDAO.loginUser(username, password);
        if (user != null) {
            UserSession.setUser(user);
            Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/Dasbor.fxml");
        } else {
            showAlert("Login Gagal", "Username atau password salah!");
        }
    }

    @FXML
    private void goToRegister() {
        Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/Register.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}