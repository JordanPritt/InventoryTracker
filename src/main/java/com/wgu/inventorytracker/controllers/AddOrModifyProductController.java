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

import static com.wgu.inventorytracker.utils.Helpers.getFilteredParts;
import static com.wgu.inventorytracker.utils.Helpers.isEmptySearch;

/**
 * A class to control the routes and logic for adding or modifying a Product.
 * <p>
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
    private Button cancelButton;

    private Product product;
    private Product newProduct = new Product(generateOrGetId(), null, 0, 0, 0, 0);
    private boolean isModifying;

    @FXML
    private void filterPartTable(KeyEvent ignoredEvent) {
        if (isEmptySearch(partSearchText)) {
            partTable.setItems(Inventory.getAllParts());
        } else {
            ObservableList<Part> filteredParts = getFilteredParts(partSearchText);

            if (filteredParts.size() == 0) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Couldn't Find Part");
                errorAlert.setContentText("Sorry, " + partSearchText.getText() + " was not found.");
                errorAlert.showAndWait();
            } else {
                partTable.setItems(filteredParts);
            }
        }
    }

    @FXML
    private void saveProduct() {
        try {
            if (isModifying) {
                newProduct = product;
                newProduct.setId(generateOrGetId());
            }

            if (Integer.parseInt(prodMin.getText()) > Integer.parseInt(prodMax.getText()))
                throw new IllegalArgumentException("Min cannot exceed Max value.");

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
        } catch (IllegalArgumentException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Couldn't Save Product");
            errorAlert.setContentText("Please ensure that the Min field is less than the Max.");
            errorAlert.showAndWait();
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Couldn't Save Product");
            errorAlert.setContentText("Please ensure that all fields are properly set before trying to save again.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void cancelForm(ActionEvent ignoredEvent) {
        closeStage();
    }

    /**
     * RUNTIME ERROR for Section B first bullet:
     * Ran into an issue where there could be a duplicate part, to resolve this
     * I added a contains check that lets me prompt the user when they try to
     * add in a duplicate item. This will avoid duplicates that are necessary.
     *
     * @param ignoredEvent the event in the event handler.
     */
    @FXML
    private void addPartToProduct(ActionEvent ignoredEvent) {
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
    private void removeAssociatedPart(ActionEvent ignoredEvent) {
        try {
            Part selectedItem = associatedPartTable.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                Alert errorAlert = new Alert(Alert.AlertType.WARNING);
                errorAlert.setHeaderText("No Part Selected");
                errorAlert.setContentText("Please select a part to remove.");
                errorAlert.showAndWait();
            }

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
                        if (isModifying)
                            product.deleteAssociatedPart(selectedItem);
                        else
                            newProduct.deleteAssociatedPart(selectedItem);
                    });
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Could Not Remove Part");
            errorAlert.setContentText("Sorry, part was not able to be remove from product. Please try again later.");
            errorAlert.showAndWait();
        }
    }

    /**
     * Initializes the form.
     *
     * @param url            Provided url to view resource.
     * @param resourceBundle Provided bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(FXCollections.observableArrayList());
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodId.setDisable(true);
    }

    /**
     * Sets the product to modify.
     *
     * @param product the provided product.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Sets a flag to indicate if modify or not.
     *
     * @param isModifying boolean to flag with.
     */
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

    /**
     * Sets up the form with provided data or as a new addition.
     */
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
