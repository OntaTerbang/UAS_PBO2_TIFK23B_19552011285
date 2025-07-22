package com.mycompany.kasirpropertijavafxfadillahfajri;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DasborController {
    @FXML private Label welcomeLabel;
    @FXML private Button btnProperti, btnPenyewa, btnTransaksi, btnLogout;

    @FXML
    private void initialize() {
        User user = UserSession.getUser();
        String role = user != null ? user.getRole() : "";
        String username = user != null ? user.getUsername() : "";
        welcomeLabel.setText("Selamat datang, " + username + " (" + role + ")");
        if (role.equals("member")) {
            btnProperti.setDisable(true);
            // btnPenyewa (Data Diri) tetap bisa diakses member
        }
    }

    @FXML
    private void goToProperti() { Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/KelolaProperti.fxml"); }
    @FXML
    private void goToPenyewa() { Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/KelolaPenyewa.fxml"); }
    @FXML
    private void goToTransaksi() { Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/KelolaTransaksi.fxml"); }
    @FXML
    private void handleLogout() { UserSession.clear(); Main.setRoot("/com/mycompany/kasirpropertijavafxfadillahfajri/Login.fxml"); }
}
