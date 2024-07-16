package org.example.bo.custom.impl;

import org.example.bo.custom.PublishersBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.PublishersDAO;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.dto.PublishersDTO;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.entity.Publishers;

import java.sql.SQLException;
import java.util.ArrayList;

public class PublishersBOImpl implements PublishersBO {

    PublishersDAO publishersDAO=(PublishersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PUBLISHERS);
    BookDAO bookDAO=(BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);

    @Override
    public ArrayList<BookDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<BookDTO> allBook= new ArrayList<>();
        ArrayList<Book> allEntityData = bookDAO.getAll();
        for (Book i : allEntityData) {
            allBook.add(new BookDTO(i.getId(),i.getUnitPrice(),i.getDescription(),i.getQtyOnHand()));
        }
        return allBook;
    }


    public ArrayList<PublishersDTO> getAllPublishers() throws SQLException, ClassNotFoundException {
        ArrayList<PublishersDTO> allPublishers= new ArrayList<>();
        ArrayList<Publishers> allEntityData = publishersDAO.getAll();
        for (Publishers i : allEntityData) {
            allPublishers.add(new PublishersDTO(i.getPuId(),i.getName(),i.getAddress(),i.getPhoneNumber(),i.getBookId()));
        }
        return allPublishers;
    }

    @Override
    public boolean add(PublishersDTO dto) throws SQLException, ClassNotFoundException {
        return publishersDAO.add(new Publishers(dto.getPuId(),dto.getName(),dto.getAddress(),dto.getPhoneNumber(),dto.getBookId()));

    }

    @Override
    public boolean update(PublishersDTO dto) throws SQLException, ClassNotFoundException {
        return publishersDAO.update(new Publishers(dto.getPuId(),dto.getName(),dto.getAddress(),dto.getPhoneNumber(),dto.getBookId()));

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return publishersDAO.exist(id);
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return publishersDAO.delete(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return publishersDAO.generateNewID();
    }

    @Override
    public Publishers searchById(String id) throws SQLException, ClassNotFoundException {
        return publishersDAO.searchByID(id);
    }
}
