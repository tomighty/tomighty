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

package org.tomighty.ui.state.laf.look.themes;

import org.tomighty.ui.state.laf.look.CachedTheme;
import org.tomighty.ui.state.laf.look.Colors;
import org.tomighty.ui.state.laf.look.Look;
import org.tomighty.ui.util.Canvas;

import com.jhlabs.image.NoiseFilter;

public class Grainy extends CachedTheme {

	@Override
	public void paint(Canvas canvas, Look look) {
		Colors colors = look.colors();
		canvas.paintGradient(colors.background());
		NoiseFilter filter = new NoiseFilter();
		filter.setMonochrome(true);
		filter.setDensity(0.5f);
		filter.setAmount(13);
		canvas.applyFilter(filter);
	}

}
