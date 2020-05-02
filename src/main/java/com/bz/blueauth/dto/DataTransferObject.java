package com.bz.blueauth.dto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

public class DataTransferObject {

    public Map toHashMap(){
		//Class<?> c = this.getClass();
		Map map = new HashMap();
		Object instance = this;
		ReflectionUtils.doWithFields(this.getClass(), field -> {
            //Method met = ReflectionUtils.findMethod(instance.getClass(), "get"+field.getName());
            field.setAccessible(true);
            map.put(field.getName(), field.get(instance));            
		});
		return map;
	}

	public Map omit(List<String> fieldNames){
		Map allMaps = this.toHashMap();
		Map resMap = new HashMap();
		Iterator<String> keys  = allMaps.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			if(!fieldNames.contains(key)){
				resMap.put(key, allMaps.get(key));
			}
		}		
		return resMap;
	}

	public Map pick(List<String> fieldNames){
		Map allMaps = this.toHashMap();
		Map resMap = new HashMap();
		Iterator<String> fields  = fieldNames.iterator();
		while( fields.hasNext() ){
			String field = fields.next();
			resMap.put(field, allMaps.get(field));			
		}
		return resMap;
	}
}