package com.almgru.expm.view;

import com.almgru.expm.model.Profile;
import com.almgru.expm.observers.MainWindowObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class MainWindowTest extends ApplicationTest {
    MainWindow mainWindow;
    Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainWindowTest.class.getResource(
                "/fxml/MainWindow.fxml"
        ));
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

    /*
     * Test case
     *
     * MainWindow should notify observer when user double-clicks on profile
     * in tree view.
     *
     * Variants:
     * - Should _not_ notify observer when user clicks once
     */
    @Test
    public void doubleClickOnProfile_should_notifyObserver() {
        MainWindowObserver observer = Mockito.mock(MainWindowObserver.class);
        mainWindow.setProfiles(Arrays.asList(
                new Profile(1, "Private", true, "/test/path/1"),
                new Profile(2, "Banking", true, "/test/path/2"),
                new Profile(3, "Shopping", true, "/test/path/3")
        ));

        // TODO: Figure out how to do this.
        doubleClickOn("Profile #1: Private");

        Mockito.verify(observer)
                .onProfileDoubleClicked(Mockito.any(Profile.class));
    }

    /*
     * Test case
     *
     * MainWindow should provide observer with the correct Profile when user
     * double-clicks on a profile in the tree view.
     */

    /*
     * Test case
     *
     * MainWindow should not notify observer when user double-clicks on
     * template.
     */
}
