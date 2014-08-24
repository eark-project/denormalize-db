package org.eu.eark.denormalizedb;

/**
 * Values of a single row.
 */
public class RowData {

    private final Object[] data;

    public RowData(Object[] data) {
        this.data = data;
    }

    public Object get(int colIndex) {
        return data[colIndex];
    }

    public int size() {
        return data.length;
    }
}
