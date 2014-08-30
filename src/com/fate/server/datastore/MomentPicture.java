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
 *<h1>MomentPicture<h1> 
 * The MomentPicture class will be used to create "Moment Picture" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-20 
 */
public class MomentPicture {
	/**
	 * This method is used to create a MomentPicture entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateMomentPicture(String pictureTitle, 
			String pictureDescription, String pictureURL, 
			String dateOfPicture, Key couple) {
		Entity momentPicture = getMomentPicture(pictureTitle, couple);
		if(momentPicture != null) {
			return false;			
		}
		else {
			momentPicture = new Entity(Constant.MOMENT_PICTURE, 
					pictureTitle + couple.toString(), couple);
			
			momentPicture.setProperty(Constant.MOMENT_PICTURE_TITLE, pictureTitle);
			momentPicture.setProperty(Constant.MOMENT_PICTURE_DESCRIPTION, pictureDescription);
			momentPicture.setProperty(Constant.MOMENT_PICTURE_URL, pictureURL);
			momentPicture.setProperty(Constant.MOMENT_PICTURE_DATE_OF_PICTURE, dateOfPicture);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentPicture.setProperty(Constant.MOMENT_PICTURE_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentPicture);
			return true;
		}
	}
	
	/**
	 * This method is used to update a MomentPicture entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateMomentPicture(String pictureTitle, 
			String pictureDescription, String pictureURL, 
			String dateOfPicture, Key couple) {
		Entity momentPicture = getMomentPicture(pictureTitle, couple);
		if(momentPicture == null) {
			return false;			
		}
		else {
			// momentPicture.setProperty(Constant.MOMENT_PICTURE_TITLE, pictureTitle);
			momentPicture.setProperty(Constant.MOMENT_PICTURE_DESCRIPTION, pictureDescription);
			momentPicture.setProperty(Constant.MOMENT_PICTURE_URL, pictureURL);
			momentPicture.setProperty(Constant.MOMENT_PICTURE_DATE_OF_PICTURE, dateOfPicture);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentPicture.setProperty(Constant.MOMENT_PICTURE_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentPicture);
			return true;
		}
	}
	
	/**
	 * This method returns a MomentPicture entity based on the 
	 * eventTitle, the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return Entity This returns a MomentPicture entity based on the 
	 * 				  eventTitle, the startTime, and the couple.
	 */
	public static Entity getMomentPicture(String pictureTitle, Key couple) {
		return DataStoreUtil.findEntity(getMomentPictureKey(pictureTitle, 
				couple));
	}
	
	/**
	 * This method returns an arraylist of all MomentPicture entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							MomentPicture entities.
	 */
	public static Iterable<Entity> getAllMomentPicture() {
		return DataStoreUtil.listEntities(Constant.MOMENT_PICTURE, null, null);
	}
	
	/**
	 * This method returns an arraylist of all MomentPicture entities of a 
	 * particular couple.
	 * 
	 * @param couple
	 * @return Iterable<Entity> This returns an arraylist of all MomentPicture 
	 * 							entities of a particular couple.
	 */
	public static Iterable<Entity> getAllMomentPicture(Key couple) {
		return DataStoreUtil.listChildren(Constant.MOMENT_EVENT, couple);
	}
	
	/**
	 * This method checks if the MomentPicture entity based on the 
	 * eventTitle, the startTime, and the couple existed or not .
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentPicture entity exists.
	 */
	public static boolean DoesMomentPictureExist(String pictureTitle, Key couple) {
		return getMomentPicture(pictureTitle, couple) != null;
	}
	
	/**
	 * This method deletes a MomentPicture entity based on the eventTitle, 
	 * the startTime, and the couple. 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentPicture is deleted.
	 */
	public static boolean DeleteMomentPicture(String pictureTitle, Key couple) {
		Entity momentPicture = getMomentPicture(pictureTitle, couple);
		if(momentPicture == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getMomentPictureKey(pictureTitle, couple));
			return true;
		}
	}
	
	/**
	 * This method returns a key of a MomentPicture entity based on 
	 * the eventTitle, the startTime, and the couple.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return Key This returns a key of a MomentPicture entity corresponding 
	 * 			   to the couple.
	 */
	public static Key getMomentPictureKey(String pictureTitle, Key couple) {
		Key momentPictureKey = KeyFactory.createKey(couple, 
				Constant.MOMENT_PICTURE, pictureTitle + couple.toString());
		
		return momentPictureKey;
	}	
}
