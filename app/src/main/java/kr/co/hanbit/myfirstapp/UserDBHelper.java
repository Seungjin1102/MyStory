package kr.co.hanbit.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME= "myfirstapp.db";


    public UserDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //DB생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS UserData (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, password TEXT NOT NULL, pwck TEXT NOT NULL, email TEXT NOT NULL)");
    }

    //DB에서 비밀번호만 불러오기
    public String getUserDataPassword(String _name){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT password FROM UserData WHERE name = '" + _name +"'", null);
        cursor.moveToNext();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }

    //DB에 저장된 사용자 목록 불러오기
    public ArrayList<UserData> getUserData(){
        ArrayList<UserData> userItems = new ArrayList<>(); //사용자정보가 저장된 배열

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM UserData", null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String pwck = cursor.getString(cursor.getColumnIndex("pwck"));
                String email = cursor.getString(cursor.getColumnIndex("email"));

                UserData userItem = new UserData();
                userItem.setName(name);
                userItem.setPassword(password);
                userItem.setPwck(pwck);
                userItem.setEmail(email);

                userItems.add(userItem);
            }
        }
        cursor.close();
        return userItems;
    }



    //데이터 삽입
    public void InsertUserData(String _name, String _password, String _pwck, String _email){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO UserData (name, password, pwck, email) VALUES('" + _name + "','" + _password + "','" + _pwck + "','" + _email + "');");
    }

    //데이터 삭제
    public void deleteUserData(String _name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM UserData WHERE name = '" + _name  + "'");
    }



    //수정문(안씀)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
