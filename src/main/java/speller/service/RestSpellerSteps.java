package speller.service;

import com.google.gson.Gson;
import speller.DTO.SpellerDTO;
import speller.ReadConfig;

import java.util.HashMap;

public class RestSpellerSteps extends ReadConfig {
    public RestSpellerSteps() {
        getDataFromProperties();
    }

    public SpellerDTO[] checkText(String param) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("text", param);

        return
                new Gson().fromJson(
                        new RestSpellerService()
                                .getWithParams(checkTextEndpoint, params)
                                .getBody().asString(), SpellerDTO[].class);
    }

    public SpellerDTO[][] checkTexts(String ... text) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("text", text);

        return
                new Gson().fromJson(
                        new RestSpellerService()
                                .getWithParams(checkTextsEndpoint, params)
                                .getBody().asString(), SpellerDTO[][].class);
    }

}
