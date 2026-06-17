package com.example.demo.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
  
  ClientHttpRequestFactory clientHttpRequestFactory() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(1000); // hang forever (fail-fast)
    requestFactory.setReadTimeout(5000);
    return requestFactory;
  }


  RestTemplate restTemplate(@NonNull ClientHttpRequestFactory requestFactory) {
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
  }
}
