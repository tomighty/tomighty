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

package org.tomighty.ui.theme;

import java.awt.Image;

import org.tomighty.resources.cache.Cache;
import org.tomighty.resources.cache.Caches;
import org.tomighty.resources.cache.Images;
import org.tomighty.ui.util.Canvas;

import javax.inject.Inject;

public abstract class CachedTheme implements Theme {

	@Inject private Look look;
	@Inject private Caches caches;

	protected abstract void paint(Canvas canvas, Look look);
	
	@Override
	public final void paint(Canvas canvas) {
		Colors colors = look.colors();
		Cache cache = caches.of(Images.class);
		String name = getClass().getSimpleName() + "_" + colors.getClass().getSimpleName();
		Image image = cache.get(name);
		if(image == null) {
			paint(canvas, look);
			cache.store(canvas.image(), name);
		} else {
			canvas.paint(image);
		}
	}

}
