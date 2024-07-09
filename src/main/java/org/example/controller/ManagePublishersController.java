package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
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
import org.example.bo.custom.PublishersBO;
import org.example.dto.BookDTO;
import org.example.dto.InventoryDTO;
import org.example.dto.PublishersDTO;
import org.example.entity.Book;
import org.example.entity.Inventory;
import org.example.entity.Publishers;
import org.example.view.tdm.InventoryTM;
import org.example.view.tdm.PublishersTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagePublishersController {

    @FXML
    private Button btnDelete;

    @FXML
    private JFXComboBox<String> cmbBookID;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private Label lblBookName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<PublishersTM> tblItem;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtCode1;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;
    PublishersBO publishersBO = (PublishersBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PUBLISHERS);

    BookBO bookBO = (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    public void initialize(){
        setCellValueFactory();
        LoadAllPublishers();
        LoadBookIds();
        txtCode.setDisable(true);
    }

    private void LoadBookIds(){
        try {

            ArrayList<BookDTO> allItems = publishersBO.getAllItems();
            for (BookDTO i : allItems) {
                cmbBookID.getItems().add(i.getId());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("Pid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bId"));

    }

    private void LoadAllPublishers(){
        tblItem.getItems().clear();
        try {

            ArrayList<PublishersDTO> allPublishers = publishersBO.getAllPublishers();

            for (PublishersDTO c : allPublishers) {
                tblItem.getItems().add(new PublishersTM(c.getpId(), c.getName(), c.getAddress(),c.getPhoneNumber(),c.getbId()));
            }
            getCurrentPublishersId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getCurrentPublishersId() throws ClassNotFoundException {
        try {
            String currentId = publishersBO.generateNewID();

            String nextCustomerId = generateNextPublishersId(currentId);
            txtCode.setText(nextCustomerId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateNextPublishersId(String currentId) {
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
    private void Clear() throws ClassNotFoundException {
        txtCode.clear();
        txtCode1.clear();
        txtAddress.clear();
        txtName.clear();
        txtPhoneNumber.clear();


        getCurrentPublishersId();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCode.getText();
        String name = txtName.getText();

        try {
            if (!name.matches("[A-Za-z0-9 ]+")){
                String code = tblItem.getSelectionModel().getSelectedItem().getpId();
                //String bid = tblInventory.getSelectionModel().getSelectedItem().getbId();
                if (publishersBO.delete(code)) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    LoadAllPublishers();
                    tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
                    tblItem.getSelectionModel().clearSelection();
                }else {
                    new Alert(Alert.AlertType.ERROR,"not Deleted").show();
                }

            } else if (publishersBO.delete(id)) {
                LoadAllPublishers();
                Clear();
            }

        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR,"not found!").show();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCode.getText();

        if (!txtName.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name !").show();
            txtName.requestFocus();
            return;
        } else if (!txtAddress.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Address Input !").show();
            txtAddress.requestFocus();
            return;
        }
        try {
            String name = txtName.getText();
            String Address = txtAddress.getText();
            String phonenumber = txtPhoneNumber.getText();
            String bId = cmbBookID.getValue();

            if (!publishersBO.exist(id)) {
                publishersBO.add(new PublishersDTO(id,name,Address,phonenumber,bId));
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                LoadAllPublishers();
                Clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Already Id Exist").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed To Save ").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnActon(ActionEvent event) {
        String id= txtCode1.getText();
        try {
            Publishers publishers = publishersBO.searchById(id);
            String bId =publishers.getbId();


            if (publishers != null) {
                txtCode.setText(publishers.getpId());
                txtName.setText(String.valueOf(publishers.getName()));
                txtAddress.setText(publishers.getAddress());
                txtPhoneNumber.setText(publishers.getPhoneNumber());
                cmbBookID.getSelectionModel().select(publishers.getbId());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tblItem.getSelectionModel().clearSelection();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id=txtCode.getText();


        if (!txtAddress.getText().matches("[A-Za-z0-9 ]+")){
            new Alert(Alert.AlertType.ERROR,"Inavlid Address Input").show();
            txtAddress.requestFocus();
            return;
        } else if (!txtName.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR,"Inavlid Name Input").show();
            txtName.requestFocus();
            return;
        }

        try {
            String address = txtAddress.getText();
            String name = txtName.getText();
            String phoneNumber = txtPhoneNumber.getText();


            if(publishersBO.exist(id)){
                publishersBO.update(new PublishersDTO(id,name,address,phoneNumber,cmbBookID.getValue()));
                new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
                LoadAllPublishers();
                Clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"This Publisher Id not exist!").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Failed To Upadte !").show();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void cmbBookIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (cmbBookID!=null){
            String bId =cmbBookID.getValue();
            Book a=bookBO.searchById(bId);
            if(a!=null){
                lblBookName.setText(a.getDescription());
            }else {
                lblBookName.setText("no Name got");
            }
        }
    }

    @FXML
    void txtCodeOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDescriptionOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtQtyOnHandOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtUnitPriceOnKeyReleased(KeyEvent event) {

    }

}
