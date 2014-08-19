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

/**
 *<h1>User<h1> 
 * The User class will be used to create "User" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-08 
 */
public class User {
	/**
	 * This method is used to create an User entity.
	 * 
	 * @param username This is user's username
	 * @param password  This is user's password(8 digits long)
	 * @param phoneNumber This is user's phone number
	 * @param preference This is user's dating preference
	 * @param aboutMe This is the introduction of the user
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateUser(String username, String password, 
			String phoneNumber, String movie, String sport, 
			String reading, String pet, String cooking, String aboutMe) {
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
			user.setProperty(Constant.USER_SIGN_UP_DATE, dateFormat.format(date));
			user.setProperty(Constant.USER_LAST_UPDATE, dateFormat.format(date));
		
			user.setProperty(Constant.PASSWORD, password);
			user.setProperty(Constant.PHONE_NUMBER, phoneNumber);
			
			// preferences
			user.setProperty(Constant.MOVIE, movie);
			user.setProperty(Constant.SPORT, sport);
			user.setProperty(Constant.READING, reading);
			user.setProperty(Constant.PET, pet);
			user.setProperty(Constant.COOKING, cooking);
			
			user.setProperty(Constant.ABOUT_ME, aboutMe);
		
			DataStoreUtil.persistEntity(user);
			return true;
		}
	}
	
	/**
	 * This method is used to update an User entity.
	 * 
	 * @param username This is user's username
	 * @param password  This is user's password(8 digits long)
	 * @param phoneNumber This is user's phone number
	 * @param preference This is user's dating preference
	 * @param aboutMe This is the introduction of the user
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateUser(String username, String password, 
			String phoneNumber, String movie, String sport, 
			String reading, String pet, String cooking, String aboutMe) {
		Entity user = getUser(username);
		if(user == null) {
			return false;
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			user.setProperty(Constant.USER_LAST_UPDATE, dateFormat.format(date));
		
			user.setProperty(Constant.PASSWORD, password);
			user.setProperty(Constant.PHONE_NUMBER, phoneNumber);
			
			// preferences
			user.setProperty(Constant.MOVIE, movie);
			user.setProperty(Constant.SPORT, sport);
			user.setProperty(Constant.READING, reading);
			user.setProperty(Constant.PET, pet);
			user.setProperty(Constant.COOKING, cooking);
			
			user.setProperty(Constant.ABOUT_ME, aboutMe);
		
			DataStoreUtil.persistEntity(user);
			return true;
		}
	}
	
	/**
	 * This method returns an User entity based on the username.
	 * 
	 * @param username This is user's username
	 * @return Entity This returns an User entity based on the username.
	 */
	public static Entity getUser(String username) {
		return DataStoreUtil.findEntity(getUserKey(username));
	}
	
	/**
	 * This method returns an arraylist of all User entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							User entities.
	 */
	public static Iterable<Entity> getAllUsers(){
		return DataStoreUtil.listEntities(Constant.USER, null, null);
	}
	
	/**
	 * This method verify user's account validity.
	 * 
	 * @param username This is user's username
	 * @param password  This is user's password(8 digits long)
	 * @return boolean This returns whether the verification passes or not.
	 */
	public static boolean verifyUser(String username, String password){
		Entity user = getUser(username);
		if(user == null) {
			return false;
		}
		String p = user.getProperty(Constant.PASSWORD).toString();
		return password.equals(p);
	}
	
	/**
	 * This method checks if the User entity based on the username existed or not .
	 * 
	 * @param username This is user's username
	 * @return boolean This returns whether or not the User entity exists.
	 */
	public static boolean DoesUserExist(String username){
		return getUser(username) != null;
	}
	
	/**
	 * This method deletes an User entity based on the username.
	 * 
	 * @param username This is user's username
	 * @return boolean This returns whether or not the User is deleted.
	 */
	public static boolean deleteUser(String username) {
		if(User.getUser(username) != null) {
			DataStoreUtil.deleteEntity(getUserKey(username));
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method returns a key based on the user name.
	 * 
	 * @param username This is user's username 
	 * @return Key This returns a key of an User entity corresponding to the username.
	 */
	public static Key getUserKey(String username) {
		Key userKey = KeyFactory.createKey(Constant.USER, username);
		
		return userKey;
	}
	
	/**
	 * This method returns .
	 * 
	 * @param 
	 * @param
	 * @param
	 * @return .
	 */
	public static List<Entity> getForcedMatch(String username){
		Query q = new Query(Constant.FORCED_MATCH).setAncestor(getUserKey(username));
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}
	
	/**
	 * This method returns .
	 * 
	 * @param 
	 * @param
	 * @param
	 * @return .
	 */
	public static List<Entity> getRegIds(String username){
		Query q = new Query(Constant.GCM_ID_KEY).setAncestor(getUserKey(username));
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}
	
	/**
	 * This method returns .
	 * 
	 * @param 
	 * @param
	 * @param
	 * @return .
	 */
	public static List<Entity> getOperationHistory(String username, Date lastUpdate){
		Query q = new Query(Constant.OPERATION_HISTROTY_KEY)
					.setAncestor(getUserKey(username)).setFilter(
						new Query.FilterPredicate(
						Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, lastUpdate.getTime()));
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}
	
	/**
	 * This method returns .
	 * 
	 * @param 
	 * @param
	 * @param
	 * @return .
	 */
	public static List<Entity> getOperationHistory(String username){
		Key key = KeyFactory.createKey(Constant.USER, username);
		Query q = new Query(Constant.OPERATION_HISTROTY_KEY)
					.setAncestor(key);
		PreparedQuery pq = DataStoreUtil.getDatastoreServiceInstance().prepare(q);
		List<Entity> results =  pq.asList(FetchOptions.Builder.withDefaults());
		return results;
	}
}
