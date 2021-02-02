package com.almgru.expm.system;

import com.almgru.expm.enums.OperatingSystem;

/**
 * Detects the operating system the program instance runs on.
 */
public class OSDetector {
    public OperatingSystem getOS() {
        String osString = System.getProperty("os.name").toLowerCase();

        if (osString.contains("windows")) {
            return OperatingSystem.Windows;
        } else if (osString.contains("mac")) {
            return OperatingSystem.macOS;
        } else if (osString.contains("linux")) {
            return OperatingSystem.Linux;
        } else {
            return OperatingSystem.Unsupported;
        }
    }
}
