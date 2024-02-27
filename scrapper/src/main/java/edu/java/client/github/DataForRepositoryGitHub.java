package edu.java.client.github;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataForRepositoryGitHub {
    private String owner;
    private String repo;
}
