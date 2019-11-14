package com.duykhanh.storeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.storeapp.R;
import com.duykhanh.storeapp.model.Comment;
import com.duykhanh.storeapp.utils.Formater;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    final String TAG = this.getClass().toString();

    Formater formater;

    Context context;
    List<Comment> comments;
    int resource;

    public CommentsAdapter(Context context, List<Comment> comments, int resource) {
        this.context = context;
        this.comments = comments;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        formater = new Formater();

        holder.tvCommentTitle.setText(comment.getTitle());
        holder.tvCommentContent.setText(comment.getContent());
        holder.tvCommentUser.setText(comment.getIdu());
        holder.tvCommentConfirm.setText("Đã mua hàng");
        holder.tvCommentDate.setText(formater.formatDate(comment.getDate()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommentTitle, tvCommentContent, tvCommentUser, tvCommentConfirm, tvCommentDate;
        RatingBar rbCommentRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCommentTitle = itemView.findViewById(R.id.tvCommentTitle);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
            tvCommentUser = itemView.findViewById(R.id.tvCommentUser);
            tvCommentConfirm = itemView.findViewById(R.id.tvCommentConfirm);
            tvCommentDate = itemView.findViewById(R.id.tvCommentDate);
            rbCommentRate = itemView.findViewById(R.id.rbCommentRate);

        }
    }
}
