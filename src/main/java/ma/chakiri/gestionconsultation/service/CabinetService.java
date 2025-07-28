package ma.chakiri.gestionconsultation.service;

import ma.chakiri.gestionconsultation.dao.ConsultationDao;
import ma.chakiri.gestionconsultation.dao.IConsultationDao;
import ma.chakiri.gestionconsultation.dao.IPatientDao;
import ma.chakiri.gestionconsultation.dao.PatientDao;
import ma.chakiri.gestionconsultation.entities.Consultation;
import ma.chakiri.gestionconsultation.entities.Patient;

import java.sql.SQLException;
import java.util.List;

public class CabinetService implements ICabinetService {
    private final IPatientDao patientDao;
    private final IConsultationDao consultationDao;

    public CabinetService() {
        this.patientDao = new PatientDao();
        this.consultationDao = new ConsultationDao();
    }

    @Override
    public void addPatient(Patient patient) throws SQLException {
        patientDao.create(patient);
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        patientDao.update(patient);
    }

    @Override
    public void deletePatient(Patient patient) throws SQLException {
        // First, delete all consultations associated with this patient
        consultationDao.deleteByPatientId(patient.getId());
        // Then, delete the patient
        patientDao.delete(patient);
    }

    @Override
    public Patient getPatientById(long id) throws SQLException {
        return patientDao.findById(id);
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        return patientDao.findAll();
    }

    @Override
    public List<Patient> searchPatientByQuery(String query) throws SQLException {
        return patientDao.searchByQuery(query);
    }

    @Override
    public void addConsultation(Consultation consultation) throws SQLException {
        consultationDao.create(consultation);
    }

    @Override
    public void updateConsultation(Consultation consultation) throws SQLException {
        consultationDao.update(consultation);
    }

    @Override
    public void deleteConsultation(Consultation consultation) throws SQLException {
        consultationDao.delete(consultation);
    }

    @Override
    public Consultation getConsultationById(long id) throws SQLException {
        return consultationDao.findById(id);
    }

    @Override
    public List<Consultation> getAllConsultations() throws SQLException {
        return consultationDao.findAll();
    }
}