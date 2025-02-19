package com.cybersoft.ecommerce.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date convertStringToDate(String dateString) {
<<<<<<< HEAD
        if (dateString == null) {
            return null;
        }
=======
>>>>>>> 9849228 (feat: order)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}