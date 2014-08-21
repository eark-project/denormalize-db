package org.eu.eark.infrastructure.sqlite;

import org.sqlite.SQLiteConnection;

public class SakilaConnection extends SQLiteConnection {

    private static final String SAKILA_FILE = "src/test/resources/sakila/sqlite-sakila.sq";

    public SakilaConnection() {
        super(SAKILA_FILE);
    }

}
