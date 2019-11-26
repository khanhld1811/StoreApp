package com.duykhanh.storeapp.view.userpage.account.login;

public class LoginPresenter implements LoginContract.Presenter,
        LoginContract.Handle.OnLoggingInListener,
        LoginContract.Handle.OnGetTokenIdListener,
        LoginContract.Handle.OnPutTokenIdToUserListener {

    LoginContract.View iView;
    LoginContract.Handle iHandle;

    public LoginPresenter(LoginContract.View iView) {
        this.iView = iView;
        iHandle = new LoginHandle();
    }

    //    Gửi yêu cầu đăng nhập
    @Override
    public void requestLogIn(String email, String password) {
        if (iView != null) {
            iView.showProgress();
        }
        iHandle.loggingIn(this, email, password);
    }

    @Override
    public void onLoggingInFinished(String userId) {
//        Lấy Token
        iHandle.getTokenId(this, userId);
    }

    @Override
    public void onGetTokenIdFinished(String userId, String tokenId) {
//        Cập nhật Token cho User
        iHandle.putTokenIdToUser(this, userId, tokenId);
    }

    @Override
    public void onPutTokenIdToUserFinished() {
        if (iView != null) {
            iView.hideProgress();
        }
//        Kết thúc!
        iView.requestLogInFinished();
    }

    @Override
    public void onLoggingInFailure(Throwable throwable) {
        iView.requestLogInFailure(throwable);
    }

    @Override
    public void onGetTokenIdFailure(Throwable throwable) {
        iView.requestLogInFailure(throwable);
    }

    @Override
    public void onPutTokenIdToUserFailure(Throwable throwable) {
//        Kết thúc ẩn Progress Bar
        if (iView != null) {
            iView.hideProgress();
        }
    }

    @Override
    public void onDestroyed() {
        iView = null;
    }
}
