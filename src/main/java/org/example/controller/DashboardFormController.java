package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.LogBO;
import org.example.dao.custom.LogDAO;
import org.example.dao.custom.impl.LogDAOImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardFormController extends ManageLoginformController{



        public AnchorPane rootNode;
        @FXML
        private Label lblProfile;

        @FXML
        private  AnchorPane root2;





       public void initialize()throws SQLException {
            lblProfile.setText(ManageLoginformController.name);
        }


        @FXML
        void btnBooksOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/book_form.fxml"));
            root2.getChildren().clear();
            root2.getChildren().add(anchorPane);

        }


        @FXML
        void btnDashBoardOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            rootNode.getChildren().clear();
            rootNode.getChildren().add(anchorPane);

        }
        @FXML
        void btnAuthorOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/author_form.fxml"));
            root2.getChildren().clear();
            root2.getChildren().add(anchorPane);
        }
        @FXML
        void btnInventoryOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/inventory_form.fxml"));
            root2.getChildren().clear();
            root2.getChildren().add(anchorPane);
        }
        @FXML
        void btnCustomerOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
            root2.getChildren().clear();
            root2.getChildren().add(anchorPane);

        }


        @FXML
        void btnExitOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login Form");

        }


        @FXML
        void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/placeOrder_form.fxml"));
            root2.getChildren().clear();
            root2.getChildren().add(anchorPane);

        }

    }



