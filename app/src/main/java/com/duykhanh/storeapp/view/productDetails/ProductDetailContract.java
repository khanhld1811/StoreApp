package com.duykhanh.storeapp.view.productDetails;

import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;

import java.util.List;

public interface ProductDetailContract {

    interface Handle {
        void getProductDetail(OnGetProductDetailListener listener, String productId);

        void getCommentByIdp(OnGetCommentByIdpListener listener, String productId);

        interface OnGetProductDetailListener {
            void onGetProductDetailFinished(Product product);

            void onGetProductDetailFailure(Throwable throwable);
        }

        interface OnGetCommentByIdpListener {
            void onGetCommentByIdpFinished(List<Comment> comment);

            void onGetCommentByIdpFailure(Throwable throwable);
        }
    }

    interface View {
        void setDataToView(Product product);

        void onResponseFailure(Throwable throwable);

        void setCommentsToRecyclerView(List<Comment> comments);

        void onCommentsResponseFailure(Throwable throwable);

        void showProgress();

        void hideProgress();

    }

    interface Presenter {
        void requestDataFromServer(String productId);

        void requestCommentDataFromServer(String productId);

        void onDestroy();
    }

}
