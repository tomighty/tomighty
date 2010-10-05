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

package org.tomighty.ui.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Closest {

	public static Location location(Point mouseLocation) {
		List<Location> list = new ArrayList<Location>();
		list.add(new Top(mouseLocation));
		list.add(new Left(mouseLocation));
		list.add(new Bottom(mouseLocation));
		list.add(new Right(mouseLocation));
		Location closest = null;
		for(Location location : list) {
			if(closest == null || location.distanceFromScreenSide() < closest.distanceFromScreenSide()) {
				closest = location;
			}
		}
		return closest;
	}

}
