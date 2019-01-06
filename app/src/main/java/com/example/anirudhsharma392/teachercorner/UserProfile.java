package com.example.anirudhsharma392.teachercorner;

public class UserProfile {

    public UserProfile(){}
    public String name;
    public String mail;
    public String age;
    public String address;
    public String phone;



    public UserProfile(String name , String mail, String age, String address, String phone) {
        this.name = name;
        this.mail = mail;

        this.age = age;
        this.address = address;
        this.phone = phone;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
