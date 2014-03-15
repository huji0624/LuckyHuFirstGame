package com.luckyhu.game.framework.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class LHMapEngine {
	
	private Array<LHMapBlock> mMapBlock;
	
	private float top = 0;
	
	private Array<LHMapBlock> mBlockCache;

	public LHMapEngine(){
		mMapBlock = new Array<LHMapBlock>();
		mBlockCache = new Array<LHMapBlock>();
		Texture.setEnforcePotImages(false);
		
		for (int i = 0; i < 6; i++) {			
			mBlockCache.add(new LHMapBlock("data/space.png",  14, 14, 14, 14));
		}
	}
	
	public void render(SpriteBatch batch,float delta,float offset){
		
		genMapBlock(offset);
		
		for (int i = 0; i < mMapBlock.size; i++) {
			LHMapBlock mb =  mMapBlock.get(i);
			mb.render(batch, delta);
			
			if(mb.getTop()<offset - Gdx.graphics.getHeight()){
				mMapBlock.removeValue(mb, true);
				mBlockCache.add(mb);
			}
		}
	}
	
	private void genMapBlock(float offset){
		while(top-offset<Gdx.graphics.getHeight()*2){
			LHMapBlock mb = getUnUseBlock();
			Vector2 bs = mb.blockSize();
			mb.moveTo(0, top);
			top+=bs.y;
			mMapBlock.add(mb);
		}
	}
	
	private LHMapBlock getUnUseBlock(){
		int i = MathUtils.random(0, mBlockCache.size-1);
		LHMapBlock mb = mBlockCache.get(i);
		mBlockCache.removeIndex(i);
		return mb;
	}
}
