package com.wgu.inventorytracker.utils;

import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Part;
import com.wgu.inventorytracker.models.Product;
import javafx.collections.ObservableList;

import java.util.Comparator;

/**
 * A class to provide functionality for generating project unique identifiers.
 */
public class IdGenerator {
    /**
     * Gets the next consecutive part id sequentially.
     *
     * @return the next id in the sequence.
     */
    public static int nextPartId() {
        ObservableList<Part> parts = Inventory.getAllParts();
        if (Inventory.getAllParts().size() <= 0) return 0;
        int nextInt = parts.stream().max(Comparator.comparing(Part::getId)).orElseThrow().getId();
        return nextInt + 1;
    }

    /**
     * Gets the next consecutive product id sequentially.
     *
     * @return the next id in the sequence.
     */
    public static int nextProductId() {
        ObservableList<Product> products = Inventory.getAllProducts();
        if (Inventory.getAllProducts().size() <= 0) return 0;
        int nextInt = products.stream().max(Comparator.comparing(Product::getId)).orElseThrow().getId();
        return nextInt + 1;
    }
}
