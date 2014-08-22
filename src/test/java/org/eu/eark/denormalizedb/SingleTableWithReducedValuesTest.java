package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class SingleTableWithReducedValuesTest extends AbstractTableTestCase {

    @Before
    public void loadCityData() throws SQLException {
        // CREATE TABLE city (
        //         city_id integer DEFAULT nextval('city_city_id_seq'::regclass) NOT NULL,
        //         city character varying(50) NOT NULL,
        //         country_id smallint NOT NULL,
        loadSakilaTable("select city_id, city, country_id from city;");
    }

    @Test
    public void shouldKnowNumberOfColumns() {
        assertEquals(3, table.numColumns());
    }

    @Test
    public void shouldKnowUniqueValues() {
        assertEquals(600, table.column(0).numUniqueValues());
        assertEquals(599, table.column(1).numUniqueValues()); // 2 cities with same name
        assertEquals(109, table.column(2).numUniqueValues()); // 109 countries!
    }

}
