package com.wgu.inventorytracker;

import com.wgu.inventorytracker.models.InHouse;
import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Outsourced;
import com.wgu.inventorytracker.models.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // setup Part table initial data
        InHouse frame = new InHouse(0, "Aluminum Frame", 5.0, 3, 0, 25, 123);
        InHouse gears = new InHouse(1, "Gear Cogs", 15.0, 16, 5, 50, 124);
        Outsourced wheel = new Outsourced(2, "Wheel Frame", 54.0, 5, 1, 10, "Company A");
        // setup Product table initial data
        Product product1 = new Product(0,"Bicycle", 499.99, 5, 1, 25);
        product1.addAssociatedPart(frame);
        product1.addAssociatedPart(gears);

        // add data to lists
        Inventory.addPart(frame);
        Inventory.addPart(gears);
        Inventory.addPart(wheel);
        Inventory.addProduct(product1);

        // application startup
        java.net.URL test = InventoryApplication.class.getResource("/main-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
