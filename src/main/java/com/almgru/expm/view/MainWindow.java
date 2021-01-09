package com.almgru.expm.view;

import com.almgru.expm.model.Profile;
import com.almgru.expm.observers.MainWindowObserver;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

public class MainWindow {
    @FXML private MenuBar menu;
    @FXML private TreeView<ProfileTreeItem> profilesTree;

    private MainWindowObserver observer;

    public void setProfiles(Collection<Profile> profiles) {
        final TreeItem<ProfileTreeItem> profileTreeRoot = new TreeItem<>(new ProfileTreeItem("Uncategorized"));

        for (Profile profile : profiles) {
            profileTreeRoot.getChildren().add(new TreeItem<>(new ProfileTreeItem(profile)));
        }

        profileTreeRoot.setExpanded(true);
        this.profilesTree.setRoot(profileTreeRoot);
    }

    public void setMainWindowObserver(MainWindowObserver observer) {
        this.observer = observer;
    }

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
