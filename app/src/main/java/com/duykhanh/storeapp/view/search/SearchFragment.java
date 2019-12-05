package com.duykhanh.storeapp.view.search;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.searproduct.ProductSearchedAdapter;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.presenter.search.SearchContract;
import com.duykhanh.storeapp.presenter.search.SearchPresenter;
import com.duykhanh.storeapp.view.productDetails.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.KEY_RELEASE_TO;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View, View.OnClickListener, SearchedItemClickListener {
    final String TAG = this.getClass().toString();

    List<Product> products;

    View view;
    Toolbar toolbar;
    EditText etSearch;
    RecyclerView rvSearchedProducts;
    ImageView ivSearch;

    SearchPresenter presenter;
    ProductSearchedAdapter adapter;
    LinearLayoutManager layoutManager;

    int firstVisibleItem, visibleItemCount, totalItemCount;
    String searchKey = "";
    private int pageNo = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;

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
        settingRecyclerView();
        //Load more
        setRvScrollListener();
        presenter.requestShowSearchKeys();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchKey = v.getText().toString();
                    presenter.requestSearch(searchKey);
                    presenter.requestSaveKey(searchKey);
                    return true;
                }
                return false;
            }
        });
        ivSearch.setOnClickListener(this);

        return view;
    }

    @Override
    public void requestSearchComplete(List<Product> productss) {
        products.addAll(productss);
        adapter.notifyDataSetChanged();
        pageNo++;
    }

    private void setRvScrollListener() {
        rvSearchedProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvSearchedProducts.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                // Handling the infinite scroll
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    Log.d(TAG, "onScrolled: p" + pageNo);
                    presenter.requestMoreData(searchKey, pageNo);
                    loading = true;
                }
            }
        });
    }

    @Override
    public void showSearchKeys(List<String> searchKeys) {
        Toast.makeText(getContext(), "List search key ey ey", Toast.LENGTH_SHORT).show();
        for (String searchKey : searchKeys) {
            Log.d(TAG, "showSearchKeys: " + searchKey);
        }
    }

    @Override
    public void requestSearchFailure(Throwable throwable) {
        Toast.makeText(getContext(), "Có lỗi!", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "requestSearchFailure: ", throwable);
    }

    @Override
    public void showSuggestWords() {

    }

    @Override
    public void hideSuggestWords() {

    }

    @Override
    public void onSearchedItemClick(int position) {
        Intent detailIntent = new Intent(getContext(), ProductDetailActivity.class);
        detailIntent.putExtra(KEY_RELEASE_TO, products.get(position).getId());
        startActivity(detailIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSearch:
                pageNo = 1;
                previousTotal = 0;
                loading = true;
                visibleThreshold = 5;
                products.clear();
                searchKey = etSearch.getText().toString();
                presenter.requestSearch(searchKey);
                break;
        }
    }

    private void settingRecyclerView() {
        rvSearchedProducts.setLayoutManager(layoutManager);
        rvSearchedProducts.setAdapter(adapter);
    }

    private void settingToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initComponent() {
        products = new ArrayList<>();
        adapter = new ProductSearchedAdapter(SearchFragment.this, products, R.layout.item_product_searched);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        presenter = new SearchPresenter(this);
    }

    private void initView() {
        toolbar = view.findViewById(R.id.tbSearching);
        etSearch = view.findViewById(R.id.etSearch);
        rvSearchedProducts = view.findViewById(R.id.rvSearchedProduct);
        ivSearch = view.findViewById(R.id.ivSearch);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
