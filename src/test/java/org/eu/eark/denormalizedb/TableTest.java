package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class TableTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCountryData() throws SQLException {
        table.getMetaData().setTableName("country");
        loadSakilaTable(table, "select country_id, country from country;");
    }

    @Test
    public void shouldHaveRows() {
        assertEquals(109, table.numRows());
    }

    @Test
    public void shouldHaveColumns() {
        assertEquals(2, table.numColumns());
    }

    @Test
    public void shouldHaveColumnValues() {
        assertEquals(1, table.column(0).row(0));
        assertEquals("Afghanistan", table.column(1).row(0));
    }
}