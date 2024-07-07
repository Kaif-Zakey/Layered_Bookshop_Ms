package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dao.SuperDAO;
import org.example.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public interface LogDAO extends SuperDAO {
    public   User checkCredential(String userId) throws SQLException, IOException, ClassNotFoundException;
}
