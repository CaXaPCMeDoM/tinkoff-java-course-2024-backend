package edu.java.bot.services.url.parser;

import java.net.URI;
import java.net.URISyntaxException;

public class URLParser {

    public String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();

        } catch (URISyntaxException e) {
            return null;
        }
    }
}
