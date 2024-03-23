package edu.java.service.jpa;

import edu.java.service.LinkUpdater;
import org.springframework.transaction.annotation.Transactional;

public class JpaLinkUpdater implements LinkUpdater {
    @Transactional
    @Override
    public void updateLinkLastCheckTime(Long linkId) {

    }
}
