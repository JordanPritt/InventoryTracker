package com.wgu.inventorytracker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddProductController {

    @FXML
    ToggleGroup inHouseOrOutsourced;
    @FXML
    TextField productName;
    @FXML
    TextField productInventory;
    @FXML
    TextField productCost;
    @FXML
    TextField productMin;
    @FXML
    TextField productMax;
    @FXML
    TextField productMachineOrCompanyName;

    @FXML
    Button saveProductButton;
    @FXML
    Button cancelProductButton;

    @FXML
    private void changeMachineOrCompanyName(ActionEvent event) {

    }

    @FXML
    private void saveNewProduct(ActionEvent event) {

    }

    @FXML
    private void cancelAddProduct(ActionEvent event) {
        Stage stage = (Stage)cancelProductButton.getScene().getWindow();
        stage.close();
    }
}
