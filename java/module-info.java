module com.mycompany.kasirpropertijavafxfadillahfajri {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.kasirpropertijavafxfadillahfajri to javafx.fxml;
    exports com.mycompany.kasirpropertijavafxfadillahfajri;
}
