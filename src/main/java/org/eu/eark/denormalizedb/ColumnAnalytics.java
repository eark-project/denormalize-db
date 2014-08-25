package org.eu.eark.denormalizedb;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Container for simple analytical (meta) data about a column's data like unique or histograms.
 */
public class ColumnAnalytics {

    private final ColumnData data;
    private final Set<Object> uniqueValues = new LinkedHashSet<Object>();

    public ColumnAnalytics(ColumnData data) {
        this.data = data;
    }

    public int numUniqueValues() {
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

}
