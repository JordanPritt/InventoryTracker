package com.wgu.inventorytracker.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        //
        return allParts.get(partId);
    }

    public static Product lookupProduct(int productId) {
        return allProducts.get(productId);
    }

    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().toLowerCase().contains(partName.toLowerCase()));
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().toLowerCase().contains(productName.toLowerCase()));
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.remove(index);
        allProducts.add(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
