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

public class User {
	
	public static boolean CreateUser(String username, String password, 
			String phone_number, String preference, String about_me) {
		Entity user = getUser(username);
		if(user != null) {
			return false;			
		}
		else {
			//Key userKey = KeyFactory.createKey(Constant.USER_KEY, username);
			user = new Entity(Constant.USER, username);
			
			user.setProperty(Constant.USERNAME, username);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			user.setProperty(Constant.SIGN_UP_DATE, dateFormat.format(date));
			user.setProperty(Constant.LAST_UPDATE, dateFormat.format(date));
		
			user.setProperty(Constant.PASSWORD, password);
			user.setProperty(Constant.PHONE_NUMBER, phone_number);
			user.setProperty(Constant.PREFERENCE, preference);
			user.setProperty(Constant.ABOUT_ME, about_me);
		
			DataStoreUtil.persistEntity(user);
			return true;
		}
	}
	
	public static boolean UpdateUser(String username, String password, 
			String phone_number, String preference, String about_me) {
		Entity user = getUser(username);
		if(user == null) {
			return false;
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			user.setProperty(Constant.LAST_UPDATE, dateFormat.format(date));
		
			user.setProperty(Constant.PASSWORD, password);
			user.setProperty(Constant.PHONE_NUMBER, phone_number);
			user.setProperty(Constant.PREFERENCE, preference);
			user.setProperty(Constant.ABOUT_ME, about_me);
		
			DataStoreUtil.persistEntity(user);
			return true;
		}
	}
	
	public static Entity getUser(String username) {
		Key key = KeyFactory.createKey(Constant.USER, username);
		return DataStoreUtil.findEntity(key);
	}
	
	public static Iterable<Entity> getAllUsers(){
		return DataStoreUtil.listEntities(Constant.USER, null, null);
	}
	
	public static boolean verifyUser(String username, String password){
		Entity user = getUser(username);
		if(user == null) {
			return false;
		}
		String p = user.getProperty(Constant.PASSWORD).toString();
		return password.equals(p);
	}
	
	public static boolean DoesUsernameExist(String username){
		return getUser(username) != null;
	}
	
	public static List<Entity> getRegIds(String username){
		Key key = KeyFactory.createKey(Constant.USER, username);
		Query q = new Query(Constant.GCM_ID_KEY).setAncestor(key);
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}
	
	public static List<Entity> getForcedMatch(String username){
		Key key = KeyFactory.createKey(Constant.USER, username);
		Query q = new Query(Constant.FORCED_MATCH).setAncestor(key);
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}

	public static List<Entity> getOperationHistory(String username, Date lastUpdate){
		Key key = KeyFactory.createKey(Constant.USER, username);
		Query q = new Query(Constant.OPERATION_HISTROTY_KEY)
					.setAncestor(key).setFilter(
						new Query.FilterPredicate(
						Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, lastUpdate.getTime()));
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}
	
	public static List<Entity> getOperationHistory(String username){
		Key key = KeyFactory.createKey(Constant.USER, username);
		Query q = new Query(Constant.OPERATION_HISTROTY_KEY)
					.setAncestor(key);
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}

}
