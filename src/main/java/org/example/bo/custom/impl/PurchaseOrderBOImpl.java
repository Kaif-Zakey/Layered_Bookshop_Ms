package org.example.bo.custom.impl;



import org.example.bo.custom.PurchaseOrderBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.CustomerDAO;
import org.example.dao.custom.OrderDAO;
import org.example.dao.custom.OrderDetailsDAO;
import org.example.db.DBConnection;
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.dto.OrderDTO;
import org.example.dto.OrderDetailDTO;
import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.entity.OrderDetails;
import org.example.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    BookDAO itemDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.searchByID(id);
        return new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getEmail());
    }


    @Override
    public BookDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Book i = itemDAO.searchByID(code);
        return new BookDTO(i.getId(),i.getUnitPrice(),i.getDescription(),i.getQtyOnHand());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerEntityData = customerDAO.getAll();
        ArrayList<CustomerDTO> convertToDto= new ArrayList<>();
        for (Customer c : customerEntityData) {
            convertToDto.add(new CustomerDTO(c.getId(),c.getName(),c.getAddress(),c.getEmail()));
        }
       return convertToDto;
    }

    @Override
    public ArrayList<BookDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Book> entityTypeData = itemDAO.getAll();
        ArrayList<BookDTO> dtoTypeData= new ArrayList<>();
        for (Book i : entityTypeData) {
            dtoTypeData.add(new BookDTO(i.getId(),i.getUnitPrice(),i.getDescription(),i.getQtyOnHand()));
        }
        return dtoTypeData;
    }


    @Override
    public boolean purchaseOrder(OrderDTO dto)throws SQLException, ClassNotFoundException{
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            boolean b1 = orderDAO.exist(dto.getOrderId());

            if (false) {
                return false;
            }

            connection.setAutoCommit(false);

            boolean b2 = orderDAO.add(new Orders(dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate()));
            if (!b2) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO d : dto.getOrderDetails()) {
                OrderDetails orderDetails = new OrderDetails(d.getOid(),d.getItemCode(),d.getQty(),d.getUnitPrice());
                boolean b3 = orderDetailsDAO.add(orderDetails);
                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                BookDTO item = findItem(d.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - d.getQty());


                boolean b = itemDAO.update(new Book(item.getId(), item.getUnitPrice(), item.getDescription(),item.getQtyOnHand()));

                if (!b) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public BookDTO findItem(String code)throws SQLException, ClassNotFoundException {
        try {
            Book i = itemDAO.searchByID(code);
            return new BookDTO(i.getId(),i.getUnitPrice(),i.getDescription(),i.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
