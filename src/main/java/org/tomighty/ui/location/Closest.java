package org.tomighty.ui.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Closest {

	public static WindowLocation windowLocation(Point mouseLocation) {
		List<WindowLocation> locations = new ArrayList<WindowLocation>();
		locations.add(new Top(mouseLocation));
		locations.add(new Left(mouseLocation));
		locations.add(new Bottom(mouseLocation));
		locations.add(new Right(mouseLocation));
		WindowLocation closest = null;
		for(WindowLocation location : locations) {
			if(closest == null || location.distanceFromScreenSide() < closest.distanceFromScreenSide()) {
				closest = location;
			}
		}
		return closest;
	}

}
