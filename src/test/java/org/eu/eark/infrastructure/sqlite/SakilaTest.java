package org.eu.eark.infrastructure.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Rule;
import org.junit.Test;

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
