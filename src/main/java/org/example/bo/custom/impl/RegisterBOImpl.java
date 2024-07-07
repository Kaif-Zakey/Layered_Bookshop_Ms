package org.example.bo.custom.impl;

import org.example.bo.custom.RegisterBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.RegisterDAO;
import org.example.entity.User;

import java.sql.SQLException;

public class RegisterBOImpl implements RegisterBO {

    RegisterDAO registerDAO = (RegisterDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.REGISTER);
    @Override
    public boolean SaveUser(User dto) throws SQLException, ClassNotFoundException {
        return registerDAO.SaveUser(new User(dto.getId(),dto.getName(),dto.getPassword()));
    }

    @Override
    public boolean ExistUser(String id) throws SQLException, ClassNotFoundException {
        return registerDAO.ExistUser(id);
    }
}
