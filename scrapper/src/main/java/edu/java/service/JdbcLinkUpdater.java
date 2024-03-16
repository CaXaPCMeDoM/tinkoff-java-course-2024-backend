package edu.java.service;

import edu.java.dao.LinkDao;
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
