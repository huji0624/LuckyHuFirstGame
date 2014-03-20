package com.luckyhu.game.framework.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHRectObject;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.svg.level.reader.SvgLevel;
import com.svg.level.reader.SvgLevelReader;
import com.svg.level.reader.SvgLevelReaderHandler;
import com.svg.level.reader.entity.SvgRect;


public class LHLevelLoader implements SvgLevelReaderHandler<LHGameObject>{
	
	public static World world;
	
	private static LHLevelLoader _instance;
	
	private SvgLevelReader<LHGameObject> svgLoader = new SvgLevelReader<LHGameObject>(this);
	
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
		FileHandle fh = Gdx.files.internal(path);
		SvgLevel<LHGameObject> sl = svgLoader.loadLevel(fh.path());
		LHLevel ll = new LHLevel();
		ll.objects = sl.objects;
		ll.size = new Vector2(sl.width,sl.height);
		return ll;
	}

	@Override
	public LHGameObject handleRect(SvgLevel<LHGameObject> level,SvgRect rect) {
		// TODO Auto-generated method stub
		Rectangle rt = new Rectangle(rect.x, level.height - rect.y, rect.width, rect.height);
		
		Matrix3 matrix = new Matrix3();
		if(rect.matrix!=null){			
			matrix.val[0] = rect.matrix[0];
			matrix.val[1] = rect.matrix[2];
			matrix.val[2] = rect.matrix[4];
			matrix.val[3] = rect.matrix[1];
			matrix.val[4] = rect.matrix[3];
			matrix.val[5] = rect.matrix[5];
			matrix.val[6] = 0;
			matrix.val[7] = 0;
			matrix.val[8] = 1;
		}
		
//		 Vector2 tran = getTranslation(matrix);
//		 LHLogger.logD(""+matrix);
		
		float av = 0;
		if(rect.desc!=null){
			String speed = rect.desc.get("speed");
			if(speed!=null){
				av = Float.valueOf(speed);
			}
		}
		
		LHRectObject ro = new LHRectObject(world, rt, getRotation(matrix), av);
		
		return ro;
	}

	public float getRotation (Matrix3 matrix) {
		return MathUtils.radiansToDegrees * (float)Math.atan2(matrix.val[Matrix3.M10], matrix.val[Matrix3.M00]);
	}
	
	public Vector2 getTranslation (Matrix3 matrix) {
		Vector2 position = new Vector2();
		position.x = matrix.val[Matrix3.M02];
		position.y = matrix.val[Matrix3.M12];
		return position;
	}
}
