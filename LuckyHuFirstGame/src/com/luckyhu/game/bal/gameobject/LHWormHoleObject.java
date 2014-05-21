package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class LHWormHoleObject extends LHBallGameObject{

	private Circle circleA;
	private Circle circleB;
	
	private Fixture mFixtureA;
	private Fixture mFixtureB;
	
	private Sprite mSpriteA;
	private Sprite mSpriteB;
	
	private Texture mTexture;
	
	public LHWormHoleObject(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}
	
	public LHWormHoleObject(World world,Circle A,Circle B){
		this(world);
		this.tag = 1;
		mColor = Color.GREEN;
		
		circleA = A;
		circleB = B;
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(0, 0);
		
		mBody = mWorld.createBody(bd);
		mBody.setUserData(this);
		
		CircleShape shape = new CircleShape();
		shape.setPosition(new Vector2(A.x, A.y));
		shape.setRadius(A.radius);
		
		FixtureDef fd = new FixtureDef();
		fd.isSensor=true;
		fd.density=0.0f;
		fd.friction=0.0f;
		fd.restitution=0.0f;
		fd.shape=shape;
		mFixtureA=mBody.createFixture(fd);
		mFixtureA.setUserData(this);
		shape.dispose();
		
		shape = new CircleShape();
		shape.setPosition(new Vector2(B.x, B.y));
		shape.setRadius(B.radius);
		
		fd = new FixtureDef();
		fd.isSensor=true;
		fd.density=0.0f;
		fd.friction=0.0f;
		fd.restitution=0.0f;
		fd.shape=shape;
		mFixtureB=mBody.createFixture(fd);
		mFixtureB.setUserData(this);
		shape.dispose();
		
		mTexture = new Texture("data/wormhole.png");
		mSpriteA = new Sprite(mTexture);
		mSpriteB = new Sprite(mTexture);
		mSpriteA.setPosition(circleA.x-circleA.radius, circleA.y-circleA.radius);
		mSpriteA.setSize(circleA.radius*2, circleA.radius*2);
		mSpriteB.setPosition(circleB.x-circleB.radius, circleB.y-circleB.radius);
		mSpriteB.setSize(circleB.radius*2, circleB.radius*2);
	}
	
	public Vector2 getOtherFixTurePosition(Circle circle){
		if (circleA.contains(circle)) {
			return new Vector2(circleB.x, circleB.y);
		}else if(circleB.contains(circle)){
			return new Vector2(circleA.x, circleA.y);
		}
		return null;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(batch, render, delta);
		
//		render.begin(ShapeType.Filled);
//		render.setColor(mColor);
//		render.circle(circleA.x, circleA.y, circleA.radius);
//		render.circle(circleB.x, circleB.y, circleB.radius);
//		render.end();
		mSpriteA.draw(batch);
		mSpriteB.draw(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mTexture.dispose();
	}
	
	@Override
	public void didRemove() {
		// TODO Auto-generated method stub
		super.didRemove();
		mWorld.destroyBody(mBody);
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		circleA.x+=dx;
		circleA.y+=dy;
		
		circleB.x+=dx;
		circleB.y+=dy;
		
		mBody.setTransform(mBody.getPosition().x + dx, mBody.getPosition().y
				+ dy, 0);
		
		mSpriteA.translate(dx, dy);
		mSpriteB.translate(dx, dy);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		float top = 0;
		if(circleA.y+circleA.radius>top){
			top = circleA.y+circleA.radius;
		}
		if(circleB.y+circleB.radius>top){
			top = circleB.y+circleB.radius;
		}
		return top;
	}

}
