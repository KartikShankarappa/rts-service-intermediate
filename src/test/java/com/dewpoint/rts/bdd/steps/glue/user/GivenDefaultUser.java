package com.dewpoint.rts.bdd.steps.glue.user;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.UserDTO;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class GivenDefaultUser {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    @Given ("^a user in the system$")
    public void aUserInTheSystem() throws Throwable {
        
        
    }

    @When("^the client issues a get request to the uri /users$")
    public void theClientIssuesAGetRequestToTheUriUsers() throws Throwable {
        backgroundData.an_active_admin ();
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestJson = new JSONObject();
        HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDTO> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/", HttpMethod.GET, request, UserDTO.class);
        genericValidator.setServiceResponseEntity (users);
        System.out.println(users.getStatusCodeValue());
        HttpHeaders responseHeaders = users.getHeaders();
        System.out.println(responseHeaders.get("Cache-Control"));
        
    }

    @Then("^the client receives list of all users in the system$")
    public void theClientReceivesListOfAllUsersInTheSystem() throws Throwable {
        
        
    }

    @When("^the client issues a POST request to the uri /users with user details$")
    public void theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails() throws Throwable {
        
        
    }

    @Then("^the new user is created in the system$")
    public void theNewUserIsCreatedInTheSystem() throws Throwable {
        
        
    }

    @When("^the client issues a PUT request to the uri /users with modified user details$")
    public void theClientIssuesAPUTRequestToTheUriUsersWithModifiedUserDetails() throws Throwable {
        
        
    }

    @Then("^the user is updated in the system$")
    public void theUserIsUpdatedInTheSystem() throws Throwable {
        
        
    }

    @When ("^the client issues a DELETE request to the uri /users$")
    public void theClientIssuesADELETERequestToTheUriUsers() throws Throwable {
        
        
    }

    @Then ("^the user is deleted from the system$")
    public void theUserIsDeletedFromTheSystem() throws Throwable {
        
        
    }
}
