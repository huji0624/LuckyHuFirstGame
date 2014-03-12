package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.engine.LHGameObject;

public class MainBall extends LHGameObject{
	
	public Circle circle;
	private Body mBody;
//	private float mSpeed = 400;
	private float mOffset;
	
	public MainBall(World world){
		super(world);
		this.tag = 624;
		
		circle = new Circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/20 );
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(circle.x, circle.y);
		mBody = world.createBody(bodyDef);
		mBody.setUserData(this);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(circle.radius);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.restitution =1.0f;
		fd.friction = 0.0f;
		mBody.createFixture(fd);
		
		shape.dispose();
	}
	
	public Body getBody(){
		return mBody;
	}
	
	public void setOffset(float offset){
		mOffset = offset;
	}
	
	private float getTouchY() {
		return Gdx.graphics.getHeight() - Gdx.input.getY() + mOffset;
	}
	
	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(render, delta);
		
		handleTouch(delta);
		
		circle.setPosition(mBody.getPosition());
		
		render.begin(ShapeType.Filled);
		render.setColor(Color.WHITE);
		render.circle(circle.x, circle.y, circle.radius);
		render.end();
		
	}
	
	private float lastX = 0;
	private float lastY = 0;
	
	private void handleTouch(float delta){
		if (Gdx.input.justTouched()) {
			lastX = Gdx.input.getX();
			lastY = getTouchY();
		}else if(Gdx.input.isTouched()){
			float x = Gdx.input.getX();
			float y = getTouchY();
			float dx = x - lastX;
			float dy = y - lastY;
			
			mBody.setTransform(mBody.getPosition().x+dx, mBody.getPosition().y + dy, 0);
			
			lastX = x;
			lastY = y;
		}
		
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
