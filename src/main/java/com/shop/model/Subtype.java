package com.shop.model;

public class Subtype {
	
	private int ID;
	private int superType;
	private String typeName;
	private int status;
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getSuperType() {
		return superType;
	}
	public void setSuperType(int superType) {
		this.superType = superType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
