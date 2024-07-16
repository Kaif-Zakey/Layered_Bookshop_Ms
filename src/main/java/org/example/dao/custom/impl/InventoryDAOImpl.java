package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.InventoryDAO;
import org.example.entity.Customer;
import org.example.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public ArrayList<Inventory> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Inventory>AllInventory = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory");
        while (rst.next()){
           AllInventory.add(new Inventory(rst.getString("i_id"),rst.getInt("i_qty"),rst.getString("i_location"),rst.getString("b_id")));
        }
        return AllInventory;
    }
    @Override
    public boolean add(Inventory entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO inventory  VALUES (?,?,?,?)", entity.getId(), entity.getQty(), entity.getLocation(), entity.getBoId());

    }

    @Override
    public boolean update(Inventory entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE inventory SET  i_qty=?, i_location=?, b_id=? WHERE i_id=?",entity.getQty(),entity.getLocation(),entity.getBoId(),entity.getId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT i_id FROM inventory WHERE i_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT i_id FROM inventory ORDER BY i_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("i_id");
            return  id;
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM inventory WHERE i_id=?", id);
    }

    @Override
    public Inventory searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory WHERE i_id=?", id + "");
        rst.next();
        return new Inventory(id + "", rst.getInt("i_qty"), rst.getString("i_location"),rst.getString("b_id"));
    }
}
