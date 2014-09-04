package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

    // TODO add other meta data like isPK, Type (as enum?), etc.
    // look into Estonian database what is provided

    @Test
    public void shouldKnowItsTableAndColIndex() {
        ColumnMetaData columnZero = table.getMetaData().getColumn(0);
        assertSame(table, columnZero.getSelfReference().getTable());
        assertEquals(0, columnZero.getSelfReference().getColIndex());
    }

    // TODO column should provide a column name and column family for dump
    // column families might be the originating table, 
    // but all names must be unique for HBase export for example.
}
