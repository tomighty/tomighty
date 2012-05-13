package org.tomighty.ui.tray.menu;

import com.google.inject.Injector;
import org.tomighty.ui.options.OptionsDialog;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ShowOptions implements ActionListener {

    @Inject
    private Injector injector;

    @Override
    public void actionPerformed(ActionEvent e) {
        OptionsDialog dialog = injector.getInstance(OptionsDialog.class);
        dialog.showDialog();
    }

}
