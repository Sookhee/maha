package kr.hs.emirim.sookhee.maha;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.adapter.TagSearchAdapter;

public class SearchDialog extends Dialog {
    public static Context mContext;
    FirebaseDatabase database;

    RecyclerView tagRecyclerView;
    StaggeredGridLayoutManager tagLayoutManager;
    TagSearchAdapter tagAdapter;

    Button btnCancel;
    Button btnSubmit;

    public SearchDialog(Context context) {
        super(context);
        mContext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.activity_search_popup);     //다이얼로그에서 사용할 레이아웃입니다.

        // Tag RecyclerView
        tagRecyclerView = (RecyclerView)findViewById(R.id.tagRecyclerView);
        tagLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        tagRecyclerView.setLayoutManager(tagLayoutManager);

        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Firebase Query
        database = FirebaseDatabase.getInstance();

        setTagRecyclerView();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HobbyActivity) HobbyActivity.mContext).getTagList();
                dismiss();
            }
        });

    }

    public void setTagRecyclerView() {
        int hobbyId = 0;
        hobbyId = ((HobbyActivity)HobbyActivity.mContext).getHobbyId();
        DatabaseReference tagRef = database.getReference().child("hobby").child(String.valueOf(hobbyId));
        tagRef.child("tag").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> values = (ArrayList<String>) dataSnapshot.getValue();
                tagRecyclerView.setAdapter(new TagSearchAdapter(values));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }

}

