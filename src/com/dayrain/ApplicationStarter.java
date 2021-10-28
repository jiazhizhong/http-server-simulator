package com.dayrain;

import com.dayrain.views.HomePage;
import javafx.application.Application;
import javafx.stage.Stage;


public class ApplicationStarter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new HomePage(primaryStage).start();

    }
}
