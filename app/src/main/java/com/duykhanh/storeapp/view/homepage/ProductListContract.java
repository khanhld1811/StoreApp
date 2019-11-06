package com.duykhanh.storeapp.view.homepage;

import android.graphics.Movie;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

/**
 * Created by Duy Khánh on 11/5/2019.
 */
public interface ProductListContract {

    interface Handle {

        interface OnFinishedListener {
            void onFinished(List<Product> productArrayList);

            void onFailure(Throwable t);
        }

        void getProductList(OnFinishedListener onFinishedListener, int pageNo);
    }


    interface View {
        void showProgress();

        void hideProgress();

        void sendDataToRecyclerView(List<Product> movieArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void getMoreData(int pageNo);

        void requestDataFromServer();
    }
}
