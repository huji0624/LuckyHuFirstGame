package com.luckyhu.game.framework.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.svg.level.reader.SvgLevelReader;


public class LHLevelLoader{
	
	public static World world;
	
	private static LHLevelLoader _instance;
	
	private SvgLevelReader svgLoader = new SvgLevelReader();
	
	public static LHLevelLoader instance(){
		if(_instance == null){
			_instance = new LHLevelLoader();
		}
		return _instance;
	}
	
	public void initLevel(String path){
		FileHandle fh = Gdx.files.internal(path);
		svgLoader.initLevel(fh.read(),path);
	}
	
	public LHLevel loadLevel(String path){

		LHLevel ll = new LHLevel(world);
		svgLoader.loadLevel(ll, path);
		return ll;
	}

}
