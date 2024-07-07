package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.OrderDAO;
import org.example.entity.Inventory;
import org.example.entity.Orders;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Orders>AllOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders");
        while (rst.next()){
            Orders inventory = new Orders(rst.getString("o_id"), rst.getString("c_id"), rst.getDate("o_date").toLocalDate());
            AllOrders.add(inventory);
        }
        return AllOrders;
    }

    @Override
    public boolean add(Orders entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO `Orders` (o_id, o_date, c_id) VALUES (?,?,?)",entity.getOid(), entity.getDate(),entity.getCid());

    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM `orders` WHERE o_id=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT o_id FROM `Orders` ORDER BY o_id DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("o_id").replace("OID-", "")) + 1)) : "OID-001";

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM orders WHERE o_id=?", id);

    }

    @Override
    public Orders searchByID(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");

    }
}
