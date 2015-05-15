package com.yuneec.android.flyingexpert.entity;

import java.io.Serializable;

public class ResourceInfo implements Serializable{

	
	private String name = "";
	private String createDate = "";
	private String size = "";
	private String link = "";
	private String streamType = "";
	private int type = 0; // to note the resource type, 0 instead of Pic,and 1 instead of media.
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getStreamType() {
		return streamType;
	}
	public void setStreamType(String streamType) {
		this.streamType = streamType;
	}
	
	

}
