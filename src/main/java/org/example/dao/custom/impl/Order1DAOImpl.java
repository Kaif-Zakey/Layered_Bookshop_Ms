package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.Orders1DAO;
import org.example.dto.Order2DTO;
import org.example.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Order1DAOImpl implements Orders1DAO {
    @Override
    public Order2DTO searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE o_id=?", id + "");
        rst.next();
        return new Order2DTO(id + "", rst.getString("c_id"), rst.getDate("o_date"));

    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM orders WHERE o_id=?", code);
        return rst.next();
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orders WHERE o_id=?", code);

    }
    public boolean deleteOd(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orderbookdetail WHERE o_id=?", code);

    }
}
