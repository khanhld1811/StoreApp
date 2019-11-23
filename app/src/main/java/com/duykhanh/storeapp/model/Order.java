package com.duykhanh.storeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Order {
    @SerializedName("_id")
    @Expose
    private String ido;
    String idu;
    @SerializedName("day")
    @Expose
    Date date;

    public Order(String idu, Date date) {
        this.idu = idu;
        this.date = date;
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdo() {
        return ido;
    }

    public void setIdo(String ido) {
        this.ido = ido;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ido='" + ido + '\'' +
                ", idu='" + idu + '\'' +
                ", date=" + date +
                '}';
    }
}
