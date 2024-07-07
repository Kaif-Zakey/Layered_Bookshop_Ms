package org.example.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.bo.BOFactory;
import org.example.bo.custom.AuthorBO;
import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.util.Regex;
import org.example.view.tdm.AuthorTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageAuthorFormController {

    @FXML
    private TableColumn<?, ?> colCountry;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<AuthorTM> tblAuthor;

    @FXML
    private TextField txtAtuhorName;

    @FXML
    private TextField txtAuthorCountry;

    @FXML
    private TextField txtAuthorId;

    @FXML
    private TextField txtAuthorId1;

    AuthorBO authorBO = (AuthorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.AUTHOR);

    public void initialize() throws ClassNotFoundException {
        tblAuthor.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblAuthor.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblAuthor.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("country"));

        txtAuthorId.setDisable(true);
        loadAllAuthors();
        getCurrentAuthorId();
    }
    private void loadAllAuthors(){
        tblAuthor.getItems().clear();
        try {
            ArrayList<AuthorDTO>allAuthor = authorBO.getAllAuthor();

            for (AuthorDTO a:allAuthor){
                tblAuthor.getItems().add(new AuthorTM(a.getId(),a.getName(),a.getCountry()));
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }catch (ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void getCurrentAuthorId() throws ClassNotFoundException {
        try {
            String currentId = authorBO.generateNewCode();

            String nextCustomerId = generateNextAuthorId(currentId);
            txtAuthorId.setText(nextCustomerId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextAuthorId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("0");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "0" + ++idNum;
        }
        return "O1";
    }
    @FXML
    void btnSearchOnAction(ActionEvent event) {
    String id =txtAuthorId1.getText();
    try{
        if(authorBO.existAuthor(id)){
            Author author=authorBO.searchById(id);
            if(author!=null){
                txtAuthorId.setText(author.getId());
                txtAtuhorName.setText(author.getName());
                txtAuthorCountry.setText(author.getCountry());
            }

        }else {
            new Alert(Alert.AlertType.ERROR,"No Author found").show();
        }
    }catch (SQLException e){
        new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
    }catch (ClassNotFoundException e){
        e.printStackTrace();
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
        Platform.runLater(() ->primaryStage.sizeToScene());

    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
   Clear();
    }

    private void Clear() throws ClassNotFoundException {
        txtAuthorId.clear();
        txtAtuhorName.clear();
        txtAuthorCountry.clear();
        txtAuthorId1.clear();

        getCurrentAuthorId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
    String id = txtAuthorId.getText();
    String name = txtAtuhorName.getText();

    try {
        if (!name.matches("^[A-Za-z]+(?: [A-Za-z]+)*$")){
            String code = tblAuthor.getSelectionModel().getSelectedItem().getId();
            if (authorBO.delete(code)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllAuthors();
                tblAuthor.getItems().remove(tblAuthor.getSelectionModel().getSelectedItem());
                tblAuthor.getSelectionModel().clearSelection();
            }else {
                new Alert(Alert.AlertType.ERROR,"not Deleted").show();
            }

        } else if (authorBO.delete(id)) {
            loadAllAuthors();
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
    String id = txtAuthorId.getText();

    if (!txtAtuhorName.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")){
        new Alert(Alert.AlertType.ERROR,"Invalid Name Input").show();
        txtAtuhorName.requestFocus();
        return;
    } else if (!txtAuthorCountry.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")) {
        new Alert(Alert.AlertType.ERROR,"Invalid Country Input").show();
        txtAuthorCountry.requestFocus();
        return;
    }
    String name =txtAtuhorName.getText();
    String coutry = txtAuthorCountry.getText();

    try{
            if (authorBO.saveAuthor(new AuthorDTO(id,name,coutry))){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved !").show();
                loadAllAuthors();

            }else {
                new Alert(Alert.AlertType.WARNING,"Not Saved");
            }

            Clear();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!txtAtuhorName.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")){
            new Alert(Alert.AlertType.ERROR,"Invalid Name Input! ").show();
            txtAtuhorName.requestFocus();
            return;
        } else if (!txtAuthorCountry.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")) {
            new Alert(Alert.AlertType.ERROR,"Invalid Coutry Input").show();
            txtAuthorCountry.requestFocus();
            return;
        }
        try{
            String id = txtAuthorId.getText();
            String name = txtAtuhorName.getText();
            String coutry = txtAuthorCountry.getText();

            if (authorBO.existAuthor(id)){
                authorBO.updateAuthor(new AuthorDTO(id,name,coutry));
                new Alert(Alert.AlertType.CONFIRMATION,"Updated !").show();
                loadAllAuthors();
            }else {
             new Alert(Alert.AlertType.ERROR,"Not Exist").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void txtAuthorCountyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.NAME,txtAuthorCountry);
    }

    @FXML
    void txtAuthorIdOnKeyReleased(KeyEvent event) {
        if(!Regex.setTextColor(org.example.util.TextField.ID,txtAuthorId1)){
            new Alert(Alert.AlertType.WARNING,"Enter '0' Before Id ").show();
        }

    }

    @FXML
    void txtAuthorNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.NAME,txtAtuhorName);
    }


}
