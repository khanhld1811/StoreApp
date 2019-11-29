package com.duykhanh.storeapp.model.data.order;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duykhanh.storeapp.daos.DatabaseHelper;
import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.OrderDetail;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;
import com.duykhanh.storeapp.presenter.order.PaymentContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHandle implements PaymentContract.Handle {
    final String TAG = this.getClass().toString();

    SQLiteDatabase database;

    public PaymentHandle(PaymentContract.View iView) {
        database = new DatabaseHelper((Context) iView).getWritableDatabase();
    }

    //    GET Orders Detail từ SQLite
    @Override
    public void getOrderDetails(OnGetOrderDetailsListener listener, Order order) {
        try {
            List<OrderDetail> orderDetails = new ArrayList<>();
            String truyvan = "SELECT * FROM " + DatabaseHelper.TABLE_CART;
            Cursor cursor = database.rawQuery(truyvan, null);
            if (cursor.moveToFirst()) {
                do {
                    int cartid = cursor.getInt(0);
                    String productid = cursor.getString(1);
                    String name = cursor.getString(2);
                    int quantity = cursor.getInt(3);
                    long price = cursor.getLong(4);
                    long total = cursor.getLong(5);
                    byte[] image = cursor.getBlob(6);
                    OrderDetail orderDetail = new OrderDetail("", productid, quantity);
                    orderDetails.add(orderDetail);
                }
                while (cursor.moveToNext());
                listener.onGetOrderDetailsFinished(order, orderDetails);
            }
        } catch (Exception e) {
            listener.onGetOrderDetailFailure(e);
        }
    }

    @Override
    public void postOrder(OnPostOrderListener listener, Order order, List<OrderDetail> orderDetails) {
        DataClient apiService = ApiUtils.getProductList();

        Call<Order> call = apiService.postOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }
                for (OrderDetail orderDetail : orderDetails) {
                    orderDetail.setOrderId(response.body().getIdo());
                }
                listener.onPostOrderFinished(orderDetails);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                listener.onPostOrderFailure(t);
            }
        });
    }

    @Override
    public void postOrderDetail(OnPostOrderDetailListener listener, List<OrderDetail> orderDetails) {

        for (OrderDetail orderDetail : orderDetails) {
            DataClient apiService = ApiUtils.getProductList();

            Call<Order> call = apiService.postOrderDetail(orderDetail);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + response.code());
                        return;
                    }
                    listener.onPostOrderDetailFinished(orderDetails);
                    Log.d(TAG, "onResponse: posted" + orderDetail.toString());
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    listener.onPostOrderDetailFailure(t);
                }
            });
        }

    }

    @Override
    public void deleteCarts(OnDeleteCartsListener listener, List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            String cartProductId = orderDetail.getProductId();
            try {
                database.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.TABLE_CART_IDP + "=?", new String[]{cartProductId});
                listener.onDeleteCartsFinished();
            } catch (Exception e) {
                listener.onDeleteCartsFailure(e);
            }
        }
    }
}
