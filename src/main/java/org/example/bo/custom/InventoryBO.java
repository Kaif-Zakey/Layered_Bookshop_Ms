package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.BookDTO;
import org.example.dto.InventoryDTO;
import org.example.entity.Book;
import org.example.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {
    public ArrayList<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException;

    public boolean delete(String code) throws SQLException, ClassNotFoundException ;

    public boolean add(InventoryDTO dto) throws SQLException, ClassNotFoundException;

    public boolean update(InventoryDTO dto) throws SQLException, ClassNotFoundException;

    public boolean exist(String code) throws SQLException, ClassNotFoundException ;

    public String generateNewID() throws SQLException, ClassNotFoundException ;
    public Inventory searchById(String id) throws SQLException,ClassNotFoundException;

}
