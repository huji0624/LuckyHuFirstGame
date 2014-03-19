package com.svg.level.reader.demo;

import com.svg.level.reader.SvgLevel;
import com.svg.level.reader.SvgLevelReader;
import com.svg.level.reader.SvgLevelReaderHandler;
import com.svg.level.reader.entity.SvgRect;

public class SvgMain implements SvgLevelReaderHandler<String>{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SvgMain main = new SvgMain();
		SvgLevelReader<String> reader = new SvgLevelReader<String>(main);
		reader.loadLevel("all.svg");
	}

	@Override
	public String handleRect(SvgLevel<String> level,SvgRect rect) {
		// TODO Auto-generated method stub
		
		return "aa";
	}
	
	public static <T> void printf(T t){
		System.out.print(t+"\n");
	}

}
