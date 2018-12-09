package com.example.surfacepro2.gatorscupid.constant;

public enum Intention {
	
	SHORT_TERM_DATING(0), LONG_TERM_DATING(1), HOOKUP(2), FRIENDSHIP(3);
	
	int value;
	
	Intention(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}

}
