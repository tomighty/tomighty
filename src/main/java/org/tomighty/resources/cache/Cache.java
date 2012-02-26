package org.tomighty.resources.cache;

import org.tomighty.log.Log;

import java.io.File;
import java.io.IOException;

public class Cache {

	private final EntryType type;
	private final File directory;
    private final Log log = new Log(getClass());

	public Cache(EntryType type, File directory) {
		this.type = type;
		this.directory = directory;
		directory.mkdirs();
	}

	public boolean contains(String name) {
		File file = new File(directory, name);
		return file.exists();
	}

	@SuppressWarnings("unchecked")
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
