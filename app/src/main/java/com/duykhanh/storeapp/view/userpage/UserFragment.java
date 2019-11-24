package com.duykhanh.storeapp.view.userpage;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duykhanh.storeapp.R;
import com.scwang.smartrefresh.layout.util.SmartUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    private static final String TAG = UserFragment.class.getSimpleName();
    private int mOffset = 0;
    private int mScrollY = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        final Toolbar toolbar = root.findViewById(R.id.toolbar);

        // view image background
        final View parallax = root.findViewById(R.id.parallax);

        final View buttonBar = root.findViewById(R.id.buttonBarLayout);
        final NestedScrollView scrollView = root.findViewById(R.id.scrollView);

        //scrollView
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = SmartUtil.dp2px(170);
            //color toolbar
            private int color = ContextCompat.getColor(getActivity().getApplicationContext(),R.color.colorIcon) & 0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBar.setAlpha(1f * mScrollY / h);
                    // set background when show toolbar
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    //scroll image
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });

        buttonBar.setAlpha(0);
        toolbar.setBackgroundColor(0);

        return root;
    }

}
