package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.OrderDetailsDAO;
import org.example.entity.Inventory;
import org.example.entity.OrderDetails;
import org.example.entity.Orders;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails>AllOrders = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM orderbookdetail");
        while (rst.next()){
            OrderDetails inventory = new OrderDetails(rst.getString("b_id"), rst.getDouble("unit_price"), rst.getInt("qty"),rst.getString("o_id"));
            AllOrders.add(inventory);
        }
        return AllOrders;
    }

    @Override
    public boolean add(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Orderbookdetail (o_id, b_id, qty,unit_price) VALUES (?,?,?,?)", entity.getoId(), entity.getbId(), entity.getQty(), entity.getUnitPrice());

    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("This feature is not implemented yet");

    }

    @Override
    public OrderDetails searchByID(String id) throws SQLException, ClassNotFoundException {
    return null;
    }
}
