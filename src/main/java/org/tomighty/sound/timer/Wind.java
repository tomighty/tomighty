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

package org.tomighty.sound.timer;

import java.io.InputStream;

import org.tomighty.config.Options;
import org.tomighty.ioc.Inject;
import org.tomighty.sound.ResourceSound;
import org.tomighty.sound.Sound;

public class Wind implements Sound {
	
	private Sound defaultSound = new ResourceSound("/crank.wav");
	
	@Inject private Options options;

	@Override
	public InputStream inputStream() {
		return defaultSound.inputStream();
	}

	@Override
	public boolean disabled() {
		return !options.sound().wind();
	}

}
