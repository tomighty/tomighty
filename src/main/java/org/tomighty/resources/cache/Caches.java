package org.tomighty.resources.cache;

import java.io.File;

import com.google.inject.Injector;
import org.tomighty.config.Directories;

import javax.inject.Inject;

public class Caches {
	
	@Inject private Injector injector;
	private File rootDirectory;
	
	@Inject
	public Caches(Directories directories) {
		rootDirectory = new File(directories.configuration(), "cache");
	}
	
	public Cache of(Class<? extends EntryType> type) {
		EntryType entryType = injector.getInstance(type);
		String cacheName = entryType.name();
		File directory = new File(rootDirectory, cacheName);
		Cache cache = new Cache(entryType, directory);
		return cache;
	}

}
