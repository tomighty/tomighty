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

package org.tomighty.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class Resources {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public String text(String resourceName) {
		InputStream input = getClass().getResourceAsStream(resourceName);
		InputStreamReader reader = new InputStreamReader(input,
				Charset.forName("utf-8"));
		BufferedReader br = new BufferedReader(reader);
		StringBuilder text = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				if (text.length() > 0) {
					text.append('\n');
				}
				text.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return text.toString();
	}

	/**
	 * Returns an {@link Image} from a given resource name. If the resource does
	 * not exist, it will return <code>null</code>.
	 * 
	 * @param resourceName
	 *            a String for the resource name and path.
	 * @return the {@link Image} for the resource name or <code>null</code> if
	 *         the resource was not found
	 */
	public Image image(final String resourceName) {
		final URL imageUrl = getClass().getResource(resourceName);

		if (imageUrl != null) {

			try {
				return ImageIO.read(imageUrl);
			} catch (IOException e) {
				logger.error("Could not load image: " + resourceName, e);
			}
		}
		return null;
	}

}
