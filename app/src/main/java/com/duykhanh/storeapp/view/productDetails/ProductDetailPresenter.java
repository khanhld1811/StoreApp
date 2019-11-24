package com.duykhanh.storeapp.view.productDetails;

import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;

import java.util.List;

public class ProductDetailPresenter implements ProductDetailContract.Presenter,
        ProductDetailContract.Handle.OnGetProductDetailListener, ProductDetailContract.Handle.OnGetCommentByIdpListener {

    ProductDetailContract.Handle iHanlde;
    ProductDetailContract.View iView;

    public ProductDetailPresenter(ProductDetailContract.View iView) {
        this.iView = iView;
        iHanlde = new ProductDetailHandle();
    }

    @Override
    public void requestDataFromServer(String productId) {
        if (iView != null) {
            iView.showProgress();
        }
        iHanlde.getProductDetail(this, productId);
    }

    @Override
    public void requestCommentDataFromServer(String productId) {
        iHanlde.getCommentByIdp(this,productId);
    }

    @Override
    public void requestCartDataFromDatabase(Product products) {

    }

    @Override
    public void onGetProductDetailFinished(Product product) {
        if (iView != null) {
            iView.hideProgress();
        }
        iView.setDataToView(product);
    }

    @Override
    public void onGetProductDetailFailure(Throwable throwable) {
        iView.onResponseFailure(throwable);
        if (iView != null) {
            iView.hideProgress();
        }
    }

    @Override
    public void onGetCommentByIdpFinished(List<Comment> comments) {
        iView.setCommentsToRecyclerView(comments);
    }

    @Override
    public void onGetCommentByIdpFailure(Throwable throwable) {
        iView.onCommentsResponseFailure(throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
