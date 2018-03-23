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
public class GetAllCandidates {

    @NonNull
    private BackgroundData backgroundData;
    @Getter
    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate = new RestTemplate();

    @NonNull
    private GenericValidator genericValidator;

    @When("^the client issues a GET request to the uri /candidates$")
    public void theClientIssuesAGETRequestToTheUriCandidates() throws Throwable {
        backgroundData.an_active_admin ();
        headers = backgroundData.getHeaders ();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<CandidateDTO> candidateDTOResponseEntity = restTemplate.exchange(DefaultBuilders.REST_SERVICE_URI+"/candidates/", HttpMethod.GET, request, CandidateDTO.class);
        genericValidator.setServiceResponseEntity (candidateDTOResponseEntity);

    }
}
