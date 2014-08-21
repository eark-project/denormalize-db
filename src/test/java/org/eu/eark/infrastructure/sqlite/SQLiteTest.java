package org.eu.eark.infrastructure.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example of SQLite usage to test infrastructure.
 * 
 * @see "http://stackoverflow.com/a/593137/104143"
 */
public class SQLiteTest {

    private static final String DB_NAME = "test.db";

    private Connection conn;

    @BeforeClass
    public static void initDriver() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    @Before
    public void createConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
    }

    @After
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @After
    public void dropDbFile() {
        new File(DB_NAME).delete();
    }

    @Test
    public void shouldInsertAndQuery() throws SQLException {
        Statement stat = conn.createStatement();
        try {
            createTable(stat);

            insertPeople();

            selectPeople(stat);

        } finally {
            stat.close();
        }
    }

    private void createTable(Statement stat) throws SQLException {
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("create table people (name, occupation);");
    }

    private void insertPeople() throws SQLException {
        PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");
        prep.setString(1, "Gandhi");
        prep.setString(2, "politics");
        prep.addBatch();
        prep.setString(1, "Turing");
        prep.setString(2, "computers");
        prep.addBatch();
        prep.setString(1, "Wittgenstein");
        prep.setString(2, "smartypants");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
    }

    private void selectPeople(Statement stat) throws SQLException {
        ResultSet rs = stat.executeQuery("select * from people;");
        try {
            assertResultRow(rs, "Gandhi", "politics");
            assertResultRow(rs, "Turing", "computers");
            assertResultRow(rs, "Wittgenstein", "smartypants");
            assertFalse(rs.next());
        } finally {
            rs.close();
        }
    }

    private void assertResultRow(ResultSet rs, String name, String job) throws SQLException {
        assertTrue(rs.next());
        assertEquals("name", name, rs.getString("name"));
        assertEquals("job", job, rs.getString("occupation"));
    }

}
