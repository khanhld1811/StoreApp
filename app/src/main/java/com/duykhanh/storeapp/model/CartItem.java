package com.duykhanh.storeapp.model;

import java.util.Arrays;

public class CartItem {
    int cartid;
    String productid;
    String name;
    long price;
    int quantity;
    long total;
    byte[]image;

    public CartItem() {
    }

    public CartItem(String productid, String name, long price, int quantity, long total, byte[] image) {
        this.productid = productid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.image = image;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartid=" + cartid +
                ", productid='" + productid + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + total +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
