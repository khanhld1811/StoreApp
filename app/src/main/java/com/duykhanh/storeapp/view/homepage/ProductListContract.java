package com.duykhanh.storeapp.view.homepage;

import android.graphics.Movie;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

/**
 * Created by Duy Khánh on 11/5/2019.
 */
public interface ProductListContract {

    // interface cho ProductHandle
    interface Handle {

        interface OnFinishedListener {
            void onFinished(List<Product> productArrayList);

            void onFinishedLoadMore();

            void onFailure(Throwable t);
        }

        interface OnFinishedListenerView {
            void onFinishedView(List<Product> viewProductArrayList);

            void onFailureView(Throwable t);
        }

        void getProductList(OnFinishedListener onFinishedListener, int pageNo);

        void getProductView(OnFinishedListenerView onFinishedListenerView, int pageView);
    }


    // interface cho HomeFragment
    interface View {
        void showProgress();

        void hideProgress();

        void sendDataToRecyclerView(List<Product> productArrayList);

        void sendDataToHorizontalView(List<Product> viewProductArrayList);

        void onResponseFailure(Throwable throwable);

    }

    // interface cho preseenter
    interface Presenter {

        void getMoreData(int pageNo);

        void getMoreDataView(int pageView);

        void requestDataFromServer();

        void requestDataFromServerView();
    }
}
