package kr.hs.emirim.sookhee.maha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HobbyActivity extends AppCompatActivity {
    Intent intent;
    TextView tvTitle;

    String hobbyName;
    int hobbyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);

        intent = getIntent();
        hobbyName = intent.getStringExtra("hobbyName");
        hobbyId = intent.getIntExtra("hobbyId", 1);

        tvTitle = (TextView)findViewById(R.id.hobbyTitle);
        tvTitle.setText(hobbyName);


    }

    public void back(View v){
        super.onBackPressed();
    }
}
