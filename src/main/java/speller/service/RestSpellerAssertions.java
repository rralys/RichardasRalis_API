package speller.service;

import speller.DTO.SpellerDTO;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RestSpellerAssertions {
    private SpellerDTO[] textAnswer;

    private SpellerDTO[][] textsAnswer;

    public RestSpellerAssertions(SpellerDTO[] response) {
        this.textAnswer = response;
    }

    public RestSpellerAssertions(SpellerDTO[][] response) {
        this.textsAnswer = response;
    }

    public void assertTextCorrectionRequest(String word) {
        assertTrue(textAnswer[0].getS().contains(word));
    }

    public void assertRightInitialWord(String word) {
        assertEquals(textAnswer[0].getWord(), word, "Wrong word");
    }

    public void assertRightNumberOfSuggestions(int  numberOfSuggestions) {
        assertEquals(textAnswer[0].getS().size(), numberOfSuggestions, "Wrong number of suggestions");
    }

    public void assertRightInitialWordLength(String length) {
        assertEquals(textAnswer[0].getLen(), length, "Wrong length of the initial word");
    }

    public void assertTextsCorrectionRequest(String text, int index) {
        assertTrue(textsAnswer[0][index].getS().contains(text), "No suggestions");
    }

}
