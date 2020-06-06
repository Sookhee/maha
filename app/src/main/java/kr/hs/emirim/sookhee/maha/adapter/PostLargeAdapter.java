package kr.hs.emirim.sookhee.maha.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import kr.hs.emirim.sookhee.maha.PostActivity;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.model.postData;

public class PostLargeAdapter extends RecyclerView.Adapter<PostLargeAdapter.CustomViewHolder> {

    Intent intent;
    private Context mCtx;
    private HashMap<String, postData> mData;

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public static String formatTimeString(long regTime) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            msg = "방금 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }

    public PostLargeAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.post_large_item, parent, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        postData post = (postData) mData.values().toArray()[position];

        holder.postId = post.getPostId();

        ArrayList<String> imgList = post.getImg();
        String mainImg = imgList.get(0);
        String profileImg = post.getWriterProfile();

        holder.tvTitle.setText(post.getTitle());
        holder.tvContent.setText(post.getContent());
        holder.tvWriter.setText(post.getWriter());
        holder.tvLikeCount.setText(post.getLikeCount() + "");
        holder.tvViewCount.setText(post.getViewCount() + "");
        holder.tvTime.setText(formatTimeString(post.getTime()));
        Picasso.get().load(mainImg).into(holder.ivMainImg);
        Picasso.get().load(profileImg).into(holder.ivWriterProfile);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        View pView;
        TextView tvTitle;
        TextView tvContent;
        TextView tvWriter;
        TextView tvLikeCount;
        TextView tvViewCount;
        TextView tvTime;
        ImageView ivMainImg;
        ImageView ivWriterProfile;

        int postId;

        public CustomViewHolder(View itemView) {
            super(itemView);

            pView = itemView;
            tvTitle = (TextView) pView.findViewById(R.id.postTitle);
            tvContent = (TextView)pView.findViewById(R.id.postContent);
            tvWriter = (TextView) pView.findViewById(R.id.postWriter);
            tvLikeCount = (TextView) pView.findViewById(R.id.postLikeCount);
            tvViewCount = (TextView) pView.findViewById(R.id.postViewCount);
            tvTime = (TextView)pView.findViewById(R.id.postTime);
            ivMainImg = (ImageView) pView.findViewById(R.id.postMainImage);
            ivWriterProfile = (ImageView)pView.findViewById(R.id.postWriterProfile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(v.getContext(), PostActivity.class);
                    intent.putExtra("postId", postId);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    public void addDataAndUpdate(String key, postData p) {
        mData.put(key, p);
        notifyDataSetChanged();
    }

    public void deleteDataAndUpdate(String key) {
        mData.remove(key);
        notifyDataSetChanged();
    }
}