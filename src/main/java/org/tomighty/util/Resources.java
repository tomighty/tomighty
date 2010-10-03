package org.tomighty.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resources {
	
	public static String readText(String resourceName) {
		InputStream input = Resources.class.getResourceAsStream(resourceName);
		InputStreamReader reader = new InputStreamReader(input);
		BufferedReader br = new BufferedReader(reader);
		StringBuilder text = new StringBuilder();
		String line;
		try {
			while((line = br.readLine()) != null) {
				if(text.length() > 0) {
					text.append('\n');
				}
				text.append(line);
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return text.toString();
	}
	
}
