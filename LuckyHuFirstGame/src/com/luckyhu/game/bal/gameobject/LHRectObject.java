package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.engine.LHGameObject;

public class LHRectObject extends LHGameObject {

	private Rectangle mRect;
	private Body mBody;
	private float mAngle;
	private float mAngularVelocity = 0.0f;

	public LHRectObject(World world, Rectangle rect, float angle,
			float angularVelocity) {
		super(world);
		this.tag = -1;
		
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
	}

	public LHRectObject(World world, Rectangle rect, float angle) {
		// TODO Auto-generated constructor stub
		this(world, rect, angle, 0.0f);
	}

	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(render, delta);

		render.begin(ShapeType.Filled);
		mAngle += mAngularVelocity * delta;
		render.rect(mRect.x, mRect.y, mRect.width, mRect.height,
				mRect.width / 2, mRect.height / 2,
				(float) Math.toDegrees(mAngle));
		render.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
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
		mBody.setTransform(mBody.getPosition().x + dx, mBody.getPosition().y
				+ dy, 0);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return (float) (mRect.y + mRect.width * Math.sin(mAngle));
	}

}
