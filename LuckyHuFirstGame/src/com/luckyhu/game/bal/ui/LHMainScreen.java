package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
import com.luckyhu.game.bal.gameobject.LHRectObject;
import com.luckyhu.game.bal.gameobject.LHWormHoleObject;
import com.luckyhu.game.bal.objectblocks.LHObjectBlockGenerator;
import com.luckyhu.game.framework.game.LHGame;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngine;
import com.luckyhu.game.framework.game.engine.LHGameObjectEngineListener;
import com.luckyhu.game.framework.game.util.LHActionQueue;
import com.luckyhu.game.framework.game.util.LHLogger;

public class LHMainScreen implements Screen, ContactListener,
		LHGameObjectEngineListener {

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

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if (!gameOver) {
			mSRender.setProjectionMatrix(mCamera.combined);
			GL10 gl = Gdx.app.getGraphics().getGL10();
			mCamera.update();
			mCamera.apply(gl);

			mWorld.step(delta, 10, 10);

			mQueue.runAll();

			mObjectEngine.renderObject(mSRender, delta);
			mMainBall.render(mSRender, delta);

			moveViewPort(delta);

			debugRender.render(mWorld, mSRender.getProjectionMatrix());
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

	private void genBlock() {

		while (mBlockTop - mOffset < mStage.getHeight() * 2) {
			try {
				int MaxBlock = 5;
				int blockNumber = MathUtils.random(1, MaxBlock);
				@SuppressWarnings("unchecked")
				Class<LHObjectBlockGenerator> onwClass = (Class<LHObjectBlockGenerator>) Class
						.forName("com.luckyhu.game.bal.objectblocks.LHOBG"
								+ blockNumber);
				LHObjectBlockGenerator gen = (LHObjectBlockGenerator) onwClass
						.newInstance();
				Array<LHGameObject> array = gen.generate(mWorld,
						mStage.getWidth(), mStage.getHeight());
				for (LHGameObject lhGameObject : array) {
					lhGameObject.moveBy(0, mBlockTop);
				}
				mObjectEngine.addObjects(array);

				mBlockTop += gen.blockSize().y;

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

		// Debug
		mObjectEngine.addObject(new LHRectObject(mWorld, new Rectangle(50, 50,
				30, 5), 1.9f, 3.6f));

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
		mMainBall.dispose();
		mWorld.destroyBody(mEdgeBox.getBody());
		mWorld.dispose();
		mSRender.dispose();
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		LHLogger.logD("beginContact happen");
		Object ud = contact.getFixtureB().getBody().getUserData();

		if (ud == null) {
			ud = contact.getFixtureA().getBody().getUserData();
		}

		if (ud != null) {

			if (ud instanceof LHWormHoleObject) {
				LHWormHoleObject wo = (LHWormHoleObject) ud;
				if (wo.inTranslate == false) {
					wo.inTranslate = true;
					final Vector2 po = wo.getOtherFixTurePosition(contact
							.getFixtureB());
					if (po == null) {
						LHLogger.logD("LHWormContact error.");
					} else {
						mQueue.enqueue(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mMainBall.moveTo(po.x, po.y);
							}
						});

					}
				}

			} else if (ud instanceof LHGameObject) {
				LHGameObject go = (LHGameObject) ud;
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
		LHLogger.logD("vo:" + mMainBall.getBody().getLinearVelocity().len());
		Object ud = contact.getFixtureB().getBody().getUserData();
		if (ud != null && ud instanceof LHWormHoleObject) {
			LHWormHoleObject wo = (LHWormHoleObject) ud;
			wo.inTranslate = false;
		}
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
		if (mCamera.position.y - obj.getTop() > Gdx.graphics.getHeight() / 2) {
			return true;
		}
		return false;
	}

}
