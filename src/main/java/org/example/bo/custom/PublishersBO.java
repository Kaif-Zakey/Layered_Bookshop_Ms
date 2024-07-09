package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.dto.PublishersDTO;
import org.example.entity.Customer;
import org.example.entity.Publishers;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PublishersBO extends SuperBO {
    public ArrayList<BookDTO> getAllItems() throws SQLException, ClassNotFoundException;
    public boolean add(PublishersDTO dto)throws  SQLException,ClassNotFoundException;
    public boolean update(PublishersDTO dto)throws  SQLException,ClassNotFoundException;

    public boolean exist(String id) throws SQLException, ClassNotFoundException;

    public boolean delete(String id) throws SQLException, ClassNotFoundException;

    public String generateNewID() throws SQLException, ClassNotFoundException;

    public Publishers searchById(String id) throws SQLException,ClassNotFoundException;
    public ArrayList<PublishersDTO> getAllPublishers() throws SQLException, ClassNotFoundException;
}
