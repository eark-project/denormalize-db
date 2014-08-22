package org.eu.eark.denormalizedb;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private final List<Object[]> rows = new ArrayList<Object[]>();

    public void addRow(Object... values) {
        rows.add(values);
    }

    public int numRecords() {
        return rows.size();
    }

    public int numColumns() {
        if (rows.size() == 0) {
            throw new IllegalStateException("no rows loaded");
        }
        return rows.get(0).length;
    }

    public Column column(int index) {
        return new Column(rows, index);
    }

}
