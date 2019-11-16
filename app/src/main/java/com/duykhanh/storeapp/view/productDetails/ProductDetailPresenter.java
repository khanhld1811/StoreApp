package com.duykhanh.storeapp.view.productDetails;

import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.view.cart.OnCartItemClickListener;

import java.util.List;

public class ProductDetailPresenter implements ProductDetailContract.Presenter,
        ProductDetailContract.Handle.OnGetProductDetailListener,
        ProductDetailContract.Handle.OnGetCommentByIdpListener,
        ProductDetailContract.Handle.OnCreateCartItemListener,
        ProductDetailContract.Handle.OnGetCartCounterListener{
    final String TAG = this.getClass().toString();

    ProductDetailContract.Handle iHanlde;
    ProductDetailContract.View iView;

    public ProductDetailPresenter(ProductDetailContract.View iView) {
        this.iView = iView;
        iHanlde = new ProductDetailHandle(iView);
    }

    @Override
    public void requestProductFromServer(String productId) {
        if (iView != null) {
            iView.showProgress();
        }
        iHanlde.getProductDetail(this, productId);
    }

    @Override
    public void requestCommentsFromServer(String productId) {
        iHanlde.getCommentByIdp(this, productId);
    }

    @Override
    public void requestCartCounter() {
        iHanlde.getCartCounter(this);
    }


    @Override
    public void addCartItem(CartItem cartItem) {
        iHanlde.createCartItem(this, cartItem);
    }

    @Override
    public void onGetProductDetailFinished(Product product) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.setDataToView(product);

    }

    @Override
    public void onGetCommentByIdpFinished(List<Comment> comments) {
        iView.setCommentsToRecyclerView(comments);
    }

    @Override
    public void onCreateCartItemFinished() {
        iHanlde.getCartCounter(this);
    }

    @Override
    public void onGetCartCounterFinished(int sumQuantity) {
        iView.setCartItemCounter(sumQuantity);
    }

    @Override
    public void onGetProductDetailFailure(Throwable throwable) {
        iView.onResponseFailure(throwable);
        if (iView != null) {
            iView.hideProgress();
        }
    }

    @Override
    public void onGetCommentByIdpFailure(Throwable throwable) {
        iView.onCommentsResponseFailure(throwable);
    }

    @Override
    public void onCreateCartItemFailure(Throwable throwable) {
        iView.onCartItemCountResponseFailure(throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
