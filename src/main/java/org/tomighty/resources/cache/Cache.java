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
