package edu.java.bot.data.repository;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class URLRepository {
    private static final Map<String, Set<String>> urls = new ConcurrentHashMap<>(); // (user_id, url)
    private StringBuilder allUrls;

    public String getAllInString(String userId) {
        allUrls = new StringBuilder();
        Set<String> urls = URLRepository.urls.get(userId);
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
        urls.computeIfAbsent(userId, k -> new HashSet<>()).add(url);
    }

    public boolean deleteByUserId(String id, String url) {
        Set<String> urls = URLRepository.urls.get(id);
        if (urls != null) {
            return urls.remove(url);
        }
        return false;
    }
}

