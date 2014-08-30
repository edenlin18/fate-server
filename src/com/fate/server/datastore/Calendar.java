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
 *<h1>Calendar<h1> 
 * The Calendar class will be used to create "Calendar" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-18 
 */
public class Calendar {
	/**
	 * This method is used to create a Calendar entity.
	 * 
	 * @param eventTitle
	 * @param eventDetail
	 * @param location
	 * @param startTime
	 * @param endTime
	 * @param reminder
	 * @param repeat
	 * @param alertSelfAtTime
	 * @param alertPartnerAtTime
	 * @param couple
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateCalendar(String eventTitle, 
			String eventDetail, String location, String startTime, 
			String endTime, String reminder, String repeat, 
			String alertSelfAtTime, String alertPartnerAtTime, 
			Key couple) {
		Entity calendar = getCalendar(eventTitle, startTime, couple);
		if(calendar != null) {
			return false;			
		}
		else {
			calendar = new Entity(Constant.CALENDAR, eventTitle + startTime, couple);
			
			calendar.setProperty(Constant.CALENDAR_EVENT_TITLE, eventTitle);
			calendar.setProperty(Constant.CALENDAR_EVENT_DETAIL, eventDetail);
			calendar.setProperty(Constant.CALENDAR_LOCATION, location);
			calendar.setProperty(Constant.CALENDAR_START_TIME, startTime);
			calendar.setProperty(Constant.CALENDAR_END_TIME, endTime);
			calendar.setProperty(Constant.CALENDAR_REMINDER, reminder);
			calendar.setProperty(Constant.CALENDAR_REPEAT, repeat);
			calendar.setProperty(Constant.CALENDAR_ALERT_SELF_AT_TIME, alertSelfAtTime);
			calendar.setProperty(Constant.CALENDAR_ALERT_PARTNER_AT_TIME, alertPartnerAtTime);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			calendar.setProperty(Constant.CALENDAR_EVENT_CREATION_DATE, dateFormat.format(date));
			calendar.setProperty(Constant.CALENDAR_EVENT_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(calendar);
			return true;
		}
	}
	
	/**
	 * This method is used to update a Calendar entity.
	 * 
	 * @param eventTitle
	 * @param eventDetail
	 * @param location
	 * @param startTime
	 * @param endTime
	 * @param reminder
	 * @param repeat
	 * @param alertSelfAtTime
	 * @param alertPartnerAtTime
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateCalendar(String eventTitle, 
			String eventDetail, String location, String startTime, 
			String endTime, String reminder, String repeat, 
			String alertSelfAtTime, String alertPartnerAtTime, 
			Key couple) {
		Entity calendar = getCalendar(eventTitle, startTime, couple);
		if(calendar == null) {
			return false;
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			calendar.setProperty(Constant.CALENDAR_EVENT_LAST_UPDATE, dateFormat.format(date));
		
			calendar.setProperty(Constant.CALENDAR_EVENT_TITLE, eventTitle);
			calendar.setProperty(Constant.CALENDAR_EVENT_DETAIL, eventDetail);
			calendar.setProperty(Constant.CALENDAR_LOCATION, location);
			calendar.setProperty(Constant.CALENDAR_START_TIME, startTime);
			calendar.setProperty(Constant.CALENDAR_END_TIME, endTime);
			calendar.setProperty(Constant.CALENDAR_REMINDER, reminder);
			calendar.setProperty(Constant.CALENDAR_REPEAT, repeat);
			calendar.setProperty(Constant.CALENDAR_ALERT_SELF_AT_TIME, alertSelfAtTime);
			calendar.setProperty(Constant.CALENDAR_ALERT_PARTNER_AT_TIME, alertPartnerAtTime);
	
			DataStoreUtil.persistEntity(calendar);
			return true;
		}
	}
	
	/**
	 * This method returns a Calendar entity based on the 
	 * eventTitle, the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return Entity This returns a Calendar entity based on the 
	 * 				  eventTitle, the startTime, and the couple.
	 */
	public static Entity getCalendar(String eventTitle, String startTime, Key couple) {
		return DataStoreUtil.findEntity(getCalendarKey(eventTitle, startTime, couple));
	}
	
	/**
	 * This method returns an arraylist of all Calendar entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							Calendar entities.
	 */
	public static Iterable<Entity> getAllCalendar() {
		return DataStoreUtil.listEntities(Constant.CALENDAR, null, null);
	}
	
	/**
	 * This method returns an arraylist of all Calendar entities of a 
	 * particular couple.
	 * 
	 * @param couple
	 * @return Iterable<Entity> This returns an arraylist of all Calendar 
	 * 							entities of a particular couple.
	 */
	public static Iterable<Entity> getAllCalendar(Key couple) {
		return DataStoreUtil.listChildren(Constant.CALENDAR, couple);
	}
	
	/**
	 * This method checks if the Calendar entity based on the 
	 * eventTitle, the startTime, and the couple existed or not .
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return boolean This returns whether or not the Calendar entity exists.
	 */
	public static boolean DoesCalendarExist(String eventTitle, 
			String startTime, Key couple) {
		return getCalendar(eventTitle, startTime, couple) != null;
	}
	
	/**
	 * This method deletes a Calendar entity based on the eventTitle, 
	 * the startTime, and the couple. 
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return boolean This returns whether or not the Calendar is deleted.
	 */
	public static boolean DeleteCalendar(String eventTitle, 
			String startTime, Key couple) {
		Entity canlendar = getCalendar(eventTitle, startTime, couple);
		if(canlendar == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getCalendarKey(eventTitle, 
					startTime, couple));
			return true;
		}
	}
	
	/**
	 * This method returns a key of a Calendar entity based on the eventTitle, 
	 * the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return Key This returns a key of a Calendar entity corresponding 
	 * 			   to the couple.
	 */
	public static Key getCalendarKey(String eventTitle, String startTime, Key couple) {
		Key CalendarKey = KeyFactory.createKey(couple, Constant.CALENDAR, eventTitle + startTime);
		
		return CalendarKey;
	}	
}
