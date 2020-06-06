package kr.hs.emirim.sookhee.maha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Date currentTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault()).format(currentTime);
        long curTime = System.currentTimeMillis();
        
    }

    public void back(View v){
        super.onBackPressed();
    }
}
