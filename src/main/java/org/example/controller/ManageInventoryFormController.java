package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.InventoryBO;
import org.example.dao.custom.impl.BookDAOImpl;
import org.example.dto.BookDTO;
import org.example.dto.InventoryDTO;
import org.example.entity.Book;
import org.example.entity.Inventory;
import org.example.util.Regex;
import org.example.view.tdm.BookTM;
import org.example.view.tdm.InventoryTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageInventoryFormController {

    @FXML
    private JFXComboBox<String> cmbBookCode;
    @FXML
    private TextField txtId1;
    @FXML
    private TableColumn<?, ?> colBId;


    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<InventoryTM> tblInventory;



    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtQuantity;

    InventoryBO inventoryBO = (InventoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INVENTORY);
    BookBO bookBO = (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    public void initialize() throws ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colBId.setCellValueFactory(new PropertyValueFactory<>("bId"));


        loadAlLInventory();
        LoadBookIds();
    }

    private void LoadBookIds(){
        try {

            ArrayList<BookDTO> allItems = bookBO.getAllItems();
            for (BookDTO i : allItems) {
                cmbBookCode.getItems().add(i.getId());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadAlLInventory() {
        tblInventory.getItems().clear();
        try {

            ArrayList<InventoryDTO> allInventory = inventoryBO.getAllInventory();

            for (InventoryDTO c : allInventory) {
                tblInventory.getItems().add(new InventoryTM(c.getId(), c.getQty(), c.getLocation(),c.getbId()));
            }
            getCurrentInventoryId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void getCurrentInventoryId() throws ClassNotFoundException {
        try {
            String currentId = inventoryBO.generateNewID();

            String nextCustomerId = generateNextBookId(currentId);
            txtId.setText(nextCustomerId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateNextBookId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("0");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "0" + ++idNum;
        }
        return "01";
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() ->primaryStage.sizeToScene());

    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        Clear();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event){
        String id= txtId1.getText();
        try {
            Inventory inventory = inventoryBO.searchById(id);
            String bId =inventory.getbId();


            if (inventory != null) {
                txtId.setText(inventory.getId());
                txtQuantity.setText(String.valueOf(inventory.getQty()));
                txtLocation.setText(inventory.getLocation());
                cmbBookCode.getSelectionModel().select(inventory.getbId());
                lblDescription.setText(bookBO.searchById(bId).getDescription());
                lblQtyOnHand.setText(String.valueOf(bookBO.searchById(bId).getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tblInventory.getSelectionModel().clearSelection();

    }

    private void Clear() throws ClassNotFoundException {
        txtId.clear();
        txtId1.clear();
        txtLocation.clear();
        txtQuantity.clear();



        getCurrentInventoryId();
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        String location = txtLocation.getText();

        try {
            if (!location.matches("^[A-Za-z]+(?: [A-Za-z]+)*$")){
                String code = tblInventory.getSelectionModel().getSelectedItem().getId();
                //String bid = tblInventory.getSelectionModel().getSelectedItem().getbId();
                if (inventoryBO.delete(code)) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    loadAlLInventory();
                    tblInventory.getItems().remove(tblInventory.getSelectionModel().getSelectedItem());
                    tblInventory.getSelectionModel().clearSelection();
                }else {
                    new Alert(Alert.AlertType.ERROR,"not Deleted").show();
                }

            } else if (inventoryBO.delete(id)) {
                loadAlLInventory();
                Clear();
            }

        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR,"not found!").show();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();


        if (!txtQuantity.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid QTY !").show();
            txtQuantity.requestFocus();
            return;
        } else if (!txtLocation.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Location Input !").show();
            txtLocation.requestFocus();
            return;
        }

        try {
            int qty = Integer.parseInt(txtQuantity.getText());
           String location = txtLocation.getText();


            if (inventoryBO.exist(id)) {
                new Alert(Alert.AlertType.ERROR, "Already " + id + "Id Taken").show();

            } else if (!inventoryBO.exist(id)) {
                inventoryBO.add(new InventoryDTO(id,qty,location,cmbBookCode.getValue()));
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                loadAlLInventory();
                Clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed To Save ").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id=txtId.getText();


        if (!txtLocation.getText().matches("[A-Za-z0-9 ]+")){
            new Alert(Alert.AlertType.ERROR,"Inavlid Location Input").show();
            txtLocation.requestFocus();
            return;
        } else if (!txtQuantity.getText().matches("^[0-9]{1,5}$")) {
            new Alert(Alert.AlertType.ERROR,"Inavlid Qty Input").show();
            txtQuantity.requestFocus();
            return;
        }

        try {
           String location = txtLocation.getText();
           int qty = Integer.parseInt(txtQuantity.getText());


            if(inventoryBO.exist(id)){
                inventoryBO.update(new InventoryDTO(id,qty,location,cmbBookCode.getValue()));
                new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
                loadAlLInventory();
                Clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"This Inventory not exist!").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Failed To Upadte !").show();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    @FXML
    void cmbBookCodeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    if (cmbBookCode!=null){
        String bId =cmbBookCode.getValue();
        Book a=bookBO.searchById(bId);
        if(a!=null){
            lblDescription.setText(a.getDescription());
          String qty= String.valueOf(a.getQtyOnHand());
            lblQtyOnHand.setText(qty);
        }
    }
    }

    @FXML
    void txtCodeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.ID,txtId1);

    }

    @FXML
    void txtLocationOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.DESCRIPTION, txtLocation);

    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.QTYONHAND, txtQuantity);

    }



}
