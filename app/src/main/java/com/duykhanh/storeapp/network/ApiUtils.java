package com.duykhanh.storeapp.network;

/**
 * Created by Duy Khánh on 11/5/2019.
 * Chứa đường dẫn server và sự kiện nhận, gửi dữ liệu đi
 */
public class ApiUtils {

    public static final String BASE_URL = "http://192.168.1.19:4444/api/str1/";
//    public static final String BASE_URL = "http://strdecor.herokuapp.com/api/str1/";

    //Nhận và gửi dữ liệu đi
    public static DataClient getProductList(){
        return RetrofitClient.getClient(BASE_URL).create(DataClient.class);
    }
}
