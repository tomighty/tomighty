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

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.tomighty.ioc.Inject;
import org.tomighty.ioc.New;
import org.tomighty.log.Log;

public class SoundPlayer {
	
	@Inject @New private Log log;

	public void play(Sound sound) {
		try {
			InputStream stream = sound.inputStream();
			AudioInputStream input = AudioSystem.getAudioInputStream(stream);
			final Clip clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if(event.getType().equals(LineEvent.Type.STOP)) {
						clip.close();
					}
				}
			});
			clip.open(input);
			clip.start();
		} catch (Exception e) {
			log.error("Error while playing sound: "+sound, e);
		}
	}

}
