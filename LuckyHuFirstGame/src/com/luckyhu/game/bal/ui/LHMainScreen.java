package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.luckyhu.game.bal.gameobject.LHBallGameObject;
import com.luckyhu.game.bal.gameobject.LHBlackHoleObject;
import com.luckyhu.game.bal.gameobject.LHDragObject;
import com.luckyhu.game.bal.gameobject.LHBezierMoveObject;
import com.luckyhu.game.bal.gameobject.LHPolygonMovingObject;
import com.luckyhu.game.bal.gameobject.LHWhiteHoleObject;
import com.luckyhu.game.bal.gameobject.LHWormHoleObject;
import com.luckyhu.game.bal.objectblocks.LHObjectBlockGenerator;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngine;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngineListener;
import com.luckyhu.game.framework.game.engine.LHMapEngine;
import com.luckyhu.game.framework.game.util.LHActionQueue;
import com.luckyhu.game.framework.game.util.LHLogger;

public class LHMainScreen extends InputAdapter implements Screen, ContactListener,
		LHGameObjectEngineListener {

	private LHMapEngine mMapEngine;
	private SpriteBatch mBatch;
	
	private LHGameObjectEngine mObjectEngine;
	private ShapeRenderer mSRender;
	private OrthographicCamera mCamera;
	private MainBall mMainBall;

	private World mWorld;

	private Box2DDebugRenderer debugRender;

	private Stage mStage;

	private float mOffset;
	private Label mLabel;
	private float mBlockTop = 0;

	private LHEdgeBox mEdgeBox;

	private LHActionQueue mQueue = new LHActionQueue();

	private boolean gameOver = false;
	private int maxDis = 0;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if (!gameOver) {
			mSRender.setProjectionMatrix(mCamera.combined);
			mBatch.setProjectionMatrix(mCamera.combined);
			GL10 gl = Gdx.app.getGraphics().getGL10();
			mCamera.update();
			mCamera.apply(gl);

			mWorld.step(delta, 10, 10);

			mQueue.runAll();

			mBatch.begin();
			mMapEngine.render(mBatch, delta,mOffset);
			mObjectEngine.renderObject(mBatch,mSRender, delta);
			mBatch.end();
			
			mMainBall.render(null, mSRender, delta);

			moveViewPort(delta);

//			debugRender.render(mWorld, mSRender.getProjectionMatrix());
			
			if(maxDis<mMainBall.getTop()){
				maxDis =(int) mMainBall.getTop();
				mLabel.setText(""+maxDis);
			}
		}
		mStage.act(delta);
		mStage.draw();
	}

	private void moveViewPort(float delta) {

		float dis = 20 * delta;
		mOffset += dis;
		mCamera.position.y += dis;
		mEdgeBox.getBody().setTransform(Gdx.graphics.getWidth() / 2,
				mEdgeBox.getBody().getPosition().y + dis, 0);

		genBlock();

		mMainBall.setOffset(mOffset);
	}

	private int blockNumber = 8;
	
	private void genBlock() {

		while (mBlockTop - mOffset < mStage.getHeight() * 2) {
			try {
				int MaxBlock = 8;
//				blockNumber = MathUtils.random(1, MaxBlock);
				
				@SuppressWarnings("unchecked")
				Class<LHObjectBlockGenerator> onwClass = (Class<LHObjectBlockGenerator>) Class
						.forName("com.luckyhu.game.bal.objectblocks.LHOBG"
								+ 1);
				LHObjectBlockGenerator gen = (LHObjectBlockGenerator) onwClass
						.newInstance();
				Array<LHBallGameObject> array = gen.generate(mWorld,
						mStage.getWidth(), mStage.getHeight());
				for (LHBallGameObject lhGameObject : array) {
					lhGameObject.moveBy(0, mBlockTop);
				}
				mObjectEngine.addObjects(array);

				mBlockTop += gen.blockSize().y;
				blockNumber --;
				if(blockNumber<=0) blockNumber=MaxBlock;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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

		mEdgeBox = new LHEdgeBox(mWorld);

		mStage = new Stage();
		mLabel = new Label("", new LabelStyle(new BitmapFont(), Color.WHITE));
		mLabel.setPosition(mStage.getWidth() - 50,
				mStage.getHeight() - mLabel.getHeight() - 10);
		mStage.addActor(mLabel);

		mBlockTop = Gdx.graphics.getHeight();
		genBlock();

		Gdx.input.setInputProcessor(this);
		
		//DEBUG
		
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		
		return super.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		Array<LHGameObject> objs = mObjectEngine.getObjects();
		for (LHGameObject lhGameObject : objs) {
			if(lhGameObject instanceof LHWormHoleObject){
				LHWormHoleObject wo = (LHWormHoleObject)lhGameObject;
				final Vector2 po = wo.getOtherFixTurePosition(mMainBall.circle);
				if(po!=null){
					mQueue.enqueue(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mMainBall.moveTo(po.x, po.y);
						}
					});
					return true;
				}
			}
		}
		
		return super.touchUp(screenX, screenY, pointer, button);
	}

	private void gameOver() {
		
		gameOver = true;
		Gdx.input.setInputProcessor(mStage);

		Group uiGroup = new Group();
		uiGroup.setColor(Color.RED);
		uiGroup.setBounds(mStage.getWidth() * 2, 0, mStage.getWidth(),
				mStage.getHeight());

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
		playButton.setPosition(uiGroup.getWidth() / 2 - playButton.getWidth()
				/ 2, uiGroup.getHeight() / 2 - playButton.getHeight() / 2);
		playButton.addListener(new ClickListener() {

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
		MainBall.mainBall =null;
		mMainBall.dispose();
		mMainBall=null;
		mWorld.destroyBody(mEdgeBox.getBody());
		mWorld.dispose();
		mWorld=null;
		mSRender.dispose();
		mBatch.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		LHLogger.logD("beginContact happen");

		checkBody(contact.getFixtureA().getBody().getUserData(), contact);
		checkBody(contact.getFixtureB().getBody().getUserData(), contact);
	}
	
	private void checkBody(Object ud,Contact contact){
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
