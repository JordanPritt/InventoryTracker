package com.wgu.inventorytracker.controllers;

import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Part;
import com.wgu.inventorytracker.models.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddOrModifyProduct implements Initializable {
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInv;
    @FXML
    private TableColumn<Part, Integer> partPrice;
    @FXML
    private TableColumn<Part, Integer> associatedPartId;
    @FXML
    private TableColumn<Part, String> associatedPartName;
    @FXML
    private TableColumn<Part, Integer> associatedPartInv;
    @FXML
    private TableColumn<Part, Integer> associatedPartPrice;
    @FXML
    private Label formLabel;
    @FXML
    private TextField prodId;
    @FXML
    private TextField prodName;
    @FXML
    private TextField prodInv;
    @FXML
    private TextField prodPrice;
    @FXML
    private TextField prodMax;
    @FXML
    private TextField prodMin;
    @FXML
    private TextField partSearchText;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private Button addPartButton;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private Product product;
    private boolean isModifying;

    @FXML
    private void filterPartTable(KeyEvent event) {
        ObservableList<Part> filteredParts = Inventory.getAllParts().filtered(part -> {
            try {
                if (part.getName().toLowerCase().contains(partSearchText.getText().toLowerCase())) {
                    return true;
                } else return part.getId() == Integer.parseInt(partSearchText.getText());
            } catch (NumberFormatException ex) {
                return false;
            }
        });

        if (partSearchText.getText().trim().equals("")) {
            partTable.setItems(Inventory.getAllParts());
            return;
        }

        partTable.setItems(filteredParts);
    }

    @FXML
    private void cancelForm(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setIsModifying(boolean isModifying) {
        this.isModifying = isModifying;
    }
}
