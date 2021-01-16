package kr.co.hanbit.myfirstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchMain extends AppCompatActivity {

    EditText search_key;
    Button search_ok;
    RecyclerView recyclerView;
    ViewDBHelper dbHelper;
    ArrayList<ViewData> items = new ArrayList<>();
    ArrayList<ViewData> search_items = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        search_key = findViewById(R.id.search_key);
        search_ok = findViewById(R.id.search_ok);
        recyclerView = findViewById(R.id.search_recyclerView);

        search_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInit();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();//종료
    }

    private void setInit() {
        search_items.clear();
        dbHelper = new ViewDBHelper(this);
        items = dbHelper.getViewData();

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getTitle().contains(search_key.getText().toString())){
                search_items.add(items.get(i));
                Log.d("DB", "추가한 title = " + items.get(i).getTitle());
            }
        }

        adapter = new CustomAdapter(search_items, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
