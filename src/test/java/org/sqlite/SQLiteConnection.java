package org.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.rules.ExternalResource;

public class SQLiteConnection extends ExternalResource {

    private static final String DRIVER_CLASS_NAME = "org.sqlite.JDBC";
    private static final String JDBC_CONNECT = "jdbc:sqlite:";

    private final String dbName;
    private final boolean removeDatabase;

    private Connection conn;
    private List<Statement> statements = new ArrayList<Statement>();

    public SQLiteConnection(String dbName) {
        this(dbName, false);
    }

    public static SQLiteConnection toTempDB() {
        return new SQLiteConnection("test.db", true);
    }

    private SQLiteConnection(String dbName, boolean removeDatabase) {
        this.dbName = dbName;
        this.removeDatabase = removeDatabase;
    }

    @Override
    protected void before() throws ClassNotFoundException, SQLException {
        initDriver();
        createConnection();
    }

    private void initDriver() throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
    }

    private void createConnection() throws SQLException {
        conn = DriverManager.getConnection(JDBC_CONNECT + dbName);
    }

    @Override
    protected void after() {
        close();

        if (removeDatabase) {
            dropDbFile();
        }
    }

    private void close() {
        for (Statement stmt : statements) {
            close(stmt);
        }
        closeConnection();
    }

    private void close(Statement stmt) {
        try {
            if (isOpen(stmt)) {
                stmt.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private boolean isOpen(Statement stmt) throws SQLException {
        // return !stmt.isClosed(); // since 1.6, not implemented by current JDBC
        return ((org.sqlite.Stmt) stmt).isOpen();
    }

    private void closeConnection() {
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private void dropDbFile() {
        new File(dbName).delete();
    }

    public Connection get() {
        return conn;
    }

    // --- statements

    private <T extends Statement> T recordStatement(T stmt) {
        statements.add(stmt);
        return stmt;
    }

    public Statement createStatement() throws SQLException {
        return recordStatement(conn.createStatement());
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return recordStatement(conn.prepareStatement(sql));
    }

    // --- delegate

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

}
