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

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author dobermai
 */
public class PluginPropertiesReaderTest {

    private ClassLoader defaultClassLoader;
    private PluginPropertiesReader reader;
    private ClassLoader emptyClassLoader;

    @Before
    public void setUp() throws Exception {
        createClassLoaders();

        reader = new PluginPropertiesReader();
    }

    @Test
    public void testGetClassName() throws Exception {

        String pluginClassName = reader.getPluginClassName(defaultClassLoader);
        assertEquals("org.tomighty.plugin.DefaultPlugin", pluginClassName);

    }

    @Test
    public void testEmptyGetClassName() throws Exception {

        String pluginClassName = reader.getPluginClassName(emptyClassLoader);
        assertNull(pluginClassName);
    }

    @Test
    public void testGetGuiceModuleClassName() {
        String guiceModuleClassName = reader.getGuiceModuleClassName(defaultClassLoader);
        assertEquals("com.google.inject.AbstractModule", guiceModuleClassName);
    }

    @Test
    public void testEmptyGetGuiceModuleClassName() {
        String guiceModuleClassName = reader.getGuiceModuleClassName(emptyClassLoader);
        assertNull(guiceModuleClassName);
    }

    @Test
    public void testVersion() {
        String version = reader.getPluginVersion(defaultClassLoader);
        assertEquals("1.0.0", version);
    }

    @Test
    public void testEmptyVersion() {
        String version = reader.getPluginVersion(emptyClassLoader);
        assertNull(version);
    }


    private void createClassLoaders() {
        defaultClassLoader = new ClassLoader() {

            @Override
            public InputStream getResourceAsStream(final String name) {
                try {
                    return new FileInputStream(getClass().getClassLoader().getResource("default-tomighty-plugin.properties").getFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        emptyClassLoader = new ClassLoader() {

            @Override
            public InputStream getResourceAsStream(final String name) {
                try {
                    return new FileInputStream(getClass().getClassLoader().getResource("empty-tomighty-plugin.properties").getFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
