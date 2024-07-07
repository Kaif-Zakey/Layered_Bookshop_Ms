package org.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.dto.OrderDTO;
import org.example.dto.OrderDetailDTO;
import org.example.view.tdm.OrderDetailTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManagePlaceOrderFormController {

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnPrintBill;

    @FXML
    private Button btnSave;

    @FXML
    private JFXComboBox<String> cmbBookId;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane pane;

    private String orderId;

    @FXML
    private TableView<OrderDetailTM> tblOrderCart;

    @FXML
    private TextField txtQty;
    PurchaseOrderBO purchaseOrderBO  = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
    public void initialize() throws SQLException, ClassNotFoundException {

        tblOrderCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderCart.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderCart.getItems().remove(param.getValue());
                tblOrderCart.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);
        lblOrderDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        lblCustomerName.setFocusTraversable(false);
        lblDescription.setFocusTraversable(false);
        lblUnitPrice.setFocusTraversable(false);
        lblQtyOnHand.setFocusTraversable(false);
        txtQty.setOnAction(event -> btnSave.fire());
        txtQty.setEditable(false);
        btnSave.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();
            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }


                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(newValue + "");
                        lblCustomerName.setText(customerDTO.getName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                lblCustomerName.setText("");
            }
        });


        cmbBookId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {


                try {
                    if (!existItem(newItemCode + "")) {

                    }


                    BookDTO item = purchaseOrderBO.searchItem(newItemCode + "");

                    lblDescription.setText(item.getDescription());

                    lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));


                    Optional<OrderDetailTM> optOrderDetail = tblOrderCart.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    lblQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                lblDescription.setText("");
                txtQty.clear();
                lblQtyOnHand.setText("");
                lblUnitPrice.setText("");
            }
        });

        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbBookId.setDisable(true);
                cmbBookId.setValue(selectedOrderDetail.getCode());
                btnSave.setText("Update");
                lblQtyOnHand.setText(Integer.parseInt(lblQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmbBookId.setDisable(false);
                cmbBookId.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }
    public String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }
    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> allCustomers = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO c : allCustomers) {
                cmbCustomerId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            ArrayList<BookDTO> allItems = purchaseOrderBO.getAllItems();
            for (BookDTO i : allItems) {
                cmbBookId.getItems().add(i.getId());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.existItem(code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.existCustomer(id);
    }
    @FXML
    void btnAdd_OnAction(ActionEvent event) {

        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(lblQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbBookId.getSelectionModel().getSelectedItem();
        String description = lblDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total =( new BigDecimal(unitPrice).setScale(2)).multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderCart.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderCart.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderCart.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(new BigDecimal(unitPrice)).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderCart.refresh();
        } else {
            tblOrderCart.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbBookId.getSelectionModel().clearSelection();
        cmbBookId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.pane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() ->primaryStage.sizeToScene());

    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/Orders_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.pane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();


    }
    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTM detail : tblOrderCart.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblNetTotal.setText("Total: " + total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderCart.getItems().isEmpty()));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws ClassNotFoundException {
        try {
            boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                    tblOrderCart.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



        orderId = generateNewOrderId();
        lblOrderId.setText( orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbBookId.getSelectionModel().clearSelection();
        tblOrderCart.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        OrderDTO orderDTO = new OrderDTO(orderId, orderDate, customerId, orderDetails);
        return purchaseOrderBO.purchaseOrder(orderDTO);
    }
    @FXML
    void btnPrintBillOnAction(ActionEvent event) {

    }

}
