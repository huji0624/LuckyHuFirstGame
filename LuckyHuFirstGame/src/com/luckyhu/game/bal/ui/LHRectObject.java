package com.luckyhu.game.bal.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.luckyhu.game.framework.game.engine.LHGameObject;
import com.luckyhu.game.framework.game.util.LHLogger;

public class LHRectObject extends LHGameObject{

	private Rectangle mRect;
	private Body mBody;
	private float mAngle;
	
	public LHRectObject(World world,Rectangle rect,float angle) {
		// TODO Auto-generated constructor stub
		super(world);
		mAngle = angle;
		mRect = new Rectangle(rect);
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		
		Vector2 an = new Vector2(1,0);
		float anR = (float)Math.toDegrees(angle);
		an.setAngle(anR+90);
		
		Vector2 center = new Vector2();
		mRect.getCenter(center);
		center.add(-mRect.x, -mRect.y);
		center.setAngle((float)Math.toDegrees(angle));
		center.add(mRect.x,mRect.y).add(an.nor().scl(mRect.height/2));
		bd.position.set(center);

		mBody = mWorld.createBody(bd);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(mRect.width/2, mRect.height / 2,
				new Vector2(0, 0),angle);
		mBody.createFixture(shape, 0);
		shape.dispose();
	}
	
	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(render, delta);
		
		render.begin(ShapeType.Filled);
		render.rect(mRect.x, mRect.y, mRect.width, mRect.height, 0, 0, (float)Math.toDegrees(mAngle));
		render.end();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mWorld.destroyBody(mBody);
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		mRect.x += dx;
		mRect.y += dy;
		mBody.setTransform(mBody.getPosition().x+dx, mBody.getPosition().y+dy, 0);
	}

}
