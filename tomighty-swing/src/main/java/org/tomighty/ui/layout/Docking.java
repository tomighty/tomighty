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

package org.tomighty.ui.layout;

public class Docking {

	static final String FILL = "Fill";
	static final String RIGHT_TOP = "TopRight";
	
	public static String fill() {
		return FILL;
	}
	
	public static String rightTop() {
		return rightTop(0, 0);
	}
	
	public static String rightTop(int right, int top) {
		return RIGHT_TOP + "=" + right + "." + top;
	}

}
