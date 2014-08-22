package org.eu.eark.denormalizedb;

import java.sql.ResultSet;
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
            int count = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] values = new Object[count];
                for (int i = 0; i < count; i++) {
                    values[i] = rs.getObject(1 + i);
                }
                table.addRow(values);
            }
        } finally {
            rs.close();
        }
    }

}
