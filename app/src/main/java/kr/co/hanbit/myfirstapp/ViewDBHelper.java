package kr.co.hanbit.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewDBHelper extends SQLiteOpenHelper implements Serializable {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME= "myfirstappView.db";


    public ViewDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //DB테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ViewData (title TEXT NOT NULL, content TEXT NOT NULL, date TEXT NOT NULL, uri1 TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //DB 튜플 전체 가져오기
    public ArrayList<ViewData> getViewData(){
        ArrayList<ViewData> ViewItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ViewData", null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                ViewData item = new ViewData();

                //item.setName(cursor.getString(cursor.getColumnIndex("name")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                item.setContent(cursor.getString(cursor.getColumnIndex("content")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                item.setUri1(cursor.getString(cursor.getColumnIndex("uri1")));


                ViewItems.add(item);
            }

        }
        cursor.close();
        return ViewItems;
    }
    //데이터삽입
    public void InsertViewData(String _title, String _content, String _date, String _uri1){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ViewData (title, content, date, uri1) VALUES('" + _title + "','" +_content + "','" + _date + "','" + _uri1 + "');");
    }

    public void deleteViewData(String _title){//데이터삭제
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM ViewData WHERE title = '" + _title + "'");
    }

    public void updateViewData(ViewData _viewData, String _getTitle){//데이터 수정
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ViewData SET title = '"+ _viewData.getTitle() +"', content = '"+ _viewData.getContent() +"' , date = '" + _viewData.getDate() + "', uri1 = '" + _viewData.getUri1() +"' WHERE title = '"+ _getTitle +"';");
    }

}
