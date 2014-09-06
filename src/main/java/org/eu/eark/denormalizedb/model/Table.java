package org.eu.eark.denormalizedb.model;

/**
 * A table in a relational schema. A table holds the columns and their meta-data
 * as well as the table's data itself (the "rows").
 */
public class Table {

    private final TableMetaData metaData = new TableMetaData();
    private final TableData data = new TableData();
    private final TableColumns columns = new TableColumns(metaData, data);

    private IdColumn idColumn;

    // delegate to the meta data

    public TableMetaData metaData() {
        return metaData;
    }

    public void addMetaDataColumn(ColumnMetaData columnMetaData) {
        columnMetaData.setSelfReferenceOnce(this, metaData.numColumns());
        metaData.addColumn(columnMetaData);
    }

    public ColumnMetaData metaDataColumn(int colIndex) {
        return metaData.column(colIndex);
    }

    public MetaDataColumns metaDataColumns() {
        return metaData.columns();
    }
    
    // delegate to the data

    public void addRow(RowData values) {
        data.add(values);
    }

    public void addRow(Object... values) {
        data.add(new RowData(values));
    }

    public RowData row(int rowIndex) {
        return data.get(rowIndex);
    }

    public RowData[] rows(int[] rowIndex) {
        return data.get(rowIndex);
    }

    public Iterable<RowData> rows() {
        return data;
    }

    public void extendWith(RowData[] values) {
        data.join(values);
    }

    public int numRows() {
        return data.size();
    }

    public int numColumns() {
        return data.numColumns();
    }

    // delegate to columns

    public Column column(int index) {
        return columns.column(index);
    }

    public TableColumns columns() {
        return columns;
    }

    // id

    public IdColumn idColumn() {
        if (idColumn == null) {
            idColumn = new IdColumn(metaData, data, columns);
        }
        return idColumn;
    }

    // de-normalisation

    public RowData[] valuesReferencedBy(Object[] fks, int inColumn) {
        int[] referencedRowsIndices = indicesOfReferencedRows(fks, inColumn);
        return rows(referencedRowsIndices);
    }

    private int[] indicesOfReferencedRows(Object[] keys, int colIndex) {
        return column(colIndex).indexesOf(keys);
    }
    
    public void copyMetaDataColumnsTo(Table target) {
        for (int colIndex = 0; colIndex < numColumns(); colIndex++) {
            ColumnMetaData cmd = metaDataColumn(colIndex);
            target.addMetaDataColumn(cmd);
        }
    }
    
}
