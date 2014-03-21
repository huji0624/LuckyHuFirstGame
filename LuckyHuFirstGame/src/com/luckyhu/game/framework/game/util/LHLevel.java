package com.luckyhu.game.framework.game.util;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHRectObject;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.svg.level.reader.SvgLevelReaderHandler;
import com.svg.level.reader.entity.Svg;
import com.svg.level.reader.entity.SvgCircle;
import com.svg.level.reader.entity.SvgPath;
import com.svg.level.reader.entity.SvgRect;

public class LHLevel implements SvgLevelReaderHandler{
	
	public Vector2 size;
	public ArrayList<LHGameObject> objects = new ArrayList<LHGameObject>();
	
	private World mWorld;
	
	public LHLevel(World world){
		mWorld = world;
	}
	
	@Override
	public void handleSvg(Svg svg) {
		// TODO Auto-generated method stub
		size = new Vector2(svg.width, svg.height);
	}
	
	@Override
	public void handleRect(SvgRect rect) {
		// TODO Auto-generated method stub
		Rectangle rt = new Rectangle(rect.x, rect.y, rect.width, rect.height);
		
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
		
		LHRectObject ro = new LHRectObject(mWorld, rt, getRotation(matrix), av);
		
		objects.add(ro);
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

	@Override
	public void handleCircle(SvgCircle circle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePath(SvgPath path) {
		// TODO Auto-generated method stub
		
	}
}
