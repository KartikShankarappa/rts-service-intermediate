package com.dewpoint.rts.bdd.steps.helpers;

import cucumber.api.java.en.Then;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenericValidator {

    @Getter
    @Setter
    ResponseEntity<?> serviceResponseEntity;

    @Then("^the client receives status code of (\\d+)$")
    public void theClientReceivesStatusCodeOf(int status) throws Throwable {
        if(serviceResponseEntity!=null){
            Assertions.assertThat (serviceResponseEntity.getStatusCode ()).isEqualTo (HttpStatus.OK);
        }


    }
}
