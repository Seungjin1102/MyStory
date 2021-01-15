package kr.co.hanbit.myfirstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    public static final int REQUEST_CODE_LOGIN = 101;
    UserDBHelper userDBHelper;
    Button btn_login;
    Button btn_join;
    EditText et_id;
    EditText et_pw;
    int position;
    ArrayList<UserData> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(this, "어플 시작!!", Toast.LENGTH_SHORT).show();

        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {//회원가입시 화면 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);



            }
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DB", "Login 버튼 성공");
                UserDBHelper helper = new UserDBHelper(getApplicationContext());

                et_id = findViewById(R.id.et_id);
                et_pw = findViewById(R.id.et_pw);

                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();

                if(pw.equals(helper.getUserDataPassword(id))){ //아이디와 비밀번호가 같을시 메인액티비티로 이동
                    Log.d("DB", "pw = " + pw + " 디비의 pw = " + helper.getUserDataPassword(id));
                    Log.d("DB", "비밀번호 일치");
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    //intent1.putExtra("name", id);//추가
                    startActivity(intent1);
                }else {
                    Log.d("DB", "비밀번호 불일치");
                    Toast.makeText(getApplicationContext(), "사용자 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    et_id.setText("");
                    et_pw.setText("");
                }


            }
        });
    }




    //로그인 메뉴바 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            position = 0;


            final Dialog dialog = new Dialog(Login.this);
            dialog.setContentView(R.layout.dialog_login);

            final EditText name_dialog = dialog.findViewById(R.id.name_dialog1);
            final EditText password_dialog = dialog.findViewById(R.id.password_dialog);
            dialog.show();
            userDBHelper = new UserDBHelper(this);
            items = userDBHelper.getUserData();

            Button ok_button = dialog.findViewById(R.id.ok_button);
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DB", "버튼 온클릭");
                    while (true) {
                        Log.d("DB", "와일문 안");
                        Log.d("DB", "name dialog = " + name_dialog.getText().toString() + " items name = " + items.get(position).getName());
                        if (name_dialog.getText().toString().equals(items.get(position).getName())) {
                            Log.d("DB", "if (name_dialog.getText().toString() == items.get(position).getName())");
                            if (password_dialog.getText().toString().equals(items.get(position).getPassword())) {
                                Log.d("DB", "if (password_dialog.getText().toString() == items.get(position).getPassword())");
                                userDBHelper.deleteUserData(items.get(position).getName()); //DB에서 사용자 정보 삭제
                                //삭제시에 다이얼로그 메시지 띄우기
                                Toast.makeText(getApplicationContext(), "" + name_dialog.getText().toString() + "의 정보가 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                break;
                            } else {
                                Toast.makeText(getApplicationContext(), "이름이나 비밀번호가 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
                                name_dialog.setText("");
                                password_dialog.setText("");
                                break;
                            }
                        }
                        position++;
                    }


                    //dialog.show();

                }

            });

            //취소 버튼
            Button cancle_button = dialog.findViewById(R.id.cancle_button);
            cancle_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

        }
        return true;
    }
}