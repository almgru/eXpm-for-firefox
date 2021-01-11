package com.almgru.expm;

import com.almgru.expm.controller.MainWindowController;
import com.almgru.expm.data_access.ProfileReader;
import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.almgru.expm.exceptions.LoadProfilesException;
import com.almgru.expm.model.Profile;
import com.almgru.expm.system.PathUtils;
import com.almgru.expm.system.ProfileLauncher;
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
        PathUtils pathUtils = new PathUtils();

        try {
            Collection<Profile> profiles = this.loadProfiles(pathUtils);
            ProfileLauncher profileLauncher = this.initProfileLauncher(pathUtils);
            this.initMainWindow(primaryStage, profiles, profileLauncher);
        } catch (LoadProfilesException | IOException | FirefoxNotInstalledException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        primaryStage.show();
    }

    /**
     * Loads profiles from Firefox profiles.ini file.
     *
     * @param pathUtils used to get the path to the profiles.ini file
     *
     * @return The loaded profiles
     */
    private Collection<Profile> loadProfiles(PathUtils pathUtils) throws LoadProfilesException {
        return new ProfileReader().loadProfiles(pathUtils.getProfilesINIPath());
    }

    /**
     * Creates, initializes and returns a profile launcher.
     * <p>
     * Specifically, it initializes the Firefox install path and the profiles path.
     *
     * @param pathUtils used to get the Firefox install path and the profiles path
     *
     * @return An initialized profile launcher
     *
     * @throws FirefoxNotInstalledException if the install path of Firefox cannot be detected
     */
    private ProfileLauncher initProfileLauncher(PathUtils pathUtils) throws FirefoxNotInstalledException {
        return new ProfileLauncher(pathUtils.getFirefoxInstallPath(), pathUtils.getProfilesPath());
    }

    /**
     * Initializes the main window by loading its FXML file, setting the profiles and setting up its
     * observers.
     *
     * @param primaryStage Stage to set scene to the loaded FXML
     * @param profiles     Collection of profiles to give main window access to
     * @param launcher     Profile launcher
     *
     * @throws IOException If the FXML file cannot be loaded
     */
    private void initMainWindow(
            Stage primaryStage, Collection<Profile> profiles, ProfileLauncher launcher
    ) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        MainWindow mainWindow = loader.getController();
        mainWindow.setProfiles(profiles);
        mainWindow.setMainWindowObserver(new MainWindowController(launcher));
    }
}
