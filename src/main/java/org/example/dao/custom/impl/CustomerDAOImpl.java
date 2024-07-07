package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.CustomerDAO;
import org.example.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM CUSTOMERS");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("c_id"), rst.getString("c_name"), rst.getString("c_address"), rst.getString("c_email"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Customers (c_id,c_name, c_address,c_email) VALUES (?,?,?,?)", entity.getId(), entity.getName(), entity.getAddress(), entity.getEmail());

    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customers SET  c_name=?, c_address=?, c_email=? WHERE c_id=?",entity.getName(),entity.getAddress(),entity.getEmail(),entity.getId());

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM Customers WHERE c_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customers ORDER BY c_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("c_id");
        return  id;
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customers WHERE c_id=?", id);
    }

    @Override
    public Customer searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customers WHERE c_id=?", id + "");
        rst.next();
        return new Customer(id + "", rst.getString("c_name"), rst.getString("c_address"),rst.getString("c_email"));

    }
}
