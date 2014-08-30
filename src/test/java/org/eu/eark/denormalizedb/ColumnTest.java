package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class ColumnTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCountryData() throws SQLException {
        table.getMetaData().setTableName("country");
        loadSakilaTable(table, "select country_id, country from country;");
    }

    @Test
    public void shouldHaveColumns() {
        assertEquals(2, table.numColumns());
    }

    @Test
    public void shouldHaveColumnValues() {
        assertEquals(1, idColumn().row(0));
        assertEquals("Afghanistan", nameColumn().row(0));
    }

    private Column idColumn() {
        return table.column(0);
    }

    private Column nameColumn() {
        return table.column(1);
    }

    @Test
    public void shouldFindFirstRowNumForValue() {
        assertEquals(1, idColumn().indexOf(2));
        assertEquals(1, nameColumn().indexOf("Algeria"));
    }

    @Test
    public void shouldFindVectorOfRowNumsForValues() {
        assertArrayEquals(new int[] { 3, 1 }, nameColumn().indexesOf("Angola", "Algeria"));
    }

    @Test
    public void shouldFindValuesForVectorOfRowNums() {
        assertArrayEquals(new String[] { "Angola", "Algeria" }, nameColumn().rows(new int[] { 3, 1 }));
    }
}
