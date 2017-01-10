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
	    * �ϴ��ļ�
	    * @param ipAddr FTP������IP��ַ
	    * @param port FTP�������˿ں�
	    * @param username FTP��������½����
	    * @param password FTP��������½����
	    * @param pathname FTP�����������ַ
	    * @param filename ��ҪҪ�ϴ����ļ�
	    * @param inputstream �����ļ���
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
		ftpclient.connect(ipAddr, port);//������FTP��������
		ftpclient.login(username, password);//��½FTP��������
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	   
	   int replyCode = ftpclient.getReplyCode();//�Ƿ�ɹ��ĵ�½��������
	   if(!FTPReply.isPositiveCompletion(replyCode))
	   {
		   System.out.println("��½ʧ�ܣ�");
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
	    * �ϴ��ļ�(�ɶ��ļ�����������)
	    * @param ipAddr FTP��������ַ
	    * @param port FTP�������˿�
	    * @param username FTP��������¼����
	    * @param password FTP��������½����
	    * @param pathname FTP�����������ַ
	    * @param filename �ϴ���FTP���������ļ���
	    * @param originfilename �ϴ�֮ǰ��ԭ�ļ���
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
	   * �����ļ�
	   * @param ipAddr FTP�������ĵ�ַ
	   * @param port FTP�������Ķ˿�
	   * @param username FTP�������ĵ�½����
	   * @param password FTP�������ĵ�¼����
	   * @param pathname FTP�������ı����ַ
	   * @param filename ��Ҫ���ص��ļ����ļ���
	   * @param loaclpath �����ļ��ı���Ŀ¼
	   * @return
	   */
	  public static boolean downloadFile(String ipAddr,int port,String username,String password,
	String pathname,String filename,String localpath)
	  {
		  boolean flag = false;
		  FTPClient ftpclient = new FTPClient();
		  ftpclient.setControlEncoding("utf-8");
		  
		  try {
			ftpclient.connect(ipAddr, port);//����FTP������
			ftpclient.login(username, password);//��½FTP������s
			
			int replyCode = ftpclient.getReplyCode();//��֤FTP�������Ƿ��½�ɹ���
			if(!FTPReply.isPositiveCompletion(replyCode))
			{
				System.out.println("��¼ʧ�ܣ�");
				return flag;	
			}
			
			ftpclient.changeWorkingDirectory(pathname);//�ı�FTPĿ¼��
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
			  System.out.println("�ɹ���");
		  }
		  else
		  {
			  System.out.println("ʧ�ܣ�");
		  }
	  }
}      

