package com.duykhanh.storeapp.presenter.category;

import com.duykhanh.storeapp.model.Product;

import java.util.List;

/**
 * Created by Duy Khánh on 11/26/2019.
 */
public interface CategoryProductListContract {
    // interface cho ProductHandle
    interface Handle {
        interface OnFinishedListener {
            void onFinished(List<Product> productList);

            void onFailure(Throwable t);
        }

        interface OnFinishedListenderGetCount {
            void onFinished(int countProduct);

            void onFaild();
        }

        interface OnCountProductCart {
            void getCountProductCart(CategoryProductListContract.Handle.OnFinishedListenderGetCount handleCount);
        }

        void getProductListCategory(CategoryProductListContract.Handle.OnFinishedListener onFinishedListener, String id_category);
    }


    // interface cho HomeFragment
    interface View {

        void senDataToRecyclerView(List<Product> productList);

        void sendCountProduct(int countProduct);

        void onResponseFailure(Throwable throwable);

        void showSizeCart();

        void hiddenSizeCart();

    }

    // interface cho preseenter
    interface Presenter {
        void requestDataFromServer(String id_category);

        void requestDataCountFormDB();
    }
}
