package kr.hs.emirim.sookhee.maha.adapter;

import android.content.Context;
import android.content.Intent;
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

public class PostSmallAdapter extends RecyclerView.Adapter<PostSmallAdapter.CustomViewHolder> {

    private Context mCtx;
    private HashMap<String, postData> mData;

    public PostSmallAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.post_small_item, parent, false);


        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        postData post = (postData) mData.values().toArray()[position];

        holder.postId = post.getPostId();

        ArrayList<String> imgList = post.getImg();
        String img = imgList.get(0);

        holder.tvTitle.setText(post.getTitle());
        holder.tvHobby.setText(post.getHobbyName());
        holder.tvWriter.setText(post.getWriter());
        holder.tvLikeCount.setText(post.getLikeCount() + "");
        holder.tvViewCount.setText(post.getViewCount() + "");
        Picasso.get().load(img).into(holder.ivMainImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        View pView;
        TextView tvTitle;
        TextView tvHobby;
        TextView tvWriter;
        TextView tvLikeCount;
        TextView tvViewCount;
        ImageView ivMainImg;

        int postId;

        public CustomViewHolder(View itemView) {
            super(itemView);

            pView = itemView;
            tvTitle = (TextView)pView.findViewById(R.id.postTitle);
            tvHobby = (TextView)pView.findViewById(R.id.postHobbyName) ;
            tvWriter = (TextView)pView.findViewById(R.id.postWriter);
            tvLikeCount = (TextView)pView.findViewById(R.id.postLikeCount);
            tvViewCount = (TextView)pView.findViewById(R.id.postViewCount);
            ivMainImg = (ImageView)pView.findViewById(R.id.postMainImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PostActivity.class);
                    intent.putExtra("postId", postId);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    public void addDataAndUpdate(String key, postData p){
        mData.put(key, p);
        notifyDataSetChanged();
    }

    public void deleteDataAndUpdate(String key){
        mData.remove(key);
        notifyDataSetChanged();
    }
}