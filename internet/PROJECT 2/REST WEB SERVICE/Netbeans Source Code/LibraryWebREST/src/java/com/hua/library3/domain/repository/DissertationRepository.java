package com.hua.library3.domain.repository;

import com.hua.library3.domain.Dissertation;
import java.sql.SQLException;
import java.util.List;

public interface DissertationRepository {
    List <Dissertation> getAllDissertations();
    List <Dissertation> getAllDirectorDissertations();
    List <Dissertation> getAllManagerDissertations();
    Dissertation getDissertationByDissId(String dissId);
    Dissertation getDissertationById(int id);
    void addDissertation(Dissertation dissertation) throws SQLException;  
    void modifyDissertation(Dissertation dissertation) throws SQLException;   
    Dissertation getDissertationByStudentId(int studentId);
}
