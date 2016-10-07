package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.LibraryEvent;
import com.hua.library3.domain.repository.LibraryEventRepository;
import com.hua.library3.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

public class LibraryEventRepositoryImpl implements LibraryEventRepository {
    private static final String FIELDS
            = "type, title, startdate, enddate";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String SQL_SELECT
            = "select " + PK_FIELDS + " from libraryevent";
    private DataSource dataSource = SingletonDataSource.getDataSource();

    @Override
    public List<LibraryEvent> getAllLibraryEvents() {
        List<LibraryEvent> libraryEventList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LibraryEvent libraryEvent = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                libraryEvent = new LibraryEvent();
                libraryEvent.setId(rs.getInt("id"));
                libraryEvent.setType(rs.getString("type"));
                libraryEvent.setTitle(rs.getString("title"));
                libraryEvent.setStartdate(new Date(rs.getDate("startdate").getTime()));
                libraryEvent.setEnddate(new Date(rs.getDate("enddate").getTime()));
                libraryEventList.add(libraryEvent);
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
        return libraryEventList;    
    }   
}
