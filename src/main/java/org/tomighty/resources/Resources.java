/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty.resources;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.tomighty.ioc.Inject;
import org.tomighty.log.Log;

public class Resources {
	
	@Inject private Log log;
	
	public String text(String resourceName) {
		InputStream input = getClass().getResourceAsStream(resourceName);
		InputStreamReader reader = new InputStreamReader(input, Charset.forName("utf-8"));
		BufferedReader br = new BufferedReader(reader);
		StringBuilder text = new StringBuilder();
		String line;
		try {
			while((line = br.readLine()) != null) {
				if(text.length() > 0) {
					text.append('\n');
				}
				text.append(line);
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return text.toString();
	}

	public Image image(String resourceName) {
		URL imageUrl = getClass().getResource(resourceName);
		try {
			return ImageIO.read(imageUrl);
		} catch (IOException e) {
			log.error("Could not load image: "+resourceName, e);
			return null;
		}
	}

}
