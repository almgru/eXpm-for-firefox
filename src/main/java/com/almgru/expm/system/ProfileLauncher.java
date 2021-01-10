package com.almgru.expm.system;

import com.almgru.expm.model.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ProfileLauncher {
    private final File firefoxInstallPath;
    private final File profilesPath;

    public ProfileLauncher(File firefoxInstallPath, File profilesPath) {
        this.firefoxInstallPath = firefoxInstallPath;
        this.profilesPath = profilesPath;
    }

    public void launchProfile(Profile profile)
            throws IOException {
        // TODO: Handle non-relative profiles
        Runtime.getRuntime().exec(new String[]{
                this.firefoxInstallPath.getPath(),
                "-profile",
                Paths.get(this.profilesPath.getPath(), profile.path).toString()
        });
    }
}
