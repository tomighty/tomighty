package org.tomighty.ui.options;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tomighty.i18n.Messages;
import org.tomighty.ui.util.FieldFactory;

public class OptionCommons {

    private Messages messages;

    public OptionCommons(Messages messages) {
        this.messages = messages;
    }

    public JFormattedTextField addStandardOption(Container container, String name, String labelMessage) {
        JFormattedTextField field = FieldFactory.createIntegerField(1, 2);
        JLabel label = new JLabel(messages.get(name), JLabel.TRAILING);
        label.setLabelFor(field);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 5, 5));
        panel.add(label);
        panel.add(field);
        panel.add(new JLabel(messages.get(labelMessage)));
        container.add(panel);
        return field;
    }

    public int valueOf(JFormattedTextField field) {
        String text = field.getText();
        return Integer.parseInt(text);
    }

}
