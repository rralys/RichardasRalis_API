package speller.functional;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import speller.DTO.SpellerDTO;
import speller.service.RestSpellerAssertions;
import speller.service.RestSpellerService;
import speller.service.RestSpellerSteps;

import java.util.ArrayList;
import java.util.List;

//todo допиши еще один тест с DataProvider, пожалуйста — Done.
public class SpellerTests {

    @Test(description = "Verify that checkText returns 200 code")
    void verifyCheckTextReturnsCorrectCode() {

        RestSpellerService service = new RestSpellerService();

        SpellerDTO[] speller = new RestSpellerSteps().checkText("datea");
        RestSpellerAssertions result = new RestSpellerAssertions(speller);

        result.assertTextEndpointReturnsCorrectResponseCode(service);
    }

    @Test(description = "Verify that checkTexts endpoint returns 200 code")
    void verifyCheckTextsReturnsCorrectCode() {

        RestSpellerService service = new RestSpellerService();

        SpellerDTO[] speller = new RestSpellerSteps().checkText("datea");
        RestSpellerAssertions result = new RestSpellerAssertions(speller);

        result.assertTextsEndpointReturnsCorrectResponseCode(service);
    }

    @Test(description = "Verify that checkText endpoint returns correct suggestions")
    void verifyCorrectSuggestionsForText() {

        SpellerDTO[] speller = new RestSpellerSteps().checkText("datea");

        RestSpellerAssertions result = new RestSpellerAssertions(speller);
        result.assertRightInitialWord("datea");

        //эти три метода можно заменить одним assertTextsCorrectionRequestAre("data","dates", "date").. — fixed.
        /*result.assertTextCorrectionRequest("data");
        result.assertTextCorrectionRequest("dates");
        result.assertTextCorrectionRequest("date");*/

        result.assertTextsCorrectionRequestAre("data", "dates", "date");
    }

    @Test(description = "Verify correctness of the result attributes")
    void verifyResultAttributes() {

        SpellerDTO[] speller = new RestSpellerSteps().checkText("spll");

        RestSpellerAssertions result = new RestSpellerAssertions(speller);
        result.assertTextCorrectionRequest("spell");
        result.assertRightNumberOfSuggestions(3);
        result.assertRightInitialWordLength("4");
    }

    @Test(description = "Verify that checkTexts returns correct suggestions")
    void verifyCorrectSuggestionsForTexts() {

        SpellerDTO[][] speller = new RestSpellerSteps().checkTexts("srvice", "vlue");

        RestSpellerAssertions result = new RestSpellerAssertions(speller);
        result.assertTextsCorrectionRequest("[\"service", 0);
        result.assertTextsCorrectionRequest("value", 1);
    }

    @DataProvider(name = "Create list of words to check")
    public static Object[] createListOfWords() {
        List<String> wordsList = new ArrayList<>();
        wordsList.add("spll");
        wordsList.add("srvice");
        wordsList.add("vlue");

        return wordsList.toArray();
    }

    @Test(dataProvider = "Create list of words to check", description = "Verify that incorrect spelling returns suggestions")
    void verifySuggestionsAreReturned(String word) {

        SpellerDTO[] speller = new RestSpellerSteps().checkText(word);

        RestSpellerAssertions result = new RestSpellerAssertions(speller);
        result.assertNumberOfCorrectionsIsNotZero();
    }

}
