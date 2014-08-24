package org.eu.eark.denormalizedb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The actual data contained in a table, that is the values of all the rows.
 */
public class TableData implements Iterable<RowData> {

    private final List<RowData> rows = new ArrayList<RowData>();

    public void add(RowData values) {
        rows.add(values);
    }

    public RowData get(int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public Iterator<RowData> iterator() {
        return rows.iterator();
    }

    public int size() {
        return rows.size();
    }
}
