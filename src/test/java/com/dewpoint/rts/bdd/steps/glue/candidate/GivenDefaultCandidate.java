package com.dewpoint.rts.bdd.steps.glue.candidate;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.CandidateDTO;
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

@RequiredArgsConstructor
public class GivenDefaultCandidate {


    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    @NonNull
    private GenericValidator genericValidator;


    @Given("^there is atleast one default candidate in the system$")
    public void thereIsAtleastOneDefaultClientInTheSystem() throws Throwable {
        theClientIssuesAPOSTRequestToTheUriCandidatesWithNewCandidateDetails ();
    }

    @When("^the client issues a GET request to the uri /candidates$")
    public void theClientIssuesAGETRequestToTheUriCandidates() throws Throwable {

        backgroundData.an_active_admin ();
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestJson = new JSONObject();
        HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CandidateDTO> candidateDTOResponseEntity = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/candidates/", HttpMethod.GET, request, CandidateDTO.class);
        genericValidator.setServiceResponseEntity (candidateDTOResponseEntity);
        System.out.println(candidateDTOResponseEntity.getStatusCodeValue());
        HttpHeaders responseHeaders = candidateDTOResponseEntity.getHeaders();
        System.out.println(responseHeaders.get("Cache-Control"));
        
    }


    @Then("^the client receives a list of all candidates$")
    public void theClientReceivesAListOfAllCandidates() throws Throwable {
        
        
    }

    @When("^the client issues a POST request to the uri /candidates with new candidate details$")
    public void theClientIssuesAPOSTRequestToTheUriCandidatesWithNewCandidateDetails() throws Throwable {

        backgroundData.an_active_user ();
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CandidateDTO requestDTO = DefaultBuilders.createDefaultCandidateDTO ( );

        HttpEntity<CandidateDTO> request = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/candidates/", HttpMethod.POST, request, String.class);
        genericValidator.setServiceResponseEntity (responseEntity);
    }

    @Then("^the candidate profile is created in the system$")
    public void theCandidateProfileIsCreatedInTheSystem() throws Throwable {
        
        
    }

    @Given ("^a candidate in the system$")
    public void aCandidateInTheSystem() throws Throwable {
        
        
    }

    @When("^the client issues a PUT request to the uri /candidates with updated candidate details$")
    public void theClientIssuesAPUTRequestToTheUriCandidatesWithUpdatedCandidateDetails() throws Throwable {
        
        
    }

    @Then("^the candidate profile is updated in the system$")
    public void theCandidateProfileIsUpdatedInTheSystem() throws Throwable {
        
        
    }

    @When ("^the client issues a GET request to the uri /candidates with the candidate id$")
    public void theClientIssuesAGETRequestToTheUriCandidatesWithTheCandidateId() throws Throwable {
        
        
    }

    @Then ("^the candidate resume is retrieved$")
    public void theCandidateResumeIsRetrieved() throws Throwable {
        
        
    }
}
