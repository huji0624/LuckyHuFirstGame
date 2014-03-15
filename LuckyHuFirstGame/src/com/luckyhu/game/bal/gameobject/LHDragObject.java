package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.ui.MainBall;

public class LHDragObject extends LHPolygonObject{

	private MainBall mainBall;
	private float mess;
	
	public LHDragObject(World world, float[] vertices) {
		super(world, vertices);
		// TODO Auto-generated constructor stub
		this.tag = -1;
	}
	
	public LHDragObject(World world, float[] vertices,MainBall mainBall) {
		this(world, vertices);
		// TODO Auto-generated constructor stub
		this.mainBall = mainBall;
		mColor = Color.PINK;
		
		mess = 100000000/Math.abs(getPolygon().area());
	}

	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		Rectangle rect = getPolygon().getBoundingRectangle();
		Vector2 center = new Vector2();
		center = rect.getCenter(center);
		Vector2 ve = new Vector2(mainBall.circle.x - center.x,  mainBall.circle.y - center.y);
		float len = ve.len();
		float a = 1/len/len;
		Vector2 nor = ve.nor();
		moveBy( nor.x * delta * a * mess, nor.y * delta * a *mess);
		
		super.render(render, delta);
	}
}
