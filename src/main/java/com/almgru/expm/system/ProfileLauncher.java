package com.almgru.expm.system;

import com.almgru.expm.model.Profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ProfileLauncher {
    public void launchProfile(File firefoxPath, File profilesPath, Profile profile)
            throws IOException {
        // TODO: Handle non-relative profiles
        Runtime.getRuntime().exec(new String[]{
                firefoxPath.getPath(),
                "-profile",
                Paths.get(profilesPath.getPath(), profile.path).toString()
        });
    }
}
