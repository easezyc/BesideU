package com.android.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.android.entity.Message;
import com.android.entity.User;

public class UserServicedao {
	protected Statement sql=null;
	protected ResultSet rs=null;
	public int add(Connection conn, User user) throws SQLException {
		sql=conn.createStatement();
		int mark=0;
		String condition="insert into user (email,pwd) values('"+user.getEmail()+"','"+user.getPwd()+"')";		
		mark=sql.executeUpdate(condition);
		return mark;
	}
	public int mod(Connection conn, User user) throws SQLException {
		sql=conn.createStatement();
		int mark=0;
		String condition="update user set pwd ='"+user.getPwd()+"' where email ='"+user.getEmail()+"'";
		mark=sql.executeUpdate(condition);
		return mark;
	}
	public int query(Connection conn,User user) throws SQLException{
		sql=conn.createStatement();
		int mark=0;
		String condition="Select *from user where email ='"+user.getEmail()+"' and pwd ='"+user.getPwd()+"'";
		rs=sql.executeQuery(condition);
		while(rs.next()){
			user.setEmail(rs.getString(1));
			user.setPwd(rs.getString(2));
			mark=1;
		}
		return mark;
	}
	public String Getheremail(Connection conn,String myemail) throws SQLException{
		sql=conn.createStatement();
		String result="";
		String condition="Select *from besideu where (email1 ='"+myemail+"' or email2 ='"+myemail+"') and mark ='1'";
		rs=sql.executeQuery(condition);
		while(rs.next()){
		if(rs.getString(1).equals(myemail))result=rs.getString(2);
		else result=rs.getString(1);
		}
		return result;
	}
	public int Deleteher(Connection conn,String heremail) throws SQLException{
		sql=conn.createStatement();
		int mark;
		String condition="delete from besideu where (email1 ='"+heremail+"' or email2 ='"+heremail+"') and mark ='1'";
		mark=sql.executeUpdate(condition);
		condition="delete from board where (email1 ='"+heremail+"' or email2 ='"+heremail+"')";
		sql.executeUpdate(condition);
		return mark;
	}
	public int Addher(Connection conn,String myemail,String heremail) throws SQLException{
		sql=conn.createStatement();
		int mark=0;
		String condition="Select *from user where email ='"+heremail+"'";
		rs=sql.executeQuery(condition);
		while(rs.next()){
			mark=1;
		}
		condition="Select *from besideu where (email1 ='"+myemail+"' or email2 ='"+myemail+"') and mark ='1'";
		rs=sql.executeQuery(condition);
		while(rs.next()){
			mark=0;
		}
		if(mark==1){
		String condition2="insert into besideu (email1,email2,mark) values('"+myemail+"','"+heremail+"','0')";
		mark=sql.executeUpdate(condition2);
		}
		return mark;
	}
	public int Agreeadd(Connection conn,String myemail,String heremail) throws SQLException{
		sql=conn.createStatement();
		int mark=1;
		String condition="Select *from besideu where (email1 ='"+myemail+"' or email2 ='"+myemail+"' or email1 ='"+heremail+"' or email2 ='"+heremail+"') and mark ='1'";
		rs=sql.executeQuery(condition);
		while(rs.next()){
			mark=0;
		}
		if(mark==1){condition="update besideu set mark ='1' where email1 ='"+heremail+"' and email2 ='"+myemail+"'";
		mark=sql.executeUpdate(condition);}
		return mark;
	}
	public int Denyher(Connection conn,String myemail,String heremail) throws SQLException{
		sql=conn.createStatement();
		int mark;
		String condition="delete from besideu where email1 ='"+heremail+"' and email2 ='"+myemail+"' and mark ='0'";
		mark=sql.executeUpdate(condition);
		return mark;
	}
	public ArrayList getApplylist(Connection conn,String myemail) throws SQLException{
		ArrayList<String> array=new ArrayList<String>();
		sql=conn.createStatement();
		String condition="Select email1 from besideu where email2 ='"+myemail+"' and mark ='0'";
		rs=sql.executeQuery(condition);
		while(rs.next()){
			array.add(rs.getString(1));
		}
		return array;
	}
	public int Sendmessage(Connection conn,String myemail,String time,String content) throws SQLException{
		int mark=0;
		String heremail=Getheremail(conn,myemail);
		if(!heremail.equals("")){
			sql=conn.createStatement();
			String condition="insert into board (email1,email2,time,content) values('"+myemail+"','"+heremail+"','"+time+"','"+content+"')";;
			mark=sql.executeUpdate(condition);
		}
		return mark;
	}
	public ArrayList<Message> getBoardlist(Connection conn,String myemail) throws SQLException{
		int mark=0;
		String heremail=Getheremail(conn,myemail);
		ArrayList<Message> array=new ArrayList<Message>();
		sql=conn.createStatement();
		if(!heremail.equals("")){
		
		String condition="Select * from board where (email1 ='"+myemail+"' or email2 ='"+myemail+"') and (email1 ='"+heremail+"' or email2 ='"+heremail+"') ORDER BY TIME ASC LIMIT 60";
		rs=sql.executeQuery(condition);
			while(rs.next()){
				Message message=new Message(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
				array.add(message);
			}
			condition="Select count(*) from board";
			rs=sql.executeQuery(condition);
			rs.next();
			int num=rs.getInt(1)-60;
			if(num>0){
				condition="delete from board where (email1 ='"+myemail+"' or email2 ='"+myemail+"') and (email1 ='"+heremail+"' or email2 ='"+heremail+"') ORDER BY TIME ASC LIMIT "+num;
				sql.executeUpdate(condition);
			}
		}
		return array;
	}
}
