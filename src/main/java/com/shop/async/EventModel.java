package com.shop.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
	private EventType type;   //事件类型
	private int actorId;      //事件触发者id，如用户id
	private int entityType;   //触发对象的类型
	private int entityId;     //触发对象id，如商品id
	private int entityOwnerId;//触发对象的拥有者
	private Map<String, String> exts = new HashMap<String, String>();

	
	public EventModel(EventType type) {
		this.type = type;
	} 
	
	public EventModel() {}
	
	public String getExt(String key) {
		return exts.get(key);
	}
	
	public EventModel setExt(String key, String value) {
		exts.put(key, value);
		return this;
	}
	
	 
	
	
	
	public EventType getType() {
		return type;
	}
	public EventModel setType(EventType type) {
		this.type = type;
		return this;
	}
	public int getActorId() {
		return actorId;
	}
	public EventModel setActorId(int actorId) {
		this.actorId = actorId;
		return this;
	}
	public int getEntityType() {
		return entityType;
	}
	public EventModel setEntityType(int entityType) {
		this.entityType = entityType;
		return this;
	}
	public int getEntityId() {
		return entityId;
	}
	public EventModel setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}
	public int getEntityOwnerId() {
		return entityOwnerId;
	}
	public EventModel setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
		return this;
	}
	public Map<String, String> getExts() {
		return exts;
	}
	public EventModel setExts(Map<String, String> exts) {
		this.exts = exts;
		return this;
	}
	
}
