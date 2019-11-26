package com.duykhanh.storeapp.view.userpage;

import com.google.firebase.auth.FirebaseUser;

public interface UserContract {

    interface Handle {
        //Lấy người dùng hiện tại
        void getCurrentUser(OnGetCurrentUserListener listener);

        void logOut(OnLogOutListener listener);

        interface OnGetCurrentUserListener {
            void onGetCurrentUserFinished(FirebaseUser firebaseUser);

            void onGetCurrentUserFailure(Throwable throwable);
        }

        interface OnLogOutListener {
            void onLogoutFinished();

            void onLogOutFailure(Throwable throwable);
        }
    }

    interface View {
        void requestCurrentUserFailure(Throwable throwable);

        void requestLogOutSuccess();

        void requestLogOutFailure(Throwable throwable);

        void showLoginRequire();

        void hideLoginRequire();
    }

    interface Presenter {
        void requestGetCurrentUser();

        void requestLogOut();

        void onDestroy();
    }
}
