package edu.java.internal.controllers.dto;

import edu.java.model.LinkData;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter public class ListLinksResponse {
    private final List<LinkResponse> links = new ArrayList<>();

    public void setLinks(Long id) {
        LinkData linkData = new LinkData();
        Set<String> list = linkData.getLinks(id);
        for (String s : list) {
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
