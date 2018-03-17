package com.dewpoint.rts.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.websystique.springboot.bdd.steps"},
        features = {"classpath:bdd/features"}
)
//@ActiveProfiles(value = "integration")
public class BddTestCoverage {
}