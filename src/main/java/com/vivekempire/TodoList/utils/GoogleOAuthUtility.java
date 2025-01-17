package com.vivekempire.TodoList.utils;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

@Component
public class GoogleOAuthUtility {
    private String googleClientId, googleSecret;
    public GoogleOAuthUtility() throws Exception {
        Properties props=new Properties();
        FileReader reader =new FileReader("src/main/resources/secret.properties");
        props.load(reader);
        this.googleClientId=props.getProperty("GOOGLE_CLIENT_ID");
        this.googleSecret=props.getProperty("GOOGLE_CLIENT_SECRET");

    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public void setGoogleClientId(String googleClientId) {
        this.googleClientId = googleClientId;
    }

    public String getGoogleSecret() {
        return googleSecret;
    }

    public void setGoogleSecret(String googleSecret) {
        this.googleSecret = googleSecret;
    }
}
