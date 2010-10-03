package org.tomighty.log;

import org.tomighty.ioc.Provider;

public class LogProvider implements Provider<Log> {

	@Override
	public Log createFor(Object needer) {
		return new Log(needer.getClass());
	}

}
