package org.eu.eark.denormalizedb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RowDataTest {

    @Test
    public void shouldKnowSize() {
        RowData rd = new RowData(new Object[] { 1, 2, 3 });
        assertEquals(3, rd.size());
    }

    @Test
    public void shouldHaveValues() {
        RowData rd = new RowData(new Object[] { 47, 11 });
        assertEquals(47, rd.get(0));
        assertEquals(11, rd.get(1));
    }

    @Test
    public void shouldJoin() {
        RowData rd1 = new RowData(new Object[] { 1 });
        RowData rd2 = new RowData(new Object[] { 'A' });
        RowData joined = rd1.join(rd2);
        assertEquals(1, joined.get(0));
        assertEquals('A', joined.get(1));
    }

}
