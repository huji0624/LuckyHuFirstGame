package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.bal.objectblocks.LHObjectBlockGenerator;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngine;
import com.luckyhu.game.framework.game.util.LHLogger;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class LHMainScreen implements Screen, ContactListener {

	private LHGameObjectEngine mObjectEngine;
	private ShapeRenderer mSRender;
	private OrthographicCamera mCamera;
	private MainBall mMainBall;
	private InputPainter mPainter;

	private World mWorld;
	
	private Box2DDebugRenderer debugRender;
	
	private Stage mStage;
	
	private float mOffset;
	private Label mLabel;
	private int currentGenBlock=0;
	private Body mEdgeBodBody;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		mSRender.setProjectionMatrix(mCamera.combined);
		GL10 gl = Gdx.app.getGraphics().getGL10(); 
        mCamera.update(); 
        mCamera.apply(gl); 
        
        mWorld.step(delta, 10, 10);

		mPainter.render(mSRender, delta, mMainBall);

		mObjectEngine.renderObject(mSRender, delta);
		mMainBall.render(mSRender, delta);
		
		mStage.act(delta);
		mStage.draw();
		
		if(mMainBall.getPositionY()-mOffset>400){
			mOffset += delta*500;
			mLabel.setText(""+((int)mOffset));
			
			int pg =(int) (mOffset/mStage.getHeight());
			if(pg>=currentGenBlock){
				genBlock();
			}
			
			if(mOffset>mCamera.position.y){
				mCamera.position.y+=100*delta;
				mEdgeBodBody.setTransform(Gdx.graphics.getWidth()/2, mEdgeBodBody.getPosition().y+100*delta, 0);
			}
			
			mPainter.setOffset(mOffset);
		}
		
		debugRender.render(mWorld, mSRender.getProjectionMatrix());
		
	}
	
	private void genBlock(){
		 try {
			Class<LHObjectBlockGenerator> onwClass = (Class<LHObjectBlockGenerator>) Class.forName("com.luckyhu.game.bal.objectblocks.LHOBG"+currentGenBlock);
			LHObjectBlockGenerator gen = (LHObjectBlockGenerator) onwClass.newInstance();
			Array<LHGameObject> array = gen.generate(mWorld, mStage.getWidth(), mStage.getHeight());
			for (LHGameObject lhGameObject : array) {
				lhGameObject.moveBy(0, currentGenBlock*mStage.getHeight());
			}
			mObjectEngine.addObjects(array);
			currentGenBlock++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		mCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mCamera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,0);

		mWorld = new World(new Vector2(0, 0), true);
		mWorld.setContactListener(this);
		debugRender = new Box2DDebugRenderer();

		mMainBall = new MainBall(mWorld);
		mPainter = new InputPainter(mWorld);
		mPainter.mainBall = mMainBall;
		
		//the box
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		mEdgeBodBody = mWorld.createBody(bd);
		
		EdgeShape shape = new EdgeShape();
		float halfW = Gdx.graphics.getWidth()/2-5;
		float halfH = Gdx.graphics.getHeight()/2-25;
		shape.set( - halfW,  - halfH ,  - halfW,  + halfH);
		mEdgeBodBody.createFixture(shape, 0);
		
//		shape.set( - halfW,  + halfH ,  + halfW,  + halfH);
//		body.createFixture(shape, 0);

		shape.set( + halfW,  + halfH ,  + halfW,  - halfH);
		mEdgeBodBody.createFixture(shape, 0);
		
		shape.set( + halfW,  - halfH , - halfW,  - halfH);
		mEdgeBodBody.createFixture(shape, 0);
		shape.dispose();
		
		mStage = new Stage();
		mLabel = new Label("", new LabelStyle(new BitmapFont(),
				Color.WHITE));
		mLabel.setPosition(mStage.getWidth() - 50, mStage.getHeight()-mLabel.getHeight()-10);
		mStage.addActor(mLabel);
		
		currentGenBlock++;
		genBlock();

	}
	
	private void gameOver(){
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
		mWorld.destroyBody(mEdgeBodBody);
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
