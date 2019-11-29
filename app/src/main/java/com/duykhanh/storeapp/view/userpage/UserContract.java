package com.duykhanh.storeapp.view.userpage;

import android.content.Context;

import com.duykhanh.storeapp.model.User;

public interface UserContract {

    interface Handle {
        //Lấy người dùng hiện tại
        void getUserId(OnGetUserIdListener listener);

        void getUserInfo(OnGetUserInfoListener listener, String userId);

        void logOut(OnLogOutListener listener);

        void removeUserId(OnRemoveUserIdListener listener);

        interface OnGetUserIdListener {
            void onGetUserIdFinished(String userId);

            void onGetUserIdFailure(Throwable throwable);
        }

        interface OnGetUserInfoListener {
            void onGetUserInfoFinished(User user);

            void onGetUserInfoFailure(Throwable throwable);
        }

        interface OnLogOutListener {
            void onLogoutFinished();

            void onLogOutFailure(Throwable throwable);
        }

        interface OnRemoveUserIdListener {
            void onRemoveUserIdFinished();

            void onRemoveUserIdFailure(Throwable throwable);
        }
    }

    interface View {
        void requestUserIdSuccess(User user);

        void requestUserIdFailure(Throwable throwable);

        void requestLogOutSuccess();

        void requestLogOutFailure(Throwable throwable);

        Context getContext();

        void showLoginRequire();

        void hideLoginRequire();
    }

    interface Presenter {
        void requestGetUserId();

        void requestLogOut();

        void onDestroy();
    }
}
