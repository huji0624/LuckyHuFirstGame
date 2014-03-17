package com.luckyhu.game.framework.game.util;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class PolyLinePath implements Path<Vector2> {

	public Vector2[] points;
	private float[] mLenghes;
	
	private float length;

	public PolyLinePath(Vector2[] points) {

		this.points = points;
		
		mLenghes = new float[points.length - 1];

		length=0;
		for (int i = 0, n = points.length-1; i < n; i ++) {
			float x = points[i + 1].x - points[i].x;
			float y = points[i + 1].y - points[i].y;
			float tl = (float) Math.sqrt(x * x + y * y);
			length += tl;
			mLenghes[i] = tl;
		}

	}

	public float getLength() {
		return length;
	}

	@Override
	public Vector2 valueAt(Vector2 out, float t) {
		// TODO Auto-generated method stub
		if(t>getLength()){
			out.x = points[points.length-1].x;
			out.y = points[points.length-1].y;
			return out;
		}else if(t<0){
			out.x = points[0].x;
			out.y = points[0].y;
			return out;
		}
		
		int index = 0;
		
		float segLen = 0;
		float len = 0;
		for (int i = 0; i < mLenghes.length; i++) {
			len+=mLenghes[i];
			if(len>t){
				index = i;
				if(i>0){
					t = t - (len-mLenghes[i]);
				}
				segLen = mLenghes[i];				
				break;
			}
		}
		
		Vector2 s = points[index].cpy();
		Vector2 e = points[index+1].cpy();
		
		Vector2 dire = new Vector2(e.x-s.x, e.y-s.y);
		
		s.add(dire.scl(t/segLen));
		
		out.x = s.x;
		out.y = s.y;
		
		return out;
	}

	@Override
	public float approximate(Vector2 v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float locate(Vector2 v) {
		// TODO Auto-generated method stub
		return 0;
	}

}
