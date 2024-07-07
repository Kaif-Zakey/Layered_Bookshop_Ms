package org.example.controller;

import com.beust.ah.A;
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
import org.example.bo.custom.CustomerBo;
import org.example.dto.BookDTO;
import org.example.dto.CustomerDTO;
import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.util.Regex;
import org.example.view.tdm.BookTM;
import org.example.view.tdm.CustomerTM;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.util.regex.Pattern.matches;

public class ManageBookFormComtroller {

    @FXML
    private Button btnDelete;

    @FXML
    private TableColumn<?, ?> colDecription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BookTM> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtCode1;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;
    BookBO bookBO=(BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    public void initialize() throws ClassNotFoundException {
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
txtCode.setDisable(true);
    loadAlLBooks();
    }


    private void loadAlLBooks() {
        tblItem.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<BookDTO> allBooks = bookBO.getAllItems();

            for (BookDTO c : allBooks) {
                tblItem.getItems().add(new BookTM(c.getId(), c.getUnitPrice(), c.getDescription(),c.getQtyOnHand()));
            }
            getCurrentBookId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getCurrentBookId() throws ClassNotFoundException {
        try {
            String currentId = bookBO.generateNewCode();

            String nextCustomerId = generateNextBookId(currentId);
            txtCode.setText(nextCustomerId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextBookId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("");  //" ", "2"
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
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        getCurrentBookId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id=txtCode1.getText();

    try {
        if (!txtDescription.getText().matches("[A-Za-z0-9 ]+")) {
            String code = tblItem.getSelectionModel().getSelectedItem().getId();

                // String code = tblItem.getSelectionModel().getSelectedItem().getId();
                if (bookBO.delete(code)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    loadAlLBooks();
                    tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
                    tblItem.getSelectionModel().clearSelection();
                }


        } else if (bookBO.delete(id)) {
            loadAlLBooks();
            Clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
        }
    }catch (SQLException e) {
        new Alert(Alert.AlertType.ERROR, "Failed to delete the item: ").show();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}



    boolean existBook(String id) throws SQLException, ClassNotFoundException {
        return bookBO.existItem(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCode.getText();


        if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid UnitPrice !").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtDescription.getText().matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Inavlid Description Input !").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^[0-9]{1,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Qty_On_Hand  input! ").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        try {
                String Description = txtDescription.getText();
                int qtyOnHand= Integer.parseInt(txtQtyOnHand.getText());
                double UnitPrice= Double.parseDouble(txtUnitPrice.getText());

                if (existBook(id)) {
                    new Alert(Alert.AlertType.ERROR, "Already " + id + "Id Taken").show();

                } else if (!existBook(id)) {
                    bookBO.saveItem(new BookDTO(id, UnitPrice, Description, qtyOnHand));
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                    loadAlLBooks();
                    Clear();
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
            Book book = bookBO.searchById(id);
            if (book != null) {
                txtCode.setText(book.getId());
                txtUnitPrice.setText(String.valueOf(book.getUnitPrice()));
                txtDescription.setText(book.getDescription());
                txtQtyOnHand.setText(String.valueOf(book.getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "book not found!").show();
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


    if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")){
        new Alert(Alert.AlertType.ERROR,"Inavlid UnitPrice Input").show();
       txtUnitPrice.requestFocus();
       return;
    } else if (!txtDescription.getText().matches("[A-Za-z0-9 ]+")) {
        new Alert(Alert.AlertType.ERROR,"Inavalid Description Input").show();
       txtDescription.requestFocus();
       return;
    } else if (!txtQtyOnHand.getText().matches("^[0-9]{1,5}$")) {
        new Alert(Alert.AlertType.ERROR,"Inavlid QtyOnHand Input").show();
        txtQtyOnHand.requestFocus();
    return;
    }

    try {
        double UnitPrice =Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand=Integer.parseInt(txtQtyOnHand.getText());
        String Description=txtDescription.getText();

        if(existBook(id)){
            bookBO.updateItem(new BookDTO(id,UnitPrice,Description,qtyOnHand));
           new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
            loadAlLBooks();
            Clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"This BookId not exist!").show();
        }

    }catch (SQLException e){
        new Alert(Alert.AlertType.ERROR,"Failed To Upadte !").show();
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }
 }

    @FXML
    void txtCodeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.ID,txtCode);

    }

    @FXML
    void txtDescriptionOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.DESCRIPTION, txtDescription);

    }

    @FXML
    void txtQtyOnHandOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.QTYONHAND, txtQtyOnHand);

    }

    @FXML
    void txtUnitPriceOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(org.example.util.TextField.UNITPRICE, txtUnitPrice);

    }

}
