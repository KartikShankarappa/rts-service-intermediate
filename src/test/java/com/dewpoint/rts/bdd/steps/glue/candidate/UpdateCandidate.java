package com.dewpoint.rts.bdd.steps.glue.candidate;

import com.dewpoint.rts.bdd.steps.helpers.BackgroundData;
import com.dewpoint.rts.bdd.steps.helpers.DefaultBuilders;
import com.dewpoint.rts.bdd.steps.helpers.GenericValidator;
import com.dewpoint.rts.dto.CandidateDTO;
import cucumber.api.java.en.When;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class UpdateCandidate {
    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate = new RestTemplate();

    @NonNull
    private GenericValidator genericValidator;


    @When("^the client issues a PUT request to the uri /candidates with updated candidate details$")
    public void theClientIssuesAPUTRequestToTheUriCandidatesWithUpdatedCandidateDetails() throws Throwable {

        backgroundData.an_active_user ( );
        headers = backgroundData.getHeaders ( );
        headers.setContentType (MediaType.APPLICATION_JSON);
        CandidateDTO requestDTO = DefaultBuilders.createDefaultCandidateDTO ( );
        requestDTO.setClientZip ("456123");
        requestDTO.setSource ("Referral");
        HttpEntity<CandidateDTO> request = new HttpEntity<> (requestDTO, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange (DefaultBuilders.REST_SERVICE_URI + "/candidates/", HttpMethod.PUT, request, String.class);
        genericValidator.setServiceResponseEntity (responseEntity);
    }
}
