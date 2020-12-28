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

class SQLFileReader {
    private static final String REGEX_STATEMENT = "\\S[^;]+;";

    private final URL sqlFile;
    private final Connection connection;

    SQLFileReader(Connection conn, URL sqlFile) {
        this.connection = conn;
        this.sqlFile = sqlFile;
    }

    void execute() throws SQLException, IOException, URISyntaxException {
        String fileContents = String.join(
                "\n",
                Files.readAllLines(Paths.get(this.sqlFile.toURI()))
        );
        Pattern pattern = Pattern.compile(SQLFileReader.REGEX_STATEMENT);
        Matcher matcher = pattern.matcher(fileContents);

        while (matcher.find()) {
            this.connection.prepareStatement(matcher.group()).execute();
        }
    }
}
