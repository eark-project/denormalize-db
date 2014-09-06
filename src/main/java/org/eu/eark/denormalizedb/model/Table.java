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

    public String getTableName() {
        return metaData.getTableName();
    }
    
    private void copyMetaDataTo(Table target) {
        metaData.copyTableNameTo(target.metaData());
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

    void copyColumnsMetaDataTo(Table target) {
        metaData.copyColumnsMetaDataTo(target.metaData);
    }

    private void copyReferencedDataTo(Table target) {
        metaData.copyReferencedDataTo(target);
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

    private void copyDataTo(Table target) {
        for (RowData row : data) {
            target.addRow(row);
        }
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

    public Table explode() {
        Table target = new Table();
        copyTo(target);
        return target;
    }

    private void copyTo(Table target) {
        copyMetaDataTo(target);
        copyColumnsMetaDataTo(target);
        copyDataTo(target);

        copyReferencedDataTo(target);
    }

    public RowData[] valuesReferencedBy(Object[] fks, int inColumn) {
        int[] referencedRowsIndices = indicesOfReferencedRows(fks, inColumn);
        return rows(referencedRowsIndices);
    }

    private int[] indicesOfReferencedRows(Object[] keys, int colIndex) {
        return column(colIndex).indexesOf(keys);
    }


}
