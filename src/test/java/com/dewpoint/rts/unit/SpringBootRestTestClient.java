package com.dewpoint.rts.unit;
 
import org.springframework.web.client.RestTemplate;

import java.nio.Buffer;

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8087/SpringBootRestApiGateway/gatewayapi";
     
    private static void listAllUsers(){

//        String myData = "";
//        String myJsonStr = JSON.stringify(myData);
//        String headerFriendlyStr = Buffer .from(myJsonStr, 'utf8').toString('base64');
        //res.addHeader('foo', headerFriendlyStr);
        //Decode it when you need reading:

//        var myBase64Str = req.headers['foo'];
//        var myJsonStr = Buffer.from(myBase64Str, 'base64').toString('utf8');
//        var myData = JSON.parse(myJsonStr);

        System.out.println("Testing listAllUsers API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        String users = restTemplate.getForObject(REST_SERVICE_URI+"/user/", String.class);
         
        if(users!=null){
            System.out.println("Users JSON : " +users);
        }else{
            System.out.println("No user exist----------");
        }
    }
     
    private static void getUser(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        String oneuser = restTemplate.getForObject(REST_SERVICE_URI+"/user/1", String.class);
        System.out.println(oneuser);
    }

    public static void main(String args[]){
        listAllUsers();
//        getUser();
    }
}