package com.duykhanh.storeapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formater {

    public String formatDate(Date date) {
        String dateString = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = simpleDateFormat.format(date);
        return dateString;
    }
}
