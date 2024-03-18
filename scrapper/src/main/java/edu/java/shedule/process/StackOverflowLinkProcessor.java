package edu.java.shedule.process;

import edu.java.external.client.stackoverflow.DataForRequestStackoverflow;
import edu.java.external.client.stackoverflow.ParserForStackOverflow;
import edu.java.external.client.stackoverflow.client.StackOverflowClient;
import edu.java.external.service.CommonDataResponseClient;
import java.net.MalformedURLException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public CommonDataResponseClient process(String link) {
        try {
            String id = ParserForStackOverflow.checkingIfThisIsAQuestionAndReturningTheId(link);
            if (id != null) {
                DataForRequestStackoverflow dataForRequestStackoverflow = stackOverflowClient.fetchQuestion(id)
                    .onErrorResume(e -> Mono.empty())
                    .blockOptional()
                    .orElse(null);
                if (dataForRequestStackoverflow != null) {
                    return dataForRequestStackoverflow;
                }
            }
        } catch (MalformedURLException ignored) {
        }
        return null;
    }
}
