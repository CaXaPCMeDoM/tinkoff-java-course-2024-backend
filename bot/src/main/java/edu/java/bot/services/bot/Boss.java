package edu.java.bot.services.bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Boss {
    private Long id;

    public Boss() {
    }

    @Override
    public String toString(){
        return String.valueOf(id);
    }
}
