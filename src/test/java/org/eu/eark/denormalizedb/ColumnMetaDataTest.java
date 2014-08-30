package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class ColumnMetaDataTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCityData() throws SQLException {
        table.getMetaData().setTableName("city");
        loadSakilaTable(table, "select city_id, city, country_id from city;");
    }

    @Test
    public void shouldOverrideUniqueValues() {
        table.getMetaData().getColumn(0).setUnique();
        table.getMetaData().getColumn(1).setUnique();
        table.getMetaData().getColumn(2).setUnique();

        assertTrue(table.column(0).allValuesUnique());
        assertTrue(table.column(1).allValuesUnique());
        assertTrue(table.column(2).allValuesUnique());
    }

}
