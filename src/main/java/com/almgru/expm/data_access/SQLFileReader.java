package com.almgru.expm.data_access;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to execute all SQL statements in a file.
 */
class SQLFileReader {
    /**
     * Simplified definition of a SQL statements. Anything that ends with a semicolon is a
     * statement. Statements are allowed to be span multiple lines.
     */
    private static final String REGEX_STATEMENT = "\\S[^;]+;";

    /**
     * Executes all SQL statements in file with path/URL <code>sqlFileUrl</code> using the database
     * connection <code>conn</code>.
     *
     * @param conn       connection to the database to execute statements on
     * @param sqlFileUrl Path/URL to SQL file from which the statements will be read
     *
     * @throws SQLException       if anything goes wrong while executing statements
     * @throws IOException        if an IO error occurs while reading SQL file
     * @throws URISyntaxException if <code>sqlFileUrl</code> is not a valid URI
     */
    void executeScript(Connection conn, URL sqlFileUrl) throws SQLException, IOException, URISyntaxException {
        String fileContents = String.join(
                "\n",
                Files.readAllLines(Paths.get(sqlFileUrl.toURI()))
        );
        Pattern pattern = Pattern.compile(SQLFileReader.REGEX_STATEMENT);
        Matcher matcher = pattern.matcher(fileContents);

        while (matcher.find()) {
            conn.prepareStatement(matcher.group()).execute();
        }
    }
}
