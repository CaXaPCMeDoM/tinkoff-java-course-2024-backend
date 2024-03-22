package edu.java.service.jdbc;

import edu.java.dao.jdbc.LinkDao;
import edu.java.service.LinkUpdater;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdater implements LinkUpdater {
    private final LinkDao linkDao;

    public JdbcLinkUpdater(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    @Override
    public void updateLinkLastCheckTime(Long linkId) {
        linkDao.updateLastCheckTimeByLink(linkId);
    }
}
