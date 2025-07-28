package ma.chakiri.gestionconsultation.service;

import ma.chakiri.gestionconsultation.entities.Consultation;
import ma.chakiri.gestionconsultation.entities.Patient;

import java.sql.SQLException;
import java.util.List;

public interface ICabinetService {
    // Patient methods
    void addPatient(Patient patient) throws SQLException;
    void updatePatient(Patient patient) throws SQLException;
    void deletePatient(Patient patient) throws SQLException;
    Patient getPatientById(long id) throws SQLException;
    List<Patient> getAllPatients() throws SQLException;
    List<Patient> searchPatientByQuery(String query) throws SQLException;

    // Consultation methods
    void addConsultation(Consultation consultation) throws SQLException;
    void updateConsultation(Consultation consultation) throws SQLException;
    void deleteConsultation(Consultation consultation) throws SQLException;
    Consultation getConsultationById(long id) throws SQLException;
    List<Consultation> getAllConsultations() throws SQLException;
}