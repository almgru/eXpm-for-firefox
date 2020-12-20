package com.almgru.expm.data_access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Helper class for <code>ProfileReader</code> to read INI files.
 */
class INIUtils {
    /**
     * Loads the contents of the INI file, groups them by section and returns those sections.
     *
     * @param reader reader to read raw INI file contents from
     *
     * @return A collection of sections contained in the INI file
     *
     * @throws IOException if an IO error occurs while reading from <code>reader</code>
     */
    Collection<INISection> readSections(InputStreamReader reader) throws IOException {
        Collection<INISection> sections = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            INISection currentSection = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                if (line.toLowerCase().matches("\\[[^]]*]")) {
                    if (currentSection != null) {
                        sections.add(currentSection);
                    }

                    currentSection = new INISection(line.replaceAll("[\\[\\]]", ""));
                } else {
                    // Discard the line if not part of a section
                    if (currentSection == null) {
                        continue;
                    }

                    String[] split = line.split("=");

                    if (split.length < 2) {
                        continue;
                    }

                    currentSection.addKeyValuePair(split[0], split[1]);
                }
            }

            if (currentSection != null) {
                sections.add(currentSection);
            }
        }

        return sections;
    }

    /**
     * Extracts the sequence number from a section name.
     *
     * @param sectionName the section name to extract sequence number from
     *
     * @return The sequence number from <code>sectionName</code>
     *
     * @throws NumberFormatException if there is no sequence number in <code>sectionName</code>
     */
    int getSequenceFromSectionName(String sectionName) throws NumberFormatException {
        return Integer.parseInt(sectionName.replaceAll("\\D+", ""));
    }

    /**
     * Converts a string into a boolean.
     * <p>
     * Is a bit "smarter" than <code>Boolean.parseBoolean</code> in that it converts "1" to
     * <code>true</code> and "0" to <code>false</code>. Case-insensitive.
     *
     * @param bool text to convert to boolean
     *
     * @return <code>true</code> if <code>bool</code> is "true" or "1", or <code>false</code> if
     * <code>bool</code> is "false" or "0"
     *
     * @throws IllegalArgumentException if <code>bool</code> is not "true", "1", "false" or "0"
     */
    boolean parseBoolean(String bool) throws IllegalArgumentException {
        switch (bool.toLowerCase()) {
            case "true":
            case "1":
                return true;

            case "false":
            case "0":
                return false;

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Helper class for storing sections read from an INI file. All values from the key-value pairs
     * are stored as text.
     */
    static class INISection {
        final String name;
        private final Map<String, String> keyValuePairs;

        INISection(String name) {
            this.name = name;
            keyValuePairs = new LinkedHashMap<>();
        }

        /**
         * Insert a key-value pair into to section.
         *
         * @param key   key to which <code>value</code> will be associated
         * @param value value to be associated with <code>key</code>
         */
        void addKeyValuePair(String key, String value) {
            this.keyValuePairs.put(key, value);
        }

        /**
         * Retrieves value associated with <code>key</code>.
         *
         * @param key key associated with the value to retrieve
         *
         * @return the value associated with <code>key</code>
         */
        String getValue(String key) {
            return this.keyValuePairs.get(key);
        }
    }
}
