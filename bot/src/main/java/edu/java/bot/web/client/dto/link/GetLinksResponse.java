package edu.java.bot.web.client.dto.link;

import java.util.List;
import lombok.Data;

@Data
public class GetLinksResponse {
    private List<Link> links;
    private int size;
}
