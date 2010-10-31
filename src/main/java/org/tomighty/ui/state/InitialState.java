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

package org.tomighty.ui.state;

import java.awt.Component;
import java.awt.Image;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.tomighty.util.Images;

public class InitialState extends UiStateSupport {

	@Override
	protected String title() {
		return null;
	}

	@Override
	protected Component createContent() {
		Image image = Images.get("/tomato-48x48.png");
		ImageIcon imageIcon = new ImageIcon(image);
		return new JLabel(imageIcon);
	}

	@Override
	protected Action[] primaryActions() {
		return new Action[] {
			new ToState(messages.get("Start pomodoro"), Pomodoro.class)
		};
	}

	@Override
	protected Action[] secondaryActions() {
		return null;
	}

}
