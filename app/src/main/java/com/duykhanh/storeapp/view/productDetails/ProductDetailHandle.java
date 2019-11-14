package com.duykhanh.storeapp.view.productDetails;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.duykhanh.storeapp.daos.DatabaseHelper;
import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.network.ApiUtils;
import com.duykhanh.storeapp.network.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailHandle implements ProductDetailContract.Handle {
    final String TAG = this.getClass().toString();

    private SQLiteDatabase database;

    public ProductDetailHandle(ProductDetailContract.View iView) {
        database = new DatabaseHelper((Context) iView).getWritableDatabase();
    }

    @Override
    public void getProductDetail(OnGetProductDetailListener listener, String productId) {
        DataClient apiService = ApiUtils.getProductList();

        Call<Product> call = apiService.getProductDetail(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    Log.d(TAG, "onResponse: " + response.body());
                    listener.onGetProductDetailFinished(product);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                listener.onGetProductDetailFailure(t);
            }
        });
    }

    @Override
    public void getCommentByIdp(OnGetCommentByIdpListener listener, String productId) {
        DataClient apiService = ApiUtils.getProductList();

        Call<List<Comment>> call = apiService.getCommentByIdp(productId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                listener.onGetCommentByIdpFinished(comments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                listener.onGetCommentByIdpFailure(t);
            }
        });
    }

    @Override
    public void createCartItem(OnCreateCartItemListener listener, Product product) {
        Log.d(TAG, "insertCart: " + product.toString());
        String selectQuerry = "select " + DatabaseHelper.TABLE_CART_ID + " from " + DatabaseHelper.TABLE_CART + " where " + DatabaseHelper.TABLE_CART_IDP + " = " + "'" + product.getId() + "'";

        String cartItemId = "";

        Cursor cursor = database.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                cartItemId = cursor.getString(0);
            }
            while (cursor.moveToNext());
        }
        if (cartItemId.equals("")) {
            Log.d(TAG, "createCartItem: Add");
            //Thêm sản phẩm vào giỏ hàng
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.TABLE_CART_IDP, product.getId());
            values.put(DatabaseHelper.TABLE_CART_NAME, product.getNameproduct());
            values.put(DatabaseHelper.TABLE_CART_QUANTITY, 1);
            values.put(DatabaseHelper.TABLE_CART_PRICE, product.getPrice());
            values.put(DatabaseHelper.TABLE_CART_TOTAL, product.getPrice());
            values.put(DatabaseHelper.TABLE_CART_IMAGE, product.getImg().get(0));
            try {
                database.insert(DatabaseHelper.TABLE_CART, null, values);
                listener.onCreateCartItemFinished();
            } catch (Exception e) {
                Log.e(TAG, "insertCart: ", e);
                listener.onCreateCartItemFailure(e);
            }
        } else {
            Log.d(TAG, "createCartItem: Update");
            try {
                database.execSQL("update " + DatabaseHelper.TABLE_CART + " set " + DatabaseHelper.TABLE_CART_QUANTITY + "=" + DatabaseHelper.TABLE_CART_QUANTITY + " +1 where " + DatabaseHelper.TABLE_CART_IDP + "=" + "'" + product.getId() + "'");
                listener.onCreateCartItemFinished();
            } catch (Exception e) {
                listener.onCreateCartItemFailure(e);
            }
        }
    }

    @Override
    public void getCartCounter(OnGetCartCounterListener listener) {
        int sumQuantity = 0;
        String selectQuerry = "select " + DatabaseHelper.TABLE_CART_QUANTITY + " from " + DatabaseHelper.TABLE_CART;
        Cursor cursor = database.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                int quantity = cursor.getInt(0);
                sumQuantity += quantity;
            }
            while (cursor.moveToNext());
        }
        listener.onGetCartCounterFinished(sumQuantity);

    }
}
