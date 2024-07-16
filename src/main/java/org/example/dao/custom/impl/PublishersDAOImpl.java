package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.PublishersDAO;
import org.example.entity.Book;
import org.example.entity.OrderDetails;
import org.example.entity.Publishers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublishersDAOImpl implements PublishersDAO {
    @Override
    public ArrayList<Publishers> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Publishers> Publishers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM publishers");
        while (rst.next()){
            Publishers publishers = new Publishers(rst.getString("pu_id"), rst.getString("pu_name"), rst.getString("pu_address"),rst.getString("pu_phone"),rst.getString("b_id"));
            Publishers.add(publishers);
        }
        return Publishers;
    }

    @Override
    public boolean add(Publishers entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO publishers (pu_id, pu_name, pu_address,pu_phone,b_id) VALUES (?,?,?,?,?)", entity.getPuId(), entity.getName(), entity.getAddress(), entity.getPhoneNumber(),entity.getBookId());

    }

    @Override
    public boolean update(Publishers entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE publishers SET   pu_name=?, pu_address=?,pu_phone=?,b_id=? WHERE pu_id=?",entity.getName(),entity.getAddress(),entity.getPhoneNumber(),entity.getBookId(),entity.getPuId());

    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT pu_id FROM publishers WHERE pu_id=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT pu_id FROM publishers ORDER BY pu_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("pu_id");
            return  id;
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM publishers WHERE pu_id=?", id);

    }

    @Override
    public Publishers searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM publishers WHERE pu_id=?", id + "");
        rst.next();
        return new Publishers(id + "", rst.getString("pu_name"), rst.getString("pu_address"), rst.getString("pu_phone"),rst.getString("b_id"));

    }
}
