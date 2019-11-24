package com.duykhanh.storeapp.network;

import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.OrderDetail;
import com.duykhanh.storeapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Duy Khánh on 11/5/2019.
 */
public interface DataClient {

    // Yêu cầu tất cả dữ liệu sản phẩm
    @GET("str1/product")
    Call<List<Product>> getDataProduct(@Query("page") int PageNo);

    // Yêu cầu dữ liệu sản phảm theo id
    @GET("str1/product/{idProduct}")
    Call<Product> getProductDetail(@Path("idProduct") String productId);

    @GET("str1/comment/idp/{idp}")
    Call<List<Comment>> getCommentByIdp(@Path("idp") String productId);

    @POST("str1/order")
    Call<Order> postOrder(@Body Order order);

    @POST("str1/orderdetail")
    Call<Order> postOrderDetail(@Body OrderDetail orderDetail);
}
