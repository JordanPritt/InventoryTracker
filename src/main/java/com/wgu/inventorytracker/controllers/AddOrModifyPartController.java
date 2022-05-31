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
    private void changeMachineOrCompanyName(ActionEvent event) {
        if (inHouseRadio.isSelected()) machineOrCompanyNameLabel.setText("Machine ID");
        else machineOrCompanyNameLabel.setText("Company Name");
    }

    @FXML
    private void savePart(ActionEvent event) {
        try {
            boolean isInHouse = TryParse.isInt(partMachineOrCompanyName.getText());
            Part newPart;

            if (isInHouse) {
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
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Incorrect Value");
            errorAlert.setContentText("Sorry, one or more of the values you entered were incorrect. Please try again " +
                    "after carefully looking over the entry.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void cancelAddPart(ActionEvent event) {
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

        if (partToModify.getClass() == InHouse.class) {
            InHouse part = (InHouse) partToModify;
            partMachineOrCompanyName.setText(String.valueOf(part.getMachineId()));
        } else {
            Outsourced part = (Outsourced) partToModify;
            partMachineOrCompanyName.setText(part.getCompanyName());
        }
    }

    public void setPart(Part part) {
        this.partToModify = part;
        if (part != null)
            setupForm();
    }

    public void setModifying(boolean isModifying) {
        this.isModifying = isModifying;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (partToModify != null) {
            setupForm();
        }
    }
}
