package com.duykhanh.storeapp.adapter.viewproduct;

import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.duykhanh.storeapp.R;

import com.duykhanh.storeapp.model.Product;
import com.duykhanh.storeapp.utils.Formater;
import com.duykhanh.storeapp.view.homepage.HomeFragment;

import java.util.List;

/**
 * Created by Duy Khánh on 11/27/2019.
 */
public class ViewProductAdapter extends RecyclerView.Adapter<ViewProductAdapter.ViewHolder> implements Filterable {

    private HomeFragment context;
    private List<Product> productList;
    //Format string
    Formater formater;

    public ViewProductAdapter(HomeFragment context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        formater = new Formater();
    }

    @NonNull
    @Override
    public ViewProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_product, parent, false);
        return new ViewProductAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        String nameProduct = formater.formatNameProduct(product.getNameproduct());
        holder.txtNameProduct.setText(nameProduct);
        holder.txtPriceProduct.setText(product.getPrice() + "đ");
//        holder.ratingbarPointProduct.setRating(product.getPoint());
        String url = null;
        try {
            url = formater.formatImageLink(product.getImg().get(0));
        } catch (Exception e) {
            Log.d("Error", "onBindViewHolder: " + e);
        }

        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.pb_load_image.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.pb_load_image.setVisibility(View.GONE);
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
        return productList.size() - (productList.size()/2);
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
                ViewProductAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProduct, txtPriceProduct;
        //        RatingBar ratingbarPointProduct;
        ImageView imgProduct;
        RecyclerView recyclerProducts;
        ConstraintLayout ctl_view_product;

//        ProgressBar pb_load_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameProduct = itemView.findViewById(R.id.txtNameViewProduct);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceViewProduct);
//            ratingbarPointProduct = itemView.findViewById(R.id.ratingbarPointProduct);
            imgProduct = itemView.findViewById(R.id.img_view_product);
            ctl_view_product = itemView.findViewById(R.id.ctl_view_product);
//            pb_load_image = itemView.findViewById(R.id.pb_load_image);
        }
    }
}
