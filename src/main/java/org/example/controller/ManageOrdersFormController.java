package org.example.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.PurchaseOrderBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.OrderDetailsDAO;
import org.example.dao.custom.Orders1DAO;
import org.example.dto.CustomerDTO;
import org.example.dto.Order2DTO;
import org.example.dto.OrderDetailDTO;
import org.example.entity.Customer;
import org.example.entity.OrderDetails;
import org.example.view.tdm.CustomerTM;
import org.example.view.tdm.Order1TM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrdersFormController {

    @FXML
    private TableColumn<?, ?> colB_id;

    @FXML
    private TableColumn<?, ?> colO_id;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colU_price;

    @FXML
    private Label lblCid;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<Order1TM> tblBookorder;

    @FXML
    private TextField txtid;

  //  PurchaseOrderBO purchaseOrderBO  = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
    OrderDetailsDAO orderDetailsDAO=(OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
   Orders1DAO orders1DAO=(Orders1DAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER1);
    public void initialize() throws ClassNotFoundException {
        tblBookorder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("boId"));
        tblBookorder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblBookorder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblBookorder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("orId"));

        loadAllOrders();
    }
    private void loadAllOrders() {
        tblBookorder.getItems().clear();
        try {

            ArrayList<OrderDetails> allOrders = orderDetailsDAO.getAll();

            for (OrderDetails c : allOrders) {
                tblBookorder.getItems().add(new Order1TM(c.getoId(), c.getbId(), c.getQty(),c.getUnitPrice()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

        @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.rootNode.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() ->primaryStage.sizeToScene());

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ids = txtid.getText();
        try {
            if (!txtid.getText().matches("[A-Za-z0-9 ]+")) {
                String code = tblBookorder.getSelectionModel().getSelectedItem().getOrId();

                // String code = tblItem.getSelectionModel().getSelectedItem().getId();
                if (orders1DAO.delete(code)) {
                    orders1DAO.deleteOd(code);
                    new Alert(Alert.AlertType.CONFIRMATION, "Orders Deleted!").show();
                    loadAllOrders();
                    tblBookorder.getItems().remove(tblBookorder.getSelectionModel().getSelectedItem());
                    tblBookorder.getSelectionModel().clearSelection();
                }


            } else if (orders1DAO.delete(ids)) {
                orders1DAO.deleteOd(ids);
                loadAllOrders();
               // Clear();
                new Alert(Alert.AlertType.CONFIRMATION, "Orders Deleted!").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the orders: ").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id= txtid.getText();
        try {
            Order2DTO order2DTO = orders1DAO.searchByID(id);
            if (order2DTO != null) {
                lblCid.setText(order2DTO.getCid());
                lblDate.setText(order2DTO.getDate().toString());
                txtid.setText(order2DTO.getOid());
             //   txtAddress.setText(String.valueOf(customer.getAddress()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Order not found!"+id).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tblBookorder.getSelectionModel().clearSelection();
    }


}
