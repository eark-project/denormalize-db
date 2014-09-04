package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the de-normalisation of a single table with one foreigh key: City references country.
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

    private void copyAllDataFromReferencedTable(Table source, int colIndex, Reference foreignKey, Table target) {
        Object[] fks = source.column(colIndex).rows();
        int[] referencedRows = foreignKey.getTable().column(foreignKey.getColIndex()).indexesOf(fks);
        RowData[] values = source.rows(referencedRows);
        target.extendWith(values);
    }

    private void copyAllColumnsMetaDataFromReferencedTable(Reference foreignKey, Table target) {
        Table referencedTable = foreignKey.getTable();

        for (int colIndex = 0; colIndex < referencedTable.numColumns(); colIndex++) {
            ColumnMetaData cmd = referencedTable.getMetaData().getColumn(colIndex);
            target.getMetaData().addColumn(cmd);
        }
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
    public void shouldAddDataColumnsOfReferencesTable() {
        int newColumnIndex = 4;
        Column newNameColumn = table.column(newColumnIndex);
        assertNotNull(newNameColumn);
        assertEquals("Botshabelo", newNameColumn.row(0)); // 87th country
    }

    // TODO new column which has only values that were used, so reduced in size or exploded in size

}
