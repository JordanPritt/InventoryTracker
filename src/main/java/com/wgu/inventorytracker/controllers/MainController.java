package com.wgu.inventorytracker.controllers;

import com.wgu.inventorytracker.InventoryApplication;
import com.wgu.inventorytracker.models.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partStock;
    @FXML
    private TableColumn<Part, Integer> partPrice;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productId;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productStock;
    @FXML
    private TableColumn<Product, Integer> productPrice;

    @FXML
    private TextField partFilter;
    @FXML
    private TextField productFilter;
    @FXML
    private Button addPartButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyPartButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button exitApplicationButton;

    @FXML
    private void exitApplication(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void filterPartTable(KeyEvent event) {
        // need to highlight when found by id
        ObservableList<Part> filteredParts = Inventory.getAllParts().filtered(part -> {
            try {
                if (part.getName().toLowerCase().contains(partFilter.getText().toLowerCase())) {
                    return true;
                } else return part.getId() == Integer.parseInt(partFilter.getText());
            } catch (NumberFormatException ex) {
                return false;
            }
        });

        if (partFilter.getText().trim().equals("")) {
            partTable.setItems(Inventory.getAllParts());
            return;
        }

        partTable.setItems(filteredParts);
    }

    @FXML
    private void filterProductTable(KeyEvent event) {
        ObservableList<Product> filteredProducts = Inventory.getAllProducts().filtered(product -> {
            try {
                if (product.getName().toLowerCase().contains(productFilter.getText().toLowerCase())) {
                    return true;
                } else return product.getId() == Integer.parseInt(productFilter.getText());
            } catch (NumberFormatException ex) {
                return false;
            }
        });

        if (productFilter.getText().trim().equals("")) {
            productTable.setItems(Inventory.getAllProducts());
            return;
        }

        productTable.setItems(filteredProducts);
    }

    @FXML
    private void deletePart(ActionEvent event) {
        Part selectedItem = partTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Please first select a part.");

            alert.setTitle("Delete Warning");
            alert.showAndWait();
            return;
        }

        // alert user if nothing was selected

        // confirm deletion
        ButtonType delete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Are you sure you want to delete this part?",
                delete,
                cancel);

        alert.setTitle("Delete Confirmation");
        alert.showAndWait()
                .filter(response -> response == delete)
                .ifPresent(response -> {
                    Inventory.deletePart(selectedItem);
                });
        // use inventory class
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        Product selectedItem = productTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedItem);
    }

    @FXML
    private void modifyPart(ActionEvent event) throws IOException {
        Part selectedItem = partTable.getSelectionModel().getSelectedItem();
        disablePartButtons();

        Stage newWindow = new Stage();
        newWindow.setTitle("Modify Part");

        FXMLLoader loader = new FXMLLoader(InventoryApplication.class.getResource("/add-or-modify-part-view.fxml"));
        Parent root = loader.load();
        AddOrModifyPartController controller = loader.getController();
        controller.setPart(selectedItem);
        controller.setModifying(true);

        newWindow.setScene(new Scene(root));
        newWindow.showAndWait();

        enablePartButtons();
    }

    @FXML
    private void addPartView(ActionEvent event) throws IOException {
        // disable buttons to prevent opening another scene;
        disablePartButtons();

        Stage newWindow = new Stage();
        newWindow.setTitle("Add Part");
        FXMLLoader loader = new FXMLLoader(InventoryApplication.class.getResource("/add-or-modify-part-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.showAndWait();

        enablePartButtons();
    }

    @FXML
    private void addProductView(ActionEvent event) throws IOException {
        disableProductButtons();
        disablePartButtons();

        Stage newWindow = new Stage();
        newWindow.setTitle("Add Product");
        FXMLLoader loader =
                new FXMLLoader(InventoryApplication.class.getResource("/add-or-modify-product-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.showAndWait();

        enableProductButtons();
        enablePartButtons();
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));
        partTable.getColumns().setAll(partId, partName, partStock, partPrice);

        productTable.setItems(Inventory.getAllProducts());

        productId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        productTable.getColumns().setAll(productId, productName, productStock, productPrice);
    }

    private void disablePartButtons() {
        addPartButton.setDisable(true);
        modifyPartButton.setDisable(true);
        deletePartButton.setDisable(true);
    }

    private void enablePartButtons() {
        addPartButton.setDisable(false);
        modifyPartButton.setDisable(false);
        deletePartButton.setDisable(false);
    }

    private void disableProductButtons() {
        addProductButton.setDisable(true);
        modifyProductButton.setDisable(true);
        deleteProductButton.setDisable(true);
    }

    private void enableProductButtons() {
        addProductButton.setDisable(false);
        modifyProductButton.setDisable(false);
        deleteProductButton.setDisable(false);
    }
}
