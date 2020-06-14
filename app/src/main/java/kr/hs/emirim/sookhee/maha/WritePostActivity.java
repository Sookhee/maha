package kr.hs.emirim.sookhee.maha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scrat.app.richtext.RichEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WritePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GET_CONTENT = 666;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 444;

    private RichEditText richEditText;
    TextView tvSubmit;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        richEditText = (RichEditText) findViewById(R.id.rich_text);
        tvSubmit = (TextView)findViewById(R.id.submit);
        etTitle = (EditText)findViewById(R.id.titleEditText);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String date = new SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault()).format(currentTime);
                long curTime = System.currentTimeMillis();
                String title = etTitle.getText().toString().trim();
                String content = richEditText.toHtml().toString();

                Log.e("title", title);
                Log.e("content", content);
                Log.e("date", date);
                Log.e("curTime", curTime + "");

                finish();
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
}