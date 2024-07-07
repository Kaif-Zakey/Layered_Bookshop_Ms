package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.entity.Book;
import org.example.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookBO extends SuperBO {
    public ArrayList<BookDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public boolean delete(String code) throws SQLException, ClassNotFoundException ;

    public boolean saveItem(org.example.dto.BookDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateItem(BookDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException ;

    public String generateNewCode() throws SQLException, ClassNotFoundException ;
    public  Book searchById(String id) throws SQLException,ClassNotFoundException;

}
