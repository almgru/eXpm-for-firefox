package com.almgru.expm.controller;

import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.almgru.expm.model.Profile;
import com.almgru.expm.observers.MainWindowObserver;
import com.almgru.expm.system.PathUtils;
import com.almgru.expm.system.ProfileLauncher;

import java.io.IOException;

public class MainWindowController implements MainWindowObserver {
    @Override
    public void onProfileDoubleClicked(Profile profile) {
        PathUtils pathUtils = new PathUtils();
        ProfileLauncher launcher = new ProfileLauncher();

        try {
            launcher.launchProfile(
                    pathUtils.getFirefoxInstallPath(),
                    pathUtils.getProfilesPath(),
                    profile
            );
        } catch (IOException | FirefoxNotInstalledException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
