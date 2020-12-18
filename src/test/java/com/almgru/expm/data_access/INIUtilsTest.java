package com.almgru.expm.data_access;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class INIUtilsTest {
    private INIUtils iniUtils;

    @Before
    public void setup() {
        this.iniUtils = new INIUtils();
    }

    @Test
    public void readSections_should_readAllSections() throws IOException {
        int expectedLength = 2;

        int actualLength = iniUtils.readSections(new InputStreamReader(new ByteArrayInputStream(
                "[Section1]\nkey=value\n[Section2]\nkey=value\n".getBytes()
        ))).size();

        assertEquals(expectedLength, actualLength);
    }

    @Test
    public void readSections_should_setExpectedValue() throws IOException {
        String key = "key";
        String expectedValue = "value";

        INIUtils.INISection actual = iniUtils.readSections(new InputStreamReader(
                new ByteArrayInputStream("[Section1]\nkey=value\n".getBytes())
        )).iterator().next();

        assertEquals(expectedValue, actual.getValue(key));
    }

    @Test
    public void readSections_should_setExpectedSectionName() throws IOException {
        String expectedName = "Section1";

        String actualName = iniUtils.readSections(new InputStreamReader(
                new ByteArrayInputStream("[Section1]\nkey=value\n".getBytes())
        )).iterator().next().name;

        assertEquals(expectedName, actualName);
    }

    @Test(expected = NumberFormatException.class)
    public void getSequenceFromSectionName_should_throwNumberFormatException_when_sectionNameDoesNotContainNumbers() {
        iniUtils.getSequenceFromSectionName("Section");
    }

    @Test
    public void parseBoolean_should_returnTrue_when_argumentIsTrue() {
        assertTrue(iniUtils.parseBoolean("True"));
    }

    @Test
    public void parseBoolean_should_returnFalse_when_argumentIsFalse() {
        assertFalse(iniUtils.parseBoolean("False"));
    }

    @Test
    public void parseBoolean_should_ignoreCase() {
        assertTrue(iniUtils.parseBoolean("tRuE"));
    }

    @Test
    public void parseBoolean_should_returnTrue_when_argumentIsOne() {
        assertTrue(iniUtils.parseBoolean("1"));
    }

    @Test
    public void parseBoolean_should_returnFalse_when_argumentIsZero() {
        assertFalse(iniUtils.parseBoolean("0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseBoolean_should_throwIllegalArgumentException_when_argumentCannotBeParsed() {
        iniUtils.parseBoolean("qwerty");
    }
}
