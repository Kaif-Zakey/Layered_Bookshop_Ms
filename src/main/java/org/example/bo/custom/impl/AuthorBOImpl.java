package org.example.bo.custom.impl;

import org.example.bo.custom.AuthorBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.AuthorDAO;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.entity.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorBOImpl implements AuthorBO {
    AuthorDAO authorDAO = (AuthorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AUTHOR);

    @Override
    public ArrayList<AuthorDTO> getAllAuthor() throws SQLException, ClassNotFoundException {
        ArrayList<AuthorDTO> allAuthor= new ArrayList<>();
        ArrayList<Author> allEntityData = authorDAO.getAll();
        for (Author i : allEntityData) {
            allAuthor.add(new AuthorDTO(i.getId(),i.getName(),i.getCountry()));
        }
        return allAuthor;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return authorDAO.delete(code);
    }

    @Override
    public boolean saveAuthor(AuthorDTO dto) throws SQLException, ClassNotFoundException {
        return authorDAO.add(new Author(dto.getId(),dto.getName(),dto.getCountry()));
    }

    @Override
    public boolean updateAuthor(AuthorDTO dto) throws SQLException, ClassNotFoundException {
        return authorDAO.update(new Author(dto.getId(),dto.getName(),dto.getCountry()));
    }

    @Override
    public boolean existAuthor(String code) throws SQLException, ClassNotFoundException {
        return authorDAO.exist(code);
    }

    @Override
    public String generateNewCode() throws SQLException, ClassNotFoundException {
        return authorDAO.generateNewID();
    }
    public Author searchById(String id) throws SQLException,ClassNotFoundException{
        return authorDAO.searchByID(id);
    }
}
