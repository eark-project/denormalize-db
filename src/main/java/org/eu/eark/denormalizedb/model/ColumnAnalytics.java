package org.eu.eark.denormalizedb.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eu.eark.denormalizedb.model.util.Predicate;

/**
 * Container for simple analytical (meta) data about a column's data like unique or histograms.
 */
public class ColumnAnalytics {

    private final ColumnData data;
    private final Map<Object, Integer> uniqueValues = new LinkedHashMap<Object, Integer>();

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
                int current = uniqueValues.containsKey(value) ? uniqueValues.get(value) : 0;
                uniqueValues.put(value, current + 1);
            }
        }
    }

    public boolean allValuesUnique() {
        return numUniqueValues() == data.size();
    }

    public int numOccurances(Object value) {
        lazyFindUniqueValues();
        return uniqueValues.get(value);
    }

    public boolean all(Predicate condition) {
        for (Object value : data) {
            if (!condition.satisfiedBy(value)) {
                return false;
            }
        }
        return true;
    }

}
