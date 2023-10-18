package com.vizzibl.scheduler.task;

import com.vizzibl.scheduler.proxy.EmailServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BadgeNotificationTask {

    @Autowired
    EmailServiceProxy emailService;

    @Scheduled(fixedDelay = 240000)
    public void badgeCollectionEmailProcess() {
        System.out.println("trigger started for badge collection");
        System.out.println(Instant.now());
        emailService.emailBadgeNotification();
        System.out.println(Instant.now());
        System.out.println("trigger completed for badge collection");
    }
}
