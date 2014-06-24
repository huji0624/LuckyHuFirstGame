package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.bal.gameobject.LHCircleObject;
import com.luckyhu.game.framework.game.util.LHGameCache;
import com.luckyhu.game.framework.game.util.LHLogger;
import com.luckyhu.game.framework.game.util.PolyLinePath;

public class MainBall extends LHCircleObject {

	public static MainBall mainBall = null;

	private PolyLinePath mPath;
	private MainBallDelegate mDelegate;
	public boolean drawPath = true;
	private boolean isPlaying = false;

	public MainBall(World world) {
		super(world, new Circle(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() / 19),
				"data/main.png", BodyType.DynamicBody);
		this.tag = 624;
		if (world!=null) {
			mBody.setSleepingAllowed(false);
		}
		mainBall = this;

		mPath = new PolyLinePath();
	}

	public void setMainBallDelegate(MainBallDelegate delegate) {
		this.mDelegate = delegate;
	}

	public Body getBody() {
		return mBody;
	}
	
	private static double SPEED = Gdx.graphics.getWidth()*0.22;

	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub

		if (mPath != null) {

			if (mPath.size() > 1) {
				Vector2 to = new Vector2();
				mPath.valueAt(to, mLoc);
				mDire = new Vector2(to.x - circle.x, to.y - circle.y);
				moveTo(to.x, to.y);
				mLoc += delta * SPEED;

				Music music = LHGameCache.loadMusic("walk3");
				if (music!=null&&isPlaying == false) {
					music.setLooping(true);
					music.play();
					isPlaying = true;
				}

				if (mLoc >= mPath.getLength()) {
					stopMoving();
				}

				if (drawPath) {
					render.begin(ShapeType.Line);
					render.setColor(Color.BLACK);
					for (int i = mPath.index(); i < mPath.size() - 1; i++) {
						render.line(mPath.get(i), mPath.get(i + 1));
					}
					render.end();
				}
			}
		}

		mSprite.setRotation(mDire.angle() - 90);

//		batch.begin();
		super.render(batch, render, delta);
//		batch.end();
	}

	public void stopMoving() {
		if(mPath.size()==0)
			return;
		else if (mPath.size()==1) {
			mPath.clear();
			return;
		}
		
		Music music = LHGameCache.loadMusic("walk3");
		mLoc = 0;
		mPath.clear();
		mDelegate.mainBallStopMoving();
		if (isPlaying) {
			music.pause();
			isPlaying = false;
		}
	}

	private float mLoc = 0;
	private Vector2 mDire = new Vector2(0, 1);

	public void initPath() {
		stopMoving();
		mPath.add(new Vector2(circle.x, circle.y));
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		super.moveBy(dx, dy);
		for (int i = 0; i < mPath.size(); i++) {
			Vector2 ve = mPath.get(i);
			ve.add(dx, dy);
		}
	}

	public void addPathDelta(Vector2 p) {
		if (p.equals(Vector2.Zero)) {
			return;
		}

		if (mPath.size() != 0) {
			Vector2 last = mPath.peek().cpy();
			mPath.add(last.add(p));
		} else {
			mPath.add(new Vector2(circle.x, circle.y));
		}
	}

	public void addPathPoint(Vector2 p){
		mPath.add(p);
	}
}
