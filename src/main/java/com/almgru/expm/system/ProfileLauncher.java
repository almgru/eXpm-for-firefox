package com.almgru.expm.system;

import com.almgru.expm.model.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Responsible for launching Firefox with a specified profile applied.
 */
public class ProfileLauncher {
    private final FirefoxLauncher firefoxLauncher;
    private final File profilesPath;

    public ProfileLauncher(FirefoxLauncher firefoxLauncher, File profilesPath) {
        this.firefoxLauncher = firefoxLauncher;
        this.profilesPath = profilesPath;
    }

    /**
     * Launches Firefox with 'profile' applied.
     *
     * @param profile Profile to apply
     */
    public void launchProfile(Profile profile) {
        try {
            firefoxLauncher.launchFirefox(
                    "-profile",
                    Paths.get(this.profilesPath.getPath(), profile.path).toString()
            );
        } catch (IOException ignored) {
        }
    }
}
