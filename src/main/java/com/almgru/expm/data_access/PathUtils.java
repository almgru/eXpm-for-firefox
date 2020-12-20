package com.almgru.expm.data_access;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for getting OS dependent paths.
 */
public class PathUtils {
    /**
     * Retrieves file pointing to the location of Firefox profiles INI file for different
     * platforms.
     * <p/>
     * Uses the <code>os.name</code> property to get platform.
     *
     * @return File pointing to Firefox profiles INI file
     *
     * @throws IllegalStateException if platform is unsupported (not Windows, Mac or Linux)
     */
    public File getProfilesINIPath() throws IllegalStateException {
        Path pathPrefix;
        String os = System.getProperty("os.name");

        if (os.toLowerCase().contains("windows")) {
            pathPrefix = Paths.get(System.getenv("APPDATA"));
        } else if (os.toLowerCase().contains("linux")) {
            throw new UnsupportedOperationException("Linux not yet tested.");
        } else if (os.toLowerCase().contains("mac")) {
            throw new UnsupportedOperationException("macOS not yet tested.");
        } else {
            throw new IllegalStateException("No supported OS.");
        }

        return Paths.get(
                pathPrefix.toString(), "Mozilla", "Firefox", "profiles.ini"
        ).toFile();
    }
}
