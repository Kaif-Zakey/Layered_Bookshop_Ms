package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.RegisterDAO;
import org.example.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAOImpl implements RegisterDAO {
    @Override
    public boolean SaveUser(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user(user_id,name,password) VALUES(?, ?, ?)",entity.getId(),entity.getName(),entity.getPassword());
    }

    @Override
    public boolean ExistUser(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM user WHERE user_id=?",id);
        return rst.next();
    }
}
