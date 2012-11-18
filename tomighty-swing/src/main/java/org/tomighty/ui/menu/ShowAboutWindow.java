package org.tomighty.ui.menu;

import com.google.inject.Injector;
import org.tomighty.ui.about.AboutDialog;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowAboutWindow implements ActionListener {

    @Inject
    private Injector injector;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        AboutDialog about = injector.getInstance(AboutDialog.class);
        about.showDialog();
    }

}
