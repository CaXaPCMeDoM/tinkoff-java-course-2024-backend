package edu.java.client.stackoverflow;

import edu.java.client.Converter;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Item {
    private String question_id;
    private OffsetDateTime last_activity_date;

    public void setLast_activity_date(long last_activity_date) {
        this.last_activity_date = Converter.UnixToOffsetDateTime(last_activity_date);
    }
}
