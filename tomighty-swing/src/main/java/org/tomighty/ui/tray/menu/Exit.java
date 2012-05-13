package org.tomighty.ui.tray.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Exit implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
