package kr.hs.emirim.sookhee.maha.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import kr.hs.emirim.sookhee.maha.HobbyActivity;
import kr.hs.emirim.sookhee.maha.HobbyAddActivity;
import kr.hs.emirim.sookhee.maha.HobbyAddDialog;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.SearchDialog;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class HobbyAddAdapter extends RecyclerView.Adapter<HobbyAddAdapter.CustomViewHolder> {

    Intent intent;
    private Context mCtx;
    private HashMap<String, hobbyData> mData;

    public HobbyAddAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.hobby_add_item, parent, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        hobbyData hobby = (hobbyData) mData.values().toArray()[position];

        String img = hobby.getImgUrl();
        holder.hobbyId = hobby.getHobbyId();
        holder.hobbyName = hobby.getName();

        holder.tvName.setText(hobby.getName());
        Picasso.get().load(img).into(holder.ivHobby);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        View pView;
        TextView tvName;
        ImageView ivHobby;

        int hobbyId;
        String hobbyName;

        public CustomViewHolder(View itemView) {
            super(itemView);

            pView = itemView;
            tvName = itemView.findViewById(R.id.hobbyName);
            ivHobby = itemView.findViewById(R.id.hobbyImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisplayMetrics dm = v.getContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
                    int width = dm.widthPixels; //디바이스 화면 너비
                    int height = dm.heightPixels; //디바이스 화면 높이

                    HobbyAddDialog had = new HobbyAddDialog(v.getContext());
                    WindowManager.LayoutParams wm = had.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
                    wm.copyFrom(had.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
                    wm.width = width;  //화면 너비의 절반
                    wm.height = (height*3)/10;  //화면 높이의 절반

                    had.show();
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