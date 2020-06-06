package kr.hs.emirim.sookhee.maha;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.annotations.Nullable;

        import de.hdodenhof.circleimageview.CircleImageView;
        import kr.hs.emirim.sookhee.maha.adapter.HobbyCircleAdapter;
        import kr.hs.emirim.sookhee.maha.adapter.PostSmallAdapter;
        import kr.hs.emirim.sookhee.maha.model.hobbyData;
        import kr.hs.emirim.sookhee.maha.model.postData;

public class MainActivity extends AppCompatActivity {
    RecyclerView hobbyRecyclerView;
    LinearLayoutManager hobbyLayoutManager;
    HobbyCircleAdapter hobbyAdapter;

    RecyclerView postRecyclerView;
    LinearLayoutManager postLayoutManager;
    PostSmallAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Query
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hobbyRef = database.getReference().child("hobby");
        Query hobbyQuery = hobbyRef;
        DatabaseReference postRef = database.getReference().child("post");
        Query postQuery = postRef;

        // Hobby RecyclerView
        hobbyRecyclerView = (RecyclerView)findViewById(R.id.hobbyRecyclerView);
        hobbyAdapter = new HobbyCircleAdapter(this);
        hobbyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hobbyRecyclerView.setLayoutManager(hobbyLayoutManager);
        hobbyRecyclerView.setAdapter(hobbyAdapter);

        // Post RecyclerView
        postRecyclerView = (RecyclerView)findViewById(R.id.postRecyclerView);
        postAdapter = new PostSmallAdapter(this);
        postLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postLayoutManager.setReverseLayout(true);
        postLayoutManager.setStackFromEnd(true);
        postRecyclerView.setLayoutManager(postLayoutManager);
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
    }

    public void clickProfile(View v){
        Intent intent;
        intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

}
