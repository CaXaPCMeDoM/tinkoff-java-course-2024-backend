package edu.java.dto.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChatLinkId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat")
    private JpaChatDto Chat;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "link")
    private JpaLinkDto Link;
}
