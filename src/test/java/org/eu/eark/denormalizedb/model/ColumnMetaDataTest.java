package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class ColumnMetaDataTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCityData() throws SQLException {
        table.metaData().setTableName("city");
        loadSakilaTable(table, "select city_id, city, country_id from city;");
    }

    // TODO add other meta data, look into Estonian database what is provided

    @Test
    public void shouldKnowItsTableAndColIndex() {
        ColumnMetaData columnZero = table.metaDataColumn(0);
        assertSame(table, columnZero.getSelfReference().getTable());
        assertEquals(0, columnZero.getSelfReference().getColIndex());
    }

    @Test
    public void shouldHaveAName() {
        ColumnMetaData columnZero = table.metaDataColumn(0);
        assertEquals("city_id", columnZero.getColumnName());
    }

    @Test
    public void shouldHaveAUniqueName() {
        ColumnMetaData columnZero = table.metaDataColumn(0);
        assertEquals("city:city_id", columnZero.getUniqueColumnName());
    }

}
