package org.tomighty.resources.cache;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Icons implements CacheType {

	@Override
	public String name() {
		return "icons";
	}

	@Override
	public Object read(File file) throws IOException {
		return ImageIO.read(file);
	}

	@Override
	public void write(Object item, File file) throws IOException {
		RenderedImage image = (RenderedImage)item;
		ImageIO.write(image, "png", file);
	}

}
