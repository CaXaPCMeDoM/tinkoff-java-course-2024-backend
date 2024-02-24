package edu.java.client.stackoverflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class DataForRequestStackoverflow {
    private List<Item> items;
}
