package com.example.surfacepro2.gatorscupid.constant;

public enum Status {
	
	INACTIVE(0), ACTIVE(1), DELETED(2);
	
	int value;
	
	Status(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}


}
