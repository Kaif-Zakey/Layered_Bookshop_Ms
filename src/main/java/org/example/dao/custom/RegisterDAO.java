package org.example.dao.custom;

import org.example.dao.SuperDAO;
import org.example.entity.User;

import java.sql.SQLException;

public interface RegisterDAO extends SuperDAO {
public boolean SaveUser(User entity)throws SQLException,ClassNotFoundException;
public boolean ExistUser(String id)throws SQLException,ClassNotFoundException;


}
