package com.almgru.expm.system;

import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

/**
 * Utility class for getting OS dependent paths.
 */
public class PathUtils {

    private static final String WINDOWS_REGISTRY_FIREFOX_PREFIX =
            "SOFTWARE\\Mozilla\\Mozilla Firefox";

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
        return Paths.get(this.getProfilesPath().getPath(), "profiles.ini").toFile();
    }

    public File getProfilesPath() throws IllegalStateException {
        Path pathPrefix;
        String os = System.getProperty("os.name");

        if (os.toLowerCase().contains("windows")) {
            pathPrefix = Paths.get(System.getenv("APPDATA"));
        } else if (os.toLowerCase().contains("linux")) {
            throw new UnsupportedOperationException("Linux not yet tested.");
        } else if (os.toLowerCase().contains("mac")) {
            throw new UnsupportedOperationException("macOS not yet tested.");
        } else {
            // TODO: Create UnsupportedOSException
            throw new IllegalStateException("No supported OS.");
        }

        return Paths.get(pathPrefix.toString(), "Mozilla", "Firefox").toFile();
    }

    public File getFirefoxInstallPath() throws IllegalStateException, FirefoxNotInstalledException {
        String os = System.getProperty("os.name");

        if (os.toLowerCase().contains("windows")) {
            try {
                return new File(WindowsRegistry.getInstance().readString(
                        HKey.HKLM,
                        String.format(
                                "%s\\%s\\Main",
                                PathUtils.WINDOWS_REGISTRY_FIREFOX_PREFIX,
                                getLatestFirefoxVersionNumber(os)
                        ),
                        "PathToExe")
                );
            } catch (RegistryException ex) {
                throw new FirefoxNotInstalledException();
            }
        }

        // TODO: Handle Linux, macOS and unsupported OS

        throw new UnsupportedOperationException("Linux and macOS not yet tested.");
    }

    private String getLatestFirefoxVersionNumber(String os) throws FirefoxNotInstalledException {
        if (os.toLowerCase().contains("windows")) {
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

        // TODO: Handle Linux, macOS and unsupported OS

        throw new UnsupportedOperationException("Linux and macOS not yet tested.");
    }
}
