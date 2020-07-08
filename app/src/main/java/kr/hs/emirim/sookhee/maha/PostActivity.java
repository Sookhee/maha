package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.scrat.app.richtext.RichEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.hs.emirim.sookhee.maha.adapter.TagAdapter;
import kr.hs.emirim.sookhee.maha.model.postData;

public class PostActivity extends AppCompatActivity {
    Intent intent;
    String postTitle, postKey = "0";
    int viewCount = 0;

    FirebaseDatabase database;
    DatabaseReference postRef;

    ArrayList<String> postImageList = new ArrayList<>();
    ArrayList<String> tagList = new ArrayList<>();

    TextView tvTitle;
    RichEditText tvContent;
    TextView tvWriter;
    TextView tvDate;
    ImageView ivPostLike;
    TextView tvLikeCount;
    TextView tvViewCount;
    LinearLayout llPostImageList;
    ImageView ivProfile;

    RecyclerView tagRecyclerView;
    StaggeredGridLayoutManager tagLayoutManager;
    TagAdapter tagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        intent = new Intent(this.getIntent());
//        postId = intent.getIntExtra("postId", -1);
        postTitle = intent.getStringExtra("postTitle");

        tvTitle = (TextView)findViewById(R.id.postTitle);
        tvContent = (RichEditText)findViewById(R.id.postContent);
        tvWriter = (TextView)findViewById(R.id.postWriter);
        tvDate = (TextView)findViewById(R.id.postDate);
        ivPostLike = (ImageView)findViewById(R.id.postLikeImage);
        tvLikeCount = (TextView)findViewById(R.id.postLikeCount);
        tvViewCount = (TextView)findViewById(R.id.postViewCount);
        ivProfile = (ImageView)findViewById(R.id.postWriterProfile);

        // Tag RecyclerView
        tagRecyclerView = (RecyclerView)findViewById(R.id.tagRecyclerView);
        tagLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        tagRecyclerView.setLayoutManager(tagLayoutManager);

        llPostImageList = (LinearLayout)findViewById(R.id.postImageList);

        // Firebase Query
        database = FirebaseDatabase.getInstance();
        postRef = database.getReference().child("post");


        postRef.orderByChild("title").equalTo(postTitle).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                postData post = dataSnapshot.getValue(postData.class);
                postKey = dataSnapshot.getKey();
                Log.e("KEY", postKey);

                tvTitle.setText(post.getTitle());
                tvContent.fromHtml(post.getContent());
                tvWriter.setText(post.getWriter());
                tvDate.setText(post.getDate());
                tvLikeCount.setText(post.getLikeCount() + "");
                tvViewCount.setText((post.getViewCount() + 1) + "");
                viewCount = post.getViewCount();
                Picasso.get().load(post.getWriterProfile()).into(ivProfile);

                postImageList = post.getImg();
                setStoryContentLayout(0);

                setTagRecyclerView();
                addPostViewCount();

                }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                postData post = dataSnapshot.getValue(postData.class);


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

        ivPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPostLike.setBackgroundResource(R.drawable.post_heart_true);
            }
        });

    }

    public void back(View v){
        super.onBackPressed();
    }


    public void setStoryContentLayout(int imgCnt){
        llPostImageList.removeAllViews();
        if(postImageList.get(0).matches("")){

        }
        else{
            for(int i = 0; i < postImageList.size(); i++){
                ImageView iv = new ImageView(this);
                Picasso.get().load(postImageList.get(i)).into(iv);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setPadding(0, (int)convertDpToPixel(16), 0, 0);
                llPostImageList.addView(iv);
            }
        }
    }

    public float convertDpToPixel(float dp){
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public void setTagRecyclerView() {
        DatabaseReference tagRef = database.getReference().child("post");
        tagRef.child(postKey).child("tag").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> values = (ArrayList<String>) dataSnapshot.getValue();
                tagRecyclerView.setAdapter(new TagAdapter(values));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }

    public void addPostViewCount(){
        //게시물 viewCount + 1 기능 구현
        DatabaseReference postLikeRef = postRef.child(postKey);
        Map<String, Object> isLikeMap = new HashMap<String, Object>();
        isLikeMap.put("viewCount", viewCount+1);

        postLikeRef.updateChildren(isLikeMap);
    }
}