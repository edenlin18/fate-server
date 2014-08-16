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
import com.google.appengine.labs.repackaged.com.google.common.collect.Iterables;

/**
 *<h1>ForcedMatch<h1> 
 * The ForcedMatch class will be used to create "Forced Match" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-08 
 */
public class ForcedMatch {
	/**
	 * This method is used to create a Forced Match entity.
	 * 
	 * @param username1 This is user one who is getting matched with user two
	 * @param username2 This is user two who is getting matched with user one
	 * @return boolean This returns success or failure of an entity creation.
	 */
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
	
	/**
	 * This method returns a Forced Match entity based on the username1 and 
	 * the username2.
	 * 
	 * @param username1 This is user one who is getting matched with user two
	 * @param username2 This is user two who is getting matched with user one
	 * @return Entity This returns a Forced Match entity based on the 
	 * 				  username1 and the username2.
	 */
	public static Entity getForcedMatch (String username1, String username2) {
		if(User.getUser(username1) != null && User.getUser(username2) != null) {
			return DataStoreUtil.findEntity(getForcedMatchKey(username1, username2));
		}
		else {
			return null;
		}
	}
	
	/**
	 * This method returns an arraylist of all Forced Match entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							Forced Match entities.
	 */
	public static Iterable<Entity> getAllFordedMatches() {
		return DataStoreUtil.listEntities(Constant.FORCED_MATCH, null, null);
	}
	
	/**
	 * This method returns an arraylist of all Forced Match entities of a 
	 * particular user.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all Forced Match 
	 * 							entities of a particular user.
	 */
	public static Iterable<Entity> getAllForcedMatches(String username) {
		Iterable<Entity> e1 = DataStoreUtil.listEntities(
				Constant.FORCED_MATCH, Constant.USERNAME_1, username);
		Iterable<Entity> e2 = DataStoreUtil.listEntities(
				Constant.FORCED_MATCH, Constant.USERNAME_2, username);
		Iterable<Entity> all = Iterables.unmodifiableIterable(
				Iterables.concat(e1, e2));
		return all;
	}
	
	/**
	 * This method checks if the Forced Match entity based on the username1 and the 
	 * username2 existed or not .
	 * 
	 * @param username1 This is user one who is getting matched with user two
	 * @param username2 This is user two who is getting matched with user one
	 * @return boolean This returns whether or not the Forced Match entity exists.
	 */
	public static boolean DoesForcedMatchExist(String username1, 
			String username2) {
		return getForcedMatch(username1, username2) != null;
	}
	
	/**
	 * This method deletes a Forced Match entity based on the username1 
	 * and the username2 
	 * 
	 * @param username1 This is user one who is getting matched with user two
	 * @param username2 This is user two who is getting matched with user one
	 * @return boolean This returns whether or not the Forced Match is deleted.
	 */
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
	
	/**
	 * This method returns a key based on the username1 and the username2.
	 * 
	 * @param username1 This is user one who is getting matched with user two
	 * @param username2 This is user two who is getting matched with user one
	 * @return Key This returns a key of a Forced Match entity corresponding 
	 * 			   to the username1 and the username2.
	 */
	public static Key getForcedMatchKey(String username1, String username2) {
		Key forcedMatchKey = KeyFactory.createKey(Constant.FORCED_MATCH, username1 + username2);
		
		return forcedMatchKey;
	}	
}
