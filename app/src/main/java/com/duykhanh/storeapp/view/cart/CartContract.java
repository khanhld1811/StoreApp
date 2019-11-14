package com.duykhanh.storeapp.view.cart;

public interface CartContract {

    interface Presenter {
        void requestCartItems();

        void increaseQuantity();

        void decreaseQuantity();

        void deleteCartItem();

        void onDestroy();
    }

    interface Handle {

    }

    interface View {

    }
}
