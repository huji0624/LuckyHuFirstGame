package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngine;
import com.luckyhu.game.framework.game.util.LHLogger;

public class LHMainScreen implements Screen, ContactListener {

	private LHGameObjectEngine mObjectEngine;
	private ShapeRenderer mSRender;
	private MainBall mMainBall;
	private InputPainter mPainter;

	private World mWorld;
	
	private Box2DDebugRenderer debugRender;
	
	private Stage mStage;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(mStage!=null){
			mStage.act(delta);
			mStage.draw();
		}else{			
			mWorld.step(delta, 10, 10);
		}

		mPainter.render(mSRender, delta, mMainBall);

		mObjectEngine.renderObject(mSRender, delta);
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
		float halfH = 300;
		shape.set( - halfW,  - halfH ,  - halfW,  + halfH);
		body.createFixture(shape, 0);
		
		shape.set( - halfW,  + halfH ,  + halfW,  + halfH);
		body.createFixture(shape, 0);

		shape.set( + halfW,  + halfH ,  + halfW,  - halfH);
		body.createFixture(shape, 0);
		
		shape.set( + halfW,  - halfH , - halfW,  - halfH);
		body.createFixture(shape, 0);
		shape.dispose();
		
		//Debug
		mObjectEngine.addObject(new LHRectObject(mWorld, new Rectangle(400, 200, 100, 20), 1.9f));
		float ves[] = {100,100,110,180,200,200,200,100,150,50};
		mObjectEngine.addObject(new LHPolygonObject(mWorld, ves));
	}
	
	private void gameOver(){
		mStage = new Stage();
		Gdx.input.setInputProcessor(mStage);
		
		Group uiGroup = new Group();
		uiGroup.setColor(Color.RED);
		uiGroup.setBounds(mStage.getWidth()*2, 0, mStage.getWidth(), mStage.getHeight());
		
		mStage.addActor(uiGroup);
		MoveToAction action = new MoveToAction();
		action.setDuration(1);
		action.setPosition(0, 0);
		uiGroup.addAction(action);
		
		TextButtonStyle style = new TextButton.TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.GREEN;
		TextButton playButton = new TextButton("Click To Play", style);
		playButton.setTouchable(Touchable.enabled);
		playButton.setPosition(uiGroup.getWidth()/2-playButton.getWidth()/2, uiGroup.getHeight()/2-playButton.getHeight()/2);
		playButton.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				LHGame.setCurrentSceen(new LHMainScreen());
			}
		});
		uiGroup.addActor(playButton);
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
		LHLogger.logD("preSolve happen");
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		LHLogger.logD("postSolve happen");
	}

}
