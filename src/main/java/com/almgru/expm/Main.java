package com.almgru.expm;

import com.almgru.expm.view.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            mainWindow = loader.getController();
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        primaryStage.show();
    }
}
