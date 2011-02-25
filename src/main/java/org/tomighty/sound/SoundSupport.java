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

package org.tomighty.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.tomighty.config.Options;
import org.tomighty.config.Options.SoundConfig;
import org.tomighty.ioc.Inject;

public abstract class SoundSupport implements Sound {

	@Inject private Options options;
	
	protected abstract SoundConfig configuration();
	
	protected abstract String defaultSoundResource();

	protected Options options() {
		return options;
	}

	@Override
	public InputStream inputStream() throws FileNotFoundException {
		File file = options.sound().wind().file();
		if(file == null) {
			return defaultSound();
		}
		return new FileInputStream(file);
	}

	@Override
	public boolean disabled() {
		return !configuration().enabled();
	}

	private InputStream defaultSound() throws FileNotFoundException {
		InputStream stream = getClass().getResourceAsStream(defaultSoundResource());
		if(stream == null) {
			throw new FileNotFoundException("Resource file not found: "+defaultSoundResource());
		}
		return stream;
	}
	
}
