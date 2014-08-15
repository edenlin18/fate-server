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

public class ForcedMatch {
	
	public static boolean CreateForcedMatch(String username1, String username2) {
		Entity forcedMatch = getForcedMatch(username1, username2);
		if(forcedMatch != null) {
			return false;			
		}
		else {
			//Key forcedMatchKey = KeyFactory.createKey(Constant.FORCED_MATCH_KEY, username1 + username2);
			forcedMatch = new Entity(Constant.FORCED_MATCH, username1 + username2);
			
			forcedMatch.setProperty(Constant.USERNAME_1, username1);
			forcedMatch.setProperty(Constant.USERNAME_2, username2);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			forcedMatch.setProperty(Constant.MATCH_DATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(forcedMatch);
			return true;
		}
	}
	
	public static Entity getForcedMatch (String username1, String username2) {
		if(User.getUser(username1) != null && User.getUser(username2) != null) {
			return DataStoreUtil.findEntity(getForcedMatchKey(username1, username2));
		}
		else {
			return null;
		}
	}
	
	public static Iterable<Entity> getAllFordedMatches(){
		return DataStoreUtil.listEntities(Constant.FORCED_MATCH_KEY, null, null);
	}
	
	public static boolean DoesForcedMatchExist(String username1, String username2) {
		return getForcedMatch(username1, username2) != null;
	}
	
	public static boolean DeleteForcedMatch(String username1, String username2) {
		Entity forcedMatch = getForcedMatch(username1, username2);
		if(forcedMatch == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getForcedMatchKey(username1, username2));
			return true;
		}
	}
	
	public static Key getForcedMatchKey(String username1, String username2) {
		Key forcedMatchKey = KeyFactory.createKey(Constant.FORCED_MATCH, username1 + username2);
		
		return forcedMatchKey;
	}	
}
