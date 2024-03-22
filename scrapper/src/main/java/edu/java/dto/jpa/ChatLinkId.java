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
    @JoinColumn(name = "chat")
    private JpaChatDto Chat;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "link")
    private JpaLinkDto Link;
}
