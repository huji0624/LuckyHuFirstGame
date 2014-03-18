package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.ui.MainBall;

public class LHBlackHoleObject extends LHCircleObject{

	private MainBall mainBall;
	private float mess;
	
	public LHBlackHoleObject(World world, Circle circle) {
		super(world, circle,"data/bh.png");
		// TODO Auto-generated constructor stub
		mColor = Color.BLUE;
	}
	
	public LHBlackHoleObject(World world, Circle circle,MainBall mb) {
		this(world, circle);
		this.mainBall = mb;
		this.tag = -1;
		mess = circle.radius*circle.radius*circle.radius * 4 /3 * (float)(Math.PI*Math.PI);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(batch, render, delta);
		
		Vector2 ve = new Vector2(circle.x -mainBall.circle.x, circle.y - mainBall.circle.y);
		float len = ve.len();
		float a = 1/len/len;
		Vector2 nor = ve.nor();
		mainBall.moveBy( nor.x * delta * a * mess, nor.y * delta * a *mess);
	}
}
