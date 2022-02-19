package com.rabkov.musictracks.entity;

public class NewEntity{
    private String name;
    private int age;

    public NewEntity(String name, int age, Role role) {
        this.name = name;
        this.age = age;

    }

    private Role role;
    private User user;

    public void showScreen(){
        System.out.println("hello from branch b");
    }


    }
