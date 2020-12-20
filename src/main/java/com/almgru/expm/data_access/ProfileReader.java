package com.almgru.expm.data_access;

import com.almgru.expm.exceptions.LoadProfilesException;
import com.almgru.expm.model.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Is responsible for loading and parsing profiles from the INI file into <code>Profile</code>
 * POJOs.
 */
public class ProfileReader {
    private final INIUtils iniUtils;

    public ProfileReader() {
        this(new INIUtils());
    }

    ProfileReader(INIUtils iniUtils) {
        this.iniUtils = iniUtils;
    }

    /**
     * Loads the profile INI specified by <code>profilesConfigFile</code>, into <code>Profile</code>
     * POJOs and returns them in a collection.
     *
     * @param profilesConfigFile <code>File</code> pointing to the INI file to load profiles from
     *
     * @return A <code>Collection</code> of the loaded profiles as <code>Profile</code> POJOs
     *
     * @throws LoadProfilesException with an appropriate message when the file cannot be read or
     *                               accessed or an unspecified IO error occurs, or when a problem
     *                               is encountered while parsing the INI file
     */
    public Collection<Profile> loadProfiles(File profilesConfigFile) throws LoadProfilesException {
        Collection<Profile> profiles = new ArrayList<>();

        try (FileReader fr = new FileReader(profilesConfigFile)) {
            for (INIUtils.INISection section : iniUtils.readSections(fr)) {
                if (section.name.toLowerCase().contains("profile")) {
                    profiles.add(new Profile(
                            this.iniUtils.getSequenceFromSectionName(section.name),
                            section.getValue("Name"),
                            this.iniUtils.parseBoolean(section.getValue("IsRelative")),
                            section.getValue("Path")
                    ));
                }
            }
        } catch (FileNotFoundException | NoSuchFileException ex) {
            throw new LoadProfilesException(String.format(
                    "Error loading profiles config file: File with path \"%s\" not found or " +
                            "inaccessible.",
                    profilesConfigFile.getPath()
            ));
        } catch (IOException ex) {
            throw new LoadProfilesException(String.format(
                    "Error loading profiles config file: An unspecified IO error occurred while " +
                            "attempting to read file \"%s\"",
                    profilesConfigFile.getPath()
            ));
        } catch (NumberFormatException ex) {
            throw new LoadProfilesException(String.format(
                    "Error loading profiles config file: An unspecified error occurred while " +
                            "trying to parse data in file \"%s\".",
                    profilesConfigFile.getPath()
            ));
        }

        return profiles;
    }
}
