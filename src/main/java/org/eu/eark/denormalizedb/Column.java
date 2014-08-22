package org.eu.eark.denormalizedb;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Column {

    private final List<Object[]> allRows;
    private final int colIndex;
    private final Set<Object> uniqueValues = new LinkedHashSet<Object>();

    public Column(List<Object[]> rows, int colIndex) {
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

}
