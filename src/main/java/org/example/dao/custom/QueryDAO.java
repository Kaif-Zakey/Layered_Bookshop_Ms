package org.example.dao.custom;




import org.example.dao.SuperDAO;
import org.example.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> searchOrder(String oid) throws SQLException, ClassNotFoundException;
}
