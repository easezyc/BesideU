package com.android.biz;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.android.dao.*;
import com.android.base.*;
import com.android.entity.Message;
import com.android.entity.User;

public class UserService extends DBConnection{
	Connection conn=null;
	public int add(User user) throws ClassNotFoundException, SQLException {
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark =dao.add(conn,user);
		closeConn();
		return mark;
	}
	public int query(User user) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.query(conn, user);
		closeConn();
		return mark;
	}
	public int mod(User user) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.mod(conn, user);
		closeConn();
		return mark;
	}
	public String Getheremail(String myemail) throws ClassNotFoundException, SQLException{
		conn=getCon();
		String result;
		UserServicedao dao=new UserServicedao();
		result=dao.Getheremail(conn, myemail);
		closeConn();
		return result;
	}
	public int Deleteher(String heremail) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.Deleteher(conn,heremail);
		closeConn();
		return mark;
	}
	public int Addher(String myemail,String heremail) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.Addher(conn,myemail,heremail);
		closeConn();
		return mark;
	}
	public int Agreeadd(String myemail,String heremail) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.Agreeadd(conn,myemail,heremail);
		closeConn();
		return mark;
	}
	public int Denyher(String myemail,String heremail) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.Denyher(conn,myemail,heremail);
		closeConn();
		return mark;
	}
	public ArrayList getApplylist(String myemail) throws ClassNotFoundException, SQLException{
		ArrayList<String> array=new ArrayList<String>();
		conn=getCon();
		UserServicedao dao=new UserServicedao();
		array=dao.getApplylist(conn, myemail);
		closeConn();
		return array;
	}
	public int Sendmessage(String myemail,String time,String content) throws ClassNotFoundException, SQLException{
		conn=getCon();
		int mark=0;
		UserServicedao dao=new UserServicedao();
		mark=dao.Sendmessage(conn,myemail,time,content);
		closeConn();
		return mark;
	}
	public ArrayList<Message> getBoardlist(String myemail) throws ClassNotFoundException, SQLException{
		ArrayList<Message> array=new ArrayList<Message>();
		conn=getCon();
		UserServicedao dao=new UserServicedao();
		array=dao.getBoardlist(conn, myemail);
		closeConn();
		return array;
	}
}
