package kr.hs.emirim.sookhee.maha.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.HobbyActivity;
import kr.hs.emirim.sookhee.maha.MainActivity;
import kr.hs.emirim.sookhee.maha.R;
import kr.hs.emirim.sookhee.maha.SearchDialog;
import kr.hs.emirim.sookhee.maha.SearchPopupActivity;
import kr.hs.emirim.sookhee.maha.WritePostActivity;

public class TagSearchAdapter extends RecyclerView.Adapter<TagSearchAdapter.ViewHolder> {
    private ArrayList<String> values;
    private ArrayList<Boolean> isChecked = new ArrayList<>();

    public TagSearchAdapter(ArrayList<String> values) {
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_false_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tagPosition = position;
        holder.tagName = values.get(position);
        holder.tag.setText(values.get(position));

        isChecked.add(false);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tag, poundKey;
        private LinearLayout tagBackground;

        private int tagPosition;
        private String tagName;

        ViewHolder(View itemView) {
            super(itemView);
            poundKey = (TextView)itemView.findViewById(R.id.poundKey);
            tag = (TextView) itemView.findViewById(R.id.tagTextView);
            tagBackground = (LinearLayout)itemView.findViewById(R.id.tagBackground);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(isChecked.get(tagPosition))){
                        poundKey.setTextColor(v.getResources().getColor(R.color.purple));
                        tag.setTextColor(v.getResources().getColor(R.color.purple));
                        tagBackground.setBackgroundResource(R.drawable.border_purple);
                        isChecked.set(tagPosition, true);
                        ((HobbyActivity)HobbyActivity.mContext).setTag(tagPosition, tagName, isChecked.get(tagPosition));

                    }
                    else{
                        poundKey.setTextColor(v.getResources().getColor(R.color.warm_gray));
                        tag.setTextColor(v.getResources().getColor(R.color.warm_gray));
                        tagBackground.setBackgroundResource(R.drawable.border_gray);
                        isChecked.set(tagPosition, false);
                        ((HobbyActivity) HobbyActivity.mContext).setTag(tagPosition, tagName, isChecked.get(tagPosition));
                    }
                }
            });
        }
    }
}