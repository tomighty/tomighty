package org.tomighty.ui.location;

import java.awt.Dimension;
import java.awt.Point;

public class Left extends LocationSupport {

	public Left(Point mouse) {
		super(mouse);
	}

	@Override
	protected int x(Dimension windowSize) {
		return screenSize().x + MARGIN;
	}
	
	@Override
	protected int y(Dimension windowSize) {
		return mouse.y - windowSize.height / 2;
	}

	@Override
	public int distanceFromScreenSide() {
		return mouse.x;
	}

}
