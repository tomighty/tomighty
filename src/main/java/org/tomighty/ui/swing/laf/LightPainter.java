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

package org.tomighty.ui.swing.laf;

import org.tomighty.ui.swing.gauge.Light;
import org.tomighty.ui.util.Geometry;
import org.tomighty.util.Range;

import java.awt.*;

public class LightPainter {

    private static final Range<Color> LIGHT_ON_COLOR = new Range<Color>(new Color(227, 244, 144), new Color(136, 130, 35));
    private static final Range<Color> LIGHT_OFF_COLOR = new Range<Color>(new Color(60, 60, 60), new Color(32, 32, 32));

    private int lightSize;
    private int margin;

    public LightPainter(int lightSize, int margin) {
        this.lightSize = lightSize;
        this.margin = margin;
    }

    public void paint(Light light, Graphics2D g2d) {
        Point position = Geometry.offset(margin, positionOf(light));
        g2d.setPaint(createGradientPaint(colorOf(light), position));
        g2d.fillOval(position.x, position.y, lightSize, lightSize);
    }

    private Paint createGradientPaint(Range<Color> color, Point position) {
        Point end = Geometry.offset(lightSize / 2, position);
        return new GradientPaint(position, color.start(), end, color.end());
    }

    private Point positionOf(Light light) {
        int x = (lightSize + margin) * light.number();
        return new Point(x, 0);
    }

    private static Range<Color> colorOf(Light light) {
        return light.isOn() ? LIGHT_ON_COLOR : LIGHT_OFF_COLOR;
    }

}
