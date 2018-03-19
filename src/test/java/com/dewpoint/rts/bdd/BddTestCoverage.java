package com.dewpoint.rts.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dewpoint.rts.bdd.steps"},
        features = {"classpath:bdd/features"}
)
//@ActiveProfiles(value = "integration")
public class BddTestCoverage {
}