package org.tomighty.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exit implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

}
