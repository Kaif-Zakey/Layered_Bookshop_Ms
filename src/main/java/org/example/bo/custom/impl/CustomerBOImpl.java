package org.example.bo.custom.impl;

import org.example.bo.custom.CustomerBo;
import org.example.dao.DAOFactory;
import org.example.dao.custom.CustomerDAO;
import org.example.dto.CustomerDTO;
import org.example.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBo {

  CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
  @Override
    public ArrayList<CustomerDTO> getAllCustomers()throws SQLException,ClassNotFoundException{
      ArrayList<CustomerDTO> allCustomers =new ArrayList<>();
      ArrayList<Customer>all =customerDAO.getAll();
      for (Customer c : all){
        allCustomers.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getEmail()));
      }
      return allCustomers;
    }
    @Override
    public boolean addCustomer(CustomerDTO dto)throws  SQLException,ClassNotFoundException{
      return customerDAO.add(new Customer(dto.getId(), dto.getName(), dto.getAddress(),dto.getEmail()));

    }
    @Override
    public boolean updateCustomer(CustomerDTO dto)throws  SQLException,ClassNotFoundException{
      return customerDAO.update(new Customer(dto.getId(),dto.getName(),dto.getAddress(), dto.getEmail()));

    }
    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException{
      return customerDAO.exist(id);

    }
    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException{
      return customerDAO.delete(id);

    }
    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException{
      return customerDAO.generateNewID();

    }
    @Override
    public  Customer searchById(String id) throws SQLException,ClassNotFoundException{
    return customerDAO.searchByID(id);
    }

}
