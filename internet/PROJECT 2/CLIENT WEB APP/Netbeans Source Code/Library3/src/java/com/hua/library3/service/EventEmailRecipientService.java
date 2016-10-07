package com.hua.library3.service;

import com.hua.library3.domain.EventEmailRecipient;
import java.sql.SQLException;

public interface EventEmailRecipientService {
    void addEventEmailRecipient(EventEmailRecipient eventEmailRecipient) throws SQLException;      
}