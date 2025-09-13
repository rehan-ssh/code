package com.example.scaler.adapters;

import com.example.scaler.library.sendgrid.Sendgrid;
import org.springframework.stereotype.Component;

@Component
public class SendgridEmailAdapter implements EmailAdapter{

    private final Sendgrid sendgrid = new Sendgrid();
    @Override
    public void sendEmail(String email, String message) throws Exception {
        sendgrid.sendEmailAsync(email, message);
    }
}
