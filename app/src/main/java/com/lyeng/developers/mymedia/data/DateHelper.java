package com.lyeng.developers.mymedia.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static Date stringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date seenDate = null;
        try {
            seenDate = format.parse(dateString);
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return seenDate;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(date);
        return dateTime;
    }
}
