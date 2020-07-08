package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.adapter.HobbyCircleAdapter;
import kr.hs.emirim.sookhee.maha.adapter.PostSmallAdapter;
import kr.hs.emirim.sookhee.maha.adapter.TagApplyAdapter;
import kr.hs.emirim.sookhee.maha.adapter.TagSearchAdapter;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class TagApplyActivity extends AppCompatActivity {
    public static Context mContext;

    Switch swTagOrHobby;
    ConstraintLayout clApplyHobby;
    ConstraintLayout clApplyTag;
    ConstraintLayout slSelectHobby;
    TextView tvSelectTagPlease;
    TextView tvApply;
    EditText etTagApply;
    EditText etHobbyApply;

    RecyclerView rvSelectHobby;
    LinearLayoutManager hobbyLayoutManager;
    TagApplyAdapter hobbyAdapter;

    String selectedHobby = null;
    boolean isClick;


    // Firebase Query
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference hobbyListRef = database.getReference().child("hobbyList");

    ArrayList<String> hobbyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_apply);

        mContext = this;

        clApplyHobby = findViewById(R.id.applyHobby);
        clApplyTag = findViewById(R.id.applyTag);
        tvApply = findViewById(R.id.tvApply);
        tvSelectTagPlease = findViewById(R.id.tvSelectTagPlease);
        slSelectHobby = findViewById(R.id.slSelectHobby);
        rvSelectHobby = findViewById(R.id.rvSelectHobby);
        etTagApply = (EditText)findViewById(R.id.etTagApply);
        etHobbyApply = (EditText)findViewById(R.id.etHobbyApply);

        setTagRecyclerView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSelectHobby.setLayoutManager(linearLayoutManager);

        hobbyAdapter = new TagApplyAdapter();
        rvSelectHobby.setAdapter(hobbyAdapter);

        swTagOrHobby = findViewById(R.id.swTagOrHobby);
        swTagOrHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 취미 신청
                if(swTagOrHobby.isChecked()){
                    clApplyHobby.setVisibility(View.GONE);
                    clApplyTag.setVisibility(View.VISIBLE);
                }
                //태그 신청
                else{
                    clApplyHobby.setVisibility(View.VISIBLE);
                    clApplyTag.setVisibility(View.GONE);
                }
            }
        });

        slSelectHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClick == false){
                    isClick = true;
                    rvSelectHobby.setVisibility(View.VISIBLE);
                }
                else{
                    isClick = false;
                    rvSelectHobby.setVisibility(View.GONE);
                }
            }
        });


        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eTag = etTagApply.getText().toString();
                String eHobby = etHobbyApply.getText().toString();
                Log.e("WHT", eTag);
                Log.e("WHT", eHobby);
                if(swTagOrHobby.isChecked()){
                    if(selectedHobby == null || selectedHobby == "" || eTag.matches("")){
                        Toast.makeText(getApplicationContext(), "빠진 항목이 없는지 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "태그 신청이 접수되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else{
                    if(etHobbyApply.getText() == null || eHobby.matches("")){
                        Toast.makeText(getApplicationContext(), "빠진 항목이 없는지 확인해주세요", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "취미 신청이 접수되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    public void back(View view){
        finish();
    }

    public void setTagRecyclerView(){
        hobbyList.clear();
        // Hobby List
        hobbyListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                hobbyList = (ArrayList<String>) dataSnapshot.getValue();
                for(int i = 0; i < hobbyList.size(); i++){
                    hobbyAdapter.addItem(hobbyList.get(i));
                }

                hobbyAdapter.notifyDataSetChanged();
                rvSelectHobby.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }

    public void clickHobbyItem(String hobby){
        tvSelectTagPlease.setText(hobby);
        isClick = false;
        rvSelectHobby.setVisibility(View.GONE);
        selectedHobby = hobby;
    }
}