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

package org.tomighty.ui.util;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class CheckableFileField extends JPanel {

	private JCheckBox checkBox;
	private JTextField fileNameField;
	private JButton selectFileButton;
	private JFileChooser fileChooser;
	private File file;

	public CheckableFileField() {
		setLayout(new BorderLayout());
		
		JPanel south = new JPanel(new BorderLayout());
		south.add(fileNameField = new JTextField(), CENTER);
		south.add(selectFileButton = new JButton("Select file..."), EAST);
		
		add(checkBox = new JCheckBox(), NORTH);
		add(south);
		
		fileNameField.setEditable(false);
		checkBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateFileSelectionState();
			}
		});
		selectFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});
		
		updateFileSelectionState();
		file(null);
	}

	public void text(String text) {
		checkBox.setText(text);
	}

	public boolean isChecked() {
		return checkBox.isSelected();
	}

	public void checked(boolean checked) {
		checkBox.setSelected(checked);
		updateFileSelectionState();
	}

	public File file() {
		return file;
	}

	public void file(File file) {
		this.file = file;
		String text;
		if(file == null) {
			text = "<default>";
		} else {
			text = file.getName();
		}
		fileNameField.setText(text);
	}

	private void updateFileSelectionState() {
		boolean enable = checkBox.isSelected();
		fileNameField.setEnabled(enable);
		selectFileButton.setEnabled(enable);
	}

	private void chooseFile() {
		if(fileChooser == null) {
			File directory = file != null ? file.getParentFile() : null;
			fileChooser = new JFileChooser(directory);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV files", "wav");
			fileChooser.setFileFilter(filter);
		}
	    int returnVal = fileChooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	file(fileChooser.getSelectedFile());
	    }
	}

}
