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
 *<h1>MomentEvent<h1> 
 * The MomentEvent class will be used to create "MomentEvent" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-19 
 */
public class MomentEvent {
	/**
	 * This method is used to create a MomentEvent entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateMomentEvent(String eventTitle, 
			String eventDetail, String location, String dateOfEvent, 
			Key couple) {
		Entity momentEvent = getMomentEvent(eventTitle, couple);
		if(momentEvent != null) {
			return false;			
		}
		else {
			momentEvent = new Entity(Constant.MOMENT_EVENT, 
					eventTitle + couple.toString(), couple);
			
			momentEvent.setProperty(Constant.MOMENT_EVENT_TITLE, eventTitle);
			momentEvent.setProperty(Constant.MOMENT_EVENT_DETAIL, eventDetail);
			momentEvent.setProperty(Constant.MOMENT_EVENT_LOCATION, location);
			momentEvent.setProperty(Constant.MOMENT_EVENT_DATE_OF_EVENT, dateOfEvent);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentEvent.setProperty(Constant.MOMENT_EVENT_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentEvent);
			return true;
		}
	}
	
	/**
	 * This method is used to update a MomentEvent entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateMomentEvent(String eventTitle, 
			String eventDetail, String location, String dateOfEvent, 
			Key couple) {
		Entity momentEvent = getMomentEvent(eventTitle, couple);
		if(momentEvent == null) {
			return false;
		}
		else {
			// momentEvent.setProperty(Constant.MOMENT_EVENT_TITLE, eventTitle);
			momentEvent.setProperty(Constant.MOMENT_EVENT_DETAIL, eventDetail);
			momentEvent.setProperty(Constant.MOMENT_EVENT_LOCATION, location);
			momentEvent.setProperty(Constant.MOMENT_EVENT_DATE_OF_EVENT, dateOfEvent);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentEvent.setProperty(Constant.MOMENT_EVENT_LAST_UPDATE, dateFormat.format(date));
		
			DataStoreUtil.persistEntity(momentEvent);
			return true;
		}
	}
	
	/**
	 * This method returns a MomentEvent entity based on the 
	 * eventTitle, the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param couple
	 * @return Entity This returns a MomentEvent entity based on the 
	 * 				  eventTitle, the startTime, and the couple.
	 */
	public static Entity getMomentEvent(String eventTitle, Key couple) {
		return DataStoreUtil.findEntity(getMomentEventKey(eventTitle, 
				couple));
	}
	
	/**
	 * This method returns an arraylist of all MomentEvent entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							MomentEvent entities.
	 */
	public static Iterable<Entity> getAllMomentEvent() {
		return DataStoreUtil.listEntities(Constant.MOMENT_EVENT, null, null);
	}
	
	/**
	 * This method returns an arraylist of all MomentEvent entities of a 
	 * particular couple.
	 * 
	 * @param couple
	 * @return Iterable<Entity> This returns an arraylist of all MomentEvent 
	 * 							entities of a particular couple.
	 */
	public static Iterable<Entity> getAllMomentEvent(Key couple) {
		return DataStoreUtil.listChildren(Constant.MOMENT_EVENT, couple);
	}
	
	/**
	 * This method checks if the MomentEvent entity based on the 
	 * eventTitle, the startTime, and the couple existed or not .
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentEvent 
	 *         entity exists.
	 */
	public static boolean DoesMomentEventExist(String eventTitle, 
			Key couple) {
		return getMomentEvent(eventTitle, couple) != null;
	}
	
	/**
	 * This method deletes a MomentEvent entity based on the eventTitle, 
	 * the startTime, and the couple. 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentEvent is deleted.
	 */
	public static boolean DeleteMomentEvent(String eventTitle, Key couple) {
		Entity momentEvent = getMomentEvent(eventTitle, couple);
		if(momentEvent == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getMomentEventKey(eventTitle, couple));
			return true;
		}
	}
	
	/**
	 * This method returns a key of a MomentEvent entity based on the eventTitle, 
	 * the startTime, and the couple.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return Key This returns a key of a MomentEvent entity corresponding 
	 * 			   to the couple.
	 */
	public static Key getMomentEventKey(String eventTitle, Key couple) {
		Key MomentEventKey = KeyFactory.createKey(couple, 
				Constant.MOMENT_EVENT, eventTitle + couple.toString());
		
		return MomentEventKey;
	}	
}
