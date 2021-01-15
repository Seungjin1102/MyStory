package kr.co.hanbit.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {


    private RecyclerView recyclerView;
    private String name;
    private ViewDBHelper dbHelper;
    private ArrayList<ViewData> items;
    public static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ViewDBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        Log.d("DB", "이 계정의 이름은 " + name +" 입니다"); //삭제해도 됨
        setInit();
    }

    private void setInit() {
        items = new ArrayList<ViewData>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        loadRecentDB();//DB 데이터 불러오기
    }

    private void loadRecentDB() {
        items = dbHelper.getViewData();

        Log.d("DB", "어댑터 설정");
        adapter = new CustomAdapter(items, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


    }


    //옵션바 띄우기
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setInit();
        Log.d("DB", "onResume setInit()호출");
    }

    //옵션바 설정
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //추가 버튼 누를 시 추가 화면으로 이동
        if(item.getItemId() == R.id.menu_main_add){
            Intent intent = new Intent(this, AddMain.class);
            //intent.putExtra("name", name);
            startActivity(intent);
            Log.d("DB", "startActivity(intent);");


        }else if(item.getItemId() == R.id.menu_main_search){//서치 버튼 누를 시 검색 창으로 이동
            Intent intent = new Intent(this, SearchMain.class);
            startActivity(intent);
            Log.d("DB", "startActivity(intent);");

        }


        return true;
    }
}