package com.wgu.inventorytracker.controllers;

import com.wgu.inventorytracker.models.InHouse;
import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Outsourced;
import com.wgu.inventorytracker.models.Part;
import com.wgu.inventorytracker.utils.IdGenerator;
import com.wgu.inventorytracker.utils.TryParse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to control the routes and logic for adding or modifying
 * a part with the associated view.
 */
public class AddOrModifyPartController implements Initializable {

    private Part partToModify = null;
    private boolean isModifying = false;

    @FXML
    Label machineOrCompanyNameLabel;
    @FXML
    ToggleGroup machineNameGroup;
    @FXML
    TextField partId;
    @FXML
    TextField partName;
    @FXML
    TextField partInventory;
    @FXML
    TextField partCost;
    @FXML
    TextField partMin;
    @FXML
    TextField partMax;
    @FXML
    TextField partMachineOrCompanyName;

    @FXML
    Button savePartButton;
    @FXML
    Button cancelPartButton;
    @FXML
    RadioButton inHouseRadio;
    @FXML
    RadioButton machineIdRadio;

    @FXML
    private void changeMachineOrCompanyName(ActionEvent ignoredEvent) {
        if (inHouseRadio.isSelected()) machineOrCompanyNameLabel.setText("Machine ID");
        else machineOrCompanyNameLabel.setText("Company Name");
    }

    @FXML
    private void savePart(ActionEvent ignoredEvent) {
        try {
            //boolean isInHouse = TryParse.isInt(partMachineOrCompanyName.getText());
            boolean isInHouse = inHouseRadio.isSelected();
            Part newPart;

            if (Integer.parseInt(partMin.getText()) > Integer.parseInt(partMax.getText()))
                throw new IllegalArgumentException("Please ensure that the Min field is less than the Max.");

            if (isInHouse) {
                boolean idIsValid = TryParse.isInt(partMachineOrCompanyName.getText());
                if (!idIsValid) throw new IllegalArgumentException("Please ensure that the Machine id is an int.");

                newPart = new InHouse(
                        generateOrGetId(),
                        partName.getText(),
                        Double.parseDouble(partCost.getText()),
                        Integer.parseInt(partInventory.getText()),
                        Integer.parseInt(partMin.getText()),
                        Integer.parseInt(partMax.getText()),
                        Integer.parseInt(partMachineOrCompanyName.getText())
                );
            } else {
                newPart = new Outsourced(
                        generateOrGetId(),
                        partName.getText(),
                        Double.parseDouble(partCost.getText()),
                        Integer.parseInt(partInventory.getText()),
                        Integer.parseInt(partMin.getText()),
                        Integer.parseInt(partMax.getText()),
                        partMachineOrCompanyName.getText()
                );
            }

            if (isModifying)
                Inventory.updatePart(Inventory.getAllParts().indexOf(partToModify), newPart);
            else
                Inventory.addPart(newPart);

            closeStage();
        } catch (IllegalArgumentException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Couldn't Save Part");
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Incorrect Value");
            errorAlert.setContentText("Sorry, one or more of the values you entered were incorrect. Please try again " +
                    "after carefully looking over the entry.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void cancelAddPart(ActionEvent ignoredEvent) {
        closeStage();
    }

    private int generateOrGetId() {
        if (isModifying) return partToModify.getId();
        else return IdGenerator.nextPartId();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelPartButton.getScene().getWindow();
        stage.close();
    }

    private void setupForm() {
        partId.setText(String.valueOf(partToModify.getId()));
        partName.setText(partToModify.getName());
        partCost.setText(String.valueOf(partToModify.getPrice()));
        partInventory.setText(String.valueOf(partToModify.getStock()));
        partMin.setText(String.valueOf(partToModify.getMin()));
        partMax.setText(String.valueOf(partToModify.getMax()));

        if (partToModify instanceof InHouse part) {
            partMachineOrCompanyName.setText(String.valueOf(part.getMachineId()));
            machineIdRadio.setSelected(false);
        } else {
            Outsourced part = (Outsourced) partToModify;
            machineIdRadio.setSelected(true);
            machineOrCompanyNameLabel.setText("Company Name");
            partMachineOrCompanyName.setText(part.getCompanyName());
        }
    }

    /**
     * Sets the part to be used with adding or modifying.
     *
     * @param part The part to set.
     */
    public void setPart(Part part) {
        this.partToModify = part;
        if (part != null)
            setupForm();
    }

    /**
     * Sets the boolean to indicate if the form should be modifying
     * an existing part or adding a new one.
     *
     * @param isModifying the boolean to indicate if modifying or not.
     */
    public void setModifying(boolean isModifying) {
        this.isModifying = isModifying;
    }

    /**
     * Initializes the form.
     *
     * @param url            the provided url to resource.
     * @param resourceBundle the provided resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (partToModify != null) {
            setupForm();
        }
    }
}
