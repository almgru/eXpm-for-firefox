package com.almgru.expm.view;

import com.almgru.expm.model.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class MainWindowTest extends ApplicationTest {
    MainWindow mainWindow;
    Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = MainWindowTest.class.getResource("/fxml/MainWindow.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        scene = new Scene(loader.load());
        stage.setScene(scene);
        mainWindow = loader.getController();

        // Needed to get lookup() to work
        scene.getRoot().applyCss();
    }

    @Test
    public void setProfiles_should_makeProfilesVisibleInTreeView() {
        mainWindow.setProfiles(Arrays.asList(
                new Profile(1, "Private", true, "/test/path/1"),
                new Profile(2, "Banking", true, "/test/path/2"),
                new Profile(3, "Shopping", true, "/test/path/3")
        ));

        // noinspection rawtypes: Type inside treeview not important
        TreeView treeView = (TreeView) scene.lookup("#profilesTree");

        assertTrue(treeView.isVisible());
        assertTrue(treeView.getRoot().isExpanded());
    }
}
