package org.example.dao.custom.impl;



import org.example.dao.SQLUtil;
import org.example.dao.custom.QueryDAO;
import org.example.entity.CustomEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrder(String oid) throws SQLException, ClassNotFoundException {
       ResultSet rst= SQLUtil.execute("SELECT Orders.o_id,Orders.o_date,Orders.c_id,orderbookdetail.o_id,orderbookdetail.b_id,orderbookdetail.qty,orderbookdetail.unit_price from Orders inner join orderbookdetail on Orders.o_id=orderbookdetail.o_id where Orders.o_id=?",oid);
        ArrayList<CustomEntity> allRecords= new ArrayList<>();
        while (rst.next()) {
            String oid1 = rst.getString("o_id");
            String date = rst.getString("o_date");
            String customerID = rst.getString("c_id");
            String itemCode = rst.getString("b_id");
            int qty = rst.getInt("qty");
            double unitPrice = rst.getDouble("unit_price");

            CustomEntity customEntity = new CustomEntity(oid1, LocalDate.parse(date), customerID, itemCode, qty, unitPrice);
            allRecords.add(customEntity);
        }
        return allRecords;
    }
}
