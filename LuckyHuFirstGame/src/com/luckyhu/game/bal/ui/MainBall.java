package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHBallGameObject;
import com.luckyhu.game.framework.game.util.LHLogger;

public class MainBall extends LHBallGameObject{
	
	public Circle circle;
	private Body mBody;
	
	private Sprite mSprite;
	private Texture mTexture;
	
	public static MainBall mainBall = null;
	
	public MainBall(World world){
		super(world);
		this.tag = 624;
		
		circle = new Circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/15 );
		mTexture = new Texture("data/main.png");
		mSprite = new Sprite(mTexture);
		mSprite.setPosition(circle.x-circle.radius, circle.y-circle.radius);
		mSprite.setSize(circle.radius*2, circle.radius*2);
		mSprite.setOrigin(circle.radius, circle.radius);
		
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
		
		mainBall = this;
		
	}
	
	public Body getBody(){
		return mBody;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(batch, render, delta);
		
		mSprite.draw(batch);
		
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		circle.x += dx;
		circle.y += dy;
		mSprite.setPosition(circle.x-circle.radius, circle.y-circle.radius);
		
//		mSprite.setRotation(mDirection.angle()-90);	
		
		mBody.setTransform(mBody.getPosition().x + dx, mBody.getPosition().y
				+ dy, 0);
	}
	
	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		circle.x = x;
		circle.y = y;
		mSprite.setPosition(circle.x-circle.radius, circle.y-circle.radius);
		mBody.setTransform(x,y, 0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mTexture.dispose();
		mWorld.destroyBody(mBody);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return circle.y + circle.radius;
	}

}
