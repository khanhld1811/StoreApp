package com.duykhanh.storeapp.presenter.order;

import android.content.Context;

import com.duykhanh.storeapp.model.CartItem;

import java.util.List;

public interface CartContract {

    interface Handle {

        void getCartItems(OnGetCartItemsListener listener);

        void increaseQuantity(OnIncreaseQuantityListener listener, String cartProductId);

        void decreaseQuantity(OnDecreaseQuantityListener listener, String cartProductId);

        void deleteCartItem(OnDeleteCartItemListener listener, String cartProductId);

        interface OnGetCartItemsListener {
            void onGetCartItemsFinished(List<CartItem> cartItems);

            void onGetCartItemsFailure(Throwable throwable);
        }

        interface OnIncreaseQuantityListener {
            void onIncreaseQuantityFinished();

            void onIncreaseQuantityFailure(Throwable throwable);
        }

        interface OnDecreaseQuantityListener {
            void onDecreaseQuantityFinished();

            void onDecreaseQuantityFailure(Throwable throwable);
        }

        interface OnDeleteCartItemListener {
            void onDeleteCartItemFinished();

            void onDeleteCartItemFailure(Throwable throwable);
        }
    }

    interface Presenter {
        void requestCartItems();

        void requestIncreaseQuantity(String cartProductId);

        void requestDecreaseQuantity(String cartProductId);

        void requestDeleteCartItem(String cartProductId);

        void onDestroy();
    }

    interface View {
        // Đưa danh sách mặt hàng trong giỏ hàng vào Recycler View
        void setCartItemsToCartRv(List<CartItem> cartItems);

        void onCartItemsResponseFailure(Throwable throwable);

        Context getContext();

        void showProgress();

        void hideProgress();
    }
}
