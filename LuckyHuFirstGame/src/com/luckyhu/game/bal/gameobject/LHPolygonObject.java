package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LHPolygonObject extends LHBallGameObject {

	private Polygon mPolygon;

	public LHPolygonObject(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public Polygon getPolygon(){
		return mPolygon;
	}
	
	@Override
	public void render(SpriteBatch batch, ShapeRenderer mSRender, float delta) {
		// TODO Auto-generated method stub
		super.render(batch, mSRender, delta);

		float ves[] = mPolygon.getTransformedVertices();
		
		mSRender.begin(ShapeType.Filled);
		mSRender.setColor(mColor);
		float x1 = ves[0];
		float y1 = ves[1];
		for (int i = 2; i < ves.length - 2; i += 2) {
			float x2 = ves[i];
			float y2 = ves[i + 1];
			float x3 = ves[i + 2];
			float y3 = ves[i + 3];
			mSRender.triangle(x1, y1, x2, y2, x3, y3);
		}
		mSRender.end();
	}

	public LHPolygonObject(World world, float vertices[]) {
		this(world);
		this.tag = -1;
		mColor = Color.ORANGE;
		
		if (vertices.length < 6) {
			throw new RuntimeException("vertices < 6");
		}

		mPolygon = new Polygon(vertices);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(0, 0);
		mBody = mWorld.createBody(bd);
		mBody.setUserData(this);

		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		FixtureDef fix = new FixtureDef();
		fix.shape = shape;
		fix.friction = 0.0f;
		mBody.createFixture(fix);
		shape.dispose();
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
		mPolygon.translate(dx, dy);
		mBody.setTransform(mPolygon.getX(), mPolygon.getY(), 0);
	}
	
	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		mPolygon.setPosition(x, y);
		mBody.setTransform(x, y, 0);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		Rectangle rect = mPolygon.getBoundingRectangle();
		return rect.getY()+rect.getHeight();
	}

}
