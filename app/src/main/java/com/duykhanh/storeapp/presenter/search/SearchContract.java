package com.duykhanh.storeapp.presenter.search;

import android.content.Context;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

public interface SearchContract {

    interface Handle {
        void getProductByKey(OnGetProductByKeyListener listener, String searchingKey, int pageNo);

        void saveSearchKey(OnSaveSearchKeyListener listener, String searchKey);

        void getSearchKeys(OnGetSearchKeysListener listener);

        interface OnGetProductByKeyListener {
            void onGetProductByKeyFinished(List<Product> products);

            void onGetProductByKeyFailure(Throwable throwable);
        }

        interface OnSaveSearchKeyListener {
            void onSaveSearchKeyFinished();

            void onSaveSearchKeyFailure(Throwable throwable);
        }

        interface OnGetSearchKeysListener {
            void onGetSearchKeysFinished(List<String> searchKeys);

            void onGetSearchKeyFailure(Throwable throwable);
        }
    }

    interface View {
        void requestSearchComplete(List<Product> products);

        void showSearchKeys(List<String> searchKeys);

        void requestSearchFailure(Throwable throwable);

        Context getContext();

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void requestSearch(String searchingKey);

        void requestMoreData(String searchingKey, int pageNo);

        void requestSaveKey(String searchingKey);

        void requestShowSearchKeys();

        void onDestroy();
    }

}
