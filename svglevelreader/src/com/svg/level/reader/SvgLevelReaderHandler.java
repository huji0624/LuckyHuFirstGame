package com.svg.level.reader;

import com.svg.level.reader.entity.SvgRect;

public interface SvgLevelReaderHandler<T> {
	public T handleRect(SvgLevel<T> level,SvgRect rect);
}
