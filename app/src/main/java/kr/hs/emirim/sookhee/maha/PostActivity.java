package kr.hs.emirim.sookhee.maha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class PostActivity extends AppCompatActivity {
    Intent intent;
    int postId;
    final ArrayList<String> postImageList = new ArrayList<>();

    TextView tvTitle;
    TextView tvContent;
    TextView tvWriter;
    TextView tvDate;
    TextView tvLikeCount;
    TextView tvViewCount;
    LinearLayout llPostImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        intent = new Intent(this.getIntent());
        postId = intent.getIntExtra("postId", -1);

        tvTitle = (TextView)findViewById(R.id.postTitle);
        tvContent = (TextView)findViewById(R.id.postContent);
        tvWriter = (TextView)findViewById(R.id.postWriter);
        tvDate = (TextView)findViewById(R.id.postDate);
        tvLikeCount = (TextView)findViewById(R.id.postLikeCount);
        tvViewCount = (TextView)findViewById(R.id.postViewCount);

        llPostImageList = (LinearLayout)findViewById(R.id.postImageList);

        // Firebase Query
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference().child("post");
        Query postQuery = postRef.child(String.valueOf(postId));

        postRef.child(String.valueOf(postId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvTitle.setText(dataSnapshot.child("title").getValue(String.class));
                tvContent.setText(dataSnapshot.child("content").getValue(String.class));
                tvWriter.setText(dataSnapshot.child("writer").getValue(String.class));
                tvDate.setText(dataSnapshot.child("date").getValue(String.class));
                tvLikeCount.setText(dataSnapshot.child("likeCount").getValue(int.class) + "");
                tvViewCount.setText(dataSnapshot.child("viewCount").getValue(int.class) + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Post 이미지 불러오기
        postRef.child(String.valueOf(postId)).child("img").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String img = postSnapshot.getValue(String.class);
                    postImageList.add(img);
                }
                setStoryContentLayout(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void back(View v){
        super.onBackPressed();
    }

    public void setStoryContentLayout(int imgCnt){
        llPostImageList.removeAllViews();
        for(int i = 0; i < postImageList.size(); i++){
            ImageView iv = new ImageView(this);
            Picasso.get().load(postImageList.get(imgCnt)).into(iv);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(0, (int)convertDpToPixel(16), 0, 0);
            llPostImageList.addView(iv);
        }
    }

    public float convertDpToPixel(float dp){
        Resources resources = this.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
