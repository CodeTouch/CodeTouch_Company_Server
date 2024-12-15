package com.tagmaster.codetouch.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateAndGenderChange {

    public static String DateTimeToDate(LocalDateTime data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(data);
    }
    public static String DateToDateTime(LocalDate data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(data);
    }

    public static Integer genderChange(String birth, String gender){
        birth = birth.substring(0, birth.indexOf("-"));
        if (Integer.parseInt(birth) > 1999) {
            if (gender.equals("male")) {
                return 3;
            } else {
                return 4;
            }
        } else {
            if (gender.equals("male")) {
                return 1;
            } else {
                return 2;
            }
        }
    }
}
