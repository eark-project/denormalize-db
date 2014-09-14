package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the de-normalisation of a single table with one foreign key: City references country.
 */
public class TableWithFKTest extends AbstractTableTestCase {

    private static final int COUNTRY_ID = 2;

    private final Table countryTable = new Table();
    private final Table cityTable = new Table();
    private Table table;

    @Before
    public void denomraliseCitiesWithCountries() throws SQLException {
        countryTable.metaData().setTableName("country");
        loadSakilaTable(countryTable, "select country_id, country from country;");

        cityTable.metaData().setTableName("city");
        loadSakilaTable(cityTable, "select city_id, city, country_id from city;");

        cityTable.metaDataColumn(COUNTRY_ID).references(countryTable, 0);

        table = cityTable.explode();
    }

    @Test
    public void shouldMarkOutgoingReferenceColumn() {
        ColumnMetaData sourceColumn = table.metaDataColumn(COUNTRY_ID);
        Table referencedTable = sourceColumn.getReference().getTable();
        String referenceTableName = referencedTable.getTableName();
        assertEquals("country", referenceTableName);
    }

    @Test
    public void shouldAddMetaDataColumnsOfReferencesTable() {
        int newColumnIndex = 3;
        ColumnMetaData newIdColumn = table.metaDataColumn(newColumnIndex);
        assertEquals("country_id", newIdColumn.getColumnName());
        assertEquals(0, newIdColumn.getSelfReference().getColIndex());
    }

    // TODO future addition: add info on FK references, e.g. number of total rows used from original table (e.g. 43% rows used of original 423).

    @Test
    public void shouldMergeOriginalAndNewColumnsOfReferencesTable() {
        RowData newRow = table.row(0);
        assertEquals(5, newRow.size());

        RowData row = table.row(100);
        assertEquals(101, row.get(0)); // city_id
        assertEquals("Cape Coral", row.get(1)); // city 101 checked
        assertEquals(103, row.get(2)); // country id

        assertEquals(103, row.get(3)); // country id
        assertEquals("United States", row.get(4)); // country 103 checked
    }

    @Test
    public void shouldIgnoreSecondFKTargetColumnForId() {
        assertEquals("city/103/United States/Cape Coral/101", table.idColumn().value(100));
    }

}
