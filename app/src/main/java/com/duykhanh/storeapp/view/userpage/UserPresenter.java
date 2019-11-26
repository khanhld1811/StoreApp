package com.duykhanh.storeapp.view.userpage;

import com.google.firebase.auth.FirebaseUser;

public class UserPresenter implements UserContract.Presenter, UserContract.Handle.OnGetCurrentUserListener, UserContract.Handle.OnLogOutListener {

    UserContract.Handle iHandle;
    UserContract.View iView;

    public UserPresenter(UserContract.View iView) {
        this.iView = iView;
        iHandle = new UserHandle();
    }

    //    Gửi yêu cầu lấy người dùng hiện tại
    @Override
    public void requestGetCurrentUser() {
        iHandle.getCurrentUser(this);
    }


    //    Lắng nghe sự kiện lấy người dùng hiện tại
    @Override
    public void onGetCurrentUserFinished(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            iView.hideLoginRequire();
        } else {
            iView.showLoginRequire();
        }
    }

    @Override
    public void onGetCurrentUserFailure(Throwable throwable) {
        iView.requestCurrentUserFailure(throwable);
    }

    //    Gửi yêu cầu Đăng Xuất
    @Override
    public void requestLogOut() {
        iHandle.logOut(this);
    }

    //    Lắng nghe sự kiện Đăng Xuất
    @Override
    public void onLogoutFinished() {
        iView.requestLogOutSuccess();
    }

    @Override
    public void onLogOutFailure(Throwable throwable) {
        iView.requestLogOutFailure(throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
