package edu.java.client.stackoverflow;

import java.net.MalformedURLException;
import java.net.URL;

public class ParserForStackOverflow {
    private static final int LINK_LENGTH = 3;

    private ParserForStackOverflow() {
    }

    public static String checkingIfThisIsAQuestionAndReturningTheId(String urlForCheck) throws MalformedURLException {
        URL url = new URL(urlForCheck);

        String path = url.getPath();
        String[] pathParts = path.split("/");

        if (
            "stackoverflow.com".equals(url.getHost())
                && pathParts.length >= LINK_LENGTH
                && "questions".equals(pathParts[1])
        ) {
            String questionId = pathParts[2];
            return questionId;
        } else {
            return null;
        }
    }
}
