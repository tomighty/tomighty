package org.tomighty.ui.plugin;

import org.tomighty.i18n.Messages;
import org.tomighty.plugin.Plugin;
import org.tomighty.plugin.PluginManager;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PluginManagerWindow extends JFrame {

    private final DefaultListModel pluginListModel;

    public PluginManagerWindow() {
        pluginListModel = new DefaultListModel();
        setContentPane(createUI());
        pack();
    }

    @Inject
    public void setPlugins(PluginManager pluginManager) {
        List<Plugin> plugins = pluginManager.getPlugins();
        for(Plugin plugin : plugins)
            pluginListModel.addElement(new PluginModel(plugin));
    }

    @Inject
    public void setMessages(Messages messages) {
        this.setTitle(messages.get("Plugins"));
    }

    private JPanel createUI() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(createPluginListView(), BorderLayout.CENTER);
        return contentPane;
    }

    private JScrollPane createPluginListView() {
        JList pluginListView = new JList(pluginListModel);
        pluginListView.setPreferredSize(new Dimension(100, 100));
        return new JScrollPane(pluginListView);
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
