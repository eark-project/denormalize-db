package org.eu.eark.denormalizedb;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A column of a table in a relational schema. A column holds the column meta-data and column's data.
 */
public class Column {

    private final ColumnData data;
    private final Set<Object> uniqueValues = new LinkedHashSet<Object>();

    public Column(ColumnData data) {
        this.data = data;
    }

    public int numUniqueValues() {
        lazyFindUniqueValues();
        return uniqueValues.size();
    }

    private void lazyFindUniqueValues() {
        if (uniqueValues.isEmpty()) {
            for (Object values : data) {
                uniqueValues.add(values);
            }
        }
    }

    public boolean allValuesUnique() {
        return numUniqueValues() == data.size();
    }

    public Object row(int rowIndex) {
        return data.row(rowIndex);
    }
}
