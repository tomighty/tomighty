package org.tomighty.bus.messages;

import java.awt.Point;

public class TrayClick {

	private final Point mouseLocation;

	public TrayClick(Point mouseLocation) {
		this.mouseLocation = mouseLocation;
	}
	
	public Point mouseLocation() {
		return mouseLocation;
	}

}
