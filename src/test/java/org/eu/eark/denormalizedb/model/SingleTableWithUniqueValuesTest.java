package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Country data contains columns that all have unique values.
 */
public class SingleTableWithUniqueValuesTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCountryData() throws SQLException {
        // CREATE TABLE country (
        //   country_id integer DEFAULT nextval('country_country_id_seq'::regclass) NOT NULL,
        //   country character varying(50) NOT NULL,
        table.getMetaData().setTableName("country");
        loadSakilaTable(table, "select country_id, country from country;");
    }

    @Test
    public void shouldCountUniqueValues() {
        assertEquals(109, table.column(0).numUniqueValues());
        assertEquals(109, table.column(1).numUniqueValues());

        assertTrue(table.column(0).allValuesUnique());

        assertArrayEquals(new int[] { 0, 1 }, table.uniqueColumnOrder());
    }

}
