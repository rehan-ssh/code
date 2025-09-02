package com.example.ecom.utils;

import java.util.Calendar;
import java.util.Date;

public class GiftCardUtils {

    public static String generateGiftCardCode(){
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int)(s.length() * Math.random());
            sb.append(s.charAt(index));
        }
        return sb.toString();
    }

    public static Date getExpirationDate(Date now){
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, 365);
        return c.getTime();
    }
}
