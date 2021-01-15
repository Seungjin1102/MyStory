package kr.co.hanbit.myfirstapp;

public class UserData {

    private String name;
    private String password;
    private String pwck;
    private String email;

    public UserData(String name, String password, String pwck, String email) {
        this.name = name;
        this.password = password;
        this.pwck = pwck;
        this.email = email;
    }

    public UserData(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwck() {
        return pwck;
    }

    public void setPwck(String pwck) {
        this.pwck = pwck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
