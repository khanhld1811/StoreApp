package com.duykhanh.storeapp.view.userpage.account.login;

public interface LoginContract {

    interface Handle {
        void loggingIn(OnLoggingInListener listener, String email, String password);

        void getTokenId(OnGetTokenIdListener listener, String userId);

        void putTokenIdToUser(OnPutTokenIdToUserListener listener, String userId, String tokenId);

        interface OnLoggingInListener {
            void onLoggingInFinished(String userId);

            void onLoggingInFailure(Throwable throwable);
        }

        interface OnGetTokenIdListener {
            void onGetTokenIdFinished(String userId, String tokenId);

            void onGetTokenIdFailure(Throwable throwable);
        }

        interface OnPutTokenIdToUserListener {
            void onPutTokenIdToUserFinished();

            void onPutTokenIdToUserFailure(Throwable throwable);
        }

    }

    interface View {
        void requestLogInFinished();

        void requestLogInFailure(Throwable throwable);

        void showProgress();

        void hideProgress();
    }


    interface Presenter {
        void requestLogIn(String email, String password);

        void onDestroyed();
    }
}
