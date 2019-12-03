package com.duykhanh.storeapp.presenter.comment;

import com.duykhanh.storeapp.model.Product;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

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
