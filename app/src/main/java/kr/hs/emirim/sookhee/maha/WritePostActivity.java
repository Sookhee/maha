package kr.hs.emirim.sookhee.maha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.scrat.app.richtext.RichEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kr.hs.emirim.sookhee.maha.adapter.TagAdapter;
import kr.hs.emirim.sookhee.maha.adapter.TagSelectAdapter;
import kr.hs.emirim.sookhee.maha.model.postData;

public class WritePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GET_CONTENT = 666;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 444;

    Intent intent;

    private RichEditText richEditText;
    TextView tvSubmit, tvSelectTag;
    ImageView ivSelectTag;
    EditText etTitle;
    ConstraintLayout clRichTextTool, clSelectTag;
    RecyclerView rvSelectTag;

    StaggeredGridLayoutManager tagLayoutManager;

    boolean isSelect = false;
    String hobbyName;
    int hobbyId;
    ArrayList<String> selectedTag = new ArrayList<>();

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        mContext = this;
        intent = getIntent();
        hobbyName = intent.getStringExtra("hobbyName");
        hobbyId = intent.getIntExtra("hobbyId", 1);

        // Firebase Query
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tagRef = database.getReference().child("hobby");
        Query tagQuery = tagRef.child(String.valueOf(0)).child("tag");
        final DatabaseReference writeRef = database.getReference().child("post");

        richEditText = (RichEditText) findViewById(R.id.rich_text);
        tvSubmit = (TextView)findViewById(R.id.submit);
        tvSelectTag = (TextView)findViewById(R.id.tvSelectTag);
        etTitle = (EditText)findViewById(R.id.etTitle);
        clRichTextTool = (ConstraintLayout)findViewById(R.id.rich_text_tool);
        clSelectTag = (ConstraintLayout)findViewById(R.id.clSelectTag);
        rvSelectTag = (RecyclerView)findViewById(R.id.rvSelectTag);
        ivSelectTag = (ImageView)findViewById(R.id.ivSelectTag);

        // Hobby RecyclerView
        tagLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        rvSelectTag.setLayoutManager(tagLayoutManager);


        etTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean gainFocus) {
                //포커스가 주어졌을 때
                if (gainFocus) {
                    clRichTextTool.setVisibility(View.GONE);
                }
                //포커스를 잃었을 때
                else {
                    //키보드 내리기
                    InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

                    immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    clRichTextTool.setVisibility(View.VISIBLE);
                }
            }
        });


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String date = new SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault()).format(currentTime);
                long curTime = System.currentTimeMillis();
                String title = etTitle.getText().toString().trim();
                String content = richEditText.toHtml().toString();


                if(title.matches("") || content.matches("") || selectedTag.size() == 0){
                    Toast.makeText(getApplicationContext(), "빠진 내용이 없는지 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<String> img = new ArrayList<>();
                    img.add("");
                    Map<String, Object> isLikeMap = new HashMap<String, Object>();
                    postData postData = new postData(hobbyId, 0, 0, curTime, date, title, content, hobbyName, "sookhee", "s2018s38@gmail.com", "https://images.unsplash.com/photo-1582769923195-c6e60dc1d8dc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60", img, selectedTag);
                    isLikeMap.put(String.valueOf(curTime), postData);

                    writeRef.updateChildren(isLikeMap);

                    finish();
                }
            }
        });

        clSelectTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelect){
                    rvSelectTag.setVisibility(View.GONE);
                    ivSelectTag.setBackgroundResource(R.drawable.ic_top_arrow);
                    isSelect = false;
                }
                else{
                    rvSelectTag.setVisibility(View.VISIBLE);
                    ivSelectTag.setBackgroundResource(R.drawable.ic_bottom_arrow);
                    isSelect = true;
                }
            }
        });


        tagQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> values = (ArrayList<String>) dataSnapshot.getValue();
                rvSelectTag.setAdapter(new TagSelectAdapter(values));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });

    }

    public void setBold(View v) {
        richEditText.bold(!richEditText.contains(RichEditText.FORMAT_BOLD));
    }

    public void setItalic(View v) {
        richEditText.italic(!richEditText.contains(RichEditText.FORMAT_ITALIC));
    }

    public void setUnderline(View v) {
        richEditText.underline(!richEditText.contains(RichEditText.FORMAT_UNDERLINED));
    }

    public void setStrikethrough(View v) {
        richEditText.strikethrough(!richEditText.contains(RichEditText.FORMAT_STRIKETHROUGH));
    }

    public void setBullet(View v) {
        richEditText.bullet(!richEditText.contains(RichEditText.FORMAT_BULLET));
    }

    public void setQuote(View v) {
        richEditText.quote(!richEditText.contains(RichEditText.FORMAT_QUOTE));
    }

    public void insertImg(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
        getImage.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.setType("image/*");
        startActivityForResult(getImage, REQUEST_CODE_GET_CONTENT);
    }

    public void back(View v){
        super.onBackPressed();
    }

    public void setTag(int postiion, String name, boolean isChecked){
        if(isChecked){
            selectedTag.add((name));

            String tagString = "#" + selectedTag.get(0);
            for(int i = 1; i < selectedTag.size(); i++){
                tagString += ", #" + selectedTag.get(i);
            }

            tvSelectTag.setText(tagString);

        }
        else{
            selectedTag.remove(selectedTag.indexOf(name));
            if(selectedTag.size() == 0){
                tvSelectTag.setText(null);
            }
            else{
                String tagString = "#" + selectedTag.get(0);
                for(int i = 1; i < selectedTag.size(); i++){
                    tagString += ", #" + selectedTag.get(i);
                }

                tvSelectTag.setText(tagString);
            }
        }
    }
}