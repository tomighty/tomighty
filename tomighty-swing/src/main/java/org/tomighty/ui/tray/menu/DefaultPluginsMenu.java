package org.tomighty.ui.tray.menu;

import com.google.inject.Injector;
import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.plugin.PluginLoaded;
import org.tomighty.i18n.Messages;
import org.tomighty.plugin.Plugin;
import org.tomighty.ui.tray.PluginsMenu;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultPluginsMenu implements PluginsMenu {

    private Menu menu;

    @Inject private Messages messages;
    @Inject private Injector injector;
    @Inject private Bus bus;

    @PostConstruct
    public void createMenu() {
        menu = new Menu(messages.get("Plugins"));
        menu.add(menuItem("Manage plugins...", ShowPluginManagerWindow.class));
    }

    @PostConstruct
    public void addMenuItemsForLoadedPlugins() {
        bus.subscribe(new AddMenuItemForLoadedPlugin(), PluginLoaded.class);
    }

    @Override
    public void addTo(PopupMenu popupMenu) {
        popupMenu.add(menu);
    }

    private MenuItem menuItem(String text, Class<? extends ActionListener> actionListenerClass) {
        MenuItem item = new MenuItem(messages.get(text));
        item.addActionListener(injector.getInstance(actionListenerClass));
        return item;
    }

    private class AddMenuItemForLoadedPlugin implements Subscriber<PluginLoaded> {
        @Override
        public void receive(PluginLoaded message) {
            Plugin plugin = message.getPlugin();

            if(plugin.getMenuItem() != null)
                menu.add(plugin.getMenuItem());
        }
    }

}
