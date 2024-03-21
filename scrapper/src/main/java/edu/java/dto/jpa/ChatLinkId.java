package edu.java.dto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class ChatLinkId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private JpaChatDto chatId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "url_id")
    private JpaLinkDto linkId;
}
