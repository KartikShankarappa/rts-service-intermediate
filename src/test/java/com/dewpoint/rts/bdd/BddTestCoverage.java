package com.dewpoint.rts.bdd;

import com.zaxxer.hikari.HikariDataSource;
import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dewpoint.rts.bdd.steps"},
        features = {"classpath:bdd/features"},
        tags = {"@RTStests"},
        snippets = SnippetType.CAMELCASE,
        format = {
                "html:target/cucumber/rts/html",
                "json:target/cucumber/rts/json"
        }

)

public class BddTestCoverage {
}