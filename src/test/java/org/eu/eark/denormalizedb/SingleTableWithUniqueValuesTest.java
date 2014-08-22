package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class SingleTableWithUniqueValuesTest extends AbstractTableTestCase {

    @Before
    public void loadCountryData() throws SQLException {
        // CREATE TABLE country (
        //   country_id integer DEFAULT nextval('country_country_id_seq'::regclass) NOT NULL,
        //   country character varying(50) NOT NULL,
        table.setTableName("country");
        loadSakilaTable("select country_id, country from country;");
    }

    @Test
    public void shouldKnowNumberOfRows() {
        assertEquals(109, table.numRecords());
    }

    @Test
    public void shouldKnowNumberOfColumns() {
        assertEquals(2, table.numColumns());
    }

    @Test
    public void shouldKnowUniqueValues() {
        assertEquals(109, table.column(0).numUniqueValues());
        assertEquals(109, table.column(1).numUniqueValues());

        assertTrue(table.column(0).allValuesUnique());

        assertArrayEquals(new int[] { 0, 1 }, table.uniqueColumnOrder());
    }

    @Test
    public void shouldCreateOrderedId() {
        assertEquals("country/1/Afghanistan", table.idColumn().value(0));
    }
}
