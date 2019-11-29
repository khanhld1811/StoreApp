package com.duykhanh.storeapp.presenter.search;

import android.util.Log;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.model.data.search.SearchHandle;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter,
        SearchContract.Handle.OnGetProductByKeyListener,
        SearchContract.Handle.OnSaveSearchKeyListener,
        SearchContract.Handle.OnGetSearchKeysListener {
    final String TAG = this.getClass().toString();

    SearchContract.Handle iHandle;
    SearchContract.View iView;

    public SearchPresenter(SearchContract.View iView) {
        this.iView = iView;
        iHandle = new SearchHandle(iView);
    }

    @Override
    public void requestSearch(String searchingKey) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.getProductByKey(this, searchingKey, 1);
    }

    @Override
    public void requestSaveKey(String searchingKey) {
        iHandle.saveSearchKey(this,searchingKey);
    }

    @Override
    public void requestShowSearchKeys() {
        iHandle.getSearchKeys(this);
    }

    @Override
    public void onGetProductByKeyFinished(List<Product> products) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestSearchComplete(products);
    }

    @Override
    public void requestMoreData(String searchingKey, int pageNo) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.getProductByKey(this, searchingKey, pageNo);
    }

    @Override
    public void onSaveSearchKeyFinished() {
        iHandle.getSearchKeys(this);
    }

    @Override
    public void onGetSearchKeysFinished(List<String> searchKeys) {
        iView.showSearchKeys(searchKeys);
    }

    @Override
    public void onGetSearchKeyFailure(Throwable throwable) {
        Log.e(TAG, "onGetSearchKeyFailure: ", throwable);
    }

    @Override
    public void onSaveSearchKeyFailure(Throwable throwable) {
        Log.e(TAG, "onSaveSearchKeyFailure: ", throwable);
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
