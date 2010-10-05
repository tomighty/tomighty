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
