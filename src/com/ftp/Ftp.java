package com.ftp;

/**
 * FTP各项属性的一个封装类
 * @author SkyFreecss
 *
 */
public class Ftp {
       
	   private String ipAddr;//ftp服务器地址
	   private int port;//ftp服务器端口
	   private String username;//ftp服务器登陆账号
	   private String password;//ftp服务器登陆密码
	   private String pathname;//ftp服务器保存地址
	   
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
