package com.dewpoint.rts.bdd.steps.helpers;

import com.dewpoint.rts.dto.UserResponseDTO;
import cucumber.api.java.en.Then;
import lombok.Getter;
import lombok.Setter;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenericValidator {

    @Getter
    @Setter
    ResponseEntity<?> serviceResponseEntity;

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int statusCode) throws Throwable {
        if(serviceResponseEntity!=null){
            assertThat (serviceResponseEntity.getStatusCode ().value ()).isEqualTo (statusCode);
        }
    }







    @Then("^the client receives a list of all candidates$")
    public void theClientReceivesAListOfAllCandidates() throws Throwable {
        assertThat (serviceResponseEntity.getStatusCode ()).isEqualTo (HttpStatus.OK);
    }

    @Then("^the candidate profile is created in the system$")
    public void theCandidateProfileIsCreatedInTheSystem() throws Throwable {
        assertThat (serviceResponseEntity.getStatusCode ()).isEqualTo (HttpStatus.OK);
    }

    @Then("^the candidate profile is updated in the system$")
    public void theCandidateProfileIsUpdatedInTheSystem() throws Throwable {
        assertThat (serviceResponseEntity.getStatusCode ()).isEqualTo (HttpStatus.OK);
    }


}
