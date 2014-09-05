package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class ColumnAnalyticsTest extends AbstractTableTestCase {

    private final Table table = new Table();

    @Before
    public void loadCountryData() throws SQLException {
        // CREATE TABLE country (
        //   country_id integer DEFAULT nextval('country_country_id_seq'::regclass) NOT NULL,
        //   country character varying(50) NOT NULL,
        table.metaData().setTableName("country");
        loadSakilaTable(table, "select country_id, country from country;");
    }

    @Test
    public void shouldKeepOriginalColumnOrderForUniqueColumnOrder() {
        assertArrayEquals(new int[] { 0, 1 }, table.columns().uniqueColumnOrder());
    }

    @Test
    public void shouldDetectTextWhenUnknown() {
        int nameColIndex = 1;
        ColumnMetaData nameColumn = table.metaDataColumn(nameColIndex);
        nameColumn.setType(ColumnDataType.UNKNOWN);

        table.column(nameColIndex).detectType();

        assertEquals(ColumnDataType.TEXT, nameColumn.getType());
    }

    @Test
    public void shouldCountMinAndMaxColLengths() {
        assertEquals(1, table.column(0).minLength());
        assertEquals(3, table.column(0).maxLength());

        assertEquals(4, table.column(1).minLength());
        assertEquals(37, table.column(1).maxLength());
    }

    // TODO implement analytics for recognising potential columns for full text index
    // many values have > 1 word -> should index in Solr

    // TODO implement analytics for detecting factors/categories/facets
    // all values are 1 word -> enum/categorisation

}
