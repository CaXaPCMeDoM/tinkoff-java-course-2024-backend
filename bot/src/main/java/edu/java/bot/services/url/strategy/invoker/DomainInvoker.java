package edu.java.bot.services.url.strategy.invoker;

import edu.java.bot.services.url.strategy.IDomainExecute;
import jakarta.annotation.Nullable;
import java.util.Objects;
import lombok.Setter;

public class DomainInvoker {
    @Nullable @Setter private IDomainExecute domainCommand;

    public void executeCommand() {
        Objects.requireNonNull(domainCommand).execute();
    }
}
