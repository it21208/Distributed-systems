package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.Library;
import com.hua.library3.domain.repository.LibraryRepository;
import com.hua.library3.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class LibraryRepositoryImpl implements LibraryRepository {
    private static final String FIELDS
            = "name, address, telephone, email, openhours";
    private static final String SQL_SELECT
            = "select " + FIELDS + " from library";
   
    private DataSource dataSource = SingletonDataSource.getDataSource();

    @Override
    public Library getLibrary() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Library library = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            if (rs.next()) {
                library = new Library();
                library.setName(rs.getString("name"));
                library.setAddress(rs.getString("address"));
                library.setTelephone(rs.getString("telephone"));
                library.setEmail(rs.getString("email"));
                library.setOpenhours(rs.getString("openhours"));
                LibraryEventRepositoryImpl libraryEventRepositoryImpl = new LibraryEventRepositoryImpl();
                library.setLibraryEventsList(libraryEventRepositoryImpl.getAllLibraryEvents());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return library;    
    }  
}
