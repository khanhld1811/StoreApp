package com.duykhanh.storeapp.view.payment;

import com.duykhanh.storeapp.model.Order;
import com.duykhanh.storeapp.model.OrderDetail;

import java.util.List;

public interface PaymentContract {

    interface Handle {

        void getOrderDetails(OnGetOrderDetailsListener listener, Order order);

        void postOrder(OnPostOrderListener listener, Order order, List<OrderDetail> orderDetails);

        void postOrderDetail(OnPostOrderDetailListener listener, List<OrderDetail> orderDetails);

        void deleteCarts(OnDeleteCartsListener listener, List<OrderDetail> orderDetails);

        interface OnGetOrderDetailsListener {
            void onGetOrderDetailsFinished(Order order, List<OrderDetail> orderDetails);

            void onGetOrderDetailFailure(Throwable throwable);

        }

        interface OnPostOrderListener {
            void onPostOrderFinished( List<OrderDetail> orderDetails);

            void onPostOrderFailure(Throwable throwable);
        }

        interface OnPostOrderDetailListener {
            void onPostOrderDetailFinished(List<OrderDetail> orderDetails);

            void onPostOrderDetailFailure(Throwable throwable);
        }

        interface OnDeleteCartsListener {
            void onDeleteCartsFinished();
            void onDeleteCartsFailure(Throwable throwable);
        }
    }

    interface View {

        void onResponsePayedFailure(Throwable throwable);

        void onPayed();

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void requestOrderDetailsFromSql(Order order);

        void requestPay(Order order, List<OrderDetail> orderDetails);

        void onDestroy();
    }
}
