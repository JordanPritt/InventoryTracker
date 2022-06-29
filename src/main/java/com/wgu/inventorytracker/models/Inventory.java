package com.wgu.inventorytracker.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A model to represent the inventory.
 */
public class Inventory {
    private final static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a given part to object.
     *
     * @param newPart the part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a given product to object.
     *
     * @param newProduct the product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Finds a part by a given id.
     *
     * @param partId the id to search on.
     * @return the part search result.
     */
    public static Part lookupPart(int partId) {
        return allParts.get(partId);
    }

    /**
     * Finds a product by a given id.
     *
     * @param productId the id to search on.
     * @return the product search result.
     */
    public static Product lookupProduct(int productId) {
        return allProducts.get(productId);
    }

    /**
     * Finds a part by name.
     *
     * @param partName the name to search on.
     * @return the search results.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().toLowerCase().contains(partName.toLowerCase()));
    }

    /**
     * Finds a product by name.
     *
     * @param productName the name to search on.
     * @return the search results.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().toLowerCase().contains(productName.toLowerCase()));
    }

    /**
     * Updates a provide part.
     *
     * @param index        index of part to update.
     * @param selectedPart the new part to udpate with.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    /**
     * Updates a product.
     *
     * @param index      index of the product to update.
     * @param newProduct the new product to update with.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.remove(index);
        allProducts.add(index, newProduct);
    }

    /**
     * Deletes a given part.
     *
     * @param selectedPart part to delete.
     * @return returns a boolean indicating success or failure.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a given product.
     *
     * @param selectedProduct the product to delete.
     * @return returns a boolean indicating success or failure.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * AllParts getter.
     *
     * @return all the parts in the object.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * AllProducts getter.
     *
     * @return all the products in the object.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
