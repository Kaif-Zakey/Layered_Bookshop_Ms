package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.ResetDAO;
import org.example.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetDAOImpl implements ResetDAO {
    @Override
    public boolean ExistUser(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM user WHERE user_id=?",id);
        return rst.next();
    }

    @Override
    public boolean Update(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET  name=?, password=? WHERE user_id=?",entity.getName(),entity.getPassword(),entity.getId());

    }
}
