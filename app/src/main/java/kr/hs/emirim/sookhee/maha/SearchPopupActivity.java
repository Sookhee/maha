package kr.hs.emirim.sookhee.maha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.maha.adapter.TagSearchAdapter;

public class SearchPopupActivity extends AppCompatActivity {
    public static Context mContext;
    FirebaseDatabase database;

    ArrayList<String> selectedTag = new ArrayList<>();

    RecyclerView tagRecyclerView;
    StaggeredGridLayoutManager tagLayoutManager;

    Button btnCancel;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_popup);

        mContext = this;

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
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), selectedTag.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깥레이어 클릭해도 안닫히게..
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public void setTagRecyclerView() {
        DatabaseReference tagRef = database.getReference().child("hobby").child("0");
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

    public void setTag(int postiion, String name, boolean isChecked){
        if(isChecked){
            selectedTag.add((name));

        }
        else{
            selectedTag.remove(selectedTag.indexOf(name));
        }
    }

}