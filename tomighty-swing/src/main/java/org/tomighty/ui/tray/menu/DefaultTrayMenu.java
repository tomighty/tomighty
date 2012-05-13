package org.tomighty.ui.tray.menu;

import org.tomighty.i18n.Messages;
import org.tomighty.ui.tray.TrayMenu;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultTrayMenu implements TrayMenu {

    private final PopupMenu popupMenu;

    @Inject private Messages messages;
    @Inject private ShowOptions showOptions;
    @Inject private ShowAboutWindow showAboutWindow;

    @Inject
    public DefaultTrayMenu() {
        popupMenu = new PopupMenu();
    }

    @PostConstruct
    public void initialize() {
        popupMenu.add(menuItem(messages.get("Options"), showOptions));
        popupMenu.add(menuItem(messages.get("About"), showAboutWindow));
        popupMenu.addSeparator();
        popupMenu.add(menuItem(messages.get("Close"), new Exit()));
    }

    private MenuItem menuItem(String text, ActionListener listener) {
        MenuItem item = new MenuItem(text);
        item.addActionListener(listener);
        return item;
    }

    @Override
    public PopupMenu getPopupMenu() {
        return popupMenu;
    }

}
