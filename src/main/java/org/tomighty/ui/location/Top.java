package org.tomighty.ui.location;

import java.awt.Dimension;
import java.awt.Point;

public class Top extends LocationSupport {

	public Top(Point mouse) {
		super(mouse);
	}

	@Override
	protected int x(Dimension windowSize) {
		return mouse.x - windowSize.width / 2;
	}
	
	@Override
	protected int y(Dimension windowSize) {
		return screenSize().y + MARGIN;
	}

	@Override
	public int distanceFromScreenSide() {
		return mouse.y;
	}

}
