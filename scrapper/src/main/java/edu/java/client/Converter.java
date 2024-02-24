package edu.java.client;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Converter {
    public static OffsetDateTime UnixToOffsetDateTime(long unix){
        return Instant.ofEpochSecond(unix).atOffset(ZoneOffset.UTC);
    }
}
