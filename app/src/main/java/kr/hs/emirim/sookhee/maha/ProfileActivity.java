package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.hs.emirim.sookhee.maha.adapter.HobbyCircleAdapter;
import kr.hs.emirim.sookhee.maha.adapter.PostSmallAdapter;
import kr.hs.emirim.sookhee.maha.model.hobbyData;
import kr.hs.emirim.sookhee.maha.model.postData;

public class ProfileActivity extends AppCompatActivity {
    RecyclerView hobbyRecyclerView;
    LinearLayoutManager hobbyLayoutManager;
    HobbyCircleAdapter hobbyAdapter;

    RecyclerView postRecyclerView;
    LinearLayoutManager postLayoutManager;
    PostSmallAdapter postAdapter;

    CircleImageView ivProfile;
    TextView tvNickname, tvUserArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Firebase Query
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hobbyRef = database.getReference().child("hobby");
        Query hobbyQuery = hobbyRef;
        DatabaseReference postRef = database.getReference().child("post");
        Query postQuery = postRef.orderByChild("writerId").equalTo("s2018s38@gmail.com");
        DatabaseReference userRef = database.getReference().child("user");
        Query userQuery = userRef.orderByChild("email").equalTo("s2018s38@gmail.com");

        ivProfile = (CircleImageView)findViewById(R.id.hobbyImage);
        tvNickname = (TextView)findViewById(R.id.proflieUserName);
        tvUserArea = (TextView)findViewById(R.id.profileArea);

        // Hobby RecyclerView
        hobbyRecyclerView = (RecyclerView)findViewById(R.id.profileMyHobbyRecyclerView);
        hobbyAdapter = new HobbyCircleAdapter(this);
        hobbyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hobbyRecyclerView.setLayoutManager(hobbyLayoutManager);
        hobbyRecyclerView.setAdapter(hobbyAdapter);

        // Post RecyclerView
        postRecyclerView = (RecyclerView)findViewById(R.id.profileMyPostRecyclerView);
        postAdapter = new PostSmallAdapter(this);
        postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postRecyclerView.setLayoutManager(postLayoutManager);
        postLayoutManager.setReverseLayout(true);
        postLayoutManager.setStackFromEnd(true);
        postRecyclerView.setAdapter(postAdapter);

        // Hobby List
        hobbyQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData hobby = dataSnapshot.getValue(hobbyData.class);

                hobbyAdapter.addDataAndUpdate(key, hobby);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData hobby = dataSnapshot.getValue(hobbyData.class);

                hobbyAdapter.addDataAndUpdate(key, hobby);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                hobbyAdapter.deleteDataAndUpdate(key);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        // User Data
        userQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String userName, userProfileImg, userArea;

                userName = dataSnapshot.child("email").getValue(String.class);
                userArea = dataSnapshot.child("area").getValue(String.class);
                userProfileImg = dataSnapshot.child("profileImg").getValue(String.class);

                tvNickname.setText(userName);
                tvUserArea.setText(userArea);
                Picasso.get().load(userProfileImg).into(ivProfile);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String userName, userProfileImg, userArea;

                userName = dataSnapshot.child("userName").getValue(String.class);
                userArea = dataSnapshot.child("area").getValue(String.class);
                userProfileImg = dataSnapshot.child("profileImg").getValue(String.class);

                tvNickname.setText(userName);
                tvUserArea.setText(userArea);
                Picasso.get().load(userProfileImg).into(ivProfile);
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
    }

    public void back(View v){
        super.onBackPressed();
    }

    public void clickSetting(View v){
        Intent intent;
        intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
    }

    public void clickHobbyAdd(View v){
        Intent intent;
        intent = new Intent(getApplicationContext(), HobbyAddActivity.class);
        startActivity(intent);
    }

    public void clickHobbyList(View v){
        Intent intent;
        intent = new Intent(getApplicationContext(), HobbyListActivity.class);
        startActivity(intent);
    }
}
