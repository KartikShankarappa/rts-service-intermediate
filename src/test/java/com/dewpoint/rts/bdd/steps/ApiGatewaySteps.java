package com.dewpoint.rts.bdd.steps;

import com.dewpoint.rts.bdd.ApiGatewayFeatureTest;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ApiGatewaySteps extends ApiGatewayFeatureTest {

    @When("^the client calls /gatewayapi/user$")
    public void the_client_issues_GET_user() throws Throwable {
     //   executePost();
        System.out.print("** good test practice");
    }

    @Then("^the client receives user with name usertest$")
    public void the_client_receives_user_with_name_usertest() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
       // throw new PendingException();
        System.out.print("** good test practice 1 1 help");
    }

    @When("^the client calls /gatewayapi/user/userid$")
    public void the_user_calls_gatewayapi_user_userid() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
        System.out.print("** good test practice 1 2 help");
    }

   @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        System.out.print("** good test practice 2");
     }

    @Then("^the client receives name useridtest$")
    public void the_client_receives_name_useridtest() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
      //  throw new PendingException();
        System.out.print("** good test practice 333");
    }
}
