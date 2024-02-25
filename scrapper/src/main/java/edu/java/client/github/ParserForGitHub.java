package edu.java.client.github;

import java.net.URI;
import java.net.URISyntaxException;

public class ParserForGitHub {
    private static final String GIT_HUB_HOST = "github.com";
    private static final int LENGTH_OF_THE_REPOSITORY_LINK = 3;

    private ParserForGitHub() {
    }

    public static DataForRepositoryGitHub getOwnerAndRepo(String urlString) throws URISyntaxException {
        DataForRepositoryGitHub dataForRepositoryGitHub = new DataForRepositoryGitHub();
        URI uri = new URI(urlString);
        String[] path = uri.getPath().split("/");
        String host = uri.getHost();
        if (GIT_HUB_HOST.equals(host) && path.length == LENGTH_OF_THE_REPOSITORY_LINK) {
            dataForRepositoryGitHub.setOwner(path[1]);
            dataForRepositoryGitHub.setRepo(path[2]);
            return dataForRepositoryGitHub;
        } else {
            return null;
        }
    }

    public static boolean checkIsGitHubHost(String urlString) throws URISyntaxException {
        URI uri = new URI(urlString);
        String host = uri.getHost();
        return GIT_HUB_HOST.equals(host);
    }
}
