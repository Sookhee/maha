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

import kr.hs.emirim.sookhee.maha.HobbyActivity;
import kr.hs.emirim.sookhee.maha.ItemTouchHelperListener;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class HobbyListAdapter extends RecyclerView.Adapter<HobbyListAdapter.ItemViewHolder> implements ItemTouchHelperListener {
    Intent intent;
    ArrayList<hobbyData> items = new ArrayList<>();
    private Context mCtx;
    private HashMap<String, hobbyData> mData;

    public HobbyListAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줌
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hobby_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.onBind(items.get(position));
    }

    @Override public int getItemCount() {
        return items.size();
    }


    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        hobbyData hobby = items.get(from_position);
        //이동할 객체 삭제
        items.remove(from_position);
        // 이동하고 싶은 position에 추가
        items.add(to_position,hobby);
        // Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);

        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }


    //오른쪽 버튼 누르면 아이템 삭제
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        items.remove(position);
        notifyItemRemoved(position);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView hobbyName;
        TextView hobbyMemberCount;
        ImageView hobbyImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            hobbyName = itemView.findViewById(R.id.hobbyName);
            hobbyMemberCount = itemView.findViewById(R.id.hobbyMemberCount);
            hobbyImage = itemView.findViewById(R.id.hobbyImage);

        }

        public void onBind(hobbyData hobby) {
            hobbyName.setText(hobby.getName());
            hobbyMemberCount.setText(String.valueOf(hobby.getMemberCount()));
            Picasso.get().load(hobby.getImgUrl()).into(hobbyImage);
        }
    }

    public void addDataAndUpdate(String key, hobbyData p){
        mData.put(key, p);
        items.add(p);
        notifyDataSetChanged();
    }

    public void deleteDataAndUpdate(String key){
        mData.remove(key);
        notifyDataSetChanged();
    }
}