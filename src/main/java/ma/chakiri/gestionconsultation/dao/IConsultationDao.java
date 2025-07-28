package ma.chakiri.gestionconsultation.dao;

import ma.chakiri.gestionconsultation.entities.Consultation;

import java.sql.SQLException;

public interface IConsultationDao extends Dao<Consultation, Long> {
    void deleteByPatientId(long patientId) throws SQLException;
}