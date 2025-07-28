package ma.chakiri.gestionconsultation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.chakiri.gestionconsultation.entities.Patient;
import ma.chakiri.gestionconsultation.service.CabinetService;
import ma.chakiri.gestionconsultation.service.ICabinetService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML private TextField textFieldNom;
    @FXML private TextField textFieldPrenom;
    @FXML private TextField textFieldTel;
    @FXML private TextField textFieldSearch;

    @FXML private TableView<Patient> tablePatients;
    @FXML private TableColumn<Patient, Long> columnId;
    @FXML private TableColumn<Patient, String> columnNom;
    @FXML private TableColumn<Patient, String> columnPrenom;
    @FXML private TableColumn<Patient, String> columnTel;

    private Patient selectedPatient;
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ICabinetService cabinetService;
    private ConsultationController consultationController;

    // Method to be called by the MainController to establish communication
    public void setConsultationController(ConsultationController consultationController) {
        this.consultationController = consultationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = new CabinetService();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tablePatients.setItems(patients);

        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                patients.setAll(cabinetService.searchPatientByQuery(newValue));
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "La recherche des patients a échoué.");
            }
        });

        tablePatients.getSelectionModel().selectedItemProperty().addListener((observableValue, oldPatient, newPatient) -> {
            if (newPatient != null) {
                textFieldNom.setText(newPatient.getNom());
                textFieldPrenom.setText(newPatient.getPrenom());
                textFieldTel.setText(newPatient.getTel());
                selectedPatient = newPatient;
            }
        });

        loadPatients();
    }

    private void loadPatients() {
        try {
            patients.setAll(cabinetService.getAllPatients());
        } catch (SQLException e) {
            showError("Erreur de Base de Données", "Le chargement des patients a échoué.");
        }
    }

    private void refreshAllTabs() {
        loadPatients();
        if (consultationController != null) {
            consultationController.refreshData();
        }
    }

    @FXML
    public void addPatient() {
        if (validateInputs()) {
            try {
                Patient patient = new Patient(textFieldNom.getText(), textFieldPrenom.getText(), textFieldTel.getText());
                cabinetService.addPatient(patient);
                refreshAllTabs();
                clearForm();
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "L'ajout du patient a échoué.");
            }
        }
    }

    @FXML
    public void deletePatient() {
        if (selectedPatient != null) {
            try {
                cabinetService.deletePatient(selectedPatient);
                refreshAllTabs();
                clearForm();
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "La suppression du patient a échoué.");
            }
        } else {
            showError("Erreur de Sélection", "Veuillez sélectionner un patient à supprimer.");
        }
    }

    @FXML
    public void updatePatient() {
        if (selectedPatient != null && validateInputs()) {
            try {
                selectedPatient.setNom(textFieldNom.getText());
                selectedPatient.setPrenom(textFieldPrenom.getText());
                selectedPatient.setTel(textFieldTel.getText());
                cabinetService.updatePatient(selectedPatient);
                refreshAllTabs();
                clearForm();
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "La mise à jour du patient a échoué.");
            }
        } else {
            showError("Erreur de Sélection", "Veuillez sélectionner un patient et remplir tous les champs pour mettre à jour.");
        }
    }

    private void clearForm() {
        textFieldNom.clear();
        textFieldPrenom.clear();
        textFieldTel.clear();
        tablePatients.getSelectionModel().clearSelection();
        selectedPatient = null;
    }

    private boolean validateInputs() {
        if (textFieldNom.getText().isEmpty() || textFieldPrenom.getText().isEmpty()) {
            showError("Erreur de Validation", "Les champs Nom et Prénom ne peuvent pas être vides.");
            return false;
        }
        return true;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}