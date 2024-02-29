package edu.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class LinkData {
    private final static Map<Long, Set<String>> LINKS = new HashMap<>();

    public void addLink(Long id, String url) {
        LINKS.computeIfAbsent(id, k -> new HashSet<>()).add(url);
    }

    public void registerId(Long id) {
        LINKS.putIfAbsent(id, new HashSet<>());
    }

    public void deleteIdAndLinks(Long id) {
        if (LINKS.containsKey(id)) {
            LINKS.remove(id);
        } else {
            throw new NoSuchElementException("Чат с ID " + id + " не найден.");
        }
    }

    public Set<String> getLinks(Long id) {
        return LINKS.getOrDefault(id, Collections.emptySet());
    }

    public List<String> getAllLinks() {
        List<String> allLinks = new ArrayList<>();
        for (Set<String> links : LINKS.values()) {
            allLinks.addAll(links);
        }
        return allLinks;
    }

    public List<Long> getIdsWithSameUrl(String url) {
        List<Long> ids = new ArrayList<>();
        for (Map.Entry<Long, Set<String>> entry : LINKS.entrySet()) {
            if (entry.getValue().contains(url)) {
                ids.add(entry.getKey());
            }
        }
        return ids;
    }

    public void deleteLink(Long id, String url) {
        Set<String> urls = LINKS.get(id);
        if (urls != null) {
            urls.remove(url);
            if (urls.isEmpty()) {
                LINKS.remove(id);
            }
        }
    }
}
