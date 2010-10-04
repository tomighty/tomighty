package org.tomighty.ui.location;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;


public abstract class LocationSupport implements Location {
	
	protected static final int MARGIN = 5;

	protected final Point mouse;

	protected LocationSupport(Point mouseLocation) {
		this.mouse = mouseLocation;
	}
	
	protected abstract int x(Dimension windowSize);
	
	protected abstract int y(Dimension windowSize);
	
	@Override
	public Point determine(Dimension windowSize) {
		int x = x(windowSize);
		int y = y(windowSize);
		return new Point(x, y);
	}

	protected Rectangle screenSize() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	}

}
