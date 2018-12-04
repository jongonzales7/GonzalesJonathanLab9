package com.gonzales.jonathan;

public class Person {
    String fullname, gender;
    int age;

    public Person() {}

    public Person(String fullname, String gender, int age) {
        this.fullname = fullname;
        this.gender = gender;
        this.age = age;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
