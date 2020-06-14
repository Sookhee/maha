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

import com.scrat.app.richtext.RichEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WritePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GET_CONTENT = 666;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 444;
    private RichEditText richEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        richEditText = (RichEditText) findViewById(R.id.rich_text);
        richEditText.fromHtml(
                "<blockquote>Android 端的富文本编辑器</blockquote>" +
                        "<ul>" +
                        "<li>支持实时编辑</li>" +
                        "<li>支持图片插入,加粗,斜体,下划线,删除线,列表,引用块,超链接,撤销与恢复等</li>" +
                        "<li>使用<u>Glide 4</u>加载图片</li>" +
                        "</ul>" +
                        "<img src=\"http://biuugames.huya.com/221d89ac671feac1.gif\"><br><br>" +
                        "<img src=\"http://biuugames.huya.com/5-160222145918.jpg\"><br><br>"
        );
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