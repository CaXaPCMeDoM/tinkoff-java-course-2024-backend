package edu.java.service.jdbc;

import edu.java.dao.jdbc.JdbcLinkDao;
import edu.java.service.LinkUpdater;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcLinkDao jdbcLinkDao;

    public JdbcLinkUpdater(JdbcLinkDao jdbcLinkDao) {
        this.jdbcLinkDao = jdbcLinkDao;
    }

    @Transactional
    @Override
    public void updateLinkLastCheckTime(Long linkId) {
        jdbcLinkDao.updateLastCheckTimeByLink(linkId);
    }
}
