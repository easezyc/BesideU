package com.android.entity;

public class Message {
private String email1,email2,time,content;
public Message(String myemail,String heremail,String time,String content){
	this.email1=myemail;
	this.email2=heremail;
	this.time=time;
	this.content=content;
}
public void setEmail1(String email){
	this.email1=email;
}
public String getEmail1(){
	return email1;
}
public void setEmail2(String email){
	this.email2=email;
}
public String getEmail2(){
	return email2;
}
public void setTime(String time){
	this.time=time;
}
public String getTime(){
	return time;
}
public void setContent(String content){
	this.content=content;
}
public String getContent(){
	return content;
}
}
