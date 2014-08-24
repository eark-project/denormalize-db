package org.eu.eark.denormalizedb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A table in a relational schema. A table holds the columns and their meta-data
 * as well as the table's data itself (the "rows").
 */
public class Table {

    private final List<Object[]> rows = new ArrayList<Object[]>();
    private final List<Column> columns = new ArrayList<Column>();
    private final TableMetaData metaData = new TableMetaData();
    private IdColumn idColumn;

    public TableMetaData getMetaData() {
        return metaData;
    }
    
    public void addRow(Object... values) {
        rows.add(values);
    }

    public Object[] row(int rowIndex) {
        return rows.get(rowIndex);
    }

    public int numRows() {
        return rows.size();
    }

    public int numColumns() {
        if (rows.size() == 0) {
            throw new IllegalStateException("no rows loaded");
        }
        return rows.get(0).length;
    }

    public Column column(int index) {
        if (columns.size() <= index) {
            lazyLoadColumns(index);
        }
        return columns.get(index);
    }

    private void lazyLoadColumns(int index) {
        for (int i = columns.size(); i <= index; i++) {
            columns.add(new Column(rows, i));
        }
    }

    public int[] uniqueColumnOrder() {
        final int[] uniqueCounts = new int[numColumns()];
        Integer[] positions = new Integer[uniqueCounts.length];
        for (int i = 0; i < uniqueCounts.length; i++) {
            positions[i] = i;
            uniqueCounts[i] = column(i).numUniqueValues();
        }
        Arrays.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.valueOf(uniqueCounts[o1]).compareTo(uniqueCounts[o2]);
            }
        });
        return toPrimitiveArray(positions);
    }

    private int[] toPrimitiveArray(Integer[] wrappers) {
        int[] primitives = new int[wrappers.length];
        for (int i = 0; i < wrappers.length; i++) {
            primitives[i] = wrappers[i];
        }
        return primitives;
    }

    public IdColumn idColumn() {
        if (idColumn == null) {
            idColumn = new IdColumn(metaData, rows, uniqueColumnOrder());
        }
        return idColumn;
    }
}
