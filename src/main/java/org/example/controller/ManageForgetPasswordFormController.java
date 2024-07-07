package org.example.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.bo.BOFactory;
import org.example.bo.custom.ResetBO;
import org.example.entity.User;

import java.sql.SQLException;

public class ManageForgetPasswordFormController {

    @FXML
    private JFXButton btnReset;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtUserId;

    ResetBO resetBO = (ResetBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESET);

    @FXML
    void btnResetOnAction(ActionEvent event) {
        String id = txtUserId.getText();
        String name = txtName.getText();
        String Password = txtNewPassword.getText();

        if (!txtUserId.getText().matches("[A-Za-z0-9 ]+")){
            new Alert(Alert.AlertType.ERROR,"Invalid id Input!").show();
            txtUserId.requestFocus();
            return;

        } else if (!txtName.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")) {
            new Alert(Alert.AlertType.ERROR,"Invalid Name Input!").show();
            txtName.requestFocus();
            return;

        } else if (!txtNewPassword.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR,"Password Should only include letters and Numbers").show();
            txtNewPassword.requestFocus();
            return;
        }

        try {

            if (resetBO.ExistUser(id)){
                resetBO.Update(new User(id,name,Password));
                new Alert(Alert.AlertType.ERROR,"Password Changed").show();

            }else {
                new Alert(Alert.AlertType.ERROR,"UserId Not Found").show();
            }


        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }


    }

}
