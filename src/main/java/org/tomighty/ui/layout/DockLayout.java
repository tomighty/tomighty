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

package org.tomighty.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;

public class DockLayout implements LayoutManager {
	
	private Map<Component, String> map = new HashMap<Component, String>();
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		map.put(comp, name);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		map.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		for(Component child : parent.getComponents()) {
			String docking = map.get(child);
			if(docking == null) {
				child.setLocation(0, 0);
			} else if(docking.equals(Docking.FILL)) {
				fill(child, parent);
			} else if(docking.startsWith(Docking.RIGHT_TOP)) {
				rightTop(parent, child, docking);
			} else {
				throw new IllegalArgumentException("Invalid docking: "+docking);
			}
		}
	}
	
	private void fill(Component child, Container parent) {
		child.setLocation(0, 0);
		child.setSize(parent.getSize());
	}

	private void rightTop(Container parent, Component child, String docking) {
		String[] location = docking.split("=")[1].split("\\.");
		int right = Integer.parseInt(location[0]);
		int top = Integer.parseInt(location[1]);
		int x = parent.getWidth() - child.getWidth() - right;
		child.setLocation(x, top);
	}

}
