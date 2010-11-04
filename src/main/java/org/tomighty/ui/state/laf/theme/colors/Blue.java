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

package org.tomighty.ui.state.laf.theme.colors;

import java.awt.Color;

import org.tomighty.ui.state.laf.theme.ColorTone;

public class Blue implements ColorTone {

	private static final Color BASE = new Color(48, 48, 80);
	private static final Color LIGHT = BASE.brighter().brighter();
	private static final Color DARK = BASE.darker().darker();
	private static final Color LIGHT_BORDER = LIGHT.brighter();

	@Override
	public Color light() {
		return LIGHT;
	}

	@Override
	public Color dark() {
		return DARK;
	}

	@Override
	public Color lightBorder() {
		return LIGHT_BORDER;
	}

	@Override
	public Color shadowBorder() {
		return DARK;
	}

	@Override
	public Color text() {
		return Color.WHITE;
	}

}
