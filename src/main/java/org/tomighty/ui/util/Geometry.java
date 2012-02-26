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
