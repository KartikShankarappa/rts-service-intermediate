package com.dewpoint.rts.bdd.steps.helpers;

import cucumber.api.java.en.Given;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@AllArgsConstructor
public class BackgroundData {

    @Getter
    private HttpHeaders headers;

    @Given("^a client with an active primary user account$")
    public void an_active_user() throws Throwable {
        this.headers = DefaultBuilders.getDefaultUserAuthorizationHeader ();
    }

    @Given("^a client with an active primary Administrator account$")
    public void an_active_admin() throws Throwable {
        this.headers = DefaultBuilders.getDefaultAdminAuthorizationHeader ();
    }
}
