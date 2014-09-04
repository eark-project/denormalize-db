package org.eu.eark.denormalizedb.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eu.eark.denormalizedb.model.analytics.Predicate;
import org.eu.eark.denormalizedb.model.analytics.Processor;

/**
 * Container for simple analytical (meta) data about a column's data like unique or histograms.
 */
public class ColumnAnalytics {

    private final ColumnData data;
    private final Map<Object, Integer> uniqueValues = new LinkedHashMap<Object, Integer>();
    private int minLength = -1;
    private int maxLength = -1;
    private int totalLength = -1;

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
                if (value == null) {
                    continue;
                }
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
            if (value == null) {
                continue;
            }
            if (!condition.satisfiedBy(value)) {
                return false;
            }
        }
        return true;
    }

    public int minLength() {
        if (minLength < 0) {
            lazyCountLengths();
        }
        return minLength;
    }

    public int maxLength() {
        if (maxLength < 0) {
            lazyCountLengths();
        }
        return maxLength;
    }

    public double averageLength() {
        if (totalLength < 0) {
            lazyCountLengths();
        }
        return 1.0 * totalLength / data.size();
    }

    private void lazyCountLengths() {
        Processor collectLengths = new Processor() {
            @Override
            public void process(Object value) {
                if (value == null) {
                    minLength = 0;
                } else {
                    int len = value.toString().length();
                    if (minLength == -1 || len < minLength) {
                        minLength = len;
                    }
                    if (len > maxLength) {
                        maxLength = len;
                    }
                    totalLength += len;
                }
            }
        };
        each(collectLengths);
    }

    public void each(Processor worker) {
        for (Object value : data) {
            if (value == null) {
                continue;
            }
            worker.process(value);
        }
    }
}
