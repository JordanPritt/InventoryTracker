package com.wgu.inventorytracker.utils;

import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Part;
import com.wgu.inventorytracker.models.Product;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class IdGenerator {
    public static int nextPartId() {
        ObservableList<Part> parts = Inventory.getAllParts();
        if (Inventory.getAllParts().size() <= 0) return 0;
        int nextInt = parts.stream().max(Comparator.comparing(x -> x.getId())).get().getId();
        return nextInt + 1;
    }

    public static int nextProductId() {
        ObservableList<Product> products = Inventory.getAllProducts();
        int nextInt = products.stream().max(Comparator.comparing(x -> x.getId())).get().getId();
        return nextInt + 1;
    }
}
