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

package org.tomighty.ui.swing.gauge;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.ui.swing.gauge.Light;

import static org.junit.Assert.*;

public class LightTest {

    private Light light;

    @Before
    public void setUp() throws Exception {
        light = new Light(123);
    }

    @Test
    public void isOffByDefault() {
        assertFalse(light.isOn());
    }

    @Test
    public void turnOn() {
        light.turnOn();
        assertTrue(light.isOn());
    }

    @Test
    public void turnOnThenOff() {
        light.turnOn();
        light.turnOff();
        assertFalse(light.isOn());
    }

    @Test
    public void number() {
        assertEquals(123, light.number());
    }

}
