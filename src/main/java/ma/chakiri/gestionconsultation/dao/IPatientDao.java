package ma.chakiri.gestionconsultation.dao;

import ma.chakiri.gestionconsultation.entities.Patient;
import java.sql.SQLException;
import java.util.List;

public interface IPatientDao extends Dao<Patient, Long> {
    List<Patient> searchByQuery(String query) throws SQLException;
}