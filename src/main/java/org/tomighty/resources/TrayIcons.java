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

package org.tomighty.resources;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.image.BufferedImage;

import org.tomighty.ioc.Inject;
import org.tomighty.time.Time;
import org.tomighty.ui.state.laf.theme.ColorTone;
import org.tomighty.ui.state.laf.theme.Theme;
import org.tomighty.ui.util.Canvas;

public class TrayIcons {

	@Inject
	private Resources resources;
	
	@Inject
	private Theme theme;
	
	public Image tomato() {
		return resources.image("/tomato-16x16.png");
	}

	public Image time(Time time) {
		Dimension canvasSize = SystemTray.getSystemTray().getTrayIconSize();
		String text = String.valueOf(time.minutes() > 0 ? time.minutes() : time.seconds());
		float fontSize = (float)canvasSize.height * 0.58f;
		ColorTone colors = theme.colorTone();
		
		BufferedImage image = new BufferedImage(canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		try {
			Canvas canvas = new Canvas(canvasSize, graphics);
			canvas.fontSize(fontSize);
			canvas.drawGradientBackground(colors);
			canvas.drawBorder(colors.light().darker());
			canvas.drawCentralizedText(text);
		} finally {
			graphics.dispose();
		}

		return image;
	}

}
