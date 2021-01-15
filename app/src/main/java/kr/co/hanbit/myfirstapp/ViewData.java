package kr.co.hanbit.myfirstapp;

import java.io.Serializable;

public class ViewData implements Serializable {

//    private String name; //이름
    private String title; //제목
    private String content;//내용
    private String date; //생성날짜
    private String uri1; //이미지 경로

    public ViewData(String title, String content, String date, String uri1) {
       // this.name = name;
        this.title = title;
        this.content = content;
        this.date = date;
        this.uri1 = uri1;

    }

    public ViewData(){}



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUri1() {
        return uri1;
    }

    public void setUri1(String uri1) {
        this.uri1 = uri1;
    }


}
