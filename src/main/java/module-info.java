module ma.chakiri.gestionconsultation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ma.chakiri.gestionconsultation.controller to javafx.fxml;
    opens ma.chakiri.gestionconsultation.entities to javafx.base;
    exports ma.chakiri.gestionconsultation;
}