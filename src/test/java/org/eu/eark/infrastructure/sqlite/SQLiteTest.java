package org.eu.eark.infrastructure.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Rule;
import org.junit.Test;
import org.sqlite.SQLiteConnection;

/**
 * Example of SQLite usage to test infrastructure.
 * 
 * @see "http://stackoverflow.com/a/593137/104143"
 */
public class SQLiteTest {

    @Rule
    public final SQLiteConnection conn = SQLiteConnection.toTempDB();

    @Test
    public void shouldInsertAndQuery() throws SQLException {
        createTable();
        insertPeople();
        selectPeople();
    }

    private void createTable() throws SQLException {
        Statement stat = conn.createStatement();
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

    private void selectPeople() throws SQLException {
        Statement stat = conn.createStatement();
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
