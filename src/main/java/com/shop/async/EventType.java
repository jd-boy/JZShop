package com.shop.async;

public enum EventType {
	
	LOOK(0),
    COMMENT(1),
    LOGIN(2),
    GOODS(3),
	CHECK(4),
	BUY_GOODS(5);
	
	private int value;
	
    EventType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
