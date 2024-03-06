package edu.java.external.client.stackoverflow;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataForRequestStackoverflow {
    private List<Item> items;

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        for (Item s : items) {
            resultString.append(s.getQuestionId().toString());
        }
        return String.valueOf(resultString);
    }
}