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
 *<h1>MomentText<h1> 
 * The MomentText class will be used to create "Moment Text" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-20 
 */
public class MomentText {
	/**
	 * This method is used to create a MomentText entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateMomentText(String textTitle, 
			String textContent, String dateOfText, Key couple) {
		Entity momentText = getMomentText(textTitle, couple);
		if(momentText != null) {
			return false;			
		}
		else {
			momentText = new Entity(Constant.MOMENT_TEXT, 
					textTitle + couple.toString(), couple);
			
			momentText.setProperty(Constant.MOMENT_TEXT_TITLE, textTitle);
			momentText.setProperty(Constant.MOMENT_TEXT, textContent);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentText.setProperty(Constant.MOMENT_TEXT_DATE_OF_MEMO, dateFormat.format(date));
			momentText.setProperty(Constant.MOMENT_TEXT_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentText);
			return true;
		}
	}
	
	/**
	 * This method is used to update a MomentText entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateMomentText(String textTitle, 
			String textContent, String dateOfText, Key couple) {
		Entity momentText = getMomentText(textTitle, couple);
		if(momentText == null) {
			return false;
		}
		else {
			// momentText.setProperty(Constant.MOMENT_Text_TITLE, textTitle);
			momentText.setProperty(Constant.MOMENT_TEXT, textContent);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentText.setProperty(Constant.MOMENT_TEXT_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentText);
			return true;
		}
	}
	
	/**
	 * This method returns a MomentText entity based on the 
	 * eventTitle, the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return Entity This returns a MomentText entity based on the 
	 * 				  eventTitle, the startTime, and the couple.
	 */
	public static Entity getMomentText(String textTitle, Key couple) {
		return DataStoreUtil.findEntity(getMomentTextKey(textTitle, couple));
	}
	
	/**
	 * This method returns an arraylist of all MomentText entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							MomentText entities.
	 */
	public static Iterable<Entity> getAllMomentText() {
		return DataStoreUtil.listEntities(Constant.MOMENT_TEXT, null, null);
	}
	
	/**
	 * This method returns an arraylist of all MomentText entities of a 
	 * particular couple.
	 * 
	 * @param couple
	 * @return Iterable<Entity> This returns an arraylist of all MomentText 
	 * 							entities of a particular couple.
	 */
	public static Iterable<Entity> getAllMomentText(Key couple) {
		return DataStoreUtil.listChildren(Constant.MOMENT_TEXT, couple);
	}
	
	/**
	 * This method checks if the MomentText entity based on the 
	 * eventTitle, the startTime, and the couple existed or not .
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentText entity exists.
	 */
	public static boolean DoesMomentTextExist(String textTitle, Key couple) {
		return getMomentText(textTitle, couple) != null;
	}
	
	/**
	 * This method deletes a MomentText entity based on the eventTitle, 
	 * the startTime, and the couple. 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentText is deleted.
	 */
	public static boolean DeleteMomentText(String textTitle, Key couple) {
		Entity momentText = getMomentText(textTitle, couple);
		if(momentText == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getMomentTextKey(textTitle, couple));
			return true;
		}
	}
	
	/**
	 * This method returns a key of a MomentText entity based on 
	 * the eventTitle, the startTime, and the couple.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return Key This returns a key of a MomentText entity corresponding 
	 * 			   to the couple.
	 */
	public static Key getMomentTextKey(String textTitle, Key couple) {
		Key momentTextKey = KeyFactory.createKey(couple, 
				Constant.MOMENT_TEXT, textTitle + couple.toString());
		
		return momentTextKey;
	}	
}
