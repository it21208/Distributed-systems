package com.hua.library.domain.repository;

import com.hua.library.domain.Dissertation;
import java.sql.SQLException;
import java.util.List;

public interface DissertationRepository {
    List <Dissertation> getAllDissertations();
    List <Dissertation> getAllDirectorDissertations();
    List <Dissertation> getAllManagerDissertations();
    Dissertation getDissertationByDissId(String dissId);
    void addDissertation(Dissertation dissertation) throws SQLException;  
    void modifyDissertation(Dissertation dissertation) throws SQLException;
}
