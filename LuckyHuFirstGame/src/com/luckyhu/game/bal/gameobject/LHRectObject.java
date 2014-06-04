package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LHRectObject extends LHBallGameObject {

	private Rectangle mRect;
	private float mAngle;
	private float mAngularVelocity = 0.0f;
	
	private Sprite mSprite;
	private Texture mTexture;

	public LHRectObject(World world, Rectangle rect, float angle,
			float angularVelocity) {
		super(world);
		this.tag = -1;
		mColor = Color.RED;
		
		mAngle = angle;
		mAngularVelocity = angularVelocity;
		mRect = new Rectangle(rect);

		BodyDef bd = new BodyDef();
		if(mAngularVelocity==0.0f){
			bd.type = BodyType.StaticBody;
		}else{			
			bd.type = BodyType.KinematicBody;
		}

		Vector2 center = new Vector2();
		mRect.getCenter(center);
		bd.position.set(center);
		bd.angularVelocity = mAngularVelocity;

		mBody = mWorld.createBody(bd);
		mBody.setUserData(this);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(mRect.width / 2, mRect.height / 2, new Vector2(0, 0),
				angle);
		mBody.createFixture(shape, 0);
		shape.dispose();
		
		mTexture = new Texture("data/rect.png");
		mSprite = new Sprite(mTexture);
		mSprite.setBounds(rect.x, rect.y, rect.width, rect.height);
		mSprite.setOrigin(rect.width/2, rect.height/2);
	}

	public LHRectObject(World world, Rectangle rect, float angle) {
		// TODO Auto-generated constructor stub
		this(world, rect, angle, 0.0f);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		batch.begin();
		mAngle += mAngularVelocity * delta;
		mSprite.setRotation((float) Math.toDegrees(mAngle));
		mSprite.draw(batch);
		batch.end();
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
		mRect.x += dx;
		mRect.y += dy;
		mSprite.translate(dx, dy);
		mBody.setTransform(mBody.getPosition().x + dx, mBody.getPosition().y
				+ dy, 0);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return (float) (mRect.y + mRect.width);
	}

}
