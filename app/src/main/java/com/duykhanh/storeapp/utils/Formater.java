package com.duykhanh.storeapp.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.duykhanh.storeapp.utils.Constants.*;

public class Formater {
    final String TAG = this.getClass().toString();

    public String formatDate(Date date) {
        String dateString = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public String formatMoney(int money) {
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        return formatter.format(money);

    }

    public String formatImageLink(String inputLink) {

        Log.d(TAG, "formatImageLink: " + inputLink);
//        return "https://strdecor.herokuapp.com/" + inputLink.substring(22);
        return BASE_URL + inputLink.substring(16);
    }

    public String formatNameProduct(String nameProduct){
        if(nameProduct.length() > 20) {
            String formatStringProduct = nameProduct.substring(22);
            String nameProductCut = nameProduct.replace(formatStringProduct,"...");
            return nameProductCut;
        }

        return nameProduct;
    }

    public String formatNameProductView(String nameProduct){
        if(nameProduct.length() > 40) {
            String formatStringProduct = nameProduct.substring(40);
            String nameProductCut = nameProduct.replace(formatStringProduct,"...");
            return nameProductCut;
        }
        return nameProduct;

    }
}
