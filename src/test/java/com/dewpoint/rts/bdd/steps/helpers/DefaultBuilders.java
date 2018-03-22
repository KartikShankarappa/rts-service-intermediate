package com.dewpoint.rts.bdd.steps.helpers;

import com.dewpoint.rts.dto.CandidateDTO;
import com.dewpoint.rts.dto.UserDTO;
import com.dewpoint.rts.util.ApiConstants;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;

public class DefaultBuilders {

    public static final String REST_SERVICE_URI = "http://localhost:8087/rts-service/v1";

    public static CandidateDTO createDefaultCandidateDTO() {

        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCurrentJobTitle ("SDET");
        candidateDTO.setClientCity ("Okemos");
        candidateDTO.setClientName ("DD");
        candidateDTO.setClientState ("MI");
        candidateDTO.setClientZip ("48864");
        candidateDTO.setBillRate (new BigDecimal (80.12));
        candidateDTO.setFirstName ("TestFirst");
        candidateDTO.setLastName ("TestLast");
        candidateDTO.setLastJobTitle ("JSDET");
        candidateDTO.setPhoneNumber ("123-456-7890");
        candidateDTO.setSkills ("Java, Spring, Hibernate, .NET");
        candidateDTO.setSource ("Referral");
        candidateDTO.setEmail ("hello@dewtest.com");

        return candidateDTO;
    }

    public static HttpHeaders getDefaultAdminAuthorizationHeader() {
        return getAuthorizationHeader(ApiConstants.API_ADMIN_USER,ApiConstants.API_ADMIN_USER_PWD);
    }

    public static HttpHeaders getDefaultUserAuthorizationHeader() {
        return getAuthorizationHeader(ApiConstants.API_USER,ApiConstants.API_USER_PWD);
    }

    private static HttpHeaders getAuthorizationHeader(String userId, String password){
        HttpHeaders headers = new HttpHeaders ();
        String plainCreds = userId + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }
}
