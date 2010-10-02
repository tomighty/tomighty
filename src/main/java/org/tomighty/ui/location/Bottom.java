package org.tomighty.ui.location;

import java.awt.Dimension;
import java.awt.Point;

public class Bottom extends WindowLocationSupport {

	public Bottom(Point mouse) {
		super(mouse);
	}
	
	@Override
	protected int x(Dimension windowSize) {
		return mouse.x - windowSize.width / 2;
	}
	
	@Override
	protected int y(Dimension windowSize) {
		return screenSize().height - windowSize.height - MARGIN;
	}

	@Override
	public int distanceFromScreenSide() {
		return screenSize().height - mouse.y;
	}

}
