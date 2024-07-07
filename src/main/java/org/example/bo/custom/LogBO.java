package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public interface LogBO extends SuperBO {
    public User checkCredential(String userId) throws SQLException, IOException, ClassNotFoundException;

}
