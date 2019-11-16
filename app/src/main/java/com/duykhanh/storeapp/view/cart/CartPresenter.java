package com.duykhanh.storeapp.view.cart;

import android.util.Log;

import com.duykhanh.storeapp.model.CartItem;

import java.util.List;

public class CartPresenter implements CartContract.Presenter, CartContract.Handle.OnGetCartItemsListener,
        CartContract.Handle.OnIncreaseQuantityListener,
        CartContract.Handle.OnDecreaseQuantityListener,
        CartContract.Handle.OnDeleteCartItemListener {

    final String TAG = this.getClass().toString();

    CartContract.View iView;
    CartContract.Handle iHandle;

    public CartPresenter(CartContract.View iView) {
        this.iView = iView;
        iHandle = new CartHandle(iView);
    }

    @Override
    public void requestCartItems() {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.getCartItems(this);
    }

    @Override
    public void onGetCartItemsFailure(Throwable throwable) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.onCartItemsResponseFailure(throwable);
    }

    //Request các kiểu

    @Override
    public void requestIncreaseQuantity(String cartProductId) {
        Log.d(TAG, "requestIncreaseQuantity: ");
        iHandle.increaseQuantity(this, cartProductId);
    }
    @Override
    public void requestDecreaseQuantity(String cartProductId) {
        Log.d(TAG, "requestDecreaseQuantity: ");
        iHandle.decreaseQuantity(this, cartProductId);

    }

    @Override
    public void requestDeleteCartItem(String cartProductId) {
        Log.d(TAG, "requestDeleteCartItem: ");
        iHandle.deleteCartItem(this, cartProductId);
    }

    //Request finished các kiểu
    @Override
    public void onGetCartItemsFinished(List<CartItem> cartItems) {
        iView.setCartItemsToCartRv(cartItems);
        if (iView != null) {
            iView.hideProgress();

        }
    }
    @Override
    public void onIncreaseQuantityFinished() {
        Log.d(TAG, "onIncreaseQuantityFinished: ");
        iHandle.getCartItems(this);
    }

    @Override
    public void onDecreaseQuantityFinished() {
        Log.d(TAG, "onDecreaseQuantityFinished: ");
        iHandle.getCartItems(this);
    }

    @Override
    public void onDeleteCartItemFinished() {
        Log.d(TAG, "onDeleteCartItemFinished: ");
        iHandle.getCartItems(this);
    }
    //Request Failure các kiểu
    @Override
    public void onIncreaseQuantityFailure(Throwable throwable) {
        Log.e(TAG, "onIncreaseQuantityFailure: ", throwable);
    }

    @Override
    public void onDecreaseQuantityFailure(Throwable throwable) {
        Log.e(TAG, "onDecreaseQuantityFailure: ", throwable);

    }

    @Override
    public void onDeleteCartItemFailure(Throwable throwable) {
        Log.e(TAG, "onDeleteCartItemFailure: ", throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
