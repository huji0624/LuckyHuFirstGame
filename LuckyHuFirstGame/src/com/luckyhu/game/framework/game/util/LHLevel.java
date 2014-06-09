package com.luckyhu.game.framework.game.util;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHBlackHoleObject;
import com.luckyhu.game.bal.gameobject.LHCircleMoveObject;
import com.luckyhu.game.bal.gameobject.LHPolygonObject;
import com.luckyhu.game.bal.gameobject.LHRectObject;
import com.luckyhu.game.bal.gameobject.LHWhiteHoleObject;
import com.luckyhu.game.bal.gameobject.LHWormHoleObject;
import com.luckyhu.game.bal.ui.MainBall;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.svg.level.reader.SvgLevelReaderHandler;
import com.svg.level.reader.entity.Svg;
import com.svg.level.reader.entity.SvgCircle;
import com.svg.level.reader.entity.SvgPath;
import com.svg.level.reader.entity.SvgPolygon;
import com.svg.level.reader.entity.SvgRect;

public class LHLevel implements SvgLevelReaderHandler{
	
	public Vector2 size;
	public ArrayList<LHGameObject> objects = new ArrayList<LHGameObject>();
	
	private World mWorld;
	private float ratio=1.0f;
	
	public LHLevel(World world){
		mWorld = world;
	}
	
	private float rv(float value){
		return value*ratio;
	}
	
	@Override
	public void handleSvg(Svg svg) {
		// TODO Auto-generated method stub
		ratio = Gdx.graphics.getWidth() / svg.width;
		LHLogger.logD("ratio:"+ratio);
		size = new Vector2(svg.width, rv(svg.height));
	}
	
	@Override
	public void handleRect(SvgRect rect) {
		// TODO Auto-generated method stub
		Rectangle rt = new Rectangle(rv(rect.x), rv(size.y-rect.y-rect.height), rv(rect.width), rv(rect.height));
		
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
		if(circle.desc==null){
			return;
		}
		String type = circle.desc.get("type");
		if (type==null) {
			return;
		}
		
		float cx = rv(circle.x);
		float cy = rv(size.y - circle.y);
		float r = rv(circle.r);
		if (type.equals("w")) {
			LHWhiteHoleObject wh = new LHWhiteHoleObject(mWorld, new Circle(cx, cy, r),MainBall.mainBall);
			objects.add(wh);
		}else if(type.equals("b")){
			LHBlackHoleObject bh = new LHBlackHoleObject(mWorld, new Circle(cx, cy, r),MainBall.mainBall);
			objects.add(bh);
		}else if (type.equals("m")) {
			float speed = 10;
			String sp = circle.desc.get("speed");
			if(sp!=null){
				speed = Float.valueOf(sp);
			}
			boolean reverse = true;
			SvgPath spath = circle.map.getPath(circle.desc.get("path"));
			Vector2 path[] = new Vector2[spath.d.length/2];
			for (int i = 0; i < path.length; i++) {
				path[i] = new Vector2(rv(spath.d[i*2]), rv(size.y - spath.d[i*2+1]));
			}
			if (circle.desc.get("reverse").equals("false")) {
				reverse = false;
			}
			LHCircleMoveObject mv = new LHCircleMoveObject(mWorld, new Circle(cx, cy, r), speed, path, reverse);
			objects.add(mv);
		}else if (type.equals("t")) {
			Circle A = new Circle(cx, cy, r);
			SvgCircle b = circle.map.getCircle(circle.desc.get("t"));
			Circle B = new Circle(rv(b.x), rv(size.y - b.y), rv(b.r));
			LHWormHoleObject rh = new LHWormHoleObject(mWorld, A, B);
			objects.add(rh);
		}
	}

	@Override
	public void handlePath(SvgPath path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePolygon(SvgPolygon polygon) {
		// TODO Auto-generated method stub
		float points[] = polygon.points.clone();
		for (int i = 0; i < points.length; i++) {
			if (i%2==1) {
				points[i]=size.y - points[i];
			}else{
				points[i]=rv(points[i]);
			}
		}
		
		LHPolygonObject po = new LHPolygonObject(mWorld, points);
		
		objects.add(po);
	}
}
