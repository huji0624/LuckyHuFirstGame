package com.luckyhu.game.bal.gameobject;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class LHCircleObject extends LHBallGameObject {

	public Circle circle;

	public LHCircleObject(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public LHCircleObject(World world, Circle circle) {
		this(world);
		this.circle = new Circle(circle);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
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
	
	@Override
	public void render(ShapeRenderer render, float delta) {
		// TODO Auto-generated method stub
		super.render(render, delta);
		
		render.begin(ShapeType.Filled);
		render.setColor(mColor);
		render.circle(circle.x, circle.y, circle.radius);
		render.end();
		
	}

	@Override
	public void moveBy(float dx, float dy) {
		// TODO Auto-generated method stub
		circle.x += dx;
		circle.y += dy;
		
		mBody.setTransform(mBody.getPosition().x + dx, mBody.getPosition().y
				+ dy, 0);
	}
	
	@Override
	public void moveTo(float x, float y) {
		// TODO Auto-generated method stub
		circle.x = x;
		circle.y = y;
		mBody.setTransform(x,y, 0);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mWorld.destroyBody(mBody);
	}

	@Override
	public float getTop() {
		// TODO Auto-generated method stub
		return circle.y + circle.radius;
	}

}
