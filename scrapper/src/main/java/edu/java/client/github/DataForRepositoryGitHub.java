package edu.java.client.github;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class DataForRepositoryGitHub {
    private String owner;
    private String repo;
}
