package com.duykhanh.storeapp.view.categorypage;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.CategoryAdapter;
import com.duykhanh.storeapp.model.Category;
import com.duykhanh.storeapp.view.categorypage.ListProductActivity.CategoryListProductActivity;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryContract.View {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    GridView grid;
    List<Category> listCategory;
    CategoryAdapter adapter;
    private CategoryPresenter presenter;

    View view;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        // Khởi tạo giao diện cần thiết
        initUI();

        return view;
    }

    private void initUI() {
        listCategory = new ArrayList<>();
        presenter = new CategoryPresenter(this);
        grid = view.findViewById(R.id.grid_category);
        adapter = new CategoryAdapter(getContext(), listCategory);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent iCategoryProductList = new Intent(getContext(), CategoryListProductActivity.class);
                iCategoryProductList.putExtra(KEY_CATEGORY, listCategory.get(i).getId());
                iCategoryProductList.putExtra(KEY_TITLE,listCategory.get(i).getName());
                startActivity(iCategoryProductList);
            }
        });

        presenter.requestDataFromServer();
    }

    @Override
    public void senDataToGridView(List<Category> categoryList) {
        listCategory.addAll(categoryList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, "onResponseFailure: ");
    }
}
