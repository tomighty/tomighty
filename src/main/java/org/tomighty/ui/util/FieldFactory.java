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

package org.tomighty.ui.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class FieldFactory {
	
	public static JFormattedTextField createIntegerField(int minValue, int maxLength) {
		JFormattedTextField field = new JFormattedTextField(NumberFormat.getIntegerInstance());
		field.setHorizontalAlignment(JTextField.CENTER);
		field.addKeyListener(new FieldListener(maxLength));
		return field;
	}
	
	private static class FieldListener extends KeyAdapter {
		
		private final int maxLength;

		public FieldListener(int maxLength) {
			this.maxLength = maxLength;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if(!Character.isDigit(e.getKeyChar())) {
				e.consume();
				return;
			}
			JTextField field = (JTextField) e.getSource();
			String text = field.getText();
			if(text == null) return;
			boolean reachedMaxLength = text.length() >= maxLength;
			boolean noTextSelected = field.getSelectedText() == null;
			if(reachedMaxLength && noTextSelected) {
				e.consume();
			}
		}
		
	}

}
