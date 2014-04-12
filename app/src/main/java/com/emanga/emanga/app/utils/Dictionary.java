package com.emanga.emanga.app.utils;

import com.emanga.emanga.app.models.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Some repositories like es.mangahere.com has not well translations, so a dictionary is needed for mapping correctly
 * English => Spanish 
 */
public class Dictionary {
	public static final Map<String, String> categories;
    static
    {
        categories = new HashMap<String, String>();
        categories.put("action", "acción");
        categories.put("mystery", "misterio");
        categories.put("scifi", "ciencia ficción");
        categories.put("sliceoflife", "vida cotidiana");
        categories.put("schoollife", "escolar");
        categories.put("comedy", "comedia");
        categories.put("fantasy", "fantasía");
    }
    
    public static HashMap<String, Genre> convertList(List<Genre> categories){
    	HashMap<String, Genre> hashMap = new HashMap<String, Genre>();
    	
    	for(Genre c : categories){
    		System.out.println("::" + c.name + "::");
    		hashMap.put(c.name, c);
    	}
    	
    	return hashMap;
    }
}