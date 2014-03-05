package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngine;
import com.luckyhu.game.framework.game.util.LHLogger;

public class LHMainScreen implements Screen, ContactListener {

	private LHGameObjectEngine mObjectEngine;
	private ShapeRenderer mSRender;
	private MainBall mMainBall;
	private InputPainter mPainter;

	private World mWorld;
	
	private Box2DDebugRenderer debugRender;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		mWorld.step(delta, 10, 10);

		mPainter.render(mSRender, delta, mMainBall);

		// mObjectEngine.renderObject(null, delta);
		mMainBall.render(mSRender, delta);

		debugRender.render(mWorld, mSRender.getProjectionMatrix());
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		mObjectEngine = new LHGameObjectEngine();
		mSRender = new ShapeRenderer();

		mWorld = new World(new Vector2(0, 0), true);
		mWorld.setContactListener(this);
		debugRender = new Box2DDebugRenderer();

		mMainBall = new MainBall(mWorld);
		mPainter = new InputPainter(mWorld);
		
		//the box
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		Body body = mWorld.createBody(bd);
		
		EdgeShape shape = new EdgeShape();
		float halfW = 400;
		float halfH = 200;
		shape.set( - halfW,  - halfH ,  - halfW,  + halfH);
		body.createFixture(shape, 0);
		
		shape.set( - halfW,  + halfH ,  + halfW,  + halfH);
		body.createFixture(shape, 0);

		shape.set( + halfW,  + halfH ,  + halfW,  - halfH);
		body.createFixture(shape, 0);
		
		shape.set( + halfW,  - halfH , - halfW,  - halfH);
		body.createFixture(shape, 0);
		shape.dispose();
		
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mWorld.dispose();
		mSRender.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		LHLogger.logD("beginContact happen");
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		LHLogger.logD("endContact happen");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
//		LHLogger.logD("preSolve happen");
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		LHLogger.logD("postSolve happen");
	}

}
