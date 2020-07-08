package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;
import com.loopeer.shadow.ShadowView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.adapter.PostLargeAdapter;
import kr.hs.emirim.sookhee.maha.model.hobbyData;
import kr.hs.emirim.sookhee.maha.model.postData;

public class HobbyActivity extends AppCompatActivity {
    public static Context mContext;

    Intent intent;
    TextView tvTitle;
    FloatingActionButton tvGoWrite;
    TextView tvGoSearch;
    TextView tvSearchText;
    ShadowView svAddHobby;
    NestedScrollView hobbyScrollView;

    TextView tvHobbyName;
    TextView tvHobbyIntro;
    TextView tvHobbyMemberCount;
    TextView tvHobbyPostCount;
    ImageView ivHobbyBackground;
    ConstraintLayout clSearchBar;

    String hobbyName;
    int hobbyId;

    RecyclerView postRecyclerView;
    LinearLayoutManager postLayoutManager;
    PostLargeAdapter postAdapter;

    SearchDialog sd;
    HobbyAddDialog hobbyAddDialog;

    ArrayList<String> selectedTag = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        mContext = this;

        intent = getIntent();
        hobbyName = intent.getStringExtra("hobbyName");
        hobbyId = intent.getIntExtra("hobbyId", 1);

        hobbyScrollView = (NestedScrollView)findViewById(R.id.hobbyScrollView);

        tvTitle = (TextView)findViewById(R.id.hobbyTitle);
        tvSearchText = (TextView)findViewById(R.id.tvSearchText);
        tvGoWrite = (FloatingActionButton) findViewById(R.id.goWrite);
        tvGoSearch = (TextView)findViewById(R.id.hobbySearch);
        svAddHobby = (ShadowView)findViewById(R.id.hobbyAdd);
        clSearchBar = (ConstraintLayout)findViewById(R.id.clSearchBar);

        tvHobbyName = (TextView)findViewById(R.id.tvHobbyName);
        tvHobbyIntro = (TextView)findViewById(R.id.tvHobbyIntro);
        tvHobbyMemberCount = (TextView)findViewById(R.id.tvHobbyMemberCount);
        tvHobbyPostCount = (TextView)findViewById(R.id.tvHobbyPostCount);
        ivHobbyBackground = (ImageView)findViewById(R.id.ivHobbyBackground);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference().child("post");
        Query postQuery = postRef.orderByChild("hobbyId").equalTo(hobbyId);
        DatabaseReference hobbyRef = database.getReference().child("hobby");
        Query hobbyQuery = hobbyRef.orderByChild("hobbyId").equalTo(hobbyId);


        // Hobby 액티비티 Top 네비게이션 텍스트 설정
        tvTitle.setText(hobbyName);

        // Post RecyclerView
        postRecyclerView = (RecyclerView)findViewById(R.id.postRecyclerView);
        postAdapter = new PostLargeAdapter(this);
        postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postLayoutManager.setReverseLayout(true);
        postLayoutManager.setStackFromEnd(true);
        postRecyclerView.setLayoutManager(postLayoutManager);
        postRecyclerView.setAdapter(postAdapter);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels; //디바이스 화면 높이

        sd = new SearchDialog(this);
        WindowManager.LayoutParams wm = sd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
        wm.copyFrom(sd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
        wm.width = width;  //화면 너비의 절반
        wm.height = height/2;  //화면 높이의 절반

        // Post List
        postQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                postData post = dataSnapshot.getValue(postData.class);

                postAdapter.addDataAndUpdate(key, post);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                postData post = dataSnapshot.getValue(postData.class);

                postAdapter.addDataAndUpdate(key, post);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                postAdapter.deleteDataAndUpdate(key);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Hobby Data
        hobbyQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData hobby = dataSnapshot.getValue(hobbyData.class);

                String imgUrl = hobby.getImgUrl();

                tvHobbyName.setText(hobby.getName());
                tvHobbyIntro.setText(hobby.getIntro());
                tvHobbyMemberCount.setText(hobby.getMemberCount() + "");
                tvHobbyPostCount.setText(hobby.getPostCount() + "");
                Picasso.get().load(imgUrl).into(ivHobbyBackground);

                hobbyScrollView.scrollTo(0, 0);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData hobby = dataSnapshot.getValue(hobbyData.class);

                tvHobbyName.setText(hobby.getName());
                tvHobbyIntro.setText(hobby.getIntro());
                tvHobbyMemberCount.setText(hobby.getMemberCount() + "");
                tvHobbyPostCount.setText(hobby.getPostCount() + "");

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvGoWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), WritePostActivity.class);
                intent.putExtra("hobbyId", hobbyId);
                intent.putExtra("hobbyName", hobbyName);
                v.getContext().startActivity(intent);
            }
        });

//        clSearchBar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent;
//                intent = new Intent(v.getContext(), SearchPopupActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });


        clSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sd.show();  //다이얼로그
            }
        });

    }

    public void setTag(int postiion, String name, boolean isChecked){
        if(isChecked){
            selectedTag.add((name));

        }
        else{
            selectedTag.remove(selectedTag.indexOf(name));
        }
    }

    public void getTagList(){
        if(selectedTag.size() > 0){
            String strTag = "#" + selectedTag.get(0);
            for(int i = 1; i < selectedTag.size(); i++){
                strTag += ", #" + selectedTag.get(i);
            }
            tvSearchText.setText(strTag);
        }
        else{
            tvSearchText.setText("");
        }
    }

    public int getHobbyId(){
        return hobbyId;
    }

    public void back(View v){
        super.onBackPressed();
    }
}
