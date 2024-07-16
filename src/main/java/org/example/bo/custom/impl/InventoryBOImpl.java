package org.example.bo.custom.impl;

import org.example.bo.custom.InventoryBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.InventoryDAO;
import org.example.dto.BookDTO;
import org.example.dto.InventoryDTO;
import org.example.entity.Book;
import org.example.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.INVENTORY);

    public ArrayList<InventoryDTO> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDTO> allInventory= new ArrayList<>();
        ArrayList<Inventory> allEntityData = inventoryDAO.getAll();
        for (Inventory i : allEntityData) {
            allInventory.add(new InventoryDTO(i.getId(),i.getQty(),i.getLocation(),i.getBoId()));
        }
        return allInventory;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return inventoryDAO.delete(code);
    }

    @Override
    public boolean add(InventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.add(new Inventory(dto.getId(),dto.getQty(),dto.getLocation(),dto.getBoId()));
    }

    @Override
    public boolean update(InventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new Inventory(dto.getId(),dto.getQty(),dto.getLocation(),dto.getBoId()));
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return inventoryDAO.exist(code);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return inventoryDAO.generateNewID();
    }
    public Inventory searchById(String id) throws SQLException,ClassNotFoundException{
        return inventoryDAO.searchByID(id);
    }



}
