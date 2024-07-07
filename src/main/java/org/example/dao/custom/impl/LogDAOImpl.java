package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.LogDAO;
import org.example.entity.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogDAOImpl implements LogDAO {
    @Override
    public User checkCredential(String userId) throws SQLException, IOException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute( "SELECT user_id,name,password FROM user WHERE user_id = ?",userId+"");
        rst.next();
        return  new User(userId+"",rst.getString("name"),rst.getString("password") );
    }
}
