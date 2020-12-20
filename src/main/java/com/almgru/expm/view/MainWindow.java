package com.almgru.expm.view;

import com.almgru.expm.model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Collection;

public class MainWindow {
    @FXML private MenuBar menu;
    @FXML private TreeView<String> profilesTree;

    public void setProfiles(Collection<Profile> profiles) {
        final TreeItem<String> profileTreeRoot = new TreeItem<>("Uncategorized");

        for (Profile profile : profiles) {
            profileTreeRoot.getChildren().add(new TreeItem<>(profile.toString()));
        }

        profileTreeRoot.setExpanded(true);
        this.profilesTree.setRoot(profileTreeRoot);
    }
}
