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
