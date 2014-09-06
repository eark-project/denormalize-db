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

        table = explode(cityTable);
    }

    // TODO move explode to production code in the end
    private Table explode(Table source) {
        Table target = new Table();

        copyTableName(source, target);
        copyColumnsMetaData(source, target);
        copyRows(source, target);

        copyAllReferencedData(source, target);

        return target;
    }

    private void copyTableName(Table source, Table target) {
        source.metaData().copyTableNameTo(target.metaData());
    }

    private void copyColumnsMetaData(Table source, Table target) {
        for (ColumnMetaData cmd : source.metaDataColumns()) {
            target.addMetaDataColumn(cmd);
        }
    }

    private void copyRows(Table source, Table target) {
        for (RowData row : source.rows()) {
            target.addRow(row);
        }
    }

    private void copyAllReferencedData(Table source, Table target) {
        int colIndex = 0;
        for (ColumnMetaData cmd : source.metaDataColumns()) {
            if (cmd.hasFK()) {
                Reference reference = cmd.getReference();
                reference.copyMetaDataColumnsTo(target);
                copyAllDataFromReferencedTable(source, colIndex, reference, target);
            }
            colIndex++;
        }
    }

    private void copyAllDataFromReferencedTable(Table source, int colIndex, Reference foreignKey, Table target) {
        Object[] keys = source.column(colIndex).rows();
        RowData[] values = foreignKey.valuesReferencedBy(keys);
        target.extendWith(values);
    }

    @Test
    public void shouldMarkOutgoingReferenceColumn() {
        ColumnMetaData sourceColumn = table.metaDataColumn(COUNTRY_ID);
        Table referencedTable = sourceColumn.getReference().getTable();
        String referenceTableName = referencedTable.metaData().getTableName();
        assertEquals("country", referenceTableName);
    }

    @Test
    public void shouldAddMetaDataColumnsOfReferencesTable() {
        int newColumnIndex = 3;
        ColumnMetaData newIdColumn = table.metaDataColumn(newColumnIndex);
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

    @Test
    public void shouldIgnoreSecondFKTargetColumnForId() {
        assertEquals("city/103/United States/Cape Coral/101", table.idColumn().value(100));
    }

}
