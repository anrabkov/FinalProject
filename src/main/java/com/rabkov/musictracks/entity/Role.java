package com.rabkov.musictracks.entity;

public enum Role {
    USER("user"),
    ADMIN("admin"),
    GUEST("guest");

    private String value;

    Role(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}
