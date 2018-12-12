package com.example.surfacepro2.gatorscupid.constant;

import java.util.HashMap;
import java.util.Map;

public enum PicAction {

	DELETE(0), ADD(1);
	
	int value;
	
	PicAction(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	private static Map<Integer, PicAction> map = new HashMap<Integer, PicAction>();

    static {
        for (PicAction legEnum : PicAction.values()) {
            map.put(legEnum.value, legEnum);
        }
    }


    public static PicAction valueOf(int legNo) {
        return map.get(legNo);
    }

}
