package com.duykhanh.storeapp.view.productDetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.SlideAdapter;
import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;

import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.KEY_RELEASE_TO;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailContract.View {
    final String TAG = this.getClass().toString();
    int dotsCount;

    ProductDetailPresenter productDetailPresenter;
    Product mProduct;

    List<Fragment> fragmentList;
    ImageView[] dots;
    RecyclerView rvComment;
    ViewPager viewpagerSlider;

    LinearLayout layoutDots;
    ProgressBar pbProductDetail;
    TextView txtNameProductDetail, txtPriceProductDetail,
    txtIdProductDetail, txtMaterialProductDetail, txtSizeProductDetail, txtWarrantyProductDetail,
    txtDesProductDetail, txtPointProductDetail, txtSizeShopping;
    RatingBar ratingbarPointProductDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();

        Intent intent = getIntent();
        String productId = intent.getStringExtra(KEY_RELEASE_TO);

        productDetailPresenter = new ProductDetailPresenter(this);
        productDetailPresenter.requestDataFromServer(productId);
        productDetailPresenter.requestCommentDataFromServer(productId);
    }

    @Override
    public void setDataToView(Product product) {
        mProduct = product;
        bindData(mProduct);
    }

    @Override
    public void setCommentsToRecyclerView(List<Comment> comments) {

        Log.d(TAG, "setCommentsToRecyclerView: isruning");
        for (Comment comment : comments){
            Log.d(TAG, "setCommentsToRecyclerView: " + comment.toString());
        }

    }

    @Override
    public void onCommentsResponseFailure(Throwable throwable) {
        Log.e(TAG, "onCommentsResponseFailure: ", throwable);
        Toast.makeText(this, "Lỗi khi hiển thị Bình Luận", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, "onResponseFailure: ", throwable);
        Toast.makeText(this, "Có lỗi khi tải dữ liệu\nVui lòng thử lại sau...", Toast.LENGTH_SHORT).show();
    }

    private void bindData(Product product) {
        bindDataToSlide(product.getImg());
        bindDataToDetail(product);
    }

    private void bindDataToDetail(Product product) {
        txtNameProductDetail.setText(product.getNameproduct());
        txtPriceProductDetail.setText(product.getPrice() + " vnđ");
        ratingbarPointProductDetail.setRating(product.getPoint());
        txtPointProductDetail.setText(product.getPoint() + "/5");
        txtIdProductDetail.setText(product.getId());
        txtMaterialProductDetail.setText(product.getMaterial());
        txtSizeProductDetail.setText(product.getSize());
        txtWarrantyProductDetail.setText(product.getWarranty());
        txtDesProductDetail.setText(product.getDescription());
    }

    private void bindDataToSlide(List<String> linkImg) {
//        Thiết lập hình
        fragmentList = new ArrayList<>();
        for (int i = 0; i < linkImg.size(); i++) {
            ProductDetailSlideFragment productDetailSlideFragment = new ProductDetailSlideFragment();
            Bundle bundle = new Bundle();
            bundle.putString("link", linkImg.get(i));
            productDetailSlideFragment.setArguments(bundle);
            fragmentList.add(productDetailSlideFragment);
        }
        SlideAdapter adapter = new SlideAdapter(getSupportFragmentManager(), fragmentList);
        viewpagerSlider.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        Set chấm tròn dưới slide
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_grey));
//            set kích thước của chấm tròn
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,
                    20);
            params.setMargins(8, 0, 8, 0);
            layoutDots.addView(dots[i], params);
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white));
        }

        viewpagerSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_grey));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void showProgress() {
        pbProductDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProductDetail.setVisibility(View.GONE);
    }

    private void init() {
        rvComment = findViewById(R.id.rvComment);
        layoutDots = findViewById(R.id.layoutDots);
        viewpagerSlider = findViewById(R.id.viewpagerSlider);
        pbProductDetail = findViewById(R.id.pbProductDetail);
        ratingbarPointProductDetail = findViewById(R.id.ratingbarPointProductDetail);

        txtNameProductDetail = findViewById(R.id.txtNameProductDetail);
        txtPriceProductDetail = findViewById(R.id.txtPriceProductDetail);
        txtIdProductDetail = findViewById(R.id.txtIdProductDetail);
        txtMaterialProductDetail = findViewById(R.id.txtMaterialProductDetail);
        txtSizeProductDetail = findViewById(R.id.txtSizeProductDetail);
        txtWarrantyProductDetail = findViewById(R.id.txtWarrantyProductDetail);
        txtDesProductDetail = findViewById(R.id.txtDesProductDetail);
        txtPointProductDetail = findViewById(R.id.txtPointProductDetail);
        txtSizeShopping = findViewById(R.id.txtSizeShopping);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productDetailPresenter.onDestroy();
    }
}
