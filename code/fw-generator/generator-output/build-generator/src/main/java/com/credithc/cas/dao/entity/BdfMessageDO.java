package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfMessageDO extends AbsEntity{
	
	private String id;
	private String content;
	private Integer read;
	private String receiver;
	private Integer reply;
	private Date sendDate;
	private String sender;
	private String title;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getRead() {
		return this.read;
	}
	public void setRead(Integer read) {
		this.read = read;
	}
	
	public String getReceiver() {
		return this.receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public Integer getReply() {
		return this.reply;
	}
	public void setReply(Integer reply) {
		this.reply = reply;
	}
	
	public Date getSendDate() {
		return this.sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	public String getSender() {
		return this.sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}

