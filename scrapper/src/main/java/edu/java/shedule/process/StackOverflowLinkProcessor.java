package edu.java.shedule.process;

import edu.java.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.client.stackoverflow.ParserForStackOverflow;
import edu.java.client.stackoverflow.client.StackOverflowClient;
import java.net.MalformedURLException;
import org.springframework.stereotype.Service;

@Service
public class StackOverflowLinkProcessor implements LinkProcess {
    private final StackOverflowClient stackOverflowClient;

    public StackOverflowLinkProcessor(StackOverflowClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    public boolean canProcess(String link) {
        return link.contains("stackoverflow.com");
    }

    @Override
    public String process(String link) {
        try {
            String id = ParserForStackOverflow.checkingIfThisIsAQuestionAndReturningTheId(link);
            if (id != null) {
                DataForRequestStackoverflow dataForRequestStackoverflow = stackOverflowClient.fetchQuestion(id).block();
                if (dataForRequestStackoverflow != null) {
                    return dataForRequestStackoverflow.toString();
                }
            }
        } catch (MalformedURLException e) {
            // Обработка исключения
        }
        return null;
    }
}
