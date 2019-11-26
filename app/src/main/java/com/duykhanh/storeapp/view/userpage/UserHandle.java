package com.duykhanh.storeapp.view.userpage;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHandle implements UserContract.Handle {
    final String TAG = this.getClass().toString();
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public UserHandle (){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void getCurrentUser(OnGetCurrentUserListener listener) {
        Log.d(TAG, "getCurrentUser: ");
        try {
            firebaseUser = firebaseAuth.getCurrentUser();
            Log.d(TAG, "getCurrentUser: " + firebaseUser);
            if (firebaseUser != null) {
                listener.onGetCurrentUserFinished(firebaseUser);
            } else {
                listener.onGetCurrentUserFinished(null);
            }
        } catch (Exception e) {
            listener.onGetCurrentUserFailure(e);
        }
    }

    @Override
    public void logOut(OnLogOutListener listener) {
        try {
            firebaseAuth.signOut();
            listener.onLogoutFinished();
        }
        catch (Exception e){
            listener.onLogOutFailure(e);
        }
    }
}
