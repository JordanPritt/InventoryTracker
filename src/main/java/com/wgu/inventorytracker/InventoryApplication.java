package com.wgu.inventorytracker;

import com.wgu.inventorytracker.models.InHouse;
import com.wgu.inventorytracker.models.Inventory;
import com.wgu.inventorytracker.models.Outsourced;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // setup Part table initial data
        Inventory.addPart(new InHouse(0, "Carbon Frame Cylinder", 5.0, 3, 0, 25, 123));
        Inventory.addPart(new InHouse(1, "Aluminum Sphere Barrings", 15.0, 16, 5, 50, 124));
        Inventory.addPart(new Outsourced(2, "Steel Support Bar", 54.0, 5, 1, 10, "Company A"));

//        AppState.productData = FXCollections.observableArrayList(
//                new Product()
//        );

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
