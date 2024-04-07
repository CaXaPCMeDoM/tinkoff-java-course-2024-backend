package edu.java.updates.process;

import edu.java.external.service.CommonDataResponseClient;

public interface LinkProcess {
    boolean canProcess(String link);

    CommonDataResponseClient process(String link);
}
