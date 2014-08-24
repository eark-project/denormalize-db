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

    private final TableMetaData metaData = new TableMetaData();
    private final TableData data = new TableData();
    private final List<Column> columns = new ArrayList<Column>();
    private IdColumn idColumn;

    public TableMetaData getMetaData() {
        return metaData;
    }
    
    public void addRow(Object... values) {
        data.add(new RowData(values));
    }

    public RowData row(int rowIndex) {
        return data.get(rowIndex);
    }

    public int numRows() {
        return data.size();
    }

    public int numColumns() {
        if (numRows() == 0) {
            throw new IllegalStateException("no rows loaded");
        }
        return data.get(0).size();
    }

    public Column column(int index) {
        if (columns.size() <= index) {
            lazyLoadColumns(index);
        }
        return columns.get(index);
    }

    private void lazyLoadColumns(int index) {
        for (int i = columns.size(); i <= index; i++) {
            columns.add(new Column(metaData.getColumn(i), new ColumnData(data, i)));
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
            idColumn = new IdColumn(metaData, data, uniqueColumnOrder());
        }
        return idColumn;
    }
}
