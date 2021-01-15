package kr.co.hanbit.myfirstapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddMain extends AppCompatActivity implements  Serializable {


    EditText add_main_title;
    EditText add_main_content;
    ImageView add_main_image;
    String name;
    String uri;
    ArrayList<String> uriSet = new ArrayList<>();
    int position = 0;
    ViewDBHelper dbHelper;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_main);
        Log.d("DB", "AddMain 시작");


        Log.d("DB", "Main으로부터 받은 name : " + name);
        add_main_image = findViewById(R.id.add_main_image);

        add_main_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage();
            }
        });



    }

    private void setImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();

                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    uri = fileUri.toString();
                    Log.d("DB", "Fragment1 uri의 String = " + uri);
                    add_main_image.setImageURI(fileUri);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //옵션바 띄우기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    //옵션바 메뉴 설정
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //메뉴별 동작

        if(item.getItemId() == R.id.add_back){//뒤로가기시 액티비티 종료
            finish();
        }else if(item.getItemId() == R.id.add_save){//저장버튼 누룰시
            dbHelper = new ViewDBHelper(this);

            add_main_title = findViewById(R.id.add_main_title);
            add_main_content = findViewById(R.id.add_main_content);

            String title = add_main_title.getText().toString();
            String content = add_main_content.getText().toString();
            String date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
            Log.d("DB", "현재 시각 : " + date);


            dbHelper.InsertViewData(title, content, date, uri); //DB에 저장장

            //UI에 저장
           MainActivity.adapter.addItem(new ViewData(title, content, date, uri));


            finish();

       }


        return true;
    }



}
