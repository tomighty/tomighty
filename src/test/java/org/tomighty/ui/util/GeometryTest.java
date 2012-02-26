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

import org.junit.Test;

import java.awt.*;

import static junit.framework.Assert.assertEquals;

public class GeometryTest {

    @Test
    public void increaseDimension() {
        Dimension dimension = new Dimension(5, 9);
        int amountToIncrease = 2;

        Dimension increasedDimension = Geometry.increase(amountToIncrease, dimension);

        assertEquals(dimension.width + amountToIncrease, increasedDimension.width);
        assertEquals(dimension.height + amountToIncrease, increasedDimension.height);
    }

    @Test
    public void increaseDimensionDoesNotChangeOriginalDimension() {
        Dimension originalDimension = new Dimension(0, 0);

        Geometry.increase(9, originalDimension);

        assertEquals(0, originalDimension.width);
        assertEquals(0, originalDimension.height);
    }

    @Test
    public void offset() {
        Point newPoint = Geometry.offset(3, new Point(1, 5));

        assertEquals(4, newPoint.x);
        assertEquals(8, newPoint.y);
    }

    @Test
    public void offsetDoesNotChangeOriginalPoint() {
        Point originalPoint = new Point(0, 0);

        Geometry.offset(11, originalPoint);
        
        assertEquals(0, originalPoint.x);
        assertEquals(0, originalPoint.y);
    }

    @Test
    public void offsetPoint() {
        Point offset = new Point(9, 15);
        Point newPoint = Geometry.offset(offset, new Point(1, 5));

        assertEquals(10, newPoint.x);
        assertEquals(20, newPoint.y);
    }

    @Test
    public void offsetPointDoesNotChangeOriginalPoint() {
        Point originalPoint = new Point(0, 0);

        Geometry.offset(new Point(1, 2), originalPoint);

        assertEquals(0, originalPoint.x);
        assertEquals(0, originalPoint.y);
    }

    @Test
    public void opposite() {
        Point point = Geometry.opposite(new Point(1, 2));
        assertEquals(-1, point.x);
        assertEquals(-2, point.y);
    }

}
