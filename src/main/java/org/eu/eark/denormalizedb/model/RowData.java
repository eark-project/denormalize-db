package org.eu.eark.denormalizedb.model;

/**
 * Values of a single row. After creation it is sort of immutable (if data is not changed outside).
 */
public class RowData {

    private static final Object[] EMPTY = new Object[0];

    private final Object[] data;

    private RowData() {
        data = EMPTY;
    }

    public RowData(Object[] data) {
        this.data = data;
    }

    public Object get(int colIndex) {
        return data[colIndex];
    }

    public int size() {
        return data.length;
    }

    public RowData join(final RowData other) {
        final int firstSize = RowData.this.size();
        return new RowData() {

            @Override
            public Object get(int colIndex) {
                if (colIndex < firstSize) {
                    return RowData.this.get(colIndex);
                }
                return other.get(colIndex - firstSize);
            }

            @Override
            public int size() {
                return firstSize + other.size();
            }
        };
    }
}
