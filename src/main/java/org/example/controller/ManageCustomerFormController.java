package org.example.controller;

import com.beust.ah.A;
import com.jfoenix.controls.JFXButton;
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
import org.example.bo.custom.CustomerBo;
import org.example.dto.CustomerDTO;
import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.util.Regex;
import org.example.view.tdm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCustomerFormController {

    @FXML
    private TextField txtId1;

    @FXML
    private Button btnSearch;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;


    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Label lblCustomerId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    CustomerBo customerBO=(CustomerBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public void initialize() throws ClassNotFoundException {
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));



        txtId.setDisable(true);
        loadAllCustomers();

    }


    private void loadAllCustomers() {
        tblCustomer.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();

            for (CustomerDTO c : allCustomers) {
                tblCustomer.getItems().add(new CustomerTM(c.getId(), c.getName(), c.getAddress(),c.getEmail()));
            }
        getCurrentCustomerId();
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
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());

    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        Clear();
    }

    private void Clear() throws ClassNotFoundException {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        getCurrentCustomerId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ids = txtId.getText();
        try {
            if (!txtName.getText().matches("[A-Za-z0-9 ]+")) {
                String code = tblCustomer.getSelectionModel().getSelectedItem().getId();

                // String code = tblItem.getSelectionModel().getSelectedItem().getId();
                if (customerBO.deleteCustomer(code)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
                    loadAllCustomers();
                    tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
                    tblCustomer.getSelectionModel().clearSelection();
                }


            } else if (customerBO.deleteCustomer(ids)) {
                loadAllCustomers();
                Clear();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the Customer: ").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        }else  if (!email.matches("^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$")){
            new Alert(Alert.AlertType.ERROR, "Email should be Correct").show();
            txtEmail.requestFocus();
            return;
        }

        try {
            if (!existCustomer(id)) {
                customerBO.addCustomer(new CustomerDTO(id, name, address, email));
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                loadAllCustomers();
                Clear();
            } else if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "Already Exist").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Failed to Save!").show();
        }catch (ClassNotFoundException e){
           e.printStackTrace();
        }
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();


        if (!txtAddress.getText().matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address Input!").show();
            txtAddress.requestFocus();
            return;
        } else if (!txtEmail.getText().matches("^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Input").show();
            txtEmail.requestFocus();
            return;
        } else if (!txtName.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name Input").show();
            txtName.requestFocus();
            return;
        }

        try{
            String name = txtName.getText();
            String Address = txtAddress.getText();
            String email = txtEmail.getText();

            if(existCustomer(id)) {
                customerBO.updateCustomer(new CustomerDTO(id,name,Address,email));
                new Alert(Alert.AlertType.CONFIRMATION,"updated!").show();
                loadAllCustomers();
                Clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"customer failed to update").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Not found").show();
        }catch (ClassNotFoundException e){
           e.printStackTrace();
        }
        }


    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);
    }



    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.ADDRESS,txtAddress);

    }

    @FXML
    void txtCustomerIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.ID, txtId);

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.EMAIL, txtEmail);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.NAME, txtName);

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
      String id =txtId.getText();
      try {
          searhId(id);
      }catch (Exception e) {
          new Alert(Alert.AlertType.ERROR, "Id Not Found").show();
      }
    }

    public void searhId(String id){
        try {
            Customer customer = customerBO.searchById(id);
            if (customer != null) {
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtEmail.setText(customer.getEmail());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void getCurrentCustomerId() throws ClassNotFoundException {
        try {
            String currentId = customerBO.generateNewCustomerID();

            String nextCustomerId = generateNextCustomerId(currentId);
            txtId.setText(nextCustomerId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id= txtId1.getText();
        try {
            Customer customer = customerBO.searchById(id);
            if (customer != null) {
                txtId.setText(customer.getId());
                txtName.setText(String.valueOf(customer.getName()));
                txtEmail.setText(customer.getEmail());
                txtAddress.setText(String.valueOf(customer.getAddress()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found!"+id).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        tblCustomer.getSelectionModel().clearSelection();
    }

    private String generateNextCustomerId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("0");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "0" + ++idNum;
        }
        return "O1";
    }


}


