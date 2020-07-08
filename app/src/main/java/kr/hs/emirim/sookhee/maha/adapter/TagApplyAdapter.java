package kr.hs.emirim.sookhee.maha.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.HobbyActivity;
import kr.hs.emirim.sookhee.maha.MainActivity;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.TagApplyActivity;
import kr.hs.emirim.sookhee.maha.WritePostActivity;

public class TagApplyAdapter extends RecyclerView.Adapter<TagApplyAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<String> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_tag_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(String data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private String hobby;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.tvApplyTagHobby);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TagApplyActivity) TagApplyActivity.mContext).clickHobbyItem(hobby);
                    Log.e("CLICK", hobby);
                }
            });

        }

        void onBind(final String data) {
            textView1.setText(data);
            hobby = data;
        }
    }
}