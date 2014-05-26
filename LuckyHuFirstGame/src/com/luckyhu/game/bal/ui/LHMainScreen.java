package com.luckyhu.game.bal.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.bal.gameobject.LHBallGameObject;
import com.luckyhu.game.bal.gameobject.LHWormHoleObject;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngine;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngineListener;
import com.luckyhu.game.framework.game.engine.LHMapEngine;
import com.luckyhu.game.framework.game.util.LHActionQueue;
import com.luckyhu.game.framework.game.util.LHGameCache;
import com.luckyhu.game.framework.game.util.LHLevel;
import com.luckyhu.game.framework.game.util.LHLevelLoader;
import com.luckyhu.game.framework.game.util.LHLogger;

public class LHMainScreen extends InputAdapter implements Screen,
		ContactListener, LHGameObjectEngineListener, MainBallDelegate {

	private LHMapEngine mMapEngine;
	private SpriteBatch mBatch;

	private LHGameObjectEngine mObjectEngine;
	private ShapeRenderer mSRender;
	private OrthographicCamera mCamera;
	private MainBall mMainBall;

	private World mWorld;

	private Box2DDebugRenderer debugRender;

	private Stage mStage;
	private GamePlayPanel mPanel;

	private float mOffset;
	private Label mLabel;
	private float mBlockTop = 0;

	private LHEdgeBox mEdgeBox;

	private LHActionQueue mQueue = new LHActionQueue();

	private boolean gameOver = false;
	private int maxDis = 0;
	
	private Image mGuideImage;
	
	static final float BOX_STEP=1/120f;
	static final int  BOX_VELOCITY_ITERATIONS=8;
	static final int BOX_POSITION_ITERATIONS=10;
	float accumulator;

	public void updateWorld(float dt){
	   accumulator+=dt;
	    while(accumulator>BOX_STEP){
	      mWorld.step(BOX_STEP,BOX_VELOCITY_ITERATIONS,BOX_POSITION_ITERATIONS);
	      accumulator-=BOX_STEP;
	   }
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		mSRender.setProjectionMatrix(mCamera.combined);
		mBatch.setProjectionMatrix(mCamera.combined);
		// GL10 gl = Gdx.app.getGraphics().getGL10();
		mCamera.update();
		// mCamera.apply(gl);

		if (!gameOver) {
			updateWorld(delta);
		}

		mQueue.runAll();

		mBatch.begin();
		mMapEngine.render(mBatch, delta, mOffset);
		mObjectEngine.renderObject(mBatch, mSRender, delta);
		mBatch.end();
		mMainBall.render(mBatch, mSRender, delta);

		if (!gameOver&&mGuideImage==null)
			moveViewPort(delta);

//		debugRender.render(mWorld, mSRender.getProjectionMatrix());

		if (maxDis < mMainBall.getTop()) {
			maxDis = (int) mMainBall.getTop();
			mLabel.setText("" + maxDis);
		}
		mStage.act(delta);
		mStage.draw();
	}

	private float viewPortSpeed = 30;
	
	private void moveViewPort(float delta) {

		float dis = viewPortSpeed * delta;
		mOffset += dis;
		mCamera.position.y += dis;
		mEdgeBox.getBody().setTransform(Gdx.graphics.getWidth() / 2,
				mEdgeBox.getBody().getPosition().y + dis, 0);

		genBlock();
	}

	private int blockNumber = 1;
	private static final int MaxBlock = 12;

	private void genBlock() {
		if (blockNumber==2) {
			return;
		}
//		while (mBlockTop - mOffset < mStage.getHeight() * 2) {

			// new level
			LHLevel level = LHLevelLoader.instance().loadLevel("level/leveld.svg");
//			LHLevel level = LHLevelLoader.instance().loadLevel("level/level"+blockNumber+".svg");
			ArrayList<LHGameObject> array = level.objects;
			for (LHGameObject lhGameObject : array) {
				lhGameObject.moveBy(0, mBlockTop);
			}
			mObjectEngine.addObjects(array);
			mBlockTop += level.size.y;
			blockNumber++;
			if (blockNumber >= MaxBlock){				
				blockNumber = 1;
				viewPortSpeed = viewPortSpeed * 1.1f;
			}
//		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		mObjectEngine = new LHGameObjectEngine(this);
		mMapEngine = new LHMapEngine();
		mBatch = new SpriteBatch();

		mSRender = new ShapeRenderer();
		mCamera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		mCamera.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		mWorld = new World(new Vector2(0, 0), true);
		mWorld.setContactListener(this);
		debugRender = new Box2DDebugRenderer();

		mMainBall = new MainBall(mWorld);
		mMainBall.setMainBallDelegate(this);

		mEdgeBox = new LHEdgeBox(mWorld);

		mStage = new Stage();
		mLabel = new Label("", new LabelStyle(new BitmapFont(), Color.WHITE));
		mLabel.setPosition(mStage.getWidth() - 50,
				mStage.getHeight() - mLabel.getHeight() - 10);
		mStage.addActor(mLabel);
		
		Image image = new Image(LHGameCache.loadTexture("data/guide.png"));
		image.setPosition(mStage.getWidth()/2-image.getWidth()/2, mStage.getHeight()/2-image.getHeight()/2);
		mStage.addActor(image);
		mGuideImage = image;

		Gdx.input.setInputProcessor(this);

		LHLevelLoader.world = mWorld;
//		for (int i = 1; i < MaxBlock; i++) {
//			LHLevelLoader.instance().initLevel("level/level"+i+".svg");
//		}
		LHLevelLoader.instance().initLevel("level/leveld.svg");

		mBlockTop = Gdx.graphics.getHeight();
		genBlock();
		// DEBUG
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		// mMainBall.moveBy(Gdx.input.getDeltaX(), -Gdx.input.getDeltaY());
		mMainBall.addPathDelta(new Vector2(Gdx.input.getDeltaX(), -Gdx.input
				.getDeltaY()));
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(mGuideImage!=null){
			mGuideImage.remove();
			mGuideImage = null;
		}
		
		mMainBall.initPath();
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public void mainBallStopMoving() {
		// TODO Auto-generated method stub
		Array<LHGameObject> objs = mObjectEngine.getObjects();
		for (LHGameObject lhGameObject : objs) {
			if (lhGameObject instanceof LHWormHoleObject) {
				LHWormHoleObject wo = (LHWormHoleObject) lhGameObject;
				final Vector2 po = wo.getOtherFixTurePosition(mMainBall.circle);
				if (po != null) {
					mQueue.enqueue(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mMainBall.moveTo(po.x, po.y);
							LHGameCache.loadSound("data/transport.ogg").play();
						}
					});
				}
			}
		}
	}

	private void gameOver() {

		LHGameCache.loadSound("data/hit.ogg").play();
		
		gameOver = true;
		Gdx.input.setInputProcessor(mStage);

		Texture t = new Texture("data/libgdx.png");
		TextureRegion tr = new TextureRegion(t, 10, 10, 5, 5);
		final Image white = new Image(tr);
		white.setBounds(0, 0, mStage.getWidth(), mStage.getHeight());
		mStage.addActor(white);
		white.getColor().a=0.0f;
		float duration = 0.2f;
		AlphaAction up = new AlphaAction();
		up.setAlpha(1.0f);
		up.setDuration(duration);
		AlphaAction down = new AlphaAction();
		down.setAlpha(0.0f);
		down.setDuration(duration);
		SequenceAction whiteaction = new SequenceAction(up,down);
		white.addAction(whiteaction);

		Preferences pre = Gdx.app.getPreferences("record");
		int best = pre.getInteger("best");
		boolean shownew = mOffset > best ? true : false;
		if (shownew) {
			pre.putInteger("best", (int) mOffset);
			pre.flush();
		}

		mPanel = new GamePlayPanel(mStage.getWidth() / 2,
				-mStage.getHeight() / 2, shownew);
		mStage.addActor(mPanel);

		mStage.addActor(mPanel);
		MoveToAction action = new MoveToAction();
		action.setDuration(1);
		action.setPosition(mPanel.getX(),
				mStage.getHeight() / 2 - mPanel.getHeight() / 2);
		mPanel.addAction(action);
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
		MainBall.mainBall = null;
		mMainBall.dispose();
		mMainBall = null;
		mWorld.destroyBody(mEdgeBox.getBody());
		mWorld.dispose();
		mWorld = null;
		mSRender.dispose();
		mBatch.dispose();
		mStage.dispose();
		if (mPanel != null)
			mPanel.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		LHLogger.logD("beginContact happen");
		if (gameOver)
			return;
		checkBody(contact.getFixtureA().getBody().getUserData(), contact);
		checkBody(contact.getFixtureB().getBody().getUserData(), contact);
	}

	private void checkBody(Object ud, Contact contact) {
		if (ud != null) {
			if (ud instanceof LHBallGameObject) {
				LHBallGameObject go = (LHBallGameObject) ud;
				if (go.tag < 0) {
					gameOver();
				}
			}
		}
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

	@Override
	public boolean removeObject(LHGameObject obj) {
		// TODO Auto-generated method stub
		if (mCamera.position.y - obj.getTop() > Gdx.graphics.getHeight()) {
			return true;
		}
		return false;
	}

}
