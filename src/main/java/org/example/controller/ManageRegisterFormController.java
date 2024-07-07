package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.RegisterBO;
import org.example.dao.DAOFactory;
import org.example.entity.User;

import java.sql.SQLException;

public class ManageRegisterFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    RegisterBO registerBO = (RegisterBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REGISTER);

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

       if (!txtUserId.getText().matches("[A-Za-z0-9 ]+")){
           new Alert(Alert.AlertType.ERROR,"Invalid UserId Input !");
           txtUserId.requestFocus();
           return;
       } else if (!txtName.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")) {
           new Alert(Alert.AlertType.ERROR,"Invalid Name Input !").show();
           txtName.requestFocus();
           return;
       } else if (!txtPassword.getText().matches("[A-Za-z0-9 ]+")) {
           new Alert(Alert.AlertType.ERROR,"Invalid Password Input!").show();
           txtPassword.requestFocus();
           return;
       }

       String UserId   = txtUserId.getText();
       String Name     = txtName.getText();
       String Password = txtPassword.getText();

       try{
           if (registerBO.ExistUser(UserId)){
               new Alert(Alert.AlertType.ERROR,"Already UserId exist").show();
           }else {
               registerBO.SaveUser(new User(UserId,Name,Password));
               new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();
               Clear();
           }


       }catch (SQLException e){
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }catch (ClassNotFoundException e){
           e.printStackTrace();
       }
    }

    private void Clear(){
        txtUserId.clear();
        txtName.clear();
        txtPassword.clear();
    }

}
