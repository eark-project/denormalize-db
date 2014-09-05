package org.eu.eark.denormalizedb.model;

public class TableBuilder {

    private final Table table = new Table();

    public TableBuilder withName(String name) {
        table.metaData().setTableName(name);
        return this;
    }

    public TableBuilder withColumn(String name) {
        ColumnMetaData idColumn = new ColumnMetaData();
        idColumn.setColumnName(name);
        table.addMetaDataColumn(idColumn);
        return this;
    }

    public TableBuilder withRow(Object... values) {
        table.addRow(values);
        return this;
    }

    public Table build() {
        return table;
    }

}
