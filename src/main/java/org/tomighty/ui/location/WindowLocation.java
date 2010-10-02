package org.tomighty.ui.location;

import java.awt.Dimension;
import java.awt.Point;

public interface WindowLocation {

	Point determine(Dimension windowSize);

	int distanceFromScreenSide();
	
}
