package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class SingleTableWithReducedValuesTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCityData() throws SQLException {
        // CREATE TABLE city (
        //         city_id integer DEFAULT nextval('city_city_id_seq'::regclass) NOT NULL,
        //         city character varying(50) NOT NULL,
        //         country_id smallint NOT NULL,
        table.getMetaData().setTableName("city");
        loadSakilaTable(table, "select city_id, city, country_id from city;");
    }

    @Test
    public void shouldCountUniqueValues() {
        assertEquals(600, table.column(0).numUniqueValues());
        assertEquals(599, table.column(1).numUniqueValues()); // 2 cities with same name
        assertEquals(109, table.column(2).numUniqueValues()); // 109 countries!

        assertFalse(table.column(2).allValuesUnique());
    }

    @Test
    public void shouldSortByNumberOfUniqueValues() {
        assertArrayEquals(new int[] { 2, 1, 0 }, table.uniqueColumnOrder());
    }

    @Test
    public void shouldCountOccurancesOfUniqueValues() {
        int countryId = 2;
        int austria = 9;
        assertEquals(3, table.column(countryId).getAnalytics().numOccurances(austria));
        // Graz, Linz, Salzburg
    }
    
    @Test
    public void shouldReverseOrderColumnsForId() {
        assertEquals("city/103/Cape Coral/101", table.idColumn().value(100));
    }

}
