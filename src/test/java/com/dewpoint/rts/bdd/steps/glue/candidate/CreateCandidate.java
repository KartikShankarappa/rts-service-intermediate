package com.dewpoint.rts.bdd.steps.glue.candidate;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.CandidateDTO;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class CreateCandidate {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate = new RestTemplate();

    @NonNull
    private GenericValidator genericValidator;

    @Given("^there is at least one default candidate in the system$")
    public void thereIsAtleastOneDefaultClientInTheSystem() throws Throwable {
        theClientIssuesAPOSTRequestToTheUriCandidatesWithNewCandidateDetails ();
    }

    @When("^the client issues a POST request to the uri /candidates with new candidate details$")
    public void theClientIssuesAPOSTRequestToTheUriCandidatesWithNewCandidateDetails() throws Throwable {

        backgroundData.an_active_user ();
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CandidateDTO requestDTO = DefaultBuilders.createDefaultCandidateDTO ( );
        HttpEntity<CandidateDTO> request = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/candidates/", HttpMethod.POST, request, String.class);
        genericValidator.setServiceResponseEntity (responseEntity);
    }
}
