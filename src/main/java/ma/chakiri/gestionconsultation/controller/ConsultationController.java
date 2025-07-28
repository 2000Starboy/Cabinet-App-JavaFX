package ma.chakiri.gestionconsultation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.chakiri.gestionconsultation.entities.Consultation;
import ma.chakiri.gestionconsultation.entities.Patient;
import ma.chakiri.gestionconsultation.service.CabinetService;
import ma.chakiri.gestionconsultation.service.ICabinetService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {

    @FXML private DatePicker dateConsultation;
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private TextArea textFieldDescription;

    @FXML private TableView<Consultation> tableConsultation;
    @FXML private TableColumn<Consultation, Long> columnId;
    @FXML private TableColumn<Consultation, Date> columnDate;
    @FXML private TableColumn<Consultation, String> columnDescription;
    @FXML private TableColumn<Consultation, Patient> columnPatient;

    private ICabinetService cabinetService;
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private final ObservableList<Consultation> consultations = FXCollections.observableArrayList();
    private Consultation selectedConsultation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = new CabinetService();

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));

        tableConsultation.setItems(consultations);
        comboPatient.setItems(patients);

        tableConsultation.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedConsultation = newSel;
                dateConsultation.setValue(selectedConsultation.getDateConsultation().toLocalDate());
                comboPatient.getSelectionModel().select(selectedConsultation.getPatient());
                textFieldDescription.setText(selectedConsultation.getDescription());
            }
        });

        refreshData();
    }

    public void refreshData() {
        try {
            patients.setAll(cabinetService.getAllPatients());
            consultations.setAll(cabinetService.getAllConsultations());
        } catch (SQLException e) {
            showError("Erreur de Base de Données", "Le chargement des données a échoué.");
        }
    }

    @FXML
    public void addConsultation() {
        if (validateInputs()) {
            try {
                Consultation consultation = new Consultation();
                consultation.setDateConsultation(Date.valueOf(dateConsultation.getValue()));
                consultation.setPatient(comboPatient.getSelectionModel().getSelectedItem());
                consultation.setDescription(textFieldDescription.getText());

                cabinetService.addConsultation(consultation);
                refreshData();
                clearForm();
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "L'ajout de la consultation a échoué.");
            }
        }
    }

    @FXML
    public void updateConsultation() {
        if (selectedConsultation != null && validateInputs()) {
            try {
                selectedConsultation.setDateConsultation(Date.valueOf(dateConsultation.getValue()));
                selectedConsultation.setPatient(comboPatient.getSelectionModel().getSelectedItem());
                selectedConsultation.setDescription(textFieldDescription.getText());
                cabinetService.updateConsultation(selectedConsultation);
                refreshData();
                clearForm();
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "La mise à jour de la consultation a échoué.");
            }
        } else {
            showError("Erreur de Sélection", "Veuillez sélectionner une consultation et remplir tous les champs pour mettre à jour.");
        }
    }

    @FXML
    public void deleteConsultation() {
        if (selectedConsultation != null) {
            try {
                cabinetService.deleteConsultation(selectedConsultation);
                refreshData();
                clearForm();
            } catch (SQLException e) {
                showError("Erreur de Base de Données", "La suppression de la consultation a échoué.");
            }
        } else {
            showError("Erreur de Sélection", "Veuillez sélectionner une consultation à supprimer.");
        }
    }

    private void clearForm() {
        dateConsultation.setValue(null);
        comboPatient.getSelectionModel().clearSelection();
        textFieldDescription.clear();
        tableConsultation.getSelectionModel().clearSelection();
        selectedConsultation = null;
    }

    private boolean validateInputs() {
        if (dateConsultation.getValue() == null || comboPatient.getSelectionModel().getSelectedItem() == null) {
            showError("Erreur de Validation", "Les champs Date et Patient ne peuvent pas être vides.");
            return false;
        }
        if (textFieldDescription.getText().trim().isEmpty()) {
            showError("Erreur de Validation", "Le champ Description ne peut pas être vide.");
            return false;
        }
        if (dateConsultation.getValue().isAfter(LocalDate.now())) {
            showError("Erreur de Validation", "La date de consultation ne peut pas être dans le futur.");
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