
package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();
    private static final String DB_URL = System.getProperty("db.url");
    private static final String DB_USER = System.getProperty("db.user");
    private static final String DB_PASSWORD = System.getProperty("db.password");

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @SneakyThrows
    public static void clearPaymentTable() {
        try (Connection conn = getConn()) {
            runner.update(conn, "DELETE FROM payment_entity");
        }
    }

    @SneakyThrows
    public static void clearCreditTable() {
        try (Connection conn = getConn()) {
            runner.update(conn, "DELETE FROM credit_request_entity");
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        Thread.sleep(500);
        return getStatus("SELECT status FROM payment_entity LIMIT 1");
    }

    @SneakyThrows
    public static String getCreditRequestStatus() {
        Thread.sleep(500);
        return getStatus("SELECT status FROM credit_request_entity LIMIT 1");
    }

    @SneakyThrows
    private static String getStatus(String query) {
        try (Connection conn = getConn()) {
            return runner.query(conn, query, new ScalarHandler<>());
        }
    }
}


