package com.svg.level.reader;

import com.svg.level.reader.entity.Svg;
import com.svg.level.reader.entity.SvgCircle;
import com.svg.level.reader.entity.SvgPath;
import com.svg.level.reader.entity.SvgPolygon;
import com.svg.level.reader.entity.SvgRect;

public interface SvgLevelReaderHandler {
	public void handleSvg(Svg svg);
	public void handleRect(SvgRect rect);
	public void handleCircle(SvgCircle circle);
	public void handlePath(SvgPath path);
	public void handlePolygon(SvgPolygon polygon);
}
