package kr.co.hanbit.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Join extends AppCompatActivity {

    public static final int REQUEST_CODE_LOGIN = 101;
    private EditText join_name;
    private EditText join_password;
    private EditText join_pwck;
    private EditText join_email;
    private Button delete;
    private Button join_button;

    private ArrayList<UserData> userItems;
    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Log.d("DB", "Join onCreate 시작");
        join_name = findViewById(R.id.join_name);
        join_password = findViewById(R.id.join_password);
        join_pwck = findViewById(R.id.join_pwck);
        join_email = findViewById(R.id.join_email);

        delete = findViewById(R.id.delete);
        join_button = findViewById(R.id.join_button);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //입력값 받기
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = join_name.getText().toString();
                String password = join_password.getText().toString();
                String pwck = join_pwck.getText().toString();
                String email = join_email.getText().toString();

                if(!password.equals(pwck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserData userData = new UserData(name, password, pwck, email);
                setInit(userData);
                Toast.makeText(getApplicationContext(), ""+ name +"의 정보가 추가 되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }




    //DB에서 사용자 정보 입력, 불러오기
    private void setInit(UserData userData) {
        userDBHelper = new UserDBHelper(this);
        userItems = new ArrayList<>(); //DB에서 사용자 정보 불러와서 저장할 배열

        loadRecentDB();//DB불러오기
        //입력한 데이터를 DB에 INSERT
        userDBHelper.InsertUserData(userData.getName(), userData.getPassword(), userData.getPwck(), userData.getEmail());

    }

    private void loadRecentDB() {
        //DB에 저장되어 있는 데이터 불러옴
        userItems = userDBHelper.getUserData();
    }

}
