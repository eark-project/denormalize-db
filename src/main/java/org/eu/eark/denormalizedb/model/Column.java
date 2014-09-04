package org.eu.eark.denormalizedb.model;

/**
 * A column of a table in a relational schema. A column holds the column meta-data and column's data.
 */
public class Column {

    private final ColumnMetaData metaData;
    private final ColumnData data;
    private final ColumnAnalytics analytics;

    public Column(ColumnMetaData metaData, ColumnData data) {
        this.metaData = metaData;
        this.data = data;
        this.analytics = new ColumnAnalytics(data);
    }

    public ColumnMetaData getMetaData() {
        return metaData;
    }

    public ColumnAnalytics getAnalytics() {
        return analytics;
    }

    public int numUniqueValues() {
        if (metaData.isUnique()) {
            return data.size();
        }
        return analytics.numUniqueValues();
    }

    public boolean allValuesUnique() {
        if (metaData.isUnique()) {
            return true;
        }
        return analytics.allValuesUnique();
    }

    public Object row(int rowIndex) {
        return data.row(rowIndex);
    }

    public Object[] rows(int[] rowIndexes) {
        return data.rows(rowIndexes);
    }

    public Object[] rows() {
        return data.rows();
    }
    
    public int indexOf(Object value) {
        return data.indexOf(value);
    }

    public int[] indexesOf(Object... values) {
        return data.indexesOf(values);
    }

}
