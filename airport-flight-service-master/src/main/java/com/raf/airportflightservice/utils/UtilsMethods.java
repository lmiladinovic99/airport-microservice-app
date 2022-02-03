package com.raf.airportflightservice.utils;

import com.raf.airportflightservice.domain.Flight;
import com.raf.airportflightservice.dto.FlightDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UtilsMethods {
    public static ResponseEntity<Object> sendGet(String url) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return response;
    }

    public static ResponseEntity<FlightDto> getFlightDto(String url) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<FlightDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, FlightDto.class);

        return response;
    }

    public static ResponseEntity<Object> sendGetHeader(String url,HttpHeaders headers) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return response;
    }

    public static ResponseEntity<Object> sendPost(String url, Object body) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

        return response;
    }

}