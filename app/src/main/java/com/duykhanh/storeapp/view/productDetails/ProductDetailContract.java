package com.duykhanh.storeapp.view.productDetails;

import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;

import java.util.List;

public interface ProductDetailContract {

    interface Handle {

        void getProductDetail(OnGetProductDetailListener listener, String productId);

        void getCommentByIdp(OnGetCommentByIdpListener listener, String productId);

        void createCartItem(OnCreateCartItemListener listener, CartItem cartItem);

        void getCartCounter(OnGetCartCounterListener listener);

        void increaseProductView(String productId);

        interface OnGetProductDetailListener {

            void onGetProductDetailFinished(Product product);

            void onGetProductDetailFailure(Throwable throwable);

        }

        interface OnGetCommentByIdpListener {
            void onGetCommentByIdpFinished(List<Comment> comment);

            void onGetCommentByIdpFailure(Throwable throwable);
        }

        interface OnCreateCartItemListener {
            void onCreateCartItemFinished();

            void onCreateCartItemFailure(Throwable throwable);
        }

        interface OnGetCartCounterListener {
            void onGetCartCounterFinished(int sumQuantity);
        }

    }

    interface View {
        //Set chi tiết sản phẩm
        void setDataToView(Product product);

        void onResponseFailure(Throwable throwable);

        //Set list Comment
        void setCommentsToRecyclerView(List<Comment> comments);

        void onCommentsResponseFailure(Throwable throwable);

        //Set số lượng hàng trong giỏ hàng
        void setCartItemCounter(int productQuantity);

        void onCartItemCountResponseFailure(Throwable throwable);

        void showProgress();

        void hideProgress();

    }

    interface Presenter {
        void requestProductFromServer(String productId);

        void requestCommentsFromServer(String productId);

        void requestCartCounter();

        void addCartItem(CartItem cartItem);

        void requestIncreaseView(String productId);

        void onDestroy();
    }

}
