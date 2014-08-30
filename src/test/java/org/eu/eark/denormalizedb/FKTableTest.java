package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FKTableTest extends AbstractTableTestCase {

    private static final int COUNTRY_ID = 2;

    private final Table countryTable = new Table();
    private final Table cityTable = new Table();
    private Table table;

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
        int newColumn = 3;
        assertEquals("country", table.getMetaData().getColumn(newColumn).getColumnName());
        // TODO adds a copy meta column, which has same meta values but also original table name and info on FK references, e.g. # of total used
    }

    @Test @Ignore
    public void shouldAddDataColumnsOfReferencesTable() {
        int newColumn = 3;
        assertNotNull(table.column(newColumn));
        // TODO new column which has only values that were used, so reduced in size or exploded in size
    }

}
