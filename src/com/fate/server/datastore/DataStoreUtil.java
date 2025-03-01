package com.fate.server.datastore;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class DataStoreUtil {

	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();  
	
	public static void persistEntity(Entity entity) {
	  	datastore.put(entity);
	  }

		/**
		 * Delete the entity from persistent store represented by the key
		 * @param key : key to delete the entity from the persistent store
		 */
	  public static void deleteEntity(Key key) {
	    datastore.delete(key);
	  }
	  
	  /**
	   * Delete list of entities given their keys
	   * @param keys
	   */
	  public static void deleteEntity(final List<Key> keys){
	    datastore.delete(new Iterable<Key>() {
			public Iterator<Key> iterator() {
			  return keys.iterator();
			}
	      });    
	  }

		/**
		 * Search and return the entity from datastore.
		 * @param key : key to find the entity
		 * @return  entity
		 */
	 
	  public static Entity findEntity(Key key) {
	  	try {	  
	  	  return datastore.get(key);
	  	} catch (EntityNotFoundException e) {
	  	  return null;
	  	}
	  }
	 

		/***
		 * Search entities based on search criteria
		 * @param kind
		 * @param searchBy
		 *            : Searching Criteria (Property)
		 * @param searchFor
		 *            : Searching Value (Property Value)
		 * @return List all entities of a kind from the cache or datastore (if not
		 *         in cache) with the specified properties
		 */
	  public static Iterable<Entity> listEntities(String kind, String searchBy,
				String searchFor) {
	  	Query q = new Query(kind);
	  	if (searchFor != null && !"".equals(searchFor)) {
	  		FilterOperator.EQUAL.of(searchBy, searchFor);
	  	}
	  	PreparedQuery pq = datastore.prepare(q);
	  	return pq.asIterable();
	  }
	  
	  
	  /**
	   * Search entities based on ancestor
	   * @param kind
	   * @param ancestor
	   * @return
	   */
	  public static Iterable<Entity> listChildren(String kind, Key ancestor) {
	  	Query q = new Query(kind);
	  	q.setAncestor(ancestor);
  		FilterOperator.GREATER_THAN.of(Entity.KEY_RESERVED_PROPERTY, ancestor);
	  	PreparedQuery pq = datastore.prepare(q);
	  	return pq.asIterable();
	  }
	  
	  /**
	   * 
	   * @param kind
	   * @param ancestor
	   * @return
	   */
	  public static Iterable<Entity> listChildKeys(String kind, Key ancestor) {
	  	Query q = new Query(kind);
	  	q.setAncestor(ancestor).setKeysOnly();
  		FilterOperator.GREATER_THAN.of(Entity.KEY_RESERVED_PROPERTY, ancestor);
	  	PreparedQuery pq = datastore.prepare(q);
	  	return pq.asIterable();
	  }

		/**
		 * List the entities in JSON format
		 * 
		 * @param entities  entities to return as JSON strings
		 */
	  public static String writeJSON(Iterable<Entity> entities) {
	  	StringBuilder sb = new StringBuilder();
	  	
	  	int i = 0;
	  	sb.append("{\"data\": [");
	  	for (Entity result : entities) {
	  	  Map<String, Object> properties = result.getProperties();
	  	  sb.append("{");
	  	  if (result.getKey().getName() == null)
	  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
	  	  else
	  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
	  
	  	  for (String key : properties.keySet()) {
	  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
	  	  }
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	  sb.append("},");
	  	  i++;
	  	}
	  	if (i > 0) {
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	}  
	  	sb.append("]}");
	  	return sb.toString();
	  }
	  
	  /**
		 * Retrieves Parent and Child entities into JSON String
		 * 
		 * @param entities
		 *            : List of parent entities
		 * @param childKind
		 *            : Entity type for Child
		 * @param fkName
		 *            : foreign-key to the parent in the child entity
		 * @return JSON string
		 */
	  public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName) {
	  	StringBuilder sb = new StringBuilder();
	  	int i = 0;
	  	sb.append("{\"data\": [");
	  	for (Entity result : entities) {
	  	  Map<String, Object> properties = result.getProperties();
	  	  sb.append("{");
	  	  if (result.getKey().getName() == null)
	  		sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
	  	  else
	  		sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
	  	  for (String key : properties.keySet()) {
	  		sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
	  	  }
	  	  Iterable<Entity> child = listEntities(childKind, fkName,
	  	  String.valueOf(result.getKey().getId()));
	  	  for (Entity en : child) {
	  		for (String key : en.getProperties().keySet()) {
	  		  sb.append("\"" + key + "\" : \"" + en.getProperties().get(key)+ "\",");
	  		}
	  	  }
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	  sb.append("},");
	  	  i++;
	  	}
	  	if (i > 0) {
	  	  sb.deleteCharAt(sb.lastIndexOf(","));
	  	}  
	  	sb.append("]}");
	  	return sb.toString();
	  }
	 
	  
		/**
		 * Utility method to send the error back to UI
		 * @param data
		 * @param resp
		 * @throws IOException 
		 */
	  public static String getErrorMessage(Exception ex) throws IOException{
	    return "Error:"+ex.toString();
	  }
	 
	  /**
	   * get DatastoreService instance
	   * @return DatastoreService instance
	   */
	  public static DatastoreService getDatastoreServiceInstance(){
		  return datastore;
	  }
	  
}
