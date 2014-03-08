package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class InputPainter extends InputAdapter {

	private Vector2 mStartP;
	private Vector2 mEndP;
	private Body mBody;
	private World mWolrd;

	static private float Box_Height = 10;

	public InputPainter(World world) {
		this.mWolrd = world;

		Gdx.input.setInputProcessor(this);
	}

	public void render(ShapeRenderer render, float delta, MainBall ball) {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			mStartP = new Vector2(Gdx.input.getX(), getTouchY());
			mEndP = new Vector2(mStartP);
		}

		if (Gdx.input.isTouched()) {
			mEndP = new Vector2(Gdx.input.getX(), getTouchY());
		}

		if (mStartP != null) {
			render.begin(ShapeType.Filled);
			Vector2 an = new Vector2(mEndP.x-mStartP.x, mEndP.y-mStartP.y);
			render.rect(mStartP.x, mStartP.y,mStartP.dst(mEndP),
					Box_Height, 0, 0, an.angle());
			render.end();
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub

		if (mBody != null)
			mWolrd.destroyBody(mBody);
		
		if(mEndP.equals(mStartP)){
			return false;
		}
		
		Vector2 an = new Vector2(mEndP.x-mStartP.x, mEndP.y-mStartP.y);
		float anR = an.angle();
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		Vector2 center = new Vector2((mEndP.x + mStartP.x) / 2,	 (mEndP.y + mStartP.y) / 2);
		an.setAngle(anR+90);
		center.add(an.nor().scl(Box_Height/2));
		bd.position.set(center);

		mBody = mWolrd.createBody(bd);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(mStartP.dst(mEndP)/2, Box_Height / 2,
				new Vector2(0, 0),(float)Math.toRadians(anR));
		mBody.createFixture(shape, 0);
		shape.dispose();

		return true;
	}

	private float getTouchY() {
		return Gdx.graphics.getHeight() - Gdx.input.getY();
	}

}
