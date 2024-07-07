package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dao.SuperDAO;
import org.example.dto.AuthorDTO;
import org.example.entity.User;

import java.sql.SQLException;

public interface RegisterBO extends SuperBO {
    public boolean SaveUser(User dto) throws SQLException, ClassNotFoundException;

    public boolean ExistUser(String id)throws SQLException,ClassNotFoundException;
}
