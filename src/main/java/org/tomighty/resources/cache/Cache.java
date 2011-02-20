package org.tomighty.resources.cache;

import java.io.File;
import java.io.IOException;

import org.tomighty.ioc.Inject;
import org.tomighty.ioc.New;
import org.tomighty.log.Log;

public class Cache {

	private final CacheType type;
	private final File directory;
	@Inject @New private Log log;

	public Cache(CacheType type, File directory) {
		this.type = type;
		this.directory = directory;
		directory.mkdirs();
	}

	public boolean contains(String name) {
		File file = new File(directory, name);
		return file.exists();
	}

	public <T> T get(String name) {
		File file = new File(directory, name);
		if(file.exists()) {
			try {
				return (T) type.read(file);
			} catch (IOException e) {
				log.error("Could not load image from cache", e);
			}
		}
		return null;
	}

	public void store(Object item, String name) {
		File file = new File(directory, name);
		try {
			type.write(item, file);
		} catch (IOException e) {
			log.error("Could not write image file to cache", e);
		}
	}

}
