package com.ftp;

/**
 * FTP�������Ե�һ����װ��
 * @author SkyFreecss
 *
 */
public class Ftp {
       
	   private String ipAddr;//ftp��������ַ
	   private int port;//ftp�������˿�
	   private String username;//ftp��������½�˺�
	   private String password;//ftp��������½����
	   private String pathname;//ftp�����������ַ
	   
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPathname() {
		return pathname;
	}
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	   
}
