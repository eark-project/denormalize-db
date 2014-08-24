package org.eu.eark.denormalizedb;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.eu.eark.infrastructure.sqlite.SakilaConnection;
import org.junit.Rule;

public abstract class AbstractTableTestCase {

    @Rule
    public final SakilaConnection conn = new SakilaConnection();

    protected final Table table = new Table();

    protected void loadSakilaTable(String query) throws SQLException {
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(query);
        try {
            ResultSetMetaData jdbcMetaData = rs.getMetaData();
            setColumnMetaData(jdbcMetaData);

            setTableData(rs);
        } finally {
            rs.close();
        }
    }

    private void setColumnMetaData(ResultSetMetaData jdbcMetaData) throws SQLException {
        int colCount = jdbcMetaData.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            ColumnMetaData column = new ColumnMetaData();
            column.setColumnName(jdbcMetaData.getColumnName(i + 1));
            table.getMetaData().addColumn(column);
        }
    }

    private void setTableData(ResultSet rs) throws SQLException {
        ResultSetMetaData jdbcMetaData = rs.getMetaData();
        int colCount = jdbcMetaData.getColumnCount();

        while (rs.next()) {
            Object[] values = new Object[colCount];
            for (int i = 0; i < colCount; i++) {
                values[i] = rs.getObject(1 + i);
            }
            table.addRow(values);
        }
    }
}
