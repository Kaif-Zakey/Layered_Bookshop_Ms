package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.entity.User;

import java.sql.SQLException;

public interface ResetBO extends SuperBO {
    public boolean ExistUser(String id) throws SQLException, ClassNotFoundException;
    public boolean Update(User entity) throws SQLException, ClassNotFoundException;


    }
