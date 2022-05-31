module com.wgu.inventorytracker {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.wgu.inventorytracker to javafx.fxml;
    exports com.wgu.inventorytracker;

    opens com.wgu.inventorytracker.controllers to javafx.fxml;
    exports com.wgu.inventorytracker.controllers;

    opens com.wgu.inventorytracker.models to javafx.fxml;
    exports com.wgu.inventorytracker.models;
}