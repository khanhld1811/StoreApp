package com.duykhanh.storeapp.view.categorypage;

import android.content.Context;

import com.duykhanh.storeapp.model.Category;
import com.duykhanh.storeapp.view.categorypage.countproductcategory.CountProductCategoryHandle;

import java.util.List;

/**
 * Created by Duy Khánh on 11/25/2019.
 */
public class CategoryPresenter implements CategoryContract.Presenter,
        CategoryContract.Handle.OnFinishedListener,
        CategoryContract.Handle.OnFinishedListenderGetCount{

    private CategoryContract.View view;
    private CategoryContract.Handle handle;
    private CategoryContract.Handle.OnCountProductCart handleCountProduct;


    public CategoryPresenter(CategoryContract.View view, Context context) {
        this.view = view;
        this.handle = new CategoryHandle();
        handleCountProduct = new CountProductCategoryHandle(context);
    }

    @Override
    public void onFinished(List<Category> categoryList) {
        view.senDataToGridView(categoryList);
    }

    @Override
    public void onFailure(Throwable t) {
        view.onResponseFailure(t);
    }

    @Override
    public void requestDataFromServer() {
        handle.getCategory(this);
    }

    @Override
    public void requestDataCountFormDB() {
        handleCountProduct.getCountProductCart(this);
    }

    @Override
    public void onFinished(int countProduct) {
        view.sendCountProduct(countProduct);
    }

    @Override
    public void onFaild() {

    }
}
