package edu.java.client.github;

import java.net.URI;
import java.net.URISyntaxException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParserForGitHub {
    private static final String GIT_HUB_HOST = "github.com";
    private static final int LENGTH_OF_THE_REPOSITORY_LINK = 3;

    public static DataForRepositoryGitHub getOwnerAndRepo(String urlString) throws URISyntaxException {
        URI uri = new URI(urlString);
        String[] path = uri.getPath().split("/");
        String host = uri.getHost();
        if (GIT_HUB_HOST.equals(host) && path.length == LENGTH_OF_THE_REPOSITORY_LINK) {
            return new DataForRepositoryGitHub(path[1], path[2]);
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
