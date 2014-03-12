package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.ui.MainBall;

public class LHWhiteHoleObject extends LHCircleObject{
	
	private MainBall mainBall;
	private float mess;

	public LHWhiteHoleObject(World world, Circle circle) {
		super(world, circle);
		// TODO Auto-generated constructor stub
		mColor = Color.ORANGE;
	}
	
	public LHWhiteHoleObject(World world, Circle circle,MainBall mb) {
		this(world, circle);
		this.mainBall = mb;
		this.tag = -1;
		mess = circle.radius*circle.radius*circle.radius * 4 /3 * (float)(Math.PI*Math.PI);
	}

	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(render, delta);
		
		Vector2 ve = new Vector2(mainBall.circle.x - circle.x, mainBall.circle.y - circle.y);
		float len = ve.len();
		float a = 1/len/len;
		Vector2 nor = ve.nor();
		mainBall.moveBy( nor.x * delta * a * mess, nor.y * delta * a *mess);
	}

}
