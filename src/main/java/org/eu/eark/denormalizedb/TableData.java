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

    public RowData[] get(int[] rowIndexes) {
        RowData[] values = new RowData[rowIndexes.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = get(rowIndexes[i]);
        }
        return values;
    }

    public RowData[] all() {
        RowData[] values = new RowData[size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = get(i);
        }
        return values;
    }

    @Override
    public Iterator<RowData> iterator() {
        return rows.iterator();
    }

    public int size() {
        return rows.size();
    }

    public void join(RowData[] values) {
        if (values.length != size()) {
            throw new IllegalArgumentException("number of values " + values.length + " != number of rows " + size());
        }

        for (int i = 0; i < size(); i++) {
            RowData newRows = rows.get(i).join(values[i]);
            rows.set(i, newRows);
        }
    }

}
