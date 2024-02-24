package edu.java.client.github;

import java.net.URI;
import java.net.URISyntaxException;

public class ParserForGitHub {
    private static final String GIT_HUB_HOST = "github.com";

    public DataForRepositoryGitHub getOwnerAndRepo(String urlString) throws URISyntaxException {
        DataForRepositoryGitHub dataForRepositoryGitHub = new DataForRepositoryGitHub();
        URI uri = new URI(urlString);
        String[] path = uri.getPath().split("/");
        String host = uri.getHost();
        if (GIT_HUB_HOST.equals(host) && path.length == 3) {
            dataForRepositoryGitHub.setOwner(path[1]);
            dataForRepositoryGitHub.setRepo(path[2]);
            return dataForRepositoryGitHub;
        } else {
            return null;
        }
    }
}
