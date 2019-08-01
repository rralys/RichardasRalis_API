package speller.requests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

public class SoapRequestTest {

    @Test
    void yandexSpellerReturnsCorrectStatus() {
        String body =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel=\"http://speller.yandex.net/services/spellservice\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <spel:CheckTextRequest lang=\"en\" options=\"0\" format=\"\">\n" +
                        "         <spel:text>datea</spel:text>\n" +
                        "      </spel:CheckTextRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";

        RestAssured
                .given().body(body)
                .header("Host", "speller.yandex.net")
                .header("SOAPAction", "http://speller.yandex.net/services/spellservice/checkText")
                .header("Content-Type", "text/xml")
                .when()
                .post("http://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body(Matchers.stringContainsInOrder(Arrays.asList("data")));
    }

}
