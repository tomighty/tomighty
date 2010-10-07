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

package org.tomighty.ui.widget;

public class Padding {
	
	private float top;
	private float right;
	private float bottom;
	private float left;

	public Padding() {
		this(0f);
	}
	
	public Padding(float all) {
		this(all, all, all, all);
	}

	public Padding(float top, float right, float bottom, float left) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	public float top() {
		return top;
	}

	public float right() {
		return right;
	}

	public float bottom() {
		return bottom;
	}

	public float left() {
		return left;
	}

	public float horizontal() {
		return left + right;
	}

	public float vertical() {
		return top + bottom;
	}

}
