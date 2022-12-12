/**
 * 
 */
package com.common;

import java.io.Serializable;
import java.util.Date;

public class UserInfoBean implements Serializable{

	private int qq;               //QQ号
	private String pwd;           //密码
	private String sign;          //签名
	private int photoID;          //头像ID
	private String nickname;      //昵称
	private String sex;           //性别
	private String birthday;        //出生日期
	private String telephone;     //电话
	private String email;         //电子邮件
	private String address;       //地址
	
	private boolean status;       //是否登录
	private String  subGroupName; //分组信息
	private String  groupName;    //群信息
	private String  IP;           //IP
	private int     PORT;         //端口
	
	public UserInfoBean(){}

	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getPhotoID() {
		return photoID;
	}

	public void setPhotoID(int photoID) {
		this.photoID = photoID;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int PORT) {
		this.PORT = PORT;
	}
}
