package com.almgru.expm.data_access;

import com.almgru.expm.exceptions.LoadProfilesException;
import com.almgru.expm.model.Profile;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileReaderTest {
    private ProfileReader profileReader;

    @Test(expected = LoadProfilesException.class)
    public void loadProfiles_should_throwLoadProfilesException_when_fileDoesNotExist()
            throws LoadProfilesException, IOException {
        this.mock_INIUtils_readSections_to_throwException(new NoSuchFileException(""));
        profileReader.loadProfiles(new File("test"));
    }

    private void mock_INIUtils_readSections_to_throwException(Exception exception)
            throws IOException {
        INIUtils mock = mock(INIUtils.class);
        when(mock.readSections(any(FileReader.class))).thenThrow(exception);
        profileReader = new ProfileReader(mock);
    }

    @Test(expected = LoadProfilesException.class)
    public void loadProfiles_should_throwLoadProfilesException_when_fileNotReadable()
            throws LoadProfilesException, IOException {
        this.mock_INIUtils_readSections_to_throwException(new FileNotFoundException());
        profileReader.loadProfiles(new File("test"));
    }

    @Test(expected = LoadProfilesException.class)
    public void loadProfiles_should_throwLoadProfilesException_when_ioExceptionOccurs()
            throws LoadProfilesException, IOException {
        this.mock_INIUtils_readSections_to_throwException(new IOException());
        profileReader.loadProfiles(new File(
                ProfileReaderTest.class.getResource(
                        "/ProfileReaderTest/exists-and-is-readable.ini"
                ).getPath())
        );
    }

    @Test(expected = LoadProfilesException.class)
    public void loadProfiles_should_throwLoadProfilesException_when_sectionNameIsInvalid()
            throws LoadProfilesException, IOException {
        INIUtils.INISection section = new INIUtils.INISection("ProfileA");
        INIUtils mockUtils = mock(INIUtils.class);
        when(mockUtils.readSections(any())).thenReturn(Collections.singletonList(section));
        when(mockUtils.getSequenceFromSectionName(any(String.class)))
                .thenThrow(new NumberFormatException());
        profileReader = new ProfileReader(mockUtils);

        profileReader.loadProfiles(new File(
                ProfileReaderTest.class.getResource(
                        "/ProfileReaderTest/exists-and-is-readable.ini"
                ).getPath())
        );
    }

    @Test
    public void loadProfiles_should_returnExpectedProfile_when_INIFileValid()
            throws IOException, LoadProfilesException {
        Profile expectedProfile = new Profile(1, "Test", true, "test/Test");
        INIUtils.INISection expectedSection = new INIUtils.INISection("Profile1");
        expectedSection.addKeyValuePair("Name", expectedProfile.name);
        expectedSection.addKeyValuePair("IsRelative", "1");
        expectedSection.addKeyValuePair("Path", expectedProfile.path);

        INIUtils mockUtils = mock(INIUtils.class);
        when(mockUtils.readSections(any())).thenReturn(Collections.singletonList(expectedSection));
        when(mockUtils.getSequenceFromSectionName(any())).thenReturn(expectedProfile.sequence);
        when(mockUtils.parseBoolean(any())).thenReturn(true);
        this.profileReader = new ProfileReader(mockUtils);

        Profile actual = profileReader.loadProfiles(new File(
                ProfileReaderTest.class.getResource(
                        "/ProfileReaderTest/exists-and-is-readable.ini"
                ).getPath())
        ).iterator().next();

        assertEquals(expectedProfile, actual);
    }
}
