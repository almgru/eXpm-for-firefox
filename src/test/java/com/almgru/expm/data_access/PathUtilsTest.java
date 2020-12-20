package com.almgru.expm.data_access;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class PathUtilsTest {
    private static final String HOME = System.getProperty("user.home");
    private static final String OS_NAME = System.getProperty("os.name");
    private static final Path WINDOWS_FIREFOX_PROFILES_PATH = Paths.get(
            PathUtilsTest.HOME, "AppData", "Roaming", "Mozilla", "Firefox", "profiles.ini"
    );

    private PathUtils pathUtils;

    @Before
    public void setup() {
        System.setProperty("os.name", PathUtilsTest.OS_NAME);
        pathUtils = new PathUtils();
    }

    @Test
    public void getProfilesINIPath_should_returnWindowsFirefoxProfilesINIPath_when_osNameIsWindows10() {
        File expected = PathUtilsTest.WINDOWS_FIREFOX_PROFILES_PATH.toFile();
        System.setProperty("os.name", "Windows 10");

        File actual = this.pathUtils.getProfilesINIPath();

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void getProfilesINIPath_should_throwIllegalStateException_when_osNameIsUnknown() {
        System.setProperty("os.name", "Unknown");

        this.pathUtils.getProfilesINIPath();
    }
}
