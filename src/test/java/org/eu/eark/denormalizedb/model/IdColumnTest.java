package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import org.eu.eark.denormalizedb.model.id.AllSelector;
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

        IdColumn idColumn = table.idColumn();
        idColumn.use(new AllSelector());
        assertEquals("country/1/Afghanistan", idColumn.value(0));
    }
    
    @Test
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

         // which fields to take?
         // if it is > 10 maybe not
         // if it is string and <= 10, good, else take numerical
    }

}
