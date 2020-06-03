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
        import kr.hs.emirim.sookhee.maha.model.hobbyData;

public class MainActivity extends AppCompatActivity {

    CircleImageView civProfileImage;

    RecyclerView hobbyRecyclerView;
    LinearLayoutManager hobbyLayoutManager;
    HobbyCircleAdapter hobbyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        civProfileImage = (CircleImageView)findViewById(R.id.mainProfileImage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference().child("hobby");
        Query shelterQuery = mRef;

        hobbyRecyclerView = (RecyclerView)findViewById(R.id.hobbyRecyclerView);
        hobbyAdapter = new HobbyCircleAdapter(this);
        hobbyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hobbyRecyclerView.setLayoutManager(hobbyLayoutManager);
        hobbyRecyclerView.setAdapter(hobbyAdapter);

        shelterQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData shelter = dataSnapshot.getValue(hobbyData.class);

                hobbyAdapter.addDataAndUpdate(key, shelter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                hobbyData shelter = dataSnapshot.getValue(hobbyData.class);

                hobbyAdapter.addDataAndUpdate(key, shelter);
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

        civProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
