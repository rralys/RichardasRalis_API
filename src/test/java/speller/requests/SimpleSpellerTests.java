package speller.requests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import speller.ReadConfig;

import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


//на лекции такие тестф бфли для примера возможностей RestAssured, в домашке можно было не делать.
//но хорошо, что есть :)
public class SimpleSpellerTests extends ReadConfig {

    private RequestSpecification REQUEST_SPECIFICATION;

    private Properties property = new Properties();

    private String domain;
    private String checkText;
    private String checkTexts;


    //todo работа с файлом пропертей - это не дело теста. В каждом тестовом классе читать проперти - непозволительная роскошь
    //убери в отдельный класс-обработчик пропетрей, с геттерами типа getDomain() — готово.

/*    private void getEndpoints() {
        try (FileInputStream file = new FileInputStream(propertiesPath + "/config.properties")) {
            property.load(file);
//кстати, отдельные перменные для каждой проперти там вообще будут лишние

            //интрефес, примерно, такой getPropertyByName("я_пропертя"); getDomain()
            domain = property.getProperty("domain");
            checkText = domain + property.getProperty("checkTextEndpoint");
            checkTexts = domain + property.getProperty("checkTextsEndpoint");

        } catch (IOException e) {
            System.err.println("Property file is not found.");
        }
    }*/

    @BeforeMethod
    public void setup() {
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter()).build();

        ReadConfig config = new ReadConfig();
        domain = config.getDomain();
        checkText = config.getCheckTextEndpoint();
        checkTexts = config.getCheckTextsEndpoint();
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
