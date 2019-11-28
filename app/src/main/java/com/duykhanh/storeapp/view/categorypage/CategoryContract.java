package com.duykhanh.storeapp.view.categorypage;

import com.duykhanh.storeapp.model.Category;
import com.duykhanh.storeapp.view.homepage.ProductListContract;

import java.util.List;

/**
 * Created by Duy Khánh on 11/25/2019.
 */
public interface CategoryContract {
    // interface cho ProductHandle
    interface Handle {
        interface OnFinishedListener {
            void onFinished(List<Category> categoryList);

            void onFailure(Throwable t);
        }

        interface OnFinishedListenderGetCount{
            void onFinished(int countProduct);
            void onFaild();
        }

        interface OnCountProductCart{
            void getCountProductCart(OnFinishedListenderGetCount handleCount);
        }
        void getCategory(CategoryContract.Handle.OnFinishedListener onFinishedListener);
    }


    // interface cho HomeFragment
    interface View {

        void senDataToGridView(List<Category> categoryList);

        void onResponseFailure(Throwable throwable);

        void sendCountProduct(int countProduct);

    }

    // interface cho preseenter
    interface Presenter {
        void requestDataFromServer();

        void requestDataCountFormDB();
    }
}
