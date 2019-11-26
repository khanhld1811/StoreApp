package com.duykhanh.storeapp.view.categorypage;

import com.duykhanh.storeapp.model.Category;

import java.util.List;

/**
 * Created by Duy Khánh on 11/25/2019.
 */
public class CategoryPresenter implements CategoryContract.Presenter, CategoryContract.Handle.OnFinishedListener {

    private CategoryContract.View view;
    private CategoryContract.Handle handle;

    public CategoryPresenter(CategoryContract.View view) {
        this.view = view;
        this.handle = new CategoryHandle();
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
}
