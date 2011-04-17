/*
 * Copyright 2011 Dominik Obermaier.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tomighty.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.Image;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author dobermai
 */
public class ResourcesTest {

	private Resources resources;

	@Before
	public void setUp() {
		resources = new Resources();
	}

	@Test
	public void testImageNotAvailable() {

		final Image image = resources.image("/tomato-22.png");
		assertNull(image);
	}

	@Test
	public void test16Image() {

		final Image image = resources.image("/tomato-16.png");
		assertNotNull(image);

		assertEquals(16, image.getHeight(null));
		assertEquals(16, image.getWidth(null));
	}

	@Test
	public void test24Image() {

		final Image image = resources.image("/tomato-24.png");
		assertNotNull(image);

		assertEquals(24, image.getHeight(null));
		assertEquals(24, image.getWidth(null));
	}

	@Test
	public void test32Image() {

		final Image image = resources.image("/tomato-32.png");
		assertNotNull(image);

		assertEquals(32, image.getHeight(null));
		assertEquals(32, image.getWidth(null));
	}

	@Test
	public void test48Image() {

		final Image image = resources.image("/tomato-48.png");
		assertNotNull(image);

		assertEquals(48, image.getHeight(null));
		assertEquals(48, image.getWidth(null));
	}
}
