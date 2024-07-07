package org.example.bo.custom;



import org.example.bo.SuperBO;
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException ;

    public BookDTO searchItem(String code) throws SQLException, ClassNotFoundException ;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

    public String generateOrderID() throws SQLException, ClassNotFoundException ;

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    public ArrayList<BookDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public boolean purchaseOrder(OrderDTO dto)throws SQLException, ClassNotFoundException;

    public BookDTO findItem(String code)throws SQLException, ClassNotFoundException;
}

