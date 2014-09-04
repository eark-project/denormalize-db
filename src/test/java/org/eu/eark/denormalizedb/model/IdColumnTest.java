package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class IdColumnTest {

    @Test
    public void shouldUseColumnsForId() {
        Table table = new Table();
        table.metaData().setTableName("country");

        ColumnMetaData idColumn = new ColumnMetaData();
        idColumn.setColumnName("country_id");
        table.metaData().addColumn(idColumn);

        ColumnMetaData nameColumn = new ColumnMetaData();
        nameColumn.setColumnName("country");
        table.metaData().addColumn(nameColumn);

        table.addRow(1, "Afghanistan");
        table.addRow(2, "Algeria");
        table.addRow(4, "Angola");

        assertEquals("country/1/Afghanistan", table.idColumn().value(0));
    }
    
    @Test @Ignore
    public void shouldUseOneUniqeColumnInTheBeginningForId() {
        // TODO next if there are more unique values, chose one 
        /* 
         * (which one?)
         * if it is > 10 maybe not
         * if it is string and <= 10, good, else take numerical
         */
    }


    // TODO ID Column, if it has too many columns, try to reduce for the key

    // * if there are more non-unique but in not overlapping dimensions, create different variations of id columns = multiple tables
    // * detect if a unique column is a subset of another unique column and sort these into groups (this is more than just ordering by count)

}
