package org.tomighty.ui.menu;

import com.google.inject.Injector;
import org.tomighty.ui.plugin.PluginManagerWindow;

import javax.inject.Inject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPluginManager implements ActionListener {

    @Inject
    private Injector injector;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        PluginManagerWindow pluginManagerWindow = injector.getInstance(PluginManagerWindow.class);
        pluginManagerWindow.setVisible(true);
    }

}
