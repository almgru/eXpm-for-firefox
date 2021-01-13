package com.almgru.expm.controller;

import com.almgru.expm.model.Profile;
import com.almgru.expm.observers.MainWindowObserver;
import com.almgru.expm.system.ProfileLauncher;

/**
 * Controller for the main window.
 * <p>
 * Responsible for responding to user input events.
 */
public class MainWindowController implements MainWindowObserver {
    private final ProfileLauncher profileLauncher;

    public MainWindowController(ProfileLauncher profileLauncher) {
        this.profileLauncher = profileLauncher;
    }

    @Override
    public void onProfileDoubleClicked(Profile profile) {
        this.profileLauncher.launchProfile(profile);
    }
}
