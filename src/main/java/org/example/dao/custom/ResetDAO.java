package org.example.dao.custom;

import org.example.dao.SuperDAO;
import org.example.entity.User;

import java.sql.SQLException;

public interface ResetDAO extends SuperDAO {
    public boolean ExistUser(String id)throws SQLException,ClassNotFoundException;
    public boolean Update(User entity)throws SQLException,ClassNotFoundException;
}
