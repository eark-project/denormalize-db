package org.eu.eark.denormalizedb;

/**
 * A column of a table in a relational schema. A column holds the column meta-data and column's data.
 */
public class Column {

    private final ColumnMetaData metaData;
    private final ColumnData data;
    private final ColumnAnalytics summary;

    public Column(ColumnMetaData metaData, ColumnData data) {
        this.metaData = metaData;
        this.data = data;
        this.summary = new ColumnAnalytics(data);
    }

    public int numUniqueValues() {
        if (metaData.isUnique()) {
            return data.size();
        }
        return summary.numUniqueValues();
    }

    public boolean allValuesUnique() {
        if (metaData.isUnique()) {
            return true;
        }
        return summary.allValuesUnique();
    }

    public Object row(int rowIndex) {
        return data.row(rowIndex);
    }
}
