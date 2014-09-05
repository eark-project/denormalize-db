package org.eu.eark.denormalizedb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eu.eark.denormalizedb.model.util.Wrapping;

/**
 * Encapsulated Container for table columns.
 */
public class TableColumns implements Iterable<Column> {

    private final TableMetaData tableMetaData;
    private final TableData tableData;
    private final List<Column> columns = new ArrayList<Column>();

    public TableColumns(TableMetaData tableMetaData, TableData tableData) {
        this.tableMetaData = tableMetaData;
        this.tableData = tableData;
    }

    public Column column(int index) {
        if (index >= numColumns()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (columns.size() <= index) {
            lazyLoadColumns(index);
        }
        return columns.get(index);
    }

    private void lazyLoadColumns(int index) {
        for (int i = columns.size(); i <= index; i++) {
            columns.add(new Column(tableMetaData.column(i), new ColumnData(tableData, i)));
        }
    }

    @Override
    public Iterator<Column> iterator() {
        return columns.iterator();
    }

    private int numColumns() {
        return tableData.numColumns();
    }

    /**
     * Sort columns by number of unique elements. In case of same number, keen
     * order of columns in table.
     */
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
        return Wrapping.toPrimitive(positions);
    }

    public int[] uniqueColumnIndices() {
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < numColumns(); i++) {
            if (column(i).allValuesUnique()) {
                indices.add(i);
            }
        }
        return Wrapping.toPrimitive(indices);
    }

}
