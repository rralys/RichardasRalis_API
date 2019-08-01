package speller.service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import speller.ReadConfig;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestSpellerService extends ReadConfig {
    private RequestSpecification REQUEST_SPECIFICATION;

    public RestSpellerService() {

        getDataFromProperties();

        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .setBaseUri(domain)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();
    }

    public Response getWithParams(String uri, Map<String, Object> params) {
        RequestSpecification specification = given(REQUEST_SPECIFICATION);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            specification.param(param.getKey(), param.getValue());
        }
        return specification.get(uri);
    }

}
