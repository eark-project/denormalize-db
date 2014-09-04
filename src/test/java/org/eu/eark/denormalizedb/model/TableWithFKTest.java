package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
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
        countryTable.getMetaData().setTableName("country");
        loadSakilaTable(countryTable, "select country_id, country from country;");

        cityTable.getMetaData().setTableName("city");
        loadSakilaTable(cityTable, "select city_id, city, country_id from city;");

        cityTable.getMetaData().getColumn(COUNTRY_ID).references(countryTable, 0);

        table = explode(cityTable);
    }

    private Table explode(Table source) {
        Table target = new Table();

        copyTableName(source, target);
        copyColumnsMetaData(source, target);
        copyRows(source, target);

        copyAllReferencedData(source, target);

        return target;
    }

    private void copyTableName(Table source, Table target) {
        target.getMetaData().setTableName(source.getMetaData().getTableName());
    }

    private void copyColumnsMetaData(Table source, Table target) {
        for (ColumnMetaData cmd : source.getMetaData().columns()) {
            target.getMetaData().addColumn(cmd);
        }
    }

    private void copyRows(Table source, Table target) {
        for (RowData row : source.rows()) {
            target.addRow(row);
        }
    }

    private void copyAllReferencedData(Table source, Table target) {
        int colIndex = 0;
        for (ColumnMetaData cmd : source.getMetaData().columns()) {
            Reference reference = cmd.getReference();
            boolean hasFK = reference != null;
            if (hasFK) {
                copyAllColumnsMetaDataFromReferencedTable(reference, target);
                copyAllDataFromReferencedTable(source, colIndex, reference, target);
            }
            colIndex++;
        }
    }

    private void copyAllColumnsMetaDataFromReferencedTable(Reference foreignKey, Table target) {
        Table referencedTable = foreignKey.getTable();

        for (int colIndex = 0; colIndex < referencedTable.numColumns(); colIndex++) {
            ColumnMetaData cmd = referencedTable.getMetaData().getColumn(colIndex);
            target.getMetaData().addColumn(cmd);
        }
    }

    private void copyAllDataFromReferencedTable(Table source, int colIndex, Reference foreignKey, Table target) {
        Object[] keys = source.column(colIndex).rows();
        RowData[] values = foreignKey.valuesReferencedBy(keys);
        target.extendWith(values);
    }

    @Test
    public void shouldMarkOutgoingReferenceColumn() {
        assertEquals("country", table.getMetaData().getColumn(COUNTRY_ID).getReference().getTable().getMetaData().getTableName());
    }

    @Test
    public void shouldAddMetaDataColumnsOfReferencesTable() {
        int newColumnIndex = 3;
        ColumnMetaData newIdColumn = table.getMetaData().getColumn(newColumnIndex);
        assertEquals("country_id", newIdColumn.getColumnName());
        assertEquals(0, newIdColumn.getSelfReference().getColIndex());
    }

    // TODO maybe add meta column info on FK references, e.g. # of total rows used from original table

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

    // TODO new column which has only values that were used, so reduced in size or exploded in size

    @Test
    @Ignore
    public void shouldIgnoreFKTargetColumnForId() {
        // TODO id column should ignore FK target column, because it is the same as
        assertEquals("city/103/Cape Coral/101", table.idColumn().value(55));
    }

}
