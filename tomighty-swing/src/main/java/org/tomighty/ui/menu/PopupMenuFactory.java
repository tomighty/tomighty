package org.tomighty.ui.menu;

import com.google.inject.Injector;
import org.tomighty.i18n.Messages;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionListener;

public class PopupMenuFactory {

    @Inject private Injector injector;
    @Inject private Messages messages;

    public JPopupMenu create(Action[] items) {
        JPopupMenu menu = new JPopupMenu();

        if(items != null && items.length > 0) {
            for(Action action : items) {
                injector.injectMembers(action);
                JMenuItem item = new JMenuItem(action);
                menu.add(item);
            }

            menu.addSeparator();
        }

        menu.add(menuItem("Options", injector.getInstance(ShowOptions.class)));
        menu.add(menuItem("About", injector.getInstance(ShowAboutWindow.class)));
        menu.addSeparator();
        menu.add(menuItem("Close", new Exit()));

        return menu;
    }

    private JMenuItem menuItem(String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(messages.get(text));
        item.addActionListener(listener);
        return item;
    }

}
