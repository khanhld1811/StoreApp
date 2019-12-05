package com.duykhanh.storeapp.presenter.comment;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Duy Khánh on 12/2/2019.
 */
public interface CommentContract {

    interface Handle {
        interface OnFinishedListener {
            void onFinished();

            void onFailed();

            void onFailure(Throwable t);
        }

        void onPostCommentProduct(OnFinishedListener finishedListener, List<MultipartBody.Part> parts, Map<String, RequestBody> map);
    }

    interface Presenter{
        void requestDataFormServer(List<MultipartBody.Part> parts,Map<String, RequestBody> map);
    }

    interface View{
        void onFinished();

        void onFailed();

        void onFailure(Throwable t);
    }
}
