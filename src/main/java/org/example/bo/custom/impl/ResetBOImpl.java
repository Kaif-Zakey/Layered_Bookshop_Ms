package org.example.bo.custom.impl;

import org.example.bo.custom.ResetBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.ResetDAO;
import org.example.entity.User;

import java.sql.SQLException;

public class ResetBOImpl implements ResetBO {
    ResetDAO resetDAO = (ResetDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESET);

    @Override
    public boolean ExistUser(String id) throws SQLException, ClassNotFoundException {
        return resetDAO.ExistUser(id);
    }

    @Override
    public boolean Update(User entity) throws SQLException, ClassNotFoundException {
        return resetDAO.Update(new User(entity.getId(),entity.getName(),entity.getPassword()));
    }
}
