package com.rabkov.musictracks.entity;

public class NewEntity{
    private String name;
    private int age;

    public NewEntity(String name, int age, Role role) {
        this.name = name;
        this.age = age;
        this.role = role;
    }

    private Role role;

    public void fixRole(){
        System.out.println(role.toString());
    }

    public String printSting(String string){
        return string;
    }
}