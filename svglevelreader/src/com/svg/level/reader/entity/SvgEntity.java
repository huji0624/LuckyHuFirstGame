package com.svg.level.reader.entity;

import java.util.HashMap;

import com.svg.level.reader.SvgEntityCacheMap;

public class SvgEntity {
	public String id;
	
	public float x;
	public float y;
	public float translateX;
	public float translateY;
	
	public HashMap<String, String> desc;
	
	public SvgEntityCacheMap map;
}
