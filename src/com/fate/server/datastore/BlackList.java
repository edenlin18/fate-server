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
 *<h1>BlackList<h1> 
 * The BlackList class will be used to create "Black List" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-08 
 */
public class BlackList {
	/**
	 * This method is used to create a Black List entity.
	 * 
	 * @param blacker This is the user who is black-listing the other person
	 * @param blackee  This is the user who is getting black-listed
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateBlackList(String blacker, String blackee) {
		Entity blackList = getBlackList(blacker, blackee);
		if(User.getUser(blacker) == null || User.getUser(blackee) == null || 
				blackList != null) {
			return false;			
		}
		else {
			Key blackerKey = KeyFactory.createKey(Constant.USER, blacker);
			blackList = new Entity(Constant.BLACK_LIST, blackee, blackerKey);
			
			blackList.setProperty(Constant.BLACKER, blacker);
			blackList.setProperty(Constant.BLACKEE, blackee);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			blackList.setProperty(Constant.BLACK_LIST_DATE, 
					dateFormat.format(date));
				
			DataStoreUtil.persistEntity(blackList);
			return true;
		}
	}
	
	/**
	 * This method returns a Black List entity based on the blacker and 
	 * the blackee.
	 * 
	 * @param blacker This is the user who is black-listing the other person
	 * @param blackee  This is the user who is getting black-listed
	 * @return Entity This returns a Black List entity based on the blacker 
	 * 				  and blackee.
	 */
	public static Entity getBlackList(String blacker, String blackee) {
		return DataStoreUtil.findEntity(getBlackListKey(blacker, blackee));
	}
	
	/**
	 * This method returns an arraylist of all Black List entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all
	 * 							Black List entities.
	 */
	public static Iterable<Entity> getAllBlackLists(){
		return DataStoreUtil.listEntities(Constant.BLACK_LIST, null, null);
	}
	
	/**
	 * This method returns an arraylist of all Black List entities of 
	 * a particular user(either black or blackee).
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all Black List 
	 * 							entities of a particular user(either black 
	 * 							or blackee).
	 */
	public static Iterable<Entity> getAllBlackLists(String blacker){
		return DataStoreUtil.listEntities(Constant.BLACK_LIST, Constant.BLACKER, blacker);
	}
	
	/**
	 * This method checks if the Black List entity based on the blacker and 
	 * the blackee existed or not .
	 * 
	 * @param blacker This is the user who is black-listing the other person
	 * @param blackee  This is the user who is getting black-listed
	 * @return boolean This returns whether or not the Black List 
	 * 				   entity exists.
	 */
	public static boolean DoesBlackListExist(String blacker, String blackee){
		return getBlackList(blacker, blackee) != null;
	}
	
	/**
	 * This method deletes a Black List entity based on the blacker 
	 * and the blackee.
	 * 
	 * @param blacker This is the user who is black-listing the other person
	 * @param blackee  This is the user who is getting black-listed
	 * @return boolean This returns whether or not the Black List is deleted.
	 */
	public static boolean deleteBlackList(String blacker, String blackee) {
		if(getBlackList(blacker, blackee) != null) {
			DataStoreUtil.deleteEntity(getBlackListKey(blacker, blackee));
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method returns a key based on the blacker and the blackee.
	 * 
	 * @param blacker This is the user who is black-listing the other person
	 * @param blackee  This is the user who is getting black-listed
	 * @return Key This returns a key of a Black List entity corresponding 
	 * 			   to the blacker and the blackee.
	 */
	public static Key getBlackListKey(String blacker, String blackee) {
		Key blackerKey = KeyFactory.createKey(Constant.USER, blacker);
		Key blackListKey = KeyFactory.createKey(blackerKey, 
				Constant.BLACK_LIST, blackee);
		
		return blackListKey;
	}
}
