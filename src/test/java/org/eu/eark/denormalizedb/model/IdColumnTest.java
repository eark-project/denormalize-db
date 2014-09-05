package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class IdColumnTest {

    @Test
    public void shouldUseColumnsForId() {
        Table table = new TableBuilder().
                        withName("country").
                        withColumn("country_id").
                        withColumn("country").
                        withRow(1, "Afghanistan").
                        withRow(2, "Algeria").
                        withRow(4, "Angola").
                        build();

        assertEquals("country/1/Afghanistan", table.idColumn().value(0));
    }
    
    @Test @Ignore
    public void shouldUseOnlyOneUniqeColumnInTheBeginningForId() {
        Table table = new TableBuilder().
                withName("table").
                withColumn("unique_id").
                withColumn("unique_text").
                withColumn("non_uniqe").
                withColumn("unique_sth").
                withRow(1, "Afghanistan", "a", 'a').
                withRow(2, "Algeria", "a", 'b').
                withRow(4, "Angola", "b", 'c').
                build();

        assertEquals("table/a/Afghanistan", table.idColumn().value(0));

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
