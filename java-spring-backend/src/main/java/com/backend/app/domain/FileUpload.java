package com.backend.app.domain;

import java.util.Date;

public class FileUpload {
private Integer id;
	private String name;
	private String url;
	private String type;
	private Boolean userstatus;
	private long size;
	private String uploadby;
	
	public FileUpload(String name,String type, long size,Integer id,Boolean userstatus,String uploadby) {
		super();
		this.name = name;
	
		this.type = type;
		this.size = size;
		this.id=id;
		this.userstatus=userstatus;
		this.uploadby=uploadby;
	}
	public FileUpload(String filename) {
		this.name=filename;
		// TODO Auto-generated constructor stub
	}
	public FileUpload(String filename, Integer id2,String fileSignedBy,String updateOn,Boolean userstatus,String uploadby) {
		this.name=filename;
		this.size=id2;
		this.url=fileSignedBy;
		this.type=updateOn;
		this.userstatus=userstatus;
		this.uploadby=uploadby;
		// TODO Auto-generated constructor stub
	}

	public String getUploadby() {
		return uploadby;
	}
	public void setUploadby(String uploadby) {
		this.uploadby = uploadby;
	}

	public Boolean getUserStatus() {
		return userstatus;
	}

	public void setUserStatus(Boolean userstatus) {
		this.userstatus = userstatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
