package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class LHCircleObject extends LHBallGameObject {

	public Circle circle;

	protected Sprite mSprite;
	protected Texture mTexture;

	public LHCircleObject(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public LHCircleObject(World world, Circle circle, String path, BodyType type) {
		this(world);
		this.circle = new Circle(circle);

		if (world != null) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = type;
			bodyDef.position.set(circle.x, circle.y);
			mBody = world.createBody(bodyDef);
			mBody.setUserData(this);

			CircleShape shape = new CircleShape();
			shape.setRadius(circle.radius);
			FixtureDef fd = new FixtureDef();
			fd.shape = shape;
			fd.restitution = 1.0f;
			fd.friction = 0.0f;
			mBody.createFixture(fd);

			shape.dispose();
		}

		mTexture = new Texture(path);
		mSprite = new Sprite(mTexture);
		mSprite.setPosition(circle.x - circle.radius, circle.y - circle.radius);
		mSprite.setSize(circle.radius * 2, circle.radius * 2);
		mSprite.setOrigin(circle.radius, circle.radius);
	}

	public LHCircleObject(World world, Circle circle, String path) {
		this(world, circle, path, BodyType.StaticBody);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(batch, render, delta);
		batch.begin();
		mSprite.draw(batch);
		batch.end();
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		circle.x += dx;
		circle.y += dy;
		mSprite.translate(dx, dy);
		if (mBody != null) {
			mBody.setTransform(mBody.getPosition().x + dx,
					mBody.getPosition().y + dy, 0);
		}
	}

	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		circle.x = x;
		circle.y = y;
		if (mBody != null) {
			mBody.setTransform(x, y, 0);
		}
		mSprite.setPosition(circle.x - circle.radius, circle.y - circle.radius);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if(mWorld!=null)
			mWorld.destroyBody(mBody);
		mTexture.dispose();
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return circle.y + circle.radius;
	}

}
