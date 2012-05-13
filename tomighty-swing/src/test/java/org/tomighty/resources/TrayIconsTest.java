/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.resources;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.ui.tray.Tray;

import java.awt.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrayIconsTest {

	private static final int TRAY_ICON_SIZE = 10;
	
	private TrayIcons trayIcons;
	private Tray tray;
	private Resources resources;
	
	@Before
	public void setUp() {
        tray = mock(Tray.class);
        resources = mock(Resources.class);
        trayIcons = new TrayIcons();
        
        trayIcons.setTray(tray);
        trayIcons.setResources(resources);

		Dimension trayIconSize = new Dimension(TRAY_ICON_SIZE, TRAY_ICON_SIZE);
		when(tray.iconSize()).thenReturn(trayIconSize);
	}
	
	@Test
	public void testTomato() {
		Image expectedIcon = mock(Image.class);
		when(resources.image(resourceName(TRAY_ICON_SIZE))).thenReturn(expectedIcon);
		Image icon = trayIcons.tomato();
		assertThat(icon, equalTo(expectedIcon));
	}
	
	@Test
	public void testDefaultTomatoIcon() {
		Image defaultIcon = mock(Image.class);
		when(resources.image(resourceName(TrayIcons.DEFAULT_ICON_SIZE))).thenReturn(defaultIcon);
		Image icon = trayIcons.tomato();
		assertThat(icon, equalTo(defaultIcon));
	}

	private String resourceName(int iconSize) {
		return "/tomato-" + iconSize + ".png";
	}
	
}
