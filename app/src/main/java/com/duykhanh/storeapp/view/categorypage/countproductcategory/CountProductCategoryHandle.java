package com.duykhanh.storeapp.view.categorypage.countproductcategory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.duykhanh.storeapp.daos.DatabaseHelper;
import com.duykhanh.storeapp.view.categorypage.CategoryContract;
import com.duykhanh.storeapp.view.categorypage.ListProductActivity.CategoryProductListContract;

/**
 * Created by Duy Khánh on 11/28/2019.
 */
public class CountProductCategoryHandle implements CategoryContract.Handle.OnCountProductCart,
        CategoryProductListContract.Handle.OnCountProductCart {
    SQLiteDatabase database;

    public CountProductCategoryHandle(Context context) {
        database = new DatabaseHelper(context).getWritableDatabase();
    }

    @Override
    public void getCountProductCart(CategoryContract.Handle.OnFinishedListenderGetCount handleCount) {
        int countProduct = 0;
        String selectQuerry = "select " + DatabaseHelper.TABLE_CART_ID + " from " + DatabaseHelper.TABLE_CART;
        Cursor cursor = database.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                countProduct = cursor.getCount();
            }
            while (cursor.moveToNext());
        }
        handleCount.onFinished(countProduct);
    }

    @Override
    public void getCountProductCart(CategoryProductListContract.Handle.OnFinishedListenderGetCount handleCount) {
        int countProduct = 0;
        String selectQuerry = "select " + DatabaseHelper.TABLE_CART_ID + " from " + DatabaseHelper.TABLE_CART;
        Cursor cursor = database.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                countProduct = cursor.getCount();
            }
            while (cursor.moveToNext());
        }
        handleCount.onFinished(countProduct);
    }
}
