package com.svg.level.reader.demo;

import com.svg.level.reader.SvgLevelReader;
import com.svg.level.reader.SvgLevelReaderHandler;
import com.svg.level.reader.entity.Svg;
import com.svg.level.reader.entity.SvgCircle;
import com.svg.level.reader.entity.SvgPath;
import com.svg.level.reader.entity.SvgPolygon;
import com.svg.level.reader.entity.SvgRect;

public class SvgMain implements SvgLevelReaderHandler{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SvgMain main = new SvgMain();
		SvgLevelReader reader = new SvgLevelReader();
		reader.initLevel("all.svg");
		reader.loadLevel(main,"all.svg");
	}
	
	public static <T> void printf(T t){
		System.out.print(t+"\n");
	}

	@Override
	public void handleSvg(Svg svg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleRect(SvgRect rect) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleCircle(SvgCircle circle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePath(SvgPath path) {
		// TODO Auto-generated method stub
		for (int i = 0; i < path.d.length; i++) {
			printf("Path:"+path.d[i]);
		}
	}

	@Override
	public void handlePolygon(SvgPolygon polygon) {
		// TODO Auto-generated method stub
		for (int i = 0; i < polygon.points.length; i++) {
			printf("Polygon:"+polygon.points[i]);
		}
	}

}
