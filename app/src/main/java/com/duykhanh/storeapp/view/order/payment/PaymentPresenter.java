package com.duykhanh.storeapp.view.order.payment;

import android.util.Log;

import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.OrderDetail;
import com.duykhanh.storeapp.model.User;

import java.util.List;

public class PaymentPresenter implements PaymentContract.Presenter,
        PaymentContract.Handle.OnGetCartItemsListener,
        PaymentContract.Handle.OnPostOrderListener,
        PaymentContract.Handle.OnPostOrderDetailListener,
        PaymentContract.Handle.OnGetOrderDetailsListener,
        PaymentContract.Handle.OnDeleteCartsListener,
        PaymentContract.Handle.OnGetCurrentUserListener,
        PaymentContract.Handle.OnUpdateUserInfoListener {
    final String TAG = this.getClass().toString();

    PaymentContract.View iView;
    PaymentContract.Handle iHandle;

    public PaymentPresenter(PaymentContract.View iView) {
        this.iView = iView;
        iHandle = new PaymentHandle(iView);
    }

    @Override //Yêu cầu trạng thái đăng nhập
    public void requestCurrentUser() {
        iHandle.getCurrentUser(this);
    }

    @Override //Hoàn thành yêu cầu trạng thái đăng nhập
    public void onGetCurrentUserFinished(User user) {
        iView.requestCurrentUserComplete(user);
    }

    @Override //Gửi yêu cầu update thông tin người dùng
    public void requestUpdateUserInfo(User user) {
        iHandle.updateUserInfo(this, user);
    }

    @Override //Yêu cầu update hoàng thành
    public void onUpdateUserInfoFinished() {
    }

    @Override //Gửi yêu cầu lấy danh sách sản phẩm trong giỏ hàng từ SQLite
    public void requestCartItemsFromSql() {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.getCartItems(this);
    }

    @Override //Lấy danh sách mặt hàng trong giỏ hàng thành công.
    public void onGetCartItemsFinished(List<CartItem> cartItems) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestCartItemsComplete(cartItems);
    }

    @Override
    public void requestOrderDetailsFromSql(Order order) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.getOrderDetails(this, order);
    }

    @Override
    public void onGetOrderDetailsFinished(Order order, List<OrderDetail> orderDetails) {
        iHandle.postOrder(this, order, orderDetails);
    }

    @Override
    public void onGetOrderDetailFailure(Throwable throwable) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestPayedFailure(throwable);
    }

    //    Request POST Order và Listener

    @Override
    public void requestPay(Order order, List<OrderDetail> orderDetails) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.postOrder(this, order, orderDetails);
    }

    @Override
    public void onPostOrderFinished(List<OrderDetail> orderDetails) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.postOrderDetail(this, orderDetails);
    }

    @Override
    public void onPostOrderFailure(Throwable throwable) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestPayedFailure(throwable);
    }

    //    POST Orders Detail Listener

    @Override
    public void onPostOrderDetailFinished(List<OrderDetail> orderDetails) {
        Log.d(TAG, "onPostOrderDetailFinished: ");
        iHandle.deleteCarts(this, orderDetails);
    }

    @Override //Delete Carts Listener
    public void onDeleteCartsFinished() {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.onPayed();
    }

    @Override
    public void onGetCartItemsFailure(Throwable throwable) {
        iView.requestPayedFailure(throwable);
    }

    @Override
    public void onPostOrderDetailFailure(Throwable throwable) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.requestPayedFailure(throwable);
    }

    @Override
    public void onGetCurrentUserFailure(Throwable throwable) {
        iView.requestPayedFailure(throwable);
    }

    @Override
    public void onDeleteCartsFailure(Throwable throwable) {
        iView.requestPayedFailure(throwable);
    }

    @Override
    public void onUpdateUserInfoFailure(Throwable throwable) {
        iView.requestPayedFailure(throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
