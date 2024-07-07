package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.CustomerDTO;
import org.example.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBO {
    public ArrayList<CustomerDTO>getAllCustomers()throws SQLException,ClassNotFoundException;

    public boolean addCustomer(CustomerDTO dto)throws  SQLException,ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO dto)throws  SQLException,ClassNotFoundException;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    public String generateNewCustomerID() throws SQLException, ClassNotFoundException;

    public Customer searchById(String id) throws SQLException,ClassNotFoundException;

}
