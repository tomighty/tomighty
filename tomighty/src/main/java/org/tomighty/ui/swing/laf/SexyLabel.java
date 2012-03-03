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

package org.tomighty.ui.swing.laf;

import java.awt.Font;

import javax.inject.Inject;
import javax.swing.JLabel;

public class SexyLabel {
	
	@Inject
    private SexyLabelUI labelUI;
	
	public JLabel small(String text) {
		return create(11f, text);
	}

	public JLabel medium(String text) {
		return create(19f, text);
	}
	
	public JLabel big() {
		return big(null);
	}

	public JLabel big(String text) {
		return create(38f, text);
	}

	public JLabel create(float size, String text) {
		JLabel label = new JLabel();
		label.setUI(labelUI);
		if (size > 0f) {
			Font font = label.getFont();
			label.setFont(font.deriveFont(size));
		}
		label.setText(text);
		return label;
	}

}
