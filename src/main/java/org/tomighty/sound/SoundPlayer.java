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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.tomighty.ioc.Inject;
import org.tomighty.ioc.New;
import org.tomighty.log.Log;

public class SoundPlayer {
	
	@Inject @New private Log log;

	private Map<Sound, Clip> activeClips = Collections.synchronizedMap(new HashMap<Sound, Clip>());

	public void play(Sound sound) {
		play(sound, false);
	}
	
	public void playRepeatedly(Sound sound) {
		play(sound, true);
	}

	public void stop(Sound sound) {
		Clip clip = activeClips.get(sound);
		if(clip != null) {
			clip.stop();
			activeClips.remove(sound);
		}
	}

	private void play(final Sound sound, boolean repeatedly) {
		stop(sound);
		try {
			InputStream stream = sound.inputStream();
			AudioInputStream input = AudioSystem.getAudioInputStream(stream);
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if(event.getType().equals(LineEvent.Type.STOP)) {
						Line line = event.getLine();
						line.close();
						activeClips.remove(sound);
					}
				}
			});
			clip.open(input);
			if(repeatedly) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.start();
			}
			activeClips.put(sound, clip);
		} catch (Exception e) {
			log.error("Error while playing sound: "+sound, e);
		}
	}

}
