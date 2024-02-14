package edu.java.bot.services.url.parser;

import lombok.Getter;
import java.net.URI;
import java.net.URISyntaxException;

@Getter public class URLParser {
    private boolean wasExceptionCaught = false;

    public String getDomainName(String url) {
        wasExceptionCaught = false;
        try {
            URI uri = new URI(url);
            return uri.getHost();

        } catch (URISyntaxException e) {
            wasExceptionCaught = true;
            return null;
        }
    }
}
