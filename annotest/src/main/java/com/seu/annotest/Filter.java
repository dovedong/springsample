package com.seu.annotest;

@Table("user")
public class Filter {

    @Column("id")
    private int id;

    @Column("user_name")
    private String userName;

    @Column("age")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
