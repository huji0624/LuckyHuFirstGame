package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHBallGameObject;

public class LHEdgeBox extends LHBallGameObject{

	private Body mEdgeBoxBody;

	public LHEdgeBox(World world) {
		super(world);
		this.tag = -1;
		// the box
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		mEdgeBoxBody = world.createBody(bd);
		mEdgeBoxBody.setUserData(this);

		EdgeShape shape = new EdgeShape();
		float halfW = Gdx.graphics.getWidth() / 2;
		float halfH = Gdx.graphics.getHeight() / 2;
		shape.set(-halfW, -halfH, -halfW, +halfH);
		mEdgeBoxBody.createFixture(shape, 0);

		// shape.set( - halfW, + halfH , + halfW, + halfH);
		// body.createFixture(shape, 0);

		shape.set(+halfW, +halfH, +halfW, -halfH);
		mEdgeBoxBody.createFixture(shape, 0);

		shape.set(+halfW, -halfH, -halfW, -halfH);
		mEdgeBoxBody.createFixture(shape, 0);
		shape.dispose();
	}

	public Body getBody() {
		return mEdgeBoxBody;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mWorld.destroyBody(mEdgeBoxBody);
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return 0;
	}

}
