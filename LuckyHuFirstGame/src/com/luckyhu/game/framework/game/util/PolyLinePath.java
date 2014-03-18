package com.luckyhu.game.framework.game.util;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PolyLinePath implements Path<Vector2> {

	public Array<Vector2> points;
	private Array<Float> mLenghes;

	private float length;

	public PolyLinePath(Vector2[] points) {
		Array<Vector2> pa = new Array<Vector2>(points);
		init(pa);
	}

	public PolyLinePath(Array<Vector2> points) {
		init(points);
	}
	
	public PolyLinePath(){
		Array<Vector2> pa = new Array<Vector2>();
		init(pa);
	}

	private void init(Array<Vector2> points) {
		this.points = points;

		mLenghes = new Array<Float>();

		length = 0;
		for (int i = 0, n = points.size - 1; i < n; i++) {
			float x = points.get(i + 1).x - points.get(i).x;
			float y = points.get(i + 1).y - points.get(i).y;
			float tl = (float) Math.sqrt(x * x + y * y);
			length += tl;
			mLenghes.add(tl);
		}
	}

	public float getLength() {
		return length;
	}
	
	public PolyLinePath add(Vector2 point){
		if(points.size==0){
			points.add(point);
		}else{
			Vector2 last = points.peek();
			Vector2 dis = new Vector2(point.x-last.x, point.y-last.y);
			float len = dis.len();
			length += len;
			mLenghes.add(len);
			points.add(point);
		}
		return this;
	}
	
	public void clear(){
		points.clear();
		mLenghes.clear();
		length=0;
	}

	@Override
	public Vector2 valueAt(Vector2 out, float t) {
		// TODO Auto-generated method stub
		if (t > getLength()) {
			out.x = points.peek().x;
			out.y = points.peek().y;
			return out;
		} else if (t <= 0) {
			out.x = points.first().x;
			out.y = points.first().y;
			return out;
		}

		int index = 0;

		float segLen = 0;
		float len = 0;
		for (int i = 0; i < mLenghes.size; i++) {
			len += mLenghes.get(i);
			if (len > t) {
				index = i;
				if (i > 0) {
					t = t - (len - mLenghes.get(i));
				}
				segLen = mLenghes.get(i);
				break;
			}
		}

		Vector2 s = points.get(index).cpy();
		Vector2 e = points.get(index + 1).cpy();

		Vector2 dire = new Vector2(e.x - s.x, e.y - s.y);

		s.add(dire.scl(t / segLen));

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
