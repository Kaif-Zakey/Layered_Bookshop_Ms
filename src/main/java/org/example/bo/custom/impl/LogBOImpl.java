package org.example.bo.custom.impl;

import org.example.bo.custom.LogBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.LogDAO;
import org.example.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public class LogBOImpl implements LogBO {
    LogDAO logDAO=(LogDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOG);

    @Override
    public User checkCredential(String userId) throws SQLException, IOException, ClassNotFoundException {
        return logDAO.checkCredential(userId);
    }
}
