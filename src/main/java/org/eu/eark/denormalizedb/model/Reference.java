package org.eu.eark.denormalizedb.model;

/**
 * A reference of a FK to another table and column.
 */
public class Reference {

    private final Table table;
    private final int colIndex;

    public Reference(Table table, int colIndex) {
        this.table = table;
        this.colIndex = colIndex;
    }

    public Table getTable() {
        return table;
    }

    public int getColIndex() {
        return colIndex;
    }

    public Object[] columnRows() {
        return table.column(colIndex).rows();
    }

    // de-normalisation

    public void copyMetaDataColumnsTo(Table target) {
        table.copyColumnsMetaDataTo(target);
    }

    public RowData[] valuesReferencedBy(Object[] fks) {
        return table.valuesReferencedBy(fks, colIndex);
    }

}
