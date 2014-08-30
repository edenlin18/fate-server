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
 *<h1>MomentVideo<h1> 
 * The MomentVideo class will be used to create "Moment Video" entity.
 *
 * @author  Eden Lin
 * @version 1.0
 * @since   2014-08-20 
 */
public class MomentVideo {
	/**
	 * This method is used to create a MomentVideo entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of an entity creation.
	 */
	public static boolean CreateMomentVideo(String videoTitle, 
			String videoDescription, String videoURL, 
			String dateOfVideo, Key couple) {
		Entity momentVideo = getMomentVideo(videoTitle, couple);
		if(momentVideo != null) {
			return false;			
		}
		else {
			momentVideo = new Entity(Constant.MOMENT_VIDEO, 
					videoTitle + couple.toString(), couple);
			
			momentVideo.setProperty(Constant.MOMENT_VIDEO_TITLE, videoTitle);
			momentVideo.setProperty(Constant.MOMENT_VIDEO_DESCRIPTION, videoDescription);
			momentVideo.setProperty(Constant.MOMENT_VIDEO_URL, videoURL);
			momentVideo.setProperty(Constant.MOMENT_VIDEO_DATE_OF_VIDEO, dateOfVideo);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentVideo.setProperty(Constant.MOMENT_VIDEO_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentVideo);
			return true;
		}
	}
	
	/**
	 * This method is used to update a MomentVideo entity.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns success or failure of entity update.
	 */
	public static boolean UpdateMomentVideo(String videoTitle, 
			String videoDescription, String videoURL, 
			String dateOfVideo, Key couple) {
		Entity momentVideo = getMomentVideo(videoTitle, couple);
		if(momentVideo == null) {
			return false;			
		}
		else {
			// momentVideo.setProperty(Constant.MOMENT_VIDEO_TITLE, videoTitle);
			momentVideo.setProperty(Constant.MOMENT_VIDEO_DESCRIPTION, videoDescription);
			momentVideo.setProperty(Constant.MOMENT_VIDEO_URL, videoURL);
			momentVideo.setProperty(Constant.MOMENT_VIDEO_DATE_OF_VIDEO, dateOfVideo);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			momentVideo.setProperty(Constant.MOMENT_VIDEO_LAST_UPDATE, dateFormat.format(date));
	
			DataStoreUtil.persistEntity(momentVideo);
			return true;
		}
	}
	
	/**
	 * This method returns a MomentVideo entity based on the 
	 * eventTitle, the startTime, and the couple.
	 * 
	 * @param eventTitle
	 * @param startTime
	 * @param couple
	 * @return Entity This returns a MomentVideo entity based on the 
	 * 				  eventTitle, the startTime, and the couple.
	 */
	public static Entity getMomentVideo(String videoTitle, Key couple) {
		return DataStoreUtil.findEntity(getMomentVideoKey(videoTitle, 
				couple));
	}
	
	/**
	 * This method returns an arraylist of all MomentVideo entities.
	 * 
	 * @return Iterable<Entity> This returns an arraylist of all 
	 * 							MomentVideo entities.
	 */
	public static Iterable<Entity> getAllMomentVideo() {
		return DataStoreUtil.listEntities(Constant.MOMENT_VIDEO, null, null);
	}
	
	/**
	 * This method returns an arraylist of all MomentVideo entities of a 
	 * particular couple.
	 * 
	 * @param couple
	 * @return Iterable<Entity> This returns an arraylist of all MomentVideo 
	 * 							entities of a particular couple.
	 */
	public static Iterable<Entity> getAllMomentVideo(Key couple) {
		return DataStoreUtil.listChildren(Constant.MOMENT_VIDEO, couple);
	}
	
	/**
	 * This method checks if the MomentVideo entity based on the 
	 * eventTitle, the startTime, and the couple existed or not .
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentVideo entity exists.
	 */
	public static boolean DoesMomentVideoExist(String videoTitle, Key couple) {
		return getMomentVideo(videoTitle, couple) != null;
	}
	
	/**
	 * This method deletes a MomentVideo entity based on the eventTitle, 
	 * the startTime, and the couple. 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return boolean This returns whether or not the MomentVideo is deleted.
	 */
	public static boolean DeleteMomentVideo(String videoTitle, Key couple) {
		Entity momentVideo = getMomentVideo(videoTitle, couple);
		if(momentVideo == null) {
			return false;
		}
		else {
			DataStoreUtil.deleteEntity(getMomentVideoKey(videoTitle, couple));
			return true;
		}
	}
	
	/**
	 * This method returns a key of a MomentVideo entity based on 
	 * the eventTitle, the startTime, and the couple.
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @return Key This returns a key of a MomentVideo entity corresponding 
	 * 			   to the couple.
	 */
	public static Key getMomentVideoKey(String videoTitle, Key couple) {
		Key momentVideoKey = KeyFactory.createKey(couple, 
				Constant.MOMENT_VIDEO, videoTitle + couple.toString());
		
		return momentVideoKey;
	}	
}
