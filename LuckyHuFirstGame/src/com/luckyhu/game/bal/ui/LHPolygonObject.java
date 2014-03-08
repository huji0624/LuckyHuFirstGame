package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.engine.LHGameObject;

public class LHPolygonObject extends LHGameObject {

	private Polygon mPolygon;
	private Body mBody;

	public LHPolygonObject(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(ShapeRenderer mSRender, float delta) {
		// TODO Auto-generated method stub
		super.render(mSRender, delta);

		float ves[] = mPolygon.getVertices();
		
		mSRender.begin(ShapeType.Filled);
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

		if (vertices.length < 6) {
			throw new RuntimeException("vertices < 6");
		}

		mPolygon = new Polygon(vertices);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(0, 0);
		mBody = mWorld.createBody(bd);

		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		mBody.createFixture(shape, 0);
		shape.dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mWorld.destroyBody(mBody);
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub

	}

}
