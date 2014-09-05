package org.eu.eark.infrastructure.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Rule;
import org.junit.Test;

/**
 * Sakila.
 * <p>
 * To start exploring de-normalising relations I need a simple schema with a few
 * relations. The schema needs to have some data and should be freely
 * accessible. The Sakila example database was originally developed for MySQL as
 * part of the documentation.
 * <ul>
 * <li>Simple Table: `County` only 1 data column.</li>
 * <li>Foreign Keys: `Address` -> `City` -> `Country`</li>
 * <li>m2n: `film_category`</li>
 * </ul>
 * For testing purpose the SQLite version of Sakila (`sqlite-sakila.sq`) has
 * been added to `src\test\resources`` and is loaded using JDBC in the tests.
 * 
 * @see "https://code.google.com/p/sakila-sample-database-ports/"
 */
public class SakilaTest {

    @Rule
    public final SakilaConnection conn = new SakilaConnection();

    @Test
    public void shouldQueryActors() throws SQLException {
        Statement stat = conn.createStatement();

        ResultSet rs = stat.executeQuery("select * from actor;");
        try {

            assertActorName(rs, "PENELOPE", "GUINESS");
            assertActorName(rs, "NICK", "WAHLBERG");

        } finally {
            rs.close();
        }
    }

    private void assertActorName(ResultSet rs, String firstName, String lastName) throws SQLException {
        assertTrue(rs.next());
        assertEquals("first_name", firstName, rs.getString("first_name"));
        assertEquals("last_name", lastName, rs.getString("last_name"));
    }

}
