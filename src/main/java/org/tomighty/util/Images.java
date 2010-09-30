package org.tomighty.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Images {
	private static final Logger logger = Logger.getLogger(Images.class.getName());
	
	public static Image get(String name) {
		URL imageUrl = Images.class.getResource(name);
		try {
			return ImageIO.read(imageUrl);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Could not load image: "+name, e);
			return null;
		}
	}
}
