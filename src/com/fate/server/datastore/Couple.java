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
 *<h1>Couple<h1> 
 * The Couple class will be used to create "Couple" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-18 
 */
public class Couple {
	/**
	 * This method is used to create a Couple entity.
	 * 
	 * @param couple1 This is user one who is having a relationship with user two
	 * @param couple2 This is user two who is having a relationship with user one
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateCouple(String couple1, String couple2) {
		Entity couple = getCouple(couple1, couple2);
		if(couple != null) {
			return false;			
		}
		else {
			couple = new Entity(Constant.COUPLE, couple1 + couple2);
			
			couple.setProperty(Constant.COUPLE_1, couple1);
			couple.setProperty(Constant.COUPLE_2, couple2);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			couple.setProperty(Constant.COUPLE_TOGETHER_SINCE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(couple);
			return true;
		}
	}
	
	/**
	 * This method returns a Couple entity based on the couple1 and 
	 * the couple2.
	 * 
	 * @param couple1 This is user one who is having a relationship with user two
	 * @param couple2 This is user two who is having a relationship with user one
	 * @return Entity This returns a Couple entity based on the 
	 * 				  couple1 and the couple2.
	 */
	public static Entity getCouple(String couple1, String couple2) {
		if(User.getUser(couple1) != null && User.getUser(couple2) != null) {
			return DataStoreUtil.findEntity(getCoupleKey(couple1, couple2));
		}
		else {
			return null;
		}
	}
	
	/**
	 * This method returns an arraylist of all Couple entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							Couple entities.
	 */
	public static Iterable<Entity> getAllFordedMatches() {
		return DataStoreUtil.listEntities(Constant.COUPLE, null, null);
	}
	
	/**
	 * This method returns an arraylist of all Couple entities of a 
	 * particular user.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all Couple 
	 * 							entities of a particular user.
	 */
	public static Iterable<Entity> getAllForcedMatches(String username) {
		Iterable<Entity> e1 = DataStoreUtil.listEntities(
				Constant.COUPLE, Constant.COUPLE_1, username);
		Iterable<Entity> e2 = DataStoreUtil.listEntities(
				Constant.COUPLE, Constant.COUPLE_2, username);
		Iterable<Entity> all = Iterables.unmodifiableIterable(
				Iterables.concat(e1, e2));
		return all;
	}
	
	/**
	 * This method checks if the Couple entity based on the couple1 and the 
	 * couple2 existed or not .
	 * 
	 * @param couple1 This is user one who is having a relationship with user two
	 * @param couple2 This is user two who is having a relationship with user one
	 * @return boolean This returns whether or not the Couple entity exists.
	 */
	public static boolean DoesForcedMatchExist(String couple1, 
			String couple2) {
		return getCouple(couple1, couple2) != null;
	}
	
	/**
	 * This method deletes a Couple entity based on the couple1 
	 * and the couple2 
	 * 
	 * @param couple1 This is user one who is having a relationship with user two
	 * @param couple2 This is user two who is having a relationship with user one
	 * @return boolean This returns whether or not the Couple is deleted.
	 */
	public static boolean DeleteCouple(String couple1, String couple2) {
		Entity couple = getCouple(couple1, couple2);
		if(couple == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getCoupleKey(couple1, couple2));
			return true;
		}
	}
	
	/**
	 * This method returns a key based on the couple1 and the couple2.
	 * 
	 * @param couple1 This is user one who is having a relationship with user two
	 * @param couple2 This is user two who is having a relationship with user one
	 * @return Key This returns a key of a Couple entity corresponding 
	 * 			   to the couple1 and the couple2.
	 */
	public static Key getCoupleKey(String couple1, String couple2) {
		Key CoupleKey = KeyFactory.createKey(Constant.COUPLE, couple1 + couple2);
		
		return CoupleKey;
	}	
}
