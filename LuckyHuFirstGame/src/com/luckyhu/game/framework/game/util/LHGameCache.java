package com.luckyhu.game.framework.game.util;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Application.ApplicationType;
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
	
	private static String transformAudio(String name){
		if(Gdx.app.getType().equals(ApplicationType.iOS)){
			return "audio/"+name+".aac";
		}else{
			return "audio/"+name+".ogg";
		}
	}
	
	public static void initSound(String path){
		if(soundCache.containsKey(path)){
			return;
		}
		path = transformAudio(path);
		Sound newSound = Gdx.audio.newSound(Gdx.files.internal(path));
		soundCache.put(path, newSound);
	}
	
	public static Sound loadSound(String path){
		path = transformAudio(path);
		initSound(path);
		return soundCache.get(path);
	}
	
	public static void initMusic(String path){
		if(musicCache.containsKey(path)){
			return;
		}
		path = transformAudio(path);
		Music newMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
		musicCache.put(path, newMusic);
	}
	
	public static Music loadMusic(String path){
		path = transformAudio(path);
		initMusic(path);
		return musicCache.get(path);
	}
	
	public static void unLoadAll(){
		Set<String> st = musicCache.keySet();
		for (String string : st) {
			Music music = musicCache.get(string);
			music.stop();
			music.dispose();
		}
		musicCache.clear();
		
		st = textureCache.keySet();
		for (String string : st) {
			Texture t = textureCache.get(string);
			t.dispose();
		}
		textureCache.clear();
		
		st = soundCache.keySet();
		for (String string : st) {
			Sound s = soundCache.get(string);
			s.dispose();
		}
		soundCache.clear();
	}
}
