package com.hua.library3.domain.repository.impl;

import com.hua.library3.domain.EventEmailRecipient;
import com.hua.library3.domain.repository.EventEmailRecipientRepository;
import com.hua.library3.singleton.SingletonDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;

public class EventEmailRecipientRepositoryImpl implements EventEmailRecipientRepository {
    private static final String FIELDS
            = "name, email, registrationdate";
    private static final String PK_FIELDS = "id, " + FIELDS;
    private static final String SQL_INSERT
            = "insert into eventemailrecipient (" + FIELDS + ") values (?,?,?)";
    private DataSource dataSource = SingletonDataSource.getDataSource();

    @Override
    public void addEventEmailRecipient(EventEmailRecipient eventEmailRecipient) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(SQL_INSERT);
            ps.setString(1, eventEmailRecipient.getName());
            ps.setString(2, eventEmailRecipient.getEmail());
            Date dt = new Date();
            ps.setDate(3, new java.sql.Date(dt.getTime()));
            int out = ps.executeUpdate();
            if (out != 0) {
                System.out.println("EventEmailRecipient saved with name=" + eventEmailRecipient.getName());
            } else {
                System.out.println("EventEmailRecipient save failed with name=" + eventEmailRecipient.getName());
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000"))
                throw new SQLException("Error: Duplicate name and/or email!", e.getSQLState());
            else {
                System.out.printf("Error code %d\nError message %s", e.getErrorCode(), e.getMessage());
                throw e;
            }
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
            }
        }
    }

}
