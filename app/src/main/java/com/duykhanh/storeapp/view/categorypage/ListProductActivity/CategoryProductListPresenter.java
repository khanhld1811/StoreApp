package com.duykhanh.storeapp.view.categorypage.ListProductActivity;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.view.categorypage.CategoryHandle;

import java.util.List;

/**
 * Created by Duy Khánh on 11/26/2019.
 */
public class CategoryProductListPresenter implements CategoryProductListContract.Presenter, CategoryProductListContract.Handle.OnFinishedListener{

    CategoryProductListContract.View view;
    CategoryProductListContract.Handle handle;

    public CategoryProductListPresenter(CategoryProductListContract.View view) {
        this.view = view;
        handle = new CategoryHandle();
    }

    @Override
    public void requestDataFromServer(String id_category) {
        handle.getProductListCategory(this,id_category);
    }

    @Override
    public void onFinished(List<Product> productList) {
        view.senDataToRecyclerView(productList);
    }

    @Override
    public void onFailure(Throwable t) {
        view.onResponseFailure(t);
    }
}
