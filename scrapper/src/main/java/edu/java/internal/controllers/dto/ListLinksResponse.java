package edu.java.internal.controllers.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter public class ListLinksResponse {
    private final List<LinkResponse> links = new ArrayList<>();

    public void setLinks(List<String> urls, Long id) {
        for (String s : urls) {
            LinkResponse linkResponse = new LinkResponse();
            linkResponse.setId(id);
            linkResponse.setUrl(s);
            links.add(linkResponse);
        }
    }

    public int getSize() {
        return links.size();
    }
}
