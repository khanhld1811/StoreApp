package com.duykhanh.storeapp.view.categorypage;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

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
public class CategoryFragment extends Fragment implements CategoryContract.View,
        View.OnClickListener {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    ImageButton btn_start_to_cart;
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

        registerListener();

        return view;
    }

    private void initUI() {
        btn_start_to_cart = view.findViewById(R.id.imgbtnSizeShop);

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
                iCategoryProductList.putExtra(KEY_TITLE, listCategory.get(i).getName());
                startActivityForResult(iCategoryProductList,KEY_START_CATEGORY_PRODUCT);
            }
        });

        presenter.requestDataFromServer();
    }

    private void registerListener() {
        btn_start_to_cart.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgbtnSizeShop:
                Fragment navhost = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController c = NavHostFragment.findNavController(navhost);
                c.navigate(R.id.navCart);
                break;
        }
    }
}
