package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.engine.LHGameObject;

public class MainBall extends LHGameObject{
	
	public Circle circle;
	private Vector2 mDirection;
	private Body mBody;
	private float mSpeed = 50;
	
	public MainBall(World world){
		circle = new Circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 20);
		mDirection = new Vector2(-1, 1);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(circle.x, circle.y);
		
		mBody = world.createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(circle.radius);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.friction = 0;
		fd.restitution = 1.0f;
		mBody.createFixture(fd);
		
		shape.dispose();
		
		Vector2 force = new Vector2(1000f, 1000f);
		mBody.applyLinearImpulse(force, bodyDef.position,true);
	}
	
	public void setDirection(float x,float y){
		mDirection.x = x;
		mDirection.y = y;
	}
	
	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(render, delta);
		
		circle.setPosition(mBody.getPosition());
		
		render.begin(ShapeType.Filled);
		render.setColor(Color.WHITE);
		render.circle(circle.x, circle.y, circle.radius);
		render.end();
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		circle.x += dx;
		circle.y += dy;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
