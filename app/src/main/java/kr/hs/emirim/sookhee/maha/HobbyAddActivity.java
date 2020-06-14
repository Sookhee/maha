package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    FirebaseDatabase database;
    DatabaseReference hobbyRef;

    EditText etHobbySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_add);

        hobbyRecyclerView = (RecyclerView)findViewById(R.id.hobbyAddRecyclerView);
        etHobbySearch = (EditText)findViewById(R.id.hobbySearch);

        // Firebase Query
        database = FirebaseDatabase.getInstance();
        hobbyRef = database.getReference().child("hobby");

        setHobbyAddRecyclerView("");

        etHobbySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String search = etHobbySearch.getText().toString().trim();
                setHobbyAddRecyclerView(search);
            }

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        etHobbySearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etHobbySearch.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    public void setHobbyAddRecyclerView(String search){

        Query hobbyQuery;

        // Hobby RecyclerView
        hobbyAdapter = new HobbyAddAdapter(this);
        hobbyLayoutManager = new GridLayoutManager(this, 2);
        hobbyRecyclerView.setLayoutManager(hobbyLayoutManager);
        hobbyRecyclerView.setAdapter(hobbyAdapter);

        if(search == "" || search == null) {
            hobbyQuery = hobbyRef;

        }
        else{
            hobbyQuery = hobbyRef.orderByChild("name").startAt(search).endAt(search + "\uf8ff");
        }

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
