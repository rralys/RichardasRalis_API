package speller.requests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import speller.ReadConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class SimpleSpellerTests extends ReadConfig {

    private RequestSpecification REQUEST_SPECIFICATION;

    private Properties property = new Properties();

    private String domain;
    private String checkText;
    private String checkTexts;

    private void getEndpoints() {
        try (FileInputStream file = new FileInputStream(propertiesPath + "/config.properties")) {
            property.load(file);

            domain = property.getProperty("domain");
            checkText = domain + property.getProperty("checkTextEndpoint");
            checkTexts = domain + property.getProperty("checkTextsEndpoint");

        } catch (IOException e) {
            System.err.println("Property file is not found.");
        }
    }

    @BeforeMethod
    public void setup() {
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();
        getEndpoints();
    }

    @Test(description = "Verify corrections for a wrong word")
    void verifyWordCorrection() {
        given(REQUEST_SPECIFICATION)
                .param("text", "rdio")
                .get(checkText)
                .then()
                .body("word[0]", is("rdio"))
                .body("s[0][0]", is("radio"));
    }

    @Test(description = "Verify result attributes")
    void verifyProperAttributesInResult() {
        given(REQUEST_SPECIFICATION)
                .param("text", "shet")
                .get(checkText)
                .then()
                .body("code[0]", is(1))
                .body("len[0]", is(4));
    }

    @Test(description = "Verify the number of suggestions")
    void verifyNumberOfSuggestions() {
        given(REQUEST_SPECIFICATION)
                .param("text", "ussue")
                .get(checkText)
                .then()
                .body("s[0]", hasSize(3));
    }

    @Test(description = "Verify sentence with an incorrect word check")
    void verifyTextFragmentCorrection() {
        given(REQUEST_SPECIFICATION)
                .param("text", "This sentence has an arror.")
                .get(checkTexts)
                .then()
                .body("s[0][0]", hasItem("error"));
    }

}
