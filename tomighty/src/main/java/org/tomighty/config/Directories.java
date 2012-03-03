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

package org.tomighty.config;

import org.tomighty.io.FileSystemDirectory;
import org.tomighty.io.Directory;

import java.io.File;

public class Directories {
	
	private File configuration;
	
	public Directories() {
		File userHome = new File(System.getProperty("user.home"));
		configuration = new File(userHome, ".tomighty");
		configuration.mkdirs();
	}

	public File configuration() {
		return configuration;
	}

    public Directory plugins() {
        File path = new File(configuration(), "plugins");
        Directory directory = new FileSystemDirectory(path);
        directory.create();
        return directory;
    }

}
