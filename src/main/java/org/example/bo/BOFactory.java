package org.example.bo;

import org.example.bo.custom.impl.*;
import org.example.entity.Customer;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory==null)?boFactory =new BOFactory():boFactory;
    }

    public enum BOTypes{
        CUSTOMER,BOOK,AUTHOR,LOG,REGISTER,RESET,INVENTORY,PO,
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case BOOK:
                return new BookBOImpl();
            case AUTHOR:
                return new AuthorBOImpl();
            case LOG:
                return new LogBOImpl();
            case REGISTER:
                return  new RegisterBOImpl();
            case RESET:
                return new ResetBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case PO:
                return new PurchaseOrderBOImpl();

            default:
                return null;
        }
    }
}
