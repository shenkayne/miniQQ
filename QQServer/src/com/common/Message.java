/**
 * 
 */
package com.common;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Date;


public class Message implements Serializable {

	private String sendIP;	
	private String receiveIP;
	private int sendPort;
	private int receivePort;
	
	private int sendQq;
	private int receiveQq;
	private String sendNickName;
	private String receiveNickName;
	private Date date;
	
	private String fontType;
	private int fontSize;
	private Color fontColor;
    private boolean isBold;
    private boolean isItatic;
    private boolean isUnderline;
    
	private String infoType;//消息类型
	private String info;
	
	private String sendsign;
	private String receivesign;
	private int sendphotoID;
	private int receivephotoID;
	
	private UserInfoBean user;
	private UserInfoBean friend;
	private ImageIcon  cut_image;
	private String  biaoqin_id;
	
	
	public Message(){}
	
	public void setSendIP(String sendIP){
		this.sendIP = sendIP;
	}
	
	public void setReceiveIP(String receiveIP){
		this.receiveIP = receiveIP;
	}
	
	public void setSendQq(int sendQq){
		this.sendQq = sendQq;
	}
	
	public void setReceiveQq(int receiveQq){
		this.receiveQq = receiveQq;
	}
	public void setSendNickName(String sendNickName){
		this.sendNickName = sendNickName;
	}
	
	public void setReceiveNickName(String receiveNickName){
		this.receiveNickName = receiveNickName;
	}
	public void setSendPort(int sendPort){
		this.sendPort = sendPort;
	}
	
	public void setReceivePort(int receivePort){
		this.receivePort = receivePort;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setFontType(String fontType){
		this.fontType = fontType;
	}
	public void setFontColor(Color fontColor){
		this.fontColor = fontColor;
	}
	
	public void setFontSize(int fontSize){
		this.fontSize = fontSize;
	}
	public void setBold(boolean isBold){
		this.isBold = isBold;
	}
	
	public void setItatic(boolean isItatic){
		this.isItatic = isItatic;
	}
	public void setUnderline(boolean isUnderline){
		this.isUnderline = isUnderline;
	}
	public void setInfoType(String infoType){
		this.infoType = infoType;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
	
	public void setsendSign(String sign){
		this.sendsign = sign;
	}
	public void setreceiveSign(String sign){
		this.receivesign = sign;
	}
	public void setsendPhotoID(int photoID){
		this.sendphotoID = photoID;
	}
	public void setreceivePhotoID(int photoID){
		this.receivephotoID = photoID;
	}
	
	public void setUserBean(UserInfoBean user){
		this.user = user;
	}
	public void setFriendBean(UserInfoBean friend){
		this.friend = friend;
	}
	public void setCutImage(ImageIcon cut_image){
		this.cut_image = cut_image;
	}
	public void setBiaoqin(String biaoqin){
		this.biaoqin_id = biaoqin;
	}
	//////////////////////////////////////////
	public String getSendIP(){
		return sendIP;
	}
	
	public String getReceiveIP(){
		return receiveIP;
	}
	
	public int getSendPort(){
		return sendPort;
	}
	
	public int getReceivePort(){
		return receivePort;
	}
	public String getInfoType(){
		return infoType;
	}
	
	public String getInfo(){
		return info;
	}
	
	public int getSendQq(){
		return sendQq;
	}
	
	public int getReceiveQq(){
		return receiveQq;
	}
	
	public String getSendNickname(){
		return sendNickName;
	}
	
	public Date getDate(){
		return date;
	}
	public String getReceiveNickname(){
		return receiveNickName;
	}
	public String getFontType(){
		return fontType;
	}
	public Color getFontColor(){
		return fontColor;
	}
	
	public int getFontSize(){
		return fontSize;
	}
	
	public boolean getIsBold(){
		return isBold;
	}
	
	public boolean getIsItatic(){
		return isItatic;
	}
	
	public boolean getIsUnderline(){
		return isUnderline;
	}
	public String getsendSign(){
		return sendsign;
	}
	public String getreceiveSign(){
		return receivesign;
	}
	public int getsendPhotoID(){
		return sendphotoID;
	}
	public int getreceivePhotoID(){
		return receivephotoID;
	}
	
	public UserInfoBean getUserBean(){
		return user;
	}
	
	public UserInfoBean getFriendBean(){
		return friend;
	}
	public ImageIcon getCutImage(){
		return this.cut_image;
	}
	public String getBiaoqin(){
		return this.biaoqin_id;
	}
}
