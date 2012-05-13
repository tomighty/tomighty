package org.tomighty.ui.tray.menu;

import com.google.inject.Injector;
import org.tomighty.ui.about.AboutDialog;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ShowAboutWindow implements ActionListener {

    @Inject
    private Injector injector;

    @Override
    public void actionPerformed(ActionEvent e) {
        AboutDialog about = injector.getInstance(AboutDialog.class);
        about.showDialog();
    }

}
