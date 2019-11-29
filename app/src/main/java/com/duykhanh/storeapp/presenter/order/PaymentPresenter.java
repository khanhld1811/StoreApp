package com.duykhanh.storeapp.presenter.order;

import android.util.Log;

import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.OrderDetail;
import com.duykhanh.storeapp.model.data.order.PaymentHandle;

import java.util.List;

public class PaymentPresenter implements PaymentContract.Presenter,
        PaymentContract.Handle.OnPostOrderListener,
        PaymentContract.Handle.OnPostOrderDetailListener,
        PaymentContract.Handle.OnGetOrderDetailsListener,
        PaymentContract.Handle.OnDeleteCartsListener {
    final String TAG = this.getClass().toString();

    PaymentContract.View iView;
    PaymentContract.Handle iHandle;

    public PaymentPresenter(PaymentContract.View iView) {
        this.iView = iView;
        iHandle = new PaymentHandle(iView);
    }

    //    Request GET Order Detail và Listener
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
        iView.onResponsePayedFailure(throwable);
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
        iView.onResponsePayedFailure(throwable);
    }

    //    POST Orders Detail Listener
    @Override
    public void onPostOrderDetailFinished(List<OrderDetail> orderDetails) {
        Log.d(TAG, "onPostOrderDetailFinished: ");
        iHandle.deleteCarts(this, orderDetails);
    }

    @Override
    public void onPostOrderDetailFailure(Throwable throwable) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.onResponsePayedFailure(throwable);
    }

    //    Delete Carts Listener
    @Override
    public void onDeleteCartsFinished() {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.onPayed();
    }

    @Override
    public void onDeleteCartsFailure(Throwable throwable) {

    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
