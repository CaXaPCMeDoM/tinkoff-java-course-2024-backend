package edu.java.bot.services.user;

import java.util.HashMap;
import java.util.Map;

public class UserRegistry {
    private final Map<Long, String> users = new HashMap<>();

    public boolean tryAddNewUser(Long id, String name) {
        if (users.containsKey(id)) {
            return false;
        } else {
            users.put(id, name);
            return true;
        }
    }

    public boolean checkUserById(Long id) {
        return !users.containsKey(id);
    }

    public String getUserNameById(Long id) {
        return users.get(id);
    }
}
