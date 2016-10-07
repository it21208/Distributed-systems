package com.hua.library3.domain.repository;

import com.hua.library3.domain.EventEmailRecipient;
import java.sql.SQLException;

public interface EventEmailRecipientRepository {
    void addEventEmailRecipient(EventEmailRecipient eventEmailRecipient) throws SQLException;      
}