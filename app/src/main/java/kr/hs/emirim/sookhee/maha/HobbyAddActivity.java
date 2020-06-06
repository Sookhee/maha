package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;

import kr.hs.emirim.sookhee.maha.adapter.HobbyAddAdapter;
import kr.hs.emirim.sookhee.maha.adapter.HobbyCircleAdapter;
import kr.hs.emirim.sookhee.maha.adapter.PostLargeAdapter;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class HobbyAddActivity extends AppCompatActivity {

    RecyclerView hobbyRecyclerView;
    GridLayoutManager hobbyLayoutManager;
    HobbyAddAdapter hobbyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_add);

        hobbyRecyclerView = (RecyclerView)findViewById(R.id.hobbyAddRecyclerView);

        // Firebase Query
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hobbyRef = database.getReference().child("hobby");
        Query hobbyQuery = hobbyRef;

        // Hobby RecyclerView
        hobbyAdapter = new HobbyAddAdapter(this);
        hobbyLayoutManager = new GridLayoutManager(this, 2);
        hobbyRecyclerView.setLayoutManager(hobbyLayoutManager);
        hobbyRecyclerView.setAdapter(hobbyAdapter);

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


    }

    public void back(View v){
        super.onBackPressed();
    }

}
