package com.duykhanh.storeapp.view.userpage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duykhanh.storeapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHandle implements UserContract.Handle {
    final String TAG = this.getClass().toString();
    FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;

    public UserHandle(UserContract.View iView) {
        sharedPreferences = iView.getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public void getUserId(OnGetUserIdListener listener) {
        try {
            String userId = sharedPreferences.getString("UserId", "");
            listener.onGetUserIdFinished(userId);
        } catch (Exception e) {
            listener.onGetUserIdFailure(e);
        }
    }

    @Override
    public void getUserInfo(OnGetUserInfoListener listener, String userId) {
        Log.d(TAG, "getUserInfo: " + userId);
        try {
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    listener.onGetUserInfoFinished(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            listener.onGetUserInfoFailure(e);
        }
    }

    @Override
    public void logOut(OnLogOutListener listener) {
        try {
            firebaseAuth.signOut();
            listener.onLogoutFinished();
        } catch (Exception e) {
            listener.onLogOutFailure(e);
        }
    }

    @Override
    public void removeUserId(OnRemoveUserIdListener listener) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("UserId").apply();
            listener.onRemoveUserIdFinished();
        } catch (Exception e) {
            listener.onRemoveUserIdFailure(e);
        }
    }
}
