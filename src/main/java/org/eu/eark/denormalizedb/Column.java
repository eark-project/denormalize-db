package org.eu.eark.denormalizedb;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A column of a table in a relational schema. A column holds the column meta-data and column's data.
 */
public class Column {

    private final ColumnMetaData metaData;
    private final ColumnData data;
    private final Set<Object> uniqueValues = new LinkedHashSet<Object>();

    public Column(ColumnMetaData metaData, ColumnData data) {
        this.metaData = metaData;
        this.data = data;
    }

    public int numUniqueValues() {
        if (metaData.isUnique()) {
            return data.size();
        }
        lazyFindUniqueValues();
        return uniqueValues.size();
    }

    private void lazyFindUniqueValues() {
        if (uniqueValues.isEmpty()) {
            for (Object value : data) {
                uniqueValues.add(value);
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
