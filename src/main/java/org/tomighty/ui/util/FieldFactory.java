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
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class FieldFactory {
	
	public static JFormattedTextField createIntegerField(int minValue, int maxLength) {
		IntegerFormatter formatter = new IntegerFormatter(minValue);
		JFormattedTextField field = new JFormattedTextField(formatter);
		field.setHorizontalAlignment(JTextField.CENTER);
		field.addKeyListener(new FieldListener(maxLength));
		return field;
	}
	
	@SuppressWarnings("serial")
	private static class IntegerFormatter extends AbstractFormatter {
		
		private final int minValue;
		private final NumberFormatter numberFormatter;

		public IntegerFormatter(int minValue) {
			this.minValue = minValue;
			numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		}

		@Override
		public Object stringToValue(String text) throws ParseException {
			Number value = (Number) numberFormatter.stringToValue(text);
			if (value.intValue() < minValue) {
				throw new ParseException("Value cannot be less than "+minValue, 0);
			}
			return value;
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			return numberFormatter.valueToString(value);
		}
		
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
