package ma.chakiri.gestionconsultation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private PatientController patientViewController;
    @FXML
    private ConsultationController consultationViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Give the PatientController a reference to the ConsultationController
        if (patientViewController != null && consultationViewController != null) {
            patientViewController.setConsultationController(consultationViewController);
        }
    }
}