package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.BookDAO;
import org.example.entity.Book;
import org.example.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public ArrayList<Book> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Book> allBooks = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM books");
        while (rst.next()) {
            allBooks.add(new Book(rst.getString("b_id"),rst.getDouble("unit_Price") ,rst.getString("Description"), rst.getInt("qty_on_hand")));
        }
        return allBooks;
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM books WHERE b_id=?", code);
    }

    @Override
    public boolean add(Book entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO books (b_id, unit_price,Description, qty_on_hand) VALUES (?,?,?,?)",entity.getId(),entity.getUnitPrice(),entity.getDescription(),entity.getQtyOnHand());
    }

    @Override
    public boolean update(Book entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Books SET  unit_price=?, Description=?, qty_on_hand=? WHERE b_id=?",entity.getUnitPrice(),entity.getDescription(),entity.getQtyOnHand(),entity.getId());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT b_id FROM books WHERE b_id=?", code);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT b_id FROM books ORDER BY b_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("b_id");
            return  id;
        }
        return null;
    }

    @Override
    public Book searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM books WHERE b_id=?", id + "");
        rst.next();
        return new Book(id + "", rst.getDouble("unit_price"), rst.getString("Description"), rst.getInt("qty_on_hand"));

    }


}
