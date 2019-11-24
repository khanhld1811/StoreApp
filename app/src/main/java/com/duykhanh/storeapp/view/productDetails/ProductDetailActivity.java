package com.duykhanh.storeapp.view.productDetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.adapter.CommentsAdapter;
import com.duykhanh.storeapp.adapter.SlideAdapter;
import com.duykhanh.storeapp.model.CartItem;
import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.utils.Formater;
import com.duykhanh.storeapp.view.cart.CartActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.duykhanh.storeapp.utils.Constants.KEY_RELEASE_TO;


public class ProductDetailActivity extends AppCompatActivity implements ProductDetailContract.View, View.OnClickListener {


    final String TAG = this.getClass().toString();
    int dotsCount;
    int sumQuanity;
    String productId;

    ProductDetailPresenter productDetailPresenter;
    List<Comment> comments;
    Product mProduct;
    CartItem cartItem;

    LinearLayoutManager mLayoutManager;
    CommentsAdapter commentsAdapter;

    List<Fragment> fragmentList;
    ImageView[] dots;
    RecyclerView rvComment;
    ViewPager vpProductImgSlide;

    LinearLayout llDots, llctnComments, llctnCommentImages;
    ProgressBar pbProductDetail;
    TextView tvProductName, tvProductPrice,
            tvProductId, tvProductMaterial, tvProductSize, tvProductWaranty,
            tvProductDescription, tvProductRating,
            tvCartCounted;
    ImageButton ibtnBack, ibtnToCart, ibtnAddToCart;
    RatingBar rbProductRating;


    Formater formater;

    //Button thêm sản phẩm vào giỏ hàng
    ImageButton btnShoppingAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //Ánh xạ UI
        initUI();
        //Khởi tạo thành phần
        initComponent();
        //Thiết lập chung cho RecyclerView hiển thị Comment (chưa đưa dữ liệu vào)
        settingCommentsRecyclerView();
        //Lấy Id của Product
        Intent intent = getIntent();
        productId = intent.getStringExtra(KEY_RELEASE_TO);
        Log.d(TAG, "onCreate: productId" + productId);

        //Sự kiệu onclick các kiểu
        ibtnBack.setOnClickListener(this);
        ibtnAddToCart.setOnClickListener(this);
        ibtnToCart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cleanData();
        productDetailPresenter.requestProductFromServer(productId);
        productDetailPresenter.requestCommentsFromServer(productId);
        productDetailPresenter.requestCartCounter();
    }

    private void cleanData() {
        comments.clear();
        mProduct = null;
        dots = null;
        dotsCount = 0;
    }

    @Override
    public void setCartItemCounter(int productQuantity) {
        sumQuanity = productQuantity;
        tvCartCounted.setVisibility(View.VISIBLE);
        tvCartCounted.setText(sumQuanity + "");
    }

    @Override
    public void setDataToView(Product product) {
        mProduct = product;
        bindData(mProduct);
    }

    @Override
    public void setCommentsToRecyclerView(List<Comment> commentss) {
        comments.clear();
        if (commentss.size() == 0) {
            Toast.makeText(this, "Không có comment", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < 2; i++) {
            comments.add(commentss.get(i));
            if (i == commentss.size() - 1) {
                break;
            }
        }
        commentsAdapter.notifyDataSetChanged();
    }

    private void bindData(Product product) {
        bindDataToSlide(product.getImg());
        bindDataToDetail(product);
    }

    private void bindDataToDetail(Product product) {

        tvProductName.setText(product.getNameproduct());
        tvProductPrice.setText(product.getPrice() + " vnđ");
        rbProductRating.setRating(product.getPoint());
        tvProductRating.setText(product.getPoint() + "/5");
        tvProductId.setText(product.getId());
        tvProductMaterial.setText(product.getMaterial());
        tvProductSize.setText(product.getSize());
        tvProductWaranty.setText(product.getWarranty());
        tvProductDescription.setText(product.getDescription());
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
        vpProductImgSlide.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        Set chấm tròn dưới slide
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];
        llDots.removeAllViews();
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_grey));
//            set kích thước của chấm tròn
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,
                    20);
            params.setMargins(8, 0, 8, 0);
            llDots.addView(dots[i], params);
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_white));
        }

        vpProductImgSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void settingCommentsRecyclerView() {
        mLayoutManager = new LinearLayoutManager(ProductDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(mLayoutManager);
        rvComment.setItemAnimator(new DefaultItemAnimator());
        rvComment.setAdapter(commentsAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtnBack:
                super.onBackPressed();
                break;
            case R.id.imgbtnShoppingAdd:
                Log.w(TAG, "onClick: " + mProduct.getImg().get(0).toString() );
                Glide.with(this)
                        .asBitmap()
                        .load(formater.formatImageLink(mProduct.getImg().get(0)))
                        .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d(TAG, "onResourceReady: ");
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                        byte[] imgCart = byteArrayOutputStream.toByteArray();
                        cartItem = new CartItem(mProduct.getId(), mProduct.getNameproduct(), mProduct.getPrice(),
                                mProduct.getQuantity(), mProduct.getQuantity(), imgCart);
                        productDetailPresenter.addCartItem(cartItem);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

                break;
            case R.id.imgbtnShopping:
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                break;
        }
    }

    @Override
    public void onCommentsResponseFailure(Throwable throwable) {
        Log.e(TAG, "onCommentsResponseFailure: ", throwable);
        Toast.makeText(this, "Lỗi khi hiển thị Bình Luận", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartItemCountResponseFailure(Throwable throwable) {
        Log.e(TAG, "onCartItemCountResponseFailure: ", throwable);
        Toast.makeText(this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, "onResponseFailure: ", throwable);
        Toast.makeText(this, "Có lỗi khi tải dữ liệu\nVui lòng thử lại sau...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        pbProductDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProductDetail.setVisibility(View.GONE);
    }

    private void initComponent() {
        productDetailPresenter = new ProductDetailPresenter(this);
        comments = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(ProductDetailActivity.this, comments, R.layout.item_comments);
        formater = new Formater();
    }


    private void initUI() {
        llDots = findViewById(R.id.layoutDots);
        vpProductImgSlide = findViewById(R.id.viewpagerSlider);
        pbProductDetail = findViewById(R.id.pbProductDetail);
        rbProductRating = findViewById(R.id.ratingbarPointProductDetail);
        rvComment = findViewById(R.id.rvComments);

        llctnComments = findViewById(R.id.llctnComments);
        llctnCommentImages = findViewById(R.id.llctnCommentImages);

        ibtnBack = findViewById(R.id.imgbtnBack);
        ibtnAddToCart = findViewById(R.id.imgbtnShoppingAdd);
        ibtnToCart = findViewById(R.id.imgbtnShopping);
        tvProductName = findViewById(R.id.txtNameProductDetail);
        tvProductPrice = findViewById(R.id.txtPriceProductDetail);
        tvProductId = findViewById(R.id.txtIdProductDetail);
        tvProductMaterial = findViewById(R.id.txtMaterialProductDetail);
        tvProductSize = findViewById(R.id.txtSizeProductDetail);
        tvProductWaranty = findViewById(R.id.txtWarrantyProductDetail);
        tvProductDescription = findViewById(R.id.txtDesProductDetail);
        tvProductRating = findViewById(R.id.txtPointProductDetail);
        tvCartCounted = findViewById(R.id.txtSizeShopping);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productDetailPresenter.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imgbtnShoppingAdd:
                productDetailPresenter.requestCartDataFromDatabase(mProduct);
                break;
        }
    }
}
