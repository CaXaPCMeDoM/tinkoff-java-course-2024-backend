package edu.java.bot.services.url.parser;

import java.net.URI;
import java.net.URISyntaxException;

public class URLParser {
    private boolean wasExceptionCaught = false;

    public String getDomainName(String url) {
        wasExceptionCaught = false;
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain;

        } catch (URISyntaxException e) {
            wasExceptionCaught = true;
            return null;
        }
    }

    public boolean isWasExceptionCaught() {
        return wasExceptionCaught;
    }
}
