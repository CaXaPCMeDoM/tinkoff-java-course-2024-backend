package edu.java.bot.data.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class URLRepository {
    private static HashMap<String, Set<String>> url = new HashMap<>(); // (user_id, url)

    public String getAllInString(String userId) {
        StringBuilder allUrls = new StringBuilder();
        Set<String> urls = url.get(userId);

        if (urls == null || urls.isEmpty()) {
            return null;
        } else {
            int i = 0;
            for (String ur : urls) {
                i++;
                allUrls.append(i).append(") ").append(ur).append("\n");
            }
            return allUrls.toString();
        }
    }

    public void addInMemoryList(String userId, String url) {
        URLRepository.url.computeIfAbsent(userId, k -> new HashSet<>()).add(url);
    }

    public boolean deleteByUserId(String id, String url) {
        Set<String> urls = URLRepository.url.get(id);
        if (urls != null) {
            return urls.remove(url);
        }
        return false;
    }
}

