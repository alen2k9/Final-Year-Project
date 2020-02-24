package data;

import java.util.Date;

public class User{
    private String username;
    private String email;
    private String password;
    private Date createTime;

    public User() {}

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public java.util.Date getCreateTime(){
        return createTime;
    }

    public void setCreate_time(Date createTime){
        this.createTime=createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
