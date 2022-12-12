/**
 * 
 */
package com.server.business.uiManager;

import com.common.UserInfoBean;
import com.server.data.Query;


public class UserInfoManager {

	String tableName = "UserInfo";
	public boolean getIsExistUser(String id,String pwd){
		Query query = new Query();
		return query.isExistUser(tableName,id,pwd);
	}
	
	public UserInfoBean getUserInfo(String id,String pwd){
		Query query = new Query();
		return query.getUserInfo(tableName,id,pwd);
	}
	
	public void getFriendsInfo(String tableName,String id){
		Query query = new Query();
		query.getFriendsInfo(tableName,id);
	}
}
