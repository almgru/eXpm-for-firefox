package com.almgru.expm.controller;

import com.almgru.expm.model.Profile;
import com.almgru.expm.observers.MainWindowObserver;
import com.almgru.expm.system.ProfileLauncher;

import java.io.IOException;

public class MainWindowController implements MainWindowObserver {
    private final ProfileLauncher profileLauncher;

    public MainWindowController(ProfileLauncher profileLauncher) {
        this.profileLauncher = profileLauncher;
    }

    @Override
    public void onProfileDoubleClicked(Profile profile) {
        try {
            this.profileLauncher.launchProfile(profile);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
