package com.luckyhu.game.framework.game.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class LHGameCache {
	
	private static HashMap<String, Texture> textureCache = new HashMap<String, Texture>();
	private static HashMap<String, Sound> soundCache = new HashMap<String, Sound>();
	private static HashMap<String, Music> musicCache = new HashMap<String, Music>();
	
	public static Texture loadTexture(String path){
		if(textureCache.containsKey(path)){
			return textureCache.get(path);
		}else{
			Texture texture = new Texture(path);
			textureCache.put(path, texture);
			return texture;
		}
	}
	
	public static void initSound(String path){
		if(soundCache.containsKey(path)){
			return;
		}
		
		Sound newSound = Gdx.audio.newSound(Gdx.files.internal(path));
		soundCache.put(path, newSound);
	}
	
	public static Sound loadSound(String path){
		initSound(path);
		return soundCache.get(path);
	}
	
	public static void initMusic(String path){
		if(musicCache.containsKey(path)){
			return;
		}
		Music newMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
		musicCache.put(path, newMusic);
	}
	
	public static Music loadMusic(String path){
		initMusic(path);
		return musicCache.get(path);
	}
}
