package edu.java.client.stackoverflow;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class DataForRequestStackoverflow {
    private List<Item> items;
}
