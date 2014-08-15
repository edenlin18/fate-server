package com.fate.server.datastore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class BlackList {
	
	public static boolean CreateBlackList(String blacker, String blackee) {
		Entity blackList = getBlackList(blacker, blackee);
		if(User.getUser(blacker) == null || User.getUser(blackee) == null || blackList != null) {
			return false;			
		}
		else {
			Key blackerKey = KeyFactory.createKey(Constant.USER, blacker);
			blackList = new Entity(Constant.BLACK_LIST, blackee, blackerKey);
			
			blackList.setProperty(Constant.BLACKER, blacker);
			blackList.setProperty(Constant.BLACKEE, blackee);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			blackList.setProperty(Constant.BLACK_LIST_DATE, dateFormat.format(date));
				
			DataStoreUtil.persistEntity(blackList);
			return true;
		}
	}
	
	public static Entity getBlackList(String blacker, String blackee) {
		return DataStoreUtil.findEntity(getBlackListKey(blacker, blackee));
	}
	
	public static Iterable<Entity> getAllBlackLists(){
		return DataStoreUtil.listEntities(Constant.BLACK_LIST, null, null);
	}
	
	public static boolean DoesBlackListExist(String blacker, String blackee){
		return getBlackList(blacker, blackee) != null;
	}
	
	public static boolean deleteBlackList(String blacker, String blackee) {
		if(getBlackList(blacker, blackee) != null) {
			DataStoreUtil.deleteEntity(getBlackListKey(blacker, blackee));
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Key getBlackListKey(String blacker, String blackee) {
		Key blackerKey = KeyFactory.createKey(Constant.USER, blacker);
		Key blackListKey = KeyFactory.createKey(blackerKey, Constant.BLACK_LIST, blackee);
		
		return blackListKey;
	}
}
