package org.tomighty.resources.cache;

import java.io.File;

import org.tomighty.config.Directories;
import org.tomighty.ioc.Factory;
import org.tomighty.ioc.Inject;

public class Caches {
	
	@Inject private Factory factory;
	private File rootDirectory;
	
	@Inject
	public Caches(Directories directories) {
		rootDirectory = new File(directories.configuration(), "cache");
	}
	
	public Cache of(Class<? extends EntryType> type) {
		EntryType entryType = factory.create(type);
		String cacheName = entryType.name();
		File directory = new File(rootDirectory, cacheName);
		Cache cache = new Cache(entryType, directory);
		return cache;
	}

}
