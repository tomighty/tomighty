package org.tomighty.ui.util;

import java.awt.*;

public class Geometry {

    public static Dimension increase(int amount, Dimension dimension) {
        return new Dimension(dimension.width + amount, dimension.height + amount);
    }

    public static Point offset(int amount, Point point) {
        return offset(new Point(amount, amount), point);
    }

    public static Point offset(Point offset, Point point) {
        return new Point(point.x + offset.x, point.y + offset.y);
    }

    public static Point opposite(Point point) {
        int oppositeX = point.x * -1;
        int oppositeY = point.y * -1;
        return new Point(oppositeX, oppositeY);
    }

}
