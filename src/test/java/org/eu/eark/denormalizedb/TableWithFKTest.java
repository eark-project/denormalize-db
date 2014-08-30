package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TableWithFKTest extends AbstractTableTestCase {

    private static final int COUNTRY_ID = 2;

    private final Table countryTable = new Table();
    private final Table cityTable = new Table();
    private Table table;

    @Before
    public void loadCountryData() throws SQLException {
        countryTable.getMetaData().setTableName("country");
        loadSakilaTable(countryTable, "select country_id, country from country;");

        cityTable.getMetaData().setTableName("city");
        loadSakilaTable(cityTable, "select city_id, city, country_id from city;");

        cityTable.getMetaData().getColumn(COUNTRY_ID).references(countryTable, 0);

        table = explode(cityTable);
    }

    private Table explode(Table sourceTable) {
        Table t = new Table();

        t.getMetaData().setTableName(sourceTable.getMetaData().getTableName());

        for (ColumnMetaData cmd : sourceTable.getMetaData().columns()) {
            t.getMetaData().addColumn(cmd);
        }

        for (ColumnMetaData cmd : sourceTable.getMetaData().columns()) {
            Reference reference = cmd.getReference();
            if (reference != null) {
                Table referencedTable = reference.getTable();
                int referencedColIndex = reference.getColIndex();

                for (int colIndex = 0; colIndex < referencedTable.numColumns(); colIndex++) {
                    // skip ID of FK because it is the same
                    if (colIndex == referencedColIndex) {
                        continue;
                    }
                    // else add all other data columns
                    t.getMetaData().addColumn(referencedTable.getMetaData().getColumn(colIndex));
                }
            }
        }

        return t;
    }

    @Test
    public void shouldMarkOutgoingReferenceColumn() {
        assertEquals("country", table.getMetaData().getColumn(COUNTRY_ID).getReference().getTable().getMetaData().getTableName());
    }

    @Test
    public void shouldAddMetaDataColumnsOfReferencesTable() {
        int newColumnIndex = 3;
        ColumnMetaData newColumn = table.getMetaData().getColumn(newColumnIndex);
        assertEquals("country", newColumn.getColumnName());
        assertEquals(1, newColumn.getSelfReference().getColIndex());
        // TODO meta column with info on FK references, e.g. # of total used
    }

    @Test @Ignore
    public void shouldAddDataColumnsOfReferencesTable() {
        int newColumn = 3;
        assertNotNull(table.column(newColumn));
        // TODO new column which has only values that were used, so reduced in size or exploded in size
    }

}
