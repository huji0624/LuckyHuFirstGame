package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class LHEdgeBox {

	private Body mEdgeBoxBody;

	public LHEdgeBox(World world) {
		// the box
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		mEdgeBoxBody = world.createBody(bd);

		EdgeShape shape = new EdgeShape();
		float halfW = Gdx.graphics.getWidth() / 2 - 5;
		float halfH = Gdx.graphics.getHeight() / 2 - 25;
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
	
	public Body getBody(){
		return mEdgeBoxBody;
	}

}
