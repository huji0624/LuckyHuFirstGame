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
import com.luckyhu.game.framework.game.util.LHLogger;

public class MainBall extends LHGameObject{
	
	public Circle circle;
	private Vector2 mDirection;
	private Body mBody;
//	private float mSpeed = 400;
	
	public MainBall(World world){
		super(world);
		circle = new Circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/20 );
		mDirection = new Vector2(-1, 1);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(circle.x, circle.y);
		mBody = world.createBody(bodyDef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(circle.radius);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.restitution =1.0f;
		fd.friction = 0.0f;
		mBody.createFixture(fd);
		
		shape.dispose();
		
		Vector2 force = new Vector2(0, 50f);
		mBody.applyLinearImpulse(force, bodyDef.position,true);
		LHLogger.logD("bvo:"+mBody.getLinearVelocity().len());
	}
	
	public Body getBody(){
		return mBody;
	}
	
	public float getPositionY(){
		return this.circle.y;
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
		
		mBody.setTransform(mBody.getPosition().x + dx, mBody.getPosition().y
				+ dy, 0);
	}
	
	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		circle.x = x;
		circle.y = y;
		mBody.setTransform(x,y, 0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mWorld.destroyBody(mBody);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return circle.y + circle.radius;
	}

}
