package com.duykhanh.storeapp.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formater {
    final String TAG = this.getClass().toString();

    public String formatDate(Date date) {
        String dateString = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public String formatMoney(int money) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(money);

    }

    public String formatImageLink(String inputLink) {
        return "http://192.168.1.17" + inputLink.substring(16);
    }
}