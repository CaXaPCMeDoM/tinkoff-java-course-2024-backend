package edu.java.external.service;

import java.time.OffsetDateTime;

public interface CommonDataResponseClient {
    OffsetDateTime getTimeLastModified();
    String getTypeOfUpdate();
}
