package com.duykhanh.storeapp.presenter.category;

import android.content.Context;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.model.data.category.CategoryHandle;
import com.duykhanh.storeapp.model.data.category.CountProductCategoryHandle;

import java.util.List;

/**
 * Created by Duy Khánh on 11/26/2019.
 */
public class CategoryProductListPresenter implements CategoryProductListContract.Presenter,
        CategoryProductListContract.Handle.OnFinishedListener,
        CategoryProductListContract.Handle.OnFinishedListenderGetCount{

    CategoryProductListContract.View view;
    CategoryProductListContract.Handle handle;
    CategoryProductListContract.Handle.OnCountProductCart handleCount;

    public CategoryProductListPresenter(CategoryProductListContract.View view, Context context) {
        this.view = view;
        handle = new CategoryHandle();
        handleCount = new CountProductCategoryHandle(context);
    }

    @Override
    public void requestDataFromServer(String id_category) {
        handle.getProductListCategory(this,id_category);
    }

    @Override
    public void requestDataCountFormDB() {
        handleCount.getCountProductCart(this);

    }

    @Override
    public void onFinished(List<Product> productList) {
        view.senDataToRecyclerView(productList);
    }

    @Override
    public void onFailure(Throwable t) {
        view.onResponseFailure(t);
    }

    @Override
    public void onFinished(int countProduct) {
        if(countProduct != 0){
            if(view != null){
                view.showSizeCart();
            }
        }
        else {
            if(view != null) {
                view.hiddenSizeCart();
            }
        }
        view.sendCountProduct(countProduct);
    }

    @Override
    public void onFaild() {

    }
}
