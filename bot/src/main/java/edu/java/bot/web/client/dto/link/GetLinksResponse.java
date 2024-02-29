package edu.java.bot.web.client.dto.link;

import lombok.Data;
import java.util.List;

@Data
public class GetLinksResponse {
    private List<Link> links;
    private int size;
}
