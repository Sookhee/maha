package kr.hs.emirim.sookhee.maha.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.ItemTouchHelperListener;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> implements ItemTouchHelperListener{
    ArrayList<hobbyData> items = new ArrayList<>();
    Context context;
    public ListAdapter(Context context){
        this.context = context;
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

    public void addItem(hobbyData person){
        items.add(person);
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
        Log.e("LIST0", items.get(0).getName());
        Log.e("LIST1", items.get(1).getName());
        Log.e("LIST2", items.get(2).getName());
        Log.e("LIST3", items.get(3).getName());
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

//    @Override
//    public void onFinish(int position, hobbyData person) {
//        items.set(position,person);
//        notifyItemChanged(position);
//    }


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
}


