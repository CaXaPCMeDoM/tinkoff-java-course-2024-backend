package edu.java.client.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.client.Converter;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    @JsonProperty("question_id")
    private String questionId;
    @JsonProperty("last_activity_date")
    private OffsetDateTime lastActivityDate;

    public void setLastActivityDate(Long unixDate) throws MalformedURLException {
        this.lastActivityDate = Converter.unixLongToOffsetDateTime(unixDate);
    }

    public void setLastActivityDate(String unixTime) {
        long epochSeconds = Long.parseLong(unixTime);
        this.lastActivityDate = OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneOffset.UTC);
    }
}
