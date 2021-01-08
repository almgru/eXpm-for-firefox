package com.almgru.expm.view;

import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.almgru.expm.model.Profile;
import com.almgru.expm.system.PathUtils;
import com.almgru.expm.system.ProfileLauncher;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Collection;

public class MainWindow {
    @FXML private MenuBar menu;
    @FXML private TreeView<ProfileTreeItem> profilesTree;

    public void setProfiles(Collection<Profile> profiles) {
        final TreeItem<ProfileTreeItem> profileTreeRoot = new TreeItem<>(new ProfileTreeItem("Uncategorized"));

        for (Profile profile : profiles) {
            profileTreeRoot.getChildren().add(new TreeItem<>(new ProfileTreeItem(profile)));
        }

        profileTreeRoot.setExpanded(true);
        this.profilesTree.setRoot(profileTreeRoot);
    }

    public void onProfileClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Profile selectedProfile = profilesTree
                    .getSelectionModel()
                    .getSelectedItem()
                    .getValue()
                    .profile;

            if (selectedProfile != null) {
                // TODO: Notify listener that launches profile instead.
                PathUtils pathUtils = new PathUtils();
                ProfileLauncher launcher = new ProfileLauncher();

                try {
                    launcher.launchProfile(
                            pathUtils.getFirefoxInstallPath(),
                            pathUtils.getProfilesPath(),
                            selectedProfile
                    );
                } catch (IOException | FirefoxNotInstalledException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
