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
 *<h1>MomentMemo<h1> 
 * The MomentMemo class will be used to create "Moment Memo" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-20 
 */
public class MomentMemo {
	/**
	 * This method is used to create a MomentMemo entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateMomentMemo(String memoTitle, 
			String memoContent, String dateOfMemo, Key couple) {
		Entity momentMemo = getMomentMemo(memoTitle, couple);
		if(momentMemo != null) {
			return false;
		}
		else {
			momentMemo = new Entity(Constant.MOMENT_MEMO, 
					memoTitle + couple.toString(), couple);
			
			momentMemo.setProperty(Constant.MOMENT_MEMO_TITLE, memoTitle);
			momentMemo.setProperty(Constant.MOMENT_MEMO, memoContent);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentMemo.setProperty(Constant.MOMENT_MEMO_DATE_OF_MEMO, dateFormat.format(date));
			momentMemo.setProperty(Constant.MOMENT_MEMO_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentMemo);
			return true;
		}
	}
	
	/**
	 * This method is used to update a MomentMemo entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateMomentMemo(String memoTitle, 
			String memoContent, String dateOfMemo, Key couple) {
		Entity momentMemo = getMomentMemo(memoTitle, couple);
		if(momentMemo == null) {
			return false;
		}
		else {
			// momentMemo.setProperty(Constant.MOMENT_MEMO_TITLE, memoTitle);
			momentMemo.setProperty(Constant.MOMENT_MEMO, memoContent);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentMemo.setProperty(Constant.MOMENT_MEMO_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentMemo);
			return true;
		}
	}
	
	/**
	 * This method returns a MomentMemo entity based on the 
	 * eventTitle, the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return Entity This returns a MomentMemo entity based on the 
	 * 				  eventTitle, the startTime, and the couple.
	 */
	public static Entity getMomentMemo(String memoTitle, Key couple) {
		return DataStoreUtil.findEntity(getMomentMemoKey(memoTitle, couple));
	}
	
	/**
	 * This method returns an arraylist of all MomentMemo entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							MomentMemo entities.
	 */
	public static Iterable<Entity> getAllMomentMemo() {
		return DataStoreUtil.listEntities(Constant.MOMENT_MEMO, null, null);
	}
	
	/**
	 * This method returns an arraylist of all MomentMemo entities of a 
	 * particular couple.
	 * 
	 * @param couple
	 * @return Iterable<Entity> This returns an arraylist of all MomentMemo 
	 * 							entities of a particular couple.
	 */
	public static Iterable<Entity> getAllMomentMemo(Key couple) {
		return DataStoreUtil.listChildren(Constant.MOMENT_MEMO, couple);
	}
	
	/**
	 * This method checks if the MomentMemo entity based on the 
	 * eventTitle, the startTime, and the couple existed or not .
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentMemo entity exists.
	 */
	public static boolean DoesMomentMemoExist(String memoTitle, Key couple) {
		return getMomentMemo(memoTitle, couple) != null;
	}
	
	/**
	 * This method deletes a MomentMemo entity based on the eventTitle, 
	 * the startTime, and the couple. 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentMemo is deleted.
	 */
	public static boolean DeleteMomentMemo(String memoTitle, Key couple) {
		Entity momentMemo = getMomentMemo(memoTitle, couple);
		if(momentMemo == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getMomentMemoKey(memoTitle, couple));
			return true;
		}
	}
	
	/**
	 * This method returns a key of a MomentMemo entity based on 
	 * the eventTitle, the startTime, and the couple.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return Key This returns a key of a MomentMemo entity corresponding 
	 * 			   to the couple.
	 */
	public static Key getMomentMemoKey(String memoTitle, Key couple) {
		Key momentMemoKey = KeyFactory.createKey(couple, 
				Constant.MOMENT_MEMO, memoTitle + couple.toString());
		
		return momentMemoKey;
	}	
}
