package com.vizzibl.scheduler.task;

import com.vizzibl.scheduler.proxy.DigitalWalletServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BulkRegistrationTask {

    @Autowired
    DigitalWalletServiceProxy digitalWalletServiceProxy;

    @Scheduled(fixedDelay = 180000)
    public void emailRegistrationScheduler() {
        System.out.println("trigger started for auto registration");
        System.out.println(Instant.now());
        digitalWalletServiceProxy.scheduler();
        System.out.println(Instant.now());
        System.out.println("trigger completed for auto registration");
    }
    
}
