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

package org.tomighty.ui.state;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ui.UiStateChanged;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.UiState;
import org.tomighty.ui.state.pomodoro.Pomodoro;
import org.tomighty.ui.state.pomodoro.PomodoroFinished;
import org.tomighty.util.Range;

@SuppressWarnings("serial")
public class Gauge extends JComponent implements Subscriber<UiStateChanged> {

	private static final int LIGHT_SIZE = 5;
	private static final int LIGHT_COUNT = 4;
	private static final int GAP_BETWEEN_LIGHTS = 3;
	private static final Range<Color> LIGHT_COLOR_ON = new Range<Color>(new Color(227, 244, 144), new Color(136, 130, 35));
	private static final Range<Color> LIGHT_COLOR_OFF = new Range<Color>(new Color(60, 60, 60), new Color(32, 32, 32));
	
	private int numberOfLightsOn = 0;
	
	@Inject
	public Gauge(Bus bus) {
		setOpaque(false);
		int width = LIGHT_SIZE * LIGHT_COUNT + GAP_BETWEEN_LIGHTS * (LIGHT_COUNT - 1) + 1;
		int height = LIGHT_SIZE + 1;
		setSize(width, height);
		setPreferredSize(getSize());
		bus.subscribe(this, UiStateChanged.class);
	}
	
	@Override
	public void receive(UiStateChanged message) {
		UiState uiState = message.uiState();

        if(uiState instanceof PomodoroFinished) {
            turnNextLightOn();

        } else if(uiState instanceof Pomodoro) {
			if(areAllLightsOn())
                turnAllLightsOff();
        }
	}

    @Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int x = startingX();
		for(int index = 0; index < LIGHT_COUNT; index++) {
			Range<Color> colors = colorForLight(index);
			Point start = new Point(x, 0);
			Point end = new Point(x + LIGHT_SIZE / 2, LIGHT_SIZE / 2);
			Paint paint = new GradientPaint(start, colors.start(), end, colors.end());
			g2d.setPaint(paint);
			g2d.fillOval(x, 0, LIGHT_SIZE, LIGHT_SIZE);
			x += LIGHT_SIZE + GAP_BETWEEN_LIGHTS;
		}
	}

	private Range<Color> colorForLight(int index) {
		return isLightOn(index) ? LIGHT_COLOR_ON : LIGHT_COLOR_OFF;
	}

    private int startingX() {
		return getWidth() / 2 - getPreferredSize().width / 2;
	}

    private boolean isLightOn(int lightIndex) {
        return lightIndex < numberOfLightsOn;
    }

    private void turnAllLightsOff() {
        numberOfLightsOn = 0;
    }

    private void turnNextLightOn() {
        numberOfLightsOn++;
    }

    private boolean areAllLightsOn() {
        return numberOfLightsOn >= LIGHT_COUNT;
    }

}
