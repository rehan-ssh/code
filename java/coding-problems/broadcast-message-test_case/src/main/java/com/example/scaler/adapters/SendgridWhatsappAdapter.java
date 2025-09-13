package com.example.scaler.adapters;

import com.example.scaler.library.sendgrid.Sendgrid;
import org.springframework.stereotype.Component;

@Component
public class SendgridWhatsappAdapter implements WhatsappAdapter{

    private final Sendgrid sendgrid = new Sendgrid();
    @Override
    public void sendWhatsappMessage(String phoneNumber, String message) throws Exception {
        sendgrid.sendWhatsApp(phoneNumber, message);
    }
}
