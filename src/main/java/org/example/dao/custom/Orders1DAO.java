package org.example.dao.custom;

import org.example.dao.SuperDAO;
import org.example.dto.Order2DTO;
import org.example.entity.Book;

import java.sql.SQLException;

public interface Orders1DAO extends SuperDAO {
    public Order2DTO searchByID(String id) throws SQLException, ClassNotFoundException ;

    public boolean exist(String code) throws SQLException, ClassNotFoundException;

    public boolean delete(String code) throws SQLException, ClassNotFoundException;

    public boolean deleteOd(String code) throws SQLException, ClassNotFoundException;
}
