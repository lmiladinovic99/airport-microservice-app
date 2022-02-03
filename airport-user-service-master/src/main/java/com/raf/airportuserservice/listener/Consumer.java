package com.raf.airportuserservice.listener;

import com.raf.airportuserservice.service.EmailSenderService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private EmailSenderService emailSenderService;

    public Consumer(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @JmsListener(destination = "users.queue")
    public void consume(String queueItem) {
        String[] keyValue = queueItem.split(":");
        if(keyValue[0].equals("cancel"))
            emailSenderService.handleCanceledFlight(keyValue[1]);
        else if(keyValue[0].equals("miles"))
            emailSenderService.addMiles(keyValue[1]);

    }
}
