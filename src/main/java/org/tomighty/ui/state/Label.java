/*
Copyright 2010 Célio Cidral Junior

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package org.tomighty.ui.state;

import java.awt.Color;
import java.awt.Font;

import org.tomighty.ui.Colors;
import org.tomighty.ui.widget.AlignX;
import org.tomighty.ui.widget.AlignY;
import org.tomighty.ui.widget.Padding;
import org.tomighty.ui.widget.TextPanel;

public class Label {
	
	public static TextPanel small(String text) {
		return create(0f, text);
	}

	public static TextPanel medium(String text) {
		return create(18f, text);
	}

	public static TextPanel big(String text) {
		return create(30f, text);
	}

	public static TextPanel create(float size, String text) {
		TextPanel label = new TextPanel();
		label.setForeground(Color.WHITE);
		label.setShadowColor(Colors.DARK);
		label.setAlignX(AlignX.CENTER);
		label.setAlignY(AlignY.CENTER);
		label.setAntialiasing(true);
		label.setPadding(new Padding(2f));
		if (size > 0f) {
			Font font = label.getFont();
			label.setFont(font.deriveFont(size));
		}
		label.setText(text);
		return label;
	}

}
