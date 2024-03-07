package edu.java.external.client;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Converter {
    private Converter() {
    }

    public static OffsetDateTime unixLongToOffsetDateTime(long unix) {
        return Instant.ofEpochSecond(unix).atOffset(ZoneOffset.UTC);
    }
}
