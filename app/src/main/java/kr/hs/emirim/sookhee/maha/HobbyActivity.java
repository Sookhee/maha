package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;

import org.w3c.dom.Text;

import kr.hs.emirim.sookhee.maha.adapter.PostLargeAdapter;
import kr.hs.emirim.sookhee.maha.model.postData;

public class HobbyActivity extends AppCompatActivity {
    Intent intent;
    TextView tvTitle;
    TextView tvGoWrite;
    NestedScrollView svScrollView;
    ConstraintLayout clSearchBar;

    String hobbyName;
    int hobbyId;

    RecyclerView postRecyclerView;
    LinearLayoutManager postLayoutManager;
    PostLargeAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        intent = getIntent();
        hobbyName = intent.getStringExtra("hobbyName");
        hobbyId = intent.getIntExtra("hobbyId", 1);

        tvTitle = (TextView)findViewById(R.id.hobbyTitle);
        tvGoWrite = (TextView)findViewById(R.id.hobbyWrite);
        svScrollView = (NestedScrollView)findViewById(R.id.hobbyScrollView);
        clSearchBar = (ConstraintLayout)findViewById(R.id.hobbyTagSearchLayout);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference().child("post");
        Query postQuery = postRef;

        // Hobby 액티비티 Top 네비게이션 텍스트 설정
        tvTitle.setText(hobbyName);

        // Post RecyclerView
        postRecyclerView = (RecyclerView)findViewById(R.id.postRecyclerView);
        postAdapter = new PostLargeAdapter(this);
        postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postRecyclerView.setLayoutManager(postLayoutManager);
        postRecyclerView.setAdapter(postAdapter);

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

        tvGoWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), WriteActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        clSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });

        svScrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    public void back(View v){
        super.onBackPressed();
    }
}
