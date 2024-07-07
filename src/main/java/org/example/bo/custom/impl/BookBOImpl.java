package org.example.bo.custom.impl;

import org.example.bo.custom.BookBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.CustomerDAO;
import org.example.dao.custom.impl.BookDAOImpl;
import org.example.dto.BookDTO;

import org.example.entity.Book;
import org.example.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookBOImpl implements BookBO {

    BookDAO  bookDAO=(BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);
    @Override
    public ArrayList<BookDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<BookDTO> allBooks= new ArrayList<>();
        ArrayList<Book> allEntityData = bookDAO.getAll();
        for (Book i : allEntityData) {
            allBooks.add(new BookDTO(i.getId(),i.getUnitPrice(),i.getDescription(),i.getQtyOnHand()));
        }
        return allBooks;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return bookDAO.delete(code);
    }

    @Override
    public boolean saveItem(BookDTO dto) throws SQLException, ClassNotFoundException {
        return bookDAO.add(new Book(dto.getId(),dto.getUnitPrice(),dto.getDescription(),dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(BookDTO dto) throws SQLException, ClassNotFoundException {
        return bookDAO.update(new Book(dto.getId(),dto.getUnitPrice(),dto.getDescription(),dto.getQtyOnHand()));
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return bookDAO.exist(code);
    }

    @Override
    public String generateNewCode() throws SQLException, ClassNotFoundException {
        return bookDAO.generateNewID();
    }
    public Book searchById(String id) throws SQLException,ClassNotFoundException{
        return bookDAO.searchByID(id);
    }





}
