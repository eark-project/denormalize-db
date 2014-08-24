package org.eu.eark.denormalizedb;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A column of a table in a relational schema. A column holds the column meta-data and column's data.
 */
public class Column {

    private final TableData allRows;
    private final int colIndex;
    private final Set<Object> uniqueValues = new LinkedHashSet<Object>();

    public Column(TableData rows, int colIndex) {
        this.allRows = rows;
        this.colIndex = colIndex;
    }

    public int numUniqueValues() {
        if (uniqueValues.isEmpty()) {
            for (Object[] values : allRows) {
                uniqueValues.add(values[colIndex]);
            }
        }
        return uniqueValues.size();
    }

    public boolean allValuesUnique() {
        return numUniqueValues() == allRows.size();
    }

    public Object row(int rowIndex) {
        return allRows.get(rowIndex)[colIndex];
    }
}
