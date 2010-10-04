package org.tomighty.ui.location;

import java.awt.Dimension;
import java.awt.Point;

public class Right extends LocationSupport {

	public Right(Point mouse) {
		super(mouse);
	}

	@Override
	protected int x(Dimension windowSize) {
		return screenSize().width - windowSize.width - MARGIN;
	}
	
	@Override
	protected int y(Dimension windowSize) {
		return mouse.y - windowSize.height / 2;
	}

	@Override
	public int distanceFromScreenSide() {
		return screenSize().width - mouse.x;
	}

}
