package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.AuthorDAO;
import org.example.entity.Author;
import org.example.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAOImpl implements AuthorDAO {
    @Override
    public ArrayList<Author> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Author> AllAuthor = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM author");
        while (rst.next()) {
            AllAuthor.add(new Author(rst.getString("a_id"),rst.getString("a_name") ,rst.getString("a_country")));
        }
        return AllAuthor;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM author WHERE a_id=?", code);
    }

    @Override
    public boolean add(Author entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO author (a_id, a_name,a_country) VALUES (?,?,?)",entity.getId(),entity.getName(),entity.getCountry());
    }

    @Override
    public boolean update(Author entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE author SET  a_name=?, a_country=? WHERE a_id=?",entity.getName(),entity.getCountry(),entity.getId());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT a_id FROM author WHERE a_id=?", code);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT a_id FROM author ORDER BY a_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("a_id");
            return  id;
        }
        return null;
    }

    @Override
    public Author searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM author WHERE a_id=?", id + "");
        rst.next();
        return new Author(id + "", rst.getString("a_name"), rst.getString("a_country"));


    }
}
