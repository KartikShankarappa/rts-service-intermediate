package com.dewpoint.rts.bdd.steps.glue.user;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.UserDTO;
import com.dewpoint.rts.dto.UserRequestDTO;
import com.dewpoint.rts.dto.UserResponseDTO;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetUserDetailsByID {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;

    @NonNull
    private CreateUser createUser;

    @NonNull
    private  InactivateUserStatus inactivateUserStatus;

    private RestTemplate restTemplate = new RestTemplate();

    @Given ("^(a|an active|an Inactive) user in the system$")
    public void aUserInTheSystem(String userStatus) throws Throwable {

        if(userStatus.equalsIgnoreCase ("a")){
            createUser.theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails();
        } else if(userStatus.equalsIgnoreCase ("an active")) {
            createUser.theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails();
        } else if(userStatus.equalsIgnoreCase ("an Inactive")) {
            createUser.theClientIssuesAPOSTRequestToTheUriUsersWithUserDetails();
            inactivateUserStatus.theClientIssuesADELETERequestToTheUriUsersUserIdInactivateWithUserId ();
        }

    }

    @When("^the client issues a GET request to the uri /users with user id$")
    public void theClientIssuesAGETRequestToTheUriUsersWithUserId() throws Throwable {
        backgroundData.an_active_admin ();
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<UserResponseDTO> users = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/users/", HttpMethod.GET, request, UserResponseDTO.class);
        genericValidator.setServiceResponseEntity (users);
    }

    @Then("^the user details are retrieved from the system$")
    public void theUserDetailsAreRetrievedFromTheSystem() throws Throwable {
        assertThat (genericValidator.getServiceResponseEntity ().getStatusCode ()).isEqualTo (HttpStatus.OK);
    }
}
