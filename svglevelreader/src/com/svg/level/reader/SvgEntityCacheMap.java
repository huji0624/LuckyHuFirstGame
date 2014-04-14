package com.svg.level.reader;

import java.util.ArrayList;

import com.svg.level.reader.entity.SvgCircle;
import com.svg.level.reader.entity.SvgEntity;
import com.svg.level.reader.entity.SvgPath;

public class SvgEntityCacheMap {
	
	private ArrayList<SvgEntity> objects;
	
	protected SvgEntityCacheMap(ArrayList<SvgEntity> objects){
		this.objects = objects;
	}
	
	/**
	 * get the SvgEntity in this level by id.
	 * @param id
	 * @return if no entity found,return null.
	 */
	public SvgEntity get(String id){
		for (int i = 0; i < objects.size(); i++) {
			SvgEntity en = objects.get(i);
			if(en.id!=null&&en.id.equals(id)){
				return en;
			}
		}
		return null;
	}
	
	/**
	 * get the SvgPath in this level by id.
	 * @param id
	 * @return
	 */
	public SvgPath getPath(String id){
		SvgEntity en = get(id);
		if(en instanceof SvgPath){
			return (SvgPath)en;
		}
		return null;
	}
	
	/**
	 * get the SvgCircle in this level by id.
	 * @param id
	 * @return
	 */
	public SvgCircle getCircle(String id){
		SvgEntity en = get(id);
		if(en instanceof SvgCircle){
			return (SvgCircle)en;
		}
		return null;
	}
}
