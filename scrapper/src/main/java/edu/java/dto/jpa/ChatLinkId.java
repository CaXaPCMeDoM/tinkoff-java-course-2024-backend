package edu.java.dto.jpa;

import edu.java.dto.ChatDto;
import edu.java.dto.LinkDto;
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
    @JoinColumn(name = "chat_id")
    private ChatDto chat;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "url_id")
    private LinkDto link;
}
