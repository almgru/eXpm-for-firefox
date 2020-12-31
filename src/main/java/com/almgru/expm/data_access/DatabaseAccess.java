package com.almgru.expm.data_access;

import com.almgru.expm.exceptions.DatabaseInitializationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess {
    public void initializeDatabase(String connURL, URL dbSchema)
            throws DatabaseInitializationException {
        try (Connection conn = DriverManager.getConnection(connURL)) {
            new SQLFileReader().executeScript(conn, dbSchema);
        } catch (URISyntaxException ex) {
            throw new DatabaseInitializationException(String.format(
                    "Could not initialize database: \"%s\" is not a valid URI.",
                    dbSchema.toString()
            ));
        } catch (IOException ex) {
            throw new DatabaseInitializationException(String.format(
                    "Could not initialize database: Could not access \"%s\".",
                    dbSchema.toString()
            ));
        } catch (SQLException ex) {
            throw new DatabaseInitializationException(String.format(
                    "Could not initialize database: Error parsing SQL statements in \"%s\".",
                    dbSchema.toString()
            ));
        }
    }
}
