package org.tomighty.ui.state.laf;

import org.tomighty.ui.model.Light;
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
        Point position = offset(positionOf(light), margin);
        g2d.setPaint(createGradientPaint(colorOf(light), position));
        g2d.fillOval(position.x, position.y, lightSize, lightSize);
    }

    private Paint createGradientPaint(Range<Color> color, Point position) {
        Point end = offset(position, lightSize / 2);
        return new GradientPaint(position, color.start(), end, color.end());
    }

    private Point positionOf(Light light) {
        int x = (lightSize + margin) * light.number();
        return new Point(x, 0);
    }

    private static Range<Color> colorOf(Light light) {
        return light.isOn() ? LIGHT_ON_COLOR : LIGHT_OFF_COLOR;
    }

    private static Point offset(Point point, int offset) {
        return new Point(point.x + offset, point.y + offset);
    }

}
