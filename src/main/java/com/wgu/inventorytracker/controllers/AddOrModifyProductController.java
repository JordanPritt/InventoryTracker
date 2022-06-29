package com.wgu.inventorytracker.controllers;

import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Part;
import com.wgu.inventorytracker.models.Product;
import com.wgu.inventorytracker.utils.IdGenerator;
import javafx.collections.FXCollections;
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

/**
 * FUTURE ENHANCEMENT for Section B second bullet point:
 * Could separate this file out into two separate UIs and a service class that houses shared logic. The benefit to this
 * enhancement would be to have fewer flags to check for in the logic body.
 */
public class AddOrModifyProductController implements Initializable {
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
    private Product newProduct = new Product(generateOrGetId(), null, 0, 0, 0, 0);
    private boolean isModifying;

    @FXML
    private void filterPartTable(KeyEvent event) {
        if (partSearchText.getText().trim().equals("")) {
            partTable.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> filteredParts = Inventory.getAllParts().filtered(part -> {
            try {
                if (part.getName().toLowerCase().contains(partSearchText.getText().toLowerCase())) {
                    return true;
                } else {
                    return part.getId() == Integer.parseInt(partSearchText.getText());
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        });

        partTable.setItems(filteredParts);

        if (filteredParts.size() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Couldn't Find Part");
            errorAlert.setContentText("Sorry, " + partSearchText.getText() + " was not found.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void saveProduct() {
        try {
            if (isModifying) {
                newProduct = product;
                newProduct.setId(generateOrGetId());
            }

            newProduct.setName(prodName.getText());
            newProduct.setPrice(Double.parseDouble(prodPrice.getText()));
            newProduct.setStock(Integer.parseInt(prodInv.getText()));
            newProduct.setMin(Integer.parseInt(prodMin.getText()));
            newProduct.setMax(Integer.parseInt(prodMax.getText()));

            if (isModifying)
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(product), newProduct);
            else
                Inventory.addProduct(newProduct);

            closeStage();
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Couldn't Save Product");
            errorAlert.setContentText("Please ensure that all fields are properly set before trying to save again.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void cancelForm(ActionEvent event) {
        closeStage();
    }

    /**
     * RUNTIME ERROR for Section B first bullet:
     * Ran into an issue where there could be a duplicate part, to resolve this
     * I added a contains check that lets me prompt the user when they try to
     * add in a duplicate item. This will avoid duplicates that are unecessary.
     *
     * @param event
     */
    @FXML
    private void addPartToProduct(ActionEvent event) {
        try {
            Part selectedItem = partTable.getSelectionModel().getSelectedItem();
            boolean isDuplicate = product != null ? product.getAllAssociatedParts().contains(selectedItem) :
                    newProduct.getAllAssociatedParts().contains(selectedItem);

            if (isDuplicate) {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setHeaderText("Duplicate Entry");
                warningAlert.setContentText("It appears that this part is already selected.");
                warningAlert.showAndWait();
            } else {
                if (isModifying) {
                    product.addAssociatedPart(selectedItem);
                } else {
                    newProduct.addAssociatedPart(selectedItem);
                    associatedPartTable.setItems(newProduct.getAllAssociatedParts());
                }
            }
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("An Error Occurred");
            errorAlert.setContentText("Sorry, something appears to be incorrect. Please try again.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void removeAssociatedPart(ActionEvent event) {
        try {
            Part selectedItem = associatedPartTable.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                errorAlert.setHeaderText("No Part Selected");
                errorAlert.setContentText("Please select a part to remove.");
                errorAlert.showAndWait();
            }

            if (isModifying) {
                product.deleteAssociatedPart(selectedItem);
            } else {
                newProduct.deleteAssociatedPart(selectedItem);
            }
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Could Not Remove Part");
            errorAlert.setContentText("Sorry, part was not able to be remove from product. Please try again later.");
            errorAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));

        associatedPartTable.setItems(FXCollections.observableArrayList());
        associatedPartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Integer>("price"));

        prodId.setDisable(true);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setIsModifying(boolean isModifying) {
        this.isModifying = isModifying;
    }

    private int generateOrGetId() {
        if (isModifying) return product.getId();
        else return IdGenerator.nextProductId();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setupForm() {
        if (isModifying) {
            prodId.setText(String.valueOf(product.getId()));
            prodInv.setText(String.valueOf(product.getStock()));
            prodPrice.setText(String.valueOf(product.getPrice()));
            prodMin.setText(String.valueOf(product.getMin()));
            prodMax.setText(String.valueOf(product.getMax()));
            prodName.setText(product.getName());
            associatedPartTable.setItems(product.getAllAssociatedParts());
        } else {
            associatedPartTable.setItems(newProduct.getAllAssociatedParts());
        }
    }
}
