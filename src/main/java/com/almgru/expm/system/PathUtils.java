package com.almgru.expm.system;

import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.almgru.expm.exceptions.UnsupportedOSException;

import java.io.File;
import java.nio.file.Paths;

/**
 * Utility class for getting OS dependent paths.
 */
public class PathUtils {
    private final OSDetector osDetector;
    private final WindowsRegistryIO windowsRegistryIO;

    public PathUtils(OSDetector osDetector) {
        this(osDetector, null);
    }

    /**
     * Constructor used when OS is Windows.
     */
    public PathUtils(OSDetector osDetector, WindowsRegistryIO windowsRegistryIO) {
        this.osDetector = osDetector;
        this.windowsRegistryIO = windowsRegistryIO;
    }

    /**
     * Retrieves file pointing to the location of Firefox profiles INI file for
     * different platforms.
     *
     * @return File pointing to Firefox profiles INI file
     *
     * @throws UnsupportedOSException if platform is unsupported (not Windows,
     *                                Mac or Linux)
     */
    public File getProfilesINIPath() throws UnsupportedOSException {
        return Paths.get(this.getProfilesPath().getPath(), "profiles.ini").toFile();
    }

    /**
     * @return the default directory where Firefox stores its profiles
     *
     * @throws UnsupportedOSException if OS is not Windows, macOs or Linux
     */
    public File getProfilesPath() throws UnsupportedOSException {
        switch (this.osDetector.getOS()) {
            case Windows:
                return Paths.get(
                        System.getenv("APPDATA"), "Mozilla", "Firefox"
                ).toFile();

            case macOS:
                throw new UnsupportedOperationException("macOS not yet tested" +
                        ".");
            case Linux:
                throw new UnsupportedOperationException("Linux not yet tested.");
            case Unsupported:
                throw new UnsupportedOSException();

            default:
                throw new IllegalStateException(String.format(
                        "this.getOS() returned invalid value '%s'.", this.osDetector.getOS()
                ));
        }
    }

    /**
     * @return the path to the latest version of the Firefox executable on the user's computer
     *
     * @throws UnsupportedOSException       if OS is not Windows, macOS or Linux
     * @throws FirefoxNotInstalledException if the install path of Firefox cannot be found
     */
    public File getFirefoxInstallPath() throws UnsupportedOSException,
            FirefoxNotInstalledException {
        switch (this.osDetector.getOS()) {
            case Windows: {
                new File(windowsRegistryIO.getPathToFirefoxExecutable());
            }

            case macOS:
                throw new UnsupportedOperationException("macOS not yet tested.");
            case Linux:
                throw new UnsupportedOperationException("Linux not yet tested.");
            case Unsupported:
                throw new UnsupportedOSException();

            default:
                throw new IllegalStateException(String.format(
                        "this.getOS() returned invalid value '%s'.", this.osDetector.getOS()
                ));
        }
    }
}
