package org.tomighty.ui.plugin;

import org.tomighty.i18n.Messages;
import org.tomighty.plugin.Plugin;
import org.tomighty.plugin.PluginManager;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PluginManagerWindow extends JFrame {

    @Inject
    public PluginManagerWindow(PluginManager pluginManager) {
        DefaultListModel pluginListModel = new DefaultListModel();
        JList pluginListView = new JList(pluginListModel);
        pluginListView.setPreferredSize(new Dimension(100, 100));

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(new JScrollPane(pluginListView), BorderLayout.CENTER);

        setContentPane(contentPane);

        pack();

        List<Plugin> plugins = pluginManager.getPlugins();
        for(Plugin plugin : plugins) {
            pluginListModel.addElement(new PluginModel(plugin));
        }
    }

    @Inject
    public void setMessages(Messages messages) {
        this.setTitle(messages.get("Plugins"));
    }

    private class PluginModel {
        private final Plugin plugin;

        public PluginModel(Plugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public String toString() {
            return plugin.getName();
        }
    }
}
