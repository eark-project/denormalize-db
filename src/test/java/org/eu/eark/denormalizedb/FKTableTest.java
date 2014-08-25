package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class FKTableTest extends AbstractTableTestCase {

    private static final int COUNTRY_ID = 2;

    private final Table countryTable = new Table();
    private final Table cityTable = new Table();

    @Before
    public void loadCountryData() throws SQLException {
        // CREATE TABLE country (
        //   country_id integer DEFAULT nextval('country_country_id_seq'::regclass) NOT NULL,
        //   country character varying(50) NOT NULL,
        countryTable.getMetaData().setTableName("country");
        loadSakilaTable(countryTable, "select country_id, country from country;");

        // CREATE TABLE city (
        //         city_id integer DEFAULT nextval('city_city_id_seq'::regclass) NOT NULL,
        //         city character varying(50) NOT NULL,
        //         country_id smallint NOT NULL,
        cityTable.getMetaData().setTableName("city");
        loadSakilaTable(cityTable, "select city_id, city, country_id from city;");

        cityTable.getMetaData().getColumn(COUNTRY_ID).references(countryTable, 0);
    }

    @Test
    public void shouldMarkOutgoingReferenceColumn() {
        assertEquals("country", cityTable.getMetaData().getColumn(COUNTRY_ID).getReference().getTable().getMetaData().getTableName());
    }
    
    // TODO same as SingleTableWithReducedValuesTest but mark metadata with FK and also load countries to denormalize
}
