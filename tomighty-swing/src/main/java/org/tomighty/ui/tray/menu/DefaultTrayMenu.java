package org.tomighty.ui.tray.menu;

import com.google.inject.Injector;
import org.tomighty.i18n.Messages;
import org.tomighty.ui.tray.PluginsMenu;
import org.tomighty.ui.tray.TrayMenu;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultTrayMenu implements TrayMenu {

    private final PopupMenu popupMenu;

    @Inject private Messages messages;
    @Inject private Injector injector;
    @Inject private PluginsMenu pluginsMenu;

    public DefaultTrayMenu() {
        popupMenu = new PopupMenu();
    }

    @PostConstruct
    public void createMenuItems() {
        popupMenu.add(menuItem("Options", injector.getInstance(ShowOptions.class)));
        pluginsMenu.addTo(popupMenu);
        popupMenu.add(menuItem("About", injector.getInstance(ShowAboutWindow.class)));
        popupMenu.addSeparator();
        popupMenu.add(menuItem("Close", new Exit()));
    }

    @Override
    public PopupMenu getPopupMenu() {
        return popupMenu;
    }

    private MenuItem menuItem(String text, ActionListener listener) {
        MenuItem item = new MenuItem(messages.get(text));
        item.addActionListener(listener);
        return item;
    }

}
