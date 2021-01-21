package com.almgru.expm.system;

import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.almgru.expm.exceptions.UnsupportedOSException;
import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

/**
 * Utility class for getting OS dependent paths.
 */
public class PathUtils {
    private final OSDetector osDetector;

    public PathUtils(OSDetector osDetector) {
        this.osDetector = osDetector;
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

    private static final String WINDOWS_REGISTRY_FIREFOX_PREFIX =
            "SOFTWARE\\Mozilla\\Mozilla Firefox";

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
                try {
                    return new File(WindowsRegistry.getInstance().readString(
                            HKey.HKLM,
                            String.format(
                                    "%s\\%s\\Main",
                                    PathUtils.WINDOWS_REGISTRY_FIREFOX_PREFIX,
                                    getLatestFirefoxVersionNumberFromWindowsRegistry()
                            ),
                            "PathToExe")
                    );
                } catch (RegistryException ex) {
                    throw new FirefoxNotInstalledException();
                }
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

    private String getLatestFirefoxVersionNumberFromWindowsRegistry()
            throws FirefoxNotInstalledException {
        try {
            List<String> allVersions = WindowsRegistry.getInstance().readStringSubKeys(
                    HKey.HKLM, PathUtils.WINDOWS_REGISTRY_FIREFOX_PREFIX
            );

            if (allVersions.size() == 0) {
                throw new FirefoxNotInstalledException();
            } else if (allVersions.size() == 1) {
                return allVersions.get(0);
            } else {
                TreeMap<Integer, String> orderedVersions = new TreeMap<>();

                for (String version : allVersions) {
                    orderedVersions.put(Integer.parseInt(
                            version.split(" ")[0].replaceAll("\\.", "")
                    ), version);
                }

                return orderedVersions.get(orderedVersions.firstKey());
            }
        } catch (RegistryException ex) {
            throw new FirefoxNotInstalledException();
        }
    }
}
