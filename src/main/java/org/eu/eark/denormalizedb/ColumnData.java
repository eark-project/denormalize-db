package org.eu.eark.denormalizedb;

import java.util.Iterator;

/**
 * The actual data contained in a column.
 */
public class ColumnData implements Iterable<Object> {

    private final TableData table;
    private final int colIndex;

    public ColumnData(TableData table, int colIndex) {
        this.table = table;
        this.colIndex = colIndex;
    }

    public Object row(int rowIndex) {
        return table.get(rowIndex).get(colIndex);
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private final Iterator<RowData> tableItr = table.iterator();

            @Override
            public boolean hasNext() {
                return tableItr.hasNext();
            }

            @Override
            public Object next() {
                return tableItr.next().get(colIndex);
            }

            @Override
            public void remove() {
                tableItr.remove();
            }
        };
    }

    public int size() {
        return table.size();
    }

}
