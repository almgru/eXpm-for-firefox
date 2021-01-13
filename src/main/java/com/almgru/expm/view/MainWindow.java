package com.almgru.expm.view;

import com.almgru.expm.model.Profile;
import com.almgru.expm.observers.MainWindowObserver;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

/**
 * Main window that displays all profiles in a tree view.
 */
public class MainWindow {
    @FXML private MenuBar menu;
    @FXML private TreeView<ProfileTreeItem> profilesTree;

    private MainWindowObserver observer;

    /**
     * Populates tree view with specified profile collection.
     *
     * @param profiles collection of profiles to populate tree view with
     */
    public void setProfiles(Collection<Profile> profiles) {
        final TreeItem<ProfileTreeItem> profileTreeRoot =
                new TreeItem<>(new ProfileTreeItem("Uncategorized"));

        for (Profile profile : profiles) {
            profileTreeRoot.getChildren()
                    .add(new TreeItem<>(new ProfileTreeItem(profile)));
        }

        profileTreeRoot.setExpanded(true);
        this.profilesTree.setRoot(profileTreeRoot);
    }

    /**
     * Sets observer that will be notified whenever a user input event occurs.
     *
     * @param observer observer to be notified
     */
    public void setMainWindowObserver(MainWindowObserver observer) {
        this.observer = observer;
    }

    /**
     * Called when the user clicks on an item in the profiles tree view.
     * <p>
     * Will notify the observer if the user has double-clicked and the target of
     * the click is a Profile.
     *
     * @param event mouse event
     */
    public void onProfileClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Profile selectedProfile = profilesTree
                    .getSelectionModel()
                    .getSelectedItem()
                    .getValue()
                    .profile;

            if (selectedProfile != null) {
                this.observer.onProfileDoubleClicked(selectedProfile);
            }
        }
    }
}
