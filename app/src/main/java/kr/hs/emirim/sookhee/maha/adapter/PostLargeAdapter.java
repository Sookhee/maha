package kr.hs.emirim.sookhee.maha.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import kr.hs.emirim.sookhee.maha.PostActivity;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.model.postData;

public class PostLargeAdapter extends RecyclerView.Adapter<PostLargeAdapter.CustomViewHolder> {

    private Context mCtx;
    private HashMap<String, postData> mData;

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

        ArrayList<String> imgList = post.getImg();
        String mainImg = imgList.get(0);
        String profileImg = post.getWriterProfile();

        holder.tvTitle.setText(post.getTitle());
        holder.tvContent.setText(post.getContent());
        holder.tvWriter.setText(post.getWriter());
        holder.tvLikeCount.setText(post.getLikeCount() + "");
        holder.tvViewCount.setText(post.getViewCount() + "");
        holder.tvTime.setText("4분 전");
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

        int hobbyId;
        String hobbyName;

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
                    Intent intent;
                    intent = new Intent(v.getContext(), PostActivity.class);
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