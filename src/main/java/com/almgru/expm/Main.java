package com.almgru.expm;

import com.almgru.expm.controller.MainWindowController;
import com.almgru.expm.data_access.ProfileReader;
import com.almgru.expm.exceptions.LoadProfilesException;
import com.almgru.expm.model.Profile;
import com.almgru.expm.system.PathUtils;
import com.almgru.expm.view.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow;
        Collection<Profile> profiles = null;

        try {
            ProfileReader profileReader = new ProfileReader();
            PathUtils pathUtils = new PathUtils();
            profiles = profileReader.loadProfiles(pathUtils.getProfilesINIPath());
        } catch (LoadProfilesException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            mainWindow = loader.getController();
            mainWindow.setProfiles(profiles);
            mainWindow.setMainWindowObserver(new MainWindowController());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        primaryStage.show();
    }
}
