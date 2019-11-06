package com.duykhanh.storeapp.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.view.homepage.HomeFragment;

import java.util.List;

/**
 * Created by Duy Khánh on 11/5/2019.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable,ProductAdapterListener {

    private HomeFragment context;
    private List<Product> productList;
    private List<Product> originalProductList;

    private String count = null;

    public ProductAdapter(HomeFragment context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.originalProductList = productList;
        HomeFragment.setOnProductAdapterListener(this);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txtNameProduct.setText(product.getNameproduct());
        holder.txtPriceProduct.setText(product.getPrice() + "đ");
        holder.ratingbarPointProduct.setRating(product.getPoint());

        Log.d("image", product.getImg().get(0));

        Glide.with(context)
                .load("https://previews.123rf.com/images/artshock/artshock1209/artshock120900045/15221647-imag-of-heart-in-the-blue-sky-against-a-background-of-white-clouds-.jpg")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d("Glide", "onLoadFailed: " + e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).apply(new RequestOptions().placeholder(R.drawable.noimage).error(R.drawable.noimage))
                .into(holder.imgProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onMovieItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("Count", "getItemCount: " + count);

            return productList.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Movie> filteredResults = null;
                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (List<Product>) filterResults.values;
                ProductAdapter.this.notifyDataSetChanged();
            }
        };
    }

    @Override
    public void loadMoreProduct(int count) {
        this.count = String.valueOf(count);
        Log.d("Count", "loadMoreProduct: " + count);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProduct, txtPriceProduct;
        RatingBar ratingbarPointProduct;
        ImageView imgProduct;
        RecyclerView recyclerProducts;
        CardView cardviewContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);
            ratingbarPointProduct = itemView.findViewById(R.id.ratingbarPointProduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            recyclerProducts = itemView.findViewById(R.id.recyclerProducts);
            cardviewContainer = itemView.findViewById(R.id.cardviewContainer);
        }
    }
}
