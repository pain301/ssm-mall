package com.pain;

/**
 * Created by Administrator on 2017/8/26.
 */
public class User {
    private String userName;
    private Phone phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", phone=" + phone +
                '}';
    }
}
