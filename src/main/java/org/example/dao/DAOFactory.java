package org.example.dao;

import org.example.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }


    public enum DAOTypes {
        CUSTOMER,BOOK,AUTHOR,LOG,REGISTER,RESET,INVENTORY,ORDER,ORDER_DETAIL,QUERY_DAO,ORDER1,PUBLISHERS
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case AUTHOR:
                return  new AuthorDAOImpl();
            case LOG:
                return  new LogDAOImpl();
            case REGISTER:
                return  new RegisterDAOImpl();
            case RESET:
                return new ResetDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailsDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            case ORDER1:
                return new Order1DAOImpl();
            case PUBLISHERS:
                return new PublishersDAOImpl();
            default:
                return null;
        }


    }
}