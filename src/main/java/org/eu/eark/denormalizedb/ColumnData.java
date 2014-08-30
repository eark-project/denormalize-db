package org.eu.eark.denormalizedb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The actual data contained in a column.
 */
public class ColumnData implements Iterable<Object> {

    private final TableData table;
    private final int colIndex;
    private final Map<Object, Integer> rowByValue = new HashMap<Object, Integer>();

    public ColumnData(TableData table, int colIndex) {
        this.table = table;
        this.colIndex = colIndex;
    }

    public Object row(int rowIndex) {
        return table.get(rowIndex).get(colIndex);
    }

    public int indexOf(Object value) {
        lazyFillLookup();
        Integer row = rowByValue.get(value);
        return toPrimitive(row, "object [" + value + "] not found in column");
    }

    private int toPrimitive(Integer row, String exceptionIfImpossible) {
        if (row == null) {
            throw new IllegalStateException(exceptionIfImpossible);
        }
        return row;
    }

    private void lazyFillLookup() {
        if (rowByValue.isEmpty()) {
            int row = 0;
            for (Object value : this) {
                found(value, row);
                row++;
            }
        }
    }

    private void found(Object value, int inRow) {
        if (rowByValue.containsKey(value)) {
            return;
        }
        rowByValue.put(value, inRow);
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
