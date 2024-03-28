package edu.java.service.jpa;

import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkUpdater;

public class JpaLinkUpdater implements LinkUpdater {
    private final JpaLinkRepository jpaLinkRepository;

    public JpaLinkUpdater(JpaLinkRepository jpaLinkRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @Override
    public void updateLinkLastCheckTime(Long linkId) {
        jpaLinkRepository.updateLastCheckTimeByLink(linkId);
    }
}
