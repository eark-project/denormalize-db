package org.eu.eark.denormalizedb;

import java.util.Iterator;

/**
 * The actual data contained in a column.
 */
public class ColumnData implements Iterable<Object> {

    private final TableData table;
    private final int colIndex;

    public ColumnData(TableData rows, int colIndex) {
        this.table = rows;
        this.colIndex = colIndex;
    }

    public Object row(int rowIndex) {
        return table.get(rowIndex)[colIndex];
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private final Iterator<Object[]> tableValues = table.iterator();

            @Override
            public boolean hasNext() {
                return tableValues.hasNext();
            }

            @Override
            public Object next() {
                return tableValues.next()[colIndex];
            }

            @Override
            public void remove() {
                tableValues.remove();
            }
        };
    }

    public int size() {
        return table.size();
    }

}
