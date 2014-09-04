package org.eu.eark.denormalizedb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColumnAnalyticsTest {

    @Test
    public void noTestsYet() {
        assertEquals(2, 1 + 1);
    }

    // TODO implement analytics for recognising potential columns for full text index
    // many values have > 1 word -> should index in Solr

    // TODO implement analytics for detecting factors/categories/facets
    // all values are 1 word -> enum/categorisation

}
