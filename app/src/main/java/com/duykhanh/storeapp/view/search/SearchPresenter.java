package com.duykhanh.storeapp.view.search;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter, SearchContract.Handle.OnGetProductByKeyListener {
    final String TAG = this.getClass().toString();

    SearchContract.Handle iHandle;
    SearchContract.View iView;

    public SearchPresenter(SearchContract.View iView) {
        this.iView = iView;
        iHandle = new SearchHandle();
    }

    @Override
    public void requestSearch(String searchingKey) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.getProductByKey(this, searchingKey);
    }

    @Override
    public void onGetProductByKeyFinished(List<Product> products) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestSearchComplete(products);
    }

    @Override
    public void onGetProductByKeyFailure(Throwable throwable) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestSearchFailure(throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
