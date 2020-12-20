package com.almgru.expm.exceptions;

/**
 * Checked exception that indicates a problem while loading profiles from the profile INI file.
 */
public class LoadProfilesException extends Exception {
    public LoadProfilesException(String message) {
        super(message);
    }
}
