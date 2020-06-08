package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;

import kr.hs.emirim.sookhee.maha.adapter.HobbyListAdapter;
import kr.hs.emirim.sookhee.maha.adapter.ListAdapter;
import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class HobbyListActivity extends AppCompatActivity {
    RecyclerView hobbyRecyclerView;
    HobbyListAdapter hobbyListAdapter;
    ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_list);

        // Firebase Query
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hobbyRef = database.getReference().child("hobby");
        Query hobbyQuery = hobbyRef;


        hobbyRecyclerView = findViewById(R.id.hobbyListRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        hobbyRecyclerView.setLayoutManager(manager);
        hobbyListAdapter = new HobbyListAdapter(this);
        hobbyRecyclerView.setAdapter(hobbyListAdapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(hobbyListAdapter));
        helper.attachToRecyclerView(hobbyRecyclerView);

        // Hobby List
        hobbyQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData hobby = dataSnapshot.getValue(hobbyData.class);

                hobbyListAdapter.addDataAndUpdate(key, hobby);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData hobby = dataSnapshot.getValue(hobbyData.class);

                hobbyListAdapter.addDataAndUpdate(key, hobby);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                hobbyListAdapter.deleteDataAndUpdate(key);
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
