package com.duykhanh.storeapp.view.userpage;

import com.duykhanh.storeapp.model.User;

public class UserIdPresenter implements UserContract.Presenter,
        UserContract.Handle.OnGetUserIdListener,
        UserContract.Handle.OnLogOutListener,
        UserContract.Handle.OnRemoveUserIdListener,
        UserContract.Handle.OnGetUserInfoListener {

    UserContract.Handle iHandle;
    UserContract.View iView;

    public UserIdPresenter(UserContract.View iView) {
        this.iView = iView;
        iHandle = new UserHandle(iView);
    }

    //    Gửi yêu cầu lấy người dùng hiện tại
    @Override
    public void requestGetUserId() {
        iHandle.getUserId(this);
    }


    //    Lắng nghe sự kiện lấy người dùng hiện tại
    @Override
    public void onGetUserIdFinished(String userId) {
        if (!userId.equals("")) {
            iView.hideLoginRequire();
            iHandle.getUserInfo(this, userId);
        } else {
            iView.showLoginRequire();
        }
    }

    @Override
    public void onGetUserInfoFinished(User user) {
        iView.requestUserIdSuccess(user);
    }

    @Override
    public void onGetUserIdFailure(Throwable throwable) {
        iView.requestUserIdFailure(throwable);
    }

    @Override
    public void onGetUserInfoFailure(Throwable throwable) {
        iView.requestLogOutFailure(throwable);
    }
    //    Gửi yêu cầu Đăng Xuất

    @Override
    public void requestLogOut() {
        iHandle.logOut(this);
    }
    //    Lắng nghe sự kiện Đăng Xuất

    @Override
    public void onLogoutFinished() {
        iHandle.removeUserId(this);
    }

    @Override
    public void onRemoveUserIdFinished() {
        iView.requestLogOutSuccess();
    }

    @Override
    public void onLogOutFailure(Throwable throwable) {
        iView.requestLogOutFailure(throwable);
    }

    @Override
    public void onRemoveUserIdFailure(Throwable throwable) {
        iView.requestLogOutFailure(throwable);
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
