package kr.co.hanbit.myfirstapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateMain extends AppCompatActivity implements Serializable {

    private EditText update_main_title;
    private EditText update_main_content;
    private ImageView update_main_image;
    private ViewDBHelper dbHelper;
    String updateUri;//수정된 Uri 저장
    String getTitle;
    ArrayList<ViewData> items = new ArrayList<>();
    private ViewData viewData = new ViewData(); //선택된 뷰의 정보



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_main);

        setInit();//초기설정


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkUpdate();//업데이트 체크 후 DB내용 변경
    }

    private void setInit() {
        //Log.d("DB", "setInit() 호출");
        dbHelper = new ViewDBHelper(this);
        items = dbHelper.getViewData();//DB에서 viewData 목록 불러오기기
        //Log.d("DB", "items = dbHelper.getViewData(); 호출");
        Intent intent = getIntent();
        getTitle = intent.getStringExtra("title");
        //Log.d("DB", "getTitle = " + getTitle);
        for(int i = 0; i < items.size(); i++){
            if(getTitle.equals(items.get(i).getTitle())){
                //Log.d("DB", "if문 items.get(i).getTitle()) = " + items.get(i).getTitle());
                viewData.setTitle(items.get(i).getTitle());
                viewData.setContent(items.get(i).getContent());
                viewData.setUri1(items.get(i).getUri1());
                updateUri = items.get(i).getUri1(); //추가한것
            }
        }

        update_main_title = findViewById(R.id.update_main_title);
        update_main_content = findViewById(R.id.update_main_content);
        update_main_image = findViewById(R.id.update_main_image);

        update_main_title.setText(viewData.getTitle()); //타이틀 불러오기
        update_main_content.setText(viewData.getContent()); //내용 불러오기

       Glide.with(getApplicationContext()).load((Uri.parse(viewData.getUri1()))).override(600, 200).into(update_main_image);
       update_main_image.setOnClickListener(new View.OnClickListener() { //이미지 클릭시 이미지 선택창으로 이동함수 호출
           @Override
           public void onClick(View v)
           {
               Log.d("DB", "UpdateMain.class : update_main_image.setOnClickListener(new View.OnClickListener() { 호출");
               resetImage();
           }
       });
    }

    private void resetImage() { //이미지 재선택
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);

    }

    private void checkUpdate() {
        if(viewData.getTitle().equals(update_main_title.getText().toString()) && viewData.getContent().equals(update_main_content.getText().toString()) &&
        viewData.getUri1().equals(updateUri)){
           Log.d("DB", "UpdateMain.class : 변경된 내용이 없습니다!!");
        }else {
            //변경내용 있을시 DB수정하기
            Log.d("DB", "UpdateMain.class : 수정사항있을때");
            String date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
            Log.d("DB", "UpdateMain.class : 현재 시각 date = " + date);
            viewData.setDate(date); //새로운 date로 수정
            viewData.setTitle(update_main_title.getText().toString()); //새로운 title로 수정
            viewData.setContent(update_main_content.getText().toString()); //새로운 cotent로 수정
            viewData.setUri1(updateUri); //새로운 uri로 수정
            dbHelper.updateViewData(viewData, getTitle); //DB 업데이트함수 호출
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream inputStream = resolver.openInputStream(fileUri);
                    updateUri = fileUri.toString(); //수정된 Uri를 저장
                    Log.d("DB", "UpdateMain.class updateUri = " + updateUri);
                    update_main_image.setImageURI(fileUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
