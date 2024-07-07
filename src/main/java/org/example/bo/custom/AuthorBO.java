package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.entity.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthorBO extends SuperBO {
    public ArrayList<AuthorDTO> getAllAuthor() throws SQLException, ClassNotFoundException;

    public boolean delete(String code) throws SQLException, ClassNotFoundException ;

    public boolean saveAuthor(AuthorDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateAuthor(AuthorDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existAuthor(String code) throws SQLException, ClassNotFoundException ;

    public String generateNewCode() throws SQLException, ClassNotFoundException ;
    public Author searchById(String id) throws SQLException,ClassNotFoundException;

}
