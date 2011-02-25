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
import java.awt.Insets;
import java.awt.LayoutManager;

public class StackLayout implements LayoutManager {
	
	private int gap;
	
	public StackLayout() {
		this(0);
	}
	
	public StackLayout(int gap) {
		this.gap = gap;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int height = 0;
		int width = 0;
		Component[] components = parent.getComponents();
		for(int index = 0; index < components.length; index++) {
			Component child = components[index];
			Dimension size = child.getPreferredSize();
			width = Math.max(width, size.width);
			height += size.height;
			if(index > 0) {
				height += gap;
			}
		}
		Insets insets = parent.getInsets();
		width += insets.left + insets.right;
		height += insets.top + insets.bottom;
		return new Dimension(width, height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = parent.getWidth() - insets.left - insets.right;
		for(Component child : parent.getComponents()) {
			Dimension preferredSize = child.getPreferredSize();
			int height = preferredSize.height;
			child.setBounds(x, y, width, height);
			y += child.getPreferredSize().height + gap;
		}
	}

}
