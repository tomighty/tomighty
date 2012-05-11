/*
 * Copyright (c) 2010-2012 Célio Cidral Junior, Dominik Obermaier.
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 */

package org.tomighty.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tomighty.plugin.PluginVersion;

import static org.junit.Assert.assertEquals;

/**
 * @author dobermai
 */
public class StringConversionUtilTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConvertStringToPluginVersionWithNullParameter() throws Exception {

        expectedException.expectMessage("Cannot convert null to a PluginVersion Instance");
        StringConversionUtil.convertStringToPluginVersion(null);
    }

    @Test
    public void testConvertStringToPluginVersionWithTooSmallNumbersOfDots() {
        expectedException.expectMessage("Version number does not match style 'major.minor.bugfix");
        StringConversionUtil.convertStringToPluginVersion("1.0");
    }

    @Test
    public void testConvertStringToPluginVersionWithTooBigNumbersOfDots() {
        expectedException.expectMessage("Version number does not match style 'major.minor.bugfix");
        StringConversionUtil.convertStringToPluginVersion("1.0.1.4");
    }

    @Test
    public void testConvertStringToPluginVersionWithNoFormat() {
        expectedException.expectMessage("Version number does not match style 'major.minor.bugfix");
        StringConversionUtil.convertStringToPluginVersion("abcde");
    }

    @Test
    public void testConvertStringToPluginVersionMajorNoInteger() {
        expectedException.expect(NumberFormatException.class);
        StringConversionUtil.convertStringToPluginVersion("a.1.0");
    }

    @Test
    public void testConvertStringToPluginVersionMinorNoInteger() {
        expectedException.expect(NumberFormatException.class);
        StringConversionUtil.convertStringToPluginVersion("1.a.0");
    }

    @Test
    public void testConvertStringToPluginVersionBugfixNoInteger() {
        expectedException.expect(NumberFormatException.class);
        StringConversionUtil.convertStringToPluginVersion("1.1.a");
    }

    @Test
    public void testconvertStringToPluginVersion() {

        PluginVersion pluginVersion = StringConversionUtil.convertStringToPluginVersion("1.2.3");

        assertEquals(1, pluginVersion.getMajor());
        assertEquals(2, pluginVersion.getMinor());
        assertEquals(3, pluginVersion.getBugfix());
    }
}
