package org.tomighty.config;

import org.tomighty.ioc.Inject;

public class Options {
	
	private static final String AUTOHIDE_WINDOW = "option.window.autohide";
	
	@Inject private Configuration config;

	public boolean autoHide() {
		return config.asBoolean(AUTOHIDE_WINDOW, true);
	}

	public void autoHide(boolean autoHide) {
		config.set(AUTOHIDE_WINDOW, autoHide);
	}

}
