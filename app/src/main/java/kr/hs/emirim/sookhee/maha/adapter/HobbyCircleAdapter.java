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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import kr.hs.emirim.sookhee.maha.HobbyActivity;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class HobbyCircleAdapter extends RecyclerView.Adapter<HobbyCircleAdapter.CustomViewHolder> {

    private Context mCtx;
    private HashMap<String, hobbyData> mData;

    public HobbyCircleAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.circle_hobby_item, parent, false);


        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        hobbyData hobby = (hobbyData) mData.values().toArray()[position];

        String img = hobby.getImgUrl();
        holder.hobbyId = hobby.getHobbyId();
        holder.hobbyName = hobby.getName();

        holder.tvName.setText(hobby.getName());
        holder.tvMemberCount.setText(hobby.getMemberCount() + "");
        Picasso.get().load(img).into(holder.ivHobby);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        View pView;
        TextView tvName;
        TextView tvMemberCount;
        ImageView ivHobby;

        int hobbyId;
        String hobbyName;

        public CustomViewHolder(View itemView) {
            super(itemView);

            pView = itemView;
            tvName = itemView.findViewById(R.id.hobbyName);
            tvMemberCount = itemView.findViewById(R.id.hobbyMemberCount);
            ivHobby = itemView.findViewById(R.id.hobbyImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(v.getContext(), HobbyActivity.class);
                    intent.putExtra("hobbyId", hobbyId);
                    intent.putExtra("hobbyName", hobbyName);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    public void addDataAndUpdate(String key, hobbyData p){
        mData.put(key, p);
        notifyDataSetChanged();
    }

    public void deleteDataAndUpdate(String key){
        mData.remove(key);
        notifyDataSetChanged();
    }
}