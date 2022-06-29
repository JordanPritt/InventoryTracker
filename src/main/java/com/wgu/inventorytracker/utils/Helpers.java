package com.wgu.inventorytracker.utils;

import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Part;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 * A helper class for generalized functionality.
 */
public class Helpers {

    /**
     * Checks to see if a text field is empty or not. Used for quickly checking if the
     * search is empty which indicates it was cleared or at the initial value of empty.
     *
     * @param textField the provided text field to check.
     * @return a boolean indicating if empty or not.
     */
    public static boolean isEmptySearch(TextField textField) {
        return textField.getText().trim().equals("");
    }

    /**
     * Gets a filtered list of parts.
     *
     * @param partSearchText the search filter string.
     * @return a filtered list of results.
     */
    public static ObservableList<Part> getFilteredParts(TextField partSearchText) {

        return Inventory.getAllParts().filtered(part -> {
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
    }
}
