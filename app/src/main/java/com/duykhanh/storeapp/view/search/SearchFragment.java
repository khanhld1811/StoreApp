package com.duykhanh.storeapp.view.search;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.model.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View {
    final String TAG = this.getClass().toString();

    View view;
    Toolbar toolbar;
    EditText etSearch;
    RecyclerView rvSearchedProducts;

    SearchPresenter presenter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initView();
        initComponent();
        settingToolbar();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.requestSearch(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void requestSearchComplete(List<Product> products) {
        for (Product product : products) {
            Log.d(TAG, "requestSearchComplete: " + product.toString());
        }
    }

    @Override
    public void requestSearchFailure(Throwable throwable) {
        Toast.makeText(getContext(), "Có lỗi!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "requestSearchFailure: ", throwable);
    }

    private void settingToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initComponent() {
        presenter = new SearchPresenter(this);
    }

    private void initView() {
        toolbar = view.findViewById(R.id.tbSearching);
        etSearch = view.findViewById(R.id.etSearch);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
