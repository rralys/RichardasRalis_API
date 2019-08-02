package speller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//так вот же это класс. Комментарии по нему написала в SimpleSpellerTests.class
public class ReadConfig {
    protected Properties property = new Properties();

    protected String domain;
    protected String checkTextEndpoint;
    protected String checkTextsEndpoint;

    protected String propertiesPath = this.getClass().getClassLoader().getResource("endpoints").getPath();

    protected void getDataFromProperties() {
        try (FileInputStream file = new FileInputStream(propertiesPath + "/config.properties")) {
            property.load(file);

            domain = property.getProperty("domain");
            checkTextEndpoint = property.getProperty("checkTextEndpoint");
            checkTextsEndpoint = property.getProperty("checkTextsEndpoint");

        } catch (IOException e) {
            System.err.println("Property file is not found.");
        }
    }

    public ReadConfig() {
        getDataFromProperties();
    }

    public String getDomain() {
        return domain;
    }

    public String getCheckTextEndpoint() {
        return domain.concat(checkTextEndpoint);
    }

    public String getCheckTextsEndpoint() {
        return domain.concat(checkTextsEndpoint);
    }

}
