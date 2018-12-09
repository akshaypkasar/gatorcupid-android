package com.example.surfacepro2.gatorscupid.constant;

public enum Gender {

    MALE(1), FEMALE(2), OTHER(3);

    int value;

    Gender(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}