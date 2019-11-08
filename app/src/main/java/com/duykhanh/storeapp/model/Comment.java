package com.duykhanh.storeapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Comment {
    @SerializedName("imgc")
    @Expose
    private List<String> img;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("idp")
    @Expose
    private String idp;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("point")
    @Expose
    private float point;
    @SerializedName("idu")
    @Expose
    private String idu;
    @SerializedName("title")
    @Expose
    private String title;

    public Comment(List<String> img, String id, String content, String idp, Date date, float point, String idu, String title) {
        this.img = img;
        this.id = id;
        this.content = content;
        this.idp = idp;
        this.date = date;
        this.point = point;
        this.idu = idu;
        this.title = title;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "img=" + img +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", idp='" + idp + '\'' +
                ", date=" + date +
                ", point=" + point +
                ", idu='" + idu + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
