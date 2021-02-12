package com.almgru.expm.system;

import com.almgru.expm.exceptions.FirefoxNotInstalledException;
import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;

import java.util.List;
import java.util.TreeMap;

/**
 * Responsible for reading and writing values from/to the Windows Registry.
 */
public class WindowsRegistryIO {
    private static final String WINDOWS_REGISTRY_FIREFOX_PREFIX =
            "SOFTWARE\\Mozilla\\Mozilla Firefox";

    /**
     * @return The path to the executable of the latest installed Firefox version
     * @throws FirefoxNotInstalledException if Firefox is not in the Windows Registry.
     */
    String getPathToFirefoxExecutable() throws FirefoxNotInstalledException {
        try {
            return WindowsRegistry.getInstance().readString(
                    HKey.HKLM,
                    String.format(
                            "%s\\%s\\Main",
                            WindowsRegistryIO.WINDOWS_REGISTRY_FIREFOX_PREFIX,
                            getLatestFirefoxVersionNumber()
                    ),
                    "PathToExe"
            );
        } catch (RegistryException ex) {
            throw new FirefoxNotInstalledException();
        }
    }

    private String getLatestFirefoxVersionNumber() throws FirefoxNotInstalledException {
        try {
            List<String> allVersions = WindowsRegistry.getInstance().readStringSubKeys(
                    HKey.HKLM, WindowsRegistryIO.WINDOWS_REGISTRY_FIREFOX_PREFIX
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
