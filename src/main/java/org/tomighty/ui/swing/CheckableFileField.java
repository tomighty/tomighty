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

package org.tomighty.ui.swing;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;

@SuppressWarnings("serial")
public class CheckableFileField extends JPanel implements Initializable {

	@Inject private Messages messages;
	private JCheckBox checkBox;
	private JTextField filenameField;
	private JButton defaultButton;
	private JFileChooser fileChooser;
	private File file;

	public CheckableFileField() {
		setLayout(new BorderLayout());
		
		JPanel south = new JPanel(new BorderLayout());
		south.add(filenameField = new JTextField(), CENTER);
		south.add(defaultButton = new JButton(), EAST);
		
		add(checkBox = new JCheckBox(), NORTH);
		add(south);
		
		filenameField.setEditable(false);
		filenameField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filenameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && filenameField.isEnabled()) {
					chooseFile();
				}
			}
		});
		checkBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateFileSelectionState();
			}
		});
		defaultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				file(null);
			}
		});
		
		updateFileSelectionState();
		file(null);
	}
	
	@Override
	public void initialize() {
		defaultButton.setText(messages.get("Default"));
		filenameField.setToolTipText(messages.get("Click to select a file"));
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
			text = defaultButton.getText();
		} else {
			text = file.getName();
		}
		filenameField.setText(text);
		defaultButton.setVisible(file != null);
	}

	private void updateFileSelectionState() {
		boolean enable = checkBox.isSelected();
		filenameField.setEnabled(enable);
		defaultButton.setEnabled(enable);
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
