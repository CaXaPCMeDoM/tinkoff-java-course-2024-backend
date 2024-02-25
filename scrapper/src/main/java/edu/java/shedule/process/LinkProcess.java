package edu.java.shedule.process;

public interface LinkProcess {
    boolean canProcess(String link);

    String process(String link);
}
