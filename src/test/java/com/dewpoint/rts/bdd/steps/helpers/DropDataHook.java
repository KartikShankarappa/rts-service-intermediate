package com.dewpoint.rts.bdd.steps.helpers;

import com.zaxxer.hikari.HikariDataSource;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DropDataHook {
    @Before
    @After
    public void dropTestData() throws SQLException {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(10);
        ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        ds.addDataSourceProperty("url", "jdbc:mysql://localhost:3306/rts");
        ds.addDataSourceProperty("user", "root");
        ds.addDataSourceProperty("password", "Welcome1");
        Connection connection = ds.getConnection ();
        Statement statement = connection.createStatement ();
        statement.executeUpdate ("delete from candidate where email like '%@dewtest.com'");
    }
}
