/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
