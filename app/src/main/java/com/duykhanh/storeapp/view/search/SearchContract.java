package com.duykhanh.storeapp.view.search;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

public interface SearchContract {

    interface Handle {
        void getProductByKey(OnGetProductByKeyListener listener, String searchingKey);

        interface OnGetProductByKeyListener {
            void onGetProductByKeyFinished(List<Product> products);

            void onGetProductByKeyFailure(Throwable throwable);
        }
    }

    interface View {
        void requestSearchComplete(List<Product> products);

        void requestSearchFailure(Throwable throwable);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void requestSearch(String searchingKey);

        void onDestroy();
    }

}
