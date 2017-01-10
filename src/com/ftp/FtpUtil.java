package com.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {
       
	   /**
	    * 上传文件
	    * @param ipAddr FTP服务器IP地址
	    * @param port FTP服务器端口号
	    * @param username FTP服务器登陆名称
	    * @param password FTP服务器登陆密码
	    * @param pathname FTP服务器保存地址
	    * @param filename 需要要上传的文件
	    * @param inputstream 输入文件流
	    * @return
	    */
	   @SuppressWarnings("static-access")
	public static boolean uploadFile(String ipAddr,int port,String username,String password,
String pathname,String filename,InputStream inputstream)
	   {
	   boolean flag = false;
	   FTPClient ftpclient = new FTPClient();
	   ftpclient.setControlEncoding("utf-8");
	   
	   try {
		ftpclient.connect(ipAddr, port);//连接至FTP服务器。
		ftpclient.login(username, password);//登陆FTP服务器。
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	   
	   int replyCode = ftpclient.getReplyCode();//是否成功的登陆服务器。
	   if(!FTPReply.isPositiveCompletion(replyCode))
	   {
		   System.out.println("登陆失败！");
		   return flag;
	   }
	   
	   try {
		ftpclient.setFileType(ftpclient.BINARY_FILE_TYPE);
		ftpclient.makeDirectory(pathname);
		ftpclient.changeWorkingDirectory(pathname);
		ftpclient.storeFile(filename,inputstream);
		   
		inputstream.close();
		ftpclient.logout();
		flag = true;
	} catch (IOException e) {
		e.printStackTrace();
	} 
	finally{
	   if(ftpclient.isConnected())
	   {
		   try {
			ftpclient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }
	}
	   return flag;
	   }
	  
	   
	   /**
	    * 上传文件(可对文件进行重命名)
	    * @param ipAddr FTP服务器地址
	    * @param port FTP服务器端口
	    * @param username FTP服务器登录名称
	    * @param password FTP服务器登陆密码
	    * @param pathname FTP服务器保存地址
	    * @param filename 上传至FTP服务器的文件名
	    * @param originfilename 上传之前的原文件名
	    * @return
	    */
	   public static boolean uploadFileFromProduction(String ipAddr,int port,String username,String password,
	String pathname,String filename,String originfilename)
	   {
		   boolean flag = false;
		   
		   try {
			InputStream inputstream = new FileInputStream(new File(originfilename));
			flag = uploadFile(ipAddr,port,username,password,pathname,filename,inputstream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		   return flag;
	   }
	   
	  /**
	   * 下载文件
	   * @param ipAddr FTP服务器的地址
	   * @param port FTP服务器的端口
	   * @param username FTP服务器的登陆名称
	   * @param password FTP服务器的登录密码
	   * @param pathname FTP服务器的保存地址
	   * @param filename 需要下载的文件的文件名
	   * @param loaclpath 下载文件的保存目录
	   * @return
	   */
	  public static boolean downloadFile(String ipAddr,int port,String username,String password,
	String pathname,String filename,String localpath)
	  {
		  boolean flag = false;
		  FTPClient ftpclient = new FTPClient();
		  ftpclient.setControlEncoding("utf-8");
		  
		  try {
			ftpclient.connect(ipAddr, port);//连接FTP服务器
			ftpclient.login(username, password);//登陆FTP服务器s
			
			int replyCode = ftpclient.getReplyCode();//验证FTP服务器是否登陆成功！
			if(!FTPReply.isPositiveCompletion(replyCode))
			{
				System.out.println("登录失败！");
				return flag;	
			}
			
			ftpclient.changeWorkingDirectory(pathname);//改变FTP目录。
			FTPFile[] ftpfiles = ftpclient.listFiles();
			
			for(FTPFile file : ftpfiles)
			{
				if(filename.equalsIgnoreCase(file.getName()))
				{
						File localfile = new File(localpath+"/"+file.getName());
						OutputStream ots = new FileOutputStream(localfile);
						ftpclient.retrieveFile(file.getName(), ots);
						ots.close();
				}
			}
			
			ftpclient.logout();
			flag = true;
		} catch (SocketException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		  finally
		  {
			  if(ftpclient.isConnected())
			  {
				  try {
					ftpclient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
		  }
		  
		  return flag;
	  }
	  
	  public static void main(String args[])
	  {
		  String ipAddr = "192.168.1.132";
		  int port = 21;
		  String username = "ftptest";
		  String password = "123456";
		  String pathname = "/";
		  String filename = "halsey.mp3";
		  String originfilename = "F://TestFile//halsey.mp3";
		  
		  boolean flag = uploadFileFromProduction(ipAddr,port,username,password,pathname,filename,originfilename);
		  
		  if(flag)
		  {
			  System.out.println("成功！");
		  }
		  else
		  {
			  System.out.println("失败！");
		  }
	  }
}      

