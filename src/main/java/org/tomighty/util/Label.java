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

package org.tomighty.util;

import java.awt.Component;

import javax.swing.JLabel;

public class Label {

	public static JLabel small(String text)
	{
		return create(0f, text);
	}

	public static JLabel medium(String text)
	{
		return create(18f, text);
	}

	public static JLabel big(String text)
	{
		return create(30f, text);
	}

	public static JLabel create(float size, String text)
	{
		JLabel label = new JLabel(text, JLabel.CENTER);
		if(size > 0f) {
			label.setFont(label.getFont().deriveFont(size));
		}
		return label;
	}

	public static Component html(String html) {
		return create(0f, html);
	}

}
