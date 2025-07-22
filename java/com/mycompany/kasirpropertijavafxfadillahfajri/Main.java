package com.mycompany.kasirpropertijavafxfadillahfajri; 

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;

            // Load halaman login saat pertama kali dibuka
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/kasirpropertijavafxfadillahfajri/Login.fxml"));
            System.out.println("Login.fxml berhasil dimuat.");
            Scene scene = new Scene(root);

            stage.setTitle("Aplikasi Kasir Properti");
            stage.setScene(scene);
            stage.setResizable(false); 
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Digunakan untuk mengganti scene dari controller lain
    public static void setRoot(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
            primaryStage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
