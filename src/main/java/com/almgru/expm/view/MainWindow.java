package com.almgru.expm.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeView;

public class MainWindow {
    @FXML private MenuBar menu;
    @FXML private TreeView<String> profilesTree;

    public void initialize() {
        System.out.println(menu);
        System.out.println(profilesTree);
    }
}
