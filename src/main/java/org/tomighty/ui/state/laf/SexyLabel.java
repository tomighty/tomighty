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

package org.tomighty.ui.state.laf;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import org.tomighty.ui.Colors;

public class SexyLabel {
	
	public static JLabel small(String text) {
		return create(0f, text);
	}

	public static JLabel medium(String text) {
		return create(19f, text);
	}
	
	public static JLabel big() {
		return big(null);
	}

	public static JLabel big(String text) {
		return create(36f, text);
	}

	public static JLabel create(float size, String text) {
		JLabel label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setBackground(Colors.DARK);
		label.setUI(SexyLabelUI.INSTANCE);
		if (size > 0f) {
			Font font = label.getFont();
			label.setFont(font.deriveFont(size));
		}
		label.setText(text);
		return label;
	}

}
