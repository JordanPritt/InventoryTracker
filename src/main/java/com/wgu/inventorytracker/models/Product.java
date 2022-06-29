package com.wgu.inventorytracker.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to model a Product.
 */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Default constructor.
     *
     * @param id    the product's identifier.
     * @param name  the product's name.
     * @param price the product's price.
     * @param stock the product's inventory count.
     * @param min   the product's min value.
     * @param max   the product's max value.
     */
    public Product(int id,
                   String name,
                   double price,
                   int stock,
                   int min,
                   int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Id setter.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name setter.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Price setter.
     *
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Stock setter.
     *
     * @param stock the stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Min setter.
     *
     * @param min the min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Max setter.
     *
     * @param max the max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Id getter.
     *
     * @return the id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Name getter.
     *
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Price getter.
     *
     * @return the price.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Stock getter.
     *
     * @return the stock.
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Min getter.
     *
     * @return the min.
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Max getter.
     *
     * @return the max.
     */
    public int getMax() {
        return this.max;
    }

    /**
     * Adds an associated part to object.
     *
     * @param part the part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes and associated part from object.
     *
     * @param selectedAssociatedPart the part to delete.
     * @return a boolean representing success or failure.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Gets all the associated parts from object.
     *
     * @return all associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
