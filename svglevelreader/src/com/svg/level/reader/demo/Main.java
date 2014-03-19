package com.svg.level.reader.demo;

import com.svg.level.reader.SvgLevelReader;
import com.svg.level.reader.SvgLevelReaderHandler;
import com.svg.level.reader.entity.SvgRect;

public class Main implements SvgLevelReaderHandler<String>{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		SvgLevelReader<String> reader = new SvgLevelReader<String>(main);
		reader.loadLevel("all.svg");
	}

	@Override
	public String handleRect(SvgRect rect) {
		// TODO Auto-generated method stub
		return "aa";
	}

}
