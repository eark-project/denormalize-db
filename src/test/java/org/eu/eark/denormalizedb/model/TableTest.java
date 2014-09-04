package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test general table functionality.
 */
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

}
