package org.tomighty.ui.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Closest {

	public static Location location(Point mouseLocation) {
		List<Location> list = new ArrayList<Location>();
		list.add(new Top(mouseLocation));
		list.add(new Left(mouseLocation));
		list.add(new Bottom(mouseLocation));
		list.add(new Right(mouseLocation));
		Location closest = null;
		for(Location location : list) {
			if(closest == null || location.distanceFromScreenSide() < closest.distanceFromScreenSide()) {
				closest = location;
			}
		}
		return closest;
	}

}
