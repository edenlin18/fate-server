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
		Entity forced_match = getForcedMatch(username1, username2);
		if(forced_match != null) {
			return false;			
		}
		else {
			forced_match = new Entity(Constant.FORCED_MATCH_KEY, username1 + username2);
			forced_match.setProperty(Constant.FORCED_MATCH_KEY, username1 + username2);
			forced_match.setProperty(Constant.USERNAME_1, username1);
			forced_match.setProperty(Constant.USERNAME_2, username2);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			forced_match.setProperty(Constant.MATCH_DATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(forced_match);
			return true;
		}
	}
	
	public static boolean DeleteForcedMatch(String username1, String username2) {
		Entity forced_match = getForcedMatch(username1, username2);
		if(forced_match == null) {
			return false;
		}
		else {
			Key key = KeyFactory.createKey(Constant.FORCED_MATCH_KEY, username1 + username2);
			DataStoreUtil.deleteEntity(key);
			return true;
		}
	}
	
	public static Entity getForcedMatch (String username1, String username2) {
		Key key = KeyFactory.createKey(Constant.FORCED_MATCH_KEY, username1 + username2);
		return DataStoreUtil.findEntity(key);
	}
	
	public static Iterable<Entity> getAllFordedMatches(){
		return DataStoreUtil.listEntities(Constant.FORCED_MATCH_KEY, null, null);
	}
	
	public static boolean DoesForcedMatchExist(String username1, String username2) {
		return getForcedMatch(username1, username2) != null;
	}
	
}
