package speller.functional;

import org.testng.annotations.Test;
import speller.DTO.SpellerDTO;
import speller.service.RestSpellerAssertions;
import speller.service.RestSpellerSteps;

//todo допиши еще один тест с DataProvider, пожалуйста
public class SpellerTests {
    @Test(description = "Verify that checkText endpoint returns correct suggestions")
    void verifyCorrectSuggestionsForText() {

        SpellerDTO[] speller = new RestSpellerSteps().checkText("datea");

        RestSpellerAssertions result = new RestSpellerAssertions(speller);
        result.assertRightInitialWord("datea");

        //эти три метода можно заменить одним assertTextsCorrectionRequestAre("data","dates", "date")..
        result.assertTextCorrectionRequest("data");
        result.assertTextCorrectionRequest("dates");
        result.assertTextCorrectionRequest("date");

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


}
