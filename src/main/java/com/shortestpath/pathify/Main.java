package com.shortestpath.pathify;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        AppController controller = new AppController();     // Initialize the AppController
        Scene scene = controller.initializeUI();    // Ensure initializeUI() returns a valid Scene

        // Configure the stage
        primaryStage.setTitle("Shortest Path Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
