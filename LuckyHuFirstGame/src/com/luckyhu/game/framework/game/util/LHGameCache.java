package com.luckyhu.game.framework.game.util;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class LHGameCache {
	
	private static HashMap<String, Texture> textureCache = new HashMap<String, Texture>();;
	
	public static Texture loadTexture(String path){
		if(textureCache.containsKey(path)){
			return textureCache.get(path);
		}else{
			Texture texture = new Texture(path);
			textureCache.put(path, texture);
			return texture;
		}
	}
}
